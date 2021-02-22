package com.ztesoft.cms.page.bo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.directwebremoting.util.Logger;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.cms.login.acions.LoginAction;
import com.ztesoft.cms.login.vo.TsmStaff;
import com.ztesoft.cms.page.CMSPrivilegesUtils;
import com.ztesoft.cms.page.CmsKey;
import com.ztesoft.cms.page.cache.CmsCacheData;
import com.ztesoft.cms.page.context.CmsContext;
import com.ztesoft.cms.page.context.CmsObj;
import com.ztesoft.cms.page.file.FileEditUtil;
import com.ztesoft.cms.page.query.CmsConst;
import com.ztesoft.cms.page.vo.CmsInfo;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.ibss.common.dao.DAOUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.FileUtil;
import com.ztesoft.ibss.common.util.SqlMapExe;
import com.ztesoft.ibss.common.util.StringUtils;
//import com.ztesoft.common.util.WebUtils;

/**
 * @author Reason.Yea
 * @version Created Oct 25, 2012
INSERT INTO tfm_services(service_name,module_id,service_desc,class_name)
VALUES('CMS_INFO','WT','CMS内容管理','com.ztesoft.cms.page.bo.CmsInfoBO');
 */
public class CmsInfoBO implements IAction {
    private static Logger log= Logger.getLogger(CmsInfoBO.class);

    public static class KEY{
        //db待审核列表
        public static final String QUERY_LIST="QUERY_LIST";
        //所有版本
        public static final String ALL_VERSIONS="ALL_VERSIONS";

        public static final String AUDIT_NOT="AUDIT_NOT";
        
        public static final String AREA_CODE = "AREA_CODE" ;
    }
    
    public SqlMapExe sqlExe = null;
    
    private static HashMap I_data = new HashMap();
    

	@Override
	public int perform(DynamicDict dict) throws FrameException {
        sqlExe=new SqlMapExe(dict.GetConnection()) ;
		String type=((String) dict.getValueByName("type",false)).toUpperCase();
		Connection conn = dict.GetConnection();
        //初始化cms对象
        setCmsObj(dict);
        //验证权限
        if(!validatePrivileges(dict))//不通过立刻返回
        	return 0;

        if(type.equals("SWITH")){//切换
            swithOnline(dict);
        }else if(type.equals("AUDIT")){//审核
            auditCmsInfo(dict);
        }else if(type.equals("FILE")){//文件操作
            doFileAction(dict);
        }else if(type.equals("ROLE")){//获取权限
            getStaffRole(dict);
        }else if(type.equals(KEY.QUERY_LIST)){
            findListByFunId(dict);
        }else if(type.equals("ADDCMSINFO")){
            addCmsInfo(dict);
        }else if(type.equals("VIEWCMSINFOBYID")){//查看
            viewCmsInfoById(dict);
        }else if(type.equals("QUERYTABLE")){//查询列表(统一查询列表)
            queryTable(dict);
        }else if(type.equals("GETFUNBYFUNID")){//缓存cms_fun信息
        	getFunByFunId(dict);
        }else if(type.equals("SHOWHISBYINFOID")){//查询列表
        	loadHisData(dict);
        }else if(type.equals("MODIFYCMSINFO")){//修改
            modifyCmsInfo(dict);
        }else if(type.equals("RETHISCMSINFO")){//回退历史版本（和修改逻辑差不多）
        	retHisCmsInfo(dict);
        }else  if(type.equals(KEY.ALL_VERSIONS)){
            getAllVersionsByFunId(dict);
        }else if(type.endsWith(KEY.AUDIT_NOT)){
            auditNotCmsInfoHandleBefore(dict);
        }else{//默认为查询
            getCmsInfo(dict);
        }

		return 0;
    }
	

