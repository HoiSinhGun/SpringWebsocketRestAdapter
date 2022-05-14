package com.g3b1.wsrestadapter.service;

import com.g3b1.wsrestadapter.data.dto.MsgDTO;
import com.g3b1.wsrestadapter.web.dto.MsgInsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * 2022-05-14 14:26 - gun
 */
@Service
public class MockMsgMgr implements IMsgMgr {

    private final MockUserFinder mockUserFinder;

    @Autowired
    public MockMsgMgr(MockUserFinder mockUserFinder) {
        this.mockUserFinder = mockUserFinder;
    }

    @Override
    public MsgDTO create(MsgInsDTO msgIns) {
        return MsgDTO.builder().id(new Random(13L).nextLong())
                .key(UUID.randomUUID().toString()).
                timestamp(OffsetDateTime.now().toInstant().toEpochMilli()).
                toUiAdapterId(msgIns.toUiAdapterId).data(msgIns.data).userId(mockUserFinder.getAuthenticatedUserId()).build();
    }
}
