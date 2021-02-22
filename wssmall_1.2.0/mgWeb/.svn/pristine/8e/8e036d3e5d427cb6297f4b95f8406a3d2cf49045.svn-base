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

<div id="numeroMainDiv">

<form action="numero!list.do" id="uploadForm" method="post"
				enctype="multipart/form-data">
				<input type="hidden" value=""  name="params.list" id="lists"> 
		<input type="hidden" id="flag" value="${params.estado }" />
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>批次号：</th>
						<td><input size="15" type="text" name="params.lote"
							value="${params.lote}" class="ipttxt" /></td>
						<th>开始时间：</th>
						<td><input size="15" type="text" name="params.start_date"
							value="${params.start_date }" id="start_date" readonly="readonly"
							class="dateinput ipttxt" dataType="date" /></td>
						<th>结束时间：</th>
						<td><input size="15" type="text" name="params.end_date"
							value="${params.end_date }" id="end_date" readonly="readonly"
							class="dateinput ipttxt" dataType="date" /></td>
					</tr>
					<tr>
						<th>号码：</th>
						<td><input size="15" type="text" name="params.number"
							value="${params.number}" class="ipttxt" /></td>
						<th>网别：</th>
						<td><select name="params.internet">
								<option value="">请选择所属网别</option>
								<c:forEach items="${internets}" var="v">
									<option
										<c:if test="${params.internet eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
						<th>号码状态：</th>
						<td><select name="params.estado" id="numero_estados">
								<option value="">请选择号码状态</option>
								<c:forEach items="${estados}" var="v">
									<option
										<c:if test="${params.estado eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
						
					</tr>
					<tr>
						<th>区域：</th>
						<td><select name="params.ciudad" style="width: 120px">
								<option value="">请选择发布区域</option>
								<c:forEach items="${ciudads}" var="v">
									<option
										<c:if test="${params.ciudad eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
						<th>发布状态：</th>
						<td><select name="params.liberacions" style="width: 120px">
								<option value="">请选择发布状态</option>
								<c:forEach items="${liberacions}" var="v">
									<option
										<c:if test="${params.liberacions eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
						</select></td>
						<th>号码分组：</th>
						<td><select name="params.grupo" style="width: 120px">
								<option value="">请选择分组</option>
								<c:forEach items="${grupos}" var="v">
									<option
										<c:if test="${params.grupo eq v.key }"> selected='selected'</c:if>
										value="${v.key }">${v.value }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">	
				<%--<input type="file" class="ipttxt" name="file" id="uploadFile" /> <input
					class="comBtn" type="button" name="searchBtn" id="searchBtn"
					value="导入号码" onclick="importacion();" style="margin-right:10px;" />
				<input class="comBtn" type="button" name="downloadBtn" id="downloadBtn"
					value="下载模板" onclick="download();" style="margin-right:10px;" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			--%>
			<input class="comBtn" type="button" value="修改" onclick="modificar();" style="margin-right:20px;" /> 
			<input class="comBtn" type="button" onclick="numberPublishFun();" value="发&nbsp;布" style="margin-right:20px;"  /> 
			<input type="button" style="margin-right:20px;" class="comBtn" onclick="busquedar();" value="搜&nbsp;索" id="button" name="button">

		</div>
		

	</form>


	<form id="gridform" class="grid">

		<grid:grid from="webpage">

			<grid:header>
				<grid:cell width="10px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>号码</grid:cell>
				<grid:cell>批次号</grid:cell>
				<grid:cell>号码分组</grid:cell>
				<grid:cell>网别</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>地市</grid:cell>
				<grid:cell>发布状态</grid:cell>
				<grid:cell>归属销售组织</grid:cell>
				<grid:cell>靓号</grid:cell>
				<grid:cell>靓号类型</grid:cell>
				<grid:cell>减免金额</grid:cell>
				<grid:cell>预存款</grid:cell>
				<grid:cell>合约期</grid:cell>
				<grid:cell>最低消费</grid:cell>
				<grid:cell>付费方式</grid:cell>
			</grid:header>

			<grid:body item="objeto">
				<grid:cell>
						<input type="checkbox" name="eckBox" value="${objeto.no_id} " region_id="${objeto.region_id }" statustxt="${objeto.distribute_status }" isLucky="${objeto.is_lucky }" id="${objeto.dn_no }" />
				</grid:cell>
				<grid:cell>${objeto.dn_no } </grid:cell>
				<grid:cell>${objeto.batch_id } </grid:cell>
				<grid:cell>${objeto.group_name}  </grid:cell>
				<grid:cell>${objeto.no_gen}  </grid:cell>
				<grid:cell>${objeto.status } </grid:cell>
				<grid:cell>${objeto.region_name} </grid:cell>
				<grid:cell>${objeto.distribute_status}  </grid:cell>
				<grid:cell><span title="${objeto.grupos }">${fn:substring(objeto.grupos,0,10) }</span></grid:cell>
				<grid:cell>${objeto.is_lucky }  </grid:cell>
				<grid:cell>${objeto.no_classify }  </grid:cell>
				<grid:cell>${objeto.fee_adjust }  </grid:cell>
				<grid:cell>${objeto.deposit }  </grid:cell>
				<grid:cell>${objeto.period }  </grid:cell>
				<grid:cell>${objeto.lowest }  </grid:cell>
				<grid:cell>
				  <c:if test="${objeto.charge_type == '1' }">预付费</c:if>
				  <c:if test="${objeto.charge_type == '2' }">后付费</c:if>
			    </grid:cell>
			</grid:body>

		</grid:grid>

		<input type="hidden" name="join_suced" value=${join_suced }>
	</form>
