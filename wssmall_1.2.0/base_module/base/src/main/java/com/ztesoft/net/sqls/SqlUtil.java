package com.ztesoft.net.sqls;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.framework.util.ReflectionUtil;


public class SqlUtil {
	
	private static final int PRIVATE = 2 ;
	
	public static void initSqls(Class clazz , Object instance , Map sqls){
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field f : fields) {
				f.setAccessible(true) ;
				if(null == f.get(instance) || "".equals( ((String) f.get(instance)).trim() ))
					throw new RuntimeException("类【"+clazz.getName()+"】的属性【"+f.getName()+"】值不能为空！" +
							" 或许【"+f.getName()+"】对应的SQL使用到的对象与当前对象有循环调用，请把使用到的对象方法抽取到DatabaseFunction类中，避免循环引用!") ;
				
				sqls.put(f.getName(), f.get(instance));
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e) ;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e) ;
		}
	}
	
	/**
	 * 返回oracle查询SQL的In语句
	 * @param col_name 列名
	 * @param list 列值枚举值
	 * @param isNum 列是否为数字
	 * @param isAnd 连接是否为and
	 * @return
	 */
	public static String getSqlInStr(String col_name,List<String> list,
			boolean isNum,boolean isAnd){
	    StringBuilder sql = new StringBuilder();
	    
	    String condition = " and ";
		if(!isAnd)
			condition = " or ";
	    
	    if(list!=null && list.size()>0){
	        sql.append(condition).append(" ( ");
            
            for(int i=1;i<=list.size();i++){
                if(i == 1){
                    sql.append(" "+col_name+" in (");
                    
                    if(isNum)
                        sql.append(list.get(i-1));
                    else
                        sql.append(" '").append(list.get(i-1)).append("' ");
                    
                    if(i==list.size()){
                        sql.append(" ) "); 
                    }
                }else if((i-1)%1000 == 0){
                    sql.append(" ) or "+col_name+" in (");
                    
                    if(isNum)
                        sql.append(list.get(i-1));
                    else
                        sql.append(" '").append(list.get(i-1)).append("' ");
                    
                    if(i==list.size()){
                        sql.append(" ) "); 
                    }
                }else{
                    if(isNum)
                        sql.append(", ").append(list.get(i-1)).append(" ");
                    else
                        sql.append(", '").append(list.get(i-1)).append("' ");
                    
                    if(i==list.size()){
                        sql.append(" ) "); 
                    }
                }
            }
            
            sql.append(" ) "); 
	    }else{
	        //列表为空，返回空条件
	        sql.append(" and 1=2 ");
	    }
	    
	    return sql.toString();
	}
	
	/**
	 * 拼装查询SQL
	 * @param tableOrSql	查询的表或SQL
	 * @param pojo	参数POJO对象
	 * @param sqlBuilds	特殊处理字段的SQL构造器， 
	 * 比如一些字段作为查询条件时需要拼装成in,like语句的，需要传入相应的构造器
	 * @return  加上查询条件的查询SQL
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getQuerySqlByPojo(String tableOrSql,Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		StringBuilder builder = new StringBuilder();
		
		if(StringUtils.isBlank(tableOrSql))
			throw new Exception("传入表名为空！");
		
		if(pojo == null)
			throw new Exception("传入pojo为空！");
		
		Set<String> doneCol = new HashSet<String>();
		
		Map fieldMap = ReflectionUtil.getPoJoFieldMap(pojo);
		
		builder.append("select * from (").append(tableOrSql).append(") table1 where 1=1 ");
		
		//SQL构造器拼装SQL
		if(sqlBuilds!=null && sqlBuilds.size()>0){
			for(int i=0;i<sqlBuilds.size();i++){
				String col_name = sqlBuilds.get(i).getCol_name();
				
				builder.append(sqlBuilds.get(i).getSql());
				
				doneCol.add(col_name);
			}
		}
		
		//普通的字段构造SQL
		for(Iterator it = fieldMap.entrySet().iterator();it.hasNext();){
			//循环POJO字段
			Entry entry = (Entry)it.next();
			//列名
			String key = String.valueOf(entry.getKey());
			//值
			Object value = entry.getValue();
			
			if(doneCol.contains(key)){
				//如果已经由构造器创建SQL了，跳过
				continue;
			}else if(value == null){
				//如果值为null，跳过
				continue;
			}else if (value instanceof String) {
				//如果值为字符串
				String str = String.valueOf(value);
				
				//字符串为空继续
				if(StringUtils.isBlank(str))
					continue;

				//字符串加上单引号
				builder.append(" and ").append(key).append("='").append(value).append("' ");
			}else if(value instanceof Integer){
				Integer v = (Integer)value;
				
				if(v != 0)
					builder.append(" and ").append(key).append("=").append(v).append(" ");
			}else if(value instanceof Long){
				Long v = (Long)value;
				
				if(v != 0)
					builder.append(" and ").append(key).append("=").append(v).append(" ");
			}else if(value instanceof Float){
				Float v = (Float)value;
				
				if(v != 0)
					builder.append(" and ").append(key).append("=").append(v).append(" ");
			}else if(value instanceof Double){
				Double v = (Double)value;
				
				if(v != 0)
					builder.append(" and ").append(key).append("=").append(v).append(" ");
			}else{
				//其它类型，直接使用"="
				builder.append(" and ").append(key).append("=").append(value).append(" ");
			}
		}
		
		return builder.toString();
	}

	/**
	 * 获取数据保存操作的SQL和参数字段数组
	 * @param table 表名，必传
	 * @param operate	操作，支持insert,update,delete，必传
	 * @param pojo	数据对象，必传
	 * @param conditionCol	条件列名，update,delete时必传
	 * @param builder	SQL的StringBuilder，必传
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String[] getSaveSqlAndColArr(String table,String operate,Object pojo,
			String[] conditionCol,StringBuilder builder) throws Exception{
		
		Map objMap = ReflectionUtil.getPoJoFieldMap(pojo);
		Set keySet = objMap.keySet();
		
		//初始化参数字段数组为对象中的所有字段
		String[] colArr = new String[keySet.size()];
		Iterator it = keySet.iterator();
		
		for(int i = 0;it.hasNext();){
			colArr[i] = String.valueOf(it.next());
			i++;
		}
		
		if("insert".equals(operate)){
			//插入操作
			builder.append("insert into " + table + " ");
			builder.append("(");
			
			//拼装字段
			for(int i=0;i<colArr.length;i++){
				if(i != 0)
					builder.append(",");
				
				builder.append(colArr[i]);
			}
			
			builder.append(")");
			builder.append(" values ");
			builder.append("(");
			
			//拼装参数问号
			for(int i=0;i<colArr.length;i++){
				if(i != 0)
					builder.append(",");
				
				builder.append("?");
			}
			
			builder.append(")");
		}else if("update".equals(operate)){
			//更新操作
			if(conditionCol == null || conditionCol.length==0)
				throw new Exception("更新操作未传入条件列名");
			
			builder.append("update " + table + " set ");
			
			//拼装UPDATE set语句
			for(int i=0;i<colArr.length;i++){
				if(i != 0){
					builder.append(",");
				}
				
				builder.append(colArr[i]+"=?");
			}
			
			builder.append(" where 1=1 ");
			
			for(int i=0;i<conditionCol.length;i++){
				builder.append(" and ").append(conditionCol[i]+"=? ");
			}
			
			int newlength = colArr.length + conditionCol.length;
			
			String[] tempkeyArr = new String[newlength];
			
			for(int i=0;i<colArr.length;i++){
				tempkeyArr[i] = colArr[i];
			}
			
			for(int i=0;i<conditionCol.length;i++){
				tempkeyArr[colArr.length + i] = conditionCol[i];
			}
			
			colArr = tempkeyArr;
		}else if("delete".equals(operate)){
			//删除
			if(conditionCol == null || conditionCol.length==0)
				throw new Exception("删除操作未传入条件列名");
			
			builder.append(" delete from "+table);
			//现在只支持根据主键删除
			builder.append(" where 1=1 ");
			
			for(int i=0;i<conditionCol.length;i++){
				builder.append(" and ").append(conditionCol[i]+"=? ");
			}
			
			colArr = conditionCol;
		}else{
			throw new Exception("不支持的操作");
		}
		
		return colArr;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] getSaveSqlAndColArrBatch(String table,String operate,List pojoList,
			String[] conditionCol,StringBuilder builder) throws Exception{
		Set keySet = new HashSet();
		
		for(Object pojo : pojoList){
			Map objMap = ReflectionUtil.getPoJoFieldMap(pojo);
			keySet.addAll(objMap.keySet());
		}
		
		//初始化参数字段数组为对象中的所有字段
		String[] colArr = new String[keySet.size()];
		Iterator it = keySet.iterator();
		
		for(int i = 0;it.hasNext();){
			colArr[i] = String.valueOf(it.next());
			i++;
		}
		
		if("insert".equals(operate)){
			//插入操作
			builder.append("insert into " + table + " ");
			builder.append("(");
			
			//拼装字段
			for(int i=0;i<colArr.length;i++){
				if(i != 0)
					builder.append(",");
				
				builder.append(colArr[i]);
			}
			
			builder.append(")");
			builder.append(" values ");
			builder.append("(");
			
			//拼装参数问号
			for(int i=0;i<colArr.length;i++){
				if(i != 0)
					builder.append(",");
				
				builder.append("?");
			}
			
			builder.append(")");
		}else if("update".equals(operate)){
			//更新操作
			if(conditionCol == null || conditionCol.length==0)
				throw new Exception("更新操作未传入条件列名");
			
			builder.append("update " + table + " set ");
			
			//拼装UPDATE set语句
			for(int i=0;i<colArr.length;i++){
				if(i != 0){
					builder.append(",");
				}
				
				builder.append(colArr[i]+"=?");
			}
			
			builder.append(" where 1=1 ");
			
			for(int i=0;i<conditionCol.length;i++){
				builder.append(" and ").append(conditionCol[i]+"=? ");
			}
			
			int newlength = colArr.length + conditionCol.length;
			
			String[] tempkeyArr = new String[newlength];
			
			for(int i=0;i<colArr.length;i++){
				tempkeyArr[i] = colArr[i];
			}
			
			for(int i=0;i<conditionCol.length;i++){
				tempkeyArr[colArr.length + i] = conditionCol[i];
			}
			
			colArr = tempkeyArr;
		}else if("delete".equals(operate)){
			//删除
			if(conditionCol == null || conditionCol.length==0)
				throw new Exception("删除操作未传入条件列名");
			
			builder.append(" delete from "+table);
			//现在只支持根据主键删除
			builder.append(" where 1=1 ");
			
			for(int i=0;i<conditionCol.length;i++){
				builder.append(" and ").append(conditionCol[i]+"=? ");
			}
			
			colArr = conditionCol;
		}else{
			throw new Exception("不支持的操作");
		}
		
		return colArr;
	}
	
	/**
	 * 获取保存数据的参数数组
	 * @param operate 操作，支持insert,update,delete
	 * @param colArr	处理的字段数组
	 * @param pojo	数据pojo
	 * @param conditionCol	条件列名，update,delete时必传
	 * @return	
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] getSaveArgs(String operate,String[] colArr,Object pojo,String[] conditionCol) throws Exception{
		Object[] args = new Object[colArr.length];
		Map oMap = ReflectionUtil.getPoJoFieldMap(pojo);
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		
		boolean hasCondition = false;
		
		Set<String> conidtionColSet = null;
		if(("update".equals(operate) || "delete".equals(operate))){
			if(conditionCol==null || conditionCol.length==0)
				throw new Exception("更新，删除操作必须传入条件列名");
			
			conidtionColSet = new HashSet<String>(Arrays.asList(conditionCol));
		}
		
		for(int j=0;j<colArr.length;j++){
			String col_name = colArr[j];
			Object obj = oMap.get(col_name);
			
			if(("update".equals(operate) || "delete".equals(operate)) 
					&& conidtionColSet.contains(col_name)
					&& obj!=null){
				hasCondition = true;
			}
			
			if(obj instanceof String){
				Date d = null;
				try{
					d = format.parse(String.valueOf(obj));
				}catch(Exception e){
					
				}
				
				if(d!=null){
					args[j] = d;
				}else{
					args[j] = obj;
				}
			}else{
				args[j] = obj;
			}
		}
		
		if((("update".equals(operate) || "delete".equals(operate))) && 
				!hasCondition)
			throw new Exception("更新或删除操作的条件列参数不能全为空");
		
		return args;
	}
	
	/**
	 * 获取批量保存数据的处理参数list
	 * @param pojoList 数据列表
	 * @param colArr	处理的字段数组
	 * @param operate	操作，支持insert,update,delete
	 * @param conditionCol	条件列名，update,delete时必传
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object[]> getBatchArgsArr(List pojoList,String[] colArr,String operate,String[] conditionCol) throws Exception{
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		
		for(int i=0;i<pojoList.size();i++){
			
			Object[] args = getSaveArgs(operate, colArr, pojoList.get(i), conditionCol);;
			
			batchArgs.add(args);
		}
		
		return batchArgs;
	}
	
	public static String getStrValue(Object obj) throws Exception{
		if(obj == null)
			return "";
		else
			return String.valueOf(obj);
		
	}
}
