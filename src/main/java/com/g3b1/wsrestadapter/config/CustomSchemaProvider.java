package com.g3b1.wsrestadapter.config;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 2022-05-03 02:15 - gun
 */
@Component
@Slf4j
public class CustomSchemaProvider implements ICustomSchemaProvider {

    @SuppressWarnings("rawtypes")
    private final Set<Class> dtoSchemaSet = new HashSet<>();

    @SuppressWarnings("rawtypes")
    private final Map<String, Schema> dtoSchemaMap = new HashMap<>();


    @SuppressWarnings("rawtypes")
    @Override
    public Map<String, Schema> provide(Set<Class> customDtoList) {
        dtoSchemaSet.addAll(customDtoList);
        Map<String, Schema> schemaMap = buildDtoSchemaMap();
        List<Schema> listSchemaList = new ArrayList<>();
        Map<String, Schema> provideMap = new HashMap<>();
        for (String name : schemaMap.keySet()) {
            Schema nameList = new ArraySchema().items(schemaMap.get(name));
            listSchemaList.add(nameList);
            // Add single item DTO
            provideMap.put(name, schemaMap.get(name));
            // Add list for item DTO
            provideMap.put(name + "List", nameList);
        }
        Schema singleResponsePayload = new ComposedSchema().oneOf(
                new ArrayList<>(schemaMap.values())
        );
        Schema listResponsePayload = new ComposedSchema().oneOf(
                new ArrayList<>(listSchemaList)
        );
        provideMap.put("SingleResponsePayload", singleResponsePayload);
        provideMap.put("ListResponsePayload", listResponsePayload);
        return provideMap;
    }

    @SuppressWarnings("rawtypes")
    private Map<String, Schema> buildDtoSchemaMap() {
        if (this.dtoSchemaMap.size() != 0)
            return this.dtoSchemaMap;

        for (Class clazz : this.dtoSchemaSet) {
            String name = clazz.getSimpleName();
            name = name.replace("DTO", "");
            dtoSchemaMap.put(name, new ObjectSchema().$ref(name));
            log.info("Adding schema: " + name);
        }
        return this.dtoSchemaMap;
    }
}
