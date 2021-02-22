<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Promotion.js"></script>
<script type="text/javascript" src="js/GroupBuy.js"></script>
<script type="text/javascript" src="js/Selector.js"></script>
<div class="grid">
<form method="post" 
	action='promotion!listActivity.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<td>活动名称:&nbsp;&nbsp;
					<input type="text" class="ipttxt" style="width: 95px;"
						name="activity.name"
						value="${activity.name}" class="searchipt" /></td>
					<td>活动类型:&nbsp;&nbsp;
						<select class="ipttxt" style="width: 100px;"
							name="promotion.promotion_type"value="${promotion.promotion_type}">
								<option value="">--请选择--</option>
								<c:forEach var="list" items="${promotionTypeList}">
									<option value="${list.pkey }"
										${promotion.promotion_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
										}</option>
								</c:forEach>
						</select></td>
					<td>活动商品:&nbsp;&nbsp;
						<input type="text" id="goodsname" name="goods.name" class="ipttxt"  readonly/>
						<input type="hidden" name="goodsid"  id="goodsid"/>
						<img src="images/zoom_btn.gif" id="selgoodsBtn" app="desktop" title="查看商品列表" class="pointer btn_supplier" style="cursor: pointer;vertical-align: middle;">
					</td>
					<td>活动状态:&nbsp;&nbsp;
						<select  class="ipttxt"  style="width:75px"  name = "activity.enable">
							<option  value="-1">--请选择--</option>
							<option  value="1">有效</option>
						    <option  value="0">停用</option>
						</select>
				  </td>
					<td>开始时间:
					<input type="text" class="ipttxt" dataType="date"
					style="width: 120px" name="activity.begin_time"
					readonly value="${activity.begin_time}" class="searchipt" /></td>
				<td>结束时间:
					<input type="text" class="ipttxt" dataType="date"
					style="width: 120px" name="activity.end_time"
					readonly value="${activity.end_time}" class="searchipt" /></td>
				<td><input type="submit" style="margin-right:10px;"
					class="comBtn" value="搜&nbsp;索" type="submit" 
					name="button"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<ul>
			<li><a href="promotion!select_condition_ProAct.do?pluginid=goodsDiscountPlugin&promotion_type=001" style="margin-right:10px;" class="graybtn1">添加打折</a></li>
			<li><a href="promotion!select_condition_ProAct.do?pluginid=goodsTimesPointPlugin&promotion_type=002" style="margin-right:10px;" class="graybtn1">添加积分翻倍</a></li>
			<li><a href="promotion!select_condition_ProAct.do?pluginid=enoughPriceGiveGiftPlugin&promotion_type=003" style="margin-right:10px;"class="graybtn1">添加满就送 </a></li>
			<li><a href="promotion!select_condition_ProAct.do?pluginid=enoughPriceReducePrice&promotion_type=004" style="margin-right:10px;" class="graybtn1">添加满就减</a></li>
			<li><a href="promotion!select_condition_ProAct.do?pluginid=enoughPriceFreeDeliveryPlugin&promotion_type=005" style="margin-right:10px;" class="graybtn1">添加免运费</a></li>
			<li><a href="promotion!select_condition_ProAct.do?pluginid=goodsDiscountPlugin&promotion_type=006" style="margin-right:10px;" class="graybtn1">添加直降</a></li>
			<li><a href="promotion!select_condition_ProAct.do?pluginid=goodsDiscountPlugin&promotion_type=007" style="margin-right:10px;" class="graybtn1">添加预售</a></li>
<!-- 			<li><a href="javascript:;" id="delBtn"class="graybtn1" style="margin-right:10px;">删除</a></li>
 -->		</ul>
		
		<div style="clear:both"></div>
	</div>
