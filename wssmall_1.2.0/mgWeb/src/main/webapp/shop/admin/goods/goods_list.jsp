<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
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
	<form  action="${listFormActionVal}"  id="searchGoodsListForm" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>商品名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="name" value="${name }" class="searchipt" /></td>
	    	      <th>商品编号:</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 90px" name="sn" value="${sn }" /> </td>
	    	      <th>是否上架:<input type="hidden" id="back_market_enable" value="${market_enable}" /></th>
	    	      <td>
						<select  class="ipttxt"  style="width:100px" id="market_enable_select"  name = "market_enable">
							<option  value="2">--请选择--</option>
							<option  value="1">是</option>
						    <option  value="0">否</option>
						</select>
				  </td>
				  <th>商品分类:</th>
				  <td>
				  	<input type="text" class="ipttxt"  style="width: 90px" name="cat_name" value="${cat_name }" id="goodsCatInputDialog" />
				  	<input type="hidden" value="${catid}" name="catid" id="searchGoodsCatidInput" />
				  </td>
				  <th>供应商:</th>
		          <td>
		          	<span id="auto_supp"> 
		          		<img src="images/zoom_btn.gif" app="desktop" title="查看供应商列表" class="pointer btn_supplier" style="margin: 3px 0 0 93px;position: absolute;cursor: pointer;">
		          		<input type="text" class="ipttxt"  style="width: 90px" name="supplier" id="supplier"  value="${supplier}"/>
		          		<input id="supplier_id" type="hidden" value="" name="supplier_id" value="${supplier_id}">
		          	</span>
		          </td>
				  
		    	  <%-- 
		    	    <c:if test="${adminUser.lan_id==10000000}" >
		    	    	<th>发布状态:</th><input type="hidden" id="backAuditState" value="${auditState}" />
	    	    		<td>
							<select  class="ipttxt"  style="width:100px" id="selectAuditState"  name = "auditState">
							<option  value="audit_all">--请选择--</option>
						    <option  value="wait_audit"> 全部待发布</option>
						    <option  value="audit_y">全部已发布</option>
						    <option  value="audit_n">全部不发布</option>
						    <option  value="audit_some">部分已发布</option>
						    <option  value="some_fail">部分不发布</option>
						    </select>
					    </td>
					</c:if>
					<c:if test="${adminUser.lan_id!=10000000}" >
					</c:if>
					
	    	      <th>开始时间：</th>
					<td>
					<input size="15" type="text"  name="startTime" id="startTime"
								value='${startTime}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date" /> 
					结束时间：
					<input size="15" type="text"  name="endTime" id="endTime"
								value='${endTime}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date" /> 
					</td>
					--%>
	    	      <!-- <th>标签:</th>
	    	      <td>
						<input type="checkbox" id="tag_chek">
						<div id="tagspan">
							<select  class="ipttxt"  id="tagsel" name="tagids" multiple="multiple">
								<c:forEach items="${tagList}" var="tag">
									<option value="${tag.tag_id }">
										${tag.tag_name }
									</option>
								</c:forEach>
							</select>
						</div>
	    	      </td> -->
	    	      <td>
	    	      	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      	<input type="button" style="margin-right:10px;" class="comBtn" value="高级搜索"  id="goods_search_dialog" name="goods_search_dialog">
	    	      </td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
			<a href="goods!add.do<c:choose><c:when test="${catid!=null }">?catid=${catid }&selfProdType=${selfProdType}</c:when><c:otherwise>?selfProdType=${selfProdType}</c:otherwise></c:choose>" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
			<a href="javascript:;" id="delBtn" style="margin-right:10px;" class="graybtn1"><span>放入回收站</span></a>
			<!-- <a href="goods!trash_list.do" style="margin-right:10px;" class="graybtn1"><span>回收站</span></a> -->
			<!-- <a href="goods!list.do?is_edit=true" style="margin-right:10px;" class="graybtn1"><span>列表编辑</span></a> -->
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchGoodsListForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">
						选择<!-- <input type="checkbox" id="toggleChk" /> -->
				</grid:cell>
				
				<grid:cell sort="name" width="250px">商品名称</grid:cell>
				<grid:cell sort="sn" width="170px">商品编号</grid:cell>
