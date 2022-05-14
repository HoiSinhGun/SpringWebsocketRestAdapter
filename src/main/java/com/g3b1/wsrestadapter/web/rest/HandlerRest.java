package com.g3b1.wsrestadapter.web.rest;

import com.g3b1.wsrestadapter.web.dto.RestHandlerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2022-05-14 09:50 - gun
 */
@RestController
@RequestMapping("/api/v1/handlers")
public class HandlerRest {

    private final RestListeners restListeners;

    @Autowired
    public HandlerRest(RestListeners restListeners) {
        this.restListeners = restListeners;
    }

    @Operation(summary = "Return rest handler methods.")
    @ApiResponses(value = {
            @ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RestHandlerDTO.class))})
    })
    @GetMapping(value = "")
    public ResponseEntity<List<RestHandlerDTO>> all() {
        Map<RequestMappingInfo, HandlerMethodWithBean> map = restListeners.getHandlerMethods();
        List<RestHandlerDTO> restHandlerDTOS = map.keySet().stream().map(
                i -> RestHandlerDTO.builder().requestMappingInfoKey(i.toString()
                ).description(map.get(i).getHandlerMethod().toString()).methodName(map.get(i).getHandlerMethod().getMethod().getName()).
                        returnType(
                                map.get(i).getHandlerMethod().getMethod().getReturnType().toString()
                        ).
                        parameterTypes(Arrays.stream(map.get(i).getHandlerMethod().getMethodParameters()).map(p->p.getParameterType().getCanonicalName()).collect(Collectors.toList())).build()).collect(Collectors.toList());
        return ResponseEntity.ok(restHandlerDTOS);
    }
}
