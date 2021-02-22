package com.ztesoft.component.common.staticdata.dao;

import java.util.ArrayList;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOSystemException;

public interface StaticDataDAO {
	public void setDto(DynamicDict dto) ;
	
	public DynamicDict getDto() ;
	public String getSql(String name) throws DAOSystemException;
	
	public ArrayList getStaticData(String dcSql) throws DAOSystemException;
	
	public ArrayList getAllSql() throws DAOSystemException;
	
	public  String[] getSidFlag() throws DAOSystemException;
	
	public void setSidFlag(String remoteSidFlag,String localSidFlag,String maintinceOperId) throws DAOSystemException;
	
}
