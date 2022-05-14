package com.g3b1.wsrestadapter.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * 2022-05-14 02:45 - gun
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Bridge")
public class BridgeDTO implements IResponseDTO{
    public String requestId;
    public Map<String, String> paramMap;
}
