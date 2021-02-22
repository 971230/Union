<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
function disableBtn(dis){	
	$("#savePhoneBtn").attr("disabled",dis);
	$("#savePhoneBtn1").attr("disabled",dis);
	$("#queryBtn").attr("disabled",dis);
}

function changeDiv(){	

	var valu=$("#queryTypeChoose").val();

	if("0"==valu){//省份选号
		$("#div1").hide();
		$("#numFormZb").hide();
		$("#form_numZb").hide();
		$("#numFormProvince").show();
		$("#ProvincenumForm").show();
		$("#queryTypeChooseReturnVal").val("0");
	}else{//总部选号
		$("#div1").hide();
		$("#numFormZb").show();
		$("#form_numZb").show();
		$("#numFormProvince").hide();	
		$("#ProvincenumForm").hide();
		$("#queryTypeChooseReturnVal").val("1");
	}
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

function changePhoneNum(){
		var phone_num=$("#phoneNumByH").val();
		if (!(/^[0-9]{11}$/.test(phone_num))) {
	      alert("电话号码只能是11位的数字组成");
	      return false;
	 	}
		var classId = '9';
		var advancePay = '';
		var advanceCom = '';
		var advanceSpe = '';
		var numThawTim = '';
		var numThawPro = '';
		var lowCostChe = '';
		var timeDurChe = '';
		var changeTagChe = '0';
		var broMonPro = '';
		var cancelTagChe = '0';
		var bremonChe = '';
		var lowCostPro = '';
		var timeDurPro = '';
		var changeTagPro = '0';
		var cancelTagPro = '0';
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
	
	 $("#cancelTagPro_dlg").val(cancelTagPro);
	 $("#changeTagPro_dlg").val(changeTagPro);
	 $("#cancelTagChe_dlg").val(cancelTagChe);
	 $("#changeTagChe_dlg").val(changeTagChe);
	 $("#phone_num_dlg").val(phone_num);
	 setValue(orderPhoneInfo,"_dlg"); 
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
	  						Eop.Dialog.close("selPhoneDlg");
	  				 }
	 				 }; 
	 				Cmp.ajaxSubmit('savePhoneInfoForm', '', url, {}, saveBack, 'json');	
}
</script>
	<div class="searchformDiv">
		<select  class="ipt_new" style="width:100px;" name="queryTypeChoose" onchange="changeDiv();" id ="queryTypeChoose" >
			<option value="0" <c:if test="${queryTypeChooseReturnVal eq '0'}">selected="selected"</c:if>>省份号码查询</option>
			<option value="1" <c:if test="${queryTypeChooseReturnVal eq '1'}">selected="selected"</c:if>>总部号码查询</option>
		</select>
	</div>
	<div class="searchformDiv" id="div1" style="display: none;">
		<div align="center" style="margin-top:20px;">
			<input type="text"  class="ipt_new" style="width:100px;" name="phoneNumByH" id ="phoneNumByH"  style="width:70px" class="ipttxt">
			<input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定" onclick="changePhoneNum();" id="savePhoneBtn1">
		</div>
	</div>
	
	  <input type="hidden" name="queryTypeChooseReturnVal" id="queryTypeChooseReturnVal" value="${queryTypeChooseReturnVal}"/>
	  <input type="hidden" name="queryTypeZbChooseReturnVal" id="queryTypeZbChooseReturnVal" value="${queryTypeZbChooseReturnVal}"/>
	  
