package com.pubutech.blog.framework.redis;

import org.crazycake.shiro.RedisManager;

public class CustomRedisManager extends RedisManager  {
    private String host = "127.0.0.1";
    private int port = 6379;
    private int expire = 2592000;
    private int timeout = 2000;
    private int database = 0;
    private String password = null;

    public CustomRedisManager() {
    }

    public byte[] set(byte[] key, byte[] value){
        return set(key, value,getExpire());
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    public int getExpire() {
        return this.expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    @Override
    public int getTimeout() {
        return this.timeout;
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        if (null == database) {
            return;
        }
        this.database = database;
    }
}
