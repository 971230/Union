package com.ztesoft.component.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class CommCache {
	private static Logger logger = Logger.getLogger(CommCache.class);
	private static CommCache instance = null;
	//序列缓存
	private Map<String, String> SEQ_CODE_CACHE = Collections.synchronizedMap(new ExtHashMap<String, String>());
	
	//静态值缓存
	private Map ATTR_VALUE_CACHE = Collections.synchronizedMap(new ExtHashMap());
	
	//模板属性缓存
	private Map TEMPLATE_ATTR_CACHE = Collections.synchronizedMap(new ExtHashMap());
	
	private Map TEMPLATE_CACHE = Collections.synchronizedMap(new ExtHashMap());
	
	//销售品申请相关数据缓存
	private Map REQUEST_TPL_ATTR_CACHE = Collections.synchronizedMap(new ExtHashMap());

	public static CommCache getInst() {
		if (instance == null) {
			synchronized (CommCache.class) {

				if (instance == null) {
					instance = new CommCache();
				}
			}
		}
		return instance;
	}

	public Map<String, String> getSeqCodeCache() {
		return SEQ_CODE_CACHE;
	}

	public Map getAttrValueCache() {
		// TEMPLATE_ATTR_VALUE_CACHE.clear();
		return ATTR_VALUE_CACHE;
	}
	
	public Map getTemplateCache() {
		return TEMPLATE_CACHE;
	}

	public Map getTemplateAttrCache() {
		// TEMPLATE_ATTR_CACHE.clear();
		return TEMPLATE_ATTR_CACHE;
	}

	public Map getRequestTplAttrCache() {
		return REQUEST_TPL_ATTR_CACHE;
	}
	
	public void clearCommCache() {
		SEQ_CODE_CACHE.clear();
		ATTR_VALUE_CACHE.clear();
		TEMPLATE_CACHE.clear();
		TEMPLATE_ATTR_CACHE.clear();
	}

	class ExtHashMap<K, V> extends HashMap<K, V> {
		@Override
		public V get(Object key) {
			V v = super.get(key);
			if(v == null) {
				 return null;
			}
			logger.info("CommCache get :"+ key);
			return v;
			/*V newV = null;
			ByteArrayOutputStream bos = null;
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
			try {
				bos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(bos);
				oos.writeObject(v);
				ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
				newV = (V) ois.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (ois != null) {
					try {
						ois.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}

				if (oos != null) {
					try {
						oos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return newV;*/
		}
	}

}
