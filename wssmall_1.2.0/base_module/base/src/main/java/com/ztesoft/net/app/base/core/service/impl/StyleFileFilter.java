package com.ztesoft.net.app.base.core.service.impl;

import com.ztesoft.net.framework.util.FileBaseUtil;

import java.io.File;
import java.io.FileFilter;

public class StyleFileFilter implements FileFilter {
	private static final String[] EXTS={"css","js","jpg","png","gif","bmp"};
	@Override
	public boolean accept(File pathname) {
		String fileName = pathname.getName().toLowerCase();
		
		if(pathname.isDirectory()){
			if(fileName.equals("borders")) return false;
			if(fileName.equals("common")) return false;
			if(fileName.equals("custompage")) return false;
			if(fileName.equals(".svn")) return false;
			return true;
		}
		String ext  = FileBaseUtil.getFileExt(fileName).toLowerCase();
		for(String e:EXTS){
			if(ext.equals(e)){
				return true;
			}
		} 
		return false; 
	}

}