<!-- 总部号码查询表单------------------------------------------------------------------------- -->
	<form method="post" id="numFormZb" style="display: none;">
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
              <form id="form_numZb" class="grid" style="display: none;">
				<grid:grid  from="webpage" ajax='yes' formId="numForm">
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
<!-- 总部号码查询表单结束------------------------------------------------------------------------- -->    
    
    
    <!-- --------------------------------------------------------------------------- --> 
	<!-- 省内查询form -->
    	
    	<form method="post" id="numFormProvince"  >
            <div class="searchformDiv">
                <table>
           	       <tr>
	           	          <th>号码地市：</th>
                             <td><html:selectdict name="provinceQueryPara_001" id="provinceQueryPara_001"  curr_val="${provinceQueryPara_001}" style="color:#000" attr_code="DC_CITY_ZJ"  ></html:selectdict></td>
	           			  <th>随机查询类型：</th>
	           			    <td><html:selectdict name="provinceQueryPara_002" id="provinceQueryPara_002"  curr_val="${provinceQueryPara_002}" style="color:#000" attr_code="DC_NumRandomSearchTypeZJ"  ></html:selectdict></td>
	           			  <th>返回个数：</th>
	           			    <td><html:selectdict name="provinceQueryPara_003" id="provinceQueryPara_003"  curr_val="${provinceQueryPara_003}" style="color:#000" attr_code="DC_NumReturnCountZJ"  ></html:selectdict></td>
	           			  
	           			   <td>
           	               <input type="button" id="queryBtnProvince" class="graybtn1" style="margin-left:30px;" value="查&nbsp;询" />
	           			   </td> 
           	       </tr>
                    <!--  对自定义select的值做设置-->
           	        <script type="text/javascript">
	           	  	      $("[name='provinceQueryPara_001'] option[value='${provinceQueryPara_001}']").attr("selected","selected");
	             	</script>
           	   </table>
           	</div>  
    	</form>  
    	
    	<!-- 省份查询返回结果 -->
    	    <form id="ProvincenumForm" class="grid" >
				<grid:grid  from="webpage" ajax='yes' formId="numFormProvince">
					<grid:header>
						<grid:cell></grid:cell> 
					  	<grid:cell>号码</grid:cell> 
						<grid:cell>是否靓号</grid:cell> 
						<grid:cell>靓号等级</grid:cell>
					</grid:header>
				    <grid:body item="obj">
				   		 <grid:cell>
				   		 	<input type="radio" name="selectNumProvince" number="${obj.number}" is_spenum="${obj.is_spenum}" num_lvl="${obj.num_lvl} }"/>
				   		 </grid:cell> 
					     <grid:cell>${obj.number}</grid:cell>
					     <grid:cell>${obj.is_spenum}</grid:cell>
					     <grid:cell>${obj.num_lvl}</grid:cell>
				  </grid:body>
				</grid:grid>
				<div align="center" style="margin-top:20px">
					<input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定"  id="savePhoneBtnProvince">
				</div>
	</form>
	
	<!-- 省份选号后要提交的隐藏表单 -->
	    <form method="post" id="savePhoneInfoFormProvince">
      <input name="phoneInfoMap.classId" id="classId_dlg" type="hidden"/>	
	  <input name="phoneInfoMap.advancePay" id="advancePay_dlg"type="hidden" />	
	  <input name="phoneInfoMap.lowCostChe" id="lowCostChe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.numThawPro" id="numThawPro_dlg" type="hidden" />	
	 
    </form>
	
	
	 <!-- 省内查询结束--------------------------------------------------------------------------- --> 
    
    
    
    
    
    
<script type="text/javascript">

 $(function(){
	 
//查号接口总部或省份选择页面初始化	 
 var optionRaw=$("#queryTypeChooseReturnVal").val();
if(optionRaw==""){
	$("#queryTypeChooseReturnVal").val("0");
}else{
	changeDiv();//已选择过的调整到上一次选择的页面  
}		 

//总部查询类型页面初始化   
   var zbQueryType=$("#queryTypeZbChooseReturnVal").val();
   if(zbQueryType==""){

	   $("#queryTypeZbChooseReturnVal").val("0");
   }else{
	   changeSpan(); //已查询过的调整到上一次查询页面  
   }

 });
 
</script>

<!-------总部换号脚本--------------------------------->

