package com.g3b1.wsrestadapter.service;

import com.g3b1.wsrestadapter.data.dto.MsgDTO;
import com.g3b1.wsrestadapter.web.dto.MsgInsDTO;

/**
 * 2022-05-14 14:24 - gun
 */
public interface IMsgMgr {

    MsgDTO create(MsgInsDTO msgIns);
}
