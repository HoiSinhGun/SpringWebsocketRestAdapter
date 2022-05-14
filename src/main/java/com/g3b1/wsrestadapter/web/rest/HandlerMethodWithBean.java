package com.g3b1.wsrestadapter.web.rest;

import lombok.*;
import org.springframework.web.method.HandlerMethod;

/**
 * 2022-05-14 09:46 - gun
 */
@Getter
@Setter
@ToString
public class HandlerMethodWithBean {

    private final HandlerMethod handlerMethod;
    private final Object bean;

    public HandlerMethodWithBean(HandlerMethod handlerMethod, Object bean) {
        this.handlerMethod = handlerMethod;
        this.bean = bean;
    }
}
