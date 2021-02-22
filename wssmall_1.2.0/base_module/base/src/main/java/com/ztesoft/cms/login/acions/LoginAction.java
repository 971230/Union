package com.ztesoft.cms.login.acions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.framework.servlet.SystemInit;
import com.powerise.ibss.util.Md5Tool;
import com.ztesoft.cms.login.vo.TsmStaff;
import com.ztesoft.cms.page.query.CmsConst;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.TEA;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.SqlMapExe;
import com.ztesoft.ibss.ct.config.CTConfig;
//import com.ztesoft.common.util.WebUtils;

/*
* insert into tfm_services(service_name,module_id,service_desc,class_name)
values('LoginAction','WT','登陆','com.ztesoft.cms.login.acions.LoginAction')
* **/
public class LoginAction implements IAction {
    private Logger log=Logger.getLogger(LoginAction.class);
    private SqlMapExe sqlMapExe=null;

    public static class KEY{
        public static final String CURRENT_TIME="CURRENT_TIME";//针对随机密码

        public static final String LOGIN_PHONE="LOGIN_PHONE";//针对随机密码

        public static final String GET_PHONE="GET_PHONE";//针对随机密码

        public static final String RANDOM_PWD="RANDOM_PWD";//随机密码
        public static final String LOGIN="LOGIN";
        public static final String LOGIN_OUT="LOGIN_OUT";
        public static final String VERIFY_LIST="VERIFY_LIST";
        //当前用户
        public static final String CURRENT_USER="currentUser";
        //用户角色
        public static final String CURRENT_USER_ROLE="currentUserRole";
        //用户功能点
        public static final String CURRENT_USER_FUNCTIONS="currentUserFunctions";
        //待审核列表
        public static final String CURRENT_USER_VERIFY_LIST="currentUserVerifyList";
        //是否展示状态切换按钮
        public static final String TOGGLE="toggle";
        //分页待审核列表
        public static final String CURRENT_USER_PAGE_VERIFY_LIST="currentUserPageVerifyList";
        //待审核数
        public static final String CURRENT_USER_VERIFY_SIZE="CURRENT_USER_VERIFY_SIZE";

        //是否显示 more
        public static final String SHOW_MORE="SHOW_MORE";


        //是否显示待审核列表按钮
        public static final String SHOW_VERIFY_LIST_BUTTON="SHOW_VERIFY_LIST_BUTTON";
    }
    //
    private void verifyList(DynamicDict dict)throws Exception{
        String staff_no=dict.getValueByNameEx("STAFF_NO");
        List list=null;
        if(StringUtils.isNotEmpty(staff_no)){
            list=getUserVerifyList(staff_no);
        }
        dict.setValueByName(KEY.CURRENT_USER_VERIFY_LIST,list);
    }

