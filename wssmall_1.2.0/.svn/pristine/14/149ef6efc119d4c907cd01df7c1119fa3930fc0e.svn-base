package com.ztesoft.net.framework.database;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * 数据库操作支撑接口
 * @author kingapex
 * 2010-1-6下午01:33:13
 * @param <T>
 */
public interface IDaoSupport<T> {
	
	/**执行sql语句**/
	public void execute(String sql, Object... args) ;
	
	
	public Page queryForPage(String sql, String countSql,int pageNo, int pageSize,RowMapper rowMapper, Object... args);
	/**
	 * 批量执行sql语句
	 * @param sql
	 * @param args
	 */
	public void batchExecute(String sql, List<Object[]> batchArgs);
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>int</code>型返回
	 * @param sql 查询的sql语句，确定结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public int queryForInt(String sql, Object... args);	
	
	/**
	 * 查询单一结果集<br/>  
	 * 并将结果转为<code>int</code>型返回
	 * @param sql 查询的sql语句，使用:name占位符  确定结果为一行一列，且为数字型
	 * @param args K-V 键值对
	 * @return
	 */
	public int queryForIntByMap(String sql, Map args);
	
	/**
	 * 获取Oracle  Sequence
	 * @param seq_name
	 * @return
	 */
	public String getSequences(String seq_name);
	
	/**
	 * 获取序列
	 * @param seq_name
	 * @param len
	 * @return
	 */
//	public String getSequences(String seq_name,String ,String seq_len);
	
	/**
	 * 获取Oracle  Sequence
	 * @param seq_name
	 * @param len序列长度
	 * @param strSeqType 获取类型
	 * @return
	 */
	public String getSequences(String seq_name, String strSeqType, int len);
	/**
	 * 获取Oracle  Sequence
	 * @param seq_name
	 * @return
	 */
	public String getSeqByTableName(String table_name);
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>long</code>型返回
	 * @param sql 查询的sql语句，确保结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public long queryForLong(String sql, Object... args);
	
	public String queryForString(String sql);


    public String queryForString(String sql,Object...args);
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>T</code>对象返回
	 * @param sql 查询的sql语句,确保结果列名和对象属性对应
	 * @param clazz  <code>T</code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public T queryForObject(String sql, Class clazz, Object... args);
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>T</code>对象返回
	 * @param sql 查询的sql语句,确保结果列名和对象属性对应
	 * @param clazz  <code>T</code>的Class对象
	 * @param args 对应sql语句中的name占位符
	 * @return
	 */
	public T queryForObjectByMap(String sql, Class clazz, Map args);
 
	public T queryForObject(String sql, ParameterizedRowMapper mapper, Object... args) ;
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>Map</code>对象返回
	 * @param sql 查询的sql语句
	 * @param args 对应sql语句中的参数值
	 * @return 以结果集中的列为key，值为value的<code>Map</code>
	 */
	@SuppressWarnings("unchecked")
	public Map queryForMap(String sql, Object... args) ;
	
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<Map></code>
	 * @param sql 查询的sql语句
	 * @param args 对应sql语句中的参数值
	 * @return  列表中元素为<code>Map</code>的<code>List</code>,<br/>Map结构：以结果集中的列为key，值为value,
	 */
	@SuppressWarnings("unchecked")
	public List<Map> queryForList(String sql, Object... args);
	
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<Map></code>
	 * @param sql 查询的sql语句
	 * @param args 对应sql语句中的参数值
	 * @return  列表中元素为<code>Map</code>的<code>List</code>,<br/>Map结构：以结果集中的列为key，值为value,
	 */
	@SuppressWarnings("unchecked")
	public List<Map> queryForListNoSourceFrom(String sql, Object... args);
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<Map></code>
	 * @param sql 查询的sql语句 使用:name占位符
	 * @param args K-V 键值对
	 * @return  列表中元素为<code>Map</code>的<code>List</code>,<br/>Map结构：以结果集中的列为key，值为value,
	 */
	@SuppressWarnings("unchecked")
	public List<Map> queryForListByMap(String sql, Map args);
	
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<T></code>
	 * @param sql  查询的sql语句
	 * @param mapper 列和对象属性的Mapper
	 * @param args 对应sql语句中的参数值
	 * @return 列表中元素为<code>T</code>的<code>List</code>
	 */
	public List<T> queryForList(String sql, RowMapper mapper, Object... args) ;
	
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<T></code>
	 * @param sql 查询的sql语句
	 * @param clazz <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return  列表中元素为<code>T</code>的<code>List</code>
	 */
	public List<T> queryForList(String sql, Class clazz, Object... args);
	
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<T></code>
	 * @param sql 查询的sql语句 使用named占位符
	 * @param clazz <code><T></code>的Class对象
	 * @param args K-V 键值对  对应sql语句中的name参数
	 * @return  列表中元素为<code>T</code>的<code>List</code>
	 */
	public List<T> queryForListByMap(String sql, Class clazz, Map args);
	
