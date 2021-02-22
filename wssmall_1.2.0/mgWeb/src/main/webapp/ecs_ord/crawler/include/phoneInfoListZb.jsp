<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript">
function disableBtn(dis){	
	$("#savePhoneBtn").attr("disabled",dis);
	$("#savePhoneBtn1").attr("disabled",dis);
	$("#queryBtn").attr("disabled",dis);
}

function changeSpan(){//控制总部SPAN显示
	
	var val=$("#queryPara_10").val();
	
	  if("01"==val){
		$("#span2").hide();
		$("#span3").hide();
		$("#span4").hide();
		$("#span5").hide();
		$("#span6").hide();
		$("#queryTypeZbChooseReturnVal").val("01");  
	} else if("02"==val){
		$("#span2").show();
		$("#span3").hide();
		$("#span4").hide();
		$("#span5").hide();
		$("#span6").hide();
		$("#queryTypeZbChooseReturnVal").val("02");
	}else if ("03"==val){
		$("#span2").hide();
		$("#span3").show();
		$("#span4").hide();
		$("#span5").hide();
		$("#span6").hide();
		$("#queryTypeZbChooseReturnVal").val("03");
	}else  if ("04"==val){
		$("#span2").hide();
		$("#span3").hide();
		$("#span4").show();
		$("#span5").hide();
		$("#span6").hide();
		$("#queryTypeZbChooseReturnVal").val("04");
	}else if ("05"==val){ 
		$("#span2").hide();
		$("#span3").hide();
		$("#span4").hide();
		$("#span5").show();
		$("#span6").hide();
		$("#queryTypeZbChooseReturnVal").val("05");
	}else if ("06"==val){ 
		$("#span2").hide();
		$("#span3").hide();
		$("#span4").hide();
		$("#span5").hide();
		$("#span6").show();
		$("#queryTypeZbChooseReturnVal").val("06");
	}else{
		$("#span2").hide();
		$("#span3").hide();
		$("#span4").hide();
		$("#span5").hide();
		$("#span6").hide();
		
	}
}


</script>
	  <input type="hidden" name="queryTypeZbChooseReturnVal" id="queryTypeZbChooseReturnVal" value="${queryTypeZbChooseReturnVal}"/>
	  
