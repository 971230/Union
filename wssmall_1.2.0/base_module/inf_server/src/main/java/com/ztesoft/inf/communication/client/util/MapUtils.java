package com.ztesoft.inf.communication.client.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapUtils {
	
	/**
	 * 转换map中的null为空字符("")
	 * @param map
	 * @return
	 */
	public static void parseNull2EmptyInMap(Map map){
		Iterator<String> keys = map.keySet().iterator();
		String keyValue = "";
		Object objValue;
		while(keys.hasNext()){
			keyValue = keys.next();
			objValue = map.get(keyValue);
			if(null == objValue){
				map.put(keyValue, "");
			}else if(objValue instanceof Map){
				Map m = (Map)objValue;
				parseNull2EmptyInMap(m);
				map.put(keyValue, m);
			}else if(objValue instanceof List){
				List listVal = (List)objValue;
				ArrayList l = new ArrayList();
				for(int i = 0; i < listVal.size(); i++){
					Map m = (Map)listVal.get(i);
					parseNull2EmptyInMap(m);
					l.add(m);
				}
				map.put(keyValue, l);
			}
		}
	}
}
