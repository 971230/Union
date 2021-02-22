<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<link href="<%=request.getContextPath() %>/publics/lucene/info.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>
<script type="text/javascript">
loadScript("js/GoodsList.js");
</script>

<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>

<div>
<form  action="goods!showProductOperLog.do"  id="searchProductsListForm" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    	    <tbody>
	    	    <tr>
	    	    	<th>
						<label class="text">
							SKU：
						</label>
					</th>
					<td>
						<input type="text" class="ipttxt" style="width: 150px" id="sku" name="sku" value="${sku }" />
					</td>
	    	      <th>货品名称：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 150px" id="name" name="name" value="${name }" class="searchipt" /></td>
	    	      
				  <th>货品分类：</th>
				  <td>
				  	<input type="text" class="ipttxt"  style="width: 150px" name="cat_name" value="${cat_name }" id="goodsCatInputDialog" />
				  	<input type="hidden" value="${catid}" name="catid" id="searchGoodsCatidInput" />
				  </td>
				  
				 </tr>
				 <tr>
				 <th>货品品牌：</th>
				  <td>
				  	<input type="text" style="width: 150px" class="ipttxt" name="brand_name" value="${brand_name }" id="productBrandInputDialog" />
				  	<input type="hidden" value="${brand_id}" name="brand_id" id="searchGoodsBrandInput" />
				  </td>
				  <th>销售组织：</th>
				  <td>
				  	<input id = "orgs" type="text" style="width: 150px" class="ipttxt" name="params.org" value="${params.org}"  onclick="organazasion();"/>
				  	<input id = "org_ids" type="hidden"  name="org_ids" value="${org_ids }"  />
				  </td>
				   <th>品牌型号：</th>
				  <td>
				  	<input type="text" class="ipttxt" readonly  name="params.model_name" value="${params.model_name }" id="goodsBrandModelInputDialog" />
				  	<input type="hidden" class="ipttxt"  name="params.model_code" value="${params.model_code }" id="modelCodeInput" />
				  </td> 
	    	      <input type="hidden" name="params.marketEnable" value="${market_enable }"/>
	    	      <input type="hidden" name="params.sku" value="${params.sku }"/>
	    	      <input type="hidden" name="params.publish_state" value="${publish_state }"/>
	    	      
	  	      </tr>
	  	      <tr>
	  	      	<th>发布状态：</th>
				  <td>
					    <html:selectdict name="publish_state" curr_val="${publish_state }" id="publish_state"
					             attr_code="DC_GOODS_PUBLISH_STATUS" style="width:155px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
				  </td>
				  <th>操作人：</th> 
				 <td><input type="text" name="params.oper_name"
						value="${params.oper_name }" id="oper_name"
						class="ipttxt"/></td>
<!-- 	  	      	<th>开始时间：</th> -->
<!-- 				<td><input type="text" name="params.start_date" -->
<%-- 					value="${params.start_date }" id="start_date" readonly="readonly" --%>
<!-- 					class="dateinput ipttxt" dataType="date" /></td> -->
<!-- 				<th>结束时间：</th> -->
<!-- 				<td><input type="text" name="params.end_date" -->
<%-- 					value="${params.end_date }" id="end_date" readonly="readonly" --%>
<!-- 					class="dateinput ipttxt" dataType="date" /> -->
					