<!-- 总部号码查询表单------------------------------------------------------------------------- -->
	<form method="post" id="numFormZb" >
       <div class="searchformDiv" >
                <table>
           	       <tr>
           	           <th>选号条件：</th>
           	            <td>
           	            <select  class="ipt_new" style="width:100px;" name="queryPara_10" id ="queryPara_10" onchange="changeSpan()">
		           	  		    <option value="0">---请选择---</option>
		           	  		    <option value="01" <c:if test="${queryTypeZbChooseReturnVal eq '01'}">selected="selected"</c:if>>随机</option>
		           	  		    <option value="02" <c:if test="${queryTypeZbChooseReturnVal eq '02'}">selected="selected"</c:if>>号段</option>
		           	  		    <option value="03" <c:if test="${queryTypeZbChooseReturnVal eq '03'}">selected="selected"</c:if>>号码关键字</option>
		           	  		    <option value="04" <c:if test="${queryTypeZbChooseReturnVal eq '04'}">selected="selected"</c:if>>靓号等级</option>
		           	  		    <option value="05" <c:if test="${queryTypeZbChooseReturnVal eq '05'}">selected="selected"</c:if>>预付费产品编码</option>
		           	  		    <option value="06" <c:if test="${queryTypeZbChooseReturnVal eq '06'}">selected="selected"</c:if>>查询号码范围</option>
	           	  		    </select>
           	            </td>
           	            
           	            <th></th>
	           	        <td>
	           	          <!-- 2号段 -->
	           	          <span id="span2" style="display: none;"><html:selectdict name ="segmentCode"  id="segmentCode" attr_code="NUM_TYPR"  curr_val="${segmentCode}"  style="width:100px;" ></html:selectdict></span>
                          <!-- 3号码关键字 -->
                          <span id="span3" style="display: none;"><input name="queryPara_21_Keywords" id ="queryPara_21_Keywords" value="${queryPara_03}"  style="width:70px" class="ipttxt" type="text"  class="ipt_new" style="width:100px;" ></span> 
	           	  		  <!-- 4 靓号等级-->
	           	  		  <span id="span4" style="display: none;"><html:selectdict  name ="queryPara_22_NumLevel" id="queryPara_22_NumLevel" attr_code="DC_NumLiangHaoLevelZB" curr_val="${queryPara_22_NumLevel}"  style="width:100px;color:#000" ></html:selectdict></span> 
	           	  	      <!-- 5预付费产品编码-->
	           	  	     <span span id="span5" style="display: none;" ><input  name="queryPara_23_prepaidProductCode" id ="queryPara_23_prepaidProductCode" value="${queryPara_23_prepaidProductCode}"  style="width:70px" class="ipttxt"   type="text" ></span> 
	           	  		  <!-- 6查询号码范围-->
	           	  		  <span id="span6" style="display: none;" ><html:selectdict  name ="queryPara_24_numRange"  id="queryPara_24_numRange" attr_code="DC_NumRange_ZB"  curr_val="${queryPara_24_numRange}"  style="width:120px;color:#000"></html:selectdict></span>
                         </td>
                           
                           <th>受理类型</th>
                           <td>
                           <select  class="ipt_new" style="width:100px;" name="queryPara_30_serType" id ="queryPara_30_serType" onchange="changeSpan()">
		           	  		    <option value="2" <c:if test="${queryPara_30_serType eq '2'}">selected="selected"</c:if>>预付费</option>
		           	  		    <option value="1" <c:if test="${queryPara_30_serType eq '1'}">selected="selected"</c:if>>后付费</option>
		           	  		   
                           </select>
                           </td>
                           
	           			   <td>
           	               <input type="button" id="queryBtnZb" class="graybtn1" style="margin-left:30px;" value="查&nbsp;询" />
	           			   </td> 
           	       </tr>
           	      
           	        <script type="text/javascript">
	           	  	      $("[name='queryPara_22_NumLevel'] option[value='${queryPara_22_NumLevel}']").attr("selected","selected");
	           	  	      $("[name='queryPara_10'] option[value='${queryPara_10}']").attr("selected","selected");
	           	  	      
	             	</script>
           	   </table>
           	</div>  
    	</form>   
    	
  <!-- ---------总部号码查询返回结果- -->  	
              <form id="form_numZb" class="grid" >
				<grid:grid  from="webpage" ajax='yes' formId="numFormZb">
					<grid:header>
						<grid:cell></grid:cell> 
					  	<grid:cell>号码</grid:cell> 
						<grid:cell>是否靓号</grid:cell> 
						<grid:cell>靓号等级</grid:cell>
					</grid:header>
				   
				    <grid:body item="obj">
				   		 <grid:cell>
				   		 	<input type="radio" name="selectNum" numId="${obj.numId}" ifLiangHao="${obj.ifLiangHao}" classId="${obj.classId}"  advancePay="${obj.advancePay}"  advanceCom="${obj.advanceCom}" advanceSpe="${obj.advanceSpe}" 
				   		 	  numThawTim="${obj.numThawTim}" numThawPro="${obj.numThawPro}" lowCostChe="${obj.lowCostChe}" timeDurChe="${obj.timeDurChe}"  changeTagChe="${obj.changeTagChe}" broMonPro="${obj.broMonPro}"
				   		 	  cancelTagChe="${obj.cancelTagChe}" bremonChe="${obj.bremonChe}" lowCostPro="${obj.lowCostPro}" timeDurPro="${obj.timeDurPro}" changeTagPro="${obj.changeTagPro}" cancelTagPro="${obj.cancelTagPro}"
				   		 	/>
				   		 </grid:cell> 
					     <grid:cell>${obj.numId}</grid:cell>
					     <grid:cell>${obj.ifLiangHao}</grid:cell>
					     <grid:cell>${obj.classId}</grid:cell>
				  </grid:body>
				</grid:grid>
				
				<div align="center" style="margin-top:20px;">
					<input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定"  id="savePhoneBtnZb">
				</div>
	       </form>
 	
