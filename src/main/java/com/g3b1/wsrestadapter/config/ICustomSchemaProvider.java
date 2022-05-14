package com.g3b1.wsrestadapter.config;

import io.swagger.v3.oas.models.media.Schema;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 2022-05-03 02:15 - gun
 */
public interface ICustomSchemaProvider {

    @SuppressWarnings("rawtypes")
    Map<String, Schema> provide(Set<Class> customDtoList);
}
