package com.g3b1.wsrestadapter.web.rest;

import com.g3b1.wsrestadapter.data.dto.MsgDTO;
import com.g3b1.wsrestadapter.service.IMsgMgr;
import com.g3b1.wsrestadapter.web.dto.MsgInsDTO;
import com.g3b1.wsrestadapter.web.dto.SingleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 2022-05-14 09:57 - gun
 */
@RestController
@RequestMapping("/api/v1/msgs")
public class MsgRest {

    private final IMsgMgr msgMgr;

    @Autowired
    public MsgRest(@Qualifier("mockMsgMgr") IMsgMgr msgMgr) {
        this.msgMgr = msgMgr;
    }

    @Operation(summary = "Creates the message.")
    @ApiResponses(value = {
            @ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleResponseDTO.class))}),
    })
    @PostMapping(value = "create", consumes = "application/json")
    public ResponseEntity<SingleResponseDTO> create(@RequestBody MsgInsDTO data) {
        MsgDTO msgDTO = msgMgr.create(data);
        SingleResponseDTO singleResponseDTO = SingleResponseDTO.builder().messageList(Collections.emptyList()).payload(msgDTO).build();
        return ResponseEntity.ok(singleResponseDTO);
    }

}
