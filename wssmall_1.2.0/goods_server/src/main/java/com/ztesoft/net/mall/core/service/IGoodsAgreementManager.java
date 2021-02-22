package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AgreementControl;
import com.ztesoft.net.mall.core.model.GoodsAgreement;

import java.util.List;

public interface IGoodsAgreementManager {
    /**
     * 添加框架协议
     * @param agreement 实体类
     */
	public void addAgreement(GoodsAgreement agreement);
	/**
	 * 编辑框架协议
	 * @param agreement
	 */
	public void updateAgreement(GoodsAgreement agreement);
	
	/**
	 * 删除框架协议
	 * @param agreement_id
	 */
	public void deleteAgreement(String agreement_id);
	/**
	 * 查询框架协议列表
	 * @param agreement_name  协议名称
	 * @param pageNo    页数
	 * @param pageSize  每页大小
	 * @return 返回查询的框架协议列表
	 */
	public Page listAgreement(String agreement_name ,int pageNo, int pageSize);
	/**
	 * 获得有效供销商
	 * @return
	 */
	public Page ListValidateSuppliers();
	/**
	 * 根据协议ID查询框架协议
	 * @param agreement_id 框架协议ID
	 * @return 返回符合条件的实体类数据
	 */
	public GoodsAgreement editAgreement(String agreement_id);
	 /**
	  * 新增协议控制
	  * @param agreementControl
	  */
	public void insertAgreementControl(AgreementControl agreementControl);
	public List getControlById(String agreementId);
	public void delAllControlByAgtId(String agreementId);
	public String getNameById(String agreement_Id);
}
