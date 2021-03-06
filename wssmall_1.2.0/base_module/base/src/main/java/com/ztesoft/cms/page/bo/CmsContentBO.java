package com.ztesoft.cms.page.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.util.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.cms.login.acions.LoginAction;
import com.ztesoft.cms.login.vo.TsmStaff;
import com.ztesoft.cms.page.CmsKey;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.SqlMapExe;
import com.ztesoft.ibss.common.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-1
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 * insert into tfm_services(service_name,module_id,service_desc,class_name)
 values('CMSCONTENTBO','WT','审核','com.ztesoft.cms.page.bo.CmsContentBO')
 */
public class CmsContentBO implements IAction {
    private Logger log= Logger.getLogger(CmsContentBO.class);
    private SqlMapExe sqlMapExe=null;

    public static class KEY{
        public static final String METHOD="METHOD";
        public static final String AUDIT="AUDIT";//审核
        public static final String RESULT="RESULT";

        public static final String AUDIT_NOT="AUDIT_NOT";//审核

        public static final String HISTORY="HISTORY";

        public static final String AUDIT_FILE="AUDIT_FILE";//通过文件ID审核cms_content_edit

        public static final String AUDIT_NOT_FILE="AUDIT_NOT_FILE";//通过文件ID审核cms_content_edit
        
        public static final String GET_OFFLINE_CONTENT="GET_OFFLINE_CONTENT";//编辑状态文件信息获取
    }


