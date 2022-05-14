package com.g3b1.wsrestadapter.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 2022-05-14 09:53 - gun
 */
@Data
@Builder
@Schema(name = "RestHandler")
public class RestHandlerDTO {
    public String requestMappingInfoKey;
    public String methodName;
    public List<String> parameterTypes;
    public String returnType;
    public String description;
}