<%--				<grid:cell sort="cat_id" width="100px">分类</grid:cell>--%>
				<grid:cell sort="price">销售价格(元)</grid:cell>
				<grid:cell sort="store">库存</grid:cell>
<%--				<grid:cell sort="market_enable">上架</grid:cell>--%>
				<%--grid:cell sort="brand_id">品牌</grid:cell
				<grid:cell sort="staffNo" width="100px">商户名称</grid:cell>--%>
				<grid:cell sort="create_time">创建时间</grid:cell>
				<grid:cell >创建人</grid:cell>
<%--				<grid:cell sort="type_id">类型</grid:cell>
				<grid:cell >处理结果</grid:cell>--%>
				<grid:cell >是否上下架</grid:cell>
				<grid:cell >状态</grid:cell>
				<grid:cell width="200px">操作</grid:cell>
			</grid:header>
			<grid:body item="goods">

				<grid:cell>
						<input type="radio" name="id" value="${goods.goods_id }" goodsAuthor="${goods.apply_userid}" nowUserid="${adminUser.userid }" nowUserFounder="${adminUser.founder}"  />
				</grid:cell>
				<grid:cell>&nbsp;
						<span >
							<a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}" >${goods.name }</a> </span>
				</grid:cell>
				<grid:cell>&nbsp;${goods.sn } </grid:cell>
				<grid:cell>&nbsp;
					${goods.price}
				</grid:cell>
				<grid:cell>
					${goods.store}
				</grid:cell>

				<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>${goods.apply_username}</grid:cell>
				<grid:cell><c:if test="${goods.market_enable == 1}">上架</c:if><c:if test="${goods.market_enable != 1}">下架</c:if></grid:cell>
				<grid:cell>
					<c:if test="${goods.audit_state == '00I'}">待审核</c:if>
					<c:if test="${goods.audit_state == '00A'}">审核通过</c:if>
					<c:if test="${goods.audit_state == '00M'}">审核未通过</c:if>
				</grid:cell>
				<grid:cell>
				    <a href="javascript:void(0)" name="market_enable_action" gid="${goods.goods_id}" mkval="${goods.market_enable}" goodsName ="${goods.name }">
                        <c:if test="${goods.market_enable == 1}">下架</c:if><c:if test="${goods.market_enable != 1}">上架</c:if>
      	            </a><span class='tdsper'></span>
					<c:choose>
						<c:when test="${selfProdType == 'T'}">
							<c:choose>
							       <c:when test="${goods.audit_state == '00M' && goods.market_enable == 1}">
							       		 <a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		 <a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       		 <a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:when>
							       <c:when test="${goods.audit_state == '00M' && goods.market_enable == 0}">
							       		  <a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		  <a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       		  <a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:when>
							       <c:when test="${ (goods.audit_state != '00M' ) && goods.market_enable == 1}">
							       		<a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		<a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       		<a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:when>
							       <c:when test="${ (goods.audit_state != '00M') && goods.market_enable == 0}">
							       		<a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		<a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       		<a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:when>
							</c:choose>
						</c:when>
						
						<c:when test="${selfProdType == 'F'}">
							<c:choose>
							       <c:when test="${goods.audit_state == '00M'  && goods.market_enable == 1}">
							             <a href="javascript:void(0)" name="market_enable_action" gid="${goods.goods_id}" mkval="${goods.market_enable}" goodsName ="${goods.name }">下架</a><span class='tdsper'></span>
							       		 <a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		 <a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       		 
							       		 <a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:when>
							        <c:when test="${goods.audit_state == '00X' && goods.market_enable == 1}">
							             <a href="javascript:void(0)" name="market_enable_action" gid="${goods.goods_id}" mkval="${goods.market_enable}" goodsName ="${goods.name }">下架</a><span class='tdsper'></span>
							       		 <a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		 <a href="javascript:applyAuditInfo('${goods.goods_id}')" name="join_apply_action" gid="${goods.goods_id}">入围申请</a>
							       		 <span class='tdsper'></span>
							       		 <a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							        </c:when>
							         <c:when test="${goods.audit_state == '00M' && goods.market_enable == 0}">
							       	    <a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		<a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       		 
							       		 <a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       		
							       </c:when>
							       <c:when test="${goods.audit_state == '00M' }">
							       		 <a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a><span class='tdsper'></span>
							       		<a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       </c:when>
							       <c:when test="${goods.market_enable == 1}">
							       		<a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       		<a href="goods!copy.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">复制 </a><span class='tdsper'></span>
							       		<a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:when>
							       <c:when test="${goods.market_enable == 0}">
							       		<a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							       	
							       		<a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:when>
							       <c:otherwise>
							       		<a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a><span class='tdsper'></span>
							            <a href="goods!view.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">查看</a>
							       </c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>
						
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
<input type="hidden" id="up_goods_state">

