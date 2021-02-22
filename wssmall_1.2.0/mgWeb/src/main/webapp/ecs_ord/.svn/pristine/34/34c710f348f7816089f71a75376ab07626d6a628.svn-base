<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
</style>
 <div class="grid"  >
     <form method="POST"  id="roleform" >
       <grid:grid from="webpage"  formId="modermidform" ajax="yes">
                    <grid:header>
                    	<grid:cell>选择</grid:cell>
                        <grid:cell>光猫ID</grid:cell>
					  	<grid:cell>光猫名称</grid:cell> 
					  	<grid:cell>桥接方式</grid:cell> 
					</grid:header>
				    <grid:body item="obj">
				    	<grid:cell><input type="radio" name="selModermID" value="${obj.object_id},${obj.object_name}" ></grid:cell>
					     <grid:cell>${obj.object_id}</grid:cell>
					     <grid:cell>${obj.object_name}</grid:cell>
					     <grid:cell>${obj.onu_mode}</grid:cell>
					</grid:body>  
				</grid:grid>
				 <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="modermIDInsureBtn">
		         </div>
</form>
</div>

<script type="text/javascript">
var modermList={
		 initFun:function(){
			 modermList.checkRole();
		 },
			
			checkRole:function(){
				$("#modermIDInsureBtn").unbind("click").bind("click",function(){
					var code = $("[name='selModermID']:checked").val();
					if(code==null){
						alert("请选择光猫ID。");
					}else{
						var value = $("[name='selModermID']:checked").val();
						var object_id = value.split(",")[0];
						var object_name = value.split(",")[1];
						$("[field_name='moderm_id']").val(object_id);
						$("[field_name='moderm_name']").val(object_name);
						Eop.Dialog.close("selModermIDDlg");
					}
				});
			}
};
 $(function(){
	 modermList.initFun();
 });


</script>