    @Override
	public int perform(DynamicDict dict) throws FrameException {
         sqlMapExe=new SqlMapExe(dict.GetConnection());
         String method=dict.getValueByNameEx("METHOD");
         if(StringUtils.isNotEmpty(method)){
             if(method.equals(KEY.LOGIN)){
                 loginJudge(dict);
             }else if(method.equals(KEY.LOGIN_OUT)){
                 logout(dict);
             }else if(method.equals(KEY.VERIFY_LIST)){
                 getUserVerifyList(dict);
             }else if(method.equals(KEY.CURRENT_USER_PAGE_VERIFY_LIST)){
                 getUserPageVerifyList(dict);
             }else if(method.equals(KEY.LOGIN_PHONE)){
                 loginJudge(dict);
             }else if(method.equals(KEY.GET_PHONE)){
                 getPhone(dict);
             }
         }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void loginJudge(DynamicDict dict)throws FrameException{
        if(judgeRepeatLogin()){ //重复登录
            dict.setValueByName("RESULT",String.valueOf(false));
            dict.setValueByName("REPEAT_LOGIN",String.valueOf(true));
            return;
        }
        if(CTConfig.getInstance().getValue("TEST_MODE").equals("T")){
        	login(dict);
        }else{
        	loginPhone(dict);
        }
    }

    //获取绑定手机号
    private void getPhone(DynamicDict dict)throws FrameException{
        boolean rval=false;
        String userName=dict.getValueByNameEx("USER_NAME");//用户名
        if(StringUtils.isNotEmpty(userName)){
            StringBuffer buffer=new StringBuffer("select staff_no,bureau_no,staff_name ,password,state,staff_code,rela_phone from tsm_staff where staff_code=? and state=1 ");
            List result=sqlMapExe.queryForMapListBySql(buffer.toString(), new String[]{userName});
            if(result.size()>0){
                Map map=(Map)result.get(0);
                String rela_phone=(String)map.get("rela_phone");
                if(StringUtils.isNotEmpty(rela_phone)){
                    dict.setValueByName("SIGN",String.valueOf(true));
                    dict.setValueByName("PHONE",rela_phone);
                }
            }
        }
        dict.setValueByName("RESULT",String.valueOf(rval));
    }

    private void loginPhone(DynamicDict dict)throws FrameException{
    	//检查IP
    	HttpServletRequest request = ContextHelper.getRequest();
    	if(!SystemInit.checkAdminIp(request))return;
    	
        String userName=dict.getValueByNameEx("USER_NAME");//用户名
        String pwd=dict.getValueByNameEx("PASSWORD");//随机密码
        boolean rval=false;
        log.debug("随机密码登陆=======>"+userName+" : "+pwd);
        StringBuffer buffer=new StringBuffer("select staff_no,bureau_no,staff_name ,password,state,staff_code,rela_phone from tsm_staff where staff_code=?");
        List result=sqlMapExe.queryForMapListBySql(buffer.toString(), new String[]{userName});
        if(result.size()>0){
            Map map=(Map)result.get(0);
            String state=(String)map.get("state");
            String rela_phone=(String)map.get("rela_phone");
            if(StringUtils.isNotEmpty(state) && !state.equals("1")){//判断用户是否有效
                dict.setValueByName("RESULT",String.valueOf(rval));
                dict.setValueByName("LOCK",String.valueOf(rval));
                return;
            }
            if(StringUtils.isEmpty(rela_phone)){//手机号未绑定
                dict.setValueByName("RESULT",String.valueOf(rval));
                dict.setValueByName("RESULT_PHONE",String.valueOf(rval));
                return;
            }
            //获取随机密码
            HttpSession session=ContextHelper.getSession();
            //String randomPwd=WebUtils.getRandomPwd(session);
            String randomPwd=getPwd(rela_phone);
            if(StringUtils.isNotEmpty(pwd) && StringUtils.isNotEmpty(randomPwd) && pwd.equals(randomPwd)){//登录成功
                TsmStaff staff=new TsmStaff();
                staff.setStaff_no((String)map.get("staff_no"));
                staff.setStaff_name((String)map.get("staff_name"));
                staff.setState((String)map.get("state"));
                staff.setStaff_code((String)map.get("staff_code"));

                session.removeAttribute(KEY.RANDOM_PWD);//销毁随机密码

                //初始化数据
                rval=init(staff);
            }
        }
        dict.setValueByName("RESULT",String.valueOf(rval));
    }

    private String getPwd(String phone){
        String pwd=null;
        if(StringUtils.isNotEmpty(phone)){
            StringBuffer buffer=new StringBuffer("select send_no,send_num,recv_num,send_content,acct_num,create_time,sysdate,(sysdate-create_time)* 24 * 60 * 60 * 1000 long_time,state from twb_send_sms_his\n" +
                    "where recv_num=? and send_no=(\n" +
                    "select max(send_no) from twb_send_sms_his where recv_num=?)");

            List list=sqlMapExe.queryMapList(buffer.toString(),new String[]{phone,phone});
            if(null!=list && list.size()>0){
                Map map=(HashMap)list.get(0);
                if(null!=map.get("acct_num") && null!=map.get("long_time")){
                    String acct_num=String.valueOf(map.get("acct_num"));
                    String long_time=String.valueOf(map.get("long_time"));
                    String state=String.valueOf(map.get("state"));
                    String send_no=String.valueOf(map.get("send_no"));
                    if(StringUtils.isNotEmpty(acct_num) && StringUtils.isNotEmpty(long_time) && StringUtils.isNotEmpty(send_no) && StringUtils.isNotEmpty(state) && state.equals("0")){
                        long result=new Long(long_time).longValue();
                        log.debug("毫秒时间=====》"+result);
                        if(result<30*60*1000){
                            //修改状态值 标识此密码已使用过
                            StringBuffer updateSql=new StringBuffer("update twb_send_sms_his set state='1' where send_no=?");
                            sqlMapExe.excuteUpdate(updateSql.toString(),new String[]{send_no});
                            pwd=acct_num;
                        }
                    }
                }
            }
        }
        return pwd;
    }

    private boolean init(TsmStaff staff){
        boolean rval=false;
        try{
            HttpSession session = ContextHelper.getSession();
            session.setAttribute(CmsConst.CMS_SESSION_KEY, CmsConst.CMS_STATE_OFFLINE);//状态
            session.setAttribute(KEY.CURRENT_USER, staff);//当前用户
//            session.setAttribute(KEY.TOGGLE,String.valueOf(checkAdmin()));//是否超级管理员
            session.setAttribute(KEY.TOGGLE,String.valueOf(true));//是否超级管理员
            //角色
            List list=getRoles(staff.getStaff_no());
            if(null!=list && list.size()>0){
                session.setAttribute(KEY.CURRENT_USER_ROLE,list);
            }

            //功能点
            List vaList=getFunctions(staff.getStaff_no());
            if(null!=vaList && vaList.size()>0){
                session.setAttribute(KEY.CURRENT_USER_FUNCTIONS,vaList);
            }

            //待审核列表
            List verifyList=getUserVerifyList(staff.getStaff_no());
            if(null!=verifyList && verifyList.size()>0){
                session.setAttribute(KEY.SHOW_VERIFY_LIST_BUTTON,String.valueOf(true));
                if(vaList.size()>5){
                    session.setAttribute(KEY.SHOW_MORE,String.valueOf(true));
                }
                session.setAttribute(KEY.CURRENT_USER_VERIFY_LIST,verifyList);
            }
            rval=true;
        }catch (FrameException e){
            e.printStackTrace();
        }finally {
            return rval;
        }
    }

    private void logout(DynamicDict dict)throws FrameException{
        HttpSession session = ContextHelper.getSession();
        boolean rval=false;
        if(null!=session){
            session.invalidate();
            rval=true;
        }
       dict.setValueByName("RESULT",String.valueOf(rval));
    }

    private void login(DynamicDict dict)throws FrameException{
        String userName=dict.getValueByNameEx("USER_NAME");
        String pwd=dict.getValueByNameEx("PASSWORD");
        boolean rval=false;

        log.debug("登陆=======>"+userName+" : "+pwd);

        StringBuffer buffer=new StringBuffer("select staff_no,bureau_no,staff_name ,password,state,staff_code from tsm_staff where staff_code=?");
        List result=sqlMapExe.queryForMapListBySql(buffer.toString(), new String[]{userName});
        if(result.size()>0){
            Map map=(Map)result.get(0);
            String password= (String)map.get("password");
            String state=(String)map.get("state");
            if(StringUtils.isNotEmpty(state) && !state.equals("1")){//判断用户是否有效
                dict.setValueByName("RESULT",String.valueOf(rval));
                dict.setValueByName("LOCK",String.valueOf(rval));
                return;
            }

            //加密密碼
            if(StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(pwd)){       
                String pwd1 = TEA.Encrypt(pwd);                
                String pwd2 = Md5Tool.getHashString(pwd);                
                if(password.equals(pwd1)||password.equals(pwd2)){         
                    TsmStaff staff=new TsmStaff();
                    staff.setStaff_no((String)map.get("staff_no"));
                    staff.setStaff_name((String)map.get("staff_name"));
                    staff.setState((String)map.get("state"));
                    staff.setStaff_code((String)map.get("staff_code"));

                    rval=init(staff);
                }
            }
        }
        dict.setValueByName("RESULT",String.valueOf(rval));
    }

    //判断用户是否已登录过
    private boolean judgeRepeatLogin(){
        boolean rval=false;
        HttpSession session = ContextHelper.getSession();
        if(null!=session){
            Object obj=session.getAttribute(KEY.CURRENT_USER);
            if(null!=obj)rval=true;
        }
        return rval;
    }

    private boolean checkAdmin(){
        boolean rval=false;
        String sign=  CTConfig.getInstance().getValue("SUPER_STAFF_CODE");  ////超级管理员
        if(StringUtils.isNotEmpty(sign)){
            String items[]=sign.split("\\,");
            HttpSession session = ContextHelper.getSession();
            if(null!=session){
                Object obj=session.getAttribute(KEY.CURRENT_USER);
                if(null!=obj){
                    TsmStaff staff=(TsmStaff)obj;
                    String item=null;
                    for(int i=0;i<items.length;i++){
                        item=items[i];
                        if(StringUtils.isEmpty(item))continue;
                        if(staff.getStaff_no().equals(item)){
                            rval=true;
                            break;
                        }
                    }
                }
            }
        }
        return rval;
    }
    
    /*
     * 获取用户角色
     * @param 用戶ID
     * */
    private List getRoles(String staff_no)throws FrameException{
    	List list=null;
    	StringBuffer buffer=new StringBuffer("select distinct t.role_id,role_name from tsm_role t,tsm_staff_role s \n" +
                "where t.role_id=s.role_id and s.staff_no=? order by role_id desc");
        if(checkAdmin()){
            buffer=new StringBuffer("select role_id,role_name from tsm_role order by role_id desc");
            list=sqlMapExe.queryForMapList(buffer.toString());
        }else {
            list=sqlMapExe.queryForMapListBySql(buffer.toString(), new String[]{staff_no});
        }
    	return list;
    }

    private List getRolesFuns(String staff_no)throws FrameException{
        List list=null;
        StringBuffer buffer=new StringBuffer("select distinct c.role_id,role_name,staff_no,role_type,state from tsm_role t,\n" +
                "tsm_role_cms c where t.role_id=c.role_id and t.staff_no=?");
        if(checkAdmin()){
             buffer=new StringBuffer("select distinct c.role_id,role_name,staff_no,role_type,state from tsm_role t,tsm_role_cms c where t.role_id=c.role_id");
             list=sqlMapExe.queryForMapList(buffer.toString());
        }else {
            list=sqlMapExe.queryForMapListBySql(buffer.toString(), new String[]{staff_no});
        }
        return list;
    }

    
    /*
     * 获取用户功能点
     * */
    private  List getFunctions(String staff_no){
    	List list=null;
    	StringBuffer buffer=new StringBuffer("select a.fun_id,fun_name,fun_type,show_cols,edit_cols,visit_url,b.role_type from cms_fun a,tsm_role_cms b,tsm_staff_role c"
    			+" where a.fun_id = b.fun_id AND b.role_id = c.role_id AND a.state='1' AND b.state='1' AND c.staff_no=? ");
    	if(checkAdmin()){
            buffer=new StringBuffer("SELECT a.fun_id,fun_name,fun_type,show_cols,edit_cols,visit_url,'9' as role_type FROM cms_fun a LEFT JOIN tsm_role_cms b ON a.fun_id = b.fun_id WHERE a.state='1'");
            list=sqlMapExe.queryForMapList(buffer.toString());
        }else {
            list=sqlMapExe.queryForMapListBySql(buffer.toString(), new String[]{staff_no});
        }
    	return list;
    }

    private void getUserVerifyList(DynamicDict dict)throws FrameException{
        HttpSession session=ContextHelper.getSession();
        boolean rval=false;
        if(null!=session){
            Object obj=session.getAttribute(KEY.CURRENT_USER);
            if(null!=obj){
                TsmStaff staff=(TsmStaff)obj;
                List list=getUserVerifyList(staff.getStaff_no());
                boolean show=false;
                if(null!=list&& list.size()>0){
                    rval=true;
                    if(list.size()>5)show=true;

                    dict.setValueByName(KEY.VERIFY_LIST,list);
                }
            }
        }
        dict.setValueByName("result",String.valueOf(rval));
    }

    /*
   * 获取用户待审核列表
   * */
    private  List getUserVerifyList(String staff_no){
        List list=new ArrayList();

        StringBuffer urlSql=new StringBuffer("select * from (select a.*,rownum rc from (\n" +
                "select fun_id,fun_name,decode(fun_type,'db','数据库','url','文件') type,fun_type,state,\n" +
                "visit_url,coun from( \n" +
                "select fun_id,fun_name,fun_type,state,visit_url,file_id from cms_fun where  fun_type='url' \n" +
                "and state=1 and fun_id in(select  fun_id from tsm_role_cms where role_type=2 and state=1\n" +
                "and role_id in(select  t.role_id from tsm_staff_role t where t.staff_no=?)))a,\n" +
                "(select c.file_id,count(c.file_id) coun  from cms_fun c,cms_content_edit f where\n" +
                "c.file_id=f.file_id and f.sequ=0 and f.file_state='2' and c.fun_id in\n" +
                "(select  fun_id from tsm_role_cms where role_type=2 and state=1 and role_id in(select  \n" +
                "t.role_id from tsm_staff_role t where t.staff_no=?))group by c.file_id) b \n" +
                "where a.file_id=b.file_id\n" +
                "union \n" +
                "select a.fun_id,fun_name,decode(fun_type,'url','文件','db','数据库') type,fun_type,state,visit_url,coun from( \n" +
                "select fun_id,fun_name,fun_type,state,visit_url from cms_fun\n" +
                "where  fun_type='db' and state=1 and fun_id in \n" +
                "(select  fun_id from tsm_role_cms where role_type=2\n" +
                "and state=1 and role_id in\n" +
                "(select  t.role_id from tsm_staff_role t where t.staff_no=?))) a,\n" +
                "(select e.fun_id ,count(1) coun from cms_fun c,cms_info_edit e\n" +
                "where c.fun_id=e.fun_id and e.sequ=0 and e.state='2' and e.fun_id in(\n" +
                "select  fun_id from tsm_role_cms where role_type=2\n" +
                "and state=1 and role_id in\n" +
                "(select  t.role_id from tsm_staff_role t where t.staff_no=?))\n" +
                "group by e.fun_id)\n" +
                " b where a.fun_id=b.fun_id\n" +
                ") a where rownum<=5) a where a.rc>=1");

        if(checkAdmin()){
           urlSql=new StringBuffer("select * from (select a.*,rownum rc from (\n" +
                   "select fun_id,fun_name,fun_type,state,visit_url,coun from( \n" +
                   "select fun_id,fun_name,fun_type,state,visit_url,file_id from cms_fun where \n" +
                   "state=1)a,\n" +
                   "(select c.file_id,count(c.file_id) coun  from cms_fun c,cms_content_edit f where \n" +
                   "c.file_id=f.file_id and f.sequ=0 and f.file_state='2' group by c.file_id) b \n" +
                   "where a.file_id=b.file_id\n" +
                   "union all\n" +
                   "select a.fun_id,fun_name,fun_type,state,visit_url,coun from( \n" +
                   "select fun_id,fun_name,fun_type,state,visit_url from cms_fun\n" +
                   "where state=1) a,\n" +
                   "(select e.fun_id ,count(1) coun from cms_fun c,cms_info_edit e\n" +
                   "where c.fun_id=e.fun_id and e.sequ=0 and e.state='2' group by e.fun_id) \n" +
                   "b where a.fun_id=b.fun_id\n" +
                   ") a where rownum<=5) a where a.rc>=1\n");

            list=sqlMapExe.queryForMapList(urlSql.toString());
        }else {
            List param=new ArrayList();
            param.add(staff_no);
            param.add(staff_no);
            param.add(staff_no);
            param.add(staff_no);
            list=sqlMapExe.queryForMapListBySql(urlSql.toString(),param);
        }
        return list;
    }


  //
  private  void getUserPageVerifyList(DynamicDict dict)throws FrameException{
//      TsmStaff staff=WebUtils.getCurrentUser(ContextHelper.getSession());
	  TsmStaff staff=null;
      if(null==staff)return;
      String staff_no=staff.getStaff_no();

      int pageIndex = Const.getPageIndex(dict);
      int pageSize = Const.getPageSize(dict);
      PageModel pager=null;
      ArrayList param=new ArrayList();
      StringBuffer urlSql=new StringBuffer("select fun_id,fun_name,decode(fun_type,'db','数据库','url','文件') type,fun_type,state,\n" +
              "visit_url,coun from( \n" +
              "select fun_id,fun_name,fun_type,state,visit_url,file_id from cms_fun where  fun_type='url' \n" +
              "and state=1 and fun_id in(select  fun_id from tsm_role_cms where role_type=2 and state=1\n" +
              "and role_id in(select  t.role_id from tsm_staff_role t where t.staff_no=?)))a,\n" +
              "(select c.file_id,count(c.file_id) coun  from cms_fun c,cms_content_edit f where\n" +
              "c.file_id=f.file_id and f.sequ=0 and f.file_state='2' and c.fun_id in\n" +
              "(select  fun_id from tsm_role_cms where role_type=2 and state=1 and role_id in(select  \n" +
              "t.role_id from tsm_staff_role t where t.staff_no=?))group by c.file_id) b \n" +
              "where a.file_id=b.file_id\n" +
              "union \n" +
              "select a.fun_id,fun_name,decode(fun_type,'url','文件','db','数据库') type,fun_type,state,visit_url,coun from( \n" +
              "select fun_id,fun_name,fun_type,state,visit_url from cms_fun\n" +
              "where  fun_type='db' and state=1 and fun_id in \n" +
              "(select  fun_id from tsm_role_cms where role_type=2\n" +
              "and state=1 and role_id in\n" +
              "(select  t.role_id from tsm_staff_role t where t.staff_no=?))) a,\n" +
              "(select e.fun_id ,count(1) coun from cms_fun c,cms_info_edit e\n" +
              "where c.fun_id=e.fun_id and e.sequ=0 and e.state='2' and e.fun_id in(\n" +
              "select  fun_id from tsm_role_cms where role_type=2\n" +
              "and state=1 and role_id in\n" +
              "(select  t.role_id from tsm_staff_role t where t.staff_no=?))\n" +
              "group by e.fun_id)\n" +
              " b where a.fun_id=b.fun_id");
      if(checkAdmin()){
          urlSql=new StringBuffer("select fun_id,fun_name,decode(fun_type,'db','数据库','url','文件') type,fun_type,state,visit_url,coun from( \n" +
                  "select fun_id,fun_name,fun_type,state,visit_url,file_id from cms_fun where \n" +
                  "state=1)a,\n" +
                  "(select c.file_id,count(c.file_id) coun  from cms_fun c,cms_content_edit f where \n" +
                  "c.file_id=f.file_id and f.sequ=0 and f.file_state='2' group by c.file_id) b \n" +
                  "where a.file_id=b.file_id\n" +
                  "union \n" +
                  "select a.fun_id,fun_name,decode(fun_type,'url','文件','db','数据库') type,fun_type,state,visit_url,coun from( \n" +
                  "select fun_id,fun_name,fun_type,state,visit_url from cms_fun\n" +
                  "where state=1) a,\n" +
                  "(select e.fun_id ,count(1) coun from cms_fun c,cms_info_edit e\n" +
                  "where c.fun_id=e.fun_id and e.sequ=0 and e.state='2' \n" +
                  "group by e.fun_id) b where a.fun_id=b.fun_id");
      }else {
          param.add(staff_no);
          param.add(staff_no);
          param.add(staff_no);
          param.add(staff_no);
      }
      pager = sqlMapExe.queryPageModelResult(urlSql.toString(), param, pageIndex, pageSize);
      Const.setPageModel(dict,pager);
  }
    
}
