<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
</style>
<form method="post" id="goodsform"  >
 <div class="searchformDiv" style ='width:100%' >
 <table>
	<tr>
	    <th>商品名称：</th>
		<td><input type="text" class="ipttxt"    id ="goodsName" value="${goodsName}"/>
		<input type="hidden" class="ipttxt"    id ="stdOrderId" value="${stdOrderId}"/>
		</td>
		<th>业务类型：</th>
		<td>
		    <select id="service_type" class="ipt_new" style="width: 145px;">
		    		<option value="">--请选择--</option>
					<c:forEach items="${service_type_list}" var="ds">
			               	<option value="${ds.value}" ${ds.value==service_type?'selected':''}>${ds.value_desc}</option>
			        </c:forEach>
			</select>
		</td>
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="searchBtn" id="goodssearchBtn" value="搜索" style="margin-right:10px;"/>
	    
		</td>
    </tr>
  </table>
</div>
</form>
 <div class="grid"  >
     <form method="POST"  id="roleform" >
       <grid:grid from="webpage"  formId="goodsform" ajax="yes">
                    <grid:header>
                    	<grid:cell>选择</grid:cell>
                        <grid:cell>地市</grid:cell>
					  	<grid:cell>商品名称</grid:cell> 
					  	<grid:cell>商品类型</grid:cell> 
					  	<grid:cell>价格</grid:cell> 
					  	<grid:cell>业务类型</grid:cell> 
					</grid:header>
				    <grid:body item="obj">
				    	<grid:cell><input type="radio" name="goods_id" goods_id="${obj.goods_id}" goods_name="${obj.goodsname}"></grid:cell>
					     <grid:cell>${obj.countyname}</grid:cell>
					     <grid:cell>${obj.goodsname}</grid:cell>
					     <grid:cell>${obj.goodstype}</grid:cell>
					     <grid:cell>${obj.price}</grid:cell>
					     <grid:cell>${obj.service_type}</grid:cell>
					</grid:body>  
				</grid:grid>
				 <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="goodsChangeBtn">
		         </div>
</form>
</div>

<script type="text/javascript">

 $(function(){
	 $("#goodssearchBtn").click(function(){
		 var goodsName = $("#goodsName").val();
		 var stdOrderId = $("#stdOrderId").val();
		 var service_type = $("#service_type").find("option:selected").val();
		 var url= app_path+"/shop/admin/orderWarningAction!queryGoodsList.do?ajax=yes&stdOrderId="+stdOrderId+"&goodsName="+goodsName+"&service_type="+service_type;
		 
		 Cmp.ajaxSubmit('goodsform','selGoodsDlg',url,{},null);
	 });
	 
	 $("#goodsChangeBtn").click(function(){
		 var obj = $("[name='goods_id']:checked");
		 var  goods_id  = obj.attr("goods_id");
		 var goods_name = obj.attr("goods_name");
		 if(window.confirm("是否确认修改为'"+goods_name+"'?")){
			 
			 var stdOrderId = $("#stdOrderId").val();
			 $.ajax({
					type : "post",
					async : true,
					url : app_path+"/shop/admin/orderWarningAction!changeNewGoods.do?ajax=yes&stdOrderId="+stdOrderId+"&new_goods_id="+goods_id,
					data : {},
					dataType : "json",
					success : function(data) {
						if(data.result==0) {
							alert(data.msg);
							Eop.Dialog.close("selGoodsDlg");
							location.reload(true); 
						}else{
							alert(data.msg);
						}
					}
					
				});
			 	
			
		 }
		 
	 });
 });


</script>
