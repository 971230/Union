package com.ztesoft.net.mall.core.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.adminuser.req.MessageReq;
import services.MessageInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.Message;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.EncryptionUtil1;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.plugin.member.MemberPluginBundle;
import com.ztesoft.net.mall.core.service.IMemberLvManager;
import com.ztesoft.net.mall.core.service.IMemberManager;
import com.ztesoft.net.mall.core.service.IMemberPointManger;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

/**
 * 会员管理
 * @author kingapex
 *2010-4-30上午10:07:24
 */
public class MemberManager extends BaseSupport<Member> implements IMemberManager{
 
	protected IMemberLvManager memberLvManager;
	private IMemberPointManger memberPointManger;
	
	private MemberPluginBundle memberPluginBundle;
	private static Logger logger = Logger.getLogger(MemberManager.class);
	@Override
	public boolean checkmobile(String mobile){
		String sql = "select m.member_id,m.uname from es_member m where m.mobile=?";
		return this.baseDaoSupport.queryForList(sql, mobile).size()>0;
	}
	
	@Override
	public void logout(){
		  Member member = UserServiceFactory.getUserService().getCurrentMember();
		  member = this.get(member.getMember_id());
		  ThreadContextHolder.getSessionContext().removeAttribute(IUserService.CURRENT_MEMBER_KEY);
		  this.memberPluginBundle.onLogout(member);
		  
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED)  
	public int register(Member member) {
		
		int result = add(member);
		this.memberPluginBundle.onRegister(member);
		
		return result;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public int addMember(Member member) {
		
		if(member==null) throw new IllegalArgumentException("member is null");
		if(member.getUname()==null) throw new IllegalArgumentException("member uname is null");
		
		//EopSite eopSite = EopContext.getContext().getCurrentSite();
		//String theme_path = eopSite.getThemepath();
		
		//add by wui
		if(member.getPassword() ==null) throw new IllegalArgumentException("member' password is null");
		
		if(!StringUtil.isMobile())
		//if(member.getEmail()==null) throw new IllegalArgumentException("member'email is null");
		
		if(this.checkname( member.getUname()) ==1){
			return 0;
		}
		
		//String lvid  = memberLvManager.getDefaultLv();
		//member.setLv_id(lvid);
		
		//member.setName(member.getUname());
		//member.setSex(0);
		
		//member.setProvince_id(0);
		//member.setCity_id(0);
		//member.setRegion_id(0);
		member.setPoint(0);
		member.setAdvance(0D);
		member.setRegtime(DBTUtil.current());
//		member.setLastlogin(DBTUtil.current());
		member.setLogincount(0);
		member.setPassword(StringUtil.md5(member.getPassword()));
		
		
		this.baseDaoSupport.insert("member", member);
		String memberid  = member.getMember_id();
		member.setMember_id(memberid);

		
		return 1;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public int add(Member member) {
		
		if(member==null) throw new IllegalArgumentException("member is null");
		if(member.getUname()==null) throw new IllegalArgumentException("member uname is null");
		
		//EopSite eopSite = EopContext.getContext().getCurrentSite();
		//String theme_path = eopSite.getThemepath();
		
		//add by wui
		if(member.getPassword() ==null) throw new IllegalArgumentException("member' password is null");
		
		if(!StringUtil.isMobile())
		//if(member.getEmail()==null) throw new IllegalArgumentException("member'email is null");
		
	    //可以存在同一个用户名 订单归集时使用 2014-9-24
		/*if(this.checkname( member.getUname()) ==1){
			return 0;
		}*/
		
		//String lvid  = memberLvManager.getDefaultLv();
		//member.setLv_id(lvid);
		
		//member.setName(member.getUname());
		//member.setSex(0);
		
		//member.setProvince_id(0);
		//member.setCity_id(0);
		//member.setRegion_id(0);
		member.setPoint(0);
		member.setAdvance(0D);
		member.setRegtime(DBTUtil.current());//lzf add
		member.setLastlogin(DBTUtil.current());
		member.setLogincount(1);
		member.setPassword(StringUtil.md5(member.getPassword()));
		
		
		this.baseDaoSupport.insert("member", member);
		String memberid  = member.getMember_id();
		member.setMember_id(memberid);

		
		return 1;
	}



	
	
	@Override
	public Member checkEmail(String checkStr) {
		
		String str = EncryptionUtil1.authcode(checkStr, "DECODE","",0);
		String[] array = str.split(",");
		if(array.length!=2) throw new RuntimeException("验证字串不正确");
		String memberid  = array[0];
		String regtime = array[1];
		
		Member member = this.get(memberid);
		if(!member.getRegtime().equals(regtime)){
			throw new RuntimeException("验证字串不正确");
		}
		if(member.getIs_cheked()==1){
			return member;
		}
		
		

		String sql = SF.memberSql("SERVICE_UPDATE_MEMBER_BY_ID");
		this.baseDaoSupport.execute(sql, memberid);
		this.memberPluginBundle.onEmailCheck(member);
		return member;
		
	}

	@Override
	public Member get(String memberId) {
		
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LIST_BY_MEMBER_ID");
	    Member m = new Member();
	    m = this.baseDaoSupport.queryForObject(sql, Member.class, memberId);
	    return m;
	}

	
	@Override
	public Member getMemberByUname(String uname) {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_BY_NAME");
	    List list = this.baseDaoSupport.queryForList(sql, Member.class, uname);
	    Member m = null;
	    if(list!=null && list.size()>0){
	    	m = (Member)list.get(0);
	    }
	    return m;
	}
	
	@Override
	public Member getMemberByMobile(String mobile) {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_BY_MOBILE");
	    List list = this.baseDaoSupport.queryForList(sql, Member.class, mobile);
	    Member m = null;
	    if(list!=null && list.size()>0){
	    	m = (Member)list.get(0);
	    }
	    return m;
	}
	
	public Member getMemberByEmail(String email){
		String sql = SF.memberSql("SERVICE_GET_MEMBER_BY_EMAIL");
	    List list = this.baseDaoSupport.queryForList(sql, Member.class, email);
	    Member m = null;
	    if(list!=null && list.size()>0){
	    	m = (Member)list.get(0);
	    }
	    return m;
	}

	
	@Override
	public Member edit(Member member) {
		//前后台用到的是一个edit方法，请在action处理好
		Map editMem = new HashMap();
		setMap(editMem,member);
		this.baseDaoSupport.update("es_member", editMem, "member_id="
				+ member.getMember_id());
		ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		return null;
	}

    private void setMap(Map editMem,Member member) {
    	if(!StringUtil.isEmpty(member.getLastlogin())){
    		editMem.put("lastlogin", member.getLastlogin());
    	}
    	if(!StringUtil.isEmpty(String.valueOf(member.getLogincount()))){
    		editMem.put("logincount", member.getLogincount());
    	}
		if(!StringUtil.isEmpty(String.valueOf(member.getPoint()))){
			editMem.put("point", member.getPoint());
		}
		if(!StringUtil.isEmpty(String.valueOf(member.getAdvance()))){
			editMem.put("advance", member.getAdvance());
		}
		if(!StringUtil.isEmpty(member.getName())){
			editMem.put("name", member.getName());
		}
		if(!StringUtil.isEmpty(member.getBirthday())){
			editMem.put("sex", member.getSex());
		}
		if(!StringUtil.isEmpty(member.getBirthday())){
			editMem.put("birthday", member.getBirthday());
		}
		if(!StringUtil.isEmpty(String.valueOf(member.getProvince_id()))){
			editMem.put("province_id", member.getProvince_id());
		}
		if(!StringUtil.isEmpty(String.valueOf(member.getCity_id()))){
			editMem.put("city_id", member.getCity_id());
		}
		if(!StringUtil.isEmpty(String.valueOf(member.getRegion_id()))){
			editMem.put("region_id", member. getRegion_id());
		}
		if(!StringUtil.isEmpty(member.getProvince())){
			editMem.put("province", member.getProvince());
		}
		if(!StringUtil.isEmpty(member.getCity())){
			editMem.put("city", member.getCity());
		}
		if(!StringUtil.isEmpty(String.valueOf(member.getRegion_id()))){
			editMem.put("region", member. getRegion_id());
		}
		if(!StringUtil.isEmpty(member.getAddress())){
			editMem.put("address", member.getAddress());
		}
		if(!StringUtil.isEmpty(member.getZip())){
			editMem.put("zip", member.getZip());
		}
		if(!StringUtil.isEmpty(member.getMobile())){
			editMem.put("mobile", member. getMobile());
		}
		if(!StringUtil.isEmpty(member.getTel())){
			editMem.put("tel", member.getTel());
		}
		if(!StringUtil.isEmpty(member.getPw_question())){
			editMem.put("pw_question", member.getPw_question());
		}
		if(!StringUtil.isEmpty(member.getPw_answer())){
			editMem.put("pw_answer", member.getPw_answer());
		}
		if(!StringUtil.isEmpty(member.getRemark())){
			editMem.put("remark", member.getRemark());
		}
		if(!StringUtil.isEmpty(member.getEmail())){
			editMem.put("email", member.getEmail());
		}
		if(!StringUtil.isEmpty(member.getPassword())){
			editMem.put("password", member.getPassword());
		}
		if(!StringUtil.isEmpty(member.getCol1())){
			editMem.put("col1", member.getCol1());
		}		
		if(!StringUtil.isEmpty(member.getCol2())){
			editMem.put("col2", member.getCol2());
		}
		
		if(!StringUtil.isEmpty(member.getCol3())){
			editMem.put("col3", member.getCol3());
		}
	}



	@Override
	public Member editSaveMember(Member member){
        Member m = new Member();
        String sql = SF.memberSql("SERVICE_GET_MEMBER_LIST_BY_MEMBER_ID");
        m = this.baseDaoSupport.queryForObject(sql, Member.class, member.getMember_id());
    	member.setPoint(m.getPoint());
		member.setAdvance(m.getAdvance());
		member.setRegtime(m.getRegtime());//lzf add
		member.setLastlogin(m.getLastlogin());
		member.setLogincount(m.getLogincount());
		member.setPassword(m.getPassword());
		
		this.baseDaoSupport.update("member", member,"member_id="+ member.getMember_id() );
		//this.baseDaoSupport.update("member", editMemMap, "member_id="
				//+ member.getMember_id());
		//ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
    	return null;
    }

	
	@Override
	public int checkname(String name) {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_COUNT_BY_NAME");
		int count = this.baseDaoSupport.queryForInt(sql, name);
		count = count > 0 ? 1 : 0;
		return count;
	}
	
	public int checkemail(String email) {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_COUNT_BY_EMAIL");
		int count = this.baseDaoSupport.queryForInt(sql, email);
		count = count > 0 ? 1 : 0;
		return count;
	}

	
	@Override
	public Page list(String order, int page, int pageSize) {
		order = order == null ? " m.member_id desc" : order;
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LIST_01");
		//sql += "  and lv.userid = "+this.getCurrentUserid()+" and lv.siteid="+this.getCurrentSiteid();
		sql += " order by  " + order;
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}
	
	
    @Override
	public void delete(String id){
		if (id == null || id.equals(""))
			return;
		String sql = SF.memberSql("SERVICE_DEL_MEMBER_BY_IDS") + " and member_id in (" + id +")";
		this.baseDaoSupport.execute(sql);
    }



 

	
	@Override
	public void updatePassword(String password) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		this.updatePassword(member.getMember_id(), password);
		ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		
	}
	

	
	@Override
	public void updatePassword(String memberid, String password) {
		password = password == null ? StringUtil.md5("") : StringUtil
				.md5(password);
		String sql = SF.memberSql("SERVICE_UPDATE_MEMBER_PASSWORD_BY_ID");
		this.baseDaoSupport.execute(sql,password,memberid);
	}
	
	@Override
	public void updatePassword1(String memberid, String password) {
		String sql = SF.memberSql("SERVICE_UPDATE_MEMBER_PASSWORD_BY_ID");
		this.baseDaoSupport.execute(sql,password,memberid);
	}
	
	public void updateFindCode(String memberid,String code){
		String sql = SF.memberSql("SERVICE_UPDATE_MEMBER_FIND_CODE_BY_ID");
		this.baseDaoSupport.execute(sql,code,memberid);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED) 
	public int login(String session_id,String username, String password) {
		//String sql = SF.memberSql("SERVICE_LOGIN_IN_BY_NAME");
        String sql="select m.*,l.name as lvname from es_member m left join es_member_lv l on (m.lv_id = l.lv_id and m.source_from = l.source_from) where " +
                "m.uname=? and password=? AND m.source_from is not null ";//m.source_from=?"; commented by Musoon 多应用用户公用登录，不需区分来源
		//用户名中包含@，说明是用邮箱登录的
		if(username.contains("@")){
			//sql = SF.memberSql("SERVICE_LOGIN_IN_BY_EMAIL");
            sql="select m.*,l.name as lvname from es_member m left join es_member_lv l on (m.lv_id = l.lv_id and m.source_from = l.source_from) " +
                    "where m.email=? and password=? AND m.source_from is not null ";//m.source_from=?"; commented by Musoon 多应用用户公用登录，不需区分来源
		}
        List param=new ArrayList();
        param.add(username);
        param.add(StringUtil.md5(password));
        //param.add(ManagerUtils.getSourceFrom());
		
		List<Member > list  =this.daoSupport.queryForList(sql, Member.class, param.toArray());

		if(list==null || list.isEmpty()  ){
			return 0;
		}
		 
		Member member = list.get(0);
		long ldate =DateFormatUtils.formatStringToDate(member.getLastlogin()).getTime();//  ((long)member.getLastlogin())*1000;
		Date date = new Date(ldate);
		Date today = new Date();
		int logincount = member.getLogincount();
		if(DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM"))){//与上次登录在同一月内
			logincount++;
		}else{
			logincount = 1;
		}
		String lastlogin = member.getLastlogin();
		member.setLastlogin(DBTUtil.current());
		member.setLogincount(logincount);
		this.edit(member);
		
		//线程变量、session中同时设置，放置出错
		member.setSessionId(session_id);
		ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		ThreadContextHolder.getCacheSessionContext(session_id).setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		
		this.memberPluginBundle.onLogin(member);
		
		//登录成功，根据需要发送站内短信
		sendSysMsg(date);
		member.setLastlogin(lastlogin);
		return 1;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED) 
	public int loginByMobile(String session_id,String mobile, String password) {
		//String sql = SF.memberSql("SERVICE_LOGIN_IN_BY_NAME");
		//此处共享用户，不能根据来源区分
        String sql="select m.*,l.name as lvname from es_member m left join es_member_lv l on (m.lv_id = l.lv_id and m.source_from = l.source_from) where " +
                "m.tel=? and password=? AND m.source_from is not null "; //AND m.source_from=?";  commented by Musoon 多应用共享用户，不能根据来源区分
		//用户名中包含@，说明是用邮箱登录的
        List param=new ArrayList();
        param.add(mobile);
        param.add(StringUtil.md5(password));
        //commented by MasterQin&Musoon
        //param.add(ManagerUtils.getSourceFrom());
		
		List<Member > list  =this.daoSupport.queryForList(sql, Member.class, param.toArray());

		if(list==null || list.isEmpty()  ){
			return 0;
		}
		 
		Member member = list.get(0);
		long ldate =DateFormatUtils.formatStringToDate(member.getLastlogin()).getTime();//  ((long)member.getLastlogin())*1000;
		Date date = new Date(ldate);
		Date today = new Date();
		int logincount = member.getLogincount();
		if(DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM"))){//与上次登录在同一月内
			logincount++;
		}else{
			logincount = 1;
		}
		String lastlogin = member.getLastlogin();
		member.setLastlogin(DBTUtil.current());
		member.setLogincount(logincount);
		this.edit(member);
		member.setSessionId(session_id);
		ThreadContextHolder.getHttpSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		ThreadContextHolder.getCacheSessionContext(session_id).setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		
		this.memberPluginBundle.onLogin(member);
		
		//登录成功，根据需要发送站内短信
		sendSysMsg(date);
		member.setLastlogin(lastlogin);
		return 1;
	}
	
	@Transactional(propagation = Propagation.REQUIRED) 
	public int loginWithCookie(String username, String password) {
		String sql = SF.memberSql("SERVICE_LOGIN_IN_BY_NAME");
		//用户名中包含@，说明是用邮箱登录的
		if(username.contains("@")){
			sql = SF.memberSql("SERVICE_LOGIN_IN_BY_EMAIL");
		}
		List<Member > list  =this.daoSupport.queryForList(sql, Member.class, username,password);
		if(list==null || list.isEmpty()  ){
			return 0;
		}
		 
		Member member = list.get(0);
		long ldate = DateFormatUtils.formatStringToDate(member.getLastlogin()).getTime();
		Date date = new Date(ldate);
		Date today = new Date();
		int logincount = member.getLogincount();
		if(DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM"))){//与上次登录在同一月内
			logincount++;
		}else{
			logincount = 1;
		}
		member.setLastlogin(DBTUtil.current());
		member.setLogincount(logincount);
		this.edit(member);
		ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		
		
		this.memberPluginBundle.onLogin(member);
 
		
		
		return 1;
	}

	
	/**
	 *系统管理员作为某个会员登录
	 */
	@Override
	public int loginbysys(String username) {

		 IUserService userService = UserServiceFactory.getUserService();
		 if(!userService.isUserLoggedIn()){
			 throw new  RuntimeException("您无权进行此操作，或者您的登录已经超时");
		 }
		 
		String sql = SF.memberSql("MEMBER_LOGIN_BY_SYS");
		sql += " and l.source_from =m.source_from and m.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		List<Member > list  =this.daoSupport.queryForList(sql, Member.class, username);
		if(list==null || list.isEmpty()  ){
			return 0;
		}
		 
		Member member = list.get(0);	
		ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		return 1;
	}
	
	@Override
	public int authLogin(String username) {
		String sql = SF.memberSql("MEMBER_LOGIN_BY_SYS");
		List<Member > list  =this.daoSupport.queryForList(sql, Member.class, username);
		if(list==null || list.isEmpty()  ){
			return 0;
		}
		Member member = list.get(0);	
		ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
		return 1;
	}

	
	
	public IMemberLvManager getMemberLvManager() {
		return memberLvManager;
	}

	public void setMemberLvManager(IMemberLvManager memberLvManager) {
		this.memberLvManager = memberLvManager;
	}

	
	@Override
	public void addMoney(String memberid, Double num) {
		String sql  = SF.memberSql("MEMBER_ADD_MONEY");
		this.baseDaoSupport.execute(sql, num,memberid);

	}
	
	@Override
	public void cutMoney(String memberid, Double num) {
		Member member  = this.get(memberid);	
		if(member.getAdvance()<num){
			throw new  RuntimeException("预存款不足:需要["+num+"],剩余["+member.getAdvance()+"]");
		}
		String sql  = SF.memberSql("MEMBER_CUT_MONEY");
		this.baseDaoSupport.execute(sql, num,memberid);
	}


	
	@Override
	public Page list(String order, String name, String uname, int page, int pageSize) {
		order = order == null ? " m.regtime desc" : order;
	//	String sql = "select m.*,lv.name as lv_name from "+this.getTableName("member")+" m left join " +this.getTableName("member_lv")+" lv on m.lv_id = lv.lv_id where 1=1";
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LIST");
		
		if(name!=null && !name.equals("")){
			sql += " and m.name like '%" + name + "%'";
		}
		if(uname!=null && !uname.equals("")){
			sql += " and m.uname like '%" + uname + "%'";
		}
		sql += " order by  " + order;
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}


	public IMemberPointManger getMemberPointManger() {
		return memberPointManger;
	}


	public void setMemberPointManger(IMemberPointManger memberPointManger) {
		this.memberPointManger = memberPointManger;
	}


	public static void main(String[] args){
		logger.info(DateUtil.toDate("2011-05-27", "yyyy-MM-dd").getTime());
	}


	@Override
	public void updateLv(String memberid, String lvid) {
		String sql = SF.memberSql("MEMBER_UPDATE_LV");
		this.baseDaoSupport.execute(sql, lvid,memberid);
	}
	public MemberPluginBundle getMemberPluginBundle() {
		return memberPluginBundle;
	}
	public void setMemberPluginBundle(MemberPluginBundle memberPluginBundle) {
		this.memberPluginBundle = memberPluginBundle;
	}
	
	/**
	 * 发送系统消息
	 */
	private void sendSysMsg(Date date){
		IUserService userService = UserServiceFactory.getUserService();
		Member m = userService.getCurrentMember();
		List msgList = checkTargetUser(m, date);
		if(msgList == null || msgList.size()==0)
			return;

		MessageInf messageServ = SpringContextHolder.getBean("messageServ");
		for(int i=0; i<msgList.size(); i++){
			Map msgMap = (Map)msgList.get(i);
			Message message = new Message();			
			message.setSubject(MapUtils.getString(msgMap, "codea"));
			message.setMessage(MapUtils.getString(msgMap, "coded"));
			message.setFrom_type("1");//系统消息
			
			message.setDate_line(DBTUtil.current());
			
			String returnmessage = "";
			try{
				if(m!=null){
					message.setTo_id(m.getMember_id());
					message.setMsg_to(m.getUname());
					MessageReq messageReq = new MessageReq();
					messageReq.setMessage(message);
					messageServ.addMessage(messageReq);
					
					returnmessage += "[" + m.getUname() + "] ";
				}
//				this.showSuccess("消息已发送给"+returnmessage,"发送消息", "member_message.html?action=send");
			}catch(Exception e){
				if(MemberManager.logger.isDebugEnabled()){
					logger.info(e.getStackTrace());
				}
//				this.showError("发送失败", "发送消息", "member_message.html?action=send");
			}
		}
	}
	
	/**
	 * 判断是否目标客户
	 * @return
	 */
	private List checkTargetUser(Member m, Date ldate){
		String sql = SF.memberSql("MEMBER_DC_PUBLIC");
		List list = this.daoSupport.queryForList(sql, 1000);
		List retList = new ArrayList();
		Map noLoginMsg = new HashMap();
		Map orderMsg = new HashMap();
		
		for(int i=0; list!=null && i<list.size(); i++){
			Map tmpMap = (Map)list.get(i);
			if("1".equals(tmpMap.get("pkey"))){
				noLoginMsg = tmpMap;
			}else if("2".equals(tmpMap.get("pkey"))){
				orderMsg = tmpMap;
			}
		}
		
		if(noLoginMsg.size()>0){
			Calendar c = Calendar.getInstance();
			c.setTime(ldate);
			int warnDays = MapUtils.getIntValue(noLoginMsg, "codeb", 0);
			c.add(Calendar.DAY_OF_YEAR, warnDays);
			if(warnDays > 0 && c.getTime().before(new Date())){
				retList.add(noLoginMsg);
			}
		}
		if(orderMsg.size()>0){
			Calendar c = Calendar.getInstance();
			c.setTime(ldate);
			int warnDays = MapUtils.getIntValue(orderMsg, "codeb", 0);
			c.add(Calendar.DAY_OF_YEAR, -warnDays);
			sql = SF.memberSql("MEMBER_CHECK_TARGET_USER");
			String dateTime = new SimpleDateFormat("yyyy-MM-dd hh：mm：ss").format(c.getTime());
			int unpay_count = this.daoSupport.queryForInt(sql, m.getMember_id(), dateTime);
			
			if(warnDays>0 && unpay_count>0){
				retList.add(orderMsg);
			}
		}
		
		return retList;
	}



	@Override
	public Page queryMemberPage(String uname, int pageNo, int pageSize) {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LIST");
		String countsql = SF.memberSql("SERVICE_GET_MEMBER_COUNT");
		if(!StringUtils.isEmpty(uname)){
			sql += " and upper(m.uname) like '%" + uname.trim().toUpperCase()+"%'";
			countsql += " and upper(m.uname) like '%" + uname.trim().toUpperCase()+"%'";
		}
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, Member.class, countsql);
	}



