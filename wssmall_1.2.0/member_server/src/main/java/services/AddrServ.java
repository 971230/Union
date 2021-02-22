package services;

import java.util.List;

import params.ZteError;
import params.addr.req.AddrListReq;
import params.addr.req.AddrReq;
import params.addr.resp.AddrListResp;
import params.addr.resp.AddrResp;
import params.regions.req.RegionCityListByProvinceReq;
import params.regions.req.RegionListByCityReq;
import params.regions.req.RegionProvinceListReq;
import params.regions.resp.RegionCityListByProvinceResp;
import params.regions.resp.RegionListByCityResp;
import params.regions.resp.RegionProvinceListResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberAddress;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IMemberAddressManager;
import com.ztesoft.net.mall.core.service.IMemberManager;
import com.ztesoft.net.mall.core.service.IRegionsManager;
import commons.CommonTools;

import consts.ConstsCore;

public class AddrServ extends ServiceBase implements AddrInf{

	private IMemberManager memberManager;
	private IMemberAddressManager memberAddressManager;
	private IRegionsManager regionsManager;
	
	/**
	 * 查询用户列表
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-24 
	 * @param json
	 */
	@Override
	public AddrListResp listAddress(AddrListReq cp){
		try{
			AddrListResp op = new AddrListResp();
			Member member = CommonTools.getLoginMember();
			//if(cp.getMember_id()==null || "".equals(cp.getMember_id())) CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"会员ID有误"));
			//Member member = CommonTools.getLoginMember();
			if(member==null && cp.getMember_id()!=null && !"".equals(cp.getMember_id()))
				member = memberManager.get(cp.getMember_id());
			if(member==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"你尚未登录，请先登录！"));
			//计置当当登录用户信息，查询列表时可以获取到当前登录用户
			ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY,member);
			List addressList =memberAddressManager.listAddress();
			op.setAddressList(addressList);
			addReturn(cp,op);
			return op;
		}catch(RuntimeException ex){
			throw ex;
		}catch(Exception ex){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"系统繁忙"));
			return null;
		}
	}

	/**
	 * 添加地址
	 */
	@Override
	public AddrResp addOrEdit(AddrReq req) {
		try{
			AddrResp resp = new AddrResp();
			MemberAddress md = req.getAddress();
			if(StringUtil.isEmpty(req.getAction())) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Action不能为空"));
			Member member = CommonTools.getLoginMember();
			if(member==null)CommonTools.addError(new ZteError(ConstsCore.NOT_LOGIN,"没有登录"));
			//计置当当登录用户信息，查询列表时可以获取到当前登录用户
			ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY,member);
			if("delete".equals(req.getAction())){
				if(StringUtil.isEmpty(md.getAddr_id()) || "0".equals(md.getAddr_id())) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Addr_id不能为空"));
				memberAddressManager.deleteAddress(md.getAddr_id());
				resp.setError_code("0");
				resp.setError_msg("成功");
				addReturn(req,resp);
				return resp;
			}
			if(req.getAddress()==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Address不能为空"));
			//if(StringUtil.isEmpty(md.getMember_id()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Member_id不能为空"));
			if(StringUtil.isEmpty(md.getName()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Name不能为空"));
			if(md.getProvince_id()==0)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Province_id不能为空"));
			if(md.getCity_id()==0)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"City_id不能为空"));
			if(md.getRegion_id()==0)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Region_id不能为空"));
			if(StringUtil.isEmpty(md.getAddr()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Addr不能为空"));
			//if(StringUtil.isEmpty(md.getZip()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Zip不能为空"));
			//if(StringUtil.isEmpty(md.getMobile()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"Mobile不能为空"));
			if(StringUtil.isEmpty(md.getProvince()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Province不能为空"));
			if(StringUtil.isEmpty(md.getCity()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"City不能为空"));
			if(StringUtil.isEmpty(md.getRegion()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Region不能为空"));
			md.setMember_id(member.getMember_id());
			md.setLast_update(DBTUtil.current());
			if("add".equals(req.getAction())){
				md.setDef_addr(0);
				memberAddressManager.addAddress(md);
			}else if("edit".equals(req.getAction())){
				if(StringUtil.isEmpty(md.getAddr_id())) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Addr_id不能为空"));
				memberAddressManager.updateAddress(md);
			}
			resp.setAddress(md);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(req,resp);
			return resp;
		}catch(RuntimeException ex){
			throw ex;
		}catch(Exception ex){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"系统繁忙"));
			return null;
		}
	}

	public IMemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(IMemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public IMemberAddressManager getMemberAddressManager() {
		return memberAddressManager;
	}

	public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
		this.memberAddressManager = memberAddressManager;
	}

	/**
	 * 获取地市数据
	 */
	@SuppressWarnings("unchecked")
	public RegionCityListByProvinceResp listCity(RegionCityListByProvinceReq req) {
		RegionCityListByProvinceResp resp = new RegionCityListByProvinceResp();
		List<Regions> regions = regionsManager.listCity(req.getProvince_id());
		resp.setChild_list(regions);
		addReturn(req, resp);
		return resp;
	}

	/**
	 * 获取省份数据
	 */
	public RegionProvinceListResp listProvince(RegionProvinceListReq req) {
		RegionProvinceListResp resp = new RegionProvinceListResp();
		List<Regions> regions = regionsManager.listProvince();
		resp.setProvince_list(regions);
		addReturn(req, resp);
		return resp;
	}
	
	/**
	 * 获取县区数据
	 */
	public RegionListByCityResp listRegion(RegionListByCityReq req) {
		RegionListByCityResp resp = new RegionListByCityResp();
		List<Regions> regions = regionsManager.listRegion(req.getCity_id());
		resp.setChild_list(regions);
		addReturn(req, resp);
		return resp;
	}

	public IRegionsManager getRegionsManager() {
		return regionsManager;
	}

	public void setRegionsManager(IRegionsManager regionsManager) {
		this.regionsManager = regionsManager;
	}
	
}
