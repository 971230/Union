/*
 * Created on 2004-1-9
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.powerise.ibss.framework;


import com.powerise.ibss.util.IbssClassLoader;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FrameSystem implements IAction {
	@Override
	public int perform(DynamicDict aDynamicDict) throws FrameException
	{
		String strActionId = aDynamicDict.m_ActionId;
		if(strActionId.equals("MFM_001")) 
			return MFM_001(aDynamicDict);
		return 0;
	}
    private int MFM_001(DynamicDict dict) throws FrameException
	{
    	IbssClassLoader loader = new IbssClassLoader();
    	try {
			loader.loadClass((String)dict.getValueByName("CLASS_NAME"));
		} catch (ClassNotFoundException e) {
			throw new FrameException(-22991001,"动态Load Class失败"+e.getMessage());
		}finally{
			loader = null;
		}
    	return 0;
    }
}