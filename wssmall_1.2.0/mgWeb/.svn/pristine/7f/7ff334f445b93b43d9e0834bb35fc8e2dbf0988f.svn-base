<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">关键字信息修改</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="" class="validate" id="specvalue_update_form" method="post">
		<input type="hidden" name="ajax" value="yes">
		<input type="hidden" class="ipttxt"  value="${esearchSpecvalues.key_id }" name="esearchSpecvalues.key_id" required="true" />
		<table class="form-table">
			<tr>
				<th><label class="text"><span class='red'></span>搜索编码：</label></th>
				<td>
					${esearchSpecvalues.search_code }
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>搜索id：</label></th>
				<td>
					${esearchSpecvalues.search_id }
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>关键字id：</label></th>
				<td>
					${esearchSpecvalues.key_id }
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>关键字值：</label></th>
				<td>
					${esearchSpecvalues.match_content }
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>是否预警：</label></th>
				<td>
					<select name="esearchSpecvalues.warming_flag" class="ipttxt">
						<option value="N" <c:if test="${esearchSpecvalues.warming_flag == 'N' }">selected</c:if> >否</option>
						<option value="Y" <c:if test="${esearchSpecvalues.warming_flag == 'Y' }">selected</c:if> >是</option>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>预警时长（小时）：</label></th>
				<td>
					<input type="text" class="ipttxt" id="warming_limit"  value="${esearchSpecvalues.warming_limit }" name="esearchSpecvalues.warming_limit"/>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>是否超时提醒：</label></th>
				<td>
					<select name="esearchSpecvalues.timeout_flag" class="ipttxt">
						<option value="N" <c:if test="${esearchSpecvalues.timeout_flag == 'N' }">selected</c:if> >否</option>
						<option value="Y" <c:if test="${esearchSpecvalues.timeout_flag == 'Y' }">selected</c:if> >是</option>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>超时时长（小时）：</label></th>
				<td>
					<input type="text" class="ipttxt" id="timeout_limit"  value ="${esearchSpecvalues.timeout_limit }" name="esearchSpecvalues.timeout_limit"/>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>关键字标识：</label></th>
				<td>
					<select name="esearchSpecvalues.match_content_type" class="ipttxt">
						<option value="succ" <c:if test="${esearchSpecvalues.match_content_type == 'succ' }">selected</c:if> >成功</option>
						<option value="fail" <c:if test="${esearchSpecvalues.match_content_type == 'fail' }">selected</c:if> >失败</option>
						<option value="filter" <c:if test="${esearchSpecvalues.match_content_type == 'filter' }">selected</c:if> >过滤</option>
					</select>
				</td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
						<input type="button" value="确  定 " class="submitBtn" onclick="submit_data();" />
						<input type="button" value="返回 " class="submitBtn" onclick="window.location.href='${ctx}/shop/admin/specvalue!list.do'" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script type="text/javascript">
//提交数据
function submit_data() {
	if($("#warming_limit").val() != ""){
		var warming_limit = $("#warming_limit").val();
		var reg = new RegExp("^[0-9]*$");
		if(!reg.test(warming_limit)){
			alert("预警时长 请输入数字!");
			return false;
		}
	}
	if($("#timeout_limit").val() != ""){
		var timeout_limit = $("#timeout_limit").val();
		var reg = new RegExp("^[0-9]*$");
		if(!reg.test(timeout_limit)){
			alert("超时时长 请输入数字!");
			return false;
		}
	}
	var json_data = $("#specvalue_update_form").serialize();
	var tem_url = ctx + "/shop/admin/specvalue!update.do";
	$.ajax({
		 type: "POST",
		 url: tem_url,
		 data: json_data,
		 dataType:"json",
		 success: function(result){
			 if (result.result == '0') {
				 alert(result.message);
				 window.location.href = ctx + '/shop/admin/specvalue!list.do';
		     } else {
		    	 alert(result.message);
		     }
		 },
		 error:function(){
			 alert("操作失败，请重试");
		 }
	});
}
</script>