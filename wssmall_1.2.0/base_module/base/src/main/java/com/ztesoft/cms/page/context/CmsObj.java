package com.ztesoft.cms.page.context;

import com.ztesoft.cms.login.vo.TsmStaff;
import com.ztesoft.ibss.common.util.SqlMapExe;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Reason.Yea
 * @version Created Oct 26, 2012
 */
public class CmsObj {
	//数据源连接
	private Connection conn;
	private String cms_state;
	private String fun_id;
	private String info_id;
	private int page_size;
	private int page_index;
	private TsmStaff staff;
	private SqlMapExe sqlExe;
	//存放参数信息	
	private Map param;

    private String file_id;

    private String sequ;

	/**
	 * 构造函数
	 * @param conn
	 * @param cms_state
	 * @param fun_id
	 */
	public CmsObj(Connection conn, String cms_state, String fun_id) {
		super();
		this.conn = conn;
		this.cms_state = cms_state;
		this.fun_id = fun_id;
		this.info_id = "0";
		this.staff=null;
		this.sqlExe = new SqlMapExe(conn);
		this.param = new HashMap();
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public String getCms_state() {
		return cms_state;
	}
	public void setCms_state(String cms_state) {
		this.cms_state = cms_state;
	}
	public String getFun_id() {
		return fun_id;
	}
	public void setFun_id(String fun_id) {
		this.fun_id = fun_id;
	}
	public SqlMapExe getSqlExe() {
		return sqlExe;
	}
	public void setSqlExe(SqlMapExe sqlExe) {
		this.sqlExe = sqlExe;
	}
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	public void putParam(String key,Object value){
		this.param.put(key, value);
	}
	public Object getParam(String key){
		return this.param.get(key);
	}
	public String getStrParam(String key){
		Object obj = getParam(key);
		if(obj==null)return "";
		return (String)obj;
	}
	public TsmStaff getStaff() {
		return staff;
	}
	public void setStaff(TsmStaff staff) {
		this.staff = staff;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public int getPage_index() {
		return page_index;
	}
	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getSequ() {
        return sequ;
    }

    public void setSequ(String sequ) {
        this.sequ = sequ;
    }
}
