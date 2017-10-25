package com.forte.runtime.mq;

import java.io.Serializable;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/19
 */
public interface ReceiveHandler {
    void handle(Serializable message);
}
