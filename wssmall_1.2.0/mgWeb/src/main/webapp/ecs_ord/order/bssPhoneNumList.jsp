<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<%@ include file="/commons/taglibs.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script>
	var ctx = '${ctx}';
</script>
<script type="text/javascript">
	function disableBtn(dis) {
		$("#savePhoneBtn").attr("disabled", dis);
		$("#savePhoneBtn1").attr("disabled", dis);
		$("#queryBtn").attr("disabled", dis);
	}
</script>
<div class="goodsList" id="listGoods">
	<form action="javascript:;" method="post" id="numFormProvince">
		<div class="searchformDiv">
			<table>
				<tr>
					<th>号码地市：</th>
					<td><html:selectdict name="provinceQueryPara_001" id="provinceQueryPara_001" curr_val="${provinceQueryPara_001}" style="color:#000" attr_code="DC_CITY_ZJ"></html:selectdict></td>
					<th>查询类型：</th>
					<td><html:selectdict name="numSearchType" id="numSearchType" curr_val="${queryPara_03}" style="color:#000" attr_code="NUM_SEARCH_PRI_KEY"></html:selectdict></td>
					<th id="randomTh">随机类型：</th>
					<td id="randomTd"><html:selectdict name="randomType" id="randomType" curr_val="${provinceQueryPara_002}" style="color:#000" attr_code="DC_NumRandomSearchTypeZJ"></html:selectdict></td>
					<th id="numKeyWordTh" style="display:none;">关键字：</th>
					<td id="numKeyWordTd" style="display:none;"><input type="text" class="ipttxt" name="numKeyWord" dataType="string" id="numKeyWord" required="true" value="${provinceQueryPara_002 }" /></td>
					<th>返回个数：</th>
					<td><html:selectdict name="provinceQueryPara_003" id="provinceQueryPara_003" curr_val="${provinceQueryPara_003}" style="color:#000" attr_code="DC_NumReturnCountZJ"></html:selectdict></td>

					<td><input type="button" id="queryBtnProvince"
						class="graybtn1" style="margin-left: 30px;" value="查&nbsp;询" /></td>
				</tr>
				<!--  对自定义select的值做设置-->
				<script type="text/javascript">
					//$("[name='provinceQueryPara_001'] option[value='${provinceQueryPara_001}']").attr("selected", "selected");
				</script>
			</table>
		</div>
	</form>
	
	
	<form id="provincenumForm" class="grid">
		<grid:grid from="webpage" ajax='yes' formId="numFormProvince">
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>号码</grid:cell>
				<grid:cell>是否靓号</grid:cell>
				<grid:cell>靓号等级</grid:cell>
			</grid:header>
			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="selectNumProvince" number="${obj.number}"
						is_spenum="${obj.is_spenum}" num_lvl="${obj.num_lvl}" />
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
		<div align="center" style="margin-top: 20px">
			<input type="button" style="margin-left: 5px; margin-top: 10px;"
				class="comBtn" value="确&nbsp;&nbsp;定" id="savePhoneBtnProvince">
		</div>
	</form>

</div>
<script type="text/javascript">
	var BssPhoneNum = {
		qryBssPhoneNum : function() {
			$("#queryBtnProvince").click(function() {
				var ordercitycode = $("#order_city_code").val();
				var url = app_path+ "/shop/admin/orderFlowAction!qryBssPhoneNumList.do?ajax=yes&isQuery=yes&ordercitycode="+ordercitycode;
				var provinceQueryPara_001 = $("#provinceQueryPara_001").val();
				var provinceQueryPara_003 = $("#provinceQueryPara_003").val();
				var numSearchType = $("#numSearchType").val();
				if(numSearchType=="01"){
					var provinceQueryPara_002 = $("#randomType").val();
					if(provinceQueryPara_002=="0"){
						alert("请选择随机类型");
						return;
					}else{
					url += "&provinceQueryPara_001="+provinceQueryPara_001+"&provinceQueryPara_002="+provinceQueryPara_002
							+"&provinceQueryPara_003="+provinceQueryPara_003+"&queryPara_03="+numSearchType;
					}
				}else{
					var provinceQueryPara_002 = $("#numKeyWord").val();
					url += "&provinceQueryPara_001="+provinceQueryPara_001+"&provinceQueryPara_002="+provinceQueryPara_002
							+"&provinceQueryPara_003="+provinceQueryPara_003+"&queryPara_03="+numSearchType;
				}
				//$("#selPhoneDlgProvince").load(url, function() {});
				$("#selPhoneDlg").load(url, function() {});
				
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
		init : function() {
			BssPhoneNum.qryBssPhoneNum();
			BssPhoneNum.check();
			BssPhoneNum.changeParam();
			resetInputAfterValRetrun();
		},
		check : function() {
			$("#savePhoneBtnProvince").unbind("click").bind("click",function() {
				var code = $("[name='selectNumProvince']:checked").val();
				if (code == null) {
					alert("还未选中号码");
				} else {
					var obj = $("[name='selectNumProvince']:checked");
					var number = obj.attr("number");
					var is_spenum = obj.attr("is_spenum");
					var num_lvl = obj.attr("num_lvl");
					$("#acc_nbr").val(number);
					$("#acc_nbr").focus();
					Eop.Dialog.close("showPhoneSelctionDiv");
				}
			});

		}
	};
	$(function() {
		BssPhoneNum.init();
	});
	function setValue(orderPhoneInfo, dlg) {
		if (dlg.length = 0) {
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

