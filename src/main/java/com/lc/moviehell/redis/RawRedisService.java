package com.lc.moviehell.redis;

import com.lc.moviehell.bean.SerializableBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Created by lc on 15/8/20.
 */
public class RawRedisService implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(
        RawRedisService.class);

    private JedisPool jedisPool;

    private static final JedisPoolConfig config = new JedisPoolConfig();

    private static final String ip = "10.240.129.196";

    private static final int port = 6379;

    private static final int timeout = 2000;

    public RawRedisService() {
        logger.info("init redis connection {}:{}", ip, port);
        config.setMaxIdle(100);
        config.setMaxActive(100);
        config.setMaxWait(3000);
        //jedis检测连接时会发ping，但nutcracker不支持，会导致该连接立即被关掉，因此把检测禁用掉
        //config.setTestWhileIdle(false);
        jedisPool = new JedisPool(config, ip, port, timeout);
    }

    @Override public boolean put(String k, SerializableBean v) {
        Jedis jedis = getConnection();
        try {
            String ret = jedis.set(k, v.serialize());
            return "OK".equals(ret);
        } catch (Exception e) {
            logger.error("redis error", e);
            onException(jedis);
        } finally {
            onFinally(jedis);
        }
        return false;
    }

    @Override public boolean put(String k, String v) {
        Jedis jedis = getConnection();
        try {
            String ret = jedis.set(k, v);
            return "OK".equals(ret);
        } catch (Exception e) {
            logger.error("redis error", e);
            onException(jedis);
        } finally {
            onFinally(jedis);
        }
        return false;
    }

    @Override public <T> T get(String k, Class<T> clazz) {
        Jedis jedis = getConnection();
        try {
            String ret = jedis.get(k);
            return SerializableBean.unserialize(ret, clazz);
        } catch (Exception e) {
            logger.error("redis error", e);
            onException(jedis);
        } finally {
            onFinally(jedis);
        }
        return null;
    }

    @Override public String get(String k) {
        Jedis jedis = getConnection();
        try {
            return jedis.get(k);
        } catch (Exception e) {
            logger.error("redis error", e);
            onException(jedis);
        } finally {
            onFinally(jedis);
        }
        return null;
    }

    @Override public boolean hasKey(String k) {
        Jedis jedis = getConnection();
        try {
            return jedis.exists(k);
        } catch (Exception e) {
            logger.error("redis error", e);
            onException(jedis);
        } finally {
            onFinally(jedis);
        }
        return false;
    }

    private Jedis getConnection() {
        try {
            return jedisPool.getResource();
        } catch (JedisConnectionException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void onFinally(Jedis jedis) {
        if (jedis != null) {
            // jedis is multi ,need discard all opes
            if (jedis.getClient().isInMulti()) {
                jedis.getClient().discard();
                jedis.disconnect();
            }

            if (jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public void onException(Jedis jedis) {
        if (jedis != null) {
            // jedis is multi ,need discard all opes
            if (jedis.getClient().isInMulti()) {
                jedis.getClient().discard();
                jedis.disconnect();
            }

            if (jedisPool != null) {
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }
}
