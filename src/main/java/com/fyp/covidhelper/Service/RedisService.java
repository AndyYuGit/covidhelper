package com.fyp.covidhelper.Service;

import com.fyp.covidhelper.Annotation.Logging;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;

@Component
public class RedisService {
    @Autowired
    RedisClient redisClient;

    GenericObjectPool<StatefulRedisConnection<String, String>> redisConnectionPool;

    @PostConstruct
    public void init() {
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> redisConnectionPoolConfig = new GenericObjectPoolConfig<>();
        redisConnectionPoolConfig.setMaxTotal(25);
        redisConnectionPoolConfig.setMaxIdle(10);
        redisConnectionPoolConfig.setTestOnReturn(true);
        redisConnectionPoolConfig.setTestWhileIdle(true);
        this.redisConnectionPool = ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), redisConnectionPoolConfig);
    }

    @PreDestroy
    public void shutdown() {
        this.redisConnectionPool.close();
        this.redisClient.shutdown();
    }

    @Logging("save data to redis")
    public <T> T executeSync(SyncCommandCallback<T> callback) {
        try (StatefulRedisConnection<String, String> redisConnection = redisConnectionPool.borrowObject()) {
            redisConnection.setAutoFlushCommands(true);
            RedisCommands<String, String> redisCommands = redisConnection.sync();
            return callback.doInConnection(redisCommands);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hset(String key, String field, String value) {
        return executeSync(commands -> commands.hset(key, field, value));
    }

    public String hget(String key, String field) {
        return executeSync(commands -> commands.hget(key, field));
    }

    public Map<String, String> hgetall(String key) {
        return executeSync(commands -> commands.hgetall(key));
    }
}