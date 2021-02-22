package com.rop.core.memcached.cache;

import com.rop.core.memcached.util.ServiceException;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.io.Serializable;
import java.util.concurrent.TimeoutException;


/**
 * cache
 *
 * @author easonwu 2012-06-14
 */
public class XmCache implements INetCache {

    //@Resource
    private XMemcachedClient memcachedClient;

    @Override
    public IBean getBean(String key) {
        try {
            return this.memcachedClient.get(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void setBean(String key, int delta, IBean value) {
        try {
            this.memcachedClient.set(key, delta, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        try {
            this.memcachedClient.delete(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        } catch (InterruptedException e) {
            throw new ServiceException(e);
        } catch (MemcachedException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteWithNoReply(String key) {
        try {
            this.memcachedClient.deleteWithNoReply(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        } catch (MemcachedException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public Serializable getObj(String key) {
        try {
            return this.memcachedClient.get(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setObj(String key, int cacheTime, Serializable value) {
        try {
            this.memcachedClient.set(key, cacheTime, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(String key, int time, Serializable value) {
        try {
            this.memcachedClient.replace(key, time, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flush() {
        try {
            this.memcachedClient.flushAll();
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MemcachedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setMemcachedClient(XMemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}
