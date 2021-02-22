package com.ztesoft.ibss.common;

import java.util.*;

import org.apache.log4j.Logger;


/**
 * @author AyaKoizumi 
 * @date 101123
 * @desc 统一管理静态数据
 * */
public class StaticDataCacheManager {
	private static Logger logger = Logger.getLogger(StaticDataCacheManager.class);
	private static final String STRING_TRUE="true";
	private static final String STRING_FALSE="false";
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		logger.info(StaticDataCacheManager.getVarKeyCount(
//				"com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", 
//				"test", 
//				"3"));
//		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test1", 
//				"1", "1val");
//		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test2", 
//				"1", "1val");
//		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test3", 
//				"1", "1val");
		StaticDataCacheManager.setCustomerKeySize("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 1);
		
		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
				"1", "1val");
		StaticDataCacheManager.get("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
		"1");
		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
				"2", "2val");
		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
				"3", "3val");
		StaticDataCacheManager.get("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
		"3");
		StaticDataCacheManager.get("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
		"3");
		StaticDataCacheManager.get("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
		"3");
		StaticDataCacheManager.get("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
		"3");
		
		logger.info("\n----------------");
//		Iterator it=StaticDataCacheManager.iterator(StaticDataCacheManager.DATA);
//		while(it.hasNext()){
//			Object c=it.next();
//			logger.info(c);
//		}
		logger.info("\ncurrSize="+StaticDataCacheManager.getCurrSize());
//		StaticDataCacheManager.remove("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
//				"2");
		
		
		String val=(String)StaticDataCacheManager.get("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
		"1");
		logger.info("\n----------------");
//		it=StaticDataCacheManager.iterator(StaticDataCacheManager.DATA);
//		while(it.hasNext()){
//			Object c=it.next();
//			logger.info(c);
//		}
		
		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
				"4", "4val");
		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
				"5", "5val");
		StaticDataCacheManager.regedit("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", "test", 
				"6", "6val");
		
