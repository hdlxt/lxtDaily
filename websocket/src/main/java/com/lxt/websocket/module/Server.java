package com.lxt.websocket.module;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * @ClassName: Server
 * @Description: to do
 * @Author: lxt
 * @Date: 2019-03-09 16:21
 * @Version 1.0
 **/
@ServerEndpoint("/websocket/{sid}")
@Component
public class Server {
}