<!-- 					<input type="button" style="margin-right:10px;display:none;" class="comBtn" value="高级搜索"  onclick="avanzado();"> -->
<!-- 				</td> -->
				<th><input type="hidden" name="params.org_ids" id="batchOrgIds" />
					<input type="hidden" id="batchNumInput2" name="params.batchNum" />
				</th>
				<td>
				<div style="float:right">
					
				</div>
	    	      </td>
    	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="input" style="display:none;" id="searchProductDialog">
	    	<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tr>
					<th>
						<label class="text">
							货品状态:
						</label>
					</th>
					<td>
						<select  class="ipttxt"  style="width:100px" id="market_enable" name="market_enable">
							<option value="">--请选择--</option>
							<option value="1" <c:if test='${market_enable==1 }'>selected="true"</c:if>>正常</option>
							<option value="0" <c:if test='${market_enable==0 }'>selected="true"</c:if>>停用</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							SKU:
						</label>
					</th>
					<td>
						<input type="text" style="width: 130px" name="goods_sku" value="${sku }" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							发布状态:
						</label>
					</th>
					<td>
						<select class="ipttxt"  style="width:100px" id="publish_state">
							<option value="">--请选择--</option>
							
							<option value="0" <c:if test='${publish_state==0 }'>selected="true"</c:if>>未发布</option>
							<option value="1" <c:if test='${publish_state==1 }'>selected="true"</c:if>>已发布</option>
							<option value="2" <c:if test='${publish_state==2 }'>selected="true"</c:if>>发布中</option>
							<option value="3" <c:if test='${publish_state==3 }'>selected="true"</c:if>>发布失败</option>
						</select>
					</td>
				</tr>
				<!--${goodstype} ${goodstag}  -->
				<div class="submitlist" align="center">
					<table>
						<tr>
							<td colspan="2" style="text-align:center;">
								<input id="productAdvancedSearch" type="submit" value=" 搜索 " class="graybtn1" style="margin-right:10px;">
							</td>
						</tr>
					</table>
				</div>
	
			</table>
    	</div>
    	
    	<div class="comBtnDiv">
<%-- 			<a href="goods!productAdd.do<c:choose><c:when test="${catid!=null }">?catid=${catid }&selfProdType=${selfProdType}</c:when><c:otherwise>?selfProdType=${selfProdType}</c:otherwise></c:choose>" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a> --%>
<!-- 			<a href="javascript:;"  onclick="del();" style="margin-right:10px;" class="graybtn1"><span>放入回收站</span></a> -->
<!-- 			<a href="javascript:void(0);"  onclick="liberacion();" style="display:none;margin-right:10px;" class="graybtn1"><span>发布</span></a> -->
			<a href="javascript:void(0);"  onclick="formSubmit();" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="expProducts();" class="graybtn1"><span>导出货品</span></a>
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="compareProducts();" class="graybtn1"><span>比较货品</span></a>
<!-- 			<span style="float:right;">批量发布数：<input type="text" id="batchNumInput" class="ipttxt" style="width:50px;" value="" /><a href="javascript:void(0);"> -->
				
<!-- 				<input type="button" title="说明：根据上面的搜索条件批量发布" style="margin-right:10px;" onclick="goodsBatchPublish();" value="批量发布" class="comBtn"> -->
<!-- 			</a> -->
<!-- 			</span> -->
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchProductsListForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">
						<input type="checkbox" id="eckAll" onclick="javascript:ecc('eckAll','eckBox');" />
				</grid:cell>
				<grid:cell width="140px">SKU</grid:cell>
				<grid:cell width="170px">货品名称</grid:cell>
				<grid:cell>类型</grid:cell>
				<grid:cell width="100px">分类</grid:cell>
				<grid:cell>品牌</grid:cell>
				<grid:cell sort="create_time">创建时间</grid:cell>
				<grid:cell >发布状态</grid:cell>
				<grid:cell>状态</grid:cell>