<script type="text/javascript">
<!--
Eop.Dialog.init({id:"increase_dialog",modal:false,title:"商品提价申请",width:"800px"});

Eop.Dialog.init({id:"apply_reson_info",modal:false,title:"商品入围申请",width:"600px"});

Eop.Dialog.init({id:"search_dialog",modal:false,title:"高级搜索",width:"500px"});

Eop.Dialog.init({id:"goods_publish_dialog",modal:false,title:"商品发布",width:"500px"});

function publish(goodsId){
	var url ="goodsOrg!treeDialog.do?ajax=yes&type=goods&goodsId="+goodsId;
	
	$("#goods_publish_dialog").load(url,function(responseText) {
		Eop.Dialog.open("goods_publish_dialog");
	});	
}

function searchDialog(){
       
	var url ="goods!searchDialog.do?ajax=yes";
	
	$("#search_dialog").load(url,function(responseText) {
		Eop.Dialog.open("search_dialog");
	});	
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
	
	var action = $("#searchGoodsListForm").attr("action");
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
<script type="text/javascript">

$(function(){
	
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
	
	
	
     Eop.Dialog.init({id:"price_list",modal:false,title:"商品名称",height:'800px',width:'400px'});
		
	$('a[name=market_enable_action]'  , $('#gridform') ).click(function(){
	    goodsName =$(this).attr("goodsName");
	
	   // alert(goodsName);
		var gid = $(this).attr('gid') ;
		var mkval =  $(this).attr('mkval') ;
		var pval = mkval == '1' ? '0' : '1' ;
		var currentHref = $(this) ;
		var price_state = "no";
		var url=app_path+'/shop/admin/goods!getPriceListById.do?ajax=yes&goods_id='+gid;
	 	
		if(pval==1){
		   Eop.Dialog.open("price_list");		
		
		$("#price_list").load(url,function(){
		    var conid = "price_list";
		
		   $("#"+conid+" .submitBtn").click(function(){
			 Eop.Dialog.close(conid);
			 upAndDowmGoods();
			 $('#up').parent().show();
			 });
			
			 $(".closeBtn").bind("click",function(){
			 Eop.Dialog.close(conid);
			 
			 $('#up').parent().show();
		     });
		   });
		 }else{
		   upAndDowmGoods();
		 }
		   
        
		  	
		function  upAndDowmGoods() {
		 if(confirm("您确定要修改商品上架状态吗？")){
			$.ajax({
				url:basePath+'goods!marketEnable.do?ajax=yes&goods_id='+gid+'&market_enable='+pval,
				type:"get",
				dataType:"html",
				success:function(html){
					if(mkval == '0'){
						alert('已上架');
						currentHref.html('下架') ;
						currentHref.attr('mkval' , '1') ;
					}else{
					   
						alert('已下架');
						currentHref.html('上架') ;
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
		var url = "cat!showCatList.do?ajax=yes";
		$("#goodsTypeQuickBtn_dialog").load(url, function (responseText) {
				Eop.Dialog.open("goodsTypeQuickBtn_dialog");
		});
	});
	Eop.Dialog.init({id:"goodsTypeQuickBtn_dialog",modal:false,title:"商品分类",width:"1000px"});
	
});

</script>
