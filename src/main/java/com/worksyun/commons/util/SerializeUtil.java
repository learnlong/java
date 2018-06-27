package com.worksyun.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	/*
	 * 对象转byte[]
	 */
	 public static byte[] serialize(Object object) {
	        ObjectOutputStream oos = null;
	        ByteArrayOutputStream baos = null;
	        try {
	            // 序列化
	            baos = new ByteArrayOutputStream();
	            oos = new ObjectOutputStream(baos);
	            oos.writeObject(object);
	            byte[] bytes = baos.toByteArray();
	            return bytes;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
        /*
         * byte[]转对象
         */
	    public static Object unserialize(byte[] bytes) {
	        ByteArrayInputStream bais = null;
	        try {
	            // 反序列化
	            bais = new ByteArrayInputStream(bytes);
	            ObjectInputStream ois = new ObjectInputStream(bais);
	            return ois.readObject();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	        /**
	         * 对象转byte[]
	         * @param obj
	         * @return
	         * @throws IOException
	         */
	        public static byte[] object2Bytes(Object obj) throws IOException{
	            ByteArrayOutputStream bo=new ByteArrayOutputStream();
	            ObjectOutputStream oo=new ObjectOutputStream(bo);
	            oo.writeObject(obj);
	            byte[] bytes=bo.toByteArray();
	            bo.close();
	            oo.close();
	            return bytes;
	        }
	        /**
	         * byte[]转对象
	         * @param bytes
	         * @return
	         * @throws Exception
	         */
	        public static Object bytes2Object(byte[] bytes) throws Exception{
	            ByteArrayInputStream in=new ByteArrayInputStream(bytes);
	            ObjectInputStream sIn=new ObjectInputStream(in);
	            return sIn.readObject();
	        }
}
