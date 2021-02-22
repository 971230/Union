package com.ztesoft.inf.operation.bo;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.inf.operation.vo.Operation;

import java.sql.SQLException;

public interface IOperationBO {

	public Operation getOperationByCode(String operationCode)
			throws FrameException, SQLException;
}