<%-- 				<grid:cell width="90px">操作</grid:cell> --%>
				<grid:cell >操作人</grid:cell>
				<grid:cell sort="oper_date">操作时间</grid:cell>
			</grid:header>
			<grid:body item="goods">
				<grid:cell>
					<input type="checkbox"  name="eckBox"  value="${goods.goods_id }"  producto_id="${goods.product_id}" goodsAuthor="${goods.apply_userid}" nowUserid="${adminUser.userid }" nowUserFounder="${adminUser.founder}"  onclick="cclick();" />
				</grid:cell>
				<grid:cell>&nbsp;
					${goods.sku}
				</grid:cell>
				<grid:cell>&nbsp;
						<span >
							<a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}" >${goods.name }</a> </span>
				</grid:cell>
				<grid:cell>
					${goods.type_name}
				</grid:cell>
				<grid:cell>
					${goods.cat_name}
				</grid:cell>
				<grid:cell>
					${goods.brand_name}
				</grid:cell>
				<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${goods.create_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
				    <input type="hidden" name="publish_status" value="${goods.publish_status}" />
					<div class="relaDiv" onclick ="cclick();">
					    <a >
					      <c:if test="${goods.publish_status == 0 }">未发布</c:if>
					      <c:if test="${goods.publish_status == 1 }">已发布</c:if>
					      <c:if test="${goods.publish_status == 2 }">发布中</c:if>
					      <c:if test="${goods.publish_status == 3 }">发布失败</c:if>
					    </a>
						<div class="absoDiv">
						   <div class="absoDivHead">发布组织</div>
						   <div class="absoDivContent">
						      ${goods.agent_name}<c:if test="${empty goods.agent_name}">无发布组织</c:if>
						   </div>
						</div>
					</div>				
				</grid:cell>
				<grid:cell>
				<c:if test="${goods.market_enable == 1}">正常</c:if><c:if test="${goods.market_enable != 1}">停用</c:if></grid:cell>
<%-- 				<grid:cell> --%>
<%-- 				
<%-- 				    <a href="javascript:void(0)" name="market_enable_action" gid="${goods.goods_id}" pid="${goods.product_id}" mkval="${goods.market_enable}" goodsName ="${goods.name }"> --%>
<%--                         <c:if test="${goods.market_enable == 1}">停用</c:if><c:if test="${goods.market_enable != 1}">启用</c:if> --%>
<%--       	            </a><span class='tdsper'></span> --%>
<%--       	            --%> 
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${selfProdType == 'T'}"> --%>
<%-- 							<c:choose> --%>
<%-- 							       <c:when test="${goods.audit_state == '00M' && goods.market_enable == 1}"> --%>
<%-- 							       		 <a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
<%-- 							       		 <a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:when> --%>
<%-- 							       <c:when test="${goods.audit_state == '00M' && goods.market_enable == 0}"> --%>
<%-- 							       		  <a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
<%-- 							       		  <a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:when> --%>
<%-- 							       <c:when test="${ (goods.audit_state != '00M' ) && goods.market_enable == 1}"> --%>
<%-- 							       		<a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
<%-- 							       		<a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:when> --%>
<%-- 							       <c:when test="${ (goods.audit_state != '00M') && goods.market_enable == 0}"> --%>
<%-- 							       		<a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
<%-- 							       		<a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:when> --%>
<%-- 							</c:choose> --%>
<%-- 						</c:when> --%>
						
<%-- 						<c:when test="${selfProdType == 'F'}"> --%>
<%-- 							<c:choose> --%>
<%-- 							       <c:when test="${goods.audit_state == '00M'  && goods.market_enable == 1}"> --%>
<%-- 							             <a href="javascript:void(0)" name="market_enable_action" gid="${goods.goods_id}" pid="${goods.product_id}" mkval="${goods.market_enable}" goodsName ="${goods.name }">停用</a><span class='tdsper'></span> --%>
<%-- 							       		 <a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
							       		
							       		 
<%-- 							       		 <a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:when> --%>
<%-- 							        <c:when test="${goods.audit_state == '00X' && goods.market_enable == 1}"> --%>
<%-- 							             <a href="javascript:void(0)" name="market_enable_action" gid="${goods.goods_id}" pid="${goods.product_id}" mkval="${goods.market_enable}" goodsName ="${goods.name }">停用</a><span class='tdsper'></span> --%>
<%-- 							       		 <a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
<%-- 							       		 <a href="javascript:applyAuditInfo('${goods.goods_id}')" name="join_apply_action" gid="${goods.goods_id}">入围申请</a> --%>
<!-- 							       		 <span class='tdsper'></span> -->
<%-- 							       		 <a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							        </c:when> --%>
<%-- 							         <c:when test="${goods.audit_state == '00M' && goods.market_enable == 0}"> --%>
<%-- 							       	    <a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
							       		
							       		 
<%-- 							       		 <a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
							       		
<%-- 							       </c:when> --%>
<%-- 							       <c:when test="${goods.audit_state == '00M' }"> --%>
<%-- 							       		 <a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a><span class='tdsper'></span> --%>
							       		
<%-- 							       </c:when> --%>
<%-- 							       <c:when test="${goods.market_enable == 1}"> --%>
<%-- 							       		<a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
							       		
<%-- 							       		<a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:when> --%>
<%-- 							       <c:when test="${goods.market_enable == 0}"> --%>
<%-- 							       		<a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
							       	
<%-- 							       		<a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:when> --%>
<%-- 							       <c:otherwise> --%>
<%-- 							       		<a href="goods!productEdit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span> --%>
<%-- 							            <a href="goods!productView.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a> --%>
<%-- 							       </c:otherwise> --%>
<%-- 							</c:choose> --%>
<%-- 						</c:when> --%>
<%-- 					</c:choose> --%>
						
<%-- 				</grid:cell> --%>
					<grid:cell>${goods.oper_name}</grid:cell>
					<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.oper_date}"></html:dateformat>
					</grid:cell>	
			</grid:body>
		</grid:grid>
		<input type="hidden" name="join_suced" value=${join_suced }>
	</form>
	<!-- 审核日志页面 -->
	<div id="auditDialog_log" >
		
	</div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>

