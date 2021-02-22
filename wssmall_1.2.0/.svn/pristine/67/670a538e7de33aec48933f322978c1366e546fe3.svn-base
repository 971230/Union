package params.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class PartnerWdAddReq extends  ZteRequest{
	@ZteSoftCommentAnnotationParam(name="用户名",type="String",isNecessary="Y",desc="用户登录名称")
	private String username;//账户名
	@ZteSoftCommentAnnotationParam(name="密码",type="String",isNecessary="Y",desc="用户登录密码")
	private String password;//密码
	@ZteSoftCommentAnnotationParam(name="图片",type="String",isNecessary="Y",desc="分销商图片")
    private String partner_image;//分销商的图片
    private String partner_ship_image;//分销商店铺图片
    @ZteSoftCommentAnnotationParam(name="店铺名称",type="String",isNecessary="Y",desc="店铺名称")
    private String partner_shop_name;//店铺名称
    @ZteSoftCommentAnnotationParam(name="店铺地址",type="String",isNecessary="Y",desc="店铺地址")
    private String partner_shop_address;//店铺地址
	@ZteSoftCommentAnnotationParam(name="分销商账户",type="String",isNecessary="Y",desc="分销商账户:account_code=p,account_name=分销商账户;只需送partner_id,account_id")
    private PartnerAccount primaryAccount;
	@ZteSoftCommentAnnotationParam(name="分销商子账户",type="String",isNecessary="Y",desc="分销商子账户:account_code=s,account_name=分销商分账户;只需送partner_id,account_id")
    private PartnerAccount secondaryAccount;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(username))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户登录名称不允许为空"));
		if(StringUtils.isEmpty(password))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户登录密码不允许为空"));
		//if(StringUtils.isEmpty(partner_image))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "分销商图片不允许为空"));
		if(StringUtils.isEmpty(partner_shop_name))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "店铺名称不允许为空"));
		//if(StringUtils.isEmpty(partner_shop_address))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "店铺地址不允许为空"));
		   
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.service.addPartnerWd";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPartner_image() {
		return partner_image;
	}

	public void setPartner_image(String partner_image) {
		this.partner_image = partner_image;
	}

	public String getPartner_ship_image() {
		return partner_ship_image;
	}

	public void setPartner_ship_image(String partner_ship_image) {
		this.partner_ship_image = partner_ship_image;
	}

	public String getPartner_shop_name() {
		return partner_shop_name;
	}

	public void setPartner_shop_name(String partner_shop_name) {
		this.partner_shop_name = partner_shop_name;
	}

	public String getPartner_shop_address() {
		return partner_shop_address;
	}

	public void setPartner_shop_address(String partner_shop_address) {
		this.partner_shop_address = partner_shop_address;
	}

	public PartnerAccount getPrimaryAccount() {
		return primaryAccount;
	}

	public void setPrimaryAccount(PartnerAccount primaryAccount) {
		this.primaryAccount = primaryAccount;
	}

	public PartnerAccount getSecondaryAccount() {
		return secondaryAccount;
	}

	public void setSecondaryAccount(PartnerAccount secondaryAccount) {
		this.secondaryAccount = secondaryAccount;
	}

}
