package com.ztesoft.cms.page;

import com.ztesoft.cms.login.acions.LoginAction;
import com.ztesoft.cms.login.vo.TsmStaff;
//import com.ztesoft.common.util.WebUtils;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.ibss.ct.config.CTConfig;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-19
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public class CMSPrivilegesUtils {

    //权限验证
    public static boolean checkPrivileges(HttpSession session,String fun_id){
        boolean rval=false;
        try{
            if(StringUtils.isNotEmpty(fun_id) && (null!=session && null!=session.getAttribute(LoginAction.KEY.CURRENT_USER))){
                if(checkAdmin(session)){
                    rval=true;
                }else{
                    TsmStaff staff=null;//WebUtils.getCurrentUser(session);
                    Object obj=session.getAttribute(LoginAction.KEY.CURRENT_USER_FUNCTIONS);
                    if(null!=staff && null!=obj){
                        List list=(ArrayList)obj;
                        Map map=null;
                        String item=null;
                        for(int i=0;i<list.size();i++){
                            map=(HashMap)list.get(i);
                            if(null==map.get("fun_id"))continue;

                            if(fun_id.equals(String.valueOf(map.get("fun_id")))){
                               rval=true;
                                break;
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            return rval;
        }
    }

    private static boolean checkAdmin(HttpSession session){
        boolean rval=false;
        try{
            String sign=  CTConfig.getInstance().getValue("SUPER_STAFF_CODE");  ////超级管理员
            if(org.apache.commons.lang.StringUtils.isNotEmpty(sign)){
                String items[]=sign.split("\\,");
                if(null!=session){
                    Object obj=session.getAttribute(LoginAction.KEY.CURRENT_USER);
                    if(null!=obj){
                        TsmStaff staff=(TsmStaff)obj;
                        String item=null;
                        for(int i=0;i<items.length;i++){
                            item=items[i];
                            if(org.apache.commons.lang.StringUtils.isEmpty(item))continue;
                            if(staff.getStaff_no().equals(item)){
                                rval=true;
                                break;
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            return rval;
        }
    }


}