	/**
	 * 权限验证
	 * @param dict
	 * @return
	 * @throws FrameException
	 */
	private boolean validatePrivileges(DynamicDict dict) throws FrameException {
		String privileges =  (String) dict.getValueByName("privileges",false);//权限
		String fun_id = (String) dict.getValueByName("fun_id",false);//函数名称
		boolean ret = true;
		 //cms权限验证
        log.debug("privileges===>"+privileges+"  ： fun_id==>"+fun_id);
        if(privileges.trim().equals("true")){
            boolean rval=CMSPrivilegesUtils.checkPrivileges(ContextHelper.getSession(),fun_id);
            log.debug("cms权限验证====》"+rval+"   "+fun_id);
            if(!rval){
                dict.setValueByName("PRIVILEGES",String.valueOf(false));
                ret = false;
            }
        }
		return ret;
	}
	/**
	 * 设置cms对象
	 * @throws FrameException
	 */
	private void setCmsObj(DynamicDict dict) throws FrameException {
		String fun_id = (String) dict.getValueByName("fun_id",false);//函数名称
		String info_id = (String) dict.getValueByName("info_id",false);//数据库操作主键
        String file_id =(String) dict.getValueByName("file_id",false);//文件id
        String sequ =  (String) dict.getValueByName("sequ",false);//序列（版本号）
        String cms_method=(String)dict.getValueByName("cms_method",false);//只有cms数据库操作才会传入

        String cms_state = CmsConst.CMS_STATE_ONLINE;//默认是在线模式
        HttpSession session = ContextHelper.getSession();
        if(session!=null && session.getAttribute(CmsConst.CMS_SESSION_KEY)!=null
                &&session.getAttribute(CmsConst.CMS_SESSION_KEY).equals(CmsConst.CMS_STATE_OFFLINE)  ){//离线模式
            cms_state=CmsConst.CMS_STATE_OFFLINE;
        }
        
        if(StringUtils.isEmpty(cms_method)){//只是文件处理的时候
        	file_id = sqlExe.querySingleValue("SELECT  a.file_id from cms_fun a WHERE a.fun_id = ? AND a.fun_type = 'url'", new String[]{fun_id});
            if(StringUtils.isEmpty(file_id))
            	file_id = "";
        }
        
        //设置线程对象
        CmsObj obj = new CmsObj(dict.GetConnection(),cms_state,fun_id);
        
        obj.setFile_id(file_id);
        if(StringUtils.isNotEmpty(file_id)) obj.putParam("file_id",file_id);

        if(StringUtils.isNotEmpty(sequ))obj.putParam("sequ",sequ);

        if(StringUtils.isNotEmpty(cms_method) && StringUtils.isNotEmpty(file_id)){
            obj.putParam("sign",String.valueOf(checkFile(file_id,"0")));
        }

        if(StringUtils.isNotEmpty(cms_method))obj.putParam("cms_method",cms_method);

        if(info_id.equals("")){//info_id为空
            info_id="0";
        }
        obj.setInfo_id(info_id);
        
        if(fun_id.equals("")){//
            fun_id="0";
        }
        obj.setFun_id(fun_id);
        
        TsmStaff staff=getCurrentUser();
        if(staff!=null){//用户不为空
            obj.setStaff(staff);
        }
        
        CmsContext.setCmsObj(obj);
	}
	/**
	 * 
	 * @param dict
	 * @throws FrameException
	 */
	private void retHisCmsInfo(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		Map cmsinfonew = (Map) dict.getValueByName("CMSINFONEW", false);
		String info_id = Const.getStrValue(cmsinfonew, "info_id");
		String sequ = Const.getStrValue(cmsinfonew, "sequ");
		
		String bakSql = //先备份
			"INSERT INTO cms_info_edit " +
			"(info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id,area_code,create_time,edit_time,eff_time,exp_time,state,file_id,sequ,col1,col2,col3,col4,col5,col6,col7,col8,oper_staff_no,descriptions) " +
			"select info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id,area_code,create_time,edit_time,eff_time,exp_time,state,file_id," +
			"(select max(sequ)+1 from CMS_INFO_EDIT where info_id =? )as sequ,col1,col2,col3,col4,col5,col6,col7,col8,oper_staff_no,descriptions " +
			"from CMS_INFO_EDIT WHERE sequ = 0 AND info_id = ?";
		boolean result = sqlExe.excuteUpdate(bakSql, new String[]{info_id,info_id}) >0;
		if(result){//备份成功
			//根据info_id 修改 sequ = 0 （最新的）那条记录
			String updSql = " update cms_info_edit set " +
					" fun_id=?, info_code=?, info_name=?, info_kind=?, info_chanel=?, info_type=?, " +
					"order_id=?, area_code=?, edit_time=sysdate, eff_time=?, exp_time=?, state=?,descriptions='历史回退', " +
					"file_id=?, sequ = ?,col1=?, col2=?, col3=?, col4=?, col5=?, col6=?, col7=?, col8=?,oper_staff_no = ? " +
					"where info_id=? and sequ = ? ";
			List param = new ArrayList();
			//String info_id = Const.getStrValue(cmsinfonew, "info_id");
			String fun_id = Const.getStrValue(cmsinfonew, "fun_id");
			String info_code = Const.getStrValue(cmsinfonew, "info_code");
			String info_name = Const.getStrValue(cmsinfonew, "info_name");
			String info_kind = Const.getStrValue(cmsinfonew, "info_kind");
			String info_chanel = Const.getStrValue(cmsinfonew, "info_chanel");
			//param.add(info_id);
			param.add(fun_id);
			param.add(info_code);
			param.add(info_name);
			param.add(info_kind);
			param.add(info_chanel);
			
			String info_type = Const.getStrValue(cmsinfonew, "info_type");
			String order_id = Const.getStrValue(cmsinfonew, "order_id");
			String area_code = Const.getStrValue(cmsinfonew, "area_code");
			String eff_time = Const.getStrValue(cmsinfonew, "eff_time");
			String exp_time = Const.getStrValue(cmsinfonew, "exp_time");
			param.add(info_type);
			param.add(order_id);
			param.add(area_code);
			param.add(DAOUtils.parseDateTime(eff_time));
			param.add(DAOUtils.parseDateTime(exp_time));
			
			
			String state = Const.getStrValue(cmsinfonew, "state");
			String file_id = Const.getStrValue(cmsinfonew, "file_id");
			param.add(state);
			param.add(file_id);
			param.add(CmsKey.SEQU_0);
			
			String col1 = Const.getStrValue(cmsinfonew, "col1");
			String col2 = Const.getStrValue(cmsinfonew, "col2");
			String col3 = Const.getStrValue(cmsinfonew, "col3");
			String col4 = Const.getStrValue(cmsinfonew, "col4");
			String col5 = Const.getStrValue(cmsinfonew, "col5");
			String col6 = Const.getStrValue(cmsinfonew, "col6");
			String col7 = Const.getStrValue(cmsinfonew, "col7");
			String col8 = Const.getStrValue(cmsinfonew, "col8");
			param.add(col1);
			param.add(col2);
			param.add(col3);
			param.add(col4);
			param.add(col5);
			param.add(DAOUtils.parseDateTime(col6));
			param.add(DAOUtils.parseDateTime(col7));
			param.add(col8);
			
			String oper_staff_no  = null;//WebUtils.getCurrentUser(ContextHelper.getRequest()).getStaff_no();
			param.add(oper_staff_no);
			
			param.add(info_id);
			param.add(CmsKey.SEQU_0);//upd最新的那条
			
			result = sqlExe.excuteUpdate2(updSql, param) > 0 ;
			String strResult = "false";
			if(result)
				strResult = "true";
			dict.setValueByName("result", strResult);
		}
	
		
	}
	/**
	 * 
	 * @param dict
	 * @throws FrameException
	 */
	private void loadHisData(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		String info_id = (String) dict.getValueByName("info_id", false);
		ArrayList li = new ArrayList();
		li.add(info_id);
		String sql = " select a.info_id,a.fun_id,a.info_code,a.info_name,a.info_kind,a.info_chanel,a.info_type,a.order_id,a.area_code,a.create_time,a.edit_time,to_char(SYSDATE,'yyyy-mm-dd') eff_time,to_char(SYSDATE,'yyyy-mm-dd') exp_time,a.state,a.file_id,a.sequ,a.col1,a.col2,a.col3,a.col4,a.col5,a.col6,a.col7,a.col8,a.oper_staff_no,(SELECT STAFF_NAME FROM TSM_STAFF T WHERE A.OPER_STAFF_NO = T.STAFF_NO )STAFF_NAME,a.descriptions, ";
			   sql+=" DECODE(state,'"+CmsKey.CMS_AUX_STATE_0+"','"+CmsKey.CMS_AUX_STATE_0_NAME+"','"+CmsKey.CMS_AUX_STATE_1+"','"+CmsKey.CMS_AUX_STATE_1_NAME+"','"+CmsKey.CMS_AUX_STATE_2+"','"+CmsKey.CMS_AUX_STATE_2_NAME+"','"+CmsKey.CMS_AUX_STATE_3+"','"+CmsKey.CMS_AUX_STATE_3_NAME+"','"+CmsKey.CMS_AUX_STATE_2_NAME+"') AS state_desc " ;
			   sql+=" from CMS_INFO_EDIT a WHERE a.info_id = ? AND a.sequ != '"+CmsKey.SEQU_0+"' " ;
		sql+="ORDER BY a.info_id ASC ,a.sequ ASC";
		int pageIndex = Const.getPageIndex(dict);
		int pageSize = Const.getPageSize(dict);
		PageModel pager =sqlExe.queryPageModelResult(sql, li, pageIndex, pageSize);
		Const.setPageModel(dict,pager);
	}

