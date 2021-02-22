<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script src="<%=request.getContextPath() %>/publics/js/ajaxfileupload.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script> --%>
<!-- <script type="text/javascript" src="js/communityActivity.js"></script> -->
<form method="post" id='community_list_form'
	action='communityAction!list.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>地市:&nbsp;&nbsp;</th>
					<td>
					<html:selectdict name="activity.city_name" curr_val="${activity.city_name }"
					             attr_code="DC_CITY_ZJ" style="width:200px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict> 
					<%-- <input type="text" class="ipttxt"  name="activity.city_name" value="${activity.city_name}" class="searchipt" /> --%></td>
					<th>行政区县:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt"  name="activity.area_name" value="${activity.area_name}" class="searchipt" /></td>
					<th>县分公司:</th>
					<td><input type="text" class="ipttxt"  name="activity.county_comp_name" value="${activity.county_comp_name}" class="searchipt" /></td>
					<th>小区名称:</th>
					<td><input type="text" class="ipttxt"  name="activity.community_name" value="${activity.community_name}" class="searchipt" /></td>
				</tr>
				 <tr>
					<th>选择文件：</th>
					<td><input type="file" name="file" style="width: 150px" id="batch_import_community"/></td>
					<th>数据导入：</th>
					<td><input type="button" value="数据导入" name="importCommunityExcel"
					     onclick="return ajaxFileUpload('batch_import_community');" /></td>
					<th>模板下载：</th>
					<td><input type="button" value="模板下载" name="downLoadExcelModel" /></td>     
					<th></th>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="button"></td>
				 </tr>
			</tbody>
		</table>
	</div>
</form>
<div class="grid" id="community_list_form">
	<!-- <form method="POST" id="community_list_form"> -->
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell sort="name" width="50px">序号</grid:cell>
				<grid:cell sort="name" width="250px">地市</grid:cell>
				<grid:cell sort="enable">行政区县</grid:cell>
				<grid:cell sort="begin_time">县分公司</grid:cell>
				<grid:cell sort="end_time">小区编码</grid:cell>
				<grid:cell sort="realname">小区名称</grid:cell>
				<grid:cell width="200px">操作</grid:cell>
			</grid:header>

			<grid:body item="community">
				<grid:cell>${index+1 } </grid:cell>
				<grid:cell>${community.city_name } </grid:cell>
				<grid:cell>${community.area_name } </grid:cell>
				<grid:cell>${community.county_comp_name } </grid:cell>
				<grid:cell>${community.community_code } </grid:cell>
				<grid:cell>${community.community_name } </grid:cell>
				<grid:cell>
				  <img src="images/transparent.gif" id="commounity_goods_real" name="commounity_goods_real" community_code="${community.community_code}" class="add">
				  <%-- <input type="button" id="commounity_goods_real" name="commounity_goods_real" community_code="${community.community_code}" value ="关联商品"  /> --%> 
				</grid:cell>
			</grid:body>
		</grid:grid>
	<!-- </form> -->
</div>
<div id="community_good_relation_dialog"></div>
<div id="communityImportDiv"></div>
<script>
$(function() {
	Eop.Dialog.init({id:"community_good_relation_dialog",modal:false,title:"关联商品",height:'300px',width:'850px'});
	esc_commountiy.init();
});
var esc_commountiy = {
		
	init : function() {
		esc_commountiy.download_excel();
		esc_commountiy.community_good_relation();
	},
	//下载小区导入模板
	download_excel : function(){
		$("[name='downLoadExcelModel']").unbind("click").bind("click",function(){
			var url = ctx + "/servlet/batchAcceptExcelServlet?url=/shop/admin/communities/community_list.jsp&lx=mb&service=community";
			var temp_form = '<form id="hidden_export_excel_form" action="'+url+'" method="post"></form>';
			if($("#hidden_export_excel_form").length>0)
				$("#hidden_export_excel_form").remove();
			$("#communityImportDiv").append(temp_form);
			$("#hidden_export_excel_form").submit();
		});
		/* $("[name='downLoadExcelModel']").unbind("click").bind("click",function(){
			var url = ctx + "/shop/admin/communityAction!downLoadExcel.do?ajax=true&excel=true";
			window.location.href = url;
		}); */
	},
	//关联商品弹出框
	community_good_relation  : function(){
		$("img[id='commounity_goods_real']").unbind("click").bind("click",function(){
			var community_code = $(this).attr("community_code");
			var url = ctx + "/shop/admin/communityAction!toGoodsCommunity.do?ajax=yes&activity.community_code="+community_code;
			Eop.Dialog.open("community_good_relation_dialog");
			$("#community_good_relation_dialog").load(url,{},function(){});
		});
	}
}	

function ajaxFileUpload(excel_from){
	var fileName =$("#"+excel_from).val();
	if (fileName==null||fileName=='') {
		MessageBox.show("上传文件为空 ", 3, 2000);
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	var extPattern = /.+\.(xls|xlsx|csv)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			
		} else {
			MessageBox.show("只能上传EXCEL文件！", 2, 3000);
			return;
		}
	}
	if(!confirm("请确认【上传小区数据】")){
		return;
    }
	$.Loading.show("正在导入，请稍侯...");
    $.ajaxFileUpload({
         url: ctx + '/shop/admin/communityAction!importExcel.do?ajax=yes&fileName='+fileName,//用于文件上传的服务器端请求地址
         secureuri:false,//一般设置为false
         fileElementId:excel_from,
         dataType: 'json',
         success: function (data){
        	 $.Loading.hide();
        	 if(data.result == -1){
        		 alert(data.message);
        	 }else{
        		 alert(data.message);
            	 window.location.href = ctx + "/shop/admin/communityAction!list.do";
        	 }
         },
         error: function (data, status, e){
        	 $.Loading.hide();
             alert(e);
         }
    });
    return false;
}
</script>