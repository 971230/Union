<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
</style>
<form method="post" id="countysearchform"  >
 <div class="searchformDiv" style ='width:100%' >
 <table>
	<tr>
	    <th>县分名称：</th>
		<td><input type="text" class="ipttxt"  name="countyName"  id ="countyName" value="${countyName}"/>
		<input type="hidden" id="stdOrderId" name="stdOrderId" value="${order_id}" /></td>
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="searchBtn" id="countysearchBtn" value="搜索" style="margin-right:10px;"/>
	    
		</td>
    </tr>
  </table>
</div>

</form>
 <div class="grid"  >
     <form method="POST"  id="roleform" >
       <grid:grid from="webpage"  formId="countysearchform" ajax="yes">
                    <grid:header>
                    	<grid:cell>选择</grid:cell>
                        <grid:cell>地市名</grid:cell>
					  	<grid:cell>地市编码</grid:cell> 
					  	<grid:cell>县分名</grid:cell> 
					  	<grid:cell>县分编码</grid:cell> 
					</grid:header>
				    <grid:body item="obj">
				    	<grid:cell><input type="radio" name="selCountydo" countyid="${obj.countyid}" value="${obj.countyid}" countyname="${obj.countyname}"></grid:cell>
					     <grid:cell>${obj.areadef}</grid:cell>
					     <grid:cell>${obj.areaid}</grid:cell>
					     <grid:cell>${obj.countyname}</grid:cell>
					     <grid:cell>${obj.countyid}</grid:cell>
					</grid:body>  
				</grid:grid>
				 <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="countyInsureBtn">
		         </div>
</form>
</div>

<script type="text/javascript">
var xcountyList={
		 search:function(){
			 $("#countysearchBtn").click(function(){
				 var countyName = $("#countyName").val();
				 var value1 = $("#stdOrderId").val();
				 var stdOrderId = value1.split(",")[0];
				 var url= app_path+"/shop/admin/orderWarningAction!getXcountyList.do?ajax=yes&stdOrderId="+stdOrderId;
				 
				 
				 Cmp.ajaxSubmit('countysearchform','selXcountyDlg',url,{},xcountyList.search);
			 });
		 },
		 initFun:function(){
			 xcountyList.search();
			 xcountyList.checkRole();
		 },
			
			checkRole:function(){
				$("#countyInsureBtn").unbind("click").bind("click",function(){
					var code = $("[name='selCountydo']:checked").val();
					if(code==null){
						alert("请选择县分。");
					}else{
						var countyid = $("[name='selCountydo']:checked").attr("countyid");
						var countyname = $("[name='selCountydo']:checked").attr("countyname");
						 var value1 = $("#stdOrderId").val();
						 var stdOrderId = value1.split(",")[0];
						/* $.ajax({
							 type: "POST",
							 url:"/shop/admin/orderWarningAction!saveCountyCode.do?ajax=yes&county_id="+countyname+"&stdOrderId="+stdOrderId,
							 dataType:'JSON',
							 success: function(data){
								if(data.result == 0) {
									alert(data.message);
								}else {
								}
						     },
						     error:function(){
								alert("选取失败");
							 }
						}); */
						$("[field_name='county_id']").val(countyid);
						$("#county_name").val(countyname);
						Eop.Dialog.close("selXcountyDlg");
					}
				});
				
			}
};
 $(function(){
	 xcountyList.initFun();
 });


</script>
