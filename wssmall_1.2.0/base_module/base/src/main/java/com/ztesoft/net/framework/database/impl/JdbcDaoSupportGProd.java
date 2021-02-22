package com.ztesoft.net.framework.database.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

import params.ZteRequest;
import params.ZteResponse;
import weblogic.jdbc.vendor.oracle.OracleThinClob;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.blog.DBProcesser;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.database.DBRuntimeException;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.DStoreInst;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SqlUtil;

/**
 * jdbc数据库操作支撑
 * 
 * @author kingapex 2010-1-6下午01:54:18
 * @param <T>
 */
public class JdbcDaoSupportGProd<T> implements IDaoSupport<T> {

	private JdbcTemplate jdbcTemplateGProd;
	private SimpleJdbcTemplate simpleJdbcTemplateGProd;
	protected final Logger logger = Logger.getLogger(getClass());
	private static List<String> keywords = new ArrayList<String>();
	static {
		keywords.add("COMMENT");
	}

	@Override
	public Connection getConnection(){
		Connection conn = DataSourceUtils.getConnection(this.jdbcTemplateGProd.getDataSource());
		return conn;
	}
	@Override
	public void closeConnection(Connection conn){
		try {
			DataSourceUtils.doReleaseConnection(conn, this.jdbcTemplateGProd.getDataSource());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void execute(String sql, Object... args) {
		try {
			String sqlReg = "(?i)insert.*";
			if(sql.matches(sqlReg)){
				if(sql.indexOf("source_from") == -1){
					sql = sql.replaceFirst("\\)", ", source_from)");
					int index = sql.lastIndexOf(")");
                    if(index!=-1){
                        sql = sql.substring(0, index) + ",'" + ManagerUtils.getSourceFrom() + "')";
                    }
				}
			}
			this.simpleJdbcTemplateGProd.update(sql, args);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}
	
	/**
	 * 
	 * @param batchArgs sql参数
	 */
	@Override
	public void batchExecute(String sql, List<Object[]> batchArgs){
		try {
			if(null != batchArgs && !batchArgs.isEmpty()){
				
				this.simpleJdbcTemplateGProd.batchUpdate(sql, batchArgs);
			}else {
				
				Connection conn = DataSourceUtils.getConnection(this.jdbcTemplateGProd.getDataSource());
				PreparedStatement pst = conn.prepareStatement(sql);
				try {					
					pst.addBatch();
					pst.executeBatch();
				} catch (Exception f) {
					f.printStackTrace();
					throw new DBRuntimeException(f, sql);
				}finally{
					
					if (pst != null) {
						pst.close();
					}
					if (conn != null) {
						DataSourceUtils.doReleaseConnection(conn, this.jdbcTemplateGProd.getDataSource());
					}
				}
			}
			
		} catch (Exception e) {

			throw new DBRuntimeException(e, sql);
		}
	}
	
	/**
	 * 
	 * @param batchArgs sql参数
	 */
	@Override
	public void batchExecuteByListMap(String sql, List<Map> batchArgs){
		try {
			Map[] pm = new Map[batchArgs.size()] ;
			int i = 0 ; 
			for(Map m : batchArgs){
				pm[i++] = m ;
			}
			SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(pm);				
			this.simpleJdbcTemplateGProd.batchUpdate(sql, batch);
		} catch (Exception e) {

			throw new DBRuntimeException(e, sql);
		}
	}
	@Override
	public String getSequences(String seq_name) {
		try {
			String seq = "-1";
			try{
				seq = getSequenceLen(seq_name, "1", 18);
				return seq;
			}catch(Exception e){
				e.printStackTrace();
				return seq;
			}			
						
		} catch (DataAccessException e) {
			e.printStackTrace();
			return "-1";
		}
		
	}
	
	@Override
	public String getSequences(String seq_name, String strSeqType, int len) {
		
		try {
			String seq = "-1";
			try{
				seq = getSequenceLen(seq_name, strSeqType, len);
				return seq;
			}catch(Exception e){
				e.printStackTrace();
				return seq;
			}			
						
		} catch (DataAccessException e) {
			e.printStackTrace();
			return "-1";
		}
	}

	@Override
	public String getSeqByTableName(String tableName) {
		String sql = "select seq_name from sequence_manager where table_name=?";
		String seq_name = null;
		try {
			seq_name = this.simpleJdbcTemplateGProd.queryForObject(sql,
					String.class, tableName);
		} catch (DataAccessException e) {
			seq_name = "";
		} catch (Exception e) {
		}
		if (seq_name == null || "".equals(seq_name)) {
			seq_name = "s_" + tableName;
		}

		String sequences = this.getSequences(seq_name);
		return sequences;
	}

	@Override
	public String getLastId(String table) {
		if (EopSetting.DBTYPE.equals("1")) {
			return this.jdbcTemplateGProd.queryForInt("SELECT last_insert_id() as id") + "";
		} else if (EopSetting.DBTYPE.equals("2")) {
			int result = 0;
			result = this.jdbcTemplateGProd.queryForInt("SELECT s_" + table
					+ ".currval as id from DUAL");
			return result + "";
		} else if (EopSetting.DBTYPE.equals("3")) {
			int result = 0;
			result = this.jdbcTemplateGProd.queryForInt("select @@identity");
			return result + "";
		}
		throw new RuntimeException("未知的数据库类型");
	}
	/**
	 * 插入方法，兼容Oracle Clob Blob字段及Oracle列关键字
	 * @author gong.zhiwei
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void insert(String table, Map<String, Object> fields, Object... args) { //
		String source_from = ManagerUtils.getSourceFrom();
		//oracle数据插入
				if (fields.isEmpty() || fields.size() <= 0) {
					return;
				}
				
				refreshWidgetCache(table);
				
				String sql = "";
				
				Assert.hasText(table, "表名不能为空");
				Assert.notEmpty(fields, "字段不能为空");
			
				table = quoteCol(table);
				//设置Oracle主键
				String key_col_name = setPKValue(table, fields);
				setColumn(fields);//设置Oracle列关键字
				setFieldsValue(fields, key_col_name, args);
				List<Map<String,Object>> clob_names = new ArrayList<Map<String,Object>>();
				StoreProcesser factory = new StoreProcesser();
				clob_names = factory.getTabClobName(table.toUpperCase());
				try {
						Assert.hasText(table, "表名不能为空");
						Assert.notEmpty(fields, "字段不能为空");
						table = quoteCol(table);

						// 字段值
						//Map<String, Object> newFields = copyMapFields(fields);
						Object[] cols = fields.keySet().toArray();
						int count = 0 ;
						boolean existFlag = false;	//判断是否存在source_from
						for (int i = 0; i < cols.length; i++) {
							if(!Consts.SYSDATE.equals(fields.get(cols[i])) && !Consts.NOW.equals(fields.get(cols[i]))
									&& !isExistsClob(clob_names, (String)cols[i]))
								count++;
							if(Consts.SOURCE_FROM.equals(cols[i].toString().toLowerCase()))
								existFlag = true;
						}
						if(!existFlag){
							count++; 
							existFlag = false;
						}
						Object[] values = new Object[count];
						Map<String,String> sysDateMap = new HashMap<String,String>();
						Map<String,String> clobMap = new HashMap<String,String>();
						int j=0;
						for (int i = 0; i < cols.length; i++) {
							Object value =fields.get(cols[i]);
							if(value instanceof String){
								String p_value =(String)value;
								
								if(!"es_attr_inst".equalsIgnoreCase(table)&&!StringUtil.isEmpty(p_value) && !"es_outer_attr_inst".equalsIgnoreCase(table) 
										&& !("ES_RULE_CONFIG_CONSTS_AUDIT".equals(table) && ("const_value".equals(cols[i]) || "const_name".equals(cols[i])))
										&& !("es_rule_cond_audit".equals(table) && ("z_value".equals(cols[i]) || "z_cvalue".equals(cols[i])))
										&& !StringUtil.isEmpty(p_value) && ManagerUtils.timeMatch(p_value))
								{
									if(p_value.length() ==10)
										p_value =value+" 00:00:00";
									value = DateFormatUtils.formatStringToDate(p_value);
								}
							}
							
							// add by wui
							if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_ORACLE)){
								if(isExistsClob(clob_names, (String)cols[i])){
									if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_ORACLE)){
										clobMap.put(i+"", (String)cols[i]);
										continue;
									}
								}
							}
							
							if(Consts.SYSDATE.equals(fields.get(cols[i]))){
								sysDateMap.put(i+"",DBTUtil.current());//(String)cols[i]
								continue;
							}
							
							if(Consts.NOW.equals(fields.get(cols[i]))){
								sysDateMap.put(i+"",DBTUtil.current());//(String)cols[i]
								continue;
							}
							
							//添加数据来源
							if(Consts.SOURCE_FROM.equals(cols[i].toString().toLowerCase()) 
									&& (value == null || value.toString().equals(""))){
								values[j++] = source_from;
								fields.put(Consts.SOURCE_FROM,source_from);
							}else{
								values[j++] =value;
								//fields.put(Consts.SOURCE_FROM,source_from);//如果map里面source_from会覆盖掉的，没一点用处。
							}
							cols[i] = quoteCol(cols[i].toString());
							
						}
						sql = "INSERT INTO " + table + " ("+ StringUtil.implode(", ", cols);
						sql = sql + ") VALUES (" + StringUtil.implodeValue(", ", values,cols,sysDateMap,clobMap);
						String regCheck = ".*(?i)\\bsource_from\\b.*";
						
						if(!sql.matches(regCheck)){
							sql = sql.replace(")", ", source_from)");
							sql += ",?";
							values[j] = source_from;
						}
						sql = sql + ")";
						sql =sql.replaceAll("#empty_clob", "empty_clob()");//避免上面source_from字段处理导致sql语法错误
						jdbcTemplateGProd.update(sql, values);
				} catch (Exception e) {
						e.printStackTrace();
						throw new DBRuntimeException(e, sql);
				}
				
				if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_ORACLE)){
					//blob字段插入处理
					try {
						if(!ListUtil.isEmpty(clob_names))
							insertClob(table, fields, key_col_name, clob_names);
							
					}  catch (Exception e) {
						e.printStackTrace();
						throw new DBRuntimeException(e, sql);
					}
				}
			
	}
	
	/**
	 * @author zqh
	 * 过滤clob类型的params字段，不在insert脚本中插入。解决商品货品添加操作失败问题。
	 * @param fields
	 */
	public Map<String, Object> copyMapFields(Map<String, Object> fields){
		Object[] keys = fields.keySet().toArray();
		Map<String, Object> newFields = new HashMap<String, Object>();
		for(int i=0;i<keys.length;i++){
			String key = (String) keys[i];
			if(!"params".equals(key)){
				newFields.put(key, fields.get(key));
			}
		}
		return newFields;
	}

	private void refreshWidgetCache(String table) {
		//add by wui统一抓取处理
		if(table.equals("es_goods") ||
				table.equals("es_goods") ||
				table.equals("es_goods_lv_price") ||
				table.equals("es_product") ||
				table.equals("es_goods_complex") ||
				table.equals("es_goods_adjunct") ||
				table.equals("es_tags") ||
				table.equals("es_brand") ||
				table.equals("es_type_brand") ||
				table.equals("es_goods_cat") || 
				table.equals("es_goods_spec") || 
				table.equals("es_article") || 
				table.equals("es_en_article") || 
				table.equals("es_adv") ||
				table.equals("es_news") ||
				table.equals("es_adv") || 
				table.equals("es_cat_complex"))
		ManagerUtils.resetWidgetCache();
	}
	

	private void insertClob(String table, Map<String, Object> fields, String key_col_name, List<Map<String,Object>> clob_names)throws SQLException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			String sql ="";
			Connection conn = DataSourceUtils.getConnection(this.jdbcTemplateGProd.getDataSource());
			ResultSet rs = null;
			PreparedStatement pst=null;
			String flag="tomcat";
			try{
				StringBuffer select = new StringBuffer("select * ");
				select.append(" from ").append(table).append(" where ");
				select.append(key_col_name).append("=? ");
				
				if(StringUtils.isNotEmpty(Const.getStrValue(fields, "source_from"))){//source_from不为空，就使用传过来的source_from值
					select.append(" and source_from='").append(Const.getStrValue(fields, "source_from")).append("'");
				}else{
					select.append(" and source_from='").append(ManagerUtils.getSourceFrom()).append("'");
				}
				select.append(" for update ");
				sql = select.toString();
				pst = conn.prepareStatement(sql);
				pst.setObject(1, fields.get(key_col_name));
				rs = pst.executeQuery();
				Object[] cols = fields.keySet().toArray();
				while (rs.next()) {
					for (Map colMap : clob_names) {
						String field_name =(String)colMap.get("field_name");
						String data = (String) fields.get(field_name.toLowerCase());
						
						try{
							//<<大字段分布式存储begin
							IStoreProcesser netBlog = StoreProcesser.getProcesser(table, field_name, (String)fields.get("source_from"), data);
							String subFolder ="default";
							if(table.indexOf("_")>-1)
								subFolder = table.substring(table.indexOf("_"));
							String ext = ".txt";
							
							if(data==null)
								continue;
							data = netBlog.upload(FileBaseUtil.getStringBuffer(data), fields.get(key_col_name)+ext, subFolder);
							data = data == null ? "" : data.toString();
							HashMap param = new HashMap();
							if(!(netBlog instanceof DBProcesser)){ //addby wui数据库存储时不需要写入存储表
								//写入日志
								param.put("table_name", table);
								param.put("field_name", field_name);							
								param.put("key_col_name", key_col_name);
								param.put("primy_key_value", fields.get(key_col_name));
								param.put("data", data);
								param.put("store_type", netBlog.getStroeType());
								insertDstoreLog(param);
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						//大字段分布式存储end>>
						
						
						//大字段db存储begin..
						Object value = rs.getClob(field_name);
						if(value==null)
							continue;
						CLOB clob = null;
						OracleThinClob otclob = null;
						Writer writer = null;
						if(value.getClass().getName().indexOf("weblogic")!=-1){
							flag = "weblogic";
							otclob = (OracleThinClob)rs.getClob(field_name);
						}
						else{
							flag = "tomcat";
							clob = (oracle.sql.CLOB)rs.getClob(field_name);
						}
						
						if (clob instanceof CLOB || otclob instanceof OracleThinClob) {
							if("tomcat".equals(flag)){
								writer = clob.getCharacterOutputStream();
							}
							else{
								writer = otclob.getCharacterOutputStream();
							}
							BufferedWriter out = new BufferedWriter(writer);
							try {
								out.write(data, 0, data.length());
								out.flush();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								try {
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						} else if (value instanceof Blob) {
							// 得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
							BLOB blob = (BLOB) rs.getBlob(field_name);
							OutputStream out = blob.getBinaryOutputStream();

							
							// data是传入的byte数组，定义：byte[] data
							try {
								out.write(data.getBytes(), 0,
										data.getBytes().length);
								out.flush();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								try {
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			conn.commit();
		}catch (SQLException e) {
			conn.rollback();//异常则回滚处理//add by wui
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (conn != null) {
				DataSourceUtils.doReleaseConnection(conn, this.jdbcTemplateGProd.getDataSource());
				
			}
		}
	}
	
	//add by wui插入BLOB、CLOB字段文件服务器改造处理逻辑
	private void insertDstoreLog(Map param) {
		String table_name = (String)param.get("table_name");
		String key_col_name = (String)param.get("key_col_name");
		String primy_key_value = (String)param.get("primy_key_value");
		String field_name = (String)param.get("field_name");
		String store_type = (String)param.get("store_type");
		String file_path = (String)param.get("data");
		if(file_path.getBytes().length>500){
			file_path = file_path.substring(250);
		}else{
			file_path = (String)param.get("data");
		}
		DStoreInst inst = new DStoreInst();
		inst.setSeq(getSequences("ES_DSTORE_INST_SEQ"));
		inst.setTable_name(table_name.toUpperCase());
		inst.setField_name(field_name.toUpperCase());
		inst.setKey_col_name(key_col_name);
		inst.setPrimy_key_value(primy_key_value);
		inst.setStore_type(store_type);
		inst.setFile_path(file_path);
		inst.setSource_from(ManagerUtils.getSourceFrom());
		inst.setDisbaled(String.valueOf(Consts.DSTORE_DISABLED_0));
		inst.setState(Consts.DSTORE_STATE_00A);
		TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(inst) {
			@Override
			public ZteResponse execute(ZteRequest inst) {
				try {
					DStoreInst instN = (DStoreInst)inst;
					String updateSql ="insert into es_dstore_inst_his select * from es_dstore_inst where Primy_key_value ='"+instN.getPrimy_key_value()+"' and field_name='"+instN.getField_name()+"'"+ManagerUtils.apSFromCond("");
					String deleteSql ="delete from es_dstore_inst where Primy_key_value ='"+instN.getPrimy_key_value()+"' and field_name='"+instN.getField_name()+"'"+ManagerUtils.apSFromCond("");
					//旧数据归档
					jdbcTemplateGProd.execute(updateSql);
					jdbcTemplateGProd.execute(deleteSql);
					//插入新数据
					logger.info("  instN  Table_name:"+instN.getTable_name()+" Field_name:"+instN.getField_name()+" File_path:"+instN.getFile_path());
					insert("es_dstore_inst", instN);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ZteResponse();
			}
		});
		ThreadPoolFactory.singleExecute(taskThreadPool); //异步单线程执行
	}
	
	@SuppressWarnings("unchecked")
	private boolean  isExistsClob(List<Map<String,Object>> col_names, String colname) {
		if(!ListUtil.isEmpty(col_names)){
			for(Map colMap:col_names){
				if(colname.toUpperCase().equals(colMap.get("field_name")))
					return true;
			}
			
		}
		return false;
	}

	private void setFieldsValue(Map<String, Object> fields,
			String key_col_name, Object... args) {
		if(!StringUtils.isEmpty(key_col_name)){
			try{
				String key_value = fields.get(key_col_name).toString();
				if(args[0] instanceof Map){
					((Map)args[0]).put(key_col_name, key_value);
				}else {
					Method[] methods = args[0].getClass().getMethods();
					for(Method method:methods){
						String name = method.getName();
						if(name.toUpperCase().equals(("set"+key_col_name).toUpperCase())){
							method.invoke(args[0],key_value);
							break;
						}

					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 设置Oracle数据库列关键字 add by gong.zhiwei
	 * @param fields
	 */
	private static void setColumn(Map<String, Object> fields) {
		if ("2".equals(EopSetting.DBTYPE)) {
			List<String> keys = new ArrayList<String>();
			for (Entry<String, Object> entry : fields.entrySet()) {
				String key = entry.getKey();
				if (!StringUtil.isEmpty(key)) {
					if (keywords.contains(key.toUpperCase())) {
						keys.add(key);
					}
				}
			}
			if (keys != null && !keys.isEmpty()) {
				for (String key : keys) {
					fields.put("\"" + key + "\"", fields.get(key));
					fields.remove(key);
				}
			}
		}
	}
	/**
	 * 获取任意表的主键Sequence add by gong.zhiwei
	 * 
	 * @param table
	 * @param fields
	 * @throws SQLException
	 */
	private String setPKValue(String table, Map<String, Object> fields) {
		boolean flag = true;
		String key_col_name = "";
		//if ("2".equals(EopSetting.DBTYPE)) {
			
			try {
				String sql = "select pk_name from es_table_manage  where table_name = ?";
				List keyList = this.simpleJdbcTemplateGProd.queryForList(sql, table.toUpperCase());
				if(!ListUtil.isEmpty(keyList)){
					Map keyMap = (Map)keyList.get(0);
					key_col_name =(String)(keyMap.get("PK_NAME"));
					
				}else{
					flag = false;
				}
				if (key_col_name != null && !"".equals(key_col_name)) {
					for (Entry<String, Object> entry : fields.entrySet()) {
						String key = entry.getKey();
						if (key_col_name.equalsIgnoreCase(key.toUpperCase())) {
							Object value = entry.getValue();
							if (value == null || "".equals(value)||"0".equals(value)) {
								value = this.getSeqByTableName(table);
								fields.put(key.toLowerCase(), value);
								flag = false;
							}else{
								flag = false;
							}
						}
					}
				}				
				if (flag) {
					String value = this.getSeqByTableName(table);
//					this.getLastId(table);
					fields.put(key_col_name.toLowerCase(), value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		//}
		return key_col_name.toLowerCase();
	}

	@Override
	public void insert(String table, Object po) {
		insert(table, ReflectionUtil.po2Map(po),po);
	}
	
	
	

	@Override
	public int queryForInt(String sql, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			return this.simpleJdbcTemplateGProd.queryForInt(sql, args);
		} catch (RuntimeException e) {
			this.logger.info(e.getMessage(), e);
			throw e;
		}

	}
	
	@Override
	public int queryForIntByMap(String sql, Map args) {
		try {
			sql = this.addSourceFrom(sql);
			return this.simpleJdbcTemplateGProd.queryForInt(sql, args);
		} catch (RuntimeException e) {
			this.logger.info(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public String queryForString(String sql) {
		String s = "";
		try {
			sql = this.addSourceFrom(sql);
			s  = this.jdbcTemplateGProd.queryForObject(sql, String.class);
		} catch (RuntimeException e) {
//			e.printStackTrace();
		}
		return s;
	}

    @Override
	public String queryForString(String sql,Object...args) {
        String s = "";
        try {
        	sql = this.addSourceFrom(sql);
            s  = this.jdbcTemplateGProd.queryForObject(sql, String.class,args);
        } catch (RuntimeException e) {
			e.printStackTrace();
        }
        return s;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List queryForList(String sql, Object... args) {
		sql = this.addSourceFrom(sql);
		List<Map<String, Object>> list = this.jdbcTemplateGProd.queryForList(sql, args);
		if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_MYSQL)){
			List<Map> mysqlMap = new ArrayList<Map>();
			for(Map m:list){
				Iterator it = m.keySet().iterator();
				Map newMap = new HashMap();
				while(it.hasNext()){
					String key = (String) it.next();
					if(key!=null)
						newMap.put(key.toLowerCase(), m.get(key));
				}
				mysqlMap.add(newMap);
			}
			return mysqlMap;
		}else{
			return list;
		}
	}


    @Override
	@SuppressWarnings("unchecked")
    public List queryForListNoSourceFrom(String sql, Object... args) {
        List<Map<String, Object>> list = this.simpleJdbcTemplateGProd.queryForList(sql, args);
        List<Map> mysqlMap = new ArrayList<Map>();
        for(Map m:list){
            Iterator it = m.keySet().iterator();
            Map newMap = new HashMap();
            while(it.hasNext()){
                String key = (String) it.next();
                if(key!=null)
                    newMap.put(key.toLowerCase(), m.get(key));
            }
            mysqlMap.add(newMap);
        }
        return mysqlMap;
    }
	
	@Override
	public List queryForLists(String sql) {
		sql = this.addSourceFrom(sql);
		return jdbcTemplateGProd.queryForList(sql);
	}
	
	@Override
	public List queryForLists(String sql,T t) {
		sql = this.addSourceFrom(sql);
		return jdbcTemplateGProd.queryForList(sql,t.getClass());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List queryForListByMap(String sql, Map args) {
		sql = this.addSourceFrom(sql);
		return this.simpleJdbcTemplateGProd.queryForList(sql, args);
	}

	@Override
	public List<T> queryForList(String sql, RowMapper mapper, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			return this.jdbcTemplateGProd.query(sql, args, mapper);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DBRuntimeException(ex, sql);
		}
	}
	
	

	@Override
	public List<T> queryForList(String sql, Class clazz, Object... args) {
		
//		this.jdbcTemplateGProd.queryForList(sql, elementType, args)
		sql = this.addSourceFrom(sql);
		return this.simpleJdbcTemplateGProd.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
	}
	
	@Override
	public List<T> queryForListByMap(String sql, Class clazz, Map args) {
		sql = this.addSourceFrom(sql);
		return this.simpleJdbcTemplateGProd.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
	}

	@Override
	public List queryForListPage(String sql, int pageNo, int pageSize,
			Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = this.buildPageSql(sql, pageNo, pageSize);
			return queryForList(listSql, args);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	@Override
	public List<T> queryForList(String sql, int pageNo, int pageSize,
			RowMapper mapper) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = this.buildPageSql(sql, pageNo, pageSize);
			return this.queryForList(listSql, mapper);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	@Override
	public long queryForLong(String sql, Object... args) {
		sql = this.addSourceFrom(sql);
		return this.jdbcTemplateGProd.queryForLong(sql, args);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map queryForMap(String sql, Object... args) {
		sql = this.addSourceFrom(sql);
		// Map map = this.simpleJdbcTemplateGProd.queryForMap(sql, args);
		try {
			Map map = this.jdbcTemplateGProd.queryForMap(sql, args);
			//if (EopSetting.DBTYPE.equals("2")) {
				Map newMap = new HashMap();
				Iterator keyItr = map.keySet().iterator();
				while (keyItr.hasNext()) {
					String key = (String) keyItr.next();
					Object value = map.get(key);
					newMap.put(key.toLowerCase(), value);
				}
				return newMap;
			//} else
			//	return map;
		} catch (Exception ex) {
			logger.warn(ex.getMessage());
			throw new ObjectNotFoundException(ex, sql);
		}
	}
	
	/**
	 * add by cc 
	 * 返回MAP，KEY-VALUE
	 */
	@Override
	public Map queryForMaps(String sql,Object... args){
		Map map = new HashMap();
		List list =queryForList(sql,args);
		for (int i = 0; i < list.size(); i++) {
			Map<String,String> m = new HashMap<String,String>();
			m  = (Map<String, String>) list.get(i);
			String keyString = m.get("value");
			String valueString = m.get("key");
			map.put(keyString,valueString);
		}
		return map;
	}
	

	@Override
	public T queryForObject(String sql, Class clazz, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			return (T) simpleJdbcTemplateGProd
					.queryForObject(sql, ParameterizedBeanPropertyRowMapper
							.newInstance(clazz), args);
			// return (T) this.jdbcTemplateGProd.queryForObject(sql, args, clazz);
		} catch (Exception ex) {
			// ex.printStackTrace();
			// throw new ObjectNotFoundException(ex, sql);
			this.logger.info("查询出错", ex);
			return null;
		}
	}
	
	@Override
	public T queryForObjectByMap(String sql, Class clazz, Map args) {
		try {
			sql = this.addSourceFrom(sql);
			return (T) simpleJdbcTemplateGProd
			.queryForObject(sql, ParameterizedBeanPropertyRowMapper
					.newInstance(clazz), args);
		} catch (Exception ex) {
			this.logger.info("查询出错", ex);
			return null;
		}
	}

	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize,
			Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) "
					+ removeSelect(removeOrders(sql));
			List list = queryForList(listSql, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}



    @Override
	public Page queryForPageNoSourceFrom(String sql, int pageNo, int pageSize,
                             Object... args) {
        try {
            Assert.hasText(sql, "SQL语句不能为空");
            Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
            String listSql = buildPageSql(sql, pageNo, pageSize);
            String countSql = "SELECT COUNT(*) "
                    + removeSelect(removeOrders(sql));
            List list = queryForListNoSourceFrom(listSql, args);
            int totalCount = queryForInt(countSql, args);
            return new Page(0, totalCount, pageSize, list);
        } catch (Exception ex) {
            throw new DBRuntimeException(ex, sql);
        }
    }

    @Override
	public Page queryForPage(String sql, int pageNo, int pageSize,boolean flag, Object... args) {
        try {
        	sql = this.addSourceFrom(sql);
            Assert.hasText(sql, "SQL语句不能为空");
            Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
            String listSql = buildPageSql(sql, pageNo, pageSize);
            String countSql = "SELECT COUNT(*) "+ removeSelect(removeOrders(sql));
            if(flag){
                 countSql = " select count(*) count from ( " + removeOrders(sql) + " ) ty";
            }
            List list = queryForList(listSql, args);
            int totalCount = queryForInt(countSql, args);
            return new Page(0, totalCount, pageSize, list);
        } catch (Exception ex) {
            throw new DBRuntimeException(ex, sql);
        }
    }
    
    
    @Override
	public Page queryForPage(String sql, int pageNo, int pageSize,Class<T> clazz,
    		boolean flag, Object... args) {
        try {
        	sql = this.addSourceFrom(sql);
            Assert.hasText(sql, "SQL语句不能为空");
            Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
            String listSql = buildPageSql(sql, pageNo, pageSize);
            String countSql = " select count(*) count from ( " + removeOrders(sql) + " ) ty";
            List<T> list = this.queryForList(listSql, clazz, args);
            int totalCount = queryForInt(countSql, args);
            return new Page(0, totalCount, pageSize, list);
        } catch (Exception ex) {
            throw new DBRuntimeException(ex, sql);
        }
    }
	
	@Override
	public Page queryForPageByMap(String sql, int pageNo, int pageSize,
			Map args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) FROM ("
				+ removeOrders(sql) + " )";
			List list = queryForListByMap(listSql, args);
			int totalCount = queryForIntByMap(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	@Override
	public Page queryForPage(String sql, String countSql,int pageNo, int pageSize,
			RowMapper rowMapper, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			countSql = this.addSourceFrom(countSql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			
			List<T> list = this.queryForList(listSql, rowMapper, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DBRuntimeException(ex, sql);
		}
	}
	
	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize,
			RowMapper rowMapper, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) "
					+ removeSelect(removeOrders(sql));
			List<T> list = this.queryForList(listSql, rowMapper, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize,
			Class<T> clazz, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) "
					+ removeSelect(removeOrders(sql));
			List<T> list = this.queryForList(listSql, clazz, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	@Override
	public Page queryForPageNoCount(String sql, int pageNo, int pageSize,
			Class<T> clazz, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			List<T> list = this.queryForList(listSql, clazz, args);
			int totalCount = list!=null?list.size():0;
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}
	
	@Override
	public Page queryForPageByMap(String sql, int pageNo, int pageSize,
			Class<T> clazz, Map args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) FROM ("
				+ removeOrders(sql) + " )";
			List<T> list = queryForListByMap(listSql, clazz, args);
			int totalCount = queryForIntByMap(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}
	
	
	@Override
	public Page queryForCPage(String sql, int pageNo, int pageSize,
			Class<T> clazz,String countSql, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			
			List<T> list = this.queryForList(listSql, clazz, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}
	

	//add by easonwu 20131219
	@Override
	public void update(String sql, Map args) {
		this.simpleJdbcTemplateGProd.update(sql, args) ;
	}


	@Override
	public void update(String table, Map fields, Map where) {
		
		String whereSql = "";

		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, fields, whereSql);
	}

	@Override
	public void update(String table, T po, Map where) {
		String whereSql = "";
		// where值
		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, ReflectionUtil.po2Map(po), whereSql);
	}

	@Override
	public void update(String table, T po, String where) {
		this.update(table, ReflectionUtil.po2Map(po), where);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int update(String table, Map fields, String where,Object ...args) {
		int result = 0;
		refreshWidgetCache(table);
		String sql = "";
		
		List<Map<String, Object>> clob_names = new ArrayList<Map<String, Object>>();
		try {
			Assert.hasText(table, "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			Assert.hasText(where, "where条件不能为空");
			table = quoteCol(table);
			setColumn(fields);
			
			clob_names = (new StoreProcesser()).getTabClobName(table.toUpperCase());
			if ("3".equals(EopSetting.DBTYPE)) { // SQLServer主键不允许更新，执行update前先删除
				try {
					where = where.replaceAll(" ", "");
					String key = where.split("=")[0];
					fields.remove(key);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("SQLServer数据库更新异常，可能需要单独处理！");
				}
			}

			// 字段值
			Object[] cols = fields.keySet().toArray();
			
			int count = 0 ;
			for (int i = 0; i < cols.length; i++) {
				if(!Consts.SYSDATE.equals(fields.get(cols[i])) && !Consts.NOW.equals(fields.get(cols[i]))
						&& !isExistsClob(clob_names, (String)cols[i]))
					count++;
			}
			
			Object[] values = new Object[count];
			Map<String,String> clobMap = new HashMap<String,String>();
			int j=0;
			for (int i = 0; i < cols.length; i++) {
				if(Consts.SYSDATE.equals(fields.get(cols[i])) || Consts.NOW.equals(fields.get(cols[i]))){
					cols[i] = quoteCol(cols[i].toString()) + "="+DBTUtil.current();
					continue;
				}
				
				Object value =fields.get(cols[i]);
				if(value instanceof String){
					String p_value =(String)value;
					if(!"es_outer_attr_inst".equalsIgnoreCase(table)&&!StringUtil.isEmpty(p_value) && ManagerUtils.timeMatch(p_value))
					{
						if(p_value.length() ==10)
							p_value =value+" 00:00:00";
						value =DateFormatUtils.formatStringToDate(p_value);
					}
				}
				
				if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_ORACLE)){
					if(isExistsClob(clob_names, (String)cols[i])){
						if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_ORACLE)){
							//value = "empty_blob()";
							clobMap.put(i+"", (String)cols[i]);
						}
					}
				}
				
				if(!StringUtil.isEmpty(clobMap.get(i+"")))
					cols[i] = quoteCol(cols[i].toString()) + "=empty_clob()";
				else{
					values[j++] =value;
					cols[i] = quoteCol(cols[i].toString()) + "=?";
				}
			}

			sql = "UPDATE " + table + " SET " + StringUtil.implode(", ", cols)
					+ " WHERE " + where;
			result = simpleJdbcTemplateGProd.update(sql, values);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBRuntimeException(e, sql);
		}
		
		//oracle才处理blob字段
		if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_ORACLE)){ //oracle数据库
			//blob字段插入处理
			try {
				if(!ListUtil.isEmpty(clob_names)){
					String key_col_name = setPKValue(table, fields);
					insertClob(table, fields, key_col_name, clob_names);
				}
			}  catch (Exception e) {
				e.printStackTrace();
				throw new DBRuntimeException(e, sql);
			}
		}
		return result;
	}

	@Override
	public String buildPageSql(String sql, int page, int pageSize) {
		String sql_str = null;

		String db_type = EopSetting.DBTYPE;
		if (db_type.equals("1")) {
			db_type = "mysql";
		} else if (db_type.equals("2")) {
			db_type = "oracle";
		} else if (db_type.equals("3")) {
			db_type = "sqlserver";
		}

		if (db_type.equals("mysql")) {
			sql_str = sql + " LIMIT " + (page - 1) * pageSize + "," + pageSize;
		} else if (db_type.equals("oracle")) {
			StringBuffer local_sql = new StringBuffer(
					"SELECT * FROM (SELECT t1.*,rownum sn1 FROM (");
			local_sql.append(sql);
			local_sql.append(") t1 WHERE rownum<= ");
			local_sql.append(page * pageSize);
			local_sql.append(") t2 WHERE t2.sn1>= ");
			local_sql.append((page-1) * pageSize+1);
			sql_str = local_sql.toString();
		} else if (db_type.equals("sqlserver")) {
			StringBuffer local_sql = new StringBuffer();
			// 找到order by 子句
			String order = SqlPaser.findOrderStr(sql);

			// 剔除order by 子句
			sql = sql.replaceAll(order, "");

			// 拼装分页sql
			local_sql.append("select * from (");
			local_sql.append(SqlPaser.insertSelectField("ROW_NUMBER() Over("
					+ order + ") as rowNum", sql));
			local_sql.append(") tb where rowNum between ");
			local_sql.append((page - 1) * pageSize + 1);
			local_sql.append(" AND ");
			local_sql.append(page * pageSize);

			// logger.info(sql);
			return sql.toString();

		}
		return sql_str.toString();
	}

	/**
	 * 格式化列名 只适用于Mysql
	 * 
	 * @param col
	 * @return
	 */
	private String quoteCol(String col) {
		if (col == null || col.equals("")) {
			return "";
		} else {
			return col;
		}
	}

	/**
	 * 格式化值 只适用于Mysql
	 * 
	 * @param value
	 * @return
	 */
	private String quoteValue(String value) {
		if (value == null || value.equals("")) {
			return "''";
		} else {
			return "'" + value.replaceAll("'", "''") + "'";
		}
	}

	/**
	 * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = 0;
		if(hql.indexOf("dc_public")>-1)
		{
			//目前只替换了source_from
			beginPos = hql.toLowerCase().replaceAll("(?i)source_from", "SOURCE_FROM").lastIndexOf("from");
		}else{
			 beginPos = hql.toLowerCase().replaceAll("(?i)source_from", "SOURCE_FROM").indexOf("from");
		}
		return hql.substring(beginPos);
	}


	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public void setJdbcTemplateGProd(JdbcTemplate jdbcTemplateGProd) {
		this.jdbcTemplateGProd = jdbcTemplateGProd;
	}

	public void setSimpleJdbcTemplateGProd(
			SimpleJdbcTemplate simpleJdbcTemplateGProd) {
		this.simpleJdbcTemplateGProd = simpleJdbcTemplateGProd;
	}

	@Override
	public T queryForObject(String sql, ParameterizedRowMapper mapper,
			Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			T t = (T) this.simpleJdbcTemplateGProd.queryForObject(sql, mapper, args);
			return t;
		} catch (RuntimeException e) {
			return null;
		}
	}
	
	public String getSequenceLen(String strSeqName,String strSeqType,int intSeqLen){
		String sql=""; // select str_to_date(now(),'%Y-%m-%d') 20131204180157
		if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_MYSQL)){ //add by wui mysql转换
			sql = "select str_to_date(now(),'%Y-%m-%d %T')"; //14-12-11-hh
			String ret = this.simpleJdbcTemplateGProd.queryForObject(sql, String.class);
			ret = ret.substring(0,ret.length()-2);
			ret = ret.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
			ret = ret.substring(2,8)+ManagerUtils.genRandowNum(6)+ret.substring(8);
			return ret;
		}else{
			if(strSeqType.trim().equals("0")){		//按长度取序列值
				sql = "SELECT LPAD(getseq('"+strSeqName+"'),"+intSeqLen+",'0') SEQ FROM DUAL";
			}else if(strSeqType.trim().equals("1")){   //在前面加8位年月日
				sql="SELECT to_char2(getdate(),'yyyymmdd')|| round(dbms_random.value(1000,9999)) ||LPAD(getseq('"+strSeqName+"'),"+intSeqLen+"-12,'0')  seq  FROM DUAL";
			}else if(strSeqType.trim().equals("2")){   //在前面加14位年月日时分秒
				sql="select to_char2(getdate(),'yyyymmddhh24miss')||LPAD(getseq('"+strSeqName+"'),"+intSeqLen+"-14,'0') seq from dual ";
			}else if(strSeqType.trim().equals("3")){		//按长度取序列值
				intSeqLen = intSeqLen-8;
				sql = "SELECT to_char2(getdate(),'yyyymmdd') || LPAD(getseq('"+strSeqName+"'),"+intSeqLen+",'0') SEQ FROM DUAL";
			}
			else if(strSeqType.trim().equals("4")){
				sql="SELECT '69'||to_char2(getdate(),'yyyymmdd')||LPAD(getseq('"+strSeqName+"'),"+intSeqLen+"-12,'0')  seq  FROM DUAL";
			}
			else{		//直接取序列
				sql="select getseq('"+strSeqName+"') from dual";
			}
			String ret = this.simpleJdbcTemplateGProd.queryForObject(sql, String.class);
			return ret;
		}
    }
	
	
	
	@Override
	public int nextVal(String seq_name) {
		String sql = "SELECT "+seq_name+".nextval SEQ FROM DUAL";
		return this.simpleJdbcTemplateGProd.queryForInt(sql, new HashMap());
	}
	
	/**
	 * 增加数据来源
	 * @param sql
	 * @return
	 */
	private static List<String> filterTables = new ArrayList<String>();
	static{
		filterTables.add("PM_APP");
		filterTables.add("dual");
		filterTables.add("ES_DC_PUBLIC");
		filterTables.add("orders");
	}
	private String addSourceFrom(String sql){
		//检查是否含有source_from(where之后是否含有source_from，有则全部过滤掉)
		
		for(String table:filterTables){
			if(sql.indexOf(table)>-1 || sql.indexOf(table.toLowerCase())>-1)
				return sql;
		}
		String regCheck = ".*(?i)\\bwhere\\b.*[\\s|.]source_from[\\s|=].*";
		//替换所有where(where前后都有间隔符，不区分大小写)
		String regReplace = "\\s+(?i)where\\s+";
		
		if(!sql.matches(regCheck)){				//如果where之后没有source_from,则替换所有where
			sql = sql.replaceAll(regReplace, " where source_from = '" + ManagerUtils.getSourceFrom() + "' and ");
		}
		return sql;
	}

	@Override
	public String queryForSingleResult(String sql, String key, Object... args) {
		Map map =null;
		sql = this.addSourceFrom(sql);
		try {
			 map = jdbcTemplateGProd.queryForMap(sql,args);
		} catch (Exception e) {
			return "";
		}
		String value ="";
		if(map !=null){
			value = (String) map.get(key);
		}
		return value;
	}

	@Override
	public Page qryForPage(String sql, int pageNo, int pageSize,
			Class<T> clazz, Object... args) {
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) "
					+ removeSelect(removeOrders(sql));
			List<T> list = this.qryForList(listSql, clazz, args);
			int totalCount = qryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}
	
	public int qryForInt(String sql, Object... args) {
		try {
			sql = this.addSourceFrom(sql);
			return this.simpleJdbcTemplateGProd.queryForInt(sql, args);
		} catch (RuntimeException e) {
			this.logger.info(e.getMessage(), e);
			throw e;
		}

	}
	
	public List<T> qryForList(String sql, Class clazz, Object... args) {
		return this.simpleJdbcTemplateGProd.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
	}

	@Override
	public void executeProceduce(String sql) {
		jdbcTemplateGProd.execute(sql);
	}
	@Override
	public void refreshPkConfig() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void refreshTableSeq() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String executeProc(CallableStatementCreator callableStatementCreator,
			CallableStatementCallback<String> callableStatementCallback) {
		// TODO Auto-generated method stub
		return jdbcTemplateGProd.execute(callableStatementCreator, callableStatementCallback);
	}
	@Override
	public void insertByMap(String table, Map<String, Object> fields) { //
		if (fields.isEmpty() || fields.size() <= 0) {
			return;
		}
		String sql ="";
		try {
			
			if(fields.containsKey("source_from")){
				fields.put("source_from", ManagerUtils.getSourceFrom());
			}
			
			Object[] cols = fields.keySet().toArray();
			int length=cols.length;
			Object[] values = new Object[length];
			StringBuffer colsBuffer = new StringBuffer();
			StringBuffer valuesBuffer = new StringBuffer();
			
			int j=0;
			for (int i = 0; i < length; i++) {
				String key_cols = (String) cols[i];
				Object value =fields.get(key_cols);
				if(value instanceof String){
					String p_value =(String)value;
					if(ManagerUtils.timeMatch(p_value)){
						if(p_value.length() ==10){
							p_value =value+" 00:00:00";
						}
						value = DateFormatUtils.formatStringToDate(p_value);
						values[i]=value;
					}
				}
				if(i==length-1){
					colsBuffer.append(key_cols);
					if(Consts.SYSDATE.equals(value)){
						valuesBuffer.append(DBTUtil.current());
					}else{
						values[j]=value;
					}
				}else{
					colsBuffer.append(key_cols).append(",");
					if(Consts.SYSDATE.equals(value)){
						valuesBuffer.append(DBTUtil.current()).append(",");
					}else{
						values[j]=value;
					}
				}
			}
			
			sql = "INSERT INTO " + table + " ("+ colsBuffer.toString()+") VALUES ("+valuesBuffer+ ")";
			simpleJdbcTemplateGProd.update(sql, values);
			} catch (Exception e) {
				e.printStackTrace();
				throw new DBRuntimeException(e, sql);
			}
	}
	
	@Override
	public void save(String table,String operate,Object pojo,String[] conditionCol) throws Exception{
		if(StringUtil.isEmpty(table))
			throw new Exception("传入的表名为空！");
		
		if(null == pojo)
			throw new Exception("传入的数据对象为空！");
		
		StringBuilder builder = new StringBuilder();
		
		String[] colArr = SqlUtil.getSaveSqlAndColArr(table, operate, pojo, conditionCol, builder);
		
		Object[] args = SqlUtil.getSaveArgs(operate, colArr, pojo, conditionCol);
		
		this.jdbcTemplateGProd.update(builder.toString(), args);
	}
	
	@Override
	public void saveBatch(String table,String operate,List pojoList,String[] conditionCol) throws Exception{
		if(pojoList==null || pojoList.size()==0)
			return;
		
		StringBuilder builder = new StringBuilder();
		String[] colArr = SqlUtil.getSaveSqlAndColArrBatch(table, operate, pojoList, conditionCol, builder );
		
		int batchSize = 200;
		List tempList = new ArrayList();
		
		for(int i=0;i<pojoList.size();i++){
			tempList.add(pojoList.get(i));
	        
	        int size = tempList.size();
	        
	        if(size % batchSize == 0 ){
	        	List<Object[]> batchArgs = SqlUtil.getBatchArgsArr(tempList, colArr, operate, conditionCol);
	        	this.batchExecute(builder.toString() , batchArgs);
	            
                tempList.clear();
            }
		}
		
		if(tempList.size() > 0){
			List<Object[]> batchArgs = SqlUtil.getBatchArgsArr(tempList, colArr, operate, conditionCol);
			this.batchExecute(builder.toString() , batchArgs);
            
            tempList.clear();
	    }
	}
	
	@Override
	public List<T> queryPojoListWithPageNo(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds, int pageNo, int pageSize)
			throws Exception {
		if(StringUtil.isEmpty(tableOrSql))
			throw new Exception("传入表名为空！");
		
		if(pojo == null)
			throw new Exception("传入pojo为空！");
		
		Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
		Assert.isTrue(pageSize >= 1, "pageSize 必须大于等于1");
		
		String baseSql = SqlUtil.getQuerySqlByPojo(tableOrSql, pojo, sqlBuilds);
		
		String sql = this.buildPageSql(baseSql, pageNo, pageSize);
		
		return this.queryForList(sql, pojo.getClass());
	}
	
	@Override
	public Page queryPageByPojo(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds, int pageNo,
			int pageSize) throws Exception {
		if(StringUtil.isEmpty(tableOrSql))
			throw new Exception("传入表名为空！");
		
		if(pojo == null)
			throw new Exception("传入pojo为空！");
		
		Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
		Assert.isTrue(pageSize >= 1, "pageSize 必须大于等于1");
		
		String baseSql = SqlUtil.getQuerySqlByPojo(tableOrSql, pojo, sqlBuilds);
		
		String sql = this.buildPageSql(baseSql, pageNo, pageSize);
		
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append(" select count(1) from (").append(baseSql).append(") ");
		
		try {
			List<T> list = this.queryForList(sql, pojo.getClass());
			
			int totalCount = queryForInt(countBuilder.toString());
			
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex,sql);
		}
	}
	
	@Override
	public List<T> queryPojoList(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		if(StringUtil.isEmpty(tableOrSql))
			throw new Exception("传入表名为空！");
		
		if(pojo == null)
			throw new Exception("传入pojo为空！");
		
		String baseSql = SqlUtil.getQuerySqlByPojo(tableOrSql, pojo, sqlBuilds);
		
		try {
			return this.queryForList(baseSql, pojo.getClass());
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, baseSql.toString());
		}
	}
	
	@Override
	public List queryForMapList(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		if(StringUtil.isEmpty(tableOrSql))
			throw new Exception("传入表名为空！");
		
		if(pojo == null)
			throw new Exception("传入pojo为空！");
		
		String baseSql = SqlUtil.getQuerySqlByPojo(tableOrSql, pojo, sqlBuilds);
		
		List<Map<String, Object>> list = this.jdbcTemplateGProd.queryForList(baseSql);
		
		if(EopSetting.DBTYPE.equals(DBTUtil.DB_TYPE_MYSQL)){
			List<Map> mysqlMap = new ArrayList<Map>();
			for(Map m:list){
				Iterator it = m.keySet().iterator();
				Map newMap = new HashMap();
				while(it.hasNext()){
					String key = (String) it.next();
					if(key!=null)
						newMap.put(key.toLowerCase(), m.get(key));
				}
				mysqlMap.add(newMap);
			}
			return mysqlMap;
		}else{
			return list;
		}
	}
	
	@Override
	public int queryCountByPojo(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		if(StringUtil.isEmpty(tableOrSql))
			throw new Exception("传入表名为空！");
		
		if(pojo == null)
			throw new Exception("传入pojo为空！");
		
		String baseSql = SqlUtil.getQuerySqlByPojo(tableOrSql, pojo, sqlBuilds);
		
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append(" select count(1) from (").append(baseSql).append(") ");
		
		int totalCount = queryForInt(countBuilder.toString());

		return totalCount;
	}
	
	@Override
	public Page queryForPageWithoutSourceFrom(String sql, int pageNo, int pageSize,Class<T> clazz,
    		Object... args) {
        try {
            Assert.hasText(sql, "SQL语句不能为空");
            Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
            
            String listSql = buildPageSql(sql, pageNo, pageSize);
            String countSql = " select COUNT(1) count from ( " + removeOrders(sql) + " ) ty";
            
            List<T> list = this.queryForListWithoutSourceFrom(listSql, clazz, args);
            
            int totalCount = this.queryForIntWithoutSourceFrom(countSql, args);
            
            return new Page(0, totalCount, pageSize, list);
        } catch (Exception ex) {
            throw new DBRuntimeException(ex, sql);
        }
    }
	
	@Override
	public List<T> queryForListWithoutSourceFrom(String sql, Class clazz, Object... args) {
		return this.simpleJdbcTemplateGProd.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
	}
	
	@Override
	public int queryForIntWithoutSourceFrom(String sql, Object... args) {
		try {
			return this.simpleJdbcTemplateGProd.queryForInt(sql, args);
		} catch (RuntimeException e) {
			this.logger.info(e.getMessage(), e);
			throw e;
		}
	}
}