<script type="text/javascript">
var PhoneList={
		 search:function(){
			 $("#queryBtnZb").click(function(){
				 var order_id  = '${order_id}';
				 var queryTypeChooseReturnVal=$("#queryTypeChoose").val();
				 var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumList.do?ajax=yes&isQuery=yes&order_id="+order_id+"&queryTypeChooseReturnVal="+queryTypeChooseReturnVal+"&queryTypeZbChooseReturnVal="+queryTypeZbChooseReturnVal+"&infIsZb=yes";
				 Cmp.ajaxSubmit('numFormZb','selPhoneDlg',url,{},PhoneList.initFun);
			 });
		 },
		 initFun:function(){
			 changeDiv();
			 changeSpan();
			 PhoneList.search();
			 PhoneList.check();
		 },
		 check:function(){
				$("#savePhoneBtnZb").unbind("click").bind("click",function(){
					var code = $("[name='selectNum']:checked").val();
					if(code==null){
						alert("还未选中号码--总部的");
						alert("2t");
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
					 setValue(orderPhoneInfo,"_dlg"); 
					 disableBtn('disabled');
                    // var param = {phoneInfoMap:orderPhoneInfo};      
                     var order_id  = '${order_id}';
                     var phone_num = '${phone_num}';
      				 var url= app_path+"/shop/admin/orderFlowAction!changePhoneInfo.do?ajax=yes&order_id="+order_id+"&phone_num="+phone_num;
      				 var saveBack = function(reply){
      					 alert("11");
      				 	disableBtn('');
      				  alert("12");
      					 alert(reply.message);	
      					 alert("13");
      					 $("#phone_td [field_name='phone_num']").val(reply.view_phone_num);
      					 alert("14");
      					 $("#old_phone_num").val(reply.view_phone_num);
      					alert("15");
      					 $("input[name='orderPhoneInfo.operatorState']").val(reply.operatorState);
      					alert("16");
      					 $("#spanOperState_${old_phone_num}").html(reply.message);
      					alert("17");
      					 if(reply.result==0){
      						alert("18");
      						setValue(orderPhoneInfo,"");//号码变更成功后更新页面显示为新号码
      						alert("19");
      						Eop.Dialog.close("selPhoneDlg");
      						alert("20");

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
	if(dlg.length=0){
		alert("1");
		alert("2:"+orderPhoneInfo.phone_num);
		$("[field_name='phone_num']").val(orderPhoneInfo.phone_num);
		alert("3");
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

//-------------------------------------------------------------------------------------------------------------
//---省份选号换号脚本----------------------------------------------------------------------------

var PhoneList2={
		 search2:function(){
			 $("#queryBtnProvince").click(function(){
				 var order_id  = '${order_id}';
				 var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumListByProvince.do?ajax=yes&isQuery=yes&order_id="+order_id+"&infIsZb=no";
				 Cmp.ajaxSubmit('numFormProvince','selPhoneDlg',url,{},PhoneList.initFun);
				                                 //selPhoneDlg 为phone_info.jsp页面的空DIV，用于显示内容
			 });
		 },
		 
		 initFun2:function(){
			 PhoneList2.search2();
			 PhoneList2.check2();
		 },
		 
		 check2:function(){
				$("#savePhoneBtnProvince").unbind("click").bind("click",function(){
					var code = $("[name='selectNumProvince']:checked").val();
					if(code==null){
						alert("还未选中号码---省份的");
					}else{
						var obj = $("[name='selectNumProvince']:checked");
						var number = obj.attr("number");
						var is_spenum = obj.attr("is_spenum");
						var num_lvl = obj.attr("num_lvl");
						
						var orderPhoneInfo = {
								     number:number,
								     classId:classId,
								     is_spenum:is_spenum,
								     num_lvl:num_lvl
						            };
					
					 $("#cancelTagPro_dlg").val(cancelTagPro);
					 $("#changeTagPro_dlg").val(changeTagPro);
					 $("#cancelTagChe_dlg").val(cancelTagChe);
					 $("#changeTagChe_dlg").val(changeTagChe);
					 $("#phone_num_dlg").val(phone_num);
					 setValue(orderPhoneInfo,"_dlg"); 
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
      					 $("#spanOperState_${old_phone_num}").html(reply.message);
      					 if(reply.result==0){
      						setValue(orderPhoneInfo,"");
      						Eop.Dialog.close("selPhoneDlg");
	      				 }
      				 }; 
      				Cmp.ajaxSubmit('savePhoneInfoFormProvince', '', url, {}, saveBack, 'json');
      				
				}	
			});
				
		}
};
 $(function(){
	 PhoneList2.initFun2();
 });
function setValue2(orderPhoneInfo,dlg){
	if(dlg.length=0){
		$("[field_name='phone_num']").val(orderPhoneInfo.phone_num);//在背景页面显示
	//	$("[name='orderPhoneInfo.cancelTagPro'] option[value='"+orderPhoneInfo.cancelTagPro+"']").attr("selected","selected");
	//	$("[name='orderPhoneInfo.changeTagPro'] option[value='"+orderPhoneInfo.changeTagPro+"']").attr("selected","selected");
	//	$("[name='orderPhoneInfo.cancelTagChe'] option[value='"+orderPhoneInfo.cancelTagChe+"']").attr("selected","selected");
	//  $("[name='orderPhoneInfo.changeTagChe'] option[value='"+orderPhoneInfo.changeTagChe+"']").attr("selected","selected");
	}
	//$("#classId"+dlg).val(orderPhoneInfo.classId);
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

