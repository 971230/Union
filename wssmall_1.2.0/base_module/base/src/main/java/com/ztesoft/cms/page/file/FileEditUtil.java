package com.ztesoft.cms.page.file;

import com.ztesoft.cms.page.context.CmsContext;
import com.ztesoft.cms.page.file.vo.FileBean;
import com.ztesoft.cms.page.query.CmsConst;
import com.ztesoft.ibss.common.util.*;
import org.directwebremoting.util.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件编辑
 * 
 * @author Reason
 * @version Created Nov 2, 2012
 */
public class FileEditUtil {
	public static String encode = "UTF-8";

    private static Logger log= Logger.getLogger(FileEditUtil.class);

	/**
	 * 获取流文件内容
	 */
	public static String getFileContent() {
		String access_path = getFilePath();
		if (StringUtils.isEmpty(access_path)) {// 文件不存在
			return "";
		}
		String content = FileUtil.getInst().readFileByBytes(access_path, encode);
		return content;
	}

    /*获取文件*/
	private static String getFilePath() {
		SqlMapExe sqlExe = CmsContext.getCmsObj().getSqlExe();
		String fun_id = CmsContext.getCmsObj().getFun_id();
		String state = CmsContext.getCmsObj().getCms_state();
        boolean  sign=false;
        String file_path="";
        //在线 离线
		String sql = "SELECT a.file_path FROM cms_content_edit a,cms_fun b where a.file_id = b.file_id AND b.state='1' AND a.sequ=0  and b.fun_id=?";
		if (state.equals(CmsConst.CMS_STATE_ONLINE)) {
			sql = "SELECT a.file_path FROM cms_content a,cms_fun b where a.file_id = b.file_id AND b.state='1' AND a.file_state='1' and b.fun_id=?";
		}

       //cms_content_edit历史版本
        Object obj=CmsContext.getCmsObj().getParam("sign");
        Object file_id=CmsContext.getCmsObj().getParam("file_id");
        Object sequ=CmsContext.getCmsObj().getParam("sequ");
        Object cms_method=CmsContext.getCmsObj().getParam("cms_method");
        if(null!=cms_method)sequ="0";
        log.debug("标识符====》"+obj);
        if((null!=obj) && Boolean.valueOf(obj.toString()).booleanValue()){//新增

        }else {
            if(null!=file_id && null!=sequ){
                if(StringUtils.isNotEmpty(String.valueOf(file_id)) && StringUtils.isNotEmpty(String.valueOf(sequ))){
                    sql="select file_path from cms_content_edit where file_id=? and sequ=?";
                    sign=true;
                }
            }
        }

        if(sign){
            file_path = sqlExe.querySingleValue(sql, new String[] {file_id.toString(),sequ.toString()});
        }else {
            file_path = sqlExe.querySingleValue(sql, new String[] { fun_id });
        }
		return file_path;
	}



    /*更新文件内容 生成新文件 同步ftp服务器
    * @return 本地文件路径
    * */
	public static String updateFileContent() {
        String filePath=null;
		// 写到本地文件中
        FileBean  path=getNewFileName();
        log.debug("新文件路径:======>"+path.getPath());

        //写入的文件内容
        String content = CmsContext.getCmsObj().getStrParam("content");
        boolean val=FileUtil.getInst().writeFile(path,content,encode);
        log.debug("写本地文件结果:"+val);
        if(val){
            filePath=path.getPath();
        }
		return filePath;
	}


