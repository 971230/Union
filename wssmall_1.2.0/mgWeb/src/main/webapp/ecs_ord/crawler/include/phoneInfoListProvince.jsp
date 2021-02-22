<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
function disableBtn(dis){	
	$("#savePhoneBtn").attr("disabled",dis);
	$("#savePhoneBtn1").attr("disabled",dis);
	$("#queryBtn").attr("disabled",dis);
}


</script>


   
    <!-- --------------------------------------------------------------------------- --> 
	<!-- 省内查询form -->
    	
    	<form method="post" id="numFormProvince"  >
            <div class="searchformDiv">
                <table>
           	       <tr>
	           	          <th>号码地市：</th>
                             <td><html:selectdict name="provinceQueryPara_001" id="provinceQueryPara_001"  curr_val="${provinceQueryPara_001}" style="color:#000" attr_code="DC_CITY_ZJ" ></html:selectdict></td>
	           			  <th>查询类型：</th>
	           			    <td><html:selectdict name="numSearchType" id="numSearchType" curr_val="${queryPara_03}" style="color:#000" attr_code="NUM_SEARCH_PRI_KEY"></html:selectdict></td>
	           			  <th id="randomTh">随机类型：</th>
	           			  <td id="randomTd"><html:selectdict name="randomType" id="randomType"  curr_val="${provinceQueryPara_002}" style="color:#000" attr_code="DC_NumRandomSearchTypeZJ"  ></html:selectdict></td>
	           			  <th id="numKeyWordTh" style="display:none;">关键字：</th>
					      <td id="numKeyWordTd" style="display:none;"><input type="text" class="ipttxt" name="numKeyWord" dataType="string" id="numKeyWord"  value="${provinceQueryPara_002 }" /></td>
					
	           			  <th>返回个数：</th>
	           			    <td><html:selectdict name="provinceQueryPara_003" id="provinceQueryPara_003"  curr_val="${provinceQueryPara_003}" style="color:#000" attr_code="DC_NumReturnCountZJ"  ></html:selectdict></td>
	           			  
	           			   <td> <input type="button" id="queryBtnProvince" class="graybtn1" style="margin-left:30px;" value="查&nbsp;询" /> </td> 
           	       </tr>
                    <!--  对自定义select的值做设置
           	        <script type="text/javascript">
	           	  	      $("[name='provinceQueryPara_001'] option[value='${provinceQueryPara_001}']").attr("selected","selected");
	             	</script>-->
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
					     				<grid:cell>
					                     <c:choose>
                                            <c:when test="${obj.is_spenum == '00'}">
       					                                                      普通号码
       				                     </c:when>
                                         <c:otherwise>
       					                               靓号
       				                     </c:otherwise>
					                   </c:choose>
				</grid:cell>
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
	  <input name="phoneInfoMap.phone_num" id="phone_num_dlg" type="hidden" />	

    </form>

	 <!-- 省内查询结束--------------------------------------------------------------------------- --> 


<script type="text/javascript">

//---省份选号换号脚本----------------------------------------------------------------------------

var PhoneList2={
		 search2:function(){
			 
			 $("#queryBtnProvince").unbind("click").bind("click",function(){
				 var order_id  = '${order_id}';
				 var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumList.do?ajax=yes&isQuery=yes&order_id="+order_id+"&infIsZb=no";
				 var numSearchType = $("#numSearchType").val();
					if(numSearchType=="01"){
						var provinceQueryPara_002 = $("#randomType").val();
						if(provinceQueryPara_002=="0"){
							alert("请选择随机类型");
							return;
						}else{
						url += "&provinceQueryPara_002="+provinceQueryPara_002 +"&queryPara_03="+numSearchType;
						}
					}else{
						var provinceQueryPara_002 = $("#numKeyWord").val();
						url += "&provinceQueryPara_002="+provinceQueryPara_002 +"&queryPara_03="+numSearchType;
					}

				 //Cmp.ajaxSubmit('numFormProvince','selPhoneDlgProvince',url,{},PhoneList2.initFun2);
				 Cmp.ajaxSubmit('numFormProvince','selPhoneDlg',url,{},PhoneList2.initFun2);
				                                 //selPhoneDlgProvince 为phone_info.jsp页面的空DIV，用于显示内容
			 });
		 },
		 
		 
			changeParam:function(){
				$("#numSearchType").change(function(){
					var searchType = $(this).val();
					if(searchType == "01"){
						$("#randomTh").show();
						$("#randomTd").show();
						$("#numKeyWordTh").hide();
						$("#numKeyWordTd").hide();
					}
					else{
						$("#randomTh").hide();
						$("#randomTd").hide();
						$("#numKeyWordTh").show();
						$("#numKeyWordTd").show();
						
					}
				});
			},
		 
		 initFun2:function(){
			 PhoneList2.search2();
			 PhoneList2.check2();
			 PhoneList2.changeParam();
			 resetInputAfterValRetrun();
			 
		 },
		 
		 check2:function(){
				$("#savePhoneBtnProvince").unbind("click").bind("click",function(){
					var code = $("[name='selectNumProvince']:checked").val();
					if(code==null){
						alert("还未选中号码");
					}else{
						var obj = $("[name='selectNumProvince']:checked");
						var phone_num = obj.attr("number");//获得选中项的新号码
						var is_spenum = obj.attr("is_spenum");
						var orderPhoneInfo = {
								     is_spenum:is_spenum,
								     phone_num:phone_num
						            };
                        var order_id  = '${order_id}';
					    disableBtn('disabled');
                        $("#phone_num_dlg").val(phone_num);//新号码赋值到要提交合并的表单
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
      					 if(reply.result==0){//变更成功设置
      						setValue2(orderPhoneInfo,"");//设置input为更新后的号码
      						Eop.Dialog.close("selPhoneDlgProvince");
	      				 }
      				 }; 
      				Cmp.ajaxSubmit('savePhoneInfoFormProvince', '', url, {}, saveBack, 'json');
				}	
			});
				
		}
};
 $(function(){//初始化调用，只在初始化时执行
	 PhoneList2.initFun2();
 });
function setValue2(orderPhoneInfo,dlg){
	if(dlg==""){
		$("[field_name='phone_num']").val(orderPhoneInfo.phone_num);//在背景页面显示
	}
}
	
	function resetInputAfterValRetrun(){//当查询返回时，根据查询类型返回的值配置关键字是否显示或隐藏
		var searchType = $("#numSearchType").val();
		if(searchType == "01"){
			$("#randomTh").show();
			$("#randomTd").show();
			$("#numKeyWordTh").hide();
			$("#numKeyWordTd").hide();
		}
		else{
			$("#randomTh").hide();
			$("#randomTd").hide();
			$("#numKeyWordTh").show();
			$("#numKeyWordTd").show();
		}
	}

</script>

