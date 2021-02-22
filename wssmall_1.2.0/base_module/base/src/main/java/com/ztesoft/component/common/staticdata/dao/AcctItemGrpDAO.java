package com.ztesoft.component.common.staticdata.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.vo.AcctItemGrpVO;

public interface AcctItemGrpDAO extends DAO {

	AcctItemGrpVO findByPrimaryKey(String pacct_item_group_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pacct_item_group_id,AcctItemGrpVO vo) throws DAOSystemException;

	long delete( String pacct_item_group_id) throws DAOSystemException;

	@Override
	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
