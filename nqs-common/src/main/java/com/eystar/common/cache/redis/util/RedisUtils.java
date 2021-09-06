package com.eystar.common.cache.redis.util;


import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;


@Component
public class RedisUtils {

	private JedisPool pool;

	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	/**
	 * 持久化数据
	 */
	public void psetex(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			// 如果key是对URL授权的角色编码，则不过期
			if (key.contains("MSCODE:URL:ROLECODE")) {
				jedis.persist(key);
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void pexpire(String key, long value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
				jedis.pexpire(key,value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}




	/**
	 * 根据键删除数据
	 *
	 * @param key
	 */
	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 根据键获取值
	 *
	 * @param key 键
	 * @return
	 */
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}


	/**
	 * 根据键获取值
	 *
	 * @param key 键
	 * @return
	 */
	public String hget(String key,String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hget(key,field);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long hset(String key,String field,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hset(key,field,value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long pttl(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.pttl(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}


	public String set(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.set(key,value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long lpush(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpush(key,value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}




	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hkeys(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public String setex(String key,int field,String value) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.setex(key,field,value);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

	public Map<String, String> hgetAll(String key) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hgetAll(key);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

public long hdel(String key,String field) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hdel(key,field);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}



}