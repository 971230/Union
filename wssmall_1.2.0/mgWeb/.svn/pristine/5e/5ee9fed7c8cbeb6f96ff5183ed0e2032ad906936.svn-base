<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="/shop/admin/cms/mapper/js/mapper.js"></script>
<div id="mapper_tpl_div" attrValue="${tpl_id}">
	<iframe id="mapper_iframe" pic_url="${mapperPicUrl}" src="/shop/admin/cms/mapper/hot.jsp" 
		width="1000px;" height="465px;" 
			frameborder="0" marginheight="0" marginwidth="0">
			
	</iframe>
</div>
<div>
	<div class="grid">
		<form method="POST">
			<grid:grid from="webpage" ajax="yes" formId="mapper_list_form">
				<grid:header>
					<grid:cell style="width: 100px;">栏目编码</grid:cell>
					<grid:cell style="width: 100px;">栏目名称</grid:cell>
					<grid:cell style="width: 30px;">操作</grid:cell>
				</grid:header>
				<grid:body item="mapper">
					<grid:cell>${mapper.column_id}</grid:cell>
					<grid:cell>${mapper.title}</grid:cell>
					<grid:cell>
						<a title="删除" class="del" name="mapper_del"
						href="javascript:void(0);" column_id="${mapper.column_id}" tpl_id="${mapper.template_id}" >删除</a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div align="center" class="submitlist" style="padding-top: 50px;">
	<a class="submitbtn" id="mapper_back" href="javascript:void(0);">返回</a>
</div>