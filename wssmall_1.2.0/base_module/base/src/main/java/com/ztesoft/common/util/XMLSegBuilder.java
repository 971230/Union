package com.ztesoft.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class XMLSegBuilder
{
    /**
     * 将vo列表转换为以items包含的xml字符串（每个vo为一个item节点）
     * @param alVOs vo列表，如果vo列表本身构成树形结构，则需要按其在树中的路径进行排序(在数据库表中的字段一般为path_code)
     * @return vo列表构成的适用于TreeList组件的xml字符串
     */
    public static String toXmlItems(List alVOs)
    {
        if(alVOs==null || alVOs.size()<1)
        {
            return "<items/>";
        }

        if(((XMLItem)alVOs.get(0)).pathInTree()==null || ((XMLItem)alVOs.get(0)).pathInTree().indexOf(".")<=0 )
        {
            return toListItems(alVOs);
        }
        else
        {
            return toTreeItems(alVOs);
        }
    }
    
    /**
     * 根据记录集构建XML树结构片断，用于存在树结构的vo，转换为树结构的item嵌套节点
     * @param alVOs VO集，必须按path_code排序
     * @return XML格式的树数据，无xml头
     */
    public static String toTreeItems(List alVOs)
    {
        StringBuffer sbXml = new StringBuffer();
        String pathCur;
        String pathPre = "";
        LinkedList llTree = new LinkedList();
        llTree.add("");
        
        sbXml.append("<items>");
        XMLItem vo;
        Iterator iterVO = alVOs.iterator();
        while(iterVO.hasNext())
        {
            vo = (XMLItem)iterVO.next();
            pathCur = vo.pathInTree()+".";
            if(!pathCur.startsWith(pathPre)) //路径不以前一个元素的路径开头，不是其子节点，需要对以前的未封闭节点添加结束标签
            {
                pathPre = (String)llTree.removeLast();
                while(!pathCur.startsWith(pathPre)) //遍历以前处理过的未封闭节点，若不是其子节点则对其添加结束标签
                {
                    sbXml.append("</item>");
                    pathPre = (String)llTree.removeLast();
                }
                llTree.addLast(pathPre);
            }
            llTree.addLast(pathCur);
            vo.toXmlItemUnclosed(sbXml);
            pathPre = pathCur;
        }
        for(int i=1; i<llTree.size(); i++) //最后处理所有未封闭的节点，添加结束标签
        {
            sbXml.append("</item>");
        }
        sbXml.append("</items>");
        return sbXml.toString();
    }
    
    /**
     * 根据记录集构建XML树结构片断，用于没有树结构的对象集，直接将列表的每个元素转换为一个封闭的item节点
     * @param alVOs VO集，必须按path_code排序
     * @return XML格式的树数据，无xml头
     */
    public static String toListItems(List alVOs)
    {
        StringBuffer sbXml = new StringBuffer();
        sbXml.append("<items>");
        XMLItem vo;
        Iterator iterVO = alVOs.iterator();
        while(iterVO.hasNext())
        {
            vo = (XMLItem)iterVO.next();
            vo.toXmlItemUnclosed(sbXml);
            sbXml.append("</item>");
        }
        sbXml.append("</items>");
        return sbXml.toString();
    }
    
//    /**
//     * 根据记录集构建XML树结构片断
//     * @param rsst 结果集，其第一列必须为path_code，表示该记录在树中的路径
//     * @param namesFldToAttr 列映射到节点的属性名 
//     * @return XML格式的树数据，无xml头
//     * @throws SQLException
//     * @deprecated
//     */
//    public static String buildTreeFromResultSet(ResultSet rsst, String[] namesFldToAttr) throws SQLException
//    {
//        StringBuffer sbXml = new StringBuffer();
//        String pathCur;
//        String pathPre = "";
//        LinkedList llTree = new LinkedList();
//        llTree.add("");
//        sbXml.append("<items>");
//        while(rsst.next())
//        {
//            pathCur = rsst.getString(1)+".";
//            if(!pathCur.startsWith(pathPre)) //new subnode
//            {
//                pathPre = (String)llTree.removeLast();
//                while(!pathCur.startsWith(pathPre))
//                {
//                    sbXml.append("</item>");
//                    pathPre = (String)llTree.removeLast();
//                }
//                llTree.addLast(pathPre);
//            }
//            llTree.addLast(pathCur);
//            sbXml.append("<item ");
//            for(int i=0; i<namesFldToAttr.length; i++)
//            {
//                sbXml.append(namesFldToAttr[i]);
//                sbXml.append("='");
//                sbXml.append(escapeXMLStringValue(rsst.getObject(i+1)));
//                sbXml.append("' ");
//            }
//            sbXml.append(">");
//            pathPre = pathCur;
//        }
//        if(llTree.size()>1)
//        {
//            for(int i=1; i<llTree.size(); i++)
//            {
//                sbXml.append("</item>");
//            }
//        }
//        else
//        {
//            for(int i=0; i<namesFldToAttr.length; i++)
//            {
//                sbXml.append(namesFldToAttr[i]);
//                sbXml.append("=' '");
//            }
//        }
//        sbXml.append("</items>");
//        return sbXml.toString();
//    }
    
    /**
     * 根据结果集构建XML结构片断
     * @param rsst 结果集，各记录为平级实体
     * @param namesFldToAttr 列映射到节点的属性名 
     * @return XML格式的树数据，无xml头
     * @throws SQLException
     */
    public static String buildNodesFromResultSet(ResultSet rsst, String[] namesFldToAttr) throws SQLException
    {
        StringBuffer sbXml = new StringBuffer();
        sbXml.append("<items>");
        while(rsst.next())
        {
            sbXml.append("<item ");
            for(int i=0; i<namesFldToAttr.length; i++)
            {
                sbXml.append(namesFldToAttr[i]);
                sbXml.append("='");
                sbXml.append(escapeXMLStringValue(rsst.getObject(i+1)));
                sbXml.append("' ");
            }
            sbXml.append("/>");
        }
        sbXml.append("</items>");
        return sbXml.toString();
    }
    
    public static String escapeXMLStringValue(Object objToStr)
    {
    	String ret = (objToStr == null ? "":objToStr.toString() );
    	ret = ret.replaceAll( "&", "&amp;") ;//替换掉特殊字符&
    	ret = ret.replaceAll("'", "&apos;") ;//替换掉特殊字符单引号
    	ret = ret.replaceAll( "<", "&lt;") ;//替换掉特殊字符小于号
    	ret = ret.replaceAll( "<", "&gt;") ;//替换掉特殊字符大于号
    	ret = ret.replaceAll( "\"", "&quot;") ;//替换掉特殊字符双引号
    	return ret ;
        //return objToStr==null ? "":objToStr.toString();
    }
    
    /**
     * 获取XML文件字符串中特定结点的值
     * @param xmlStr
     * @param itemName
     * @return
     */
    public static ArrayList getXMLItem(String xmlStr, String itemName){
    	ArrayList returnValue = new ArrayList();
    	String tempXml = "";
    	boolean flag = true;
    	if(xmlStr==null || "".equals(xmlStr))
    		return returnValue;
    	
    	if(xmlStr.indexOf(itemName)>0){
    		flag = true;
    		tempXml = xmlStr;
    	}
    	
    	while(flag){
    		String itemvalue = tempXml.substring(tempXml.indexOf("<"+itemName+">") + itemName.length() + 2, tempXml.indexOf("</"+itemName+">"));
    		returnValue.add(itemvalue);
    		tempXml = tempXml.substring(tempXml.indexOf("</"+itemName+">") + itemName.length() + 2);
    		if(tempXml.indexOf(itemName)<0){
        		flag = false;
    		}
    	}
    	
    	return returnValue;
    }
}