//		it=StaticDataCacheManager.iterator(StaticDataCacheManager.DATA);
//		while(it.hasNext()){
//			Object c=it.next();
//			logger.info(c);
//		}
		logger.info("\ncurrSize="+StaticDataCacheManager.getCurrSize());
		logger.info(StaticDataCacheManager.containsKey(
				"com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", 
				"test", 
				"2"));
		
		logger.info(StaticDataCacheManager.getVarKeyCount(
				"com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", 
				"test", 
				"3"));
		StaticDataCacheManager.resetVarKeyCount("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", 
				"test", 
				"3");
		logger.info(StaticDataCacheManager.getVarKeyCount(
				"com.ztesoft.component.common.staticdata.service.StaticDataCacheManager", 
				"test", 
				"3"));
		
		
		HashMap map=new HashMap();
		synchronized(syncObj){
			ArrayList ids=getIds("com.ztesoft.component.common.staticdata.service.StaticDataCacheManager","test");
			if(ids!=null && !ids.isEmpty()){
				for(int i=0;i<ids.size();i++){
					String id=(String)ids.get(i);
					Object key=idMap.get(id);
					Object val1=data.get(id);
					map.put(key, val1);
				}
			}
			logger.info("ccccccccc"+map) ;
		}
	}

	private static long id=0;
	private static HashMap data=new HashMap();
	private static HashMap idMap=new HashMap();
	private static HashMap pathMap=new HashMap();
	private static HashMap idTimeMap=new HashMap();
	private static HashMap varKeyGetCount=new HashMap();
	private static ArrayList pathMapKeySet=new ArrayList();
	private static HashMap statusFlag=new HashMap();//是否使用cache,如果不使用,那么get方法统一返回null
	public static final String DATA="data";
	public static final String ID_MAP="idMap";
	public static final String PATH_MAP="pathMap";
	private static long maxVarSize=100000;//可以定义的变量val总个数:一个变量如果有多个key,算多条
	private static long maxKeySize=1000;//一个变量中可以put的key个数
	private static HashMap customerKeySize=new HashMap();//自定义一个变量中可以put的key个数
	private static long currSize=0;//当前定义的变量个数
	
	private static String syncObj="syncObj";
	
	public static Long getMaxVarSize(){
		return new Long(maxVarSize);
	}
	public static void setMaxKeySize(long newSize){
		maxKeySize=newSize;
	}
	public static void setMaxVarSize(long newSize){
		maxVarSize=newSize;
	}
	public static void setCustomerKeySize(String className,String varName,long newSize){
		String cacheKey=className+varName;
		synchronized(customerKeySize){
			customerKeySize.put(cacheKey, new Long(newSize));
		}
	}
	public static long getCustomerKeySize(String className,String varName){
		String cacheKey=className+varName;
		synchronized(customerKeySize){
			Object c=customerKeySize.get(cacheKey);
			if(c==null)return maxKeySize;
			return ((Long)customerKeySize.get(cacheKey)).longValue();
		}
	}
	public static String getSyncOjb(){
		return syncObj;
	}
	public static HashMap getData(){
		return data;
	}
	public static HashMap getIdMap(){
		return idMap;
	}
	public static HashMap getIdTimeMap(){
		return idTimeMap;
	}
	public static HashMap getPathMap(){
		return pathMap;
	}
	public static HashMap getKeyGetCount(){
		return varKeyGetCount;
	}
	public static ArrayList getPathMapKeySet(){
		return pathMapKeySet;
	}
	public static long getCurrSize(){
		return currSize;
	}
	public static synchronized void setStatus(String className,String varName,boolean flag){
		synchronized(statusFlag){
			ArrayList ids=getIds(className,varName);
			if(ids!=null && !ids.isEmpty()){
				String _flag="";
				if(flag)
					_flag=STRING_TRUE;
				else
					_flag=STRING_FALSE;
				for(int i=0;i<ids.size();i++){
					String id=(String)ids.get(i);
					statusFlag.put(id, _flag);
				}
			}
		}
	}
	public static synchronized void setStatus(String className,String varName,String key,boolean flag){
		synchronized(statusFlag){
			String id=getId(className,varName,key);
			if(flag)
				statusFlag.put(id, STRING_TRUE);
			else
				statusFlag.put(id, STRING_FALSE);
		}
	}
	public static synchronized boolean getStatusFlag(String id){
		synchronized(statusFlag){
			String flag= (String)statusFlag.get(id);
			if(flag!=null && flag.equals(STRING_FALSE))
				return false;
			else
				return true;
		}
	}
	//增加静态数据：方法说明：在className类里增加varName的静态数据，类型是HashMap，key,value为HashMap的key和val
	public static void regedit(String className,String varName,String key,Object value){
		if(containsKey(className,varName,key)){
			String id=getId(className,varName,key);
			if(id==null|| id.equals(""))return;
			synchronized(syncObj){//只要是修改多个变量,就统一用同步变量
				data.remove(id);
				data.put(id, value);
				idTimeMap.remove(id);
				idTimeMap.put(id, new Long(System.currentTimeMillis()));
				return;
			}
		}

		synchronized(syncObj){
			String customerSizeKey=className+varName;
			Long _customerSizeKey=(Long)customerKeySize.get(customerSizeKey);
			if(currSize>=maxVarSize){//如果所有类变量的总数超过阀值,那么抽取内存里所有的数据,剔除最不常用的
				String removeId=getGetOutId();
				if(removeId!=null && !removeId.equals(""))
				removeById(removeId);
			}
			ArrayList ids=getIds(className,varName);
			long _keySize=maxKeySize;
			if(_customerSizeKey!=null)
				_keySize=_customerSizeKey.longValue();
			if(ids!=null && ids.size()>=_keySize){//如果单个类变量的key的数量超过阀值,那么需要在这个类变量里剔除一个最不常用的
				String removeId=getGetOutIdFromClassName(ids);
				if(removeId!=null && !removeId.equals(""))
				removeById(removeId);
			}

			//id重新编码
			String _id=String.valueOf(id);
			HashMap pathInfo=new HashMap();
			pathInfo.put("className",className);
			pathInfo.put("varName",varName);
			pathMap.put(_id, pathInfo);
			pathMapKeySet.add(_id);
			data.put(_id, value);
			idMap.put(_id, key);
			idTimeMap.put(_id, new Long(System.currentTimeMillis()));
			id++;
			currSize++;
		}
	}
	private static String getGetOutIdFromClassName(ArrayList ids){
		String currId="";
		synchronized(syncObj){
			if(ids!=null && !ids.isEmpty()){
				String currVal="";
				String varCount="";
				for(int i=0;i<ids.size();i++){
					String id=(String) ids.get(i);
					//设置了开关的变量不能被remove
					if(statusFlag.containsKey(id))continue;
					HashMap varKeyCount=(HashMap)varKeyGetCount.get(id);
					if(varKeyCount==null || varKeyCount.isEmpty()){
						varCount="1";
					}else{
						String key=(String)idMap.get(id);
						varCount=(String)varKeyCount.get(key);
					}
					if(i==0){
						currVal=varCount;
						currId=id;
						continue;
					}
					
					if(Long.parseLong(currVal)>Long.parseLong(varCount)){
						currVal=varCount;
						currId=id;
					}else if(Long.parseLong(currVal)==Long.parseLong(varCount)){
						if(Long.parseLong(currId)>Long.parseLong(id)){//先进先出
							currVal=varCount;
							currId=id;
						}
					}
				}
			}
		}
		return currId;
	}
	private static String getGetOutId(){
		String currId="";
		synchronized(syncObj){
			if(pathMapKeySet!=null && !pathMapKeySet.isEmpty()){
				String currVal="";
				String varCount="";
				for(int i=0;i<pathMapKeySet.size();i++){
					String id=(String) pathMapKeySet.get(i);
					//设置了开关的变量不能被remove
					if(statusFlag.containsKey(id))continue;
					HashMap varKeyCount=(HashMap)varKeyGetCount.get(id);
					if(varKeyCount==null || varKeyCount.isEmpty()){
						varCount="1";
					}else{
						String key=(String)idMap.get(id);
						varCount=(String)varKeyCount.get(key);
					}
					if(i==0){
						currVal=varCount;
						currId=id;
						continue;
					}
					if(Long.parseLong(currVal)>Long.parseLong(varCount)){
						currVal=varCount;
						currId=id;
					}else if(Long.parseLong(currVal)==Long.parseLong(varCount)){
						if(Long.parseLong(currId)>Long.parseLong(id)){//先进先出
							currVal=varCount;
							currId=id;
						}
					}
				}
			}
		}
		return currId;
	}
	private static void addVarKeyCount(String id,String key){
		HashMap varKeyCount=(HashMap)varKeyGetCount.get(id);
		if(varKeyCount==null){
			varKeyCount=new HashMap();
			varKeyGetCount.put(id, varKeyCount);
		}
		String varCount=(String)varKeyCount.get(key);
		if(varCount==null)varCount="0";
		long _varCount=Long.parseLong(varCount);
		if(_varCount>90000000)
			_varCount=100;
		varCount=String.valueOf(Long.parseLong(varCount)+1);
		varKeyCount.put(key, varCount);
	}
	private static String getVarKeyCount(String className,String varName,String key){
		String id=getId(className,varName,key);
		HashMap varKeyCount=(HashMap)varKeyGetCount.get(id);
		if(varKeyCount==null){
			varKeyCount=new HashMap();
			varKeyGetCount.put(id, varKeyCount);
		}
		String keyCount= (String)varKeyCount.get(key);
		if(keyCount==null || keyCount.equals(""))
			keyCount="0";
		varKeyCount.put(key, keyCount);
		return keyCount;
	}
	private static void resetVarKeyCount(String className,String varName,String key){
		String id=getId(className,varName,key);
		HashMap varKeyCount=(HashMap)varKeyGetCount.get(id);
		if(varKeyCount==null){
			varKeyCount=new HashMap();
			varKeyGetCount.put(id, varKeyCount);
		}
		String varCount="0";
		varKeyCount.put(key, varCount);
	}

	public static Object get(String className,String varName,String key){
		String id=getId(className,varName,key);
		if(id==null|| id.equals(""))return null;
		if(!getStatusFlag(id))return null;
		addVarKeyCount(id,key);//记录访问次数
		return data.get(id);
	}
	public static boolean containsKey(String className,String varName,String key){
		if(idMap==null || idMap.isEmpty())return false;
		String id=getId(className,varName,key);
		if(id==null|| id.equals(""))return false;
		return true;
	}
	public static boolean isContainVar(String className,String varName){
		ArrayList ids= getIds(className,varName);
		if(ids==null || ids.isEmpty())return false;
		return true;
	}
	public static ArrayList getIds(String className,String varName){
		if(idMap==null || idMap.isEmpty())return null;
		if(className==null || className.equals(""))return null;
		if(varName==null || varName.equals(""))return null;
		synchronized(syncObj){
			ArrayList ids=new ArrayList();
			for(int i=0;i<pathMapKeySet.size();i++){
				String id=(String) pathMapKeySet.get(i);
				HashMap pathInfo=(HashMap)pathMap.get(id);
				String _className=(String)pathInfo.get("className");
				String _varName=(String)pathInfo.get("varName");
				if(_className.equals(className) && _varName.equals(varName)){
					ids.add(id);
				}
			}
			
			return ids;
		}
	}
	public static String getId(String className,String varName,String key){
		if(idMap==null || idMap.isEmpty())return null;
		if(className==null || className.equals(""))return null;
		if(varName==null || varName.equals(""))return null;
		if(key==null || key.equals(""))return null;
		synchronized(syncObj){
			for(int i=0;i<pathMapKeySet.size();i++){
				String id=(String) pathMapKeySet.get(i);
				HashMap pathInfo=(HashMap)pathMap.get(id);//key
				String _className=(String)pathInfo.get("className");
				String _varName=(String)pathInfo.get("varName");
				if(_className.equals(className) && _varName.equals(varName)){
					String _key=(String)idMap.get(id);
					if(_key!=null && !_key.equals("") && _key.equals(key))
						return id;
				}
			}
		}
		return null;
	}
	public static HashMap getPath(String className,String varName,String key){
		if(idMap==null || idMap.isEmpty())return null;
		if(className==null || className.equals(""))return null;
		if(varName==null || varName.equals(""))return null;
		if(key==null || key.equals(""))return null;
		String id=getId(className,varName,key);
		if(id==null|| id.equals(""))return null;
		synchronized(pathMap){
			return (HashMap) pathMap.get(id);
		}
	}
	public static void clearAll(){
		synchronized(syncObj){
			pathMap.clear();
			data.clear();
			idMap.clear();
			pathMap=null;
			pathMapKeySet=null;
			data=null;
			idMap=null;
			varKeyGetCount=null;
			statusFlag.clear();
			statusFlag=null;
			currSize=0;
			idTimeMap.clear();
			idTimeMap=null;
		}
	}
	public static void clear(String className,String varName){
		ArrayList ids=getIds(className,varName);
		if(ids==null|| ids.isEmpty())return;
		synchronized(syncObj){
			for(int i=0;i<ids.size();i++){
				String id=(String)ids.get(i);
				removeById(id);
			}
		}
	}
	public static void remove(String className,String varName,String key){
		String id=getId(className,varName,key);
		removeById(id);
	}
	private static void removeById(String id){
		if(id==null|| id.equals(""))return;
		synchronized(syncObj){
			Object c=pathMap.get(id);
			c=null;
			pathMap.remove(id);
			pathMapKeySet.remove(id);
			c=data.get(id);
			c=null;
			data.remove(id);
			c=idMap.get(id);
			c=null;
			idMap.remove(id);
			c=varKeyGetCount.get(id);
			c=null;
			varKeyGetCount.remove(id);
			statusFlag.remove(id);
			idTimeMap.remove(id);
			currSize--;
		}
	}

	private static StaticDataCacheManager staticManager=new StaticDataCacheManager();
	public static StaticDataCacheManager getInstance(){
		if(staticManager==null)
			staticManager=new StaticDataCacheManager();
		return staticManager;
	}
	//虚拟的HashMap:针对某个实例化的ClassName和VarName
	public class StaticMap{
		private String CLASS_NAME="";
		private String VAR_NAME="";
		public StaticMap(String pClassName,String pVarName){
			this.CLASS_NAME=pClassName;
			this.VAR_NAME=pVarName;
		}
		public void setKeySize(int newSize){
			StaticDataCacheManager.setCustomerKeySize(CLASS_NAME, VAR_NAME, newSize);
		}
		public void add(String key){
			StaticDataCacheManager.regedit(CLASS_NAME, VAR_NAME, key, key);
		}
		public void put(String key,Object val){
			StaticDataCacheManager.regedit(CLASS_NAME, VAR_NAME, key, val);
		}
		public Object get(String key){
			return StaticDataCacheManager.get(CLASS_NAME, VAR_NAME, key);
		}
		public Object getFromCache(String key){
			return StaticDataCacheManager.get(CLASS_NAME, VAR_NAME, key);
		}
		public void putCache(String key,Object obj){
			StaticDataCacheManager.regedit(CLASS_NAME, VAR_NAME, key, obj);
		}
		public boolean containsKey(String key){
			return StaticDataCacheManager.containsKey(CLASS_NAME, VAR_NAME, key);
		}
		public boolean isEmpty(){
			return !StaticDataCacheManager.isContainVar(CLASS_NAME, VAR_NAME); 
		}
		public void remove(String key){
			StaticDataCacheManager.remove(CLASS_NAME, VAR_NAME, key);
		}
		public void clear(){
			StaticDataCacheManager.clear(CLASS_NAME, VAR_NAME);
		}
		public String getCount(String key){
			return StaticDataCacheManager.getVarKeyCount(CLASS_NAME, VAR_NAME, key);
		}
		public int size(){
			ArrayList oriSet=StaticDataCacheManager.getIds(CLASS_NAME, VAR_NAME);
			if(oriSet==null || oriSet.isEmpty()) return 0;
			return oriSet.size();
		}
		public Set getKeys(){
	    	ArrayList oriSet=StaticDataCacheManager.getIds(CLASS_NAME, VAR_NAME);
	    	HashMap cloneMap=new HashMap();
	    	for(int i=0;i<oriSet.size();i++){
	    		String id=(String) oriSet.get(i);
	    		String key=(String) StaticDataCacheManager.getIdMap().get(id);
//				Object cachekey=oriSet.get(i);
				if(key==null)continue;
				cloneMap.put(key, null);
	    	}
	    	return cloneMap.keySet();
	    }
		
		public HashMap convertToHashMap(){
			HashMap map=new HashMap();
			synchronized(syncObj){
				ArrayList ids=getIds(CLASS_NAME,VAR_NAME);
				if(ids!=null && !ids.isEmpty()){
					for(int i=0;i<ids.size();i++){
						String id=(String)ids.get(i);
						Object key=idMap.get(id);
						Object val=data.get(id);
						map.put(key, val);
					}
				}
				return map;
			}
		}
		
		public void setMap(Map vo){
			if(vo==null || vo.isEmpty())return;
			for(Iterator it=vo.entrySet().iterator();it.hasNext();){
				Map.Entry map=(Map.Entry)it.next();
				this.put((String)map.getKey(), map.getValue());
			}
		}
	}
}
