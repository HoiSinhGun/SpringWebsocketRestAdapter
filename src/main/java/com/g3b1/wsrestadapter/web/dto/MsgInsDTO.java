package com.g3b1.wsrestadapter.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 2022-05-14 14:18 - gun
 */
@Data
@Schema(name = "MsgIns")
public class MsgInsDTO {
    public Long toUiAdapterId;
    public String data;
}
