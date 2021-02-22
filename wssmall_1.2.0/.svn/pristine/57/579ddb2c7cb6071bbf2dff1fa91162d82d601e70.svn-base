package com.powerise.ibss.framework;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;




public class BO {
	public static Logger m_Logger = Logger.getLogger(BO.class);

    /** BO名字 */
    public String m_Name = null;

    /** BO动作 */
    public String m_Verb =null;

    /** BO属性名称 */
    public String m_Attr = null;

    /** BO属性集合 */
    private HashMap m_Value = null;
    private boolean m_IgnoreCase = true;

    private Object m_Obj = null; //当前的Object，通过Path指定的
    
    
    public void EnableCase(boolean enable)
    {
    	m_IgnoreCase = enable;
    }
    public BO(String strName) {
        m_Name = strName;
        m_Value = new HashMap();
    }

    public BO(String strName, String strVerb) {
    	  m_Name = strName;
    	  m_Verb = strVerb;
    	  m_Attr = strName;
    	  m_Value = new HashMap();
    }
    public void setValueByName(String aName, Object aValue) throws FrameException {
    	setValueByName(aName,aValue,0);
    }
    public void setValueByName(String aName, Object aValue, int aFlag) throws FrameException{
    	Object o = null;
    	boolean bNew = false;
    	boolean isBO = false;
    	boolean isCurrentBO = false;
    	ArrayList ar = null;
        if (aValue == null || aName == null) {
            return;
        }
        if(m_IgnoreCase)
        	aName = aName.toUpperCase();
    	o = m_Value.get(aName);
    	if(o == null)   //新增属性
    		bNew = true;
    	else
    		bNew = false;
    	if (aFlag != 0 && bNew == true)
    		throw new FrameException(-22991601,"已经存在该名称的值了:"+aName);
    	//判断当前的对象是否为BO类型
    	if(o.getClass().getName().indexOf("ArrayList")!= -1) 
    	{
    		if(((ArrayList)o).size()>0 && ((ArrayList)o).get(0).getClass().getName().indexOf(".BO") != -1)
    			isCurrentBO = true;
    		else
    			isCurrentBO = false;
    	}
    	else
    		isCurrentBO = false;
    	if(aValue.getClass().getName().indexOf(".BO") != -1)  //判断是否为BO
    		isBO = true;
    	else
    		isBO = false;
    	if(isBO == false)
    	{
            m_Value.put(aName, aValue);            
        } else{  //添加BO
        	if (bNew){
        		ar = new ArrayList();
        	}else
        		ar = (ArrayList)o;
        	ar.add(aValue);        	
        }
    }
   
    public void removeValue(String aName) {
        
    }

   

    public void clear() {
        m_Value.clear();
    }

   

   
   
    public Object locateBO(String strPath) throws FrameException {
        return locateBO(strPath,-1);
    }
    /**
     * 定位到指定的Path，如果存在同名的多个Prop，则是第一个，同时返回其数量
     * 
     * @param strPath
     * @return @throws
     *         FrameException
     */
    public Object locateBO(String strPath, int iSeq) throws FrameException {
        int iCnt = 0, iSize = 0;
        String strPropName, strOrigName;
        Object obj = null;
        StringTokenizer strToken;
        //对path进行解析，分成多个包
        strToken = new StringTokenizer(strPath, ".");
        iSize = strToken.countTokens();
        iCnt = 1;//找到属性，缺省是一个
        for (int i = 0; i < iSize; i++) {

            strOrigName = strToken.nextToken();
            strPropName = strOrigName.toUpperCase();
//            if (i == 0) //这是本BO，直接从props取值
//                obj = props.get(strPropName);
//            else
//                obj = ((BO) obj).getProp(strPropName);
//            if (obj == null)
//                throw new FrameException(-22991101, strOrigName + "没有找到");
            if (obj.getClass().getName().indexOf("java.util.ArrayList") != -1) //是BO，表现为ArrayList
            {
                iCnt = ((ArrayList) obj).size();
                if (iCnt > 0) //如果是ArrayList的数据的话，里面存储的必是BO，否则之前构成时存在BUG
                {
                    if (i == (iSize - 1)) {
                        if (iSeq >= 0)
                            obj = ((ArrayList) obj).get(iSeq);
                        break;
                    } else
                        obj = ((ArrayList) obj).get(0);
                    
                    if (obj.getClass().getName().indexOf("BusiOBJ") == -1)
                        throw new FrameException(-22991102, strOrigName + "虽然是BO，但是没有存储BO数据，格式出错");
                } else
                    throw new FrameException(-22991103, strOrigName + "虽然是BO，但是没有任何数据，无法定位");

            } else {
                throw new FrameException(-22991104, strOrigName + "不是BO，或者就是其本身");
            }

        }

        return obj;
    }

    

}
