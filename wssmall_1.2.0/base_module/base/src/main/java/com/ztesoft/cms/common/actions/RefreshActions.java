package com.ztesoft.cms.common.actions;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.ibss.common.util.StringUtils;

//import com.ztesoft.cms.utils.RefreshCacheUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-14
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 *
 * insert into tfm_services(service_name,module_id,service_desc,class_name)
 values('REFRESHS_ACTIONS','RA','刷新缓存类','com.ztesoft.cms.common.actions.RefreshActions')
 */
public class RefreshActions implements IAction {
    public static class KEY{
        public static final String METHOD="METHOD";

        public static final String REFRESH_CMS="REFRESH_CMS";
    }

    @Override
	public int perform(DynamicDict dict) throws FrameException {
        String method=String.valueOf(dict.getValueByName(KEY.METHOD,false));

        if(StringUtils.isNotEmpty(method)){
            if(method.equals(KEY.REFRESH_CMS)){
                this.refreshCms(dict);
            }
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //
    private void refreshCms(DynamicDict dict) throws FrameException {
        //RefreshCacheUtils.refresh();
        dict.setValueByName("RESULT",String.valueOf(true));
    }
}