    //返回新文件名
    private static FileBean getNewFileName(){
        FileBean rval=null;
        StringBuffer path = new StringBuffer(KEY.RES+"/");
        String folder = "";
        String name = "";
        String postFix = "";
        
        SqlMapExe sqlExe=CmsContext.getCmsObj().getSqlExe();
        String fun_id=CmsContext.getCmsObj().getFun_id();
        String info_id = CmsContext.getCmsObj().getInfo_id();
        String servTypeSql="select serv_kind from cms_fun where fun_id=?";
        String servKind=sqlExe.querySingleValue(servTypeSql,fun_id);
        if(StringUtils.isEmpty(servKind))
        	servKind = KEY.DEFAULT_SERV_KIND;
        path.append(servKind.toLowerCase()).append("/");
        String date = getCurrDay();
        path.append(date).append("/");
        //目录ok
        folder = path.toString();
        
        String pathSql = "";
        Map content = new HashMap();
        String cms_method = CmsContext.getCmsObj().getStrParam("cms_method");
        if(StringUtils.isEmpty(cms_method)){//文件
        	pathSql =
        		"SELECT MAX(SEQU) AS SEQU " +
        		"  FROM CMS_CONTENT_EDIT A, CMS_FUN B " + 
        		" WHERE A.FILE_ID = B.FILE_ID " + 
        		"   AND B.STATE = '1' " + 
        		"   AND B.FUN_ID = ?";
        	//取得最大的序列
        	String seq_temp = sqlExe.querySingleValue(pathSql,new String[]{fun_id});
        	
        	pathSql = 
        		"SELECT a.file_path " +
        		"  FROM CMS_CONTENT_EDIT A, CMS_FUN B " + 
        		" WHERE A.FILE_ID = B.FILE_ID " + 
        		"   AND a.sequ = '0' " + 
        		"   AND B.STATE = '1' " + 
        		"   AND B.FUN_ID = ?";
    		//取sequ为0的文件路径
        	String file_path_temp = sqlExe.querySingleValue(pathSql,new String[]{fun_id});	
        	content.clear(); 
        	content.put("sequ", seq_temp);
        	content.put("file_path", file_path_temp);
        }else{//db
        	String sign = CmsContext.getCmsObj().getStrParam("sign");
        	if(!StringUtils.isEmpty(sign)&&!Boolean.getBoolean(sign)){//cms数据库的文件修改操作
        		pathSql = 
        			"SELECT MAX(a.sequ) sequ " +
        			"  FROM CMS_CONTENT_EDIT A, CMS_INFO_EDIT B " + 
        			" WHERE A.FILE_ID = B.FILE_ID " + 
        			"   AND b.sequ = '0' " + 
        			"   AND B.INFO_ID = ?";
        		//取sequ最大值
        		String seq_temp = sqlExe.querySingleValue(pathSql,new String[]{info_id});
        		pathSql = 
        			"SELECT a.file_path " +
        			"  FROM CMS_CONTENT_EDIT A, CMS_INFO_EDIT B " + 
        			" WHERE A.FILE_ID = B.FILE_ID " + 
        			"   AND a.sequ = '0' " + 
        			"   AND b.sequ = '0' " + 
        			"   AND B.INFO_ID = ? ";
        		//取sequ为0的文件路径
        		String file_path_temp = sqlExe.querySingleValue(pathSql,new String[]{info_id});
            	content.clear(); 
            	content.put("sequ", seq_temp);
            	content.put("file_path", file_path_temp);
        	}else{//cms数据库操作的文件新增操作
        		content.put("sequ", "-1");//修复了seq为0和为1 都是_001的bug
        		content.put("file_path", "");
        	}
        }
        String sequ = Const.getStrValue(content, "sequ");
        String file_path = Const.getStrValue(content, "file_path");
        if(StringUtils.isEmpty(file_path)){
        	name = getFileName();
        	postFix=".html";
        	file_path = folder+name+".html";
        }else{
//        	 String file=file_path;
             //folder=file_path.substring(0,file_path.lastIndexOf("/")+1);
             name=file_path.substring(file_path.lastIndexOf("/")+1,file_path.lastIndexOf("."));//获取文件名
             if(name.lastIndexOf("_")>=-1){//文件名字带'_'结尾
            	 String postName = name.substring(name.lastIndexOf("_")+1);
            	Pattern pattern = Pattern.compile("^\\d+$");
      		   	Matcher isNum = pattern.matcher(postName);   
      		   	if(isNum.matches())//结尾部分为数字形式（证明是版本号）
      		   		name=name.substring(0,name.lastIndexOf("_"));
             }
             postFix=file_path.substring(file_path.lastIndexOf("."));
        }
        NumberFormat format = new DecimalFormat("000");
        
        rval=new FileBean();
        rval.setFolder(folder);
        rval.setPath(folder+name+"_"+ format.format(Integer.parseInt(sequ)+1)+postFix);
        rval.setName(name+"_"+ format.format(Integer.parseInt(sequ)+1)+postFix);
        rval.setPostfix(postFix);
//        
//        
//        /*获取当前文件的最大版本号*/
//        StringBuffer sequSql=new StringBuffer("SELECT max(sequ) sequ FROM cms_content_edit a,cms_fun b where a.file_id = b.file_id  " +
//                "AND b.state='1' and b.fun_id=?");
//        String seq=sqlExe.querySingleValue(sequSql.toString(),new String[]{fun_id});
//        StringBuffer sel=new StringBuffer("select serv_kind from cms_fun where fun_id=?");
//        String serv_kind=sqlExe.querySingleValue(sel.toString(),new String[]{fun_id});
//        if(StringUtils.isEmpty(serv_kind))serv_kind=KEY.OTHER;
//        serv_kind=serv_kind.toLowerCase();
//        NumberFormat format = new DecimalFormat("000");
//        if(!StringUtils.isEmpty((String) CmsContext.getCmsObj().getParam("file_id"))){
//            sequSql=new StringBuffer("SELECT max(sequ) sequ FROM cms_content_edit where file_id=?");
//            seq=sqlExe.querySingleValue(sequSql.toString(),new String[]{CmsContext.getCmsObj().getParam("file_id").toString()});
//        }
//        if(StringUtils.isEmpty(seq)){
//        	seq="0";
//        }
//        String file_path=getFilePath();//文件全路径
//        //已经有文件存在的则应用源文件路径 没有则生成新路径
//        if(StringUtils.isNotEmpty(seq) && StringUtils.isNotEmpty(file_path)){
//            String file=String.valueOf(file_path);
//            String folder=file.substring(0,file.lastIndexOf("/")+1);
//
//            String name=file.substring(file.lastIndexOf("/")+1,file.lastIndexOf("."));//获取文件名
//            if((name.lastIndexOf("_")!=-1) && (name.indexOf("_")!=name.lastIndexOf("_"))){//
//                name=name.substring(0,name.lastIndexOf("_"));
//            }
//
//            String hou=file.substring(file.lastIndexOf("."));
//
//            rval=new FileBean();
//            rval.setFolder(KEY.RES+"/"+serv_kind+"/"+KEY.CURRENT_DAY+"/");
//            rval.setPath(KEY.RES+"/"+serv_kind+"/"+KEY.CURRENT_DAY+"/"+name+"_"+ format.format(Integer.parseInt(seq)+1)+hou);
//            rval.setName(name+"_"+ format.format(Integer.parseInt(seq)+1)+hou);
//            rval.setPostfix(hou);
//        }else {
//            //默认html文件
//            String prefix=".html";
//            rval=new FileBean();
//            rval.setFolder(KEY.RES+"/"+serv_kind+"/"+KEY.CURRENT_DAY+"/");
//            rval.setName(getFileName()+prefix);
//            rval.setPath(rval.getFolder()+"/"+rval.getName());
//        }
        return rval;
    }

    //生成文件名
    private static synchronized String getFileName(){
        return DateFormatUtils.formatDate(new Date(System.currentTimeMillis()), CrmConstants.DATE_TIME_FORMAT_14);
    }

    /*
    * 文件目录结构 /res+/模块/+日期+文件类型
    * */
    public static class KEY{
        public static final String RES="/res";

       public static final String CURRENT_DAY=DateFormatUtils.getFormatedDateTime8();
        
        public static final String DEFAULT_SERV_KIND="FCT";
        

        public static final String IMAGES="images";

        public static final String OTHER="other";
    }
    
    
    public static final String getCurrDay(){
    	return DateFormatUtils.getFormatedDateTime8();
    }

}
