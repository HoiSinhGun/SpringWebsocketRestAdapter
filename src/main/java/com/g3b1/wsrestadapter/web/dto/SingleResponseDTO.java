package com.g3b1.wsrestadapter.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 2022-05-14 09:58 - gun
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(name = "SingleResponse")
public class SingleResponseDTO implements IResponseDTO {

    public List<String> messageList;
    public String requestId;
    @Schema(ref = "SingleResponsePayload")
    public Object payload;

}