	/**
	 * 修改
	 * @param dict
	 * @throws FrameException
	 */
	private void modifyCmsInfo(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		Map cmsinfonew = (Map) dict.getValueByName("CMSINFONEW", false);
		String info_id = Const.getStrValue(cmsinfonew, "info_id");
		String bakSql = //先备份
			"INSERT INTO cms_info_edit " +
			"(info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id,area_code,create_time,edit_time,eff_time,exp_time,state,file_id,sequ,col1,col2,col3,col4,col5,col6,col7,col8,oper_staff_no,descriptions) " +
			"select info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id,area_code,create_time,edit_time,eff_time,exp_time,state,file_id," +
			"(select max(sequ)+1 from CMS_INFO_EDIT where info_id =? )as sequ,col1,col2,col3,col4,col5,col6,col7,col8,oper_staff_no,descriptions " +
			"from CMS_INFO_EDIT WHERE sequ = 0 AND info_id = ?";
		boolean result = sqlExe.excuteUpdate(bakSql, new String[]{info_id,info_id}) >0;
		if(result){//备份成功
			//根据info_id 修改 sequ = 0 （最新的）那条记录
			String updSql = " update cms_info_edit set " +
					" fun_id=?, info_code=?, info_name=?, info_kind=?, info_chanel=?, info_type=?, " +
					"order_id=?, area_code=?, edit_time=sysdate, eff_time=?, exp_time=?, state=?,descriptions='修改' ," +
					"file_id=?, sequ = ?,col1=?, col2=?, col3=?, col4=?, col5=?, col6=?, col7=?, col8=?,oper_staff_no = ? " +
					"where info_id=? and sequ = '0' ";
			List param = new ArrayList();
			//String info_id = Const.getStrValue(cmsinfonew, "info_id");
			String fun_id = Const.getStrValue(cmsinfonew, "fun_id");
			String info_code = Const.getStrValue(cmsinfonew, "info_code");
			String info_name = Const.getStrValue(cmsinfonew, "info_name");
			String info_kind = Const.getStrValue(cmsinfonew, "info_kind");
			String info_chanel = Const.getStrValue(cmsinfonew, "info_chanel");
			//param.add(info_id);
			param.add(fun_id);
			param.add(info_code);
			param.add(info_name);
			param.add(info_kind);
			param.add(info_chanel);
			
			String info_type = Const.getStrValue(cmsinfonew, "info_type");
			String order_id = Const.getStrValue(cmsinfonew, "order_id");
			String area_code = Const.getStrValue(cmsinfonew, "area_code");
			String eff_time = Const.getStrValue(cmsinfonew, "eff_time");
			String exp_time = Const.getStrValue(cmsinfonew, "exp_time");
			param.add(info_type);
			param.add(order_id);
			param.add(area_code);
			param.add(DAOUtils.parseDateTime(eff_time));
			param.add(DAOUtils.parseDateTime(exp_time));
			
			
			String state = Const.getStrValue(cmsinfonew, "state");
			String file_id = Const.getStrValue(cmsinfonew, "file_id");
			String sequ = "0";
			param.add(state);
			param.add(file_id);
			param.add(sequ);//表明为最新
			
			String col1 = Const.getStrValue(cmsinfonew, "col1");
			String col2 = Const.getStrValue(cmsinfonew, "col2");
			String col3 = Const.getStrValue(cmsinfonew, "col3");
			String col4 = Const.getStrValue(cmsinfonew, "col4");
			String col5 = Const.getStrValue(cmsinfonew, "col5");
			String col6 = Const.getStrValue(cmsinfonew, "col6");
			String col7 = Const.getStrValue(cmsinfonew, "col7");
			String col8 = Const.getStrValue(cmsinfonew, "col8");
			param.add(col1);
			param.add(col2);
			param.add(col3);
			param.add(col4);
			param.add(col5);
			param.add(DAOUtils.parseDateTime(col6));
			param.add(DAOUtils.parseDateTime(col7));
			param.add(col8);
			
			String oper_staff_no  = null;//WebUtils.getCurrentUser(ContextHelper.getRequest()).getStaff_no();
			param.add(oper_staff_no);
			
			param.add(info_id);
			result = sqlExe.excuteUpdate2(updSql, param) > 0 ;
			String strResult = "false";
			if(result)
				strResult = "true";
			dict.setValueByName("result", strResult);
		}
	}
    //查询所有所有版本记录
    private void getAllVersionsByFunId(DynamicDict dict)throws FrameException{
        String fun_id=(String)dict.getValueByName("fun_id",false);
        log.debug("getAllVersionsByFunId===>"+fun_id);
        if(StringUtils.isNotEmpty(fun_id)){
            StringBuffer selSql=new StringBuffer("select staff_name,c.*,decode(c.state,'0','无效','1','启用','2','待审核','3','审核不通过') status " +
                    "from cms_info_edit c,tsm_staff t " +
                    "where c.oper_staff_no= t.staff_no and  " +
                    "fun_id=? order by edit_time  desc");
            ArrayList param=new ArrayList();
            param.add(fun_id);

            int pageIndex = Const.getPageIndex(dict);
            int pageSize = Const.getPageSize(dict);
            PageModel pager =sqlExe.queryPageModelResult(selSql.toString(), param, pageIndex, pageSize);
            Const.setPageModel(dict,pager);
        }
    }

	/**
	 * 
	 * @param dict
	 * @throws FrameException
	 */
	private void queryTable(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		String fun_id = (String) dict.getValueByName("fun_id", false);
		String action_type = (String) dict.getValueByName("action_type", false);
		ArrayList li = new ArrayList();
		li.add(fun_id);
		String sql = " select a.info_id,a.fun_id,a.info_code,a.info_name,a.info_kind,a.info_chanel,a.info_type,a.order_id,a.area_code,a.create_time,a.edit_time,to_char(eff_time,'yyyy-mm-dd') eff_time,to_char(exp_time,'yyyy-mm-dd') exp_time,a.state,a.file_id,a.sequ,a.col1,a.col2,a.col3,a.col4,a.col5,a.col6,a.col7,a.col8,a.oper_staff_no,a.descriptions, ";
			   sql+=" DECODE(state,'"+CmsKey.CMS_AUX_STATE_0+"','"+CmsKey.CMS_AUX_STATE_0_NAME+"','"+CmsKey.CMS_AUX_STATE_1+"','"+CmsKey.CMS_AUX_STATE_1_NAME+"','"+CmsKey.CMS_AUX_STATE_2+"','"+CmsKey.CMS_AUX_STATE_2_NAME+"','"+CmsKey.CMS_AUX_STATE_3+"','"+CmsKey.CMS_AUX_STATE_3_NAME+"','"+CmsKey.CMS_AUX_STATE_2_NAME+"') AS state_desc" ;
			   sql+=" from CMS_INFO_EDIT a WHERE a.fun_id = ? AND a.sequ = '0' " ;
		if(action_type.equals(CmsKey.ACTION_TYPE_X)){
			sql+=" and a.state in( ? , ? ) ";
			li.add(CmsKey.CMS_AUX_STATE_2);
			li.add(CmsKey.CMS_AUX_STATE_3);
		}
		sql+="ORDER BY a.info_id ASC ,a.sequ ASC";
		int pageIndex = Const.getPageIndex(dict);
		int pageSize = Const.getPageSize(dict);
		PageModel pager =sqlExe.queryPageModelResult(sql, li, pageIndex, pageSize);
		pager.setList(dealAreaCode(pager.getList(),dict,sqlExe));
		Const.setPageModel(dict,pager);
		
	}
	
