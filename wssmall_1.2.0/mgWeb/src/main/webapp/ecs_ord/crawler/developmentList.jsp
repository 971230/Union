<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
</style>
<form method="post" id="devserchform" >
 <div class="searchformDiv" style ='width:100%' >
 <table>
	<tr>
	    <th>发展人编码：</th>
		<td><input type="text" class="ipttxt"  name="developmentCode"  id ="developmentCode" value="${developmentCode}"/></td>
		<th>发展人名称：</th>
		<td><input type="text" class="ipttxt"  name="developmentName"  id ="developmentName" value="${developmentName}"/></td>
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="searchBtn" id="devsearchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
    </tr>
  </table>
</div>

</form>
 <div class="grid"  >
     <form method="POST"  id="roleform" >
       <grid:grid from="webpage"  formId="devserchform" ajax="yes">
                    <grid:header>
                        <grid:cell>选择</grid:cell>
					  	<grid:cell>发展人编码</grid:cell> 
					  	<grid:cell>发展人名称</grid:cell> 
					</grid:header>
				    <grid:body item="obj">
					     <grid:cell><input type="radio" name="selDevrdo" devlopmentName="${obj.developmentName}" value="${obj.developmentCode}"></grid:cell>
					     <grid:cell>${obj.developmentCode}</grid:cell>
					     <grid:cell>${obj.developmentName}</grid:cell>
					</grid:body>  
				</grid:grid>
				 <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="devSelInsureBtn">
		         </div>
</form>
</div>

<script type="text/javascript">
var developmentList={
		 search:function(){
			 $("#devsearchBtn").click(function(){
				
				 var developmentName = $("#developmentName").val();
				 var developmentCode = $("#developmentCode").val();
				 
				 var url= app_path+"/shop/admin/orderWarningAction!getDevelopmentList.do?ajax=yes";
				 Cmp.ajaxSubmit('devserchform','selDevelopmentDlg',url,{},developmentList.search);
				  
			 });
		 },
		 initFun:function(){
			 developmentList.search();
			 developmentList.checkRole();
		 },
			
			checkRole:function(){
				$("#devSelInsureBtn").unbind("click").bind("click",function(){
					var code = $("[name='selDevrdo']:checked").val();
					if(code==null){
						alert("还未选中发展人");
					}else{
						$("[field_name='development_code']").val(code);
						var devname = $("[name='selDevrdo']:checked").attr("devlopmentName");
						$("[field_name='development_name']").val(devname);
						Eop.Dialog.close("selDevelopmentDlg");
					}
				});
				
			}
};
 $(function(){
	 developmentList.initFun();
 });


</script>
