//package com.ztesoft.cms.login.acions;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang.StringUtils;
//import org.directwebremoting.util.Logger;
//
//import com.powerise.ibss.framework.DynamicDict;
//import com.powerise.ibss.framework.FrameException;
//import com.powerise.ibss.framework.IAction;
//import com.powerise.ibss.framework.servlet.SystemInit;
//import com.ztesoft.cms.login.vo.TwbSendSms;
//import com.ztesoft.common.util.ContextHelper;
//import com.ztesoft.ibss.common.dao.SequenceManageDAOImpl;
//import com.ztesoft.ibss.common.util.SqlMapExe;
//
///**
// * Created with IntelliJ IDEA.
// * User: Administrator
// * Date: 12-11-20
// * Time: 下午2:52
// * To change this template use File | Settings | File Templates.
// *
// * insert into tfm_services(service_name,module_id,service_desc,class_name)
// values('TWB_SEND_SMS_ACTION','WT','生成随机密码','com.ztesoft.cms.login.acions.TwbSendSmsAction')
// */
//public class TwbSendSmsAction implements IAction {
//    private SqlMapExe sqlMapExe=null;
//
//    private Logger log= Logger.getLogger(TwbSendSmsAction.class);
//
//    public static class KEY{
//        public static final String METHOD="METHOD";
//
//        public static final String GENERATE_PWD="GENERATE_PWD";
//
//        public static final String RESULT="RESULT";
//    }
//
//    public int perform(DynamicDict dict) throws FrameException {
//        sqlMapExe=new SqlMapExe(dict.GetConnection());
//
//        String method=dict.getValueByNameEx(KEY.METHOD);
//        if(StringUtils.isNotEmpty(method)){
//            if(method.equals(KEY.GENERATE_PWD)){
//                 this.generatePwd(dict);
//            }
//        }
//        return 0;
//    }
//
//
//    private void generatePwd(DynamicDict dict)throws FrameException{
//    	//检查IP
//    	HttpServletRequest request = (HttpServletRequest)ContextHelper.getRequest();
//    	if(!SystemInit.checkAdminIp(request))return;
//    	
//    	boolean rval=false;
//        String phone=(String)dict.getValueByName("phone",false);
//        if(StringUtils.isNotEmpty(phone)){
//            String random=getRandomPwd();
//            HttpSession session= ContextHelper.getSession();
//
//            TwbSendSms sms=new TwbSendSms();
//            sms.setSend_content("欢迎使用cms系统,此次登录密码为"+random+".有效时间为30分钟.仅使用一次.");
//            sms.setSend_num("10000");
//            sms.setRecv_num(phone);
//            sms.setAcct_num(random);
//            sms.setState("0");
//            sms.setSms_opertype("CMS");
//            rval=sendCms(sms);
//            if(rval){
//                session.setAttribute(LoginAction.KEY.RANDOM_PWD,random);
//                session.setAttribute(LoginAction.KEY.CURRENT_TIME,String.valueOf(System.currentTimeMillis()));
//            }
//        }
//        dict.setValueByName(KEY.RESULT,String.valueOf(rval));
//    }
//
//    //生成随机密码
//    private String getRandomPwd(){
//        StringBuffer buffer=new StringBuffer();
//        Random random=new Random();
//        for(int i=0;i<6;i++){
//            buffer.append(random.nextInt(10));
//        }
//        log.debug("生成的随机密码：=========》"+buffer.toString());
//        return buffer.toString();
//    }
//
//
//    //发送随机密码
//    private boolean sendCms(TwbSendSms sms){
//        boolean rval=false;
//        try{
//            //短信发送表中插入数据
//            StringBuffer insertSql=new StringBuffer("INSERT INTO TWB_SEND_SMS(\n" +
//                    "SEND_NO, SEND_NUM, RECV_NUM, SEND_CONTENT, \n" +
//                    "SMS_OPERTYPE, SEND_COUNT, STATE,\n" +
//                    "CREATE_TIME,acct_num,deal_count,smgp_charge_nbr)\n" +
//                    "VALUES(?,?,?,?,?,?,?,sysdate,?,'0','10000')");
//
//            List param=new ArrayList();
//            param.add(new SequenceManageDAOImpl().getSequenceLen("s_TWB_SEND_SMS_SEQ","0",10,sqlMapExe)); //获取键
//            param.add(sms.getSend_num());
//            param.add(sms.getRecv_num());
//            param.add(sms.getSend_content());
//            param.add(sms.getSms_opertype());
//            param.add(String.valueOf(sms.getSend_count()));
//            param.add(sms.getState());
//            param.add(sms.getAcct_num());
//
//            sqlMapExe.excuteUpdate(insertSql.toString(),param);
//            rval=true;
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally{
//            return rval;
//        }
//    }
//
//
//
//}
