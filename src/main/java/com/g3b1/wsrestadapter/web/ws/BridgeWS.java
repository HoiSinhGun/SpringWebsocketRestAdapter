package com.g3b1.wsrestadapter.web.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g3b1.wsrestadapter.web.dto.BridgeDTO;
import com.g3b1.wsrestadapter.web.dto.IResponseDTO;
import com.g3b1.wsrestadapter.web.rest.HandlerMethodWithBean;
import com.g3b1.wsrestadapter.web.rest.RestListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 2022-05-14 15:10 - gun
 */
@Controller
public class BridgeWS {

    private final RestListeners restListeners;

    @Autowired
    public BridgeWS(RestListeners restListeners) {
        this.restListeners = restListeners;
    }

    @MessageMapping("bridge/**")
    @SendToUser(destinations="/queue/bridgemsg", broadcast=false)
    public Object bridge(@SuppressWarnings("rawtypes") @Headers Map headers, @Payload BridgeDTO data) {
        String destination = headers.get("simpDestination").toString().replace("/socket/bridge", "/api/v1");
        HandlerMethodWithBean handlerMethodForDestination = restListeners.getHandlerMethodForDestination(destination);
        Method theMethod = handlerMethodForDestination.getHandlerMethod().getMethod();
        Parameter[] parameters = theMethod.getParameters();
        List<Object> argList = new ArrayList<>();
        for (Parameter p : parameters) {
            Object t = fromJsonTo(data.getParamMap().get(p.getName()), p.getType());
            argList.add(t);
        }

        try {
            //noinspection unchecked
            ResponseEntity<IResponseDTO> invoke = (ResponseEntity<IResponseDTO>) theMethod.invoke(handlerMethodForDestination.getBean(), argList.toArray());
            IResponseDTO responseDTO = invoke.getBody();
            if (responseDTO != null) {
                responseDTO.setRequestId(data.requestId);
            }
            return responseDTO;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    public <T> T fromJsonTo(String json, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // convert JSON string to Map
            //noinspection UnnecessaryLocalVariable
            T msg = mapper.readValue(json, tClass);
            return msg;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