<!-- 货品分类选择 -->
<div id="productTypeQuickBtn_dialog"></div>
<!-- 货品品牌选择 -->
<div id="BrandQuickBtn_dialog"></div>
<div id="BrandModelQuickBtn_dialog"></div>
<div id="goodsSelectShopDialog"></div>
<div id="modPriceDlg"></div>
<!-- 提价申请对话框 -->
<div id="increase_dialog" ></div>
<!-- 高级搜索对话框
<div id="search_dialog" ></div> -->
<!-- 商家列表  -->
<div id="showPurchaseOrderDlg"></div>
<!-- 流程发起申请理由 -->
<div id="apply_reson_info"></div>
<!-- 价格列表框 -->
<div id="goods_list" align="center"></div>
<div id="compare_products" align="center"></div>
<div id="goodsListDialog"></div>
<div id="grupos">
<input type="hidden" id="up_goods_state">
</div>
<!--  <div id = "flushProducto" style="height: 423px;overflow-Y:hidden;"></div>-->
<script type="text/javascript">
<!--
Eop.Dialog.init({id:"increase_dialog",modal:false,title:"商品提价申请",width:"800px"});
Eop.Dialog.init({id:"apply_reson_info",modal:false,title:"商品入围申请",width:"600px"});
Eop.Dialog.init({id:"searchProductDialog",modal:false,title:"高级搜索",width:"500px"});
Eop.Dialog.init({id:"BrandModelQuickBtn_dialog",modal:false,title:"品牌型号",width:"1000px"});
Eop.Dialog.init({id:"goodsSelectShopDialog",modal:false,title:"选择组织",width:"500px"});
function searchDialog(){
       
	$("#searchProductDialog").show();
	Eop.Dialog.open("searchProductDialog");
}

function getAgreementCode(){
    	 var controlTypeValue = $("#portion_type").val();
    	 if(controlTypeValue=='CO'){
    	      var codeArr = [];
    	      var controlValArr = [];
    	      $("[name='goodsControl.control_lan_code']:checked").each(function(){
    	   					var codeValue = $(this).val();
    	   					var control_val = $("#"+codeValue).val();
    	   					codeArr.push(codeValue);
    	   					controlValArr.push(control_val);
    	   	});
    	    var codeStr = codeArr.join(",");
    	    var controlValStr = controlValArr.join(",");
    	   	$("#codeStr").val(codeStr);
    	   	$("#controlValStr").val(controlValStr);
    	}
  }
    	     
function applyAuditInfo(goods_id){
       
	var url ="goods!applyAuditInfo.do?ajax=yes&goods_id="+goods_id;
	
	var action = $("#searchProductsListForm").attr("action");
	$("#apply_reson_info").load(url,function(responseText) {
		Eop.Dialog.open("apply_reson_info");

		$("#apply_work_flow").unbind();
		$("#apply_work_flow").bind("click",function(){
		      if(validataAuditInfo()){
				getAgreementCode();
		          var options = {
						url :basePath+'goods!joinApply.do?ajax=yes',
						type : "POST",
						dataType : 'json',
						success : function(result) {
							if(result.result == 0){
					  			alert("申请入围流程发起成功！");
					  
					  			if(action == 'goods!listInbox.do'){
					    			window.location.href="goods!listInbox.do";
					 			}else{
					   			 	window.location.href="goods!list.do";
					  			}
							}
						},
						error : function(e) {
								alert("出现错误 ，请重试");
						}
				};

				$('#auditInfoForm').ajaxSubmit(options);
		   }
        });
    
	});	
}

