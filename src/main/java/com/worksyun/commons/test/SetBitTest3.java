package com.worksyun.commons.test;

import java.util.BitSet;

import redis.clients.jedis.Jedis;

public class SetBitTest3 {
	/* 
	public int uniqueCount(Jedis redis,String action, String date) {
	   String key = action + ":" + date;
	   BitSet users = BitSet.valueOf(redis.get(key.getBytes()));
	   return users.cardinality();
	 }


	public int uniqueCount(Jedis redis,String action, String... dates) {
	   BitSet all = new BitSet();
	   for (String date : dates) {
	     String key = action + ":" + date;
	     BitSet users = BitSet.valueOf(redis.get(key.getBytes()));
	     all.or(users);
	   }
	   return all.cardinality();
	 }*/


	public static byte[] bitSet2ByteArray(BitSet bitSet) {
	byte[] bytes = new byte[bitSet.size() / 8];
	for (int i = 0; i < bitSet.size(); i++) {
	int index = i / 8;
	int offset = 7 - i % 8;
	bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
	}
	return bytes;
	}


	public static BitSet byteArray2BitSet(byte[] bytes) {
	BitSet bitSet = new BitSet(bytes.length * 8);
	int index = 0;
	for (int i = 0; i < bytes.length; i++) {
	for (int j = 7; j >= 0; j--) {
	bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true
	: false);
	}
	}
	return bitSet;
	}



	/*public static void main(String[] args) {
	Jedis j = null;


	try {


	j = new Jedis("127.0.0.1", 6379);


	//setbit 参数说明
	//setbit key 用户ID 1 or 0 (1表示用户登入过 0表示没有登入 默认为0)
	// 2016-12-3 login operation user
	//设置2016-12-3登入的用户ID
	j.setbit("login:2016-12-3", 1002, true);
	j.setbit("login:2016-12-3", 101, true);
	j.setbit("login:2016-12-3", 102, true);
	j.setbit("login:2016-12-3", 103, true);
	j.setbit("login:2016-12-3", 105, true);
	//一下命令等价redis 命令 bitcount login:2016-12-3
	//BitSet b = BitSet.valueOf(j.get("login:2016-12-3").getBytes());
	BitSet b =  byteArray2BitSet(j.get("login:2016-12-3").getBytes());


	// the number of bit value 1
	int lognum3 = b.cardinality();
	System.out.println("2016-12-3   登入用户数量: " + lognum3);


	// 2016-12-3 login operation user
	//设置2016-12-4登入的用户ID
	j.setbit("login:2016-12-4", 1002, true);
	j.setbit("login:2016-12-4", 101, true);
	j.setbit("login:2016-12-4", 5852, true);
	//BitSet b2 = BitSet.valueOf(j.get("login:2016-12-4".getBytes()));
	BitSet b2 =  byteArray2BitSet(j.get("login:2016-12-4").getBytes());
    
	//设置2016-12-5登入的用户ID
		j.setbit("login:2016-12-5", 100, true);
		j.setbit("login:2016-12-5", 101, true);
		j.setbit("login:2016-12-5", 5852, true);
		//BitSet b2 = BitSet.valueOf(j.get("login:2016-12-4".getBytes()));
		BitSet b3 =  byteArray2BitSet(j.get("login:2016-12-5").getBytes());

		System.out.println(b3.toLongArray()[50]+"-----------------");
		
		
	int lognum4 = b2.cardinality();
	System.out.println("2016-12-4   登入用户数量: "
	+ b2.cardinality());
	            
	int longnum5 = b3.cardinality();
	System.out.println("2016-12-5   登入用户数量: "
			+ b2.cardinality());
	//统计连续两天都登入的用户数
	b.and(b2);
	b.and(b3);
	// or操作之后 同样userid的记录会重合不做记录，所以具体的数据统计看自己的需求而定
	int lognumexceptsameuser = b.cardinality();
	int logtotalnum = lognum3 + lognum4 + longnum5;
	System.out
	.println("2016-12-3 to 2016-12-4 login user number except same userid: "
	+ lognumexceptsameuser);
	System.out.println("2016-12-3 to 2016-12-4 login user number: "
	+ logtotalnum);

	Long ss = j.bitcount("login:2016-12-5");
	
	System.out.println(ss);
	
	Boolean s = j.getbit("login:2016-12-5", 100);
	if(s) {
		System.out.println("sadasd");
	}

	//输出连续两天都登入的用户
	    System.out.println("输出连续两天都登入的用户ID ");
	for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i + 1)) {
	System.out.print(i+"\t");
	} 



	} catch (Exception e) {


	e.printStackTrace();


	}


	finally {


	if (j != null) {


	j.disconnect();


	}


	}
	}*/
}
