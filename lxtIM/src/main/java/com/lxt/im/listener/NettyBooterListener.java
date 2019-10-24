package com.lxt.im.listener;

import com.lxt.im.initializer.NettySocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 用于和客户端进行连接
 *
 * @author phubing
 *
 */
@Slf4j
@Component
public class NettyBooterListener implements ApplicationListener<ContextRefreshedEvent>{

    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;

    @Value("${netty.socket.port}")
    private int port;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                 parentGroup = new NioEventLoopGroup();
                 childGroup = new NioEventLoopGroup();
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap
                        .group(parentGroup, childGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new NettySocketInitializer());
                serverBootstrap.bind(port);
                log.info("netty  start ok,port=>{}",port);
            } catch (Exception e) {
                parentGroup.shutdownGracefully();
                childGroup.shutdownGracefully();
                log.error("netty 启动异常", e);
            }
        }
    }

    @PreDestroy
    public void shutdown(){
        log.info("netty  shutdown start,port=>{}",port);
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
        log.info("netty  shutdown end,port=>{}",port);
    }

}