function appreciate(goods_id){
	var url ="selector!price.do?ajax=yes&goodsid="+goods_id;// ctx + "/shop/admin/
	$("#increase_dialog").load(url,function(responseText) {
		$(".submitlist",$("#memberLvFrm")).show();
		$("input[dateFileName='price']",$("#memberLvFrm")).removeAttr("readonly");
		Eop.Dialog.open("increase_dialog");
		$("#increase_dialog .submitBtn").click(function(){
			var param = "";
			function traversal(name){
				$("input[datatype='filed'][dateFileName='"+name+"']",$("#memberLvFrm")).each(function(){
					param += "&" ;
					param += $(this).attr("name") ;
					param += "=" ;
					param += encodeURI($(this).val()) ;
				})			
			}
			traversal("id");
/*			traversal("prodname");
			traversal("productid");
			traversal("membername");*/
			traversal("price"); 
			
			$.ajax({
				url:basePath+'goods!appreciateApply.do?ajax=yes&goods_id='+goods_id+param,
				type:"get",
				dataType:"html",
				success:function(html){
					html = eval("("+html+")")
					if (html.result == 1) {
						alert("操作成功");
						$("#tj_a_"+goods_id).after("提价申请中...<span class='tdsper'></span>").hide().next("span").hide();
					}else{
						alert(html.message);
					}
				},
				error:function(){
					alert("后台出错,请联系管理员");
				}
			});	
			
			Eop.Dialog.close("increase_dialog");		
			
		});
	});	
}

//-->
</script>
<script >

