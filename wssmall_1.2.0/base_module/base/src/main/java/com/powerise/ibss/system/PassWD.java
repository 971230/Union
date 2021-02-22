package com.powerise.ibss.system;

import org.apache.log4j.Logger;

/**加、解密类*/
public class PassWD{
   private static Logger m_Logger=null;
   public static void main(String[] args){
	  
      PassWD app =new PassWD();
      String jia ="lizhi";
      String jia1 =PassWD.pWORD(jia, 1);
      String jia2 =PassWD.pWORD(jia1, 2);
	  
	 
      m_Logger.debug(jia +"\t"+jia1+"\t"+jia2);
   }
   
   private void getLogger(){
		if(m_Logger==null)
			m_Logger = Logger.getLogger(getClass().getName()); 
	}
      /**
      对sSource，进行加密、解密操作。
      @param sSource String ：操作的字符串
      @param iFlag int :操作类型 1-加密  2-解密
      @return String :加密或解密后的字符串
   */


   public static String pWORD(String sSource, int iFlag){
      int li_len,i,li_asc,li_rand,li_head;
      String ls_i,ls_head,ls_code="";
	  //getLogger();
	  m_Logger = Logger.getLogger("PassWD().pWORD()");
      if(iFlag ==1){
         li_len=sSource.length();
         
//       double rand =Math.random();
         li_head=(int)(Math.random()*10);
         if(li_head ==0)
            li_head =1;
         for(i=0; i<li_len; i++){
            int rand2 =(int)(Math.random() *94);
            if(rand2 ==0)
               rand2 =1;
            li_rand=rand2+32;
            li_asc=(sSource.substring(i,i+1).charAt(0));
            ls_i=String.valueOf((char)(li_asc -i));

            if(li_asc+i+li_head>126){
               if(li_rand%2 ==1)
                  li_rand=li_rand+1;
               ls_i=String.valueOf((char)(li_rand))+String.valueOf((char)(li_asc -i -li_head));
            }else{
               if(li_rand%2 ==0) 
                  li_rand=li_rand+1;
               ls_i=String.valueOf((char)(li_rand))+String.valueOf((char)(li_asc +i +li_head));
            }
            ls_code=ls_code+ls_i;
         }
         int rand1 =(int)(Math.random()*9);
         if(rand1 ==0)
            rand1 =1;
         ls_code=String.valueOf((char)(rand1*10+li_head+40))+ls_code;
      }else{
         int li_ret;
         li_len=sSource.length();
         ls_code="";
         li_ret=(sSource.substring(0,1).charAt(0)) %10;
         for( i=2 ;i <li_len; i=i+2){
            li_asc=(sSource.substring(i,i+1).charAt(0));
            if((sSource.substring(i - 1,i).charAt(0)) %2 ==0){
               ls_i=String.valueOf((char)(li_asc + (i - 1)/2 + li_ret));
            }else{
               ls_i=String.valueOf((char)(li_asc - (i - 1)/2 - li_ret));
            }
            ls_code=ls_code+ls_i;
         }
      }
      return ls_code;
   }
}