<!-- 总部号码查询结束 -->
	<!-- 隐藏的表单项，用于选中号码后提交查询参数 -->
    <form method="post" id="savePhoneInfoForm">
      <input name="phoneInfoMap.classId" id="classId_dlg" type="hidden"/>	
	  <input name="phoneInfoMap.advancePay" id="advancePay_dlg"type="hidden" />	
	  <input name="phoneInfoMap.lowCostChe" id="lowCostChe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.numThawPro" id="numThawPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.numThawTim" id="numThawTim_dlg" type="hidden" />	
	  <input name="phoneInfoMap.advanceSpe" id="advanceSpe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.advanceCom" id="advanceCom_dlg" type="hidden" />	
	  <input name="phoneInfoMap.lowCostPro" id="lowCostPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.bremonChe" id="bremonChe_dlg"  type="hidden" />	
	  <input name="phoneInfoMap.cancelTagChe" id="cancelTagChe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.broMonPro" id="broMonPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.changeTagChe" id="changeTagChe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.timeDurChe" id="timeDurChe_dlg" type="hidden" />							   
	  <input name="phoneInfoMap.timeDurPro" id="timeDurPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.changeTagPro" id="changeTagPro_dlg" type="hidden" />						  
	  <input name="phoneInfoMap.cancelTagPro" id="cancelTagPro_dlg" type="hidden" /><!-- 协议期是否销户 -->
      <input name="phoneInfoMap.phone_num" id="phone_num_dlg" type="hidden" />
    </form>
    
    <input pageSizeReturn="${pageSizeReturn}" type="hidden"/>
<!-- 总部号码查询表单结束------------------------------------------------------------------------- -->    
    
  


<!-------总部换号脚本--------------------------------->

