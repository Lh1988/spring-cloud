package com.shumu.common.redis.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.shumu.common.redis.service.IRedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
/**
* @Description: 
* @Author: Li
* @Date: 2022-02-07
* @LastEditTime: 2022-02-07
* @LastEditors: Li
*/
@Service
public class RedisServiceImpl implements IRedisService {
    @Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public boolean setExpire(String key, long time) {
        try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(String key) {
        try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public void delete(String... key) {
        if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
                Collection<String> keys = new ArrayList<String>();
                for (int i = 0; i < key.length; i++) {
                    keys.add(key[i]);
                }
				redisTemplate.delete(keys);
			}
		}
    }

    @Override
    public String getString(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean setString(String key, String value) {
        try {
			stringRedisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean setString(String key, String value, long time) {
        try {
            if (time > 0) {
			   stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                setString(key, value);
            }
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public Object getObject(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean setObject(String key, Object value) {
        try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean setObject(String key, Object value, long time) {
        try {
            if (time > 0) {
			   redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                setObject(key, value);
            }
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public double increment(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Object getHashItem(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    @Override
    public boolean setHashItem(String key, String item, Object value) {
        try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean setHashItem(String key, String item, Object value, long time) {
        try {
			redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				setExpire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public void removeHashItem(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);  
    }

    @Override
    public boolean hashHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    @Override
    public Map<Object, Object> getHashMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public boolean setHashMap(String key, Map<String, Object> map) {
        try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean setHashMap(String key, Map<String, Object> map, long time) {
        try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				setExpire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public List<Object> getList(String key, long start, long end) {
        try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @Override
    public long getListSize(String key) {
        try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }

    @Override
    public Object getListItem(String key, long index) {
        try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @Override
    public boolean setList(String key, List<Object> value) {
        try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean setList(String key, List<Object> value, long time) {
        try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) {
				setExpire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean addList(String key, Object value) {
        try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean addList(String key, Object value, long time) {
        try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) {
				setExpire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean updateListItem(String key, long index, Object value) {
        try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public long removeListItem(String key, long count, Object value) {
        try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }

    @Override
    public Set<Object> getSet(String key) {
        try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @Override
    public long setSet(String key, Object... values) {
        try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }

    @Override
    public long setSet(String key, long time, Object... values) {
        try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0) {
				setExpire(key, time);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }
    
}
