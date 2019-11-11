package com.chuan.client;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class Main {
	private static ClientNetty client = new ClientNetty();
	public static void main(String[] args)  {
		
		try {
			client.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ByteBuf src = ByteBufAllocator.DEFAULT.buffer();
		src.writeByte(2);
		src.writeInt(2);
		String first = "i am first";
		JSONObject json = new JSONObject();
		json.put("dev", "bkrderp");
		json.put("market", "360");
		json.put("packs", "789,369");
		first = json.toJSONString();
		int len = first.getBytes().length;
		byte[] byt = first.getBytes();
		src.writeInt(len);
		src.writeBytes(byt, 0, len);
		String second = "i am second";
		json = new JSONObject();
		json.put("dev", "baidu");
		json.put("market", "360");
		json.put("packs", "123,456");
		second = json.toJSONString();
		
		len = second.getBytes().length;
		byt = second.getBytes();
		src.writeInt(len);
		src.writeBytes(byt, 0, len);
		
		ClientNetty.writeandflash(src);
		
		String param = "baidu";
		src = ByteBufAllocator.DEFAULT.buffer();
		src.writeByte(1);
		src.writeInt(param.getBytes().length);
		src.writeBytes(param.getBytes());
		ClientNetty.writeandflash(src);
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