<script type="text/javascript">
var PhoneList={
		
		 
		//提交方案1 分页出错
		search:function(){
			 $("#queryBtnZb").unbind("click").bind("click",function(){
				 var queryPara_10=$("#queryPara_10").val();
				 if("0"==queryPara_10){
					 alert("请选择选号条件");
				 }else{
				 var order_id  = '${order_id}';
				 var queryTypeChooseReturnVal=$("#queryTypeChoose").val();
			     var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumList.do?ajax=yes&isQuery=yes&order_id="+order_id+"&infIsZb=yes&selNumChannel=zb"+"&queryTypeChooseReturnVal="+queryTypeChooseReturnVal+"&queryTypeZbChooseReturnVal="+queryTypeZbChooseReturnVal;
				 Cmp.ajaxSubmit('numFormZb','selPhoneDlgZb',url,{},PhoneList.initFun);
				 }
			 });
		 },

		 
		 
		 
		 //提交方案2
		 /*search:function(){
			 $("#queryBtnZb").unbind("click").bind("click",function(){
				 alert("提交1");
				 var order_id  = '${order_id}';
				 var queryPara_10=$("#queryPara_10").val();
				 var ordercitycode = $("#order_city_code").val();
				 if(queryPara_10 != "0"){
				 var queryTypeChooseReturnVal=$("#queryTypeChoose").val();
				 var queryPara_30_serType=$("#queryPara_30_serType").val();
			  // var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumList.do?ajax=yes&isQuery=yes&order_id="+order_id+"&infIsZb=yes&selNumChannel=zb";
				 var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumList.do?ajax=yes&isQuery=yes&order_id="+order_id+"&infIsZb=yes&selNumChannel=zb"+"&queryPara_30_serType="+queryPara_30_serType;
				 alert("提交2");
				 if("01"==queryPara_10){
					 url=url+"&queryPara_10=01";
				 }else if("02"==queryPara_10){
					 segmentCode=$("#segmentCode").val();
					 url=url+"&queryPara_10=02&segmentCode="+segmentCode;
				 }else if("03"==queryPara_10){
					 queryPara_21_Keywords=$("#queryPara_21_Keywords").val();
					 url=url+"&queryPara_10=03&queryPara_21_Keywords="+queryPara_21_Keywords;
				 }else if("04"==queryPara_10){
					 queryPara_22_NumLevel=$("#queryPara_22_NumLevel").val();
					 url=url+"&queryPara_10=04&queryPara_22_NumLevel="+queryPara_22_NumLevel;
				 }else if("05"==queryPara_10){
					 queryPara_23_prepaidProductCode=$("#queryPara_23_prepaidProductCode").val();
					 url=url+"&queryPara_10=05&queryPara_23_prepaidProductCode="+queryPara_23_prepaidProductCode;
				 }else if("06"==queryPara_10){
					 queryPara_24_numRange=$("#queryPara_24_numRange").val();
					 url=url+"&queryPara_10=06&queryPara_24_numRange="+queryPara_24_numRange;	 
				 }
				 //Cmp.ajaxSubmit('numFormZb','selPhoneDlgZb',url,{},PhoneList.initFun);
				 alert("提交前");
				 $("#selPhoneDlgZb").load(url, function() {});
				 alert("已提交");
				 }else{
					 alert("请选择选号条件");
				 }
			 });
		 },*/
		 
		 
		 initFun:function(){
			 changeSpan();
			 PhoneList.search();
			 PhoneList.check();
			
		 },
		 check:function(){
				$("#savePhoneBtnZb").unbind("click").bind("click",function(){
					var code = $("[name='selectNum']:checked").val();
					if(code==null){
						alert("还未选中号码");
					}else{
						var obj = $("[name='selectNum']:checked");
						var phone_num = obj.attr("numId");
						var classId = obj.attr("classId");
						var advancePay = obj.attr("advancePay");
						var advanceCom = obj.attr("advanceCom");
						var advanceSpe = obj.attr("advanceSpe");
						var numThawTim = obj.attr("numThawTim");
						var numThawPro = obj.attr("numThawPro");
						var lowCostChe = obj.attr("lowCostChe");
						var timeDurChe = obj.attr("timeDurChe");
						var changeTagChe = obj.attr("changeTagChe");
						var broMonPro = obj.attr("broMonPro");
						var cancelTagChe = obj.attr("cancelTagChe");
						var bremonChe = obj.attr("bremonChe");
						var lowCostPro = obj.attr("lowCostPro");
						var timeDurPro = obj.attr("timeDurPro");
						var changeTagPro = obj.attr("changeTagPro");
						var cancelTagPro = obj.attr("cancelTagPro");
						
						var orderPhoneInfo = {
								     phone_num:phone_num,
								     classId:classId,
								     advancePay:advancePay,
								     advanceCom:advanceCom,
								     advanceSpe:advanceSpe,
								     numThawTim:numThawTim,
								     numThawPro:numThawPro,
								     lowCostChe:lowCostChe,
								     timeDurChe:timeDurChe,
								     changeTagChe:changeTagChe,
								     broMonPro:broMonPro,
								     cancelTagChe:cancelTagChe,
								     bremonChe:bremonChe,
								     lowCostPro:lowCostPro,
								     timeDurPro:timeDurPro,
								     changeTagPro:changeTagPro,
								     cancelTagPro:cancelTagPro
						            };
					
					 $("#cancelTagPro_dlg").val(cancelTagPro);//协议期是否销户
					 $("#changeTagPro_dlg").val(changeTagPro);//协议期是否过户
					 $("#cancelTagChe_dlg").val(cancelTagChe);//考核期是否销户
					 $("#changeTagChe_dlg").val(changeTagChe);//考核期是否过户
					 $("#phone_num_dlg").val(phone_num);//号码
					 //setValue(orderPhoneInfo,"_dlg"); 
					 disableBtn('disabled');
                    // var param = {phoneInfoMap:orderPhoneInfo};      
                     var order_id  = '${order_id}';
                     var phone_num = '${phone_num}';
      				 var url= app_path+"/shop/admin/orderFlowAction!changePhoneInfo.do?ajax=yes&order_id="+order_id+"&phone_num="+phone_num;
      				 var saveBack = function(reply){
 	 				 	disableBtn('');
	 					 alert(reply.message);	
	 					 $("#phone_td [field_name='phone_num']").val(reply.view_phone_num);
	 					 $("#old_phone_num").val(reply.view_phone_num);
	 					 $("input[name='orderPhoneInfo.operatorState']").val(reply.operatorState);
	 					 if("0"==reply.operatorState){
	 					 	$("#spanOperState").val("预占成功");
	 					 }else if("1"==reply.operatorState){
	 					 	$("#spanOperState").val("预占失败");
	 					 }else if("2"==reply.operatorState){
	 					 	$("#spanOperState").val("预订成功");
	 					 }else if("3"==reply.operatorState){
	 					 	$("#spanOperState").val("预订失败");
	 					 }else if("4"==reply.operatorState){
	 					 	$("#spanOperState").val("非付费预订预订成功");
	 					 }else if("5"==reply.operatorState){
	 					 	$("#spanOperState").val("非付费预订预订失败");
	 					 }
      					 if(reply.result==0){
      						setValue(orderPhoneInfo,"");
      						Eop.Dialog.close("selPhoneDlgZb");
	      				 }
      				 }; 
      				Cmp.ajaxSubmit('savePhoneInfoForm', '', url, {}, saveBack, 'json');
				}	
			});	
		}
};


 $(function(){
	 PhoneList.initFun();
 });
 
 
