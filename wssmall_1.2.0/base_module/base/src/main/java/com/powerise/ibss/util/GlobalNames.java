package com.powerise.ibss.util;

import java.util.ArrayList;
/**
应用服务器端全局变量定义
*/
public class GlobalNames {
    
    
    /**保存错误号的Attribute名称*/
    public static String ERROR_ATTR ="error";
    
    /**HTML文件中控件名字的开始符*/
    
       public static final String BEGING_TAG ="para_";


   /**数据库连接方式 1：DataSource 2：JDBC THIN*/
   public static int DB_CONN_MODE =1;//(APP)

   /**应用服务器初始化Factory*/
   public static String INITIAL_FACTORY = "weblogic.jndi.WLInitialContextFactory";//(APP/WEB)
   public static String URL_PROVIDER = "t3://172.18.2.55:7001";//(APP/WEB)
   //缺省的数据库连接参数，使用连接池
   public static String DBPOOL_URL="";
   public static String DBPOOL_DRIVER="";

   /**数据源名称*/
   public static String DATA_SOURCE = "ibss";//(APP)
   /**应用服务器不用于业务处理的数据源*/
   public static String OTHER_DATA_SOURCE ="other_ibss";

   public static String URL ="";//(APP)
   public static String DRIVER ="";//(APP)
   public static String DB_USER_NAME ="";//(APP)
   public static String DB_PASSWORD ="";//(APP)

   /**系统虚拟路径*/
   public static String WEB_PATH = "";//(WEB)

   /**系统名称*/
   public static String SYSTEM_NAME = "中国电信网上营业厅系统";//(APP/WEB)
   /**开发商名字*/
   public static String SOFTWARE_NAME = "中兴软创CRM产品部";//(APP/WEB)

   /**项目所属模式 0:开发模式 1：产品模式*/
   public static int PROJECT_MODE = 1;//(APP/WEB)

   /**是否对中文进行ISO 到GBK的转换*/
   public static boolean ISO_TO_GBK = false;//(APP/WEB)

   /**是否对中文进行GBK 到ISO的转换*/
   public static boolean GBK_TO_ISO = false;//(APP/WEB)

   /**输出到终端还是输出到文件, 0:终端 1：文件(文件位置为ibss.xml配置的文件位置)*/
   public static int LOG_TYPE = 0;//(WEB)

   /**是否打印到终端*/
   public static boolean PRINT_TERMINAL =false;//(APP/WEB)

   /**是否把错误信息输出到数据库表tfm_err_log*/
   public static boolean ERR_TO_DB =true;//(APP)

   /**EAI XML编码方式*/
   public static String EAI_ENCODING ="gb2312";//(APP)

   /**是否从配置文件进行数据Load*/
   public static boolean CONFIG_LOADED =false;//(APP/WEB)
   
   /**系统是否使用LDAP*/
   public static boolean USE_LDAP =false;//APP
   
   /**系统是否通过EAI通讯， true:是；false: 否，不通过EAI通讯则通过BO XML文件进行模拟*/
   public static boolean USE_EAI =false; //APP
   
   /**系统是否对通过EAI通讯的BO XML记录到$IBSSHOME/bo目录*/
   public static boolean LOG_EAI_MSG =false; //APP
   /**EAI 队列列表*/
   public static ArrayList EAI_QUERYS =null;
}