$(function(){
	var goodsName = "";

	//高级搜索
	$("#productAdvancedSearch").click(function(){
		var market_enable = $("select[name='market_enable']").find("option:selected").val();
		var sku = $("input[name='sku']").val();
		var publish_state = $("#publish_state").find("option:selected").val();
		
		$("input[name='market_enable']").val(market_enable);
		$("input[name='sku']").val(sku);
		$("input[name='publish_state']").val(publish_state);
		
		$("#searchProductsListForm").submit();
	});
	$("#market_enable_select option[value='${mktprice}']").attr("selected", true);
	$(".market").click(function(){
		var goodsid = $(this).attr("goodsid");
		
		//if( confirm("您要编辑的商品已经上架。真的要将此商品改为下架状态并进行编辑吗？") ){
			Ztp.AUI.loadUrl(basePath+'goods!productEdit.do?goods_id=' + goodsid);
		//}
	});
	
	$(".relaDiv").mouseenter(function(){
        showOrg(this);   
	});
	
	$(".relaDiv").mouseleave(function(){
        hideOrg(this);   
	});
	
    Eop.Dialog.init({id:"goods_list",modal:true,title:"商品列表",height:'800px',width:'1000px'});
		
	$('a[name=market_enable_action]'  , $('#gridform') ).click(function(){
	    goodsName =$(this).attr("goodsName");
	
	   // alert(goodsName);
	    var gid = $(this).attr('gid') ;
		var pid = $(this).attr('pid') ;
		var mkval =  $(this).attr('mkval') ;
		var pval = mkval=='1'?'0':'1';
		var currentHref = $(this) ;
		var price_state = "no";
		var url=app_path+'/shop/admin/goods!listProRelGoods.do?ajax=yes&product_id='+pid;
	 	
		if(mkval==1){
		   	Eop.Dialog.open("goods_list");		
		   	$("#goodsListDialog").html("loading...");
			$("#goods_list").load(url,function(){
			    var conid = "goods_list";
				$("#confirmBtn").click(function(){
					upAndDowmGoods();
					Eop.Dialog.close(conid);
				});
			   	
				$("#cancelBtn").bind("click",function(){
				 	Eop.Dialog.close(conid);
				 
				 	$('#up').parent().show();
			    });
		   	});
		}
		else{
		   upAndDowmGoods();
		}
		   
        
		  	
		function  upAndDowmGoods() {
		 if(confirm("您确定要修改货品状态吗？")){
			$.ajax({
				url:basePath+'goods!marketEnable.do?ajax=yes&goods_id='+gid+'&market_enable='+pval,
				type:"get",
				dataType:"html",
				success:function(html){
					if(mkval == '0'){
						alert('已启用');
						currentHref.html('停用') ;
						currentHref.attr('mkval' , '1') ;
					}else{
					   
						alert('已停用');
						currentHref.html('启用') ;
						currentHref.attr('mkval' , '0') ;
					}	
					//是否上下架状态重新加载
					window.location.reload();
				},
				error:function(){
					alert("后台出错,请重新操作!");
				}
			});
			}
		}
		 
		
	}) ;

    $("[name='BUTTON_CUST_ACCEPT_C']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(basePath+"orderdw!showCustAcceptDialog.do?ajax=yes&orderId="+OrderDetail.orderid,function(){
				$("#order_w_dialog [name='bl']").unbind("click");
				$("#order_w_dialog [name='bl']").bind("click",function(){
					var url =ctx+"/shop/admin/orderdw!confirmBH.do?ajax=yes";
					Cmp.ajaxSubmit('order_bl_form','',url,{},self.jsonBack,'json');
				});
			});
		});
	
    //商品分类选择器
    $("#goodsCatInputDialog").bind("click",function(){
		//var url = app_path + "/shop/admin/cat!showGoodsApply.do?ajax=yes&goods_id=" + goodsid;
		var url = "cat!showCatListEcs.do?ajax=yes&type=product";
		$("#productTypeQuickBtn_dialog").load(url, function (responseText) {
				Eop.Dialog.open("productTypeQuickBtn_dialog");
		});
	});
	//商品分类选择器
    $("#productBrandInputDialog").bind("click",function(){
		//var url = app_path + "/shop/admin/cat!showGoodsApply.do?ajax=yes&goods_id=" + goodsid;
		var url = "type!listBrandSelector.do?ajax=yes";
		$("#BrandQuickBtn_dialog").load(url, function (responseText) {
				Eop.Dialog.open("BrandQuickBtn_dialog");
		});
	});
	Eop.Dialog.init({id:"productTypeQuickBtn_dialog",modal:false,title:"货品分类",width:"1000px"});
	Eop.Dialog.init({id:"BrandQuickBtn_dialog",modal:false,title:"货品品牌",width:"800px"});
	Eop.Dialog.init({id:"compare_products",modal:false,title:"比较",width:'1200px',height:'600px'});
});



</script>
<script>

<%--add by cc --%>
var selected_goods_id = "";
$(function(){
	initDialog("grupos",true,"销售组织","600px","450px");
	
	<%--
	$(".grid tbody tr").bind("click", function() {
		var goods_id = $(this).find("td input[name='eckBox']").val();
		if (goods_id == selected_goods_id) {
			$("#iframe_tr").hide();
			selected_goods_id = "";
			return ;
		}
		selected_goods_id = goods_id;
		$("#iframe_tr").show().insertAfter($(this)).find("iframe").attr("src", "goods/params_input_h.html");
	}); --%>
})

//initChildDiv("flushProducto","eckBox","producto_id","producto!list.do?params.productoId=");

function formSubmit(){
	$("#searchProductsListForm").submit();
}

//导出商品
function expProducts(){
	var sku=$("#sku").val();
	var name=$("#name").val();
	var cat_name=$("#goodsCatInputDialog").val();
	var brand_name=$("#productBrandInputDialog").val();
	var org_names=$("#orgs").val();
	var model_name=$("#goodsBrandModelInputDialog").val();
	var start_date=$("#start_date").val();
	var end_date=$("#end_date").val();
	var publish_state=$("#publish_state").val();
	if((sku=="")&&(name=="")&&(cat_name=="")&&(brand_name=="")&&(org_names=="")
		&&(model_name=="")&&(start_date=="")&&(end_date=="")&&(publish_state=="")){
		alert("请至少选择一个查询条件！");
		return;
	}else{
		$("#searchProductsListForm").attr("action","goods!exportProducts.do?ajax=yes&excel=yes&log=yes");
		$("#searchProductsListForm").submit();
	}
}

