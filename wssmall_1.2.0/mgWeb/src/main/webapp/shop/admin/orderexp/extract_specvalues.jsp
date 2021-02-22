<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
	<div>
		<table border="1" style="border-spacing: 2px;text-align: center;">
			<tbody>
				<c:choose>
					<c:when test="${search_field eq '1' }">
						<tr><td>抽取的报文（请求报文）：</td></tr>
						<tr>
							<td>
								<textarea rows="20" cols="95" id="extract_specvalues_in_param">${in_param}</textarea>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr><td>抽取的报文（响应报文）：</td></tr>
						<tr>
							<td>
								<textarea rows="20" cols="95" id="extract_specvalues_out_param" >${out_param}</textarea>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td>
						<input class="comBtn" type="button" id="imitateBtn" value="模拟抽取" style="margin-right:10px;"onclick="imitateSpecvalues('${log_id }','${search_id }','${search_code }');" />
					</td>
				</tr>
				<tr>
					<td>
						<textarea rows="10" cols="95" id="extract_specvalues_specvalues"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
<script type="text/javascript">
	//模拟抽取关键字按钮触发点击事件
	function imitateSpecvalues(log_id,search_id,search_code){
		$.ajax({
			 type: "POST",
			 url: ctx + "/shop/admin/keyrule!imitateSpecvalues.do",
			 dataType:"json",
			 data: "ajax=yes&search_id=" + search_id + "&log_id=" + log_id + "&search_code=" + search_code,
			 success: function(result){
				 $("#extract_specvalues_specvalues").text(result.message);
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
</script>
