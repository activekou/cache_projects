package com.chuan.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientNettyHandler extends SimpleChannelInboundHandler<ByteBuf>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("我收到来自客户端的信息");
		int a = msg.readInt();
		byte[] b = new byte[a];
		msg.readBytes(b, 0, a);
		String str = new String(b);
		System.out.println(str);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ClientNetty.setChannel(ctx.channel());
		System.out.println("channelActive");
//		super.channelActive(ctx);
	}
}
