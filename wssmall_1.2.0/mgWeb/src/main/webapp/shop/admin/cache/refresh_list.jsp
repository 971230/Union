<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
	<table cellspacing="0" cellpadding="0" border="0" width="100%" class="finderInform">
		<tbody>
			<div>
				<c:forEach items="${serviceList}" var="service">
					<tr>
						<td>
							<iframe height="30" src="${service.cf_value}${ctx}/shop/admin/cache!${refreshType}.do?ajax=yes&serviceIp=${service.cf_value}" width="100%" 
								frameborder="0" scrolling="no" style="margin:0px;padding:0px;overflow:hidden;">
							</iframe>
						</td>
					</tr>
				</c:forEach>
			</div>
		</tbody>
	</table>
</div>