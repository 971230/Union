<%@ page contentType='text/html;charset=UTF-8'%>
<%@ include file='/commons/taglibs.jsp'%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="html"%>
<div class='mk_content'>
		<form method='post' id='cms_plugin_form'>
			<div class='searchformDiv'>
				<table width='100%' border='0' cellspacing='0' cellpadding='0'>
					<tbody>
						<tr>
							<th>名称：</th>
							<td><input id="widgetname_tx" type='text' name='widget_name' value='${widget_name}'
								class='searchipt' style="width: 100px;">
								<input id="widget_module_id" type="hidden" name="module_id" value="${module_id }" /> 
							</td>
							<td style='text-align:center;'><a href='javascript:void(0);'
								class='searchBtn' id='search_plugin_btn'><span>查&nbsp;询</span></a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="plugin_list_div" class='cms_grid pro_list_table mtt'>
				<grid:grid from='webpage' ajax='yes' formId="cms_plugin_form">
					<table cellpadding='0' cellspacing='0'>
						<tbody>
							<c:forEach items='${webpage.data}' var='data' varStatus='index'>
								<c:if test='${index.first }'>
									<tr>
								</c:if>
								<td style="width: 159px;height: 159px;">
								   <a name="widtet_plugin_a_btn" href='javascript:void(0);'>
										<div class='pro_img'>
											<img src='${staticserver}${data.widget_image}' width='140' height='140'>
										</div>
										<p style="text-align: center;">
											<input type='radio' name='widget_id' widget_image="${data.widget_image }" value="${data.widget_id}" />
											${data.widget_name}
										</p>
								</a></td>
								<c:if test='${(index.index + 1) % 4 == 0 }'>
									</tr>
									<tr>
								</c:if>
								<c:if test='${index.end }'>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</grid:grid>
			</div>
		</form>
	</div>
<script type="text/javascript">
	$(function(){
		$("div .page .info").hide();
	});
</script>