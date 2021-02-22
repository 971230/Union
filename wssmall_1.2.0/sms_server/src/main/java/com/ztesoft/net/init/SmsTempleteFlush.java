package com.ztesoft.net.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.util.Logger;

import com.ztesoft.ibss.common.util.ConnectionManager;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.SqlMapExe;
import com.ztesoft.net.config.SmsTempleteConfig;
import com.ztesoft.net.mall.core.timer.CheckTimerServer;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-10-10 20:58
 * To change this template use File | Settings | File Templates.
 * 刷新短信模板定时任务
 */
public class SmsTempleteFlush {
    Logger logger = Logger.getLogger(SmsTempleteFlush.class);

   
    public void execute() {
    	//ip验证处理 add by wui所有定时任务都需要加上限制条件
  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"execute"))
  			return ;
        SmsTempleteConfig.instance().clear();

        logger.debug("刷新短信模板!");
        String sql = "select * from ES_SMS_TEMPLETE";
        try {
            ConnectionManager.beginTrans(false);
            if(ConnectionManager.getConnection()==null)return;

            SqlMapExe sqlMapExe = new SqlMapExe(ConnectionManager.getConnection());
            List list = sqlMapExe.queryMapList(sql, new String[]{});
            logger.debug("刷新记录数："+list.size());
            Map map = null;
            String key, value;
            for (Object obj : list) {
                if (null == obj) continue;

                map = (HashMap) obj;
                key = Const.getStrValue(map, "sms_key");
                value = Const.getStrValue(map, "sms_value");
                SmsTempleteConfig.instance().setTemplete(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                if (null != ConnectionManager.getConnection()) ConnectionManager.close();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
