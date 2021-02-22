<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
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

<div>
	<form id="batchForm" action="numero!listBatch.do" method="post">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>是否靓号：</th>
						<td><select id="select_isLucky" style="width: 155px" v="${params.isLucky }"
							name="params.isLucky">
								<option value="">--请选择--</option>
								<option value="1">是</option>
								<option value="0">否</option>
						</select></td>
						<th>网别：</th>
						<td><select name="params.internet" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${internets}" var="v">
									<option
										<c:if test="${params.internet eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
						<th>号码分组：</th>
						<td><select name="params.group" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${grupos}" var="v">
									<option
										<c:if test="${params.group eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>合约期：</th>
						<td><select id="select_contract" style="width:155px;" name="params.contract" v="${params.contract}">
								<option value="">--请选择--</option>
								<option value="3">3个月</option>
								<option value="6">6个月</option>
								<option value="9">9个月</option>
								<option value="12">12个月</option>
								<option value="18">18个月</option>
								<option value="24">24个月</option>
								<option value="36">36个月</option>
						</select></td>
						<th>区域：</th>
						<td><select name="params.address" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${ciudads}" var="v">
									<option <c:if test="${params.address eq v.key }"> selected='selected'</c:if> value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
						<th>发布状态：<input type="hidden" name="params.flag"
							value="search" id="publishAndSearch"></th>
						<td><select name="params.no_statu" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${liberacions}" var="v">
									<option
										<c:if test="${params.no_statu eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
						
					</tr>
					<tr>
						<th>前7位：</th>
						<td><input type="input" name="params.beforeSeven" id="beforeSeven_input" value="${params.beforeSeven }"
							dataType="int"  class="ipttxt"></td>
						<th>后4位：</th>
						<td><input type="input" name="params.lastFour" id="lastFour_input" value="${params.lastFour }" dataType="int"
							class="ipttxt"></td>
						<th>预存款：</th>
						<td><input type="input" id="deposit_min_input" name="params.deposit_min" value="${params.deposit_min }"
							dataType="int" style="width:62px" class="ipttxt">&nbsp;至 &nbsp;<input
							type="input" name="params.deposit_max" id="deposit_max_input" value="${params.deposit_max }" dataType="int"
							style="width:62px" class="ipttxt"></td>
					</tr>
					<tr>
						<th>批次号：</th>
						<td><input type="input" id="batch_id_input" name="params.batch_id" value="${params.batch_id }"
							dataType="int" class="ipttxt">
							
						<th>开始时间：</th>
						<td><input type="text" name="params.start_date"
							value="${params.start_date }" id="start_date" readonly="readonly"
							style="width:150px" class="dateinput ipttxt" dataType="date" /></td>
						<th>结束时间：</th>
						<td><input type="text" name="params.end_date"
							value="${params.end_date }" id="end_date" readonly="readonly"
							style="width:150px" class="dateinput ipttxt" dataType="date" /></td>
					</tr>
					
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">
		    <input type="button" class="comBtn" style="margin-left:10px;"
							value="搜&nbsp;索" id="searchButton" name="button">
			<span style="float:right;">批量发布数：
				<input type="hidden" name="params.orgIds" id="orgids_input">
				<input type="input" name="params.batchNum" dataType="int" id="confirmBatchNum" value="${params.batchNum }"
							size="8" class="ipttxt"> <input type="button"
							style="margin-right:20px;" class="comBtn" value="发&nbsp;布"
							id="publishButton" name="button">
			<span>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell width="5px">
				</grid:cell>
				<grid:cell>批次号</grid:cell>
				<grid:cell>号码</grid:cell>
				<grid:cell>号码分组</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>地市</grid:cell>
				<grid:cell>发布状态</grid:cell>
				<grid:cell>销售组织</grid:cell>
				<grid:cell>靓号</grid:cell>
				<grid:cell>靓号类型</grid:cell>
				<grid:cell>预存款</grid:cell>
				<grid:cell>合约期</grid:cell>
				<grid:cell>最低消费</grid:cell>
				<grid:cell>减免金额</grid:cell>
			</grid:header>

			<grid:body item="objeto">
				<grid:cell>
				</grid:cell>
				<grid:cell>${objeto.batch_id } </grid:cell>
				<grid:cell>${objeto.dn_no } </grid:cell>
				<grid:cell>${objeto.group_name}  </grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${objeto.created_date }"></html:dateformat></grid:cell>
				<grid:cell>${objeto.status } </grid:cell>
				<grid:cell>${objeto.region_name} </grid:cell>
				<grid:cell>${objeto.distribute_status}  </grid:cell>
				<grid:cell>
					<span title="${objeto.grupos }">${fn:substring(objeto.grupos,0,10) }</span>
				</grid:cell>
				<grid:cell>${objeto.is_lucky }  </grid:cell>
				<grid:cell>${objeto.no_classify }  </grid:cell>
				<grid:cell>${objeto.deposit }元</grid:cell>
				<grid:cell> ${objeto.period }个月 </grid:cell>
				<grid:cell> ${objeto.lowest }元</grid:cell>
				<grid:cell> ${objeto.fee_adjust }元</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
<!-- 号码批量发布 -->
<div id="numbers_pub_dialog"></div>
<script>
$(function() {
	$("form.validate").validate();
	Eop.Dialog.init({
		id : "numbers_pub_dialog",
		modal : true,
		title : "号码发布",
		width : "500px",
		height : "400px"
	});
	
	function checkInput(){
		var beforeS = $.trim($("#beforeSeven_input").val());
		if(beforeS.length!=0&&beforeS.length!=7){
			//联通号码开头2g的131.132.155.156.3g的186
			alert("前7位号码输入有误，请检查！");
			return false;
		}
		var lastFour = $.trim($("#lastFour_input").val());
		if(lastFour.length!=0&&lastFour.length!=4){
			alert("后4位号码输入有误，请检查！");
			return false;
		}
		var lowest = $.trim($("#lowest_input").val());
		if(lowest.length!=0&&parseInt(lowest)>99999){
			alert("最低消费不能大于100000，请检查！");
			return false;
		}
		var deposit_min = $.trim($("#deposit_min_input").val());
		if(deposit_min.length!=0&&parseInt(deposit_min)>99999){
			alert("预存款不能大于100000，请检查！");
			return false;
		}
		var deposit_max = $.trim($("#deposit_max_input").val());
		if(deposit_max.length!=0&&parseInt(deposit_max)>99999){
			alert("预存款不能大于100000，请检查！");
			return false;
		}
		return true;
	}
	
	$("#searchButton").bind("click", function() {
		$("#publishAndSearch").val("search");
		if(checkInput()){
			$("#batchForm").submit();
		}
	});

	$("#publishButton").bind("click", function() {
		if($.trim($("#confirmBatchNum").val())==""||parseInt($.trim($("#confirmBatchNum").val()))>500){
			alert("请输入小于500的发布数量再操作！");
		}else{
			if(checkInput()){
				$("#publishAndSearch").val("publish");
				var url = "numero!numeroBatchPubTree.do?busqueda=false&batchPublishWay=amount";
				abrirCajaVentana("numbers_pub_dialog",url);
				//var url = "numero!selectShop.do?ajax=yes&numbers=";
				//$("#numbers_pub_dialog").load(url, function(resp) {
					//Eop.Dialog.open("numbers_pub_dialog");
				//});
			}
		}
	});
	//还原合约期和是否靓号
	$("#select_isLucky").find("option[value='"+$("#select_isLucky").attr("v")+"']").attr("selected","selected");
	$("#select_contract").find("option[value='"+$("#select_contract").attr("v")+"']").attr("selected","selected");
});
</script>