	private List dealAreaCode(List list,DynamicDict dict,SqlMapExe sqlExe) throws FrameException {
		//first load
		if((HashMap)I_data.get(KEY.AREA_CODE) == null){
			List li = sqlExe.queryForMapList("SELECT a.area_code,a.city_name FROM tvlwb_city_info a ");
			for (Iterator iterator = li.iterator(); iterator.hasNext();) {
				HashMap area = (HashMap) iterator.next();
				setCacheData(KEY.AREA_CODE, Const.getStrValue(area, "area_code"), area.get("city_name"));
			}
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			HashMap info = (HashMap) iterator.next();
			String area_code = (String) info.get("area_code");
			String area_code_name = "";
			String[] area = area_code.split(",");
			for (int i = 0; i < area.length; i++) {
				String name = (String) getCacheData(KEY.AREA_CODE,area[i]);
				area_code_name += name+",";
			}
			if(area.length >= 1)//去掉最后一个逗号
				area_code_name = area_code_name.substring(0, area_code_name.length()-1);
			info.put("area_code_name", area_code_name);
		}
		
		return list;
	}
	
    public static synchronized void setCacheData(String strCatagory, String strId, Object oData) throws FrameException {
        HashMap hmData = null;
        Object o = I_data.get(strCatagory);
        if (o == null) {
            hmData = new HashMap();
        } else {
            hmData = (HashMap) o;
        } 
        if (hmData.get(strId) == null)
        {
            hmData.put(strId, oData);
            I_data.put(strCatagory,hmData);
        }
    }
    
    public static Object getCacheData(String strCatagory,String strId) throws FrameException {
          HashMap hmData = null;
          Object oData = null;
          Object o = I_data.get(strCatagory);
          if (o != null) {
              hmData = (HashMap) o;
              oData = hmData.get(strId);          
          }
          if(oData==null){
          	log.info("====I_data======>"+strId);
          }
          return oData;
    }

	/**
	 * 通过fun_id获取cms_fun信息
	 * @param dict
	 */
	private void getFunByFunId(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		String fun_id = (String) dict.getValueByName("fun_id", false);
		String sql = "select a.fun_name,a.show_cols,a.edit_cols from cms_fun a where a.fun_id = ? ";
		List li = sqlExe.queryForMapListBySql(sql, new String[]{fun_id});
		List dealLi =  dealEditCols(li);//验证edit_cols字段的合法性，如果不合法，就置null
		if(!dealLi.isEmpty()){
			dict.setValueByName("result", dealLi);
			dict.setValueByName("flag", "yes");
		}else{
			dict.setValueByName("flag", "no");
		}
	}
	/**
	 * 对cms数据库处理时候对fun信息的处理
	 * @param li
	 * @return
	 * @throws FrameException
	 */
	private List dealEditCols(List li) throws FrameException {
		//数据库配置edit_cols字段事例col1:AA,col2:BB
		for (Iterator iterator = li.iterator(); iterator.hasNext();) {
			HashMap fun = (HashMap) iterator.next();
			boolean flag = true;
			String edit_cols = (String) fun.get("edit_cols");
			if(StringUtils.isEmpty(edit_cols)){
				flag = false;
				break;
			}
			else{
				String[] cols = edit_cols.split(",");// col1:AA,col2:BB  --> col1:AA
				for (int i = 0; i < cols.length; i++) {
					String col = cols[i];
					if(StringUtils.isEmpty(col)){
						flag = false;
						break;
					}
					else{
						String[] keyVal = col.split(":");
						if(keyVal.length != 2){
							flag = false;
							break;
						}
						String key = keyVal[0];
						String val = keyVal[1];
						if(key.indexOf("col") < 0){
							flag = false;
							break;
						}
						if(StringUtils.isEmpty(val)){
							flag = false;
							break;
						}
					}
				}
			}
			if(!flag){
				fun.put("edit_cols", null);// if validate failed ,  put 'edit_cols' null
			}
		}
		return li;
	}


	/**
	 * 
	 * @param dict
	 * @throws FrameException
	 */
	private void viewCmsInfoById(DynamicDict dict) throws FrameException {
	
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		String  info_id = (String) dict.getValueByName("info_id", false);
		String  sequ = (String) dict.getValueByName("sequ", false);
		String sql  = 
			"select info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id, " +
			" area_code,create_time,edit_time,to_char(eff_time,'yyyy-mm-dd') as eff_time, to_char(exp_time,'yyyy-mm-dd') as exp_time,state,file_id,sequ, " + 
			" col1,col2,col3,col4,col5, to_char(col6,'yyyy-mm-dd HH24:MI:SS') as col6, to_char(col7,'yyyy-mm-dd HH24:MI:SS') as col7 ,col8,oper_staff_no,nvl(descriptions,'未审核') AS DESCRIPTIONS" + 
			" from cms_info_edit where info_id = ? and sequ =  ? ";

		List li = sqlExe.queryForMapListBySql(sql, new String[]{info_id,sequ});
		dict.setValueByName("result", li);
	}
	/**
	 * 新增cms数据库编辑
	 * @param dict
	 * @throws FrameException
	 */
	private void addCmsInfo(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		Map cmsinfonew = (Map) dict.getValueByName("CMSINFONEW", false);
		String sql = "insert into cms_info_edit (info_id, fun_id, info_code, info_name, info_kind, info_chanel, " +
				"info_type, order_id, area_code, eff_time, exp_time, state, file_id,sequ, col1, " +
				"col2, col3, col4, col5, col6, col7, col8,oper_staff_no,create_time, edit_time) values " +
				"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,sysdate)";
		List param = new ArrayList();
		String info_id = Const.getStrValue(cmsinfonew, "info_id");
		String fun_id = Const.getStrValue(cmsinfonew, "fun_id");
		String info_code = Const.getStrValue(cmsinfonew, "info_code");
		String info_name = Const.getStrValue(cmsinfonew, "info_name");
		String info_kind = Const.getStrValue(cmsinfonew, "info_kind");
		String info_chanel = Const.getStrValue(cmsinfonew, "info_chanel");
		param.add(info_id);
		param.add(fun_id);
		param.add(info_code);
		param.add(info_name);
		param.add(info_kind);
		param.add(info_chanel);
		
		String info_type = Const.getStrValue(cmsinfonew, "info_type");
		String order_id = Const.getStrValue(cmsinfonew, "order_id");
		String area_code = Const.getStrValue(cmsinfonew, "area_code");
		String eff_time = Const.getStrValue(cmsinfonew, "eff_time");
		String exp_time = Const.getStrValue(cmsinfonew, "exp_time");
		param.add(info_type);
		param.add(order_id);
		param.add(area_code);
		param.add(DAOUtils.parseDateTime(eff_time));
		param.add(DAOUtils.parseDateTime(exp_time));
		
		
		String state = Const.getStrValue(cmsinfonew, "state");
		String file_id = Const.getStrValue(cmsinfonew, "file_id");
		String sequ = "0";
		param.add(state);
		param.add(file_id);
		param.add(sequ);//表明为最新
		
		String col1 = Const.getStrValue(cmsinfonew, "col1");
		String col2 = Const.getStrValue(cmsinfonew, "col2");
		String col3 = Const.getStrValue(cmsinfonew, "col3");
		String col4 = Const.getStrValue(cmsinfonew, "col4");
		String col5 = Const.getStrValue(cmsinfonew, "col5");
		String col6 = Const.getStrValue(cmsinfonew, "col6");
		String col7 = Const.getStrValue(cmsinfonew, "col7");
		String col8 = Const.getStrValue(cmsinfonew, "col8");
		param.add(col1);
		param.add(col2);
		param.add(col3);
		param.add(col4);
		param.add(col5);
		param.add(DAOUtils.parseDateTime(col6));
		param.add(DAOUtils.parseDateTime(col7));
		param.add(col8);
		
		String oper_staff_no  =null;// WebUtils.getCurrentUser(ContextHelper.getRequest()).getStaff_no();
		param.add(oper_staff_no);

		boolean result = sqlExe.excuteUpdate2(sql, param) > 0 ;
		String strResult = "false";
		if(result)
			strResult = "true";
		dict.setValueByName("result", strResult);
		
	}
	
