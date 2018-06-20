package com.worksyun.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/RedisDemoController")
@Api(description = "redis相关操作")
public class RedisDemoController {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings({ "unused", "rawtypes" })
	@Autowired
	private RedisTemplate redisTemplate;

	@GetMapping("/setRedis")
	@ApiOperation(value = "设置缓存", notes = "设置redis缓存")
	public void setRedis(@RequestParam(value = "redisset", required = true) String redisset,
			@RequestParam(value = "redisget", required = true) String redisget) {
		stringRedisTemplate.opsForValue().set(redisset, redisget);
	}

	@GetMapping("/getRedis")
	@ApiOperation(value = "获取缓存信息", notes = "根据K，V获取缓存信息")
	public String getRedis(@RequestParam(value = "redisset", required = true) String redisset) {
		String s = stringRedisTemplate.opsForValue().get(redisset);
		return s;
	}
}
