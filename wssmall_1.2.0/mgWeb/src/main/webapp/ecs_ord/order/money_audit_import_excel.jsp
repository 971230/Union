<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
  </head>
<script src="<%=request.getContextPath() %>/publics/js/ajaxfileupload.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath() %>/ecs_ord/js/money_audit_import_excel.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<body>
 <form method="post" action="moneyAuditAction!expOfflinePay.do?ajax=yes" id="exportwlForm">
<div class="gridWarp">

	<div class="new_right">
        <div class="right_warp">
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;批次号<!-- <input type="text" class="ipttxt" name="batch_number" id="batch_number" value="2016-10-21" /> -->
         <input style="width: 138px;" type="text" name="batch_number" id="batch_number" value="${auditQueryParame.now_date }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-{%M-2}-%d',maxDate:'%y-%M-%d'})">
        <!-- 营业款稽核:开始 -->
                <div class="grid_n_div">
            	    <h2><a href="javascript:void(0);" class="closeArrow"></a>营业款稽核</h2> 
                	<div class="grid_n_cont">
					   <div class="grid_n_cont_sub">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">CBSS报表导入：</td>
	                                <td><input type="file" class="ipttxt" name="file" id="excel_type_cbss" /> </td>
	                                <td>	<input class="comBtn" type="button"  id="excel_type_cbss_bt"   onclick="return ajaxFileUpload('excel_type_cbss');" value="确认导入"  style="margin-right:10px;" />
						   		    </td>
	                          	</tr>
	                        </table>
                         
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">BSS报表导入：</td>
	                                <td><input type="file" class="ipttxt" name="file"  id="excel_type_bss"/> </td>
	                                <td>	<input class="comBtn" type="button"  id="excel_type_bss_bt"   onclick="return ajaxFileUpload('excel_type_bss');" value="确认导入"  style="margin-right:10px;" />
						   		    </td>
	                          	</tr>
	                        </table>
                         
                       </div>  
                        <div class="pop_btn" align="center"> <input class="comBtn" type="button"  id="excel_type_bss_audit_bt" onclick="auditData('excel_type_bss')" value="营业款稽核"  style="margin-right:10px;" /></div>
        	        </div>
        	   </div>
        <!-- 营业款稽核:结束 -->	 
        
        
        <!-- 总部订单实收款稽核:开始 -->
                <div class="grid_n_div">
            	    <h2><a href="javascript:void(0);" class="closeArrow"></a>总部订单实收款稽核</h2> 
                	<div class="grid_n_cont">
					   <div class="grid_n_cont_sub">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">总部订单详情报表导入：</td>
	                                <td><input type="file" class="ipttxt" name="file"  id="excel_type_zb_details"/> </td>
	                                <td>	<input class="comBtn" type="button" name="submit_import_form"  id="excel_type_zb_details_bt" onclick="return ajaxFileUpload('excel_type_zb_details');"  value="确认导入"  style="margin-right:10px;" />
						   		    </td>
	                          	</tr>
	                        </table>
                         
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">ECS报表导入：</td>
	                                <td><input type="file" class="ipttxt" name="file"  id="excel_type_zb_busi_pay"/> </td>
	                                <td>	<input class="comBtn" type="button"  name="submit_import_form" id="excel_type_zb_busi_pay_bt"  onclick="return ajaxFileUpload('excel_type_zb_busi_pay');"  value="确认导入"  style="margin-right:10px;" />
						   		    </td>
	                          	</tr>
	                        </table>
                         
                       </div>  
                        <div class="pop_btn" align="center"> <input class="comBtn" type="button"  id="excel_type_zb_details_audit_bt"  onclick="auditData('excel_type_zb_details')" value="总部订单实收款稽核"  style="margin-right:10px;" /></div>
        	        </div>
        	   </div>
        <!-- 总部订单实收款稽核:结束 -->	 
        
        
        <!-- 沃平台订单实收款稽核:开始 -->
                <div class="grid_n_div">
            	    <h2><a href="javascript:void(0);" class="closeArrow"></a>沃平台订单实收款稽核</h2> 
                	<div class="grid_n_cont">
					   <div class="grid_n_cont_sub">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">沃平台报表导入：</td>
	                                <td><input type="file" class="ipttxt" name="file"  id="excel_type_wo" /> </td>
	                                <td>	<input class="comBtn" type="button"  name="submit_import_form" id="excel_type_wo_bt" onclick="return ajaxFileUpload('excel_type_wo');" value="确认导入"  style="margin-right:10px;" />
						   		    </td>
	                          	</tr>
	                        </table>
                       </div>  
                        <div class="pop_btn" align="center"> <input class="comBtn" type="button"  id="excel_type_wo_audit_bt" onclick="auditData('excel_type_wo')"  value="沃平台订单实收款稽核"  style="margin-right:10px;" /></div>
        	        </div>
        	   </div>
        <!-- 沃平台订单实收款稽核:结束 -->	   
        
        <!-- 本地淘宝订单实收款稽核:开始 -->
               <div class="grid_n_div">
           	    <h2><a href="javascript:void(0);" class="closeArrow"></a>本地淘宝订单实收款稽核</h2> 
               	<div class="grid_n_cont">
				   <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
                          	<tr>
                                <td class="excle_title" width="30%">本地淘宝报表导入：</td>
                                <td><input type="file" class="ipttxt" name="file"  id="excel_from_bdtb"/> </td>
                                <td>	<input class="comBtn" type="button"   name="submit_import_form"  id="excel_from_bdtb_bt"  onclick="return ajaxFileUpload('excel_from_bdtb');" value="确认导入"  style="margin-right:10px;" />
					   		    </td>
                          	</tr>
                        </table>
                      </div>  
                       <div class="pop_btn" align="center"> <input class="comBtn" type="button"  id="excel_from_bdtb_audit_bt" value="本地淘宝订单实收款稽核"  onclick="auditData('excel_from_bdtb')" style="margin-right:10px;" /></div>
       	        </div>
       	    </div>
        <!-- 本地淘宝订单实收款稽核:结束 -->	
        <!-- 稽核周报表导入:开始 -->
             <div class="grid_n_div">
         	    <h2><a href="javascript:void(0);" class="closeArrow"></a>稽核周报表导入</h2> 
             	<div class="grid_n_cont">
				   <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
                        	<tr>
                              <td class="excle_title" width="30%">代收货款报表导入：</td>
                              <td><input type="file" class="ipttxt" name="file"  id="excel_from_agency"/> </td>
                              <td>	<input class="comBtn" type="button"   name="submit_import_form" id="excel_from_agency_bt" onclick="return ajaxFileUpload('excel_from_agency');"  value="确认导入"  style="margin-right:10px;" /> </td>
                        	</tr>
                        </table>
                    </div>  
                     <div class="pop_btn" align="center"> 
                    
	                     <input class="comBtn" type="button"  id="excel_from_agency_audit_bt" onclick="auditData('excel_from_agency')" value="订单周稽核"  style="margin-right:10px;" />
	                     <input class="comBtn" type="button"   onclick="export_wl_info()" value="未匹配物流信息导出"  style="margin-right:10px;" />
               
                     </div>
     	        </div>
     	   </div>
     <!-- 稽核周报表导入:结束 --> 
        
        
         <!-- 稽核月报表导入:开始 -->
        <div class="grid_n_div">
    	    <h2><a href="javascript:void(0);" class="closeArrow"></a>稽核月报表导入</h2> 
        	<div class="grid_n_cont">
			       <div class="grid_n_cont_sub">
		                       <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
		                        	<tr>
		                              <td class="excle_title" width="30%">总部淘宝报表导入：</td>
		                              <td><input type="file" class="ipttxt" name="file"  id="excel_from_zbtb"/> </td>
		                              <td>	<input class="comBtn" type="button"  name="submit_import_form" id="excel_from_zbtb_bt" onclick="return ajaxFileUpload('excel_from_zbtb');"  value="确认导入"  style="margin-right:10px;" /></td>
		                        	</tr>
		                      </table>
	              </div>  
                  <div class="pop_btn" align="center"> <input class="comBtn" type="button" id="excel_from_zbtb_audit_bt" onclick="auditData('excel_from_zbtb')" value="订单月稽核"  style="margin-right:10px;" /></div>
     	    </div>
     	</div>
       <!-- 稽核月报表导入:结束 --> 
        </div>
    </div>
    
</div>
      </form>
<a id = "exportTemplate" style="display:none" href="./未匹配报表.xls">未匹配报表</a>    
</body>
</html>
