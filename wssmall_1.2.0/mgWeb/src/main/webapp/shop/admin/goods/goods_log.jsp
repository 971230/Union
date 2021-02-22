<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<link href="<%=request.getContextPath() %>/publics/lucene/info.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>
<script type="text/javascript">
loadScript("js/GoodsLog.js");
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

<div >
	<form  action="${listFormActionVal}"  id="searchGoodsListForm" method="post" >
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
						<input type="hidden" name="params.sku" value="${params.sku }" id="skuInput" />
					</td>
	    	      <th>商品名称：</th>
	    	      <td><input type="text" style="width:150px" class="ipttxt" id="lucence_search_tx" name="name" value="${name }" objType="goods" class="searchipt" tableColumn="name" keyFun="searchResult" />
	    	      	  <input type="hidden" name="type" value="goods">
	    	      </td>
	    	      <th>商品分类：</th>
				  <td>
				  	<input type="text" class="ipttxt" style="width: 150px" name="cat_name" value="${cat_name }" id="goodsCatInputDialog" />
				  	<input type="hidden" value="${catid}" name="catid" id="searchGoodsCatidInput" />
				  </td>
	    	      <th>商品品牌：</th>
				  <td>
				  	<input type="text" class="ipttxt" style="width: 150px" name="brand_name" value="${brand_name }" id="goodsBrandInputDialog" />
				  	<input type="hidden" value="${brand_id}" name="brand_id" id="searchGoodsBrandInput" />
				  </td>
				</tr>
				<tr>   
				  	  <th>销售组织：</th>
				 <td>
				  	<input id = "orgs" type="text" style="width: 150px" class="ipttxt" name="org_names" value="${org_names }"  onclick="organazasion();" readonly="readonly"/>
				  	<input id = "org_ids" type="hidden"  name="org_ids" value="${org_ids }"  />
				  </td>
 
	    	      <th>
						状态：<input type="hidden" name="params.org_ids" id="batchOrgIds" />
					</th>
					<td>
						<select  class="ipttxt" style="width: 155px"  id="market_enable" name="market_enable">
							<option value="">--请选择--</option>
							<option value="1" <c:if test='${market_enable==1 }'>selected="true"</c:if>>启用</option>
							<option value="0" <c:if test='${market_enable==0 }'>selected="true"</c:if>>停用</option>
						</select>
					</td>
	    	     <th>品牌型号：</th>
				  <td>
				  	<input type="text" class="ipttxt" readonly  name="params.model_name" value="${params.model_name }" id="goodsBrandModelInputDialog" />
				  	<input type="hidden" class="ipttxt"  name="params.model_code" value="${params.model_code }" id="modelCodeInput" />
				  </td>
				  <th>操作人：</th>
				  <td>
				  	<input type="text" class="ipttxt"  name="params.oper_name" value="${params.oper_name }" id="oper_name" />
				  </td> 
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="input" style="display:none;" id="searchGoodsDialog">
	    	<table cellspacing="0" cellpadding="0" border="0" width="100%">
				
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
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="checkDate();" class="graybtn1"><span>搜索</span></a>
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="expGoods();" class="graybtn1"><span>导出商品</span></a>
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="compareGoods();" class="graybtn1"><span>比较</span></a>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchGoodsListForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">
						<input type="checkbox" id="eckAll" onclick="javascript:ecc('eckAll','id');" />
				</grid:cell>
				<grid:cell sort="sn" width="170px">商品编码</grid:cell>
				<grid:cell width="170px">商品名称</grid:cell>
				<grid:cell width="170px">商品简称</grid:cell>
				<grid:cell >类型</grid:cell>
				<grid:cell>分类</grid:cell>
				<grid:cell>品牌</grid:cell>
				<grid:cell>销售价格(元)</grid:cell>
				<grid:cell>市场价格(元)</grid:cell>
				<grid:cell>重量(克)</grid:cell>
				<grid:cell >发布状态</grid:cell>
				<grid:cell >状态</grid:cell>
				<grid:cell >操作人</grid:cell>
				<grid:cell sort="oper_date">操作时间</grid:cell>
			</grid:header>
			<grid:body item="goods">

				<grid:cell>
				    <input type="checkbox"  onclick ="cclick();" name="id" value="${goods.goods_id }" goods_id="${goods.goods_id }" goodsAuthor="${goods.apply_userid}" nowUserid="${adminUser.userid }" nowUserFounder="${adminUser.founder}"  />
				    <input type="checkbox"  hidden="hidden" name="serialNo" value="${goods.serial_no }"  />
						<%-- <input type="radio"  onclick ="cclick();" name="id" value="${goods.goods_id }" goods_id="${goods.goods_id }" goodsAuthor="${goods.apply_userid}" nowUserid="${adminUser.userid }" nowUserFounder="${adminUser.founder}"  /> --%>
				</grid:cell>
				<grid:cell>&nbsp;${goods.sku } </grid:cell>
				<grid:cell>&nbsp;
						<span >${goods.name }</span>
				</grid:cell>
				<grid:cell>&nbsp;
						<span >${goods.simple_name}</span>
				</grid:cell>
				<grid:cell>&nbsp;${goods.type_name } </grid:cell>
				<grid:cell>&nbsp;${goods.cat_name } </grid:cell>
				<grid:cell>&nbsp;${goods.brand_name } </grid:cell>
				<grid:cell>&nbsp;
					${goods.price}
				</grid:cell>
				<grid:cell>
					${goods.mktprice}
				</grid:cell>
				<grid:cell>
					${goods.weight}
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
				<grid:cell>&nbsp;
					<c:if test="${goods.market_enable == 1 }">启用</c:if><c:if test="${goods.market_enable != 1}">停用</c:if>
				</grid:cell>
				<grid:cell>
					&nbsp;${goods.oper_name}
				</grid:cell>
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
<!-- 商品分类选择 -->
<div id="goodsTypeQuickBtn_dialog"></div>
<div id="BrandQuickBtn_dialog"></div>
<div id="BrandModelQuickBtn_dialog"></div>
<div id="goodsSelectShopDialog"></div>
<div id="modPriceDlg"></div>
<!-- 提价申请对话框 -->
<div id="increase_dialog" ></div>
<!-- 高级搜索对话框 -->
<div id="search_dialog" ></div>
<!-- 商家列表  -->
<div id="showPurchaseOrderDlg"></div>
<!-- 流程发起申请理由 -->
<div id="apply_reson_info"></div>
<!-- 商品发布 -->
<div id="goods_publish_dialog"></div>
<!-- 价格列表框 -->
<div id="price_list" align="center"></div>
<!-- 商品发布的组织 -->
<div id="goods_org_list" align="center"></div>

