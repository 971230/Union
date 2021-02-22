package com.ztesoft.net.eop.sdk.database;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.database.IDBRouter;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.impl.JdbcDaoSupportWork;
import com.ztesoft.net.framework.util.ReflectionUtil;

/**
 * Saas式的数据库操作类，包装了JdbcDaoSupport<br/>
 * 自动设置的表名
 * 
 * @author kingapex 2010-1-10下午07:30:59
 * @param <T>
 */
public class BaseJdbcDaoSupportWork<T> extends JdbcDaoSupportWork<T> {
	private IDBRouter dbRouterWork;

	public void setDbRouterWork(IDBRouter dbRouterWork) {
		this.dbRouterWork = dbRouterWork;
	}

	@Override
	public void insert(String table, Object po) {
		Map poMap = ReflectionUtil.po2Map(po);
		if (po instanceof Map) {
			insert(table, (Map)po);
			return;
		}
		table = this.dbRouterWork.getTableName(table);
		super.insert(table, poMap, po);
	}

	@Override
	public void execute(String sql, Object... args) {
		sql = wrapExeSql(sql);
		super.execute(sql, args);
	}

//	public String getLastId(String table) {
//		table = dbRouterGProd.getTableName(table);
//		return super.getLastId(table);
//	}

	public void insert(String table, Map fields) {
		table = this.dbRouterWork.getTableName(table);
		super.insert(table, fields, fields);
	}

	@Override
	public int queryForInt(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForInt(sql, args);
	}
	
	@Override
	public int queryForIntByMap(String sql, Map args) {
		sql = wrapSelSql(sql);
		return super.queryForIntByMap(sql, args);
	}

