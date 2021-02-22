package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;




/**
 * @Description csv 文件读取
 * @author  zhangJun
 * @date    2016年10月27日
 */
public class MoneyAuditCSVUtils {
	private static Logger logger = Logger.getLogger(MoneyAuditCSVUtils.class);
    /**
     * 导入
     * 
     * @param file csv文件(路径+文件)
     * @return
     */
    private static List<String> getRowData(File file,boolean is_zbtb){
        List<String> dataList=new ArrayList<String>();
        BufferedReader br=null;
        try {
        	DataInputStream in = new DataInputStream(new FileInputStream(file));
            br= new BufferedReader(new InputStreamReader(in,"GBK"));
            String temp = ""; 
            while((temp=br.readLine()) != null){ 
            	if(is_zbtb){
            		temp=temp.replace("\"=\"\"", "");
            		temp=temp.replace("\"\"\"", "");
            	}
            	dataList.add(temp);
            } 
            br.close();
         }catch (Exception e) {
        	 e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return dataList;
    }
    public static List<Map<String,String>> getBatchMap(File file,int titleInRow,Map<String,String> fieldMap,Map<String,String> addMap,boolean is_zbtb) throws Exception{
    	List<Map<String,String>>  ListData=new ArrayList<Map<String,String>>();
    	List<String> rowList=MoneyAuditCSVUtils.getRowData(file,is_zbtb);
    	if(rowList!=null&&rowList.size()>=titleInRow){
    		//标题获取
    		String titleRowStr=rowList.get(titleInRow-1);
    		String[] titleArray={};
    		if(titleRowStr!=null&&!titleRowStr.equals("")){
    			titleArray=titleRowStr.split(",");
    		}
    		//获取内容
    		for (int i = 0; i < rowList.size(); i++) {//行
    			String[] 	rowArray=rowList.get(i).split(",");
    			Map<String,String> rowMap=new HashMap<String, String>();
    			rowMap.putAll(addMap);
    			for (int j = 0; j < rowArray.length; j++) {//列 
    				
					String callContent=rowArray[j];//某行的列的内容 value
					String titleStr=titleArray[j].trim();//标题
					String fieldStr=fieldMap.get(titleStr);//key  //根据名称获取字段
					if(fieldStr!=null&&!fieldStr.equals("")){
						rowMap.put(fieldStr, callContent);
					}
				}
    			if(i>=titleInRow){//只放入标题以下的行
    				ListData.add(rowMap);
    			}
    			
			}
    	}else{
    		throw new Exception("标题行不正确");
    	}
		return ListData;
    }
    
    public static void main(String[] args) {
    	test1();
    	//test2();
    }
    public static void test2(){
    	try {/*
    	File file = new File("E://AUDIT_ZB_DETAILS.csv");  
    	 FileInputStream fr = new FileInputStream("E://AUDIT_ZB_DETAILS.csv");
         CSVReader reader = new CSVReader(new InputStreamReader(fr, "gbk"));
         int a=0;
         while(reader.readNext()!=null){
        	 a++;
        	 Object[]row = reader.readNext();
             System.out.printf("%s, %s \r\n", row[0], row[1]);
             if(a>=3){
            	 System.out.printf("%s, %s \r\n", row[0], (Long)row[1]);
             }
         }
         
    	*/} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public static void test1(){

		File file=new File("E://AUDIT_ZB_DETAILS.csv");
		Map<String,String> fieldMap=new HashMap<String, String>();
 		fieldMap.put("订单状态","STUATS");
 		fieldMap.put("订单ID","ORDER_ID");

		Map<String,String> addMap=new HashMap<String, String>();
		addMap.put("BATCH_NUMBER", "2016-10-07");
		int titleInRow=3;
		try {
			List<Map<String,String>> listData=MoneyAuditCSVUtils.getBatchMap(file, titleInRow, fieldMap, addMap,false);
			int a=1;
			for (Map<String, String> map : listData) {
				Set<String> set=map.keySet();
				a++;
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					
					String key = (String) iterator.next();
					String value = map.get(key);
					logger.info(key +" : " +value);
				}
				logger.info("###############################");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
    }
    
    
}