    @Override
	public int perform(DynamicDict dict) throws FrameException {
        sqlMapExe=new SqlMapExe(dict.GetConnection());
        String method=dict.getValueByNameEx(KEY.METHOD);


        if(StringUtils.isNotEmpty(method)){
            if(method.equals(KEY.AUDIT)){
                virifyCmsContent(dict);
            }else if(method.equals(KEY.HISTORY)){
                queryHistory(dict);
            }else if(method.equals(KEY.AUDIT_NOT)){
                virifyNotCmsContent(dict);
            }else if(method.equals(KEY.AUDIT_FILE)){
                 virifyCmsContentByFileId(dict);
            }else if(method.endsWith(KEY.AUDIT_NOT_FILE)){
                virifyNotCmsContentByFileId(dict);
            }else if(method.equals(KEY.GET_OFFLINE_CONTENT)){
            	getOfflineContent(dict);
            }
        }


        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void getOfflineContent(DynamicDict dict) throws FrameException {
    	String val = (String) dict.getValueByName("val");
		String sql = "select * from ( select a.fun_id val,a.serv_kind kind ,b.file_path path from CMS_FUN a,CMS_CONTENT_EDIT b WHERE a.file_id=b.file_id and a.fun_type='url' and b.sequ=0 "+
		" UNION SELECT to_char(c.info_id) val,c.info_kind kind,b.file_path path FROM cms_info_edit c,cms_content_edit b WHERE c.file_id = b.file_id and b.sequ=0  ) where val=? ";;
		List ls = sqlMapExe.queryForMapListBySql(sql,new String[]{val});
		if(ls!=null && ls.size()>0){
			dict.setValueByName("CONTENT", ls.get(0));
		}else{
			dict.setValueByName("CONTENT", new HashMap());
		}
		
	}

	//通过文件ID审核 cms_content_edit
    private void virifyCmsContentByFileId(DynamicDict dict)throws FrameException{
        String file_id=(String)dict.getValueByName("file_id",false);
        if(StringUtils.isEmpty(file_id)){
            log.debug("CmsContentBO.virifyCmsContentByFileId ===>审核出错!file_id --->"+file_id);
            dict.setValueByName(KEY.RESULT,String.valueOf(false));
            return;
        }
        StringBuffer delSql=new StringBuffer("delete from cms_content where file_id=?");  //删除生产表中的数据
        sqlMapExe.excuteUpdate(delSql.toString(),new String[]{file_id});

        //向生产表中插入新数据
        StringBuffer insertSql=new StringBuffer("insert into cms_content(file_id,file_name,file_path,file_size,file_state," +
                "oper_time,oper_staff_no) select file_id,file_name,file_path,file_size,1,oper_time," +
                "oper_staff_no from cms_content_edit where file_id=? and sequ=0");
        sqlMapExe.excuteUpdate(insertSql.toString(),new String[]{file_id});

        //编辑表中插入操作日志
        StringBuffer sequSql=new StringBuffer("select max(sequ)+1 sequ from cms_content_edit where file_id=?");
        String sequ=sqlMapExe.querySingleValue(sequSql.toString(),new String[]{file_id});
        StringBuffer bakSql=new StringBuffer("insert into cms_content_edit(file_id,file_name,file_path,file_size,file_state," +
                "oper_time,oper_staff_no,sequ,descriptions) select file_id,file_name,file_path,file_size,file_state,oper_time," +
                "oper_staff_no,?,descriptions from cms_content_edit where file_id=? and sequ=0");
        sqlMapExe.excuteUpdate(bakSql.toString(),new String[]{sequ,file_id});

        //修改编辑表中的状态
        TsmStaff staff=getCurrentUser();
        StringBuffer updateSql=new StringBuffer("update cms_content_edit set file_state='1',descriptions='审核通过',oper_time=sysdate,oper_staff_no=? where file_id=? and sequ='0'");
        sqlMapExe.excuteUpdate(updateSql.toString(),new String[]{staff.getStaff_no(),file_id});

        dict.setValueByName(KEY.RESULT,String.valueOf(true));
        log.debug("审核Cms_content_editch成功!");
        //RefreshCacheUtils.refresh();
    }

    //通过文件ID审核 cms_content_edit
    private void virifyNotCmsContentByFileId(DynamicDict dict)throws FrameException{
        log.debug("CmsContentBO.virifyNotCmsContentByFileId =========>审核不通过开始.");
        String file_id=(String)dict.getValueByName("file_id",false);
        if(StringUtils.isEmpty(file_id)){
            log.debug("CmsContentBO.virifyNotCmsContentByFileId ===>审核不通过出错!file_id --->"+file_id);
            dict.setValueByName(KEY.RESULT,String.valueOf(false));
            return;
        }

        //编辑表中插入操作日志
        StringBuffer sequSql=new StringBuffer("select max(sequ)+1 sequ from cms_content_edit where file_id=?");
        String sequ=sqlMapExe.querySingleValue(sequSql.toString(),new String[]{file_id});
        StringBuffer bakSql=new StringBuffer("insert into cms_content_edit(file_id,file_name,file_path,file_size,file_state," +
                "oper_time,oper_staff_no,sequ,descriptions) select file_id,file_name,file_path,file_size,file_state,oper_time," +
                "oper_staff_no,?,descriptions from cms_content_edit where file_id=? and sequ=0");
        sqlMapExe.excuteUpdate(bakSql.toString(),new String[]{sequ,file_id});

        //修改编辑表中的状态
        TsmStaff staff=getCurrentUser();
        StringBuffer updateSql=new StringBuffer("update cms_content_edit set file_state='3',descriptions='审核不通过',oper_time=sysdate,oper_staff_no=? where file_id=? and sequ='0'");
        sqlMapExe.excuteUpdate(updateSql.toString(),new String[]{staff.getStaff_no(),file_id});

        dict.setValueByName(KEY.RESULT,String.valueOf(true));
        log.debug("审核Cms_content_editch不通过!");
        //RefreshCacheUtils.refresh();
    }

    //查询历史
    private void queryHistory(DynamicDict dict)throws FrameException{
        String fun_id=dict.getValueByNameEx("FUN_ID");
        if(StringUtils.isNotEmpty(fun_id)){
            StringBuffer selSql=new StringBuffer("select sequ,oper_staff_no,descriptions,oper_time,(SELECT STAFF_NAME FROM TSM_STAFF T WHERE C.OPER_STAFF_NO = T.STAFF_NO )staff_name,file_id,file_state,file_path,  " +
                    " decode(file_state,'"+CmsKey.CMS_AUX_STATE_0+"','"+CmsKey.CMS_AUX_STATE_0_NAME+"','"+CmsKey.CMS_AUX_STATE_1+"','"+CmsKey.CMS_AUX_STATE_1_NAME+"','"+CmsKey.CMS_AUX_STATE_2+"','"+CmsKey.CMS_AUX_STATE_2_NAME+"','"+CmsKey.CMS_AUX_STATE_3+"','"+CmsKey.CMS_AUX_STATE_3_NAME+"') state  " +
                    " from cms_content_edit c where    " +
                    " 1=1 and file_id =(  " +
                    " select file_id from cms_fun where fun_id=?)" +
                    " AND c.sequ != '"+CmsKey.CMS_AUX_STATE_0+"' order by c.sequ desc");
            ArrayList param=new ArrayList();
            param.add(fun_id);

            int pageIndex = Const.getPageIndex(dict);
            int pageSize = Const.getPageSize(dict);
            PageModel pager = sqlMapExe.queryPageModelResult(selSql.toString(), param, pageIndex, pageSize);
            Const.setPageModel(dict,pager);
        }
    }

    //审核 cms_content_edit表中的数据  先删除cms_content中的数据，再将cms_content_edit中的数据添加进cms_content  服务器中同步文件
    private void virifyCmsContent(DynamicDict dict)throws FrameException{
            String fun_id=(String)dict.getValueByName("fun_id",false);
            if(StringUtils.isEmpty(fun_id)){
                log.debug("CmsContentBO.virifyCmsContent ===>审核出错!fun_id --->"+fun_id);
                dict.setValueByName(KEY.RESULT,String.valueOf(false));
                return;
            }
            StringBuffer selSql=new StringBuffer("SELECT a.file_id FROM cms_content_edit a,cms_fun b where a.file_id = b.file_id   " +
                    "AND b.state='1'  and b.fun_id=? and a.sequ=0");
            String file_id=sqlMapExe.querySingleValue(selSql.toString(),new String[]{fun_id});

            StringBuffer delSql=new StringBuffer("delete from cms_content where file_id=?");  //删除生成表中的数据
            sqlMapExe.excuteUpdate(delSql.toString(),new String[]{file_id});

            //向生产表中插入新数据
            StringBuffer insertSql=new StringBuffer("insert into cms_content(file_id,file_name,file_path,file_size,file_state," +
                    "oper_time,oper_staff_no) select file_id,file_name,file_path,file_size,1,oper_time," +
                    "oper_staff_no from cms_content_edit where file_id=? and sequ=0");
            sqlMapExe.excuteUpdate(insertSql.toString(),new String[]{file_id});

           //编辑表中插入操作日志
          StringBuffer sequSql=new StringBuffer("select max(sequ)+1 sequ from cms_content_edit where file_id=?");
          String sequ=sqlMapExe.querySingleValue(sequSql.toString(),new String[]{file_id});
          StringBuffer insertSql2=new StringBuffer("insert into cms_content_edit(file_id,file_name,file_path,file_size,file_state," +
                "oper_time,oper_staff_no,sequ,descriptions) select file_id,file_name,file_path,file_size,file_state,oper_time," +
                "oper_staff_no,?,descriptions from cms_content_edit where file_id=? and sequ=0");
           sqlMapExe.excuteUpdate(insertSql2.toString(),new String[]{sequ,file_id});

          //修改编辑表中的状态
          TsmStaff staff=getCurrentUser();
           StringBuffer updateSql=new StringBuffer("update cms_content_edit set file_state='1',descriptions='审核通过',oper_time=sysdate,oper_staff_no=? where file_id=? and sequ='0'");
           sqlMapExe.excuteUpdate(updateSql.toString(),new String[]{staff.getStaff_no(),file_id});

          //RefreshCacheUtils.refresh();
          dict.setValueByName(KEY.RESULT,String.valueOf(true));
    }

    //审核不通过
    private void virifyNotCmsContent(DynamicDict dict)throws FrameException{
        String fun_id=(String)dict.getValueByName("fun_id",false);
        if(StringUtils.isEmpty(fun_id)){
            if(StringUtils.isEmpty(fun_id)){
                log.debug("CmsContentBO.virifyNotCmsContent ===>审核出错!fun_id --->"+fun_id);
                dict.setValueByName(KEY.RESULT,String.valueOf(false));
                return;
            }
        }
        StringBuffer selSql=new StringBuffer("SELECT a.file_id FROM cms_content_edit a,cms_fun b where a.file_id = b.file_id   " +
                "AND b.state='1'  and b.fun_id=? and a.sequ=0");
        String file_id=sqlMapExe.querySingleValue(selSql.toString(),new String[]{fun_id});

        StringBuffer sequSql=new StringBuffer("select max(sequ)+1 sequ from cms_content_edit where file_id=?");
        String sequ=sqlMapExe.querySingleValue(sequSql.toString(),new String[]{file_id});

        StringBuffer insertSql=new StringBuffer("insert into cms_content_edit(file_id,file_name,file_path,file_size,file_state," +
                "oper_time,oper_staff_no,sequ,descriptions) select file_id,file_name,file_path,file_size,file_state,oper_time," +
                "oper_staff_no,?,descriptions from cms_content_edit where file_id=? and sequ=0");
        sqlMapExe.excuteUpdate(insertSql.toString(),new String[]{sequ,file_id});

        TsmStaff staff=getCurrentUser();
        StringBuffer updateSql=new StringBuffer("update cms_content_edit set file_state='3',descriptions='审核不通过',oper_time=sysdate,oper_staff_no=? where file_id=? and sequ='0'");
        sqlMapExe.excuteUpdate(updateSql.toString(),new String[]{staff.getStaff_no(),file_id});
        dict.setValueByName(KEY.RESULT,String.valueOf(true));
    }

    //获取当前登录人
    public TsmStaff getCurrentUser(){
        TsmStaff staff=null;
        HttpSession session= ContextHelper.getSession();
        if(null!=session && null!=session.getAttribute(LoginAction.KEY.CURRENT_USER)){
           staff=(TsmStaff)session.getAttribute(LoginAction.KEY.CURRENT_USER);
        }
        return staff;
    }
}
