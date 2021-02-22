<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">数据搬迁</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>  
<div class="input">	
	<form  class="validate" method="post" name="dataSyncform" id="dataSyncform">
	<table class="form-table">
		<tbody>
			<tr>
				<td style="text-align: center; width: 15%;" >选择同步类型</td>
				<td style="text-align: left;">
					<input type="radio" name="data_type" value="1" checked="checked">订单1.0配置
					<input type="radio" name="data_type" value="2">订单2.0配置
				</td>
			</tr>
			<tr>
				<td style="text-align: center;width: 15%;">选择源数据库类型</td>
				<td style="text-align: left;">
					<select id="from_db_type" name="from_db_type">
						<option value="00T">测试数据库</option>
						<option value="00A">生产数据库</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;width: 15%;">选择目标数据库类型</td>
				<td style="text-align: left;">
					<select id="to_db_type" name="to_db_type">
						<option value="00X">灰度数据库</option>
						<option value="00A">生产数据库</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:forEach items="${hostEnvList }" var="hostEnv">
						<c:if test="${hostEnv.env_type eq 'ecsord_server'}">
							<c:if test="${hostEnv.env_status eq '00A' }">
								1.0生产环境：${hostEnv.host_info } </br>
							</c:if>
							<c:if test="${hostEnv.env_status eq '00X' }">
								1.0灰度环境：${hostEnv.host_info }</br>
							</c:if>
						</c:if>
						<c:if test="${hostEnv.env_type eq 'ecsord_server2.0'}">
							<c:if test="${hostEnv.env_status eq '00A' }">
								2.0生产环境：${hostEnv.host_info } </br>
							</c:if>
							<c:if test="${hostEnv.env_status eq '00X' }">
								2.0灰度环境：${hostEnv.host_info }</br>
							</c:if>
						</c:if>
						<c:if test="${hostEnv.env_type eq 'ecsord_ceshi'}">
								1.0测试环境：${hostEnv.host_info }</br>
						</c:if>
						<c:if test="${hostEnv.env_type eq 'ecsord2.0_ceshi'}">
								2.0测试环境：${hostEnv.host_info }</br>
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</tbody>
	</table>
		<div class="submitlist" align="center">
		  <table>
			<tr>
			 <td> 
		     	<input  type="button" id="dataSyncConfirm"	 value=" 确  定 "  class="submitBtn"  />
		     </td>
		   </tr>
		  </table>
		</div> 
	</form>
</div>
<script>
$(function(){
	$("#dataSyncConfirm").bind("click",function(){
		var from_db_type = $("#from_db_type").val();
		var to_db_type = $("#to_db_type").val();
		if(from_db_type == to_db_type){
			alert("数据同步时源数据库和目标数据库不能相同，请重新选择！");
			return ;
		}
		 var url =app_path + "/shop/admin/grayDataSyncAction!doGrayDataSync.do?ajax=yes";
	        Cmp.ajaxSubmit('dataSyncform', '', url, {}, function(reply){
	        	 alert(reply.message);
		        }, 'json');
	});
});
</script>
<div id="rfsResult">
</div>
