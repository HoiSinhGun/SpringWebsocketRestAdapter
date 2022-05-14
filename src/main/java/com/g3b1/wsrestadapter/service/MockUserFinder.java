package com.g3b1.wsrestadapter.service;

import org.springframework.stereotype.Component;

/**
 * 2022-05-14 14:29 - gun
 */
@Component
public class MockUserFinder {

    public Long getAuthenticatedUserId() {
        return 1L;
    }
}
