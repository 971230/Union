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
 <script type="text/javascript"  src="<%=request.getContextPath() %>/ecs_ord/js/money_audit_del_excel.js"></script> 
<body>

<!-- <div class="gridWarp"> -->

	<div class="new_right">
        <div class="right_warp">
        <!-- 营业款稽核:开始 -->
                <div class="grid_n_div">
            	    <h2><a href="javascript:void(0);" class="closeArrow"></a>报表删除</h2> 
                	<div class="grid_n_cont">
					   <div class="grid_n_cont_sub">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">批次号：</td>
	                                <td> <input style="width: 138px;" type="text" name="batch_number" id="batch_number" value="${auditQueryParame.now_date }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-{%M-2}-%d',maxDate:'%y-%M-%d'})"> </td>
	                          	</tr>
	                        </table>
                         
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">表格：</td>
	                                <td>
	                                    <select id="excel_from" name="excel_from" class="ipt_new" style="width: 140px;">
											<option value="excel_from_zbtb">总部淘宝报表</option>
											<option value="excel_from_agency">货到付款报表</option>
											<option value="excel_from_bdtb">本地天猫表</option>
											<option value="excel_type_wo">沃平台报表</option>
											<option value="excel_type_zb_busi_pay">ECS报表</option>
											<option value="excel_type_zb_details">总部订单详情表</option>
											<option value="excel_type_bss">BSS报表</option>
											<option value="excel_type_cbss">CBSS报表</option>
									     </select>
									</td>
	                          	</tr>
	                        </table>
                         
                       </div>  
                        <div class="pop_btn" align="center"> <input class="comBtn" type="button"  id="del_excle_bt" value="报表删除"  style="margin-right:10px;" /></div>
        	        </div>
        	   </div>
        <!-- 营业款稽核:结束 -->	 
        
    
        </div>
    </div>
    
<!-- </div> -->

</body>
</html>
