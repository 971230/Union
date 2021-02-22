<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link href="/${ctx}/statics/default/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="agent/agentstaff.js"></script>
<script type="text/javascript" src="agent/agentcat.js"></script>


<script type="text/javascript"> 
$(function(){
	 $("form.validate").validate();
	 $("input[type=text]").attr("autocomplete","off");
	 $("input[type=submit]").click(function(){
		 $.Loading.text="正在生成缩略图，请稍候...";
	 });
});
</script>
<style>
	.division {
	    background: none repeat scroll 0 0 #FFFFFF;
	    border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	    border-style: solid;
	    border-width: 1px 2px 2px 1px;
	    line-height: 150%;
	    margin: 10px;
	    padding: 5px;
	    white-space: normal;
	    overflow:hidden;
	}
	.division table {
	    margin: 0;
	    padding: 0;
	}
	.orderdetails_basic th {
	    color: #336699;
	    text-align: left;
	    white-space: nowrap;
	}
	.division th {
	    background: none repeat scroll 0 0 #E2E8EB;
	    border-right: 1px solid #CCCCCC;
	    font-size: 14px;
	    text-align: right;
	    white-space: nowrap;
	    width: 140px;
	}
	.division th, .division td {
	    border-color: #FFFFFF #DBE2E7 #DDDDDD #FFFFFF;
	    border-style: solid;
	    border-width: 1px;
	    padding: 5px;
	    vertical-align: top;
	}
	.tableform {
	    background: none repeat scroll 0 0 #EFEFEF;
	    border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	    border-style: solid;
	    border-width: 1px;
	    margin: 10px;
	    padding: 5px;
	}
	h5 {
	    font-size: 1em;
	    font-weight: bold;
	}
	h1, h2, h3, h4, h5, h6 {
	    clear: both;
	    color: #111111;
	    margin: 0.5em 0;
	}
	#order_dialog .con {
	    background: none repeat scroll 0 0 #FFFFFF;
	    height: 400px;
	    opacity: 1;
	    overflow-x: hidden;
	    overflow-y: auto;
	    position: relative;
	    visibility: visible;
	}
	ul.tab li {
	    padding: 0 10px;
	}
	.agent_left{float:left;overflow: hidden;width: 65%;}
	.agent_right{float:right;overflow: hidden;width: 33%;}
</style>
<form method="post" name="theForm" action="${actionName}" id="theForm" class="validate">
	<div class="division clearfix">
		<div class='agent_left'>
			<table class="form-table">
				<tbody><tr>
					<th>商户名称：</th><td>${agentView.username }</td>
					<th>证件号码：</th><td>${agentView.id_card}</td>
					<th>联系电话：</th><td>${agentView.mobile}</td>
				</tr>
				<tr>
					<th>邮政编码：</th><td>${agentView.zip }</td>
					<th>邮箱地址：</th><td>${agentView.email}</td>
					<th>qq：</th><td>${agentView.qq}</td>
				</tr>
				<tr>
					<th>MSN：</th><td>${agentView.msn}</td>
					<th>地址：</th><td colspan="3">${agentView.address}</td>
				</tr>
				<tr>
					<th>申请原因：</th><td colspan="5">${agentView.apply_reason}</td>
				</tr>
				<tr>
					<th>处理意见：</th><td colspan="5">${agentView.comments}</td>
				</tr>
			</tbody>
			</table>
		</div>
		<div class='agent_right'>
			<img src=${agentView.image_file}  />
		</div>
	</div>
	<table class="form-table" style="width:55%;">
		<tr>
			<th>商户：</th>
			<td>
				<input type='hidden' name='agent.state' id='state' />
				<input type='hidden' name='agent.agentid' id='agentid' value="${agentView.agentid}" />
				<input type="hidden" name="agent.staff_no" id="staff_no" class="input_text"  value="${agentView.staff_no}" style="width:250px"/>
				<input type='text' name='agent.staff_code' id='staff_code' value="${agentView.staff_code}" />
			    <input type="button" class="mempriceBtn" value="关联用户" />
			</td>
		</tr>
	</table>
	
	<table class="form-table" style="width:55%;">
		<tr>
			<th>分类：</th>
			<td>
				<input type="hidden" name="agent.cat_ids" id="cat_ids" class="input_text"  value="${agentView.cat_ids}" style="width:250px"/>
				<input type='text' name='agent.cat_names' id='cat_names' value="${agentView.cat_names}" />
			    <input type="button" class="catsBtn" value="关联分类" />
			</td>
		</tr>
	</table>
	
	<div class="submitlist" align="center" >
		 <table>
			 <tr>
			   <td >
			   		<input  type="submit"	  value="保存" class="submitBtn" />
			   </td>
			 
			   </tr>
		 </table>
	</div>
	<script>
		$(function(){
			
		})
		
		
         
	</script>
</form>
<div id="agentstaffdlg"></div>
<div id="agentcatdlg"></div>
