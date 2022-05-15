package com.g3b1.wsrestadapter.config;

import com.g3b1.wsrestadapter.data.dto.MsgDTO;
import com.g3b1.wsrestadapter.web.dto.MsgInsDTO;
import com.g3b1.wsrestadapter.web.dto.SingleResponseDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Configuration
@OpenAPIDefinition
public class Swagger2Config {

    private final ServerProperties serverProperties;
    private final CustomSchemaProvider customSchemaProvider;
    private final CommandLineRunner commandLineRunner;

    @Autowired
    public Swagger2Config(ServerProperties serverProperties, CustomSchemaProvider customSchemaProvider, CommandLineRunner commandLineRunner) {
        this.serverProperties = serverProperties;
        this.customSchemaProvider = customSchemaProvider;
        this.commandLineRunner = commandLineRunner;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .components(new Components());
        Components components = openAPI.getComponents();
        //noinspection rawtypes
        Map<String, Schema> schemas = components.getSchemas();
        schemas = schemas == null ? new HashMap<>() : schemas;
        schemas.putAll(customSchemaProvider.provide(new HashSet<>(Arrays.asList(MsgDTO.class, MsgInsDTO.class))));
        components.schemas(schemas);
        openAPI.components(components);
        openAPI.info(new Info().title(serverProperties.appId).version("snapshot"));
        try {
            this.commandLineRunner.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return openAPI;
    }
}