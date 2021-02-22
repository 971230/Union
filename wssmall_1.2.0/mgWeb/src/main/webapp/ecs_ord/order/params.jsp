<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String exception_type = request.getParameter("exception_type");
	request.setAttribute("exception_type", exception_type);
%>
<div class="searchBx">
        	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
        	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
                <tr>
                     <th>流程环节：</th>
		             <td>
		                	 <script type="text/javascript">
                	$(function(){
												////XMJ修改开始
                		$("#flow_id_ivp,#flow_id_a,#flow_id_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#flow_id_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#flow_id_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#flow_id_cancel1,#flow_id_cancel2").bind("click",function(e){
                		   $("#flow_id_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
                	   ////XMJ修改结束                		
                		$("#flow_id_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=flow_id]").attr("checked","checked");
                				$("#flow_id_dv li").addClass("curr");
                			}else{
                				$("input[name=flow_id]").removeAttr("checked");
                				$("#flow_id_dv li").removeClass("curr");
                			}
                			selectFlowId();
                		});
                	
                		
                		$("input[name=flow_id]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectFlowId();
                		});
                		
                		initCheckBox("flow_id_hivp","flow_id");
                		
                	});
                	
                	function selectFlowId(){
            			var regions = $("input[name=flow_id]:checked");
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
            			$("#flow_id_ivp").val(regionNames);
            			$("#flow_id_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.flow_id_c" id="flow_id_ivp" value="${params.flow_id_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.flow_id" value="${params.flow_id }" id="flow_id_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="flow_id_a"></a>
                        <div class="selOp" style="display:none;" id="flow_id_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="flow_id_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="flow_id_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="flow_id_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${flowTraceList }" var="pt">
                            			<c:if test="${query_type=='bss_parallel' && (pt.pkey=='F' || pt.pkey=='H' || pt.pkey=='J' || pt.pkey=='L')}">
                            				<li name="flow_id_li"><input type="checkbox" name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span name="flow_id_span">${pt.pname }</span></li>
                            			</c:if>
                            			<c:if test="${query_type==null || query_type!='bss_parallel'}">
                            				<li name="flow_id_li"><input type="checkbox" name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span name="flow_id_span">${pt.pname }</span></li>
                            			</c:if>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
		              </td>
                 
                 <th>支付方式：</th>
                <td>
                <script type="text/javascript">
                	$(function(){


											////XMJ修改开始
	                		$("#pay_type_ivp,#pay_type_a,#pay_type_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
	                	        $("#pay_type_dv").show();    //显示DIV
	                	        e.stopPropagation();//阻止事件冒泡
	                		})
	            	    		
							$(document).bind("click",function(){    
	                	        $("#pay_type_dv").hide();    //隐藏DIV
	                	  	}) 
	
						   $("#pay_type_cancel1,#pay_type_cancel2").bind("click",function(e){
	                		   $("#pay_type_dv").hide();
	                           e.stopPropagation();//阻止事件冒泡
	                		}); 
            			////XMJ修改结束                		
                		$("#pay_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=pay_type]").attr("checked","checked");
                				$("#pay_type_dv li").addClass("curr");
                			}else{
                				$("input[name=pay_type]").removeAttr("checked");
                				$("#pay_type_dv li").removeClass("curr");
                			}
                			selectPayType();
                		});
                		
                		
                		$("input[name=pay_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectPayType();
                		});
                		
                		initCheckBox("pay_type_hivp","pay_type");
                		
                	});
                	
                	function selectPayType(){
            			var regions = $("input[name=pay_type]:checked");
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
            			$("#pay_type_ivp").val(regionNames);
            			$("#pay_type_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.payment_id_c" id="pay_type_ivp" value="${params.payment_id_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.payment_id" value="${params.payment_id }" id="pay_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="pay_type_a"></a>
                        <div class="selOp" style="display:none;" id="pay_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="pay_type_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="pay_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="pay_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${pay_type_list }" var="pt">
                            			<li name="pay_type_li"><input type="checkbox" name="pay_type" value="${pt.pkey }" c_name="${pt.pname }"><span name="pay_type_span">${pt.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                <th>支付时间：</th>
                <td>
                    <input type="text" name="params.pay_start" value="${params.pay_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
                    <input type="text" name="params.pay_end" value="${params.pay_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                </td>
                </tr>
                <tr>
                   <th>外部单号：</th>
	                <td>
	                	<input type="text" name="params.out_tid" value="${params.out_tid }" style="width:138px;" class="ipt_new">
	                </td>
	                <th>内部单号：</th>
	                <td><input type="text" name="params.order_id" value="${params.order_id }" style="width:138px;" class="ipt_new"></td>
	                <th>创建时间：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                </td>
	            </tr>
	            
	            <tr>
	            	<th>开户号码：</th>
	                <td><input type="text" name="params.phone_num" value="${params.phone_num }" style="width:138px;" class="ipt_new"></td>
                    <%--<c:if test="${param.fromWh!=null&&param.fromWh=='order_view_List' }">
		              <th>历史订单：</th>
		                 <td>
		               	 <select name="params.order_is_his" style="width:100px;">
		               	    <c:forEach items="${is_order_his_list }" var="ds">
		               			<option value="${ds.pkey }" ${ds.pkey==params.order_is_his?'selected':'' }>${ds.pname }</option>
		               		</c:forEach>
						 </select> 
		               </td>
		               </c:if>  --%>
		                <%-- <c:if test="${param.fromWh==null||param.fromWh!='order_view_List' }">
		              <th>处理状态：</th>
		                <td>
		                	<select name="params.status" class="ipt_new" style="width:138px;">
		                		<c:forEach items="${deal_status }" var="ds">
		                			<option value="${ds.pkey }" ${ds.pkey==params.status?'selected':'' }>${ds.pname }</option>
		                		</c:forEach>
							</select>
		                </td>
		               </c:if>  --%>
	              <th>是否当月办理：</th>
	              <td>
                	<html:selectdict  name="params.bss_time_type"  appen_options="<option value=''>---请选择---</option>"  curr_val="${params.bss_time_type}"   attr_code="DC_BSS_TIME_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:190px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
	              </td>
			     <th>条形码：</th>
              	<td><input type="text" class="ipt_new" style="width:138px;" name="params.order_sn" value="${params.order_sn }" onkeydown= "if(event.keyCode==13){query_order_s.click();}"/>
              		<input type="hidden" name="query_list_by_bt" value="Y" /><!-- 单点登录用到 -->
							 <a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							<!--  <input class="comBtn" type="button" name="excelReport" id="excelReport" value="导出数据" style="margin-right:10px;"/> -->
							<c:if test="${query_type=='order_view' }">
									<a href="javascript:void(0);" id="excelReport" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;导&nbsp;&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:if>
							 
							 <c:if test="${query_type=='order_recover' }">
							 <a href="javascript:void(0);" id="orders_recovery" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;恢&nbsp;&nbsp;&nbsp;&nbsp;复&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							 </c:if>
              	</td>
	            </tr>
	            </tbody>
                <tbody id="param_tb_F">
                
	            <tr>
		            <th>地市：</th>
	                <td>
	                <script type="text/javascript">
	                	$(function(){
	
												////XMJ修改开始
	                		$("#region_ivp,#region_a,#region_div").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
	                	        $("#region_div").show();    //显示DIV
	                	        e.stopPropagation();//阻止事件冒泡
	                		})
	            	    		
							$(document).bind("click",function(){    
	                	        $("#region_div").hide();    //隐藏DIV
	                	  	}) 
	
						   $("#regionCancel,#regionCancel2").bind("click",function(e){
	                		   $("#region_div").hide();
	                           e.stopPropagation();//阻止事件冒泡
	                		}); 
	            		////XMJ修改结束                		
	                		$("#regioncheckAll").bind("click",function(){
	                			if(this.checked){
	                				$("input[name=region_id]").attr("checked","checked");
	                				$("#region_div li").addClass("curr");
	                			}else{
	                				$("input[name=region_id]").removeAttr("checked");
	                				$("#region_div li").removeClass("curr");
	                			}
	                			selectRegions();
	                		});
	                		
	                		
	                		$("input[name=region_id]").bind("click",function(){
	                			if(this.checked){
	                				$(this).parents("li").addClass("curr");
	                			}else{
	                				$(this).parents("li").removeClass("curr");
	                			}
	                			selectRegions();
	                		});
	                		
	                		initCheckBox("region_hivp","region_id");
	                		
	                	});
	                	
	                	function selectRegions(){
	            			var regions = $("input[name=region_id]:checked");
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
	                </script>
	                
	                	<span class="selBox" style="width:120px;">
	                    	<input type="text" name="params.order_city_code_c" id="region_ivp" value="${params.order_city_code_c }" class="ipt" readonly="readonly">
	                    	<input type="hidden" name="params.order_city_code" value="${params.order_city_code }" id="region_hivp" />
	                    	<a id="region_a" href="javascript:void(0);" class="selArr"></a>
	                    	
	                        <div id="region_div" class="selOp" style="display:none;">
	                        	<div class="allSel">
	                            	<label><input type="checkbox" id="regioncheckAll">全选</label>
	                                <label><a href="javascript:void(0);" class="aCancel" id="regionCancel">关闭</a></label>
	                                <label><a href="javascript:void(0);" class="aClear" id="regionCancel2"></a></label>
	                            </div>
	                            
	                            <div class="listItem">
	                            	<ul>
		                            	<c:forEach items="${regionList }" var="rg">
		                            		<li><input type="checkbox" name="region_id" value="${rg.region_id }" c_name="${rg.local_name }"><span name="region_li">${rg.local_name }</span></li>
		                            	</c:forEach>
	                                </ul>
	                            </div>
	                        </div>
	                    </span>
	                </td>
	                
	                <th>县分：</th>
	                <td>
	                	<script type="text/javascript">
		                	$(function(){
		
		                		$("#county_ivp,#county_a,#county_div").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
		                	        $("#county_div").show();    //显示DIV
		                	        e.stopPropagation();//阻止事件冒泡
		                		})
		            	    		
								$(document).bind("click",function(){    
		                	        $("#county_div").hide();    //隐藏DIV
		                	  	}) 
		
							    $("#countyCancel,#countyCancel2").bind("click",function(e){
									$("#county_div").hide();
									e.stopPropagation();//阻止事件冒泡
		                		}); 
		            					
		                		$("#countycheckAll").bind("click",function(){
		                			if(this.checked){
		                				$("input[name=county_id]").attr("checked","checked");
		                				$("#county_div li").addClass("curr");
		                			}else{
		                				$("input[name=county_id]").removeAttr("checked");
		                				$("#county_div li").removeClass("curr");
		                			}
		                			
		                			selectCountys();
		                		});
		                		
		                		
		                		$("input[name=county_id]").bind("click",function(){
		                			if(this.checked){
		                				$(this).parents("li").addClass("curr");
		                			}else{
		                				$(this).parents("li").removeClass("curr");
		                			}
		                			
		                			selectCountys();
		                		});
		                		
		                		initCheckBox("county_hivp","county_id");
		                		
		                	});
		                	
		                	function selectCountys(){
		            			var items = $("input[name=county_id]:checked");
		            			var names = "";
		            			var ids = "";
		            			
		            			items.each(function(idx,item){
		            				var name = $(item).attr("c_name");
		            				var id = $(item).attr("value");
		            				
		            				names += name+",";
		            				ids += id+",";
		            			});
		            			
		            			if(ids.length > 1){
		            				ids = ids.substr(0,ids.length-1);
		            				names = names.substr(0,names.length-1);
		            			}
		            			
		            			$("#county_ivp").val(names);
		            			$("#county_hivp").val(ids);
		            		}
		                </script>
	                
	                	<span class="selBox" style="width:120px;">
	                    	<input type="text" name="params.order_county_code_c" id="county_ivp" value="${params.order_county_code_c }" class="ipt" readonly="readonly">
	                    	<input type="hidden" name="params.order_county_code" value="${params.order_county_code }" id="county_hivp" />
	                    	<a id="county_a" href="javascript:void(0);" class="selArr"></a>
	                    	
	                        <div id="county_div" class="selOp" style="display:none;">
	                        	<div class="allSel">
	                            	<label><input type="checkbox" id="countycheckAll">全选</label>
	                                <label><a href="javascript:void(0);" class="aCancel" id="countyCancel">关闭</a></label>
	                                <label><a href="javascript:void(0);" class="aClear" id="countyCancel2"></a></label>
	                            </div>
	                            
	                            <div class="listItem">
	                            	<ul>
		                            	<c:forEach items="${countyList }" var="rg">
		                            		<li><input type="checkbox" name="county_id" value="${rg.county_id }" region_id="${rg.region_id}" c_name="${rg.county_name }"><span name="region_li">${rg.county_name }</span></li>
		                            	</c:forEach>
	                                </ul>
	                            </div>
	                        </div>
	                    </span>
	                </td>
	            </tr>
	            
	            <tr>
                <th>配送方式：</th>
                <td>
                	<script type="text/javascript">
                	$(function(){


										////XMJ修改开始
                		$("#shipping_type_ivp,#shipping_type_a,#shipping_type_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#shipping_type_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                	        
                	        
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#shipping_type_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#shipping_type_cancel1,#shipping_type_cancel2").bind("click",function(e){
                		   $("#shipping_type_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#shipping_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=shipping_type]").attr("checked","checked");
                				$("#shipping_type_dv li").addClass("curr");
                			}else{
                				$("input[name=shipping_type]").removeAttr("checked");
                				$("#shipping_type_dv li").removeClass("curr");
                			}
                			selectShippintType();
                		});
                		
                		
                		$("input[name=shipping_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectShippintType();
                		});
                		
                		initCheckBox("shipping_type_hivp","shipping_type");
                		
                	});
                	
                	function selectShippintType(){
            			var regions = $("input[name=shipping_type]:checked");
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
            			$("#shipping_type_ivp").val(regionNames);
            			$("#shipping_type_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.shipping_id_c" id="shipping_type_ivp" value="${params.shipping_id_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.shipping_id" value="${params.shipping_id }" id="shipping_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="shipping_type_a"></a>
                        <div class="selOp" style="display:none;" id="shipping_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id=shipping_type_checkall>全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="shipping_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="shipping_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${shipping_type_list }" var="st">
                            			<li name="shipping_type_li"><input type="checkbox" name="shipping_type" value="${st.pkey }" c_name="${st.pname }"><span name="shipping_type_span">${st.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
	                 <th>商品名称：</th>
	             <td>
                	<input type="text" class="ipt_new" style="width:138px;" name="params.goods_name" value="${params.goods_name }"/>
	              </td>  
	            </tr>
	            <tr>
	             <th>商品包类型：</th>
	              <td>
	              <html:selectdict  name="params.goods_pagekage_type"  appen_options="<option value=''>---请选择---</option>"  curr_val="${params.goods_pagekage_type}"   attr_code="DC_MODE_GOODS_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:138px;"></html:selectdict>
                
               <!-- 
                	<select name="select" id="select" class="ipt_new" style="width:138px;">
                    	<option>全部</option>
					</select>
				 -->
                </td>
                <th>型号：</th>
                <td><input type="text" name="params.model_code" value="${params.model_code }" class="ipt_new" style="width:138px;"></td>
              	<th>待业务办理：</th>
              	<td>
	                  <select name="params.exists_business_to_deal_with" class="ipt_new" style="width:138px;">
	                      <option value="">---请选择---</option>
	                      <option value="0">已办理</option>
	                      <option value="1">未办理</option>
		               </select>
		               <script type="text/javascript">
		                  $("[name='params.exists_business_to_deal_with'] option[value='${params.exists_business_to_deal_with}']").attr("selected","selected");
						</script>
	            </td>
              </tr>
              <tr>
               <th>异常单类型：</th>
                <td>
                <input type="hidden" name="query_type" value="${query_type }" />
                	<select name="params.exception_type"  class="ipt_new" style="width:138px;">
                		<c:forEach items="${orderExceptionTypeList }" var="qs"> 
                			<option value="${qs.pkey }" ${qs.pkey==params.exception_type?'selected':'' }>${qs.pname }</option>
                		</c:forEach>
					</select>
				<script type="text/javascript">
					var _exception_type = '${params.exception_type}';
					if(_exception_type ==null || _exception_type == ''){
						var exception_type = '${exception_type}';
						if(exception_type=='all'){
							$("select[name='params.exception_type']").val("-1");
						}
						else{
							$("select[name='params.exception_type']").val(exception_type);
						}
					}
				</script>
                </td>
                
                <%-- <c:if test="${query_type=='order' }">
	                <th>是否异常订单：</th>
	                <td>
	                	<select name="params.refund_deal_type"  class="ipt_new" style="width:138px;">
	                    	<option value="-1" ${params.refund_deal_type=='-1'?'selected':'' }>--请选择--</option>
	                    	<option value="01" ${params.refund_deal_type=='01'?'selected':'' } >正常单</option>
	                    	<option value="02" ${params.refund_deal_type=='02'?'selected':'' }>异常单</option>
						</select>
	                </td>
                </c:if> --%>
                <th>物流单号：</th>
                <td>
                	<input type="text" name="params.logi_no" value="${params.logi_no }" class="ipt_new" style="width:138px;">
                </td>
                
                <th>是否可见：</th>
                <td>
                	<select name="params.visible_status"  class="ipt_new" style="width:150px;">
                    	<option value="-1" ${params.visible_status=='-1'?'selected':'' }>--请选择--</option>
                    	<option value="0" ${params.visible_status=='0'?'selected':'' } >可见</option>
                    	<option value="1" ${params.visible_status=='1'?'selected':'' }>不可见</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- <a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>-->
                </td>
              </tr>
              <tr>
              	<th>订单来源：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){

												////XMJ修改开始
                    		$("#order_from_vp,#order_from_a,#order_from_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
                    	        $("#order_from_dv").show();    //显示DIV
                    	        e1.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(e1){    
                    	        $("#order_from_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#order_from_cancel1,#order_from_cancel2").bind("click",function(e1){
                    		   $("#order_from_dv").hide();
                               e1.stopPropagation();//阻止事件冒泡
                    		}); 
                    	   ////XMJ修改结束                			
                			$("#order_from_chack_all").bind("click",function(){
                    			if(this.checked){
                    				$("input[name=order_from]").attr("checked","checked");
                    				$("#order_from_dv li").addClass("curr");
                    			}else{
                    				$("input[name=order_from]").removeAttr("checked");
                    				$("#order_from_dv li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		
                    		
                    		$("input[name=order_from]").bind("click",function(){
                    			if(this.checked){
                    				$(this).parents("li").addClass("curr");
                    			}else{
                    				$(this).parents("li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		
                    		initCheckBox("order_from_ivp","order_from");
                    	});
                    	
                    	function selectOrderFroms(){
                			var regions = $("input[name=order_from]:checked");
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
                			$("#order_from_vp").val(regionNames);
                			$("#order_from_ivp").val(regionIds);
                		}
                    	
                    	//初始化多选框
                    	function initCheckBox(value_id,check_box_name){
                    		var cv = $("#"+value_id).val();
                    		if(cv){
                    			var arr = cv.split(",");
                    			for(i=0;i<arr.length;i++){
                    				var item = arr[i];
                        			var obj = $("input[type=checkbox][name="+check_box_name+"][value="+item+"]");
                        			obj.attr("checked","checked");
                        			obj.parents("li").addClass("curr");
                    			}
                    		}
                    	}
                	</script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_from_c" id="order_from_vp" value="${params.order_from_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_from" value="${params.order_from }" id="order_from_ivp" />
                    	<a href="javascript:void(0);" id="order_from_a" class="selArr"></a>
                        <div class="selOp" style="display:none;" id="order_from_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_from_chack_all">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_from_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_from_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${order_from_list }" var="of">
                            			<li name="order_from_li"><input type="checkbox" name="order_from" value="${of.value }" c_name="${of.value_desc }"><span name="order_from_span">${of.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                <th>订单类型：</th>
              	
                <td>
                	   	 <script type="text/javascript">
                	$(function(){
                		$("#order_type_ivp,#order_type_a,#order_type_dv").bind("click",function(e){
                    		$("#order_type_dv").show();
                	        e.stopPropagation();//阻止事件冒泡
                    	});
						$(document).bind("click",function(){    
                	        $("#order_type_dv").hide();    //隐藏DIV
                	  	});
                		$("#order_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=order_type]").attr("checked","checked");
                				$("#order_type_dv li").addClass("curr");
                			}else{
                				$("input[name=order_type]").removeAttr("checked");
                				$("#order_type_dv li").removeClass("curr");
                			}
                			selectOrderType();
                		});
                		$("#order_type_cancel1,#order_type_cancel2").bind("click",function(e){
                			$("#order_type_dv").hide();
                	        e.stopPropagation();//阻止事件冒泡
                		});
                		
                		$("input[name=order_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectOrderType();
                		});
                		
                		initCheckBox("order_type_hivp","order_type");
                		
                	});
                	
                	function selectOrderType(){
            			var regions = $("input[name=order_type]:checked");
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
            			$("#order_type_ivp").val(regionNames);
            			$("#order_type_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_type_c" id="order_type_ivp" value="${params.order_type_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.order_type" value="${params.order_type }" id="order_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="order_type_a"></a>
                        <div class="selOp" style="display:none;" id="order_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_type_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${order_type_list }" var="pt">
                            			<li name="order_type_li"><input type="checkbox" name="order_type" value="${pt.pkey }" c_name="${pt.pname }"><span name="order_type_span">${pt.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
					
                </td>
                <!-- <th>料箱号：</th>
                <td>
                	<input type="text" class="ipt_new" style="width:138px;" name="boxCode" id="boxCodeId"/>
                </td> -->
                <th>生产模式：</th>
	             <td>
	                	<select name="params.order_model" class="ipt_new" style="width:150px;">
	                		<c:forEach items="${order_model_list }" var="ds">
	                			<option value="${ds.pkey }" ${ds.pkey==params.order_model?'selected':'' }>${ds.pname }</option>
	                		</c:forEach>
						</select>
	              </td>
              </tr>
              <%--<tr>
	              <%-- <th>客户类型：</th>
	              <td>
                	<html:selectdict  name="params.cust_type"  appen_options="<option value=''>---请选择---</option>"  curr_val="${params.cust_type}"   attr_code="DC_CUSTOMER_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
	              </td> --%>
	          <%--<c:if test="${!(query_type=='order') }">
              <th>集团编码：</th>
              <td><input type="text" class="ipt_new" style="width:138px;" name="params.group_code" value="${params.group_code }"/></td>
              </c:if>
              </tr> --%>
              <c:if test="${!(query_type=='order') }">
              <tr>
              <th>集团名称：</th>
              <td><input type="text" class="ipt_new" style="width:138px;" name="params.group_name" value="${params.group_name }"/></td>
                
                <!-- 
                <th>收货人电话：</th>
	             <td>
                	<input type="text" class="ipt_new" style="width:138px;" name="params.ship_tel"/>
	              </td>
	              -->
                <th>订单发展归属：</th>
                <td>
                <script type="text/javascript">
                	$(function(){
                		////XMJ修改开始
                		$("#channel_mark_ivp,#channel_mark_a,#channel_mark_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#channel_mark_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#channel_mark_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#channel_mark_cancel1,#channel_mark_cancel2").bind("click",function(e){
                		   $("#channel_mark_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束
                		$("#channel_mark_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=channel_mark]").attr("checked","checked");
                				$("#channel_mark_dv li").addClass("curr");
                			}else{
                				$("input[name=channel_mark]").removeAttr("checked");
                				$("#channel_mark_dv li").removeClass("curr");
                			}
                			selectChannelMark();
                		});
                		
                		
                		$("input[name=channel_mark]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectChannelMark();
                		});
                		
                		initCheckBox("channel_mark_hivp","channel_mark");
                		
                	});
                	
                	function selectChannelMark(){
            			var regions = $("input[name=channel_mark]:checked");
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
            			$("#channel_mark_ivp").val(regionNames);
            			$("#channel_mark_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:130px;">
                    	<input type="text" name="params.order_channel_c" id="channel_mark_ivp" value="${params.order_channel_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" value="${params.order_channel }" name="params.order_channel" id="channel_mark_hivp" />
                    	<a href="javascript:void(0);" id="channel_mark_a" class="selArr"></a>
                        <div class="selOp" style="display:none;" id="channel_mark_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="channel_mark_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="channel_mark_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="channel_mark_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${channel_mark_list }" var="cm">
                            			<li name="channel_mark_li"><input type="checkbox" name="channel_mark" value="${cm.pkey }" c_name="${cm.pname }"><span name="channel_mark_span">${cm.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
              </tr>
              <tr>
              <th>是否预约单：</th>
	             <td>
                	<select name="params.wm_isreservation_from"  class="ipt_new" style="width:138px;">
                    	<option value="0" ${params.wm_isreservation_from=='0'?'selected':'' }>否</option>
                    	<option value="1" ${params.wm_isreservation_from=='1'?'selected':'' }>是</option>
                    	<option value="-1" ${params.wm_isreservation_from=='-1'?'selected':'' }>--全部--</option>
					</select>
	              </td>
	              <c:if test="${query_type=='logistics_print' }">
		          	 <th>ICCID：</th>
              		 <td>
              		 	<input type="text" class="ipt_new" style="width:138px;" name="params.iccid" value="${params.iccid }"/>
              		 </td>
		          </c:if>
              <c:if test="${(param.fromWh!=null&&param.fromWh=='order_view_List')||query_type=='exception' }">
               
               		<c:if test="${query_type=='exception' }">
		                <th>异常类型：</th>
		                <td>
                	<script type="text/javascript">
                		$(function(){

												////XMJ修改开始
                    		$("#exception_code_vp,#exception_code_a,#exception_code_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
                    	        $("#exception_code_dv").show();    //显示DIV
                    	        e1.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(e1){    
                    	        $("#exception_code_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#exception_code_cancel1,#exception_code_cancel2").bind("click",function(e1){
                    		   $("#exception_code_dv").hide();
                               e1.stopPropagation();//阻止事件冒泡
                    		}); 
                    	   ////XMJ修改结束                			
                			$("#exception_code_chack_all").bind("click",function(){
                    			if(this.checked){
                    				$("input[name=exception_code]").attr("checked","checked");
                    				$("#exception_code_dv li").addClass("curr");
                    			}else{
                    				$("input[name=exception_code]").removeAttr("checked");
                    				$("#exception_code_dv li").removeClass("curr");
                    			}
                    			selectExceptionCodes();
                    		});
                    		
                    		
                    		$("input[name=exception_code]").bind("click",function(){
                    			if(this.checked){
                    				$(this).parents("li").addClass("curr");
                    			}else{
                    				$(this).parents("li").removeClass("curr");
                    			}
                    			selectExceptionCodes();
                    		});
                    		
                    		initCheckBox("exception_code_ivp","exception_code");
                    	});
                    	
                    	function selectExceptionCodes(){
                			var regions = $("input[name=exception_code]:checked");
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
                			$("#exception_code_vp").val(regionNames);
                			$("#exception_code_ivp").val(regionIds);
                		}
                	</script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.exception_code_c" id="exception_code_vp" value="${params.exception_code_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.exception_code" value="${params.exception_code }" id="exception_code_ivp" />
                    	<a href="javascript:void(0);" id="exception_code_a" class="selArr"></a>
                        <div class="selOp" style="display:none;" id="exception_code_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="exception_code_chack_all">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="exception_code_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="exception_code_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${exceptionTypeList }" var="qs">
                            			<li name="exception_code_li"><input type="checkbox" name="exception_code" value="${qs.pkey }" c_name="${qs.pname }"><span name="exception_code_span">${qs.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
		                </td>
	                </c:if>
	                <c:if test="${param.fromWh!=null&&param.fromWh=='order_view_List'}">
	                 <th>处理状态：</th>
		                <td>
		                	<select name="params.status" class="ipt_new" style="width:138px;">
		                		<c:forEach items="${deal_status }" var="ds">
		                			<option value="${ds.pkey }" ${ds.pkey==params.status?'selected':'' }>${ds.pname }</option>
		                		</c:forEach>
							</select>
		                </td>
		           </c:if>
	              <c:if test="${!(query_type=='order') }">
		              <th>集团编码：</th>
		              <td><input type="text" class="ipt_new" style="width:138px;" name="params.group_code" value="${params.group_code }"/></td>
	              </c:if>
               </c:if>
              	
              </tr>
              </c:if>
              <tr>
              	<th>是否老用户：</th>
              	<td><html:selectdict  name="params.is_old"  appen_options="<option value=''>---请选择---</option>"  curr_val="${params.is_old}"   attr_code="DC_IS_OR_NO" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:138px;"></html:selectdict></td>
			    <c:if test="${param.fromWh!=null&&param.fromWh=='order_view_List' }">
		            <th>历史订单：</th>
		            <td>
	               	 	<select name="params.order_is_his" class="ipt_new" style="width:138px;">
	               	    	<c:forEach items="${is_order_his_list }" var="ds">
	               				<option value="${ds.pkey }" ${ds.pkey==params.order_is_his?'selected':'' }>${ds.pname }</option>
	               			</c:forEach>
					 	</select> 
		            </td>
		          </c:if>  
		          <c:if test="${query_type=='order' || query_type=='order_view'}">
		          	 <th>ICCID：</th>
              		 <td>
              		 	<input type="text" class="ipt_new" style="width:138px;" name="params.iccid" value="${params.iccid }" onkeydown= "if(event.keyCode==13){query_order_s.click();}"/>
              		 </td>
		          </c:if>
		          <c:if test="${query_type=='order_view' }">
		          <th>锁定人帐号：</th>
					<td>
					<input type="text" class="ipt_new" style="width:138px;" id="username" name="username" value="${username }" onfocus="queryUserId()"/>
					<input type="hidden" class="ipt_new" style="width:138px;" id="lock_user_id" name="params.lock_user_id" value="${params.lock_user_id }" />
					<a href="javascript:void(0);" id="resetBtn" class="dobtn" style="margin-right:10px;">清除</a>
					</td>
					<tr> <th>订单状态：</th>
                <td>
                <script type="text/javascript">
                		$(function(){

												////XMJ修改开始
                    		$("#order_qry_status_vp,#order_qry_status_a,#order_qry_status_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
                    	        $("#order_qry_status_dv").show();    //显示DIV
                    	        e1.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(e1){    
                    	        $("#order_qry_status_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#order_qry_status_cancel1,#order_qry_status_cancel2").bind("click",function(e1){
                    		   $("#order_qry_status_dv").hide();
                               e1.stopPropagation();//阻止事件冒泡
                    		}); 
                    	   ////XMJ修改结束                			
                			$("#order_qry_status_chack_all").bind("click",function(){
                    			if(this.checked){
                    				$("input[name=order_qry_status]").attr("checked","checked");
                    				$("#order_qry_status_dv li").addClass("curr");
                    			}else{
                    				$("input[name=order_qry_status]").removeAttr("checked");
                    				$("#order_qry_status_dv li").removeClass("curr");
                    			}
                    			selectOrderQryStatus();
                    		});
                    		
                    		
                    		$("input[name=order_qry_status]").bind("click",function(){
                    			if(this.checked){
                    				$(this).parents("li").addClass("curr");
                    			}else{
                    				$(this).parents("li").removeClass("curr");
                    			}
                    			selectOrderQryStatus();
                    		});
                    		
                    		initCheckBox("order_qry_status_ivp","order_qry_status");
                    	});
                    	
                    	function selectOrderQryStatus(){
                			var regions = $("input[name=order_qry_status]:checked");
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
                			$("#order_qry_status_vp").val(regionNames);
                			$("#order_qry_status_ivp").val(regionIds);
                		}
                	</script>
                	<span class="selBox" style="width:100px;">
                    	<input type="text" name="params.order_qry_status_c" id="order_qry_status_vp" value="${params.order_qry_status_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_qry_status" value="${params.order_qry_status }" id="order_qry_status_ivp" />
                    	<a href="javascript:void(0);" id="order_qry_status_a" class="selArr"></a>
                        <div class="selOp" style="display:none;" id="order_qry_status_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_qry_status_chack_all">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_qry_status_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_qry_status_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${OrderQryStatusList }" var="oqs">
                            			<li name="order_qry_status_li"><input type="checkbox" name="order_qry_status" value="${oqs.pkey }" c_name="${oqs.pname }"><span name="order_qry_status_span">${oqs.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                	
					&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- <a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>-->
                </td></tr>
		          </c:if>
		         
              </tr>
              <tr>
              		<th>证件类型：</th>
              		<td>
              			<select name="params.certi_type" disable = 'disable' class="ipt_new" style="width:138px;">
                    		<option value="SFZ18" ${params.certi_type=='SFZ18'?'selected':'' }>身份证</option>
                    	</select>
              		</td>
              		<th>证件号码：</th>
              		<td>
              			<input type="text" class="ipt_new" style="width:138px;" name="params.cert_card_num" value="${params.cert_card_num}" onkeydown= "if(event.keyCode==13){query_order_s.click();}"/>
              		</td>
              		<th>联系号码：</th>
              		<td>
              			<input type="text" class="ipt_new" style="width:138px;" name="params.ship_tel" value="${params.ship_tel}" "/>
              		</td>
              </tr>
               <tr>
		         <th>证件上传状态：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){
												////XMJ修改开始
                    		$("#if_Send_Photos_vp,#if_Send_Photos_a,#if_Send_Photos_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
                    	        $("#if_Send_Photos_dv").show();    //显示DIV
                    	        e1.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(e1){    
                    	        $("#if_Send_Photos_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#if_Send_Photos1,#if_Send_Photos2").bind("click",function(e1){
                    		   $("#if_Send_Photos_dv").hide();
                               e1.stopPropagation();//阻止事件冒泡
                    		}); 
                    	   ////XMJ修改结束                			
                			$("#if_Send_Photos_all").bind("click",function(){
                    			if(this.checked){
                    				$("input[name=if_Send_Photos]").attr("checked","checked");
                    				$("#if_Send_Photos_dv li").addClass("curr");
                    			}else{
                    				$("input[name=if_Send_Photos]").removeAttr("checked");
                    				$("#if_Send_Photos_dv li").removeClass("curr");
                    			}
                    			select_if_Send_Photos();
                    		});
                    		
                    		
                    		$("input[name=if_Send_Photos]").bind("click",function(){
                    			if(this.checked){
                    				$(this).parents("li").addClass("curr");
                    			}else{
                    				$(this).parents("li").removeClass("curr");
                    			}
                    			select_if_Send_Photos();
                    		});
                    		
                    		initCheckBox("if_Send_Photos_ivp","if_Send_Photos");
                    	});
                    	
                    	function select_if_Send_Photos(){
                			var regions = $("input[name=if_Send_Photos]:checked");
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
                			$("#if_Send_Photos_vp").val(regionNames);
                			$("#if_Send_Photos_ivp").val(regionIds);
                		}
                    	
                    	//初始化多选框
                    	function initCheckBox(value_id,check_box_name){
                    		var cv = $("#"+value_id).val();
                    		if(cv){
                    			var arr = cv.split(",");
                    			for(i=0;i<arr.length;i++){
                    				var item = arr[i];
                        			var obj = $("input[type=checkbox][name="+check_box_name+"][value="+item+"]");
                        			obj.attr("checked","checked");
                        			obj.parents("li").addClass("curr");
                    			}
                    		}
                    	}
                	</script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.if_Send_Photos_Name" id="if_Send_Photos_vp" value="${params.if_Send_Photos_Name }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.if_Send_Photos" value="${params.if_Send_Photos }" id="if_Send_Photos_ivp" />
                    	<a href="javascript:void(0);" id="if_Send_Photos_a" class="selArr"></a>
                        <div class="selOp" style="display:none;" id="if_Send_Photos_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="if_Send_Photos_all">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="if_Send_Photos1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="if_Send_Photos2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${role_group_list}" var="ro">
                            			<li name="order_from_li"><input type="checkbox" name="if_Send_Photos" value="${ro.pkey}" c_name="${ro.pname}"><span name="if_Send_Photos_Name">${ro.pname}</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                
                <!-- add by  cqq 20181128 -->
	           	<th>是否自定义流程：</th>
	           	<td>
	              	<select name="params.is_work_custom" id="workCustomSel"  class="ipt_new" style="width:138px;" >
	              		<option value="" >--全部--</option>
	                  	<option value="0" >否</option>
	                  	<option value="1" >是</option>
					</select>
					<script type="text/javascript">
		                 $("[name='params.is_work_custom'] option[value='${params.is_work_custom}']").attr("selected","selected");
		               //add by cqq 20181129
		               /* var is_work_custom = $("#workCustomSel").val(); */
		               var workCustom = $("#workCustomSel").val();	
					if(workCustom == 1){
						$("#traceTh").attr("style","display:none");
						$("#traceTd").attr("style","display:none");
						$("#traceTh_new").attr("style","");
						$("#traceTd_new").attr("style","");
					}else{
						$("#traceTh").attr("style","");
						$("#traceTd").attr("style","");
						$("#traceTh_new").attr("style","display:none");
						$("#traceTd_new").attr("style","display:none");
					}
					</script>
	           	</td>
	           	<th>顶级种子专业线：</th>
	             <td>
	                	<select name="params.top_seed_professional_line" class="ipt_new" style="width:150px;">
	                		<c:forEach items="${top_seed_professional_line_list }" var="ds">
	                			<option value="${ds.value }" ${ds.value==params.top_seed_professional_line?'selected':'' }>${ds.value_desc }</option>
	                		</c:forEach>
						</select>
	              </td>
              </tr>
              <tr>
              <th>顶级种子类型：</th>
	             <td>
	                	<select name="params.top_seed_type" class="ipt_new" style="width:150px;">
	                		<c:forEach items="${top_seed_type_list }" var="ds">
	                			<option value="${ds.value }" ${ds.value==params.top_seed_type?'selected':'' }>${ds.value_desc }</option>
	                		</c:forEach>
						</select>
	              </td>
	          <th>顶级种子分组：</th>
	             <td>
	                	<select name="params.top_seed_group_id" class="ipt_new" style="width:150px;">
	                		<c:forEach items="${top_seed_group_list }" var="ds">
	                			<option value="${ds.value }" ${ds.value==params.top_seed_group_id?'selected':'' }>${ds.value_desc }</option>
	                		</c:forEach>
						</select>
	              </td>
	           <th>种子用户号码：</th>
	             <td>
	                	<input type="text" name="params.share_svc_num" value="${params.share_svc_num }" style="width:138px;" class="ipt_new">
	              </td>     
              </tr>
             </tbody>
            </table>
        </div>

<script type="text/javascript">
	$(function(){
		$("#param_tb_F").hide();
		$("#hide_params_tb").hide();
		$("#show_params_tb").show();
		var count = -2;
		$("#hide_params_tb").bind("click",function(){
			if(count==1){
				$("#param_tb_F").hide();
				count = 2;
			}else{
				$("#tbody_A").hide();
				count = -1; 
				$("#hide_params_tb").hide();
				$("#show_params_tb").show();
			}
			
		});
		$("#show_params_tb").bind("click",function(){
			if(count==-1){
				$("#tbody_A").show();
				count =-2;
			}else{
				$("#param_tb_F").show();
				count = 1;
				$("#hide_params_tb").show();
				$("#show_params_tb").hide();
			}
			
		});
		$("#excelReport").bind("click",function(){
			$.Loading.show("正在加载所需内容，请稍侯...");
			var url = ctx+"/shop/admin/ordAuto!exportOrderReport.do?ajax=yes&excel=check&type=dtl";
			Cmp.ajaxSubmit('aito_order_f','',url,{},function(data){
				if(data.result=='0'){
					alert("数据过多");
					$.Loading.hide();
				}else{
					$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!exportOrderReport.do?ajax=yes&excel=yes&type=dtl");
					$("#aito_order_f").submit();
					//$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do").submit();
					//$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!orderReportQuery.do");
				}
			},'json');
		});
		$("#query_order_s").bind("click",function(){
			$.Loading.show("正在加载所需内容，请稍侯...");
			//只有订单处理的才做特殊处理
			var query_type = $("input[name='query_type']").val();
			if(query_type == "order"){
				//获取订单条形码
				var order_sn = $.trim($("input[name='params.order_sn']").val());
				var iccid = $.trim($("input[name='params.iccid']").val());
				if(order_sn != "" || iccid != ""){
					$.ajax({
						type : "post",
						async : true,
						url : "ordAuto!getOrderInfoBySn.do?ajax=yes&params.order_sn="+order_sn+"&params.iccid="+iccid,
						data : {},
						dataType : "json",
						success : function(data) {
							 $.Loading.hide();
							 if(data.result == "1"){//查询失败
								 alert(data.message);
							 }else{
								 if(data.result == "0" && data.flow_trace_id == "F"){//只有查询到订单，并且订单当前环节为质检稽核环节的才弹出对话框
									 var order_id = data.order_id;
									 //获取质检稽核订单详情页面地址
									 var resultUrl =  AutoFlow.getActionUrl(order_id);
									 var resultObj = resultUrl.split("|");
									 var o_url = "";
									if(resultObj[0]==0){
										o_url = resultObj[1];	
									}else{
										alert(resultObj[1]);
										return false;
									}
									if(o_url && o_url.indexOf("?")!=-1){
										o_url += "&order_id="+order_id+"&query_type="+query_type;
									}else{
										o_url += "?order_id="+order_id+"&query_type="+query_type;
									}
									var url = ctx+"/"+o_url;
									//锁单
									$.ajax({
										type : "post",
										async : false,
										url : "ordAuto!order_lock.do?ajax=yes&order_id="+order_id,
										data : {},
										dataType : "json",
										success : function(data) {
											if (data.result == "0") {
												//弹框
												<%--$("#orderDetailDialog").html("loading...");
												$("#orderDetailDialog").load(url,{},function(){
													  CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:order_id,order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
													  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:order_id,order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
													  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:order_id,ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
													  //按钮显示
													  OrdBtns.showBtns(order_id,"","","orderDetailDialog",null,function(){
															if("exception"==query_type){
																$("a[name=offlineOpenAccount]").show();
															}
															$("#btnlists").find("div.comBtns").css("width","90%");
															//绑定按钮事件
															Eop.Dialog.init({id:"order_detail_btn_event_dialog",modal:true,title:"订单处理", height:"600px",width:"800px"});
															$("a[orderbtns=btn]").bind("click",btnDialogEvent);
													  });
												});
												Eop.Dialog.open("orderDetailDialog");--%>
												window.open(url,"newwindow","height=500,width=1100, top=115, left=210, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
											} else {
												alert("查询失败！");
											}
										}
									});	
									
								 }else{//否则提交表单查询
									 $("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do").submit();
								 }
							 }
						},
						error : function(){
							$.Loading.hide();
						}
					});
					
				}else{
					$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do").submit();
				}
			}else{	
				$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do").submit();
			}
		});
		//$("#order_from_dv").aramsDiv();
		$("#boxCodeId").bind("blur", function() {
			var boxCode = $("#boxCodeId").val();
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!getOrdByBoxIdFromWMS.do?ajax=yes&boxCode="+boxCode,
				data : {},
				dataType : "json",
				success : function(data) {
					if (data!= undefined && data.result=="0") {
						$("input[name='params.order_id']").val(data.message);
						$("#query_order_s").click();
					}
				}
			});
		});
		$("#resetBtn").click(function (){
			document.getElementById("username").value = "";
			document.getElementById("lock_user_id").value = "";
		});
	});
	
	//add by cqq 20181128
	$("#workCustomSel").bind("click",function(){
		var workCustom = $("#workCustomSel").val();	
		if(workCustom == 1){
			$("#traceTh").attr("style","display:none");
			$("#traceTd").attr("style","display:none");
			$("#traceTh_new").attr("style","");
			$("#traceTd_new").attr("style","");
		}else{
			$("#traceTh").attr("style","");
			$("#traceTd").attr("style","");
			$("#traceTh_new").attr("style","display:none");
			$("#traceTd_new").attr("style","display:none");
		}
		
	});
	
function orderListReQuery(){
	$("#query_order_s").click();
}
</script>        