	@Override
	public List listOrderLog(String order_id) {
		// TODO Auto-generated method stub
		String sql = SF.memberSql("MEMBER_LIST_ORDERLOG");
		List list = null;
		if(order_id!=null && "".equals(order_id))
			list = this.baseDaoSupport.queryForList(sql, order_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}



	@Override
	public List listGiftByOrderId(String order_id) {
		// TODO Auto-generated method stub
		String sql  = SF.memberSql("MEMBER_LIST_GIFTITEMS");
		List list = null;
		if(order_id!=null && "".equals(order_id))
			list = this.baseDaoSupport.queryForList(sql, order_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}



	@Override
	public Page queryPointHistoryPage(String pointType, String member_id, int page_index,
			int page_size) {
		// TODO Auto-generated method stub
//		IUserService userService = UserServiceFactory.getUserService();
//		Member member = userService.getCurrentMember();
		String sql = SF.memberSql("MEMBER_PAGE_POINT_HISTORY");
		Page webpage = this.baseDaoSupport.queryForPage(sql, page_index, page_size,
				member_id,pointType);
		return webpage;
	}



	@Override
	public String queryConsumePoint(String pointType,String member_id) {
		// TODO Auto-generated method stub
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		
		//select SUM(point) from point_history where  member_id = ?  and  type=0  and point_type=?
		String sql = SF.memberSql("MEMBER_GET_CONSUME_POINT");
		Long result = this.baseDaoSupport.queryForLong(sql, member.getMember_id(), pointType);
		return result+"";
	}



	@Override
	public String queryGainPoint(String pointType,String member_id) {
		// TODO Auto-generated method stub
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		//select SUM(point) from point_history where    member_id = ? and  type=1  and point_type=?
		String sql = SF.memberSql("MEMBER_GET_GAINED_POINT");
		Long result = this.baseDaoSupport
				.queryForLong(sql, member.getMember_id(), pointType);
		return result+"";
	}



	@Override
	public boolean isBuy(String goods_id) {
		// TODO Auto-generated method stub
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		if(member==null) return false;
		String sql  = SF.memberSql("MEMBER_IS_BUY");
		int count  = this.daoSupport.queryForInt(sql, member.getMember_id(),goods_id);
		return count>0;
	}

}
