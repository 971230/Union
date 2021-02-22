package com.ztesoft.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListToTreeModule {
	
	/**
	 * 
	 * @param list					通过DAO从数据库中查询出来的值对象列表
	 * @param parentId			上级ID的值，用于和主键字段关联
	 * @param masterField	VO的主键字段
	 * @param parentField		VO和主键字段关联的字段
	 * @return
	 * @throws Exception
	 */
	public ArrayList getTreeModule(List list, String parentIdValue,String masterField,String parentField) throws Exception{
		ArrayList firstMenus = new ArrayList(); 
		
		for(int i = 0; i <list.size(); i ++){
			String superId  = "" ;
			Object vo = list.get(i);
			Method[] methodList = vo.getClass().getMethods();
			for( int m = 0; m < methodList.length; m ++ ){
				Method method = methodList[m];
				if( method.getName().equalsIgnoreCase( ("get" + parentField) ) ){
					superId =	(String)method.invoke(vo,null);
				} 
			}
			if( parentIdValue.equals(superId)){
				TreeModule treeMenuVO = new TreeModule();
				treeMenuVO.setData( list.get(i));
				treeMenuVO.setChildren( new ArrayList() );
				firstMenus.add( treeMenuVO);
			}
		}

		for( int i = 0; i < firstMenus.size(); i ++ ){
			TreeModule firstMenuVO = (TreeModule)firstMenus.get(i);
			buildTree( list, firstMenuVO, masterField, parentField );
		}
		return firstMenus;
	}

	public void buildTree(List source, TreeModule dest, String masterField, String parentField) throws Exception {
		
		Object obj = dest.getData();
		Method[] objMethodList = obj.getClass().getMethods();
		String masterId = "" ;
		for( int i = 0; i < objMethodList.length; i ++ ){
			Method m = objMethodList[i];
			if( m.getName().equalsIgnoreCase(("get" + masterField))){
				masterId = (String)m.invoke(obj,null);
			}
		}
		
		for (int i = 0; i < source.size(); i++) {
			Object vo =source.get(i); 
			String superId  = "" ;
			Method[] methodList = vo.getClass().getMethods();
			for( int m = 0; m < methodList.length; m ++ ){
				Method method = methodList[m];
				if( method.getName().equalsIgnoreCase( ("get" + parentField) ) ){
					superId = (String)method.invoke(vo,null);
				}
			}
			
			//if (superId.equals(masterId)) {
			if( masterId.equals(superId)){
				TreeModule child = new TreeModule();
				child.setData(vo);
				child.setChildren(new ArrayList());
				child.setData( vo );

				dest.getChildren().add(child);
				buildTree(source, child,masterField, parentField);
			}
		}
	}
}
