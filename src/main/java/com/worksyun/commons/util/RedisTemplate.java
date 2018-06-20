package com.worksyun.commons.util;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTemplate {
	  private JedisPool jedisPool;
	 
	  public RedisTemplate(JedisPool jedisPool) {
	    this.jedisPool = jedisPool;
	  }
	  
	  public <T> T execute(RedisCallback<T> callback) {
		    Jedis jedis = jedisPool.getResource();
		    try {
		      return callback.handle(jedis);
		    }
		    catch (Exception e) {
		      // throw your exception
		      throw e;
		    }
		    finally {
		      returnResource(jedis);
		    }
		  }
	  
	  private void returnResource(Jedis jedis) {
		    if (jedis != null) {
		      jedis.close();
		    }
		  }
}


interface RedisCallback<T> {
	  public T handle(Jedis jedis);
	}
