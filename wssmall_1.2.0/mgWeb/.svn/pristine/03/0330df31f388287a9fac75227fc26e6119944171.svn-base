<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="cms/js/cms_obj.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">CMS选择元素弹出示例</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0);" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td><p style="color: red;">最终承接到的res_id，此文本框id为final_res_id，请以此为例，勿做id的更改：</p>
					<input type='text' style="width: 300px;" id="final_res_id"/></td>
					<td><p style="color: red;">最终承接到的res_value，此文本框id为final_res_value，请以此为例，勿做id的更改：</p>
					<input type='text' style="width: 300px;" id="final_res_value"/></td>
				</tr>
				<tr>
					<h1 style="color: red;">此弹出以选择商品为例，主键为商品id（goods_id），所需值为名称、价格（name-price）
					，调用方法为CmsObj.showDialog("goods","goods_id","name-price");注意需要引入cms_obj.js。
					参数格式、定义均在cms_obj.js中有描述。</h1>
				</tr>
				<tr>
					<td><input type="submit" id="openCmsObj" value="点我弹出"
						class="submitBtn" style="cursor: pointer;"/></td>
				</tr>
			</table>
		</div>
	</form>
</div>


<div>
	<h1>保存es_cms_column_content测试</h1>
	<form method="post" action="/shop/admin/cms/modual!saveColumnContent.do">
	<p>
		<input type="text" name="title_7" value="title_7"/> 
	</p>
	
	<p>
		<input type="text" name="icon_url_7" value="icon_url_7"/> 
	</p>
	
	<p>
		<input type="text" name="link_url_7" value="link_url_7"/> 
	</p>
	
	<p>
		<button type="submit" value="提交表单" style="width: 70px;height: 22px;">提交表单</button>
	</p>
</form>
</div>




<script>
	$(function(){
		$("#openCmsObj").bind("click",function(){
// 			CmsObj.showDialog("notice","notice_id","title");
			CmsObj.showDialog("goods","goods_id","name-price");
		});
	});
</script>