package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/*
 * add by wcl
 * 用户号码	acc_nbr	Y	String	业务号码
合约期	contract_month	N	String	
靓号信息	nice_info	N	MAP	


nice_info节点信息
名称	字段名	是否必填	类型	备注
靓号活动ID	lhscheme_id	N	String	BSS靓号必传
靓号预存金额	pre_fee	N	String	BSS靓号必传
预存话费金额	advancePay	N	String	CB总部靓号:存话费金额，单位：厘
号码等级	classId	N	String	CB总部靓号:
1：一级靓号
2：二级靓号
3：三级靓号
4：四级靓号
5：五级靓号
6：六级靓号
9：普通号码
协议期最低消费	lowCostPro	N	String	CB总部靓号:
单位：厘
协议时长	timeDurPro	N	String	CB总部靓号: 
当值为00000时表示无期限

 */
public class PhoneInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name="业务号码",type="String",isNecessary="Y",desc="业务号码")
	private String acc_nbr;
	
	@ZteSoftCommentAnnotationParam(name="合约期",type="String",isNecessary="Y",desc="合约期")
	private String contract_month;
	
	@ZteSoftCommentAnnotationParam(name="靓号信息",type="String",isNecessary="Y",desc="靓号信息")
	private NiceInfoVO nice_info;
	
	@ZteSoftCommentAnnotationParam(name="副卡开户时对应的主卡号码，副卡开户必传",type="String",isNecessary="N",desc="副卡开户时对应的主卡号码，副卡开户必传")
    private String mainNumber;
	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getContract_month() {
		return contract_month;
	}

	public void setContract_month(String contract_month) {
		this.contract_month = contract_month;
	}

	public NiceInfoVO getNice_info() {
		return nice_info;
	}

	public void setNice_info(NiceInfoVO nice_info) {
		this.nice_info = nice_info;
	}

    public String getMainNumber() {
        return mainNumber;
    }

    public void setMainNumber(String mainNumber) {
        this.mainNumber = mainNumber;
    }
	
}
