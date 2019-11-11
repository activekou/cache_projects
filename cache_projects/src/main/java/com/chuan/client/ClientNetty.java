package com.chuan.client;

import java.util.concurrent.atomic.AtomicReference;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientNetty {
	private Bootstrap bootstrap;
	private EventLoopGroup workerGroup;
	private static AtomicReference<Channel> channel = new AtomicReference<Channel>();
	
	public static boolean setChannel(Channel channel){
		ClientNetty.channel.set(channel);
		return true;
	} 
	
	public static boolean writeandflash(ByteBuf msg){
		channel.get().writeAndFlush(Unpooled.wrappedBuffer(msg));
		return true;
	}
	
	public void start()throws Exception{
		String host = "127.0.0.1";
		int port = 1350;
		bootstrap = new Bootstrap();
		workerGroup = new NioEventLoopGroup();
		bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				// TODO Auto-generated method stub
				ChannelPipeline p = ch.pipeline();
				p.addLast("frameEncoder", new LengthFieldPrepender(4));
				p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				p.addLast("idleStateHandler", new IdleStateHandler(0, 0, 300)); // 5分钟空闲断开
				p.addLast("handler",new ClientNettyHandler());
			}
		}).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
		
		ChannelFuture f = bootstrap.connect(host, port).await();
//		Channel ch = f.channel();
	}
}