	/**
	 * 获取用户CMS权限
	 * 
		0: VIEW_ROLE	只可以浏览CMS功能(默认都可以浏览)
		1: EDIT_ROLE	可以编辑CMS功能
		2: AUDIT_ROLE	可以编辑和审核CMS功能
		9: ADMIN_ROLE	所有权限
	 * {'fun_id':'1','fun_id':'2'}
	 * @param dict
	 * @throws FrameException
	 */
	private void getStaffRole(DynamicDict dict) throws FrameException {
		HttpSession httpSession = ContextHelper.getSession();
		List ls= (ArrayList) httpSession.getAttribute(LoginAction.KEY.CURRENT_USER_FUNCTIONS);
		TsmStaff staff=  null;//WebUtils.getCurrentUser(httpSession);
		HashMap fun_rule =new HashMap();
		for (int i = 0; i < ls.size(); i++) {
			HashMap m = (HashMap) ls.get(i);
			String key = Const.getStrValue(m, "fun_id");
			String value = Const.getStrValue(m, "role_type");
			fun_rule.put(key, value);
		}
		HashMap audit_fun = getAudit();
		dict.setValueByName("FUN_RULE", fun_rule);
		dict.setValueByName("AUDIT_FUN", audit_fun);
		dict.setValueByName("STAFF_NO", staff.getStaff_no());
	}


