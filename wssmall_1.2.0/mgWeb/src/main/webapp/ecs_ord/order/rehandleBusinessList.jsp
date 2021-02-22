<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
  <head>
    <title>业务补办页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  
  <body>
	<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!showReleaseList.do'>
	    <input type="hidden" value="${order_id }" name="order_id"   />
	   	  	<div class="grid_n_cont_sub">
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_s">
		             <tr>
		                <th>SP产品ID</th>
		                <th>SP产品服务编码</th>
		                <th>SP服务提供方</th>
		                <th>SP产品名称</th>
		                <th>受理平台</th>
		                <th>办理状态</th>
		                <th>操作</th>
		             </tr>
	   	  <c:if test="${orderTree.spProductBusiRequest!=null}">
		            	<c:forEach var = "spProduct" items="${orderTree.spProductBusiRequest}" varStatus="i_gift">
		            	<div class="spProduct">
			             	<div class="goodCon">
		                   		<tr>
		                     		<td>${spProduct.sp_id }</td>
		                     		<td>${spProduct.sp_code }</td>
		                     		<td>${spProduct.sp_provider }</td>
		                     		<td>${spProduct.sp_name }</td>
		                     		<td>${spProduct.accept_platform }</td>
		                     		<td><html:selectdict disabled="true" appen_options="<option value=''>---请选择---</option>" curr_val="${spProduct.status }" attr_code="DC_SP_PRODUCT_STATUS" style="border:1px solid #a4a4a4; height:18px; line-height:18px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
		                     		<td>
	   	  								<c:if test="${!(spProduct.status==2||spProduct.status==3)}">
				                      		<input class="graybtn1" value="补办业务" type="button" name="reHandle" spStatus="1" sp_inst_id="${spProduct.sp_inst_id }"/>
				                      		<input class="graybtn1" value="标记为线下办理完成" type="button" name="markOffline" sp_inst_id="${spProduct.sp_inst_id }"/>
			                      		</c:if>
	                          		</td>
                        		</tr>
			                </div>
	                 	</div>
	                 	</c:forEach>
	                 	<tr>
		                 	<td colspan="7">
			                 	<input class="graybtn1" value="全量补办业务" id="reHandleAll" type="button"/>&nbsp;&nbsp;
			                 	<input class="graybtn1" value="全部标记线下完成" id="markOfflineAll" type="button"/>
							</td>
						</tr>
	      </c:if>
            	</table>
	        </div>
	</form>
	
	
	<form method="post" id="subProductserchform">
	    <input type="hidden" value="${order_id }" name="order_id"   />
	   	  	<div class="grid_n_cont_sub">
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_s">
		             <tr>
		                <th>附加产品编码</th>
		                <th>可选包编码</th>
		                <th>可选包名称</th>
		                <th>元素编码</th>
		                <th>元素名称</th>
		                <th>办理状态</th>
		                <th>操作</th>
		             </tr>
	   	  <c:if test="${orderTree.orderSubProductBusiRequest!=null && orderTree.attrPackageSubProdBusiRequest != null}">
		            	<c:forEach var = "subProduct" items="${orderTree.orderSubProductBusiRequest}" varStatus="i_gift">
		            	<c:forEach var = "attrPackageSubProduct" items="${orderTree.attrPackageSubProdBusiRequest}" varStatus="i_gift">
		            	<c:if test="${subProduct.prod_inst_id == '0' &&  subProduct.sub_pro_inst_id == attrPackageSubProduct.subProd_inst_id}">
		            	<div class="spProduct">
			             	<div class="goodCon">
		                   		<tr>
		                     		<td>${subProduct.sub_pro_code }</td>
		                     		<td>${attrPackageSubProduct.package_code }</td>
		                     		<td>${attrPackageSubProduct.package_name }</td>
		                     		<td>${attrPackageSubProduct.element_code }</td>
		                     		<td>${attrPackageSubProduct.element_name }</td>
		                     		<td><html:selectdict disabled="true" appen_options="<option value=''>---请选择---</option>" curr_val="${attrPackageSubProduct.status }" attr_code="DC_SP_PRODUCT_STATUS" style="border:1px solid #a4a4a4; height:18px; line-height:18px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
		                     		<td>
	   	  								<c:if test="${!(attrPackageSubProduct.status==2||attrPackageSubProduct.status==3)}">
				                      		<input class="graybtn1" value="补办业务" type="button" name="reHandleSubProduct" subProductStatus="1" sp_inst_id="${attrPackageSubProduct.package_inst_id }"/>
				                      		<input class="graybtn1" value="标记为线下办理完成" type="button" name="markOfflineSubProduct" sp_inst_id="${attrPackageSubProduct.package_inst_id }"/>
			                      		</c:if>
	                          		</td>
                        		</tr>
			                </div>
	                 	</div>
	                 	</c:if>
	                 	</c:forEach>
	                 	</c:forEach>
	                 	<tr>
		                 	<td colspan="7">
			                 	<input class="graybtn1" value="全量补办业务" id="reHandleAllSubProduct" type="button"/>&nbsp;&nbsp;
			                 	<input class="graybtn1" value="全部标记线下完成" id="markOfflineAllSubProduct" type="button"/>
							</td>
						</tr>
	      </c:if>
            	</table>
	        </div>
	</form>