	/**
	 * 分页查询多行结果集<br/>
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize  每页数量
	 * @param args  对应sql语句中的参数值
	 * @return 列表中元素为<code>Map</code>的<code>List</code>,<br/>Map结构：以结果集中的列为key，值为value,
	 */
	@SuppressWarnings("unchecked")
	public List<Map> queryForListPage(String sql, int pageNo, int pageSize, Object... args);
	
	/**
	 * 分页查询多行结果集<br/>
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param mapper 列和对象属性的Mapper
	 * @return 列表中元素为<code>T</code>的<code>List</code>
	 */ 
	public List<T> queryForList(String sql,  int pageNo, int pageSize,RowMapper mapper);
	
	/**
	 * 分页查询
	 * @param sql  查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize  每页数量
	 * @param args  对应sql语句中的参数值
	 * @return 分页结果集对象
	 */
	public Page queryForPage(String sql, int pageNo, int pageSize, Object... args) ;

    public Page queryForPage(String sql, int pageNo, int pageSize,boolean flag ,Object... args) ;

    public Page queryForPageNoSourceFrom(String sql, int pageNo, int pageSize,
                                         Object... args);
	
	/**
	 * 分页查询
	 * @param sql  查询的sql语句 使用:name占位符
	 * @param pageNo 查询的起始页
	 * @param pageSize  每页数量
	 * @param args  k-v 键值对 对应sql语句中的name参数
	 * @return 分页结果集对象
	 */
	public Page queryForPageByMap(String sql, int pageNo, int pageSize, Map args) ;
	
	/**
	 *  分页查询
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param rowMapper 列和对象属性的Mapper
	 * @param args 对应sql语句中的参数值
	 * @return 分页结果集对象
	 */
	public Page queryForPage(String sql,int pageNo, int pageSize, RowMapper rowMapper, 	Object... args);
	
