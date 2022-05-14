package com.g3b1.wsrestadapter.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 2022-05-13 14:11 - gun
 */
@Component
@Slf4j
public class RestListeners {

    private final Map<RequestMappingInfo, HandlerMethodWithBean> handlerMethods = new HashMap<>();

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext ctx = event.getApplicationContext();
        RequestMappingHandlerMapping handlerMapping = ctx.getBean(RequestMappingHandlerMapping.class);
        for(RequestMappingInfo info :handlerMapping.getHandlerMethods().keySet()) {
            HandlerMethod handlerMethod = handlerMapping.getHandlerMethods().get(info);
            handlerMethods.put(info, new HandlerMethodWithBean(handlerMethod, ctx.getBean(handlerMethod.getBean().toString())));
        }
        handlerMethods.forEach((key, value) -> log.info("{} {}", key, value));
    }


    public HandlerMethodWithBean getHandlerMethodForDestination(String destination) {
        for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
            if (requestMappingInfo.getPatternsCondition() == null)
                continue;
            String handlerDestination = requestMappingInfo.getPatternsCondition().getPatterns().iterator().next();
            if(handlerDestination.equals(destination)) {
                return handlerMethods.get(requestMappingInfo);
            }
        }
        throw new NoSuchElementException("No handler found for " + destination);
    }

    public Map<RequestMappingInfo, HandlerMethodWithBean>  getHandlerMethods() {
        return handlerMethods;
    }
}
