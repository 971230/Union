<%@ page contentType='text/html;charset=UTF-8'%>
<%@ include file='/commons/taglibs.jsp'%>
<style>
/*定义列表容器中所有公用元素样式，需要重定义的，在下面再重新定义*/
.cms_grid ul{
	list-style-type:none;
	text-align:center;
}
.cms_grid ul li{
	float:left;
}

.cms_grid .page{
	padding:5px 0 0 10px;
}

.cms_grid .page .info{
	border: 1px solid #cdcdcd;
	background: #f8f8f8;
	padding:3px 6px;
	margin:0px 2px 2px 2px;
	color: #444;
	float:left;
}

/*定义列表容器中分页的样式，使用ul元素*/
.cms_grid .page ul{
	color: #FFF;
	float:right;
	line-height:30px;
}

/*定义列表容器中分页的样式，使用li元素*/
.cms_grid .page ul li{
}

/*定义列表容器中分页链接的样式，使用li元素*/
.cms_grid .page li a{
	border: 1px solid #cdcdcd;
	background: #f8f8f8;
	padding:2px 6px;
	margin:2px;
	color: #444;
	text-decoration: none;	
}

/*定义列表容器中选中页的样式，使用li元素*/
.cms_grid .page .selected{
	font-weight: bold; color: #FFF;
	background:#6497d4;
	border:1px solid #6497d4;
}
</style>

<div class='mk_content'>
	<form method='post' id='goods_form' action='cmsObj!getGoods.do'>
		<div class='searchformDiv'>
			<table width='100%' border='0' cellspacing='0' cellpadding='0'>
				<tbody>
					<tr>
						<th>关键字:</th>
						<td><input type='text' name='goods_name'
							value='${goods_name}' class='searchipt'></td>
						<td style='text-align:center;'><a href='javascript:void(0);' class='searchBtn' id='search_goods'><span>查&nbsp;询</span></a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form method='POST' id='goods_form'>
		<div class='cms_grid pro_list_table mtt'>
			<grid:grid from='webpage' ajax='yes'>
				<table cellpadding='0' cellspacing='0'>
					<tbody>
						<c:forEach items='${webpage.data}' var='data' varStatus='index'>
							<c:if test='${index.first}'>
								<tr>
							</c:if>
							<td><a href='javascript:void(0);'>
									<div class='pro_img'>
										<img src='${data.image_file}' width='140' height='140'>
									</div>
									<p style="text-align: center;">
									<input type='checkbox' name='goods_check'/>
									<input type='hidden' name='goods_id' value='${data.goods_id}' />
									<input type='hidden' name='name' value='${data.name}' />
									<input type='hidden' name='sn' value='${data.sn}' />
									<input type='hidden' name='brand_id' value='${data.brand_id}' />
									<input type='hidden' name='cat_id' value='${data.cat_id}' />
									<input type='hidden' name='type_id' value='${data.type_id}' />
									<input type='hidden' name='goods_type' value='${data.goods_type}' />
									<input type='hidden' name='weight' value='${data.weight}' />
									<input type='hidden' name='market_enable' value='${data.market_enable}' />
									<input type='hidden' name='brief' value='${data.brief}' />
									<input type='hidden' name='price' value='${data.price}' />
									<input type='hidden' name='cost' value='${data.cost}' />
									<input type='hidden' name='mktprice' value='${data.mktprice}' />
									<input type='hidden' name='have_spec' value='${data.have_spec}' />
									<input type='hidden' name='create_time' value='${data.create_time}' />
									<input type='hidden' name='last_modify' value='${data.last_modify}' />
									<input type='hidden' name='view_count' value='${data.view_count}' />
									<input type='hidden' name='buy_count' value='${data.buy_count}' />
									<input type='hidden' name='disabled' value='${data.disabled}' />
									<input type='hidden' name='store' value='${data.store}' />
									<input type='hidden' name='point' value='${data.point}' />
									<input type='hidden' name='page_title' value='${data.page_title}' />
									<input type='hidden' name='meta_keywords' value='${data.meta_keywords}' />
									<input type='hidden' name='meta_description' value='${data.meta_description}' />
									<input type='hidden' name='sord' value='${data.sord}' />
									<input type='hidden' name='staff_no' value='${data.staff_no}' />
									<input type='hidden' name='service_type' value='${data.service_type}' />
									<input type='hidden' name='image_default' value='${data.image_default}' />
									<input type='hidden' name='image_file' value='${data.image_file}' />
									<input type='hidden' name='specs' value='${data.specs}' />
									<input type='hidden' name='adjuncts' value='${data.adjuncts}' />
									<input type='hidden' name='crm_offer_id' value='${data.crm_offer_id}' />
									<input type='hidden' name='type_code' value='${data.type_code}' />
									<input type='hidden' name='state' value='${data.state}' />
									<input type='hidden' name='creator_user' value='${data.creator_user}' />
									<input type='hidden' name='supper_id' value='${data.supper_id}' />
									<input type='hidden' name='audit_state' value='${data.audit_state}' />
									<input type='hidden' name='simple_name' value='${data.simple_name}' />
									<input type='hidden' name='specifications' value='${data.specifications}' />
									<input type='hidden' name='unit' value='${data.unit}' />
									<input type='hidden' name='stype_id' value='${data.stype_id}' />
									<input type='hidden' name='ctn' value='${data.ctn}' />
									<input type='hidden' name='effect_date' value='${data.effect_date}' />
									<input type='hidden' name='fail_date' value='${data.fail_date}' />
									<input type='hidden' name='source_from' value='${data.source_from}' />
									<input type='hidden' name='sub_stype_id' value='${data.sub_stype_id}' />
									<input type='hidden' name='search_key' value='${data.search_key}' />
									<input type='hidden' name='sku' value='${data.sku}' />
									<input type='hidden' name='model_code' value='${data.model_code}' />
									<input type='hidden' name='type' value='${data.type}' />
									<input type='hidden' name='params' value='${data.params}' />
									${data.name}</p>
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