	private HashMap getAudit() {
		HashMap map = new HashMap();
		String auditSql=" SELECT a.fun_id,a.fun_type,COUNT(1) num FROM cms_fun a,cms_content_edit b WHERE a.file_id=b.file_id AND b.sequ='0' AND b.file_state in ('2','3') GROUP BY a.fun_id,a.fun_type"
				+" UNION ALL SELECT a.fun_id,a.fun_type,COUNT(1) num  FROM cms_fun a,cms_info_edit c WHERE a.fun_id = c.fun_id AND c.sequ='0'AND c.state in ('2','3') GROUP BY a.fun_id,a.fun_type";
		List ls = sqlExe.queryForMapList(auditSql);
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			HashMap obj = (HashMap) iterator.next();
			String fun_id = Const.getStrValue(obj, "fun_id");
			String num = Const.getStrValue(obj, "num");
			if(Integer.parseInt(num)>0){
				map.put(fun_id, "1");
			}else{
				map.put(fun_id, "0");
			}
		}
		return map;
	}

	/*状态切换*/
	private void swithOnline(DynamicDict dict) throws FrameException {
		HttpSession session = ContextHelper.getSession();
		String state = (String) dict.getValueByName("state",false);
		
		if(state.equals("")){
			state = (String) session.getAttribute(CmsConst.CMS_SESSION_KEY);
		}
		if(state!=null && state.equals(CmsConst.CMS_STATE_ONLINE)){//在线
			state = CmsConst.CMS_STATE_OFFLINE;
		}else{
			state = CmsConst.CMS_STATE_ONLINE;
		}
		session.setAttribute(CmsConst.CMS_SESSION_KEY, state);
		dict.setValueByName("FLAG", "1");//切换成功
	}

	private void getCmsInfo(DynamicDict dict) throws FrameException {
		//缓存中获取返回值
		String pageIndex=(String) dict.getValueByName("page_index", false);
		String pageSize=(String) dict.getValueByName("page_size", false);
		if(pageIndex.equals("")){
			pageIndex="1";
			pageSize="500";
		}
		CmsContext.getCmsObj().setPage_index(Integer.parseInt(pageIndex));
		CmsContext.getCmsObj().setPage_size(Integer.parseInt(pageSize));
		CmsInfo cmsInfo =new CmsCacheData().getCmsInfo();
        if(null!=cmsInfo)Const.setPageModel(dict,cmsInfo.getPageModel());
	}
	
	/**
	 * 文件处理
	 * @param dict
	 * @throws FrameException 
	 */
	private void doFileAction(DynamicDict dict) throws FrameException {
        boolean val=false;
        boolean refresh=false;
		String io_method = (String) dict.getValueByName("io_method");
		HashMap result=new HashMap();
		if(io_method.equals("read")){//根据路径读取文件内容
			String content = FileEditUtil.getFileContent();
			result.put("content", content);
		}else if(io_method.endsWith("write")){//写入文件
            refresh=true;
			String content =(String) dict.getValueByName("content");
			CmsContext.getCmsObj().putParam("content", content);
			String file_path=FileEditUtil.updateFileContent();

            //文件大小
            String size=FileUtil.getInst().formetFileSize(content.length());
            if(StringUtils.isNotEmpty(file_path)){
            	String file_id=CmsContext.getCmsObj().getStrParam("file_id"); //只针对cms_info_edit
            	String ret_file_id = editContent(file_id,file_path,size);
            	val = true;
//                //判断是否新增文件记录
//                Object obj=CmsContext.getCmsObj().getParam("sign"); //只针对cms_info_edit
//                if((null!=obj) && Boolean.valueOf(obj.toString()).booleanValue()){//新增
//                   val=addCmsContentEdit(file_path,size);
//                }else{//修改
//                   if(isCmsInfo()){
//                     val=saveCmsContentEditInfo(file_path);
//                   }else  val=saveCmsContentEdit(CmsContext.getCmsObj().getFun_id(),file_path);
//                }
//                result.put("file_path", file_path);
            	result.put("file_id", ret_file_id);
            	result.put("result", String.valueOf(val));
            }
		}
		dict.setValueByName("FILE", result);
	}

	//文件编辑操作
	public String editContent(String file_id,String file_path,String file_size){
		String sequ="";
		String desc ="";
		try{
			String staff_no=getCurrentUser().getStaff_no();
			if(StringUtils.isEmpty(file_id)){//新增
				file_id= DateFormatUtils.formatDate(new Date(System.currentTimeMillis()), CrmConstants.DATE_TIME_FORMAT_14);
				sequ="0";
				desc ="新增";
				StringBuffer addSql=new StringBuffer("insert into cms_content_edit(file_id,file_path,file_size,file_state,oper_time," +
						" oper_staff_no,sequ,descriptions) " +
				"values(?,?,?,'2',sysdate," +
				"?,?,?)");
				sqlExe.excuteUpdate(addSql.toString(),new String[]{file_id,file_path,file_size,staff_no,sequ,desc});
			}else{//修改
				//sequ =sqlExe.querySingleValue("select max(sequ)+1 from cms_content_edit where file_id=?",file_id);
				desc="修改";
				String editSql = "insert into cms_content_edit (file_id,file_path,file_size,file_state,oper_time, oper_staff_no,sequ,descriptions) " +
						" select file_id,file_path,file_size,file_state,oper_time, oper_staff_no," +
						" (select max(sequ)+1 from cms_content_edit where file_id=? )sequ,descriptions  FROM CMS_CONTENT_EDIT where file_id=? and sequ=0 ";
				sqlExe.excuteUpdate(editSql,new String[]{file_id,file_id});
				
				editSql = "update cms_content_edit set file_path=?,file_size=?,oper_staff_no=?,descriptions=?,file_state = ?,oper_time=sysdate where  file_id=? and sequ=0";
				sqlExe.excuteUpdate(editSql,new String[]{file_path,file_size,staff_no,desc,CmsKey.CMS_AUX_STATE_2,file_id});
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return file_id;
	}
	
    //cms_content_edit中新增一条记录  同时cms_info_edit中修改file_id值
    private boolean addCmsContentEdit(String file_path,String file_size){
        boolean rval=false;
        try{
            String file_id= DateFormatUtils.formatDate(new Date(System.currentTimeMillis()), CrmConstants.DATE_TIME_FORMAT_14);
            String staff_no="";
            TsmStaff staff=getCurrentUser();
            if(null!=staff){
                staff_no=staff.getStaff_no();
            }

            StringBuffer addSql=new StringBuffer("insert into cms_content_edit(file_id,file_path,file_size,file_state,oper_time,oper_staff_no,sequ,descriptions) " +
                    "values(?,?,?,'2',sysdate,?,0,'新增')");
            sqlExe.excuteUpdate(addSql.toString(),new String[]{file_id,file_path,file_size,staff_no});

            String info_id=CmsContext.getCmsObj().getInfo_id();
            String sequ=(String)CmsContext.getCmsObj().getParam("sequ");

            StringBuffer sequSql=new StringBuffer("SELECT max(sequ) sequ FROM cms_content_edit where file_id=?");
            String seq=sqlExe.querySingleValue(sequSql.toString(), new String[]{file_id});
            int nextSequ=Integer.parseInt(seq)+1;

            StringBuffer bakSql=new StringBuffer("insert into cms_info_edit(info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_idarea_code,create_time,edit_time,eff_time,exp_time,state,file_id,col1,col2,col3, " +
                    "col4,col5,col6,col7,col8,DESCRIPTIONS,OPER_STAFF_NO,sequ)  " +
                    "select info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id, " +
                    "area_code,create_time,edit_time,eff_time,exp_time,state,file_id,col1,col2,col3, " +
                    "col4,col5,col6,col7,col8,DESCRIPTIONS,OPER_STAFF_NO,?  " +
                    "from cms_info_edit where sequ=0 and info_id=?");
            sqlExe.excuteUpdate(bakSql.toString(),new String[]{String.valueOf(nextSequ),info_id});

            //修改 cms_info_edit数据
            StringBuffer updateSql=new StringBuffer("update cms_info_edit set file_id=?,oper_staff_no=?,descriptions='修改',edit_time=sysdate where info_id=? and sequ=0");
            sqlExe.excuteUpdate(updateSql.toString(),new String[]{file_id,staff_no,info_id,sequ});
            rval=true;
          //  RefreshCacheUtils.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
       return rval;
    }

    //判断是否新增文件记录 true新增 false修改
    private boolean checkFile(String file_id,String sequ){
        boolean rval=true;
        try{
            StringBuffer selSql=new StringBuffer("select file_path from cms_content_edit where file_id=? and sequ=?");
            String val=sqlExe.querySingleValue(selSql.toString(), new String[]{file_id, sequ});
            if(StringUtils.isNotEmpty(val))rval=false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return rval;
    }

    private boolean isCmsInfo(){
        boolean rval=false;
        Object obj=CmsContext.getCmsObj().getParam("cms_method");
        if(null!=obj && StringUtils.isNotEmpty(obj.toString()))rval=true;

        return rval;
    }

    private boolean saveCmsContentEditInfo(String path){
        boolean rval=false;
        try{
            String staff_no="";
            TsmStaff staff=getCurrentUser();
            if(null!=staff)staff_no=staff.getStaff_no();

            StringBuffer sequSql=new StringBuffer("SELECT max(sequ) sequ FROM cms_content_edit where file_id=?");
            String file_id=CmsContext.getCmsObj().getParam("file_id").toString();
            String seq=sqlExe.querySingleValue(sequSql.toString(), new String[]{file_id});

            int nextSequ=Integer.parseInt(seq)+1;
            StringBuffer insertSql=new StringBuffer("insert into cms_content_edit(file_id,file_name,file_path,file_size,file_state, " +
                    "oper_time,oper_staff_no,sequ) " +
                    "SELECT file_id,file_name,file_path,file_size,file_state,oper_time,oper_staff_no,? " +
                    "FROM cms_content_edit where sequ=0 and file_id=?");
            sqlExe.excuteUpdate(insertSql.toString(),new String[]{String.valueOf(nextSequ),file_id});

            StringBuffer updateSql=new StringBuffer("update cms_content_edit set oper_time=sysdate,file_state='2',descriptions='修改',file_path=?,oper_staff_no=? " +
                    "where sequ=0 and file_id=?");
            sqlExe.excuteUpdate(updateSql.toString(),new String[]{path,staff_no,file_id});

            rval=true;
           // RefreshCacheUtils.refresh();
        }catch (Exception e){
          e.printStackTrace();
        }
        return rval;
    }

    //cms_content_edit 记录 日志
    public boolean saveCmsContentEdit(String fun_id,String path){
        boolean rval=false;
        try{
            //针对 cms_fun cms_content_edit 有关联
            StringBuffer sequSql=new StringBuffer("SELECT max(sequ) sequ FROM cms_content_edit a,cms_fun b where a.file_id = b.file_id  " +
                    "AND b.state='1' and b.fun_id=?");
            String seq=sqlExe.querySingleValue(sequSql.toString(), new String[]{fun_id});
            int nextSequ=Integer.parseInt(seq)+1;

            StringBuffer insertSql=new StringBuffer("insert into cms_content_edit(file_id,file_name,file_path,file_size,file_state, " +
                    "oper_time,oper_staff_no,sequ) " +
                    "SELECT a.file_id,a.file_name,a.file_path,a.file_size,a.file_state,a.oper_time,a.oper_staff_no,? " +
                    "FROM cms_content_edit a,cms_fun b where a.file_id = b.file_id  " +
                    "AND b.state='1' and a.sequ=0 and b.fun_id=?");
            sqlExe.excuteUpdate(insertSql.toString(),new String[]{String.valueOf(nextSequ),fun_id});

            StringBuffer updateSql=new StringBuffer("update cms_content_edit set oper_time=sysdate,file_state='2',descriptions='修改',file_path=?,oper_staff_no=? " +
                    "where sequ=0 and file_id=(SELECT a.file_id from cms_content_edit a,cms_fun b  " +
                    "where a.file_id = b.file_id  " +
                    "AND b.state='1' and b.fun_id=? and a.sequ=0)");

            String staff_no="";
            TsmStaff staff=getCurrentUser();
            if(null!=staff)staff_no=staff.getStaff_no();

            List param=new ArrayList();
            param.add(path);
            param.add(staff_no);
            param.add(fun_id);
            sqlExe.excuteUpdate(updateSql.toString(),param);

            rval=true;
            //RefreshCacheUtils.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
       return rval;
    }

    //查询审核列表
    public void findListByFunId(DynamicDict dict)throws FrameException{
        int pageIndex = Const.getPageIndex(dict);
        int pageSize = Const.getPageSize(dict);

        String fun_id=dict.getValueByNameEx("FUN_ID");
        HttpSession session=ContextHelper.getSession();
        Object obj=session.getAttribute(LoginAction.KEY.CURRENT_USER);
        if(null!=obj && StringUtils.isNotEmpty(fun_id)){
            StringBuffer sql=new StringBuffer("select *  from cms_info_edit where fun_id=? and sequ=0 and state='2' order by create_time desc ");
            ArrayList param=new ArrayList();
            param.add(fun_id);

            PageModel pager = sqlExe.queryPageModelResult(sql.toString(), param, pageIndex, pageSize);
            Const.setPageModel(dict,pager);
        }
    }


    /*审核*/
    private void auditCmsInfo(DynamicDict dict)throws FrameException{
        String info_id=dict.getValueByNameEx("INFO_ID");
        String fun_id=(String)dict.getValueByName("fun_id",false);
        if(StringUtils.isNotEmpty(info_id) && info_id.trim().equals("0")){
            StringBuffer selSql=new StringBuffer("select info_id from cms_info_edit where fun_id=? and sequ='"+CmsKey.SEQU_0+"' and state='"+CmsKey.CMS_AUX_STATE_2+"'");
            info_id=sqlExe.querySingleValue(selSql.toString(),new String[]{fun_id});
        }

        if(StringUtils.isEmpty(info_id) || StringUtils.isEmpty(fun_id)){
            dict.setValueByName("result",String.valueOf(false));
            return;
        }

        boolean chk=checkCmsContent(info_id);
        boolean rval=auditCmsInfoHandle(dict); //审核 cms_info_edit
        log.debug("CmsInfoBO.auditCmsInfo:INFO_ID======>" + info_id);
        if(rval){
            //是否审核 cms_content_edit
            if(chk)rval=verifyCmsContent("AUDIT_FILE",info_id);

            //RefreshCacheUtils.refresh();
        }

        dict.setValueByName("result", String.valueOf(rval));
    }

    //是否关联有文件
    private boolean checkCmsContent(String info_id){
        boolean sign=false;
        if(StringUtils.isNotEmpty(info_id)){
            StringBuffer selSql=new StringBuffer("select info_id,info_name,fun_id,c.file_id,c.file_path from cms_info_edit t,cms_content_edit c " +
                    "where t.file_id=c.file_id and t.state in('"+CmsKey.CMS_AUX_STATE_2+"','"+CmsKey.CMS_AUX_STATE_3+"') and t.sequ=0 and c.file_state in('"+CmsKey.CMS_AUX_STATE_2+"','"+CmsKey.CMS_AUX_STATE_3+"') and c.sequ='"+CmsKey.SEQU_0+"' " +
                    "and info_id=?");
            List list=sqlExe.queryForMapListBySql(selSql.toString(),new String[]{info_id});
            if(list.size()>0)sign=true;
        }
        log.debug("是否关联有文件!=====>"+sign);
        return sign;
    }

    //调用审核cms_content_edit
    private boolean verifyCmsContent(String method,String info_id)throws FrameException{
        boolean sign=false;
        try{
            StringBuffer selSql=new StringBuffer("select file_id from cms_info_edit where info_id=? and sequ='"+CmsKey.SEQU_0+"' ");
            String file_id=sqlExe.querySingleValue(selSql.toString(),new String[]{info_id});
            DynamicDict dynamic=new DynamicDict();
            dynamic.m_ActionId="CMSCONTENTBO";
            dynamic.setValueByName("file_id",file_id);
           // dynamic.setValueByName("METHOD","AUDIT_FILE");
            dynamic.setValueByName("METHOD",method);
            DynamicDict rval=ActionDispatch.dispatch(dynamic);
            if(null!=rval){
                String val=String.valueOf(rval.getValueByName("RESULT",false));
                if(StringUtils.isNotEmpty(val)){
                    sign=Boolean.valueOf(val).booleanValue();
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return sign;
    }

    /* 删除生产表中的数据  同时将编辑表中的数据添加到生产表中
    *  @param fun_id 功能点ID
    *  table cms_info cms_info_edit
    * */
    private boolean auditCmsInfoHandle(DynamicDict dict)throws FrameException{
        //删除cms_info中的数据 将cms_info_edit中的数据添加进cms_info中
    	
    	String info_id=dict.getValueByNameEx("INFO_ID");
        String fun_id=(String)dict.getValueByName("fun_id",false);
        
        StringBuffer delSql=new StringBuffer("delete from cms_info where info_id =? " );
        int t =sqlExe.excuteUpdate(delSql.toString(),new String[]{info_id});
        //生产表中插入数据
        StringBuffer insertSql=new StringBuffer("insert into cms_info(info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id," +
                "area_code,create_time,edit_time,eff_time,exp_time,state,file_id,col1,col2,col3," +
                "col4,col5,col6,col7,col8)" +
                "select info_id,fun_id," +
                "info_code,info_name,info_kind,info_chanel,info_type,order_id," +
                "area_code,create_time,edit_time,eff_time,exp_time,1,file_id,col1,col2,col3," +
                "col4,col5,col6,col7,col8 from cms_info_edit where sequ='"+CmsKey.SEQU_0+"' and info_id=?");
        t=sqlExe.excuteUpdate(insertSql.toString(),new String[]{info_id});
        //编辑表日志
        StringBuffer selSql=new StringBuffer("select max(sequ)+1 sque from cms_info_edit where info_id=?");
        String sequ=sqlExe.querySingleValue(selSql.toString(),new String[]{info_id});
        StringBuffer insertSql2=new StringBuffer("insert into cms_info_edit(info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id, " +
                "area_code,create_time,edit_time,eff_time,exp_time,state,file_id,col1,col2,col3, " +
                "col4,col5,col6,col7,col8,DESCRIPTIONS,OPER_STAFF_NO,sequ)  " +
                "select info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id, " +
                "area_code,create_time,edit_time,eff_time,exp_time,state,file_id,col1,col2,col3, " +
                "col4,col5,col6,col7,col8,DESCRIPTIONS,OPER_STAFF_NO,?  " +
                "from cms_info_edit where sequ='"+CmsKey.SEQU_0+"' and info_id=?");
        t=sqlExe.excuteUpdate(insertSql2.toString(),new String[]{sequ,info_id});

        //修改状态 cms_info_edit   0无效 1已审核 2未审核
        TsmStaff staff=getCurrentUser();
        
        String discriptions = (String)dict.getValueByName("discriptions",false);
        discriptions = CmsKey.CMS_AUX_STATE_1_NAME+":"+discriptions;
        
        StringBuffer updateSql=new StringBuffer("update cms_info_edit set state='"+CmsKey.CMS_AUX_STATE_1+"',oper_staff_no=?,edit_time=sysdate,descriptions= ? where info_id=? and sequ='"+CmsKey.SEQU_0+"' ");
        t=sqlExe.excuteUpdate(updateSql.toString(),new String[]{staff.getStaff_no(),discriptions,info_id});

        return true;
    }

    private void  auditNotCmsInfoHandleBefore(DynamicDict dict)throws FrameException{
        String info_id=(String)dict.getValueByName("info_id",false);
        if(StringUtils.isNotEmpty(info_id) && info_id.trim().equals("0")){
            StringBuffer selSql=new StringBuffer("select info_id from cms_info_edit where fun_id=? and sequ='"+CmsKey.SEQU_0+"' and state='"+CmsKey.CMS_AUX_STATE_2+"' ");
            info_id=sqlExe.querySingleValue(selSql.toString(),new String[]{CmsContext.getCmsObj().getFun_id()});
        }//不知道要干嘛的
        if(StringUtils.isEmpty(info_id)){
            dict.setValueByName("RESULT",String.valueOf(false));
            return;
        }//也是
        boolean sign=checkCmsContent(info_id);
        boolean rval=auditNotCmsInfoHandle(dict);
        if(rval){
           if(sign)rval=verifyCmsContent("AUDIT_NOT_FILE",info_id);
        }
        dict.setValueByName("RESULT",String.valueOf(rval));
    }
    
    /**
     * cms_info_edit 审核不通过
     * @param dict
     * @return
     */
    private boolean auditNotCmsInfoHandle(DynamicDict dict){
        boolean rval=false;
        try{
            String info_id=(String)dict.getValueByName("info_id",false);
            if(StringUtils.isNotEmpty(info_id) && info_id.trim().equals("0")){
                StringBuffer selSql=new StringBuffer("select info_id from cms_info_edit where fun_id=? and sequ='"+CmsKey.SEQU_0+"' and state='"+CmsKey.CMS_AUX_STATE_2+"' ");
                info_id=sqlExe.querySingleValue(selSql.toString(),new String[]{CmsContext.getCmsObj().getFun_id()});
            }
            if(StringUtils.isEmpty(info_id)){
                dict.setValueByName("RESULT",String.valueOf(false));
                return rval;
            }
            StringBuffer selSql=new StringBuffer("select max(sequ)+1 sque from cms_info_edit where info_id=?");
            String sequ=sqlExe.querySingleValue(selSql.toString(),new String[]{info_id});

            StringBuffer insertSql=new StringBuffer("insert into cms_info_edit(info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id, " +
                    "area_code,create_time,edit_time,eff_time,exp_time,state,file_id,col1,col2,col3, " +
                    "col4,col5,col6,col7,col8,DESCRIPTIONS,OPER_STAFF_NO,sequ)  " +
                    "select info_id,fun_id,info_code,info_name,info_kind,info_chanel,info_type,order_id, " +
                    "area_code,create_time,edit_time,eff_time,exp_time,state,file_id,col1,col2,col3, " +
                    "col4,col5,col6,col7,col8,DESCRIPTIONS,OPER_STAFF_NO,?  " +
                    "from cms_info_edit where sequ='"+CmsKey.SEQU_0+"' and info_id=?");
            sqlExe.excuteUpdate(insertSql.toString(),new String[]{sequ,info_id});

            TsmStaff staff=getCurrentUser();
            
            String discriptions = (String)dict.getValueByName("discriptions",false);
            discriptions = CmsKey.CMS_AUX_STATE_3_NAME+":"+discriptions;
            
            StringBuffer updateSql=new StringBuffer("update cms_info_edit set state='"+CmsKey.CMS_AUX_STATE_3+"',oper_staff_no=?,edit_time=sysdate,descriptions = ? where info_id=? and sequ='"+CmsKey.SEQU_0+"' ");
            sqlExe.excuteUpdate(updateSql.toString(),new String[]{staff.getStaff_no(),discriptions,info_id});
            rval=true;
        }catch (FrameException e){
            e.printStackTrace();
        }
       return rval;
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
    
//    public static void main(String[] args) {
//    
//    	   String aa = "computer_fuck_111";
//    	   if(aa.lastIndexOf("_")>=-1){
//    		   aa = aa.substring(aa.lastIndexOf("_")+1);
//    		   
//    		   
//    		   Pattern pattern = Pattern.compile("^\\d+$");
//    		   Matcher isNum = pattern.matcher(aa);  
//    		   if(isNum.matches())
//    			   log.info("======"+aa);
//    		   else
//    			   log.info("=========else");
//    	   }
//    	   
//    	}
}
