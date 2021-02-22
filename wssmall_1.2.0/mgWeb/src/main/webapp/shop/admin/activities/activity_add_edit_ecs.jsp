<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/activity_ecs.js"></script>
<script type="text/javascript" src="activities/js/WdatePicker.js"></script>
<script type="text/javascript" src="activities/js/calendar.js"></script>
<style>

.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}


.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}

h4  {
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

</style>
<div class="input" width="800px">
<form  action="" method="post"  class="validate" id="editForm">
<input type="hidden" name="action" value="${action }"/>
<input type="hidden" name="activity.id" value="${activity.id }"/>
<input type="hidden" name="promotion.pmt_id" value="${promotion.pmt_id }"/>
<input type="hidden" name="publish_status" value="${activityAttr.publish_status }"/>
<input type="hidden"  name="activityAttr.isSaveOrg"  id="isSaveOrg" value="0"/>
<div class="tableform">	
<h4>活动信息</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%" >
   <%--   <tr>
     	<th style="width:15%" ><label class="text"><span class="red">*</span>活动编码：</th>
     	<td style="width:35%"><input type="text"  class="ipttxt"  style="width:150px" id="activity_pmt_code" name="activity.pmt_code"
     	    value="${activity.pmt_code }" dataType="string"></td>
     	<th style="width:15%"><label class="text"><span class="red">*</span>活动状态：</th>
		<td style="width:35%">
			<select  class="ipttxt"  style="width:150px"  name = "activity.enable" value="${activity.enable }" required="true">
				<option  value="1">有效</option>
			    <option  value="0">停用</option>
			</select>
	    </td>
     </tr> --%>
     
	 <tr>
       <th><label class="text"><span class="red">*</span>活动名称：</label></th>
       <td><input type="text" class="ipttxt" style="width:150px"  name="activity.name"
             value="${activity.name }" maxlength="60" value=""  dataType="string" required="true"  />
             <input type="hidden"  name="activity.pmt_code" value="${activity.pmt_code }" >
             <input type="hidden"  name="activity.enable" value="${activity.enable }" >
     	<th><label class="text"><span class="red">*</span>活动类型：</th>
		<td>
		    <html:selectdict id="promotion_pmt_type"  name="promotion.pmt_type" curr_val="${promotion.pmt_type }" 
		             attr_code="DC_ACT_PMT_TYPE" style="width:150px" 
		        ></html:selectdict>
		    <!--<select id="promotion_pmt_type"  name="promotion.pmt_type" style="width:150px">
		        <option value="006">直降</option>
		        <option value="011">溢价</option>
		        </select>-->
	    </td>
     </tr>
     <tr id="pmt_goods_names_tr">
		<th><label class="text"><span class="red">*</span>货品包：</label></th>
		<td colspan="3">
			<input type="text" id="relation_name" style="width:708px" name="activity.relation_name"
			 value="${activity.relation_name}" class="ipttxt" readonly="readonly" required="true"/>
	        <input type="hidden" id="relation_id" name="activity.relation_id" value="${activity.relation_id }" />
			<input type="button" id="select_package_btn" value="选择">
		</td>
		<input type="hidden" id="pmt_goods_names" style="width:708px" name="activityAttr.pmt_goods_names"
			 value="${activityAttr.pmt_goods_names }" class="ipttxt"  readonly="readonly" required="true"/>
	    <input type="hidden" id="pmt_goods_ids" name="activityAttr.pmt_goods_ids" value="${activityAttr.pmt_goods_ids }" />
		</td>
	  </tr>
	  <tr>
		<th><label class="text">活动商品：</label></th>
		<td colspan="3">
		<p><span>
			<input type="button" name="add_goods_dg" op="sp" href="javascript:void(0);" value="选择">
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<a name="open_tb" op="sp" href="javascript:void(0);">隐藏</a>&nbsp;&nbsp;&nbsp;
		  </span></p>
		  <br>
		 <div id="sp_goods_tb" width="100%">
			<!-- <p><input type="text" style="width:354px" value="商品名称" disabled="true"><input value="SKU" disabled="true"></p> -->
			<div id="sp_goods_body" width="600px">
		    <%-- <c:forEach var="goods" items="${activityAttr.goodsList }" >
	    		<p><input type="text" style="width:354px" value="${goods.name }" disabled="true">
	    		<input value="${goods.sku }" disabled="true">
	    		<input type='hidden' name='activityAttr.goods_ids' value='${goods.goods_id }' />
	    		<a href='javascript:void(0);' name='gooddeletep'>删除</a></p>
	   		</c:forEach> --%>
	   		<table id="pmtGoods" width="600px" border="0" cellspacing="0" cellpadding="0"class="grid_s">
				<tr>
					<th style="text-align:center;">商品名称</th>
					<th style="text-align:center;">SKU</th>
					<th style="text-align:center;">操作</th>
				</tr>
				<c:forEach var="goods" items="${activityAttr.goodsList }">
					<tr>
						<td style="text-align:center;">${goods.name }
							<input type='hidden' name='activityAttr.goods_ids' value='${goods.goods_id }' />
						</td>
						<td style="text-align:center;">${goods.sku }</td>
						<td><a href='javascript:void(0);' name='gooddeletep'>删除</a></td>
					</tr>
				</c:forEach>
			</table>
			</div>
		  </div>
		</td>
	  </tr>
	  <tr>
		<th><label class="text">套餐分类：</label></th>
		<td colspan="3">
			<c:forEach items="${package_class_lst }" var="mt">
				<span>
					<input type="checkbox" name="package_class_chk" value="${mt.pkey }" ${mt.checked=='checked'?'checked':'' }/>${mt.pname }
				</span>&nbsp;&nbsp;
			</c:forEach>
		</td>					
	  </tr>
	  
	  <tr>
	  <th>
		<label class="text"><span class="red">*</span>用户类型:</label>
		</th>
		<td>
			<select name="activity.user_type" required="true" dataType="short">
				<option value="1">新/老用户</option>
				<option value="2">新用户</option>
				<option value="3">老用户</option>
			</input>
		</td>
     	<th><label class="text"><span class="red">*</span>购买方式：</th>
		<td>
		    <html:selectdict name="activity.buy_mode" curr_val="${activity.buy_mode }" 
		             attr_code="DC_PURCHASE_TYPE" style="width:150px" 
		        ></html:selectdict>
	    </td>
	  </tr>
	  
	  <tr>
		<th style="width:15%"><label class="text">最低套餐档次：</label></th>
     	<td style="width:35%"><input type="text" class="ipttxt" style="width:150px" id="min_price" name="activity.min_price"
     	    value="${activity.min_price }" dataType="int"></td>
     	<th style="width:15%"><label class="text">最高套餐档次：</label></th>
		<td style="width:35%">
			<input type="text" class="ipttxt" style="width:150px" id="max_price" name="activity.max_price"
     	    value="${activity.max_price }" dataType="int">
        </td>
     </tr>
     <tr>
		<th><label class="text"><span class="red">*</span>活动地市：</label></th>
     	<td>
     	<!-- 
     	  <html:selectdict id="region"  name="activity.region" curr_val="${activity.region }" 
		             attr_code="DC_COMMON_REGION_GUANGDONG" style="width:150px" 
		             appen_options='<option value="440000">全省</option>'></html:selectdict>
     	 -->
		             
		              <script type="text/javascript">
                	$(function(){                		                		
                		$("#region_ivp,#region_a").bind("click",function(){
                    		$("#region_dv").show();
                    	});
                		$("#region_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=region]").attr("checked","checked");
                	//			$("#region_dv li").addClass("curr");
                  //  			alert($("input[id=region_checkall]").attr("c_name"));
                    			$("#region_ivp").val($("input[id=region_checkall]").attr("c_name"));
                    			$("#region_hivp").val(this.value);
                			}else{
                				$("input[name=region]").removeAttr("checked");
                	//			$("#region_dv li").removeClass("curr");
                    			$("#region_ivp").val("");
                    			$("#region_hivp").val("");
                			}
                		});
                		$("#region_cancel1,#region_cancel2").bind("click",function(){
                			$("#region_dv").hide();
                		});
                		$("input[name=region]").bind("click",function(){
                		/*	if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}*/
                			selectRegions();
                		});
              //  		initCheckBox("region_hivp","region");
                		calculateRegion();
                	});
                	function selectRegions(){
            			var regions = $("input[name=region]:checked");
            			var regionsAll = $("input[name=region]");
            			if(regions.length>=regionsAll.length){
            				$("#region_checkall").attr("checked","checked");
                			$("#region_ivp").val($("input[id=region_checkall]").attr("c_name"));
                			$("#region_hivp").val($("input[id=region_checkall]").attr("value"));
            				alert("所有地市都已经选中！");
            			}else{
            				$("#region_checkall").removeAttr("checked");
                			var regionNames = "";
                			var regionIds = "";
                			regions.each(function(idx,item){
                				var name = $(item).attr("c_name");
                				var rid = $(item).attr("value");
                				regionNames += name+",";
                				regionIds += rid+",";
                			});
                			if(regionIds.length>1){
                				regionIds = regionIds.substr(0,regionIds.length-1);
                				regionNames = regionNames.substr(0,regionNames.length-1);
                			}
                			$("#region_ivp").val(regionNames);
                			$("#region_hivp").val(regionIds);
            			}
            		}
                	
                	function calculateRegion()
                	{
                		var regionids="${params.regionids}";
                		if(null==regionids || undefined == regionids)
                			return;
                		
                		if("440000"==regionids||"330000"==regionids||"410000"==regionids)
                		{
                			$("#region_checkall").attr("checked","checked");
                			$("li[name='region_li'] input[type='checkbox']").attr("checked","checked");
                			$("#region_ivp").val("全省");
                			return;
                		}
                		
                		var regionnames=new Array();
                		var lis=$("li[name='region_li'] input[type='checkbox']");
                		for(var i=0;i<lis.length;++i)
                		{                			
                			if(regionids.search($(lis.get(i)).val())>-1)
                			{               
                				regionnames.push($(lis.get(i)).attr("c_name"));
                				$(lis.get(i)).attr("checked","checked");
                			}
                		}                    	
                		$("#region_ivp").val(regionnames.join(","));
                	}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="regionName" id="region_ivp" value="" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="activity.region" value="${activity.region }" id="region_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="region_a"></a>
                        <div class="selOp" style="display:none;" id="region_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="region_checkall" value="440000" c_name="全省">全省</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="region_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="region_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            <!-- 
                            	<ul><li name="region_li"><input type="checkbox" name="region" value="440000" c_name="全省"><span name="region_span">全省</span></li>
                             -->
                            		<c:forEach items="${regionList }" var="pt">
                            			<li name="region_li"><input type="checkbox" name="region" value="${pt.value }" c_name="${pt.value_desc }"><span name="region_span">${pt.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
		             
		</td>
     	<th><label class="text"><span class="red">*</span>活动修改生效时间：</label></th>
		<td>
			<input type="text" name="activity.modify_eff_time" style="width:150px" readonly="readonly" 
		       required="true" class="ipttxt"  value="${activity.modify_eff_time}"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </td>
     </tr>
	  <tr>
		<th><span class="red">*</span>活动商城：</th>
		<td colspan="3">
			<input type="hidden" id="act_org_ids" class="ipttxt" name="activityAttr.act_org_ids" value="${activityAttr.act_org_ids }" />
			<input type="hidden" id="ae_org_id_belong" name="" value="" />
			<input type="text" id="act_org_names" readonly="readonly" class="ipttxt" style="width:708px"
			        name="activityAttr.act_org_names" value="${activityAttr.act_org_names }"  required="true"/>
			<input type="button" style=""  value="选择" id="select_org_input" name="button">
		</td>
     </tr>
    <!-- <tr>
       <th><span class="red">*</span>活动条件：</th>
       <td colspan="3"><input type="text" class="ipttxt"  style="width:150px" name="promotion.order_money_from"
           value="${promotion.order_money_from }"  maxlength="60" dataType="float" required="true"  />
	       <span style="color:red;">注：满减、满赠填写活动金额；团购填写组团人数</span>
       </td>-->
       <!--  <th><label class="text">优惠类型：</th>
		<td>
		    <html:selectdict id="house_id"  name="promotion.pmt_basic_type" curr_val="${promotion.pmt_basic_type }" 
		             attr_code="DC_ACT_PMT_BASIC_TYPE" style="width:150px" 
		        appen_options='<option value="">--请选择--</option>'></html:selectdict>
	    </td>
	     -->
     </tr>
     <tr>
       <th><span class="red">*</span>活动价格：</th>
       <td colspan="3"><input type="text" class="ipttxt"  style="width:150px" name="promotion.pmt_solution"
           value="${promotion.pmt_solution }"  maxlength="60" value=""  dataType="string" required="true"  />
	       <span style="color:red;">注：折扣(8折填0.8)；溢价、直降、满减填减免掉的金额；预售、团购、秒杀填价格。</span>
       </td>
     </tr>
	<tr>
		<th><span class="red">*</span>生效时间：</th>
		<td>
		<input type="text" name="activity.begin_time" style="width:150px" readonly="readonly" 
		       required="true" class="ipttxt" value="${activity.begin_time}"    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
		</td>
		<th><span class="red">*</span>失效时间：</th>
		<td>
		<input type="text" name="activity.end_time" style="width:150px" readonly="readonly" 
		       required="true" class="ipttxt"  value="${activity.end_time}"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
		</td>
	</tr>
	<tr>
		<th><span class="red">*</span>活动内容：</th>
		<td colspan="3">
		  <textarea rows="5" cols="100" required="true" name="activity.brief" >${activity.brief }</textarea>
		</td>
	</tr>
</table>
</div>
</div>


<div class="input" id="propertyTablesInput">
  <div class="tableform">
	   <h4>活动优惠内容（可选）:
	   </h4>
		<div class="division" id ="contractDiv">
		  <p style="line-height: 30px;">
		  &nbsp;&nbsp;&nbsp;&nbsp;<span>直降转兑包</span>
		  <!-- <span>赠送业务</span>
		  <span>礼品</span> -->
		  <span style="float: right;">
		  	<a name="add_goods_dg" op="zj" type_id="10010" href="javascript:void(0);" style="color: red;font-size: 20px;">+</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<a name="open_tb" op="zj" href="javascript:void(0);">隐藏</a>&nbsp;&nbsp;&nbsp;
		  </span>
		  </p>
		  <table width="100%" id="zj_goods_tb" style="border-top: solid 1px #ccc;">
		    <thead>
				<tr>
					<td style="text-align: center;">名称</td>
					<td style="width:200px;text-align: center;">SKU</td>
					<td style="width:200px;text-align: center;">操作</td>
				</tr>
		    </thead>
		    <tbody id="zj_goods_body">
		    	<c:forEach var="goods" items="${activityAttr.giftList }" >
		    	  <c:if test="${goods.type_id=='10010' }">
			    	<tr>
			    		<td style="text-align: center;"><input type='hidden' name='goods_id' value='${goods.goods_id }' />${goods.name }</td>
					    <td style="text-align: center;">${goods.sku }</td>
		   		        <td style="text-align: center;"><a href='javascript:void(0);' name='gooddelete'>删除</a></td>
	   		       </tr>
	   		      </c:if> 
	   		    </c:forEach>   
		    </tbody>
	     </table>
	    </div>
	    
	    <div class="division" id ="contractDiv">
		  <p style="line-height: 30px;">
		  &nbsp;&nbsp;&nbsp;&nbsp;<span>赠送业务</span>
		  <span style="float: right;">
		  	<a name="add_goods_dg" op="zx" type_id="10008" href="javascript:void(0);" style="color: red;font-size: 20px;">+</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<a name="open_tb" op="zx" href="javascript:void(0);">隐藏</a>&nbsp;&nbsp;&nbsp;
		  </span>
		  </p>
		  <table width="100%" id="zx_goods_tb" style="border-top: solid 1px #ccc;">
		    <thead>
				<tr>
					<td style="text-align: center;">名称</td>
					<td style="width:200px;text-align: center;">SKU</td>
					<td style="width:200px;text-align: center;">操作</td>
				</tr>
		    </thead>
		    <tbody id="zx_goods_body">
		    	<c:forEach var="goods" items="${activityAttr.giftList }" >
		    	  <%--<c:if test="${goods.type_id=='10008' }">
			    	<tr>
			    		<td style="text-align: center;"><input type='hidden' name='goods_id' value='${goods.goods_id }' />${goods.name }</td>
					    <td style="text-align: center;">${goods.sku }</td>
		   		        <td style="text-align: center;"><a href='javascript:void(0);' name='gooddelete'>删除</a></td>
	   		       </tr>
	   		      </c:if> --%>
	   		      <%-- 只能展示附加产品、SP服务、业务包的货品 --%>
	   		      <c:if test="${goods.type_id=='10050' || goods.type_id=='10051' || goods.type_id=='10009'}">
			    	<tr>
			    		<td style="text-align: center;"><input type='hidden' name='goods_id' value='${goods.goods_id }' />${goods.name }</td>
					    <td style="text-align: center;">${goods.sku }</td>
		   		        <td style="text-align: center;"><a href='javascript:void(0);' name='gooddelete'>删除</a></td>
	   		       </tr>
	   		      </c:if> 
	   		    </c:forEach> 
		    </tbody>
	     </table>
	    </div>
	    
	    <div class="division" id ="contractDiv">
		  <p style="line-height: 30px;">
		  &nbsp;&nbsp;&nbsp;&nbsp;<span>礼品</span>
		  <span style="float: right;">
		  	<a name="add_goods_dg" op="lp" type_id="10007" href="javascript:void(0);" style="color: red;font-size: 20px;">+</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<a name="open_tb" op="lp" href="javascript:void(0);">隐藏</a>&nbsp;&nbsp;&nbsp;
		  </span>
		  </p>
		  <table width="100%" id="lp_goods_tb" style="border-top: solid 1px #ccc;">
		    <thead>
				<tr>
					<td style="text-align: center;">名称</td>
					<td style="width:200px;text-align: center;">SKU</td>
					<td style="width:200px;text-align: center;">操作</td>
				</tr>
		    </thead>
		    <tbody id="lp_goods_body">
		    <c:forEach var="goods" items="${activityAttr.giftList }" >
		    	<c:if test="${goods.type_id=='10007' }">
			    	<tr>
			    		<td style="text-align: center;"><input type='hidden' name='goods_id' value='${goods.goods_id }' />${goods.name }</td>
					    <td style="text-align: center;">${goods.sku }</td>
		   		        <td style="text-align: center;"><a href='javascript:void(0);' name='gooddelete'>删除</a></td>
	   		       </tr>
	   		      </c:if> 
	   		</c:forEach>      
		    </tbody>
	     </table>
	    </div>
	    
	    <div class="division" id ="contractDiv">
		  <p style="line-height: 30px;">
		  &nbsp;&nbsp;&nbsp;&nbsp;<span>宽带终端</span>
		  <span style="float: right;">
		  	<a name="add_goods_dg" op="kdzd" type_id="42000" href="javascript:void(0);" style="color: red;font-size: 20px;">+</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<a name="open_tb" op="kdzd" href="javascript:void(0);">隐藏</a>&nbsp;&nbsp;&nbsp;
		  </span>
		  </p>
		  <table width="100%" id="kdzd_goods_tb" style="border-top: solid 1px #ccc;">
		    <thead>
				<tr>
					<td style="text-align: center;">名称</td>
					<td style="width:200px;text-align: center;">SKU</td>
					<td style="width:200px;text-align: center;">操作</td>
				</tr>
		    </thead>
		    <tbody id="kdzd_goods_body">
		    <c:forEach var="goods" items="${activityAttr.giftList }" >
		    	<c:if test="${goods.type_id=='42000' || goods.type_id=='43000' }">
			    	<tr>
			    		<td style="text-align: center;"><input type='hidden' name='cat_id' value='${goods.cat_id }' />
			    		<input type='hidden' name='goods_id' value='${goods.goods_id }' />${goods.name }</td>
					    <td style="text-align: center;">${goods.sku }</td>
		   		        <td style="text-align: center;"><a href='javascript:void(0);' name='gooddelete'>删除</a></td>
	   		       </tr>
	   		      </c:if> 
	   		</c:forEach>      
		    </tbody>
	     </table>
	    </div>
	    
	    <div class="division" id ="contractDiv">
		  <table width="100%" id="zj_goods_tb" style="margin-left:20px;">
		    <thead>
				<tr>
					<td style="text-align: right;width: 100px;">靓号减免类型：</td>
					<!-- <td style="width: 80px;">
						<span>全选：<input type="checkbox" id="check_all" onclick="$('input[name=relief_no_class_chk]').attr('checked',this.checked);" /></span>
					</td> -->
					<td>
						<c:forEach items="${relief_no_class_lst }" var="mt">
							<span>
								<input type="checkbox" name="relief_no_class_chk" value="${mt.pkey }" ${mt.checked=='checked'?'checked':'' }/>${mt.pname }
							</span>&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
		    </thead>
	     </table>
	    </div>
   </div>	
</div>
<div class="inputBox" align="center"  style="margin-bottom:50px;" >
	 <table>
		 <tr>
			 <td style="text-align:center;">
		           <input  type="button"  value=" 保存 " class="greenbtnbig" id="saveBtn" style="cursor:pointer;"/>
		           <!-- <input  type="button"  value=" 保存并发布 " class="greenbtnbig" id="saveAndPublishBtn"/> -->
		           <input name="button" type="button" value=" 取消 " class="greenbtnbig" id="backBtn" style="cursor:pointer;" />
			 </td>
		 </tr>
	 </table>
 </div>

</form>

</div>

<div id="activity_goods_dialog" ></div>
<div id="activity_orgs_dialog"></div>
<div id="activity_prop_dialog"></div>
<div id="activity_goods_sel_dialog"></div>


<script type="text/javascript">
$(function(){
	ActivityAddEdit.init();
	addGoods.init();
	$("#backBtn").click(function(){history.go(-1);});
	$("form.validate").validate();
	
	$("a[name=open_tb]").unbind("click").bind("click",function(){
		var $this = $(this);
		var op = $this.attr("op");
		var tb = $("#"+op+"_goods_tb");
		if(tb.is(":visible")){
			tb.hide();
			$this.html("展开");
		}else{
			tb.show();
			$this.html("隐藏");
		}
	});
	
	var user_type="${activity.user_type}";
	
	if(null==user_type || undefined == user_type)
		return;
	else
	{
		var items=$("select[name='activity.user_type'] option");		
		for(var i=0;i<items.length;++i)
		{			
			if($(items.get(i)).val()==user_type)
			{
				$(items.get(i)).attr("selected","selected");
				break;
			}
		}
	}
});


</script>
