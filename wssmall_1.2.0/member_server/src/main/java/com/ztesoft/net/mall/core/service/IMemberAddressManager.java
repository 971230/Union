package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.MemberAddress;

/**
 * 个人中心-接收地址
 * @author lzf<br/>
 * 2010-3-17 下午02:49:23<br/>
 * version 1.0<br/>
 */
public interface IMemberAddressManager {
	
	/**
	 * 列表接收地址
	 * @return
	 */
	public List listAddress();
	
	/**
	 * 取得地址详细信息
	 * @param addr_id
	 * @return
	 */
	public MemberAddress getAddress(String addr_id);
	
	public void addAddress(MemberAddress address);
	
	public void updateAddress(MemberAddress address);
	
	public void deleteAddress(String addr_id);
	
	public MemberAddress getLastAddress();
	
	public List<MemberAddress> listMemberAddress(String menber_id);

}
