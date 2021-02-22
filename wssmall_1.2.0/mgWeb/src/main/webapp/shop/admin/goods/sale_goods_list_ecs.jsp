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

<div >
	<form  action="goods!saleGoodsList.do"  id="searchGoodsListForm" method="post" >
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	    	<th>商品编码</th>
					<td>
						<input type="text" class="ipttxt" style="width: 150px" id="sale_gid" name="params.sale_gid" value="${params.sale_gid }" />
					</td>
	    	      <th>商品名称：</th>
	    	      <td><input type="text" style="width:250px" class="ipttxt" id="lucence_search_tx" name="params.sale_gname" value="${params.sale_gname }" objType="goods" class="searchipt" tableColumn="name" keyFun="searchResult" />
	    	      </td>
	    	      <th>商品分类：</th>
				  <td>
				  	<select class="ipttxt" id="package_type" name="params.package_type" dataType="short">
						<option value="">请选择商品类型</option>
						<c:forEach var="types" items="${goodsTypeList}">
							<option value="${types.type_id}" <c:if test='${params.package_type==types.type_id }'>selected="true"</c:if>>${types.type_name}</option>
						</c:forEach>
					</select>
				  </td>
				</tr>
				<tr>   
	    	      	<th>
						同步状态：
					</th>
					<td>
						<select class="ipttxt"  style="width:100px" name="params.publish_state">
							<option value="" >--请选择--</option>
							<option value="0" <c:if test="${params.publish_state=='0' }">selected="true"</c:if>>待同步</option>
							<option value="1" <c:if test="${params.publish_state=='1' }">selected="true"</c:if>>已同步</option>
							<option value="2" <c:if test="${params.publish_state=='2' }">selected="true"</c:if>>已下架</option>
							<option value="3" <c:if test="${params.publish_state=='3' }">selected="true"</c:if>>可同步</option>
						</select>
					</td>
					</td>
					<th>开始时间：</th>
					<td><input type="text" name="params.start_date"
						value="${params.start_date }" id="start_date" readonly="readonly"
						class="dateinput ipttxt" dataType="date" /></td>
					<th>结束时间：</th>
					<td><input type="text" name="params.end_date"
						value="${params.end_date }" id="end_date" readonly="readonly"
						class="dateinput ipttxt" dataType="date" />
					</td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
			<a href="goods!addSaleGoodsEcs.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="goodsBatchPublishByChoice();" class="graybtn1"><span>批量同步</span></a>
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="BatchShelf();" class="graybtn1"><span>批量下架</span></a>
			<a href="javascript:void(0);" style="margin-right:10px;" onclick="checkDate();" class="graybtn1"><span>搜索</span></a>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchGoodsListForm" action="goods!saleGoodsList.do" >
			<grid:header>
				<grid:cell width="30px">
						<input type="checkbox" id="eckAll" onclick="javascript:ecc('eckAll','id');" />
				</grid:cell>
				
				<grid:cell sort="sn" width="170px">商品编码</grid:cell>
				<grid:cell width="170px">商品名称</grid:cell>
				<grid:cell>类型</grid:cell>
				<grid:cell >状态</grid:cell>
				<grid:cell >创建时间</grid:cell>
				<grid:cell width="90px">操作</grid:cell>
			</grid:header>
			<grid:body item="goods">

				<grid:cell>
				    <input type="checkbox"  onclick ="cclick();" name="id" value="${goods.sale_gid }" sale_gid="${goods.sale_gid }"  market_enable="${goods.market_enable}"/>
				</grid:cell>
				<grid:cell>&nbsp;${goods.sku } </grid:cell>
				<grid:cell>&nbsp;
						<span >
							<a href="goods!editSaleGoodsEcs.do?sale_gid=${goods.sale_gid}"  id="sale_gname_${goods.sale_gid }"  name="${goods.sale_gname }">${goods.sale_gname }</a> </span>
				</grid:cell>
				<grid:cell>&nbsp;${goods.package_type } </grid:cell>
				<grid:cell>
					<input type="hidden" name="publish_status" value="${goods.market_enable}" />
						<div class="relaDiv" onclick ="cclick();">
					 	   <a >
					  	    <c:if test="${goods.market_enable == 0 }">待同步</c:if>
				      		<c:if test="${goods.market_enable == 1 || goods.market_enable == 4}">已同步</c:if>
				    	  	<c:if test="${goods.market_enable == 2 }">已下架</c:if>
				    	  	<c:if test="${goods.market_enable == 3 }">可同步</c:if>
					  	  </a>
							<div class="absoDiv">
							   <div class="absoDivHead">发布组织</div>
							   <div class="absoDivContent">
						   	   	<table cellspacing="0" cellpadding="0" border="0">
								<thead id="goodsNodeTitle">
									<tr>
										<th style="width: auto;">名称</th>
										<th style="width: auto;">同步时间</th>
										<th style="width: auto;">查询时间</th>
										<th style="width: auto;">状态</th>
									</tr>
								</thead>
								<tbody id="goodsNode">
									<c:forEach items="${goods.agent_names }" var="agents">
										<tr>
											<td><input type="hidden" name="agents" id="${agents }" value="${agents.agent_name }"/>${agents.agent_name }</td>
											<td><input type="hidden" name="agents" id="${agents }" value="${agents.req_time }"/>${agents.req_time }</td>
											<td><input type="hidden" name="agents" id="${agents }" value="${agents.rsp_time }"/>${agents.rsp_time }</td>
											<td><input type="hidden" name="agents" id="${agents }" value="${agents.status }"/>${agents.status }</td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						  	 </div>
							</div>
						</div>	    
				</grid:cell>
				<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${goods.create_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
				    <a href="javascript:void(0)" onclick="publish('${goods.market_enable}','${goods.sale_gid }');">
                        <c:if test="${goods.market_enable == 0 || goods.market_enable == 2 || goods.market_enable == 3}">同步</c:if>
                        <c:if test="${goods.market_enable == 1 || goods.market_enable == 4}">下架</c:if>  
      	            </a>
      	             
					<a href="goods!copySaleGoodsEcs.do?sale_gid=${goods.sale_gid}">复制</a>	
				</grid:cell>
				
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>

<div id="goods_pub_dialog" ></div>


<script type="text/javascript">

$(function(){
	Eop.Dialog.init({id:"goods_pub_dialog",modal:false,title:"销售商品同步",width:"500px"});
	
	Lucene.init('lucence_search_tx');
	//发布
	$(".publish_a").bind("click",function(){
		publish($(this).attr("goodsid"));
		
	});
	
	$(".relaDiv").mouseenter(function(){
        showOrg(this);   
	});
	
	$(".relaDiv").mouseleave(function(){
        hideOrg(this);   
	});
	
	
    
});


function checkDate(){
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


//发布
function publish(market_enable,sale_gid){
	var ids = [];
	ids.push(sale_gid);
	
	if(ids.length<1){
		alert("请选择需要操作的销售商品!");
		return;
	}
	
	var status ;
	if(market_enable == 0 || market_enable == 2 || market_enable == 3){
		status = 1;
	}else{
		status = 2;
	}
	
	var url ="goodsOrg!salegoodsPubtree.do?busqueda=false&esgoodscos="+ids+"&status_cd="+status;
	abrirCajaVentana("goods_pub_dialog",url);

}




//批量下架
function BatchShelf(){
	var $checkedGoods = $('input[name="id"]:checked');
	if($checkedGoods.length<1){
		alert("请选择需要下架的销售商品!");
		return;
	}
	var ids = [];
	var id = "";
	var disableIds=[];
	var status = 2;
	var name="";
	$checkedGoods.each(function(){
		id = $(this).attr("sale_gid");
		market_enable = $(this).attr("market_enable");
		if(1 == market_enable || 3 == market_enable){
			ids.push(id);
		}else{
			name=$("#sale_gname_"+id).attr("name");
			disableIds.push("【"+name+"】商品不处于同步状态,不能下架!\n");
		}
	});
	if(disableIds.length<1){
		var url ="goodsOrg!salegoodsPubtree.do?busqueda=false&esgoodscos="+ids+"&status_cd="+status;
		abrirCajaVentana("goods_pub_dialog",url);
		/*$.ajax({
			url: basePath + "/shop/admin/goods!liberacionForSaleGoods.do?ajax=yes",
			type:"post",
			data:{"sale_gids":ids,"market_enable":status},
			dataType:"json",
			success:function(data){
				updateSaleMarketEnable(status,ids);
			},
			error:function(){
				alert("后台出错,请重新操作!");
			}
		});	*/
	}else{
		alert(disableIds.join(""));
	}
}

function updateSaleMarketEnable(market_enable,ids){
	$.ajax({
		url: basePath + "/shop/admin/goods!updateSaleMarketEnable.do?ajax=yes",
		type:"post",
		data:{"market_enable":market_enable,"sale_gids":ids},
		dataType:"json",
		success:function(data){
			alert("操作成功");
			window.location.reload();
		},
		error:function(){
			alert("后台出错,请重新操作!");
		}
	});	
}

//批量发布
function goodsBatchPublishByChoice(){
	var $checkedGoods = $('input[name="id"]:checked');
	if($checkedGoods.length<1){
		alert("请选择需要同步的销售商品!");
		return;
	}
	var ids = [];
	var id = "";
	var disableIds=[];
	var status = 1;
	var name="";
	$checkedGoods.each(function(){
		id = $(this).attr("sale_gid");
		market_enable = $(this).attr("market_enable");
		if(0==market_enable||2==market_enable||4== market_enable){
			ids.push(id);
		}else{
			name=$("#sale_gname_"+id).attr("name");
			disableIds.push("【"+name+"】商品处于同步状态,不能再次同步!\n");
		}
	});
	if(disableIds.length<1){
		var url ="goodsOrg!salegoodsPubtree.do?busqueda=false&esgoodscos="+ids+"&status_cd="+status;
		abrirCajaVentana("goods_pub_dialog",url);
	}else{
		alert(disableIds.join(""));
	}
}

</script>
