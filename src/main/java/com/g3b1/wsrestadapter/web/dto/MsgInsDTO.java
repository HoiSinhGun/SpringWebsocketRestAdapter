package com.g3b1.wsrestadapter.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2022-05-14 14:18 - gun
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(name = "MsgIns")
public class MsgInsDTO {
    @Schema(name = "toUiAdapterId")
    public Long toUiAdapterId;
    @Schema(name = "data")
    public String data;
}
