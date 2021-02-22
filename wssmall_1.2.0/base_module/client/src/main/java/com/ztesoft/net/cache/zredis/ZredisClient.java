package com.ztesoft.net.cache.zredis;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import com.ztesoft.net.cache.common.CacheException;
import com.ztesoft.zcache.client.IZcacheClient;
import com.ztesoft.zcache.client.ZcacheFactory;
import com.ztesoft.zcache.client.etc.ZcacheConfig;
import com.ztesoft.zcache.client.result.ResultCode;
/**
 * @Description
 * @author  zhangJun
 * @date    2016年1月20日
 * @version 1.0
 */
public class ZredisClient  {
	private static  Logger log = Logger.getLogger(ZredisClient.class);
	private static  IZcacheClient zcacheCli =null;
	private static  ZredisClient inst=new ZredisClient();
	static{
		log.info("开始初始化redis 客户端zcacheCli");
		init() ;
	}
	
	public static final ZredisClient getInstance(){
		return inst;
	}
	
	private ZredisClient() {
	}
	/**
	 * 初始化操作客户端
	 */
	private static void init() {
		try {
			 //zcacheCli = ZcacheFactory.createCache("zcache");
			 
			 ZcacheConfig config = new ZcacheConfig();
			 
			 config.setNodes(ZredisCacheValues.SERVER_LIST);
			 config.setClusterName(ZredisCacheValues.CLUSTER_NAME);
			 config.setClientName(ZredisCacheValues.CLIENT_NAME);
			 config.setMaxTotal(ZredisCacheValues.MAX_TOTAL);
			 config.setMaxIdle(ZredisCacheValues.MAX_IDLE);
			 config.setMinIdle(ZredisCacheValues.MIN_IDLE);
			 config.setConnTimeout(ZredisCacheValues.CONN_TIMEOUT);
			 config.setSoTimeout(ZredisCacheValues.SO_TIMEOUT);
			 config.setMaxRedirections(ZredisCacheValues.MAX_REDIRECTIONS);
			 config.setMaxScanCount(ZredisCacheValues.MAX_SCANCOUNT);
			 config.setMaxWaitMillis(ZredisCacheValues.MAX_WAITMILLIS);
			 config.setTestOnBorrow(ZredisCacheValues.TEST_ON_BORROW);
			 config.setTestOnReturn(ZredisCacheValues.TEST_ON_RETURN);
			
			 zcacheCli = ZcacheFactory.createCache(config);
		} catch (Exception ex ) {
			log.info("开始初始化redis 客户端zcacheCli 时出现异常");
		    ex.printStackTrace();
		}
	}

    
    
	/**
	 * 根据key获取value
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public final Serializable r_get(String key) throws Exception {
		Object obj = null;
		if (zcacheCli == null) {
			throw new Exception("zredis 客户端zcacheCli 为空");
		}
		JedisCluster jc = (JedisCluster)zcacheCli.getOrigin();
		//根据zcacheCli.get(key);判断，JedisCluster.get(key) 不抛异常就成功
		byte[] value=jc.get(redis.clients.util.SafeEncoder.encode(key));
		if (value==null){
			return null;
		}else{
			obj = byteToObject(value);
			return (Serializable)obj;
		}
	}
	/**
	 * Description  设置键值对 (可设置键的生存时限)
	 * @param key         要操作的键名
	 * @param value       该键要设置的值
	 * @param expireTime  生存时限值,单位:ms.(-1表示无生存时限限制)
	 * @return 不抛异常就成功
	 */
	public final void r_set(String key, Serializable value,long expireTime) {
			JedisCluster jc = (JedisCluster)zcacheCli.getOrigin();
			//根据zcacheCli.set(key,value);判断JedisCluster.set不抛异常就成功。
			if(expireTime<0){
				jc.set(redis.clients.util.SafeEncoder.encode(key), this.objectToByte(value));
			}else{
				jc.psetex(redis.clients.util.SafeEncoder.encode(key),expireTime, this.objectToByte(value));//
			}
	}
	
	public final void r_delete(String key) {
		ResultCode rc = zcacheCli.delete(key);
		// 删除成功返回SUCCESS；否则返回错误信息
		if (!ResultCode.SUCCESS.equals(rc)) {
			throw new CacheException(rc.getMessage());
		}
	}
	
	private byte[] objectToByte(java.lang.Object obj) {
		byte[] bytes =null;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (bytes);
	}

	private static Object byteToObject(byte[] bytes){
        java.lang.Object obj=null;
        try {
	        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
	        ObjectInputStream oi = new ObjectInputStream(bi);
	        obj = oi.readObject();
	        bi.close();
	        oi.close();
        }catch(Exception e) {
           e.printStackTrace();
        }
        return obj;
    }
}
