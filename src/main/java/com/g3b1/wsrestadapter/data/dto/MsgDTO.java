package com.g3b1.wsrestadapter.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 2022-05-14 14:25 - gun
 */
@Data
@Builder
@Schema(name = "Msg")
public class MsgDTO {

    public Long id;
    public String key;
    public Long userId;
    public Long timestamp;
    public Long toUiAdapterId;
    public String data;
}