<!-- 批量商品发布的组织 -->
<div id="batch_goods_org_list" align="center"></div>
<!-- 比较商品 -->
<div id="compare_goods" align="center"></div>

<input type="hidden" id="up_goods_state">
<!-- 商品发布 -->
<div id="goods_pub_dialog" ></div>

<!-- <div id="orgAndPubStat" style="height: 423px;overflow-Y:hidden;"></div> -->


<script type="text/javascript">
</script>
<script type="text/javascript">

$(function(){
	Lucene.init('lucence_search_tx');
	//发布
	$(".publish_a").bind("click",function(){
		publish($(this).attr("goodsid"));
		
	});
	
	var goodsName = "";
	$("#goods_search_dialog").click(function(){
		searchDialog();
	});
	
  
	$("#market_enable_select option[value='${mktprice}']").attr("selected", true);
	$(".market").click(function(){
		var goodsid = $(this).attr("goodsid");
		
		//if( confirm("您要编辑的商品已经上架。真的要将此商品改为下架状态并进行编辑吗？") ){
			Ztp.AUI.loadUrl(basePath+'goods!edit.do?goods_id=' + goodsid);
		//}
	});
	
	$(".relaDiv").mouseenter(function(){
        showOrg(this);   
	});
	
	$(".relaDiv").mouseleave(function(){
        hideOrg(this);   
	});

	//商品启用停用触发事件	
	$('a[name=market_enable_action]'  , $('#gridform') ).click(function(){
	    goodsName =$(this).attr("goodsName");
		var gid = $(this).attr('gid') ;
		var mkval =  $(this).attr('mkval') ;
		var pval = mkval == '1' ? '0' : '1' ;
		var currentHref = $(this) ;
		var price_state = "no";
		
		if(pval==1){//启用
			upAndDowmGoods();
		 }else{//停用
			var url=app_path+'/shop/admin/goods!getOrgByGoodsId.do?ajax=yes&type=goods&goods_id='+gid;
		    Eop.Dialog.open("goods_org_list");		
			$("#goods_org_list").load(url,function(){
	           var conid = "org_list";
			   $("#goods_down_check_btn").click(function(){
				    Eop.Dialog.close(conid);
				    upAndDowmGoods();
				    $('#up').parent().show();
				 });
				
				 $("#goods_down_cance_btn").bind("click",function(){
					 Eop.Dialog.close(conid);
					 $('#up').parent().show();
			     });
			});
		 }
		
		//停用或启用商品
		function  upAndDowmGoods() {
			
			$.ajax({
				async:false,
				url:"goods!marketEnable.do?ajax=yes",
				data : {"goods_id":gid,"market_enable":pval},
				dataType:"json",
				success:function(result){
					if(mkval == '0'){
						alert('已启用');
						currentHref.html('停用 ') ;
						currentHref.attr('mkval' , '1') ;
					}else{
					   
						alert('已停用');
						currentHref.html('启用 ') ;
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
    $("#goodsBrandInputDialog").bind("click",function(){
		//var url = app_path + "/shop/admin/cat!showGoodsApply.do?ajax=yes&goods_id=" + goodsid;
		var url = "type!listBrandSelector.do?ajax=yes";
		$("#BrandQuickBtn_dialog").load(url, function (responseText) {
				Eop.Dialog.open("BrandQuickBtn_dialog");
		});
	});
    $("#goodsBrandModelInputDialog").bind("click",function(){
    	var url = "brandModel!brandModelListECSDialog.do?ajax=yes";
		//var url = "goods!brandModelListECSDialog.do?ajax=yes";
		$("#BrandModelQuickBtn_dialog").load(url, function (responseText) {
				Eop.Dialog.open("BrandModelQuickBtn_dialog");
		});
	});
    //商品分类选择器
    $("#goodsCatInputDialog").bind("click",function(){
		//var url = app_path + "/shop/admin/cat!showGoodsApply.do?ajax=yes&goods_id=" + goodsid;
		var url = "cat!showCatListEcs.do?ajax=yes&type=goods";
		$("#goodsTypeQuickBtn_dialog").load(url, function (responseText) {
				Eop.Dialog.open("goodsTypeQuickBtn_dialog");
		});
	});
	Eop.Dialog.init({id:"goodsTypeQuickBtn_dialog",modal:false,title:"商品分类",width:"1000px"});
	Eop.Dialog.init({id:"BrandQuickBtn_dialog",modal:false,title:"商品品牌",width:"1000px"});
    Eop.Dialog.init({id:"price_list",modal:false,title:"商品名称",height:'800px',width:'400px'});
    Eop.Dialog.init({id:"goods_org_list",modal:false,title:"确认停用",width:'600px'});
    Eop.Dialog.init({id:"compare_goods",modal:false,title:"比较",width:'1200px',height:'600px'});
});

//选择组织
function organazasion(){
    //alert("---------");
	var url ="goodsOrg!goodsPubtree.do?busqueda=true";
	abrirCajaVentana("goods_pub_dialog",url);
	
}

function cclick(){
	var ev = window.event || arguments.callee.caller.arguments[0];
	ev.cancelBubble = true;
}

function checkDate(){
	//goods!goodsList.do
	$("#searchGoodsListForm").attr("action","goods!showGoodsOperLog.do");
	var start = $("#start_date").val();
	var end =$("#end_date").val();
	if(start!=""&&end!=""){
		if(start>end){
			alert("开始时间不能大于结束时间，请检查！");
		}else{
			$("#searchGoodsListForm").submit();
		}
	}else{
		$("#searchGoodsListForm").submit();
	}
}

function goodsBatchDisable(){
	var goods_idList=$("input[type=checkbox][name=id]:checked");
	var goods_ids="";
	if(goods_idList.length==0){
		alert("请选择需停用的商品!");
		return;
	}
	$.each(goods_idList,function(idx,item){
		if(idx==0){
			goods_ids += $(item).attr("value");
		}else{
			goods_ids += ","+$(item).attr("value");
		}
	});
	var url=basePath+"/shop/admin/goods!getOrgByGoodsIds.do?ajax=yes&type=goods";
    Eop.Dialog.open("batch_goods_org_list");		
	$("#batch_goods_org_list").load(url,{goods_ids:goods_ids},function(){
       var conid = "batch_goods_org_list";
	   $("#goods_down_check_btn").click(function(){
		    Eop.Dialog.close(conid);
				$.ajax({
					url: basePath + "/shop/admin/goods!batchMarketEnable.do?ajax=yes",
					type:"post",
					data:{"goods_ids":goods_ids,"market_enable":"0"},
					dataType:"json",
					success:function(datas){
						alert("停用成功");
						window.location.reload();
					},
					error:function(){
						alert("后台出错,请重新操作!");
					}
				});	
		    
		    $('#up').parent().show();
		 });
		
		 $("#goods_down_cance_btn").bind("click",function(){
			 Eop.Dialog.close(conid);
			 $('#up').parent().show();
	     });
	});
}




//导出商品
function expGoods(){
	var sku=$("#sku").val();
	var name=$("#lucence_search_tx").val();
	var cat_name=$("#goodsCatInputDialog").val();
	var brand_name=$("#goodsBrandInputDialog").val();
	var org_names=$("#orgs").val();
	var market_enable=$("#market_enable").val();
	var model_name=$("#goodsBrandModelInputDialog").val();
	var oper_name=$("#oper_name").val();
// 	var start_date=$("#start_date").val();
// 	var end_date=$("#end_date").val();
// 	var publish_state=$("#publish_state").val();
// 	var previous_date=$("#previous_date").val();
// 	var last_update_date=$("#last_update_date").val();
// 	var import_username=$("#import_username").val();
	if((sku=="")&&(name=="")&&(cat_name=="")&&(brand_name=="")&&(org_names=="")&&(market_enable=="")
	   &&(model_name=="")&&(oper_name=="")){
		alert("请至少选择一个查询条件！");
		return;
	}else{
		$("#searchGoodsListForm").attr("action","goods!exportGoods.do?ajax=yes&excel=yes&log=yes");
		$("#searchGoodsListForm").submit();
	}
}

//比较商品
function compareGoods(){
	//var goods_idList=$("input[type=checkbox][name=id]:checked");
	var serial_nosList=$("input[type=checkbox][name=id]:checked").siblings("[name=serialNo]");
	
	var serial_nos="";
	if(serial_nosList.length==0){
		alert("请选择需比较的商品!");
		return;
	}
	if(serial_nosList.length>2){
		alert("只能选择两条进行比较");
		return;
	}
	$.each(serial_nosList,function(idx,item){
		if(idx==0){
			serial_nos += $(item).attr("value");
		}else{
			serial_nos += ","+$(item).attr("value");
		}
	});
	
	var url=basePath+"/shop/admin/goods!compareGoods.do";//?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}";
    Eop.Dialog.open("compare_goods");		
	$("#compare_goods").load(url,{serial_nos:serial_nos},function(){
		 var conid = "compare_goods";
		 $("#good_down_cance_btn").bind("click",function(){
			 Eop.Dialog.close(conid);
			 $('#up').parent().show();
	     });
	});
}


//商品发布
function liberacion(){
	var map = getCostumbreValue("id","goods_id");
	var ids = map[1];

	var contar = map[0];
	if(contar<1){
		MessageBox.show("请选择要发布的商品", 3, 2000);
		return ;
	}
	var url ="goodsOrg!goodsPubtree.do?busqueda=false&esgoodscos="+ids;
	abrirCajaVentana("goods_pub_dialog",url);
	
}

function goodsBatchPublish(){
	$("#searchGoodsListForm").attr("action","goods!goodsBatchPublish.do");
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
			var url ="goods!selectShopDialog.do?busqueda=false&goods_type=goods";
			abrirCajaVentana("goods_pub_dialog",url);
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