	@Override
	public List<Map> queryForList(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, args);
	}
	
	@Override
	public List<Map> queryForListByMap(String sql, Map args) {
		sql = wrapSelSql(sql);
		return super.queryForListByMap(sql, args);
	}

	@Override
	public List<T> queryForList(String sql, RowMapper mapper, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, mapper, args);
	}

	public List<T> queryForList(String sql, RowMapper mapper, List args) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, mapper, args);
	}
	
	@Override
	public List<T> queryForList(String sql, Class clazz, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, clazz, args);
	}
	
	@Override
	public List<T> queryForListByMap(String sql, Class clazz, Map args) {
		sql = wrapSelSql(sql);
		return super.queryForListByMap(sql, clazz, args);
	}

	@Override
	public List<Map> queryForListPage(String sql, int pageNo, int pageSize,
			Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForListPage(sql, pageNo, pageSize, args);
	}

	@Override
	public List<T> queryForList(String sql, int pageNo, int pageSize,
			RowMapper mapper) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, pageNo, pageSize, mapper);
	}

	@Override
	public long queryForLong(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForLong(sql, args);
	}

	@Override
	public String queryForString(String sql) {
		sql = wrapSelSql(sql);
		return super.queryForString(sql);
	}

	@Override
	public Map queryForMap(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForMap(sql, args);
	}

	@Override
	public T queryForObject(String sql, Class clazz, Object... args) {
		sql = wrapSelSql(sql);
		// logger.info(sql);
		return super.queryForObject(sql, clazz, args);

	}
	@Override
	public T queryForObjectByMap(String sql, Class clazz, Map args) {
		sql = wrapSelSql(sql);
		// logger.info(sql);
		return super.queryForObjectByMap(sql, clazz, args);
	}

	@Override
	public T queryForObject(String sql, ParameterizedRowMapper mapper,
			Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForObject(sql, mapper, args);
	}

	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize,
			Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForPage(sql, pageNo, pageSize, args);
	}
	
	@Override
	public Page queryForPageByMap(String sql, int pageNo, int pageSize,
			Map args) {
		sql = wrapSelSql(sql);
		return super.queryForPageByMap(sql, pageNo, pageSize, args);
	}
	
	@Override
	public Page queryForPageByMap(String sql, int pageNo, int pageSize, Class<T> clazz,
			Map args) {
		sql = wrapSelSql(sql);
		return super.queryForPageByMap(sql, pageNo, pageSize, clazz, args);
	}

	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize,
			RowMapper rowMapper, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForPage(sql, pageNo, pageSize, rowMapper, args);
	}

	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize,
			Class<T> clazz, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForPage(sql, pageNo, pageSize, clazz, args);
	}
	
    @Override
	public Page queryForPage(String sql, int pageNo, int pageSize,Class<T> clazz,
    		boolean flag, Object... args) {
    	sql = wrapSelSql(sql);
    	return super.queryForPage(sql, pageNo, pageSize, clazz, flag, args);
    }

	@Override
	public Page qryForPage(String sql, int pageNo, int pageSize,
			Class<T> clazz, Object... args) {
		return super.qryForPage(sql, pageNo, pageSize, clazz, args);
	}

    @Override
	public Page queryForPageNoSourceFrom(String sql, int pageNo, int pageSize,
                                         Object... args){
        return super.queryForPageNoSourceFrom(sql,pageNo,pageSize,args);
    }
	
	@Override
	public Page queryForCPage(String sql, int pageNo, int pageSize,
			Class<T> clazz,String countSql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForCPage(sql, pageNo, pageSize, clazz, countSql,args);
	}
	
	
	@Override
	public void update(String table, Map fields, Map where) {
		table = this.dbRouterWork.getTableName(table);
		super.update(table, fields, where);
	}

	public int update(String table, Map fields, String where) {
		table = this.dbRouterWork.getTableName(table);
		return super.update(table, fields, where);
	}

	@Override
	public void update(String table, T po, Map where) {
		table = this.dbRouterWork.getTableName(table);
		super.update(table, po, where);
	}

	@Override
	public void update(String table, T po, String where) {
		table = this.dbRouterWork.getTableName(table);
		super.update(table, po, where);
	}

	/**
	 * 读取当前登录用户id
	 * 
	 * @return
	 */
	private String getCurrentUserId() {
		String userid = EopContext.getContext().getCurrentSite().getUserid();
		return userid;
	}

	/**
	 * 读取当前管理的站点
	 * 
	 * @return
	 */
	private String getCurrentSiteId() {
		String id = EopContext.getContext().getCurrentSite().getId();
		return id;
	}

	public String wrapExeSql(String sql) {
		String pattern;
		if (sql.indexOf("update") >= 0) {
			pattern = "(update\\s+)(\\w+)(.+)";

		} else if (sql.indexOf("delete") >= 0) {
			pattern = "(delete\\s+from\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("insert") >= 0) {
			pattern = "(insert\\s+into\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("truncate") >= 0) {
			pattern = "(truncate\\s+table\\s+)(\\w+)(.*?)";
		} else {
			return sql;
		}

		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		if (m.find()) {
			String tname = m.group(2);
			sql = m.replaceAll("$1 " + this.dbRouterWork.getTableName(tname)
					+ " $3");
		}

		return sql;
	}

	public static void main(String args[]) {
		// //on tb1.a1=tb2.a2 where w=1 group by g order by o
		//
		// String sql = "select * from tb1 left join tb2 ";
		// // select * from tb
		// String pattern = "(.*)from(\\s+)(\\w+)(\\s+)()";
		// // select *from tb
		// //sql=addWhereUid(sql);
		// logger.info(sql);

		// String sql ="delete from bac  where b=2";
		// sql=wrapExeSql(sql);
		// logger.info(sql);

		/*String pattern = "(truncate\\s+table\\s+)(\\w+)(.*?)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher("truncate table menu");
		StringBuffer sb = new StringBuffer();

		if (m.find()) {
			String tname = m.group(2);
			logger.info(m.replaceAll("$1 es_" + tname + "_2 $3"));
		}
		String sql = "insert into xxtab(xxa ,bbb, ccs,) values(:a,:b:d,Ld)" ;
		String pattern = null ;
		if (sql.indexOf("update") >= 0) {
			pattern = "(update\\s+)(\\w+)(.+)";

		} else if (sql.indexOf("delete") >= 0) {
			pattern = "(delete\\s+from\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("insert") >= 0) {
			pattern = "(insert\\s+into\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("truncate") >= 0) {
			pattern = "(truncate\\s+table\\s+)(\\w+)(.*?)";
		} else {
//			return sql;
		}

		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		String tname = null ;
		if (m.find()) {
			tname = m.group(2);
//			sql = m.replaceAll("$1 " + this.dbRouterGProd.getTableName(tname)
//					+ " $3");
		}
		logger.info(tname) ;*/
	}

	/**
	 * 替换join句里的表名
	 * 
	 * @param sql
	 * @return
	 */
	public String rpJoinTbName(String sql) {

		String pattern = "(join\\s+)(\\w+)(\\s+)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();

		if (m.find()) {
			String tname = m.group(2);
			if(!StringUtils.equals(tname, "order_product")&&!StringUtils.equals(tname, "order_account_info")
					&&!StringUtils.equals(tname, "order_carry")&&!StringUtils.equals(tname, "order_pay")
					&&!StringUtils.equals(tname, "order_customer")&&!StringUtils.equals(tname, "order_exception_info")
					&&!StringUtils.equals(tname, "sys_dict_item")&&!StringUtils.equals(tname, "at_account_info")
					&&!StringUtils.equals(tname, "v_task_orders_unique"))
			m.appendReplacement(sb, "join " + this.dbRouterWork.getTableName(tname)
					+ " ");
		}
		m.appendTail(sb);

		return sb.toString();
	}

	/**
	 * 替换from句里的表名
	 * 
	 * @param sql
	 * @return
	 */
	public String rpFromTbName(String sql) {

		String pattern = "(\\s+from\\s+)(\\w+)(\\s*)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();

		if (m.find()) {
			String tname = m.group(2);
			if(!StringUtils.equals(tname, "dual")&&!StringUtils.equals(tname, "orders"))
			m.appendReplacement(sb, " from " + this.dbRouterWork.getTableName(tname)
					+ " ");
		}
		m.appendTail(sb);
		return sb.toString();
		
	}

	/**
	 * 替换select语句里的表名
	 * 
	 * @param sql
	 * @return
	 */
	public String rpSelTbName(String sql) {
		sql = rpJoinTbName(sql);
		sql = rpFromTbName(sql);
		return sql;
	}

	public String addUidTerm(String sql) {

		String term;
		if (sql.indexOf("where") > 1) {
			term = " and userid=" + getCurrentUserId() + " and siteid="
					+ getCurrentSiteId() + " ";
		} else {
			term = " where userid=" + getCurrentUserId() + " and siteid="
					+ getCurrentSiteId() + " ";
		}

		String pattern;
		if (sql.indexOf("group") > 1) {
			pattern = "(.+)(group\\s+by)";
		} else if (sql.indexOf("order") > 1) {
			pattern = "(.+)(order\\s+by)";
		} else {
			pattern = "(.+)($)";
		}

		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		if (m.find()) {
			sql = m.replaceAll("$1 " + term + " $2");
		}
		return sql;
	}

	/**
	 * 将select语句包装为相应的saas sql
	 * 
	 * @param sql
	 * @return
	 */
	public String wrapSelSql(String sql) {
		sql = rpSelTbName(sql);
		// sql=addUidTerm(sql);
		return sql;
	}


}
