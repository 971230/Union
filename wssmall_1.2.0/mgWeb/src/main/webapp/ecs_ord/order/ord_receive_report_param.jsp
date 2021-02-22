<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.searchformDiv table th {
	width: 90px;
}
</style>
<div class="searchBx">
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
             
             <tr>
                 <th>处理日期：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">

	                
                   </td>
                 <th>处理人选：</th>
               <td>
               <input type="text" class="ipt_new" style="width:138px;" id="username" name="username" value="${username }" onfocus="queryUserId()"/>
			   <input type="hidden" class="ipt_new" style="width:138px;" id="lock_user_id" name="params.lock_user_id" value="${params.lock_user_id }" />
               <a href="javascript:void(0);" id="resetBtn" class="dobtn" style="margin-right:10px;">清除</a>
               
               	                 &nbsp;
				     <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		             &nbsp;
		             <input class="comBtn" type="button" name="excel" id="excelOrd" value="导出" style="margin-right:10px;"/>
               </td>
             </tr>
             </tbody>
            </table>
        </div>
<div id="queryUserListDlg"></div>

<script type="text/javascript">

$(function (){
	Eop.Dialog.init({id:"queryUserListDlg",modal:true,title:"订单领取日报", width:"800px"});
	
	$("#resetBtn").click(function (){
		document.getElementById("username").value = "";
		document.getElementById("lock_user_id").value = "";
	});
});

	function queryUserId(){
		Eop.Dialog.open("queryUserListDlg");
		lock_user_id = $("#lock_user_id").val();
		var url= ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=query";
		    $("#queryUserListDlg").load(url,{},function(){});
	}  
	
</script>  
       