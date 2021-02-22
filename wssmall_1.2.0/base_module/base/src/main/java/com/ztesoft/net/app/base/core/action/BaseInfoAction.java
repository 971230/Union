package com.ztesoft.net.app.base.core.action;

import com.ztesoft.net.eop.resource.IUserManager;
import com.ztesoft.net.eop.resource.model.EopUser;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;

import java.io.File;

/**
 * User基本信息维护
 * 
 * @author lzf
 *         <p>
 *         created_time 2009-12-4 上午10:37:58
 *         </p>
 * @version 1.0
 */
public class BaseInfoAction extends WWAction {

	private EopUser eopUser;
	private String userid;

	private IUserManager userManager;
	private File cologo;	
	public String toEmptyUrl(){
		return WWAction.JSON_MESSAGE;
	}
	public File getCologo() {
		return cologo;
	}
    
	public void setCologo(File cologo) {
		this.cologo = cologo;
	}

	public String getCologoFileName() {
		return cologoFileName;
	}

	public void setCologoFileName(String cologoFileName) {
		this.cologoFileName = cologoFileName;
	}

	private String cologoFileName;
	private File license;		//营业执照
	private String licenseFileName;

	public File getLicense() {
		return license;
	}

	public void setLicense(File license) {
		this.license = license;
	}

	public String getLicenseFileName() {
		return licenseFileName;
	}

	public void setLicenseFileName(String licenseFileName) {
		this.licenseFileName = licenseFileName;
	}
 

	public EopUser getEopUser() {
		return eopUser;
	}

	public void setEopUser(EopUser eopUser) {
		this.eopUser = eopUser;
	}

 
	
	@Override
	public String execute() throws Exception {
		this.userid =EopContext.getContext().getCurrentSite().getUserid();
		eopUser = userManager.get(userid);
		return "input";
	}

	public String save() throws Exception {

		
		try {
			if (cologo != null) {
				String logoPath = UploadUtil.upload(cologo, cologoFileName,
						"user");
				eopUser.setLogofile(logoPath);

			}

			if (license != null) {

				String licensePath = UploadUtil.upload(license,
						licenseFileName, "user");
				eopUser.setLicensefile(licensePath);

			}

			this.userManager.edit(eopUser);
			this.msgs.add("修改成功");
			
		} catch (RuntimeException e) {
			this.msgs.add(e.getMessage());
		}
		this.urls.put("用户信息页面", "baseInfo.do");
		return "message";
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public IUserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}

	
}
