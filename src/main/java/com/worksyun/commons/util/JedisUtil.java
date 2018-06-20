package com.worksyun.commons.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.worksyun.commons.model.Message;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	    
		private static JedisPool jedisPool; 
	    
	    /**
	     * 构建redis连接池
	     */
	    public static JedisPool getJedisPool() {
	      if (jedisPool == null) {
	        JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxTotal(1024); // 可用连接实例的最大数目,如果赋值为-1,表示不限制.
	        config.setMaxIdle(5); // 控制一个Pool最多有多少个状态为idle(空闲的)jedis实例,默认值也是8
	        config.setMaxWaitMillis(1000 * 100); // 等待可用连接的最大时间,单位毫秒,默认值为-1,表示永不超时/如果超过等待时间,则直接抛出异常
	        config.setTestOnBorrow(true); // 在borrow一个jedis实例时,是否提前进行validate操作,如果为true,则得到的jedis实例均是可用的
	        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
	      }
	      return jedisPool;
	    }
	    
	    public static String getByTemplate(final String key) {
	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	  String value = redisTemplate.execute(new RedisCallback<String>() {
	    	    @Override
	    	    public String handle(Jedis jedis) {
	    	      return jedis.get(key);
	    	    }
	    	  });
	    	  return value;
	    	}
	   
	    public static byte[] getByTemplate2(final byte[] key) {
	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	  byte[] value = redisTemplate.execute(new RedisCallback<byte[]>() {
	    	    @Override
	    	    public byte[] handle(Jedis jedis) {
	    	      return jedis.get(key);
	    	    }
	    	  });
	    	  return value;
	    	}
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public static void setByTemplate(final byte[] key,final byte[] value) {
	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	redisTemplate.execute(new RedisCallback() {
	    		 @Override
		    	    public Object handle(Jedis jedis) {
		    	       return jedis.set(key, value);
		    	    }
			});
	    }
	    
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public static void setByTemplate2(final byte[] key,final byte[] value,final int time) {
	    	RedisTemplate redisTemplate  = new RedisTemplate(getJedisPool());
	    	redisTemplate.execute(new RedisCallback(){
	    		@Override
	    	    public Object handle(Jedis jedis) {
	    	        jedis.set(key, value);
	    	        return jedis.expire(key, time);
	    	    }
	    	});
	    }
	    
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public static void hsetByTemplate(final byte[] key,final byte[] field,final byte[] value) {
	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	redisTemplate.execute(new RedisCallback() {
	    		@Override
	    	    public Object handle(Jedis jedis) {
	    	        return jedis.hset(key, field, value); 
	    	    }
	    	});
	    }
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public static void hsetByTemplate2(final String key,final String field,final String value) {
	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	redisTemplate.execute(new RedisCallback() {
	    		@Override
	    	    public Object handle(Jedis jedis) {
	    	        return jedis.hset(key, field, value); 
	    	    }
	    	});
	    }
	    
	    
	    public static String hgetByTemplate(final String key,final String field) {
	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	  String value = redisTemplate.execute(new RedisCallback<String>() {
	    	    @Override
	    	    public String handle(Jedis jedis) {
	    	      return jedis.hget(key, field);
	    	    }
	    	  });
	    	  return value;
	    	}
	    
	    
	    public static byte[] hgetByTemplate2(final byte[] key,final byte[] field) {
	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	  byte[] value = redisTemplate.execute(new RedisCallback<byte[]>() {
	    	    @Override
	    	    public byte[] handle(Jedis jedis) {
	    	      return jedis.hget(key, field);
	    	    }
	    	  });
	    	  return value;
	    	}
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public static void hdelByTemplate(final byte[] key,final byte[] field) {
	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	  redisTemplate.execute(new RedisCallback() {
	    	    @Override
	    	    public Object handle(Jedis jedis) {
	    	      return jedis.hdel(key, field);
	    	    }
	    	  });
	    	}
	    
	    /** 
	     * 存储REDIS队列 顺序存储 
	     * @param byte[] key reids键名 
	     * @param byte[] value 键值 
	     */ 
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public static void lpushByTemplate(final byte[] key,final byte[] value) {
	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	    	  redisTemplate.execute(new RedisCallback() {
	    	    @Override
	    	    public Object handle(Jedis jedis) {
	    	      return jedis.lpush(key, value);
	    	    }
	    	  });
	    }
	    
	    /** 
	     * 存储REDIS队列 反向存储 
	     * @param byte[] key reids键名 
	     * @param byte[] value 键值 
	     */ 
	    @SuppressWarnings({ "unchecked", "rawtypes" })
	  		public static void rpushByTemplate(final byte[] key,final byte[] value) {
	  	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
	  	    	  redisTemplate.execute(new RedisCallback() {
	  	    	    @Override
	  	    	    public Object handle(Jedis jedis) {
	  	    	      return jedis.rpush(key, value);
	  	    	    }
	  	    	  });
	  	    }
	    
	    /** 
	     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端 
	     * @param byte[] key reids键名 
	     * @param byte[] value 键值 
	     */  
	    @SuppressWarnings({ "unchecked", "rawtypes" })
  		public static void rpoplpushByTemplate(final byte[] key,final byte[] destination) {
  	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  	    	  redisTemplate.execute(new RedisCallback() {
  	    	    @Override
  	    	    public Object handle(Jedis jedis) {
  	    	      return jedis.rpush(key, destination);
  	    	    }
  	    	  });
  	    }
	    
	    /** 
	     * 获取队列数据 
	     * @param byte[] key 键名 
	     * @return 
	     */  
  		public static List<byte[]> lpopListByTemplate(final byte[] key) {
  	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  	    	 List<byte[]> list =  redisTemplate.execute(new RedisCallback<List<byte[]>>() {
  	    	    @Override
  	    	    public List<byte[]> handle(Jedis jedis) {
  	    	      return  jedis.lrange(key, 0, -1); 
  	    	    }
  	    	  });
  	    	 return list;
  	    }
  		
  		 /** 
  	     * 获取队列数据 
  	     * @param byte[] key 键名 
  	     * @return 
  	     */  
  		public static byte[] rpopByTemplate(final byte[] key) {
  	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  	    	 byte[] list =  redisTemplate.execute(new RedisCallback<byte[]>() {
  	    	    @Override
  	    	    public byte[] handle(Jedis jedis) {
  	    	      return  jedis.rpop(key);
  	    	    }
  	    	  });
  	    	 return list;
  	    }
  		
  		
  		 @SuppressWarnings({ "unchecked", "rawtypes" })
 		public static void hmsetByTemplate(final Object key, final Map<String, String> hash) {
 	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
 	    	  redisTemplate.execute(new RedisCallback() {
 	    	    @Override
 	    	    public Object handle(Jedis jedis) {
 	    	      return jedis.hmset(key.toString(), hash);
 	    	    }
 	    	  });
 	    	}
  		 
  		 
  		 @SuppressWarnings({ "unchecked", "rawtypes" })
  		public static void hmsetByTemplate2(final Object key, final Map<String, String> hash,final int time) {
  	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  	    	  redisTemplate.execute(new RedisCallback() {
  	    	    @Override
  	    	    public Object handle(Jedis jedis) {
  	    	      jedis.hmset(key.toString(), hash);
  	    	      return jedis.expire(key.toString(), time);
  	    	    }
  	    	  });
  	    	}
  		
  		 
  		public static List<String> hmgetByTemplate(final Object key, final String... fields) {
  	    	RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  	    	 List<String> result =  redisTemplate.execute(new RedisCallback<List<String>>() {
  	    	    @Override
  	    	    public List<String> handle(Jedis jedis) {
  	    	      return  jedis.hmget(key.toString(), fields);
  	    	    }
  	    	  });
  	    	 return result;
  	    }
  		
  		public static Set<String> hkeysByTemplate(final String key){
  			RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  			Set<String> result = redisTemplate.execute(new RedisCallback<Set<String>>() {
  				@Override
  	    	    public Set<String> handle(Jedis jedis) {
  	    	      return  jedis.hkeys(key);
  	    	    }
			});
  			return result;
  		}
  		
  		public static List<byte[]> lrangeByTemplate(final byte[] key,final int from, final int to){
  			RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  			List<byte[]> result = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
  				@Override
  	    	    public List<byte[]> handle(Jedis jedis) {
  	    	      return  jedis.lrange(key, from, to);
  	    	    }
			});
  			return result;
  		}
  		
  		
  		public static Map<byte[], byte[]> hgetAllByTemplate(final byte[] key){
  			RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  			Map<byte[],byte[]> result = redisTemplate.execute(new RedisCallback<Map<byte[],byte[]>>() {
  				@Override
  	    	    public Map<byte[],byte[]> handle(Jedis jedis) {
  	    	      return  jedis.hgetAll(key);
  	    	    }
			});
  			return result;
  		}
  		
  		
  		 @SuppressWarnings({ "unchecked", "rawtypes" })
  		public static void delByTemplate(final byte[] key) {
  	    	  RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  	    	  redisTemplate.execute(new RedisCallback() {
  	    	    @Override
  	    	    public Object handle(Jedis jedis) {
  	    	      return jedis.del(key);
  	    	    }
  	    	  });
  	    	}
  		 
  		 
  		public static long llenAllByTemplate(final byte[] key){
  			RedisTemplate redisTemplate = new RedisTemplate(getJedisPool());
  			long result = redisTemplate.execute(new RedisCallback<Long>() {
  				@Override
  	    	    public Long handle(Jedis jedis) {
  	    	      return  jedis.llen(key);
  	    	    }
			});
  			return result;
  		}
  		
	    
	   /* public static void main(String[]args) {
	    	JedisPool jedispool = JedisUtil.getJedisPool();
	    	Jedis jedis = jedispool.getResource();
	    	byte[] a = new byte[] {1,2,3};
	    	byte[] b = new byte[] {4,5,6};
	    	JedisUtil.setByTemplate(a, b);
	    	byte[] c = JedisUtil.getByTemplate2(a);
	    	System.out.println(c[0]);
	    	System.out.println("--------------------------");
	    	jedis.set("name", "webb"); // 添加数据
	    	System.out.println("name -> " + jedis.get("name"));
	    	
	    	 String key = "java framework";
	    	 
	    	  jedis.lpush(key, "spring");
	    	  jedis.lpush(key, "spring mvc");
	    	  jedis.lpush(key, "mybatis");
	    	 
	    	  System.out.println(jedis.lrange(key, 0 , -1));
	    	  
	    	  String key = "user";
	    	  
	    	  Map<String, String> map = new HashMap<>();
	    	  map.put("name", "webb");
	    	  map.put("age", "24");
	    	  map.put("city", "hangzhou");
	    	 
	    	  jedis.hmset(key, map); // 添加数据
	    	 
	    	  List<String> rsmap = jedis.hmget(key, "name", "age", "city"); // 第一个参数存入的是redis中map对象的key,后面跟的是放入map中的对象的key
	    	  System.out.println(rsmap);
	    	  System.out.println("-------------------");
	    	 for(int i = 0;i<3;i++) {
	    		 pop(); 
	    	 }
	    }*/
	    
	    public static byte[] redisKey = "key".getBytes();  
	    static{  
	        init();  
	    }  
	    @SuppressWarnings("unused")
		private static void pop() {  
	        byte[] bytes = JedisUtil.rpopByTemplate(redisKey);  
	        Message msg = (Message) SerializeUtil.unserialize(bytes);  
	        if(msg != null){  
	            System.out.println(msg.getId()+"   "+msg.getContent());  
	        }  
	    }  
	  
	    private static void init() {  
	        Message msg1 = new Message(1, "内容1");  
	        JedisUtil.lpushByTemplate(redisKey, SerializeUtil.serialize(msg1));  
	        Message msg2 = new Message(2, "内容2");  
	        JedisUtil.lpushByTemplate(redisKey, SerializeUtil.serialize(msg2));  
	        Message msg3 = new Message(3, "内容3");  
	        JedisUtil.lpushByTemplate(redisKey, SerializeUtil.serialize(msg3));  
	    }  
}