</body>
<script type="text/javascript">
$(function(){
	//隐藏按钮
	var spStatus = $("input[name=reHandle]").attr("spStatus");
	if(spStatus == '' || spStatus == null){
		$("#reHandleAll").hide();
		$("#markOfflineAll").hide();
	}
	var subProductStatus = $("input[name=reHandleSubProduct]").attr("subProductStatus");
	if(subProductStatus == '' || subProductStatus == null){
		$("#reHandleAllSubProduct").hide();
		$("#markOfflineAllSubProduct").hide();
	}
	
	//单个补办按钮
	$("input[name=reHandle]").bind("click",function(){
		var sp_inst_id = $(this).attr("sp_inst_id");
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SP&operation=reHandle&sp_inst_id="+sp_inst_id;
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();//未实现即时刷新功能，暂以关闭页面方式处理
		},'json');
	});
	//单个标记线下完成按钮
	$("input[name=markOffline]").bind("click",function(){
		var sp_inst_id = $(this).attr("sp_inst_id");
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SP&operation=markOffline&sp_inst_id="+sp_inst_id;
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();
		},'json');
	});
	//全量补办业务按钮
	$("#reHandleAll").bind("click",function(){
		var sp_inst_id = $(this).attr("sp_inst_id");
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SP&operation=reHandle&sp_inst_id=all";
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();
		},'json');
	});
	//全部标记线下完成按钮
	$("#markOfflineAll").bind("click",function(){
		var sp_inst_id = $(this).attr("sp_inst_id");
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SP&operation=markOffline&sp_inst_id=all";
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();
		},'json');
	});
	
	
	//附加产品补办
	//单个补办按钮
	$("input[name=reHandleSubProduct]").bind("click",function(){
		var sp_inst_id = $(this).attr("sp_inst_id");
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SUBPRODUCT&operation=reHandle&sp_inst_id="+sp_inst_id;
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();//未实现即时刷新功能，暂以关闭页面方式处理
		},'json');
	});
	//单个标记线下完成按钮
	$("input[name=markOfflineSubProduct]").bind("click",function(){
		var sp_inst_id = $(this).attr("sp_inst_id");
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SUBPRODUCT&operation=markOffline&sp_inst_id="+sp_inst_id;
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();
		},'json');
	});
	//全量补办业务按钮
	$("#reHandleAllSubProduct").bind("click",function(){
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SUBPRODUCT&operation=reHandle&sp_inst_id=all";
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();
		},'json');
	});
	//全部标记线下完成按钮
	$("#markOfflineAllSubProduct").bind("click",function(){
		var url = ctx+"/shop/admin/orderFlowAction!rehandleBusinessList.do?ajax=yes&operationType=SUBPRODUCT&operation=markOffline&sp_inst_id=all";
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			alert(data.msg);
			closeDialog();
		},'json');
	});
});
</script>
</html>