	/**
	 * 分页查询
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param clazz  <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object... args);
	
	/**
	 * 分页查询(不查询总数)
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param clazz  <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public Page queryForPageNoCount(String sql, int pageNo, int pageSize, Class<T> clazz, Object... args);
	
	/**
	 * 分页查询
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param clazz  <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz,boolean flag, Object... args);
	
	/**
	 * 分页查询:因不需es_前缀而加，如查inf_comm_client_calllog数据
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @param clazz
	 * @param args
	 * @return
	 */
	public Page qryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object... args);
	/**
	 * 分页查询
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param clazz  <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public Page queryForPageByMap(String sql, int pageNo, int pageSize, Class<T> clazz, Map args);
	
	
	/**
	 * 分页查询，提供count语句
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param clazz  <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public Page queryForCPage(String sql, int pageNo, int pageSize, Class<T> clazz,String countSql, Object... args);
	
	/**
	 * 更新数据
	 * @param table 表名
	 * @param fields 字段-值Map
	 * @param where 更新条件(字段-值Map)
	 */
	@SuppressWarnings("unchecked")
	public void update(String table, Map fields, Map where);
	
	/**
	 * 更新数据
	 * @param table  表名
	 * @param fields 字段-值Map
	 * @param where 更新条件,如"a='1' AND b='2'"
	 */
	@SuppressWarnings("unchecked")
	public int update(String table, Map fields, String where,Object ...args);
	
	/**
	 * 更新数据
	 * @param table 表名
	 * @param po 要更新的对象，保证对象的属性名和字段名对应
	 * @param where 更新条件(字段-值Map)
	 */
	@SuppressWarnings("unchecked")
	public void update(String table, T po, Map where);
	
	/**
	 * 更新数据
	 * @param table 表名
	 * @param po 要更新的对象，保证对象的属性名和字段名对应
	 * @param where 更新条件,如"a='1' AND b='2'"
	 */
	public void update(String table, T po, String where) ;
	
	/**
	 * 新增数据
	 * @param table  表名
	 * @param fields 字段-值Map
	 */
	@SuppressWarnings("unchecked")
	public void insert(String table, Map<String,Object> fields, Object... args);//Object... args
		
	/**
	 * 新增数据
	 * @param table 表名
	 * @param po 要新增的对象，保证对象的属性名和字段名对应
	 */
	public void insert(String table, Object po);
	/**
	 * 不考虑打字段，提高处理性能
	 * @param table
	 * @param fields
	 */
	public void insertByMap(String table, Map<String, Object> fields);
		
	/**
	 * 获取当前事务最后一次更新的主键值
	 * @return
	 */
	public String getLastId(String table);
	
	
 
	/**
	 * 
	 * @param sql
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public String buildPageSql(String sql, int page, int pageSize);
	
	//提供namedjdbctemplate操作功能
	public void update(String sql, Map args) ;
	//批量操作
	public void batchExecuteByListMap(String sql, List<Map> batchArgs) ;
	
	//获取正常序列
	public int nextVal(String seq_name)  ;
	
	/**
	 * 获取单列
	 * @param sql
	 * @return
	 * 
	 */
	List queryForLists(String sql);
	
	List queryForLists(String sql,T t);
	
	String queryForSingleResult(String sql ,String key,Object... args);
	
	Map queryForMaps(String sql ,Object... args);
	
	public void executeProceduce(String sql);
	
	public Connection getConnection();
	public void closeConnection(Connection conn);
	
	/**
	 * 刷新表主键配置
	 * @作者 MoChunrun
	 * @创建日期 2014-12-4
	 */
	public void refreshPkConfig();
	public void refreshTableSeq();
	

	/**
	 * 执行带返回参数的存储过程
	 * @param callableStatementCreator
	 * @param callableStatementCallback
	 */
	public String executeProc(CallableStatementCreator callableStatementCreator,
			CallableStatementCallback<String> callableStatementCallback);

	/**
	 * 保存操作
	 * @param table	表名
	 * @param operate	操作，支持的有insert,update,delete
	 * @param pojo	数据pojo
	 * @param conditionCol	条件列名，update或delete时必传
	 * @throws Exception
	 */
	public void save(String table,String operate,Object pojo,String[] conditionCol) throws Exception;
	
	/**
	 * 批量保存
	 * @param table	表名
	 * @param operate	操作，支持的有insert,update,delete
	 * @param pojoList	数据list
	 * @param conditionCol	条件列名，update或delete时必传
	 * @throws Exception
	 */
	public void saveBatch(String table,String operate,List pojoList,String[] conditionCol) throws Exception;
	
	/**
	 * 根据传入的POJO参数对象和SQL构造器查询数据LIST
	 * @param tableOrSql	表名或SQL
	 * @param pojo	参数pojo对象
	 * @param sqlBuilds	特殊处理字段的SQL构造器，
	 * 比如一些字段作为查询条件时需要拼装成in,like语句的，需要传入相应的构造器
	 * @param pageNo	第几页
	 * @param pageSize	每页数据量
	 * @return	数据对象的List
	 * @throws Exception
	 */
	public List<T> queryPojoListWithPageNo(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception;

	/**
	 * 根据传入的POJO参数对象和SQL构造器查询数据
	 * @param tableOrSql	表名或SQL
	 * @param pojo	参数pojo对象
	 * @param sqlBuilds	特殊处理字段的SQL构造器，
	 * 比如一些字段作为查询条件时需要拼装成in,like语句的，需要传入相应的构造器
	 * @param pageNo	第几页
	 * @param pageSize	每页数据量
	 * @return	数据PAGE
	 * @throws Exception
	 */
	public Page queryPageByPojo(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 根据传入的POJO参数对象和SQL构造器查询数据
	 * @param tableOrSql	表名或SQL
	 * @param pojo	参数pojo对象
	 * @param sqlBuilds	特殊处理字段的SQL构造器，
	 * 比如一些字段作为查询条件时需要拼装成in,like语句的，需要传入相应的构造器
	 * @return	数据对象的List
	 * @throws Exception
	 */
	public List<T> queryPojoList(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 根据传入的POJO参数对象和SQL构造器查询数据
	 * @param tableOrSql	表名或SQL
	 * @param pojo	参数pojo对象
	 * @param sqlBuilds	特殊处理字段的SQL构造器，
	 * 比如一些字段作为查询条件时需要拼装成in,like语句的，需要传入相应的构造器
	 * @return	数据MAP的List
	 * @throws Exception
	 */
	public List queryForMapList(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 根据传入的POJO参数对象和SQL构造器查询数量
	 * @param tableOrSql	表名或SQL
	 * @param pojo	参数pojo对象
	 * @param sqlBuilds	特殊处理字段的SQL构造器，
	 * 比如一些字段作为查询条件时需要拼装成in,like语句的，需要传入相应的构造器
	 * @return	数量
	 * @throws Exception
	 */
	public int queryCountByPojo(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 不加source_from条件查询页面
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @param clazz
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Page queryForPageWithoutSourceFrom(String sql, int pageNo, 
			int pageSize,Class<T> clazz,Object... args);
	
	/**
	 * 不加source_from条件查询列表
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return
	 */
	public List<T> queryForListWithoutSourceFrom(String sql, Class clazz, Object... args);
	
	/**
	 * 不加source_from条件查询数量
	 * @param sql
	 * @param args
	 * @return
	 */
	public int queryForIntWithoutSourceFrom(String sql, Object... args);
}
