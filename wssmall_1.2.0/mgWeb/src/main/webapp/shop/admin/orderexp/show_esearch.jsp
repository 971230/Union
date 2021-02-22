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
				<tr><td>请求报文：</td></tr>
				<tr>
					<td>
						<textarea rows="20" cols="95" id="in_param">${in_param}</textarea>
					</td>
				</tr>
				<tr><td>响应报文：</td></tr>
				<tr>
					<td>
						<textarea rows="20" cols="95" id="out_param" >${out_param}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