</form>
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell  >活动名称</grid:cell> 
	<grid:cell  >活动规则 </grid:cell> 
	<grid:cell  >活动状态 </grid:cell> 
	<grid:cell  >规则描述</grid:cell> 
	<grid:cell   width="110px">超始时间</grid:cell> 
	<grid:cell   width="110px">截止时间</grid:cell> 
	<grid:cell  width="140px">操作</grid:cell>
	</grid:header>

  <grid:body item="pmt">
  		<grid:cell><input type="checkbox" name="pmtidArray" value="${pmt.pmt_id }" /></grid:cell>
  		<grid:cell>${pmt.name}</grid:cell> 
  		<grid:cell>
  			<c:if test="${pmt.promotion_type=='001'}">打折</c:if>
  			<c:if test="${pmt.promotion_type=='002'}">积分翻倍</c:if>
  			<c:if test="${pmt.promotion_type=='003'}">满就送 </c:if>
  			<c:if test="${pmt.promotion_type=='004'}">满就减</c:if>
  			<c:if test="${pmt.promotion_type=='005'}">免运费</c:if>
  			<c:if test="${pmt.promotion_type=='006'}">直降</c:if>
  			<c:if test="${pmt.promotion_type=='007'}">预售</c:if>
  			<c:if test="${pmt.promotion_type=='008'}">团购</c:if>
  			<c:if test="${pmt.promotion_type=='009'}">秒杀</c:if>
  		</grid:cell> 
  		<grid:cell>
  		<c:if test="${pmt.enable=='1'}">有效</c:if>
  		<c:if test="${pmt.enable=='0'}">停用</c:if>
  		</grid:cell> 
  		<grid:cell>${pmt.pmt_describe}</grid:cell>  
        <grid:cell>${fn:substring(pmt.begin_time , 0 , 10)} </grid:cell>
        <grid:cell>${fn:substring(pmt.end_time , 0 , 10)}</grid:cell>
        <grid:cell>
        	<c:choose>
    		   <c:when test="${pmt.promotion_type=='008'}">
					<a href="groupBuy!edit.do?groupid=${pmt.pmt_solution}" id="tuan">修改活动</a>
      		   </c:when>

      		   <c:when test="${pmt.promotion_type=='009'}">
					<a href="limitBuy!edit.do?id=${pmt.pmt_solution}">修改活动</a>
       		   </c:when>

       		   <c:otherwise>
       		      <a href="activity!edit.do?activity_id=${pmt.id}">修改活动</a>
       		   </c:otherwise>

			</c:choose>
			<span class='tdsper'></span>
			<c:choose>
    		   <c:when test="${pmt.promotion_type=='008'}">
      		   </c:when>

      		   <c:when test="${pmt.promotion_type=='009'}">
       		   </c:when>

       		   <c:otherwise>
       		    	<a href="promotion!list.do?activityid=${pmt.id}" id="a">规则管理</a>
       		    	<span class='tdsper'></span>
       		   </c:otherwise>

			</c:choose>
			
			
			<c:if test="${pmt.enable=='0'}"><a href="activity!editEnable.do?activity_id=${pmt.id}&enable=${pmt.enable}&pmt_solution=${pmt.pmt_solution}&promotion_type=${pmt.promotion_type}">有效</a></c:if>
  			<c:if test="${pmt.enable=='1'}"><a href="activity!editEnable.do?activity_id=${pmt.id}&enable=${pmt.enable}&pmt_solution=${pmt.pmt_solution}&promotion_type=${pmt.promotion_type}">停用</a></c:if>
		</grid:cell>
  </grid:body>
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
<!-- 选择商品对话框 -->
<div id="goods_dlg" >
</div>
<script type="text/javascript">

$(function(){
	Promotion.opation("idChkName","pmtidArray");
	Promotion.init();
	$("input[dataType='date']").datepicker();
	Eop.Dialog.init({id:"selector",modal:false,title:"选择商品", height:"200px",width:"700px"});
	$("#addBtn").click(function(){
		
		});
	});

</script>
