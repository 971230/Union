<%@ page contentType='text/html;charset=UTF-8'%>
<%@ include file='/commons/taglibs.jsp'%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="html"%>
<style>
/*定义列表容器中所有公用元素样式，需要重定义的，在下面再重新定义*/
.cms_grid ul {
	list-style-type: none;
	text-align: center;
}

.cms_grid ul li {
	float: left;
}

.cms_grid .page {
	padding: 5px 0 0 10px;
}

.cms_grid .page .info {
	border: 1px solid #cdcdcd;
	background: #f8f8f8;
	padding: 3px 6px;
	margin: 0px 2px 2px 2px;
	color: #444;
	float: left;
}

/*定义列表容器中分页的样式，使用ul元素*/
.cms_grid .page ul {
	color: #FFF;
	float: right;
	line-height: 30px;
}

/*定义列表容器中分页的样式，使用li元素*/
.cms_grid .page ul li {
	
}

/*定义列表容器中分页链接的样式，使用li元素*/
.cms_grid .page li a {
	border: 1px solid #cdcdcd;
	background: #f8f8f8;
	padding: 2px 6px;
	margin: 2px;
	color: #444;
	text-decoration: none;
}

/*定义列表容器中选中页的样式，使用li元素*/
.cms_grid .page .selected {
	font-weight: bold;
	color: #FFF;
	background: #6497d4;
	border: 1px solid #6497d4;
}
.searchformDiv table th {
	width: 70px;
}
</style>
<div class='mk_content'>
	<form method='post' id='adv_form' action='cmsObj!getAdv.do'>
		<div class='searchformDiv'>
			<table width='100%' border='0' cellspacing='0' cellpadding='0'>
				<tbody>
					<tr>
						<th>名称：</th>
						<td><input type='text' name='advertisement.aname' value='${advertisement.aname}'
							class='searchipt' style="width: 100px;'"></td>
						<th>素材大类：</th>
						<td><html:selectdict name="advertisement.atype" curr_val="${advertisement.atype}"
								attr_code="DC_MATERIAL_TYPE_BIG" style="width:100px"
								appen_options='<option value="">--请选择--</option>'></html:selectdict>
						</td>
						<th>素材小类：</th>
						<input type="hidden" value="${advertisement.subtype }" id="hidden_subtype"/>
						<td class="sub_stype" a_val=""><select ><option
									value="" selected="selected">--请选择--</option></select></td>
						<td class="sub_stype" a_val="1" style="display: none;"><html:selectdict
								 curr_val="${advertisement.subtype}"
								attr_code="DC_MATERIAL_TYPE_1" style="width:100px"
								appen_options='<option value="">--请选择--</option>'></html:selectdict></td>
						<td class="sub_stype" a_val="2" style="display: none;"><html:selectdict
								 curr_val="${advertisement.subtype}"
								attr_code="DC_MATERIAL_TYPE_2" style="width:100px"
								appen_options='<option value="">--请选择--</option>'></html:selectdict></td>
						<td class="sub_stype" a_val="3" style="display: none;"><html:selectdict
								 curr_val="${advertisement.subtype}"
								attr_code="DC_MATERIAL_TYPE_3" style="width:100px"
								appen_options='<option value="">--请选择--</option>'></html:selectdict></td>
						<td class="sub_stype" a_val="4" style="display: none;"><html:selectdict
								 curr_val="${advertisement.subtype}"
								attr_code="DC_MATERIAL_TYPE_4" style="width:100px"
								appen_options='<option value="">--请选择--</option>'></html:selectdict></td>
						<td style='text-align:center;'><a href='javascript:void(0);'
							class='searchBtn' id='search_adv'><span>查&nbsp;询</span></a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form method='POST' id='adv_form'>
		<div class='cms_grid pro_list_table mtt'>
			<grid:grid from='webpage' ajax='yes'>
				<table cellpadding='0' cellspacing='0'>
					<tbody>
						<c:forEach items='${webpage.data}' var='data' varStatus='index'>
							<c:if test='${index.first }'>
								<tr>
							</c:if>
							<td><a href='javascript:void(0);'>
									<div class='pro_img'>
										<img src='${data.img_url}' width='140' height='140'>
									</div>
									<p style="text-align: center;">
										<input type='radio' name='adv_check' /> <input type='hidden'
											name='aid' value='${data.aid}' /> <input type='hidden'
											name='acid' value='${data.acid}' /> <input type='hidden'
											name='atype' value='${data.atype}' /> <input type='hidden'
											name='begintime' value='${data.begintime}' /> <input
											type='hidden' name='endtime' value='${data.endtime}' /> <input
											type='hidden' name='isclose' value='${data.isclose}' /> <input
											type='hidden' name='attachment' value='${data.attachment}' />
										<input type='hidden' name='atturl' value='${data.atturl}' />
										<input type='hidden' name='url' value='${data.url}' /> <input
											type='hidden' name='aname' value='${data.aname}' /> <input
											type='hidden' name='clickcount' value='${data.clickcount}' />
										<input type='hidden' name='linkman' value='${data.linkman}' />
										<input type='hidden' name='subtype' value='${data.subtype}' />
										<input type='hidden' name='company' value='${data.company}' />
										<input type='hidden' name='contact' value='${data.contact}' />
										<input type='hidden' name='disabled' value='${data.disabled}' />
										<input type='hidden' name='user_id' value='${data.user_id}' />
										<input type='hidden' name='state' value='${data.state}' /> <input
											type='hidden' name='resol' value='${data.resol}' /> <input
											type='hidden' name='create_date' value='${data.create_date}' />
										<input type='hidden' name='source_from'
											value='${data.source_from}' /> ${data.aname}
									</p>
							</a></td>
							<c:if test='${(index.index + 1) % 3 == 0 }'>
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
