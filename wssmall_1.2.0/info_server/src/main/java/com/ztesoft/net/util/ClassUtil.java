package com.ztesoft.net.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import params.ZteBusiRequest;
import zte.net.common.annontion.context.request.DefaultActionRequestDefine;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.common.annontion.context.request.RequestFieldDefinition;

/**
 * 
 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
 * 
 * @author : 张金叶
 * @createTime : 2015-3-3
 * @version : 1.0
 * @documentPath:
 */
public class ClassUtil {
	//通过这个对象获取表名 字段名称
		public static DefaultActionRequestDefine context = null;
		static{
			context = DefaultActionRequestDefine.getInstance();
		}
	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @作者 MoChunrun
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class> classes,
			Class anntionClass) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			@Override
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes,
						anntionClass);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					Class clazz = Thread.currentThread()
							.getContextClassLoader()
							.loadClass(packageName + '.' + className);
					// 获取注解
					Annotation fact = clazz.getAnnotation(anntionClass);
					if (fact != null)
						classes.add(clazz);
				} catch (Error e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @作者 MoChunrun
	 * @param pack
	 *            包路径
	 * @param recursive
	 *            是否循环迭代
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set<Class> getClasses(String pack, Class anntionClass,
			boolean recursive) {
		// 第一个class类的集合
		Set<Class> classes = new LinkedHashSet<Class>();
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				try {
					// 获取下一个元素
					URL url = dirs.nextElement();
					// 得到协议的名称
					String protocol = url.getProtocol();
					// 如果是以文件的形式保存在服务器上
					if ("file".equals(protocol)) {
						// System.err.println("file类型的扫描");
						// 获取包的物理路径
						String filePath = URLDecoder.decode(url.getFile(),
								"UTF-8");
						// 以文件的方式扫描整个包下的文件 并添加到集合中
						findAndAddClassesInPackageByFile(packageName, filePath,
								recursive, classes, anntionClass);
					} else if ("jar".equals(protocol)) {
						// 如果是jar包文件
						// 定义一个JarFile
						// System.err.println("jar类型的扫描");
						JarFile jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							try {
								// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
								JarEntry entry = entries.nextElement();
								String name = entry.getName();
								// 如果是以/开头的
								if (name.charAt(0) == '/') {
									// 获取后面的字符串
									name = name.substring(1);
								}
								// 如果前半部分和定义的包名相同
								if (name.startsWith(packageDirName)) {
									int idx = name.lastIndexOf('/');
									// 如果以"/"结尾 是一个包
									if (idx != -1) {
										// 获取包名 把"/"替换成"."
										packageName = name.substring(0, idx)
												.replace('/', '.');
									}
									// 如果可以迭代下去 并且是一个包
									if ((idx != -1) || recursive) {
										// 如果是一个.class文件 而且不是目录
										if (name.endsWith(".class")
												&& !entry.isDirectory()) {
											// 去掉后面的".class" 获取真正的类名
											String className = name.substring(
													packageName.length() + 1,
													name.length() - 6);
											// 添加到classes
											// classes.add(Class.forName(packageName
											// + '.'+ className));
											try {
												Class clazz = Thread
														.currentThread()
														.getContextClassLoader()
														.loadClass(
																packageName
																		+ '.'
																		+ className);
												// 获取注解
												Annotation fact = clazz
														.getAnnotation(anntionClass);
												if (fact != null)
													classes.add(clazz);
											} catch (Error ex) {
												ex.printStackTrace();
											}
										}
									}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
								continue;
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * 获取表名
	 * 
	 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
	 * @author      : 张金叶
	 * @createTime  : 2015-3-3
	 * @version     : 1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  static String getTableName(String clazzName){
		String tableName="";
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = context.getActionRequestServiceMap(clazzName);
		if(serviceDefinition!=null){
			tableName=serviceDefinition.getTable_name();
		}
		return tableName;
	}
	
	/**83279754
	 * 获取字段名称
	 * 
	 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
	 * @author      : 张金叶
	 * @createTime  : 2015-3-3
	 * @version     : 1.0
	 */
	public static List<String> getFieldName(String clazzName){
		List<RequestFieldDefinition> fieldDefinitions = context.getActionRequestFieldsMap(clazzName);
		 List<String> listName=new ArrayList<String>(16);
		if(fieldDefinitions!=null&&fieldDefinitions.size()>0){
			for(RequestFieldDefinition df:fieldDefinitions){
				listName.add(df.getService_name());
			}
		}
		return listName;
	}
}