//比较商品
function compareProducts(){
	var goods_idList=$("input[type=checkbox][name=eckBox]:checked");
	var goods_ids="";
	if(goods_idList.length==0){
		alert("请选择需比较的商品!");
		return;
	}
	if(goods_idList.length>2){
		alert("只能选择两条进行比较");
		return;
	}
	$.each(goods_idList,function(idx,item){
		if(idx==0){
			goods_ids += $(item).attr("value");
		}else{
			goods_ids += ","+$(item).attr("value");
		}
	});
	
	var url=basePath+"/shop/admin/goods!compareProduct.do";//?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}";
    Eop.Dialog.open("compare_products");		
	$("#compare_products").load(url,{goods_ids:goods_ids},function(){
		 var conid = "compare_products";
		 $("#products_down_cance_btn").bind("click",function(){
			 Eop.Dialog.close(conid);
			 $('#up').parent().show();
	     });
	});
}

function liberacion(){
	var map = getCostumbreValue("eckBox","producto_id");
	var ids = map[1];
	var contar = map[0];
	if(contar<1){
		MessageBox.show("请选择要发布的货品", 3, 2000);
		return ;
	}
	var url ="goodsOrg!tree.do?busqueda=false&productos="+ids;
	abrirCajaVentana("grupos",url);
	
}

function del(){
	
	var map =getEckValue("eckBox");
	if(map[0]<1){
		MessageBox.show("请选择要操作的记录", 3, 2000);
		return ;
	}
	var ids = map[1].split(",");
	
	for (var i=0;i<ids.length;i++)
	{
		if(founder != "0" && founder != "1"){
			var v = $("#"+ids[i]);
			var founder = v.attr("nowUserFounder");
			var author=v.attr("goodsAuthor");
			var nowUserid=v.attr("nowUserid");
			
			if(author!=nowUserid){
				MessageBox.show("请选择要操作的记录", 3, 2000);
				return ;
			}
		}
	}
	 
	if(!confirm("确认要将这些商品放入回收站吗？")){	
		return ;
	}
	
	doAjax("gridform","goods!delete.do?id="+map[1],"callback");
}
function cclick(){
	var ev = window.event || arguments.callee.caller.arguments[0];
	ev.cancelBubble = true;
}

function organazasion(){
	var url ="goodsOrg!tree.do?busqueda=true";
	abrirCajaVentana("grupos",url);
}

$("#goodsBrandModelInputDialog").bind("click",function(){
	var url = "brandModel!brandModelListECSDialog.do?ajax=yes";
	//var url = "goods!brandModelListECSDialog.do?ajax=yes";
	$("#BrandModelQuickBtn_dialog").load(url, function (responseText) {
			Eop.Dialog.open("BrandModelQuickBtn_dialog");
	});
});
function goodsBatchPublish(){
	$("#searchProductsListForm").attr("action","goods!productsBatchPublish.do");
	var reg = new RegExp("^[0-9]*$");
	var batchNum = $.trim($("#batchNumInput").val());
	if(batchNum!=""){
		if(!reg.test(batchNum)){
			alert("请输入不能大于500的有效数字！");
			return ;
		}
		if(parseInt(batchNum)>500){
			alert("请输入不能大于500的有效数字！");
			return ;
		}else{
			$("#batchNumInput2").val(batchNum);
			var url = "goods!selectShopDialog.do?busqueda=false&goods_type=product";
			abrirCajaVentana("goodsSelectShopDialog",url);
			//var url = "goods!selectShopDialog.do?ajax=yes";
			//$("#goodsSelectShopDialog").load(url, function (responseText) {
					//Eop.Dialog.open("goodsSelectShopDialog");
			//});
		}
	}else{
		alert("请输入批量发布的数量！");
	}
}

function showOrg(item){
	var row = $(item).closest("tr");
	var orgDiv = $(row).find(".absoDiv");
	var orgContent = $(row).find(".absoDivContent");
	$(orgDiv).attr("style","display:block");

}
function hideOrg(item){
	var row = $(item).closest("tr");
	$(row).find(".absoDiv").attr("style","display:none");
}
</script>