</div>


<div id="grupos"></div>
<div id="modificar"></div>
<!-- 号码发布 -->
<div id="numbers_pub_dialog"></div>
<script>
	initDialog("grupos", false, "号码分组", "630px", "600px");
	initDialog("modificar", true, "号码修改", "600px", "350px");
	initDialog("numbers_pub_dialog", true, "号码发布", "400px", "500px");

	function importacion() {
	
		var fileName = document.getElementById("uploadFile").value;
		if (isNull(fileName)) {
			MessageBox.show("上传文件为空 ", 3, 2000);
			return;
		}
		var extPattern = /.+\.(xls|xlsx)$/i;

		if ($.trim(fileName) != "") {
			if (extPattern.test(fileName)) {
				$("#lists").val("yes");
				document.getElementById("uploadForm").submit();
			} else {
				MessageBox.show("只能上传EXCEL文件！", 2, 3000);
				return;
			}
		}
		$.Loading.show("正在导入号码，请耐心等待...");

	}

	function gruposCaja() {
		var url = "grupo!list.do?params.flag=choose";
		abrirCajaVentana("grupos", url);
	}
	
	function modificar() {

		var map = getEckValue("eckBox");
		var flag ="";
		if(map[0]>1){
			flag = $("#flag").val();
			if (flag == "" | flag === undefined | flag == undefined) {
				MessageBox.show("请先搜索号码状态", 3, 2000);
				return;
			}
		}

	
	
	
		if (map[0]<1) {
			MessageBox.show("请选择要操作的记录", 3, 2000);
			return;
		}


		var url = "numero!modificar.do?params.flag=" + flag + "&ids=" + map[1];
		abrirCajaVentana("modificar", url);
	}
	function numberPublishFun(){
		var nums="";
		//号码是否是靓号
		$(".gridbody").find("tbody").find("tr").each(function(i,data){
			if($(data).find("td:eq(0)").find("input[type='checkbox']").is(":checked")){
				var $obj = $(data).find("td:eq(0)").find("input[type='checkbox']");
				if($obj.attr("statustxt")=="未发布"){//只能发布未发布的号码
					nums+=$obj.attr("id")+",";//+"-"+isLuck+"-"+$obj.attr("region_id");
				}
			}
		});
		if(nums!=""){
			nums=nums.substring(0, nums.length-1);
			var url = "numero!numeroPubtree.do?busqueda=false&objectType=N&numbers="+nums;
			abrirCajaVentana("numbers_pub_dialog",url);
		}else{
			MessageBox.show("请选择“未发布”的号码进行发布操作", 3, 2000);
		}
	}
	
	function busquedar(){
		document.getElementById("uploadForm").submit();
	}
</script>