function setValue(orderPhoneInfo,dlg){
	if(dlg==""){
		$("[field_name='phone_num']").val(orderPhoneInfo.phone_num);
	//	$("[name='orderPhoneInfo.cancelTagPro'] option[value='"+orderPhoneInfo.cancelTagPro+"']").attr("selected","selected");
	//	$("[name='orderPhoneInfo.changeTagPro'] option[value='"+orderPhoneInfo.changeTagPro+"']").attr("selected","selected");
	//	$("[name='orderPhoneInfo.cancelTagChe'] option[value='"+orderPhoneInfo.cancelTagChe+"']").attr("selected","selected");
	//  $("[name='orderPhoneInfo.changeTagChe'] option[value='"+orderPhoneInfo.changeTagChe+"']").attr("selected","selected");
	}
	
	//$("#classId"+dlg).val(orderPhoneInfo.classId);//给隐藏表单赋值
	//$("#advancePay"+dlg).val(orderPhoneInfo.advancePay);
	//$("#advanceCom"+dlg).val(orderPhoneInfo.advanceCom);
	//$("#advanceSpe"+dlg).val(orderPhoneInfo.advanceSpe);
	//$("#numThawTim"+dlg).val(orderPhoneInfo.numThawTim);
	//$("#numThawPro"+dlg).val(orderPhoneInfo.numThawPro);
	//$("#lowCostChe"+dlg).val(orderPhoneInfo.lowCostChe);
	//$("#timeDurChe"+dlg).val(orderPhoneInfo.timeDurChe);
	//$("#changeTagChe"+dlg).val(orderPhoneInfo.changeTagChe);
	//$("#broMonPro"+dlg).val(orderPhoneInfo.broMonPro);
	//$("#cancelTagChe"+dlg).val(orderPhoneInfo.cancelTagChe);
	//$("#bremonChe"+dlg).val(orderPhoneInfo.bremonChe);
	//$("#lowCostPro"+dlg).val(orderPhoneInfo.lowCostPro);
	//$("#timeDurPro"+dlg).val(orderPhoneInfo.timeDurPro);
	//$("#changeTagPro"+dlg).val(orderPhoneInfo.changeTagPro);
	//$("#cancelTagPro"+dlg).val(orderPhoneInfo.cancelTagPro);
	}

</script>

