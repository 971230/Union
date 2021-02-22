package commons;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class PlatService {
	
	public static String system_tag = "";

	/**
	 * 
	 * @param <T>
	 * @param province
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static <T> T getPlatInstance(Class<T> cla) throws Exception {
		String IClassName = cla.getSimpleName();
		
		if("".equals(system_tag)){
			system_tag = CommonTools.getSourceForm();
		}
		
		
		//String classNameInst = cla.getPackage().getName()+"."+ IClassName+ "Inst";
		//String classNameLacal =classNameInst
		//		+ system_tag;  
		String classNameLacal = IClassName+"Inst"+system_tag;
		Object t = null;
		try{
		 t =  SpringContextHolder.getBean(classNameLacal);
		}catch(Exception e){
			t = SpringContextHolder.getBean(IClassName+"Inst");
		}
		
		
		/*Class claName = null;
		*//**
		 * 修改报错情况：
		 * 当本地目录没实现的时候会报错
		 *//*
		try {
			claName = Class.forName(classNameLacal);
		} catch (Exception e) {
			
		}
		
		try {
			if(claName == null){
				claName = Class.forName(classNameInst);
			}
		} catch (Exception e) {
		}
		
		if(claName !=null)
		t = (T) claName.newInstance();*/
		
		return (T)t;

	}
	
	public static <T> T getPlatServInstance(Class<T> cla) {
		String IClassName = toLowerCaseFirstOne(cla.getSimpleName());
		
		String classNameLacal = IClassName + CommonTools.getSourceForm();
		Object t = null;
		try{
			t = SpringContextHolder.getBean(classNameLacal);
		}catch(NoSuchBeanDefinitionException e){
			t = SpringContextHolder.getBean(IClassName);
		}
		
		return (T)t;

	}
	public static String toLowerCaseFirstOne(String name) {
        char[] ch = name.toCharArray();
      
        ch[0] = Character.toLowerCase(ch[0]);
       
        StringBuffer a = new StringBuffer();
        a.append(ch);
        return a.toString();
    }
}
