<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div>
	<div>
		<%-- <a class="graybtn" href="javascript:void(0);" cond_type="cal_cond" name="add_plan_cond" ><i class="ic_add"></i>添加</a>
		<div id="search_plan_cond">
			<c:if test="${!empty planCondList}">
				<c:forEach items="${planCondList }" var="planCond">
					<div class="ptit" 
						 id="${planCond.attr_id }" 
						 name="planCondDiv" 
						 obj_code="${planCond.obj_code }" 
						 attr_code="${planCond.attr_code }"
						 attr_name="${planCond.attr_name }"
						 ele_type="${planCond.ele_type }">
						<select id="is_include_${planCond.attr_code }" select_attr_name="${planCond.attr_name }">
							<option value="-1">--请选择--</option>
							<option value="1" <c:if test="${planCond.is_include==1 }">selected</c:if>>匹配</option>
							<option value="0" <c:if test="${planCond.is_include==0 }">selected</c:if>>不匹配</option>
						</select>
						<span class="name">${planCond.attr_name }：</span>
						<c:forEach items="${planCond.values }" var="values">
							<label id="">
								<input type="${planCond.ele_type }" 
									value="${values.value }" 
									checked="checked"
									obj_code="obj_code_${planCond.obj_code }" 
									attr_code="attr_code_${planCond.attr_code }" 
									attr_name="${values.name }"/>${values.name }
							</label>
						</c:forEach>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<a class="blueBtns" id="save_plan_cond" href="javascript:void(0);"><span>保存</span></a> --%>
		<div>
			<c:if test="${!empty ruleObjList}">
				<c:forEach items="${ruleObjList }" var="ruleObj">
					<c:forEach items="${ruleObj.objAttrList}" var="objAttr">
							<input
								id="t_${ruleObj.obj_code }_${objAttr.attr_code}"
								class="resigterIpt _x_ipt"
								type="text"
								dataType="string"
								<c:if test="${!empty planCondList}">
								<c:forEach items="${planCondList }" var="planCondVo">
									<c:choose>
										<c:when test="${objAttr.attr_code == planCondVo.attr_code }">
											value="${planCondVo.attr_value }"
											style="display: inline-block;"
										</c:when>
									</c:choose>
								</c:forEach>
								</c:if>
								<c:if test="${empty planCondList }">
											value="${objAttr.attr_code }"
								</c:if>
								value="${objAttr.attr_code }"
								style="display: none;"
								/>
							<label>
							<input
								id="c_${ruleObj.obj_code }_${objAttr.attr_code}"
								type="checkbox"
								obj_id="${ruleObj.obj_id }"
								obj_code="${ruleObj.obj_code }"
								obj_name="${ruleObj.obj_name }"
								attr_id="${objAttr.attr_id }"
								attr_code="${objAttr.attr_code }"
								attr_name="${objAttr.attr_name }"
								ele_type="${objAttr.ele_type }"
								<c:forEach items="${planCondList }" var="planCondVo">
								<c:if test="${objAttr.attr_code == planCondVo.attr_code }">
									checked="checked"
								</c:if>
								</c:forEach>
								/>${objAttr.attr_name }
							</label>
						<br/>
					</c:forEach>
				</c:forEach>
			</c:if>
		</div>
		<a class="blueBtns" id="savePlanFilterCond" href="javascript:void(0);"><span>保存</span></a>
		<input type="hidden" id="cond_plan_id" value="${cond_plan_id }" />
		<%-- <input type="hidden" id="dir_id" value="${dir_id }" /> --%>
	</div>
</div>
<script type="text/javascript">
$(function() {
	
	$("input[type='checkbox']").unbind().bind("click", function() {
		var $this = $(this);
		var obj_code = $this.attr("obj_code");
		var attr_code = $this.attr("attr_code");
		var t_id = "#t_"+obj_code+"_"+attr_code;
		if ($this.attr("checked")) {
			$(t_id).css("display", "inline-block");
		} else {
			$(t_id).css("display", "none");
		}
	});
	
	$("#savePlanFilterCond").unbind().bind("click", function() {
		 // 所有被选中的属性
		var checkboxs = $("input[type='checkbox']:checked");
		var plan_condss = "["; // 组装JSON数据字符创
		for (var i =0; i<checkboxs.length; i ++) {
			var plan_cond = "";
			var obj_id = $(checkboxs[i]).attr("obj_id");
			var obj_code = $(checkboxs[i]).attr("obj_code");
			var obj_name = $(checkboxs[i]).attr("obj_name");
			var attr_id = $(checkboxs[i]).attr("attr_id");
			var attr_code = $(checkboxs[i]).attr("attr_code");
			var attr_name = $(checkboxs[i]).attr("attr_name");
			var ele_type= $(checkboxs[i]).attr("ele_type");
			var checkbox_id = "#c_"+obj_code+"_"+attr_code;
			var text_id = "#t_"+obj_code+"_"+attr_code;
			var text_value = $(text_id).val();
			if (text_value == "") { // 选中的属性必须同时选中值
				alert("请输入【"+attr_name+"】条件值！");
				plan_condss = "";
				return;
			}
			plan_cond = "{\"obj_id\":\""+obj_id+"\","+
						"\"obj_code\":\""+obj_code+"\","+
						"\"obj_name\":\""+obj_name+"\","+
						"\"attr_id\":\""+attr_id+"\","+
						"\"attr_code\":\""+attr_code+"\","+
						"\"attr_name\":\""+attr_name+"\","+
						"\"ele_type\":\""+ele_type+"\","+
						"\"attr_value\":\""+text_value+"\","+
						"\"is_include\":\""+1+"\"}";
			if (i != checkboxs.length - 1) {
				plan_condss += plan_cond+",";
			} else {
				plan_condss += plan_cond;
			}
		}
		plan_condss += "]";
		$.ajax({
			type : "post",
			async : false,
			url : "ruleManager!savePlanCond.do?ajax=yes",
			data : {"cond_plan_id":$("#cond_plan_id").val(),"plan_cond":plan_condss},
			dataType : "json",
			success : function(data) {
				data = eval(data);
				alert(data.message);
			}
		});
	});
	$("a[name='add_plan_cond']").unbind().bind("click", function() {
		Eop.Dialog.open("show_search_dlg");
		var plan_id = $("#cond_plan_id").val();
		var url = ctx + "/shop/admin/ruleManager!getSearchFactList.do?ajax=yes&dir_id=&cond_plan_id="+plan_id;
		var rule_id = $(this).attr('rule_id');
		var cond_type = $(this).attr('cond_type');
		$("#show_search_dlg").load(url,{"source":"search_plan_cond"}, function() {
			var divs = $("div[name='planCondDiv']");
			for (var index=0; index<divs.length; index ++) {
				var element = divs[index];
				var values = $(element).find("input:checked");
				var attr_id = $(element).attr("attr_code");
				var seleceds = $("select[id='is_include_"+attr_id+"']");
				$("input[search_attr_code='"+attr_id+"']").attr("checked", true);
				$("input[search_attr_code='"+attr_id+"']").click();
				$("input[search_attr_code='"+attr_id+"']").attr("checked", true);
				var search_seleceds = $("select[id='is_include_"+attr_id+"']");
				for (var m =0; m<seleceds.length; m ++) {
					$("select[id='is_include_"+attr_id+"']").val($(seleceds[m]).val());
				}
				var search_attr_values = $("input[attr_code='attr_code_"+attr_id+"']");
				for (var n=0; n<search_attr_values.length; n ++) {
					var search_attr_value = search_attr_values[n];
					for (var j = 0; j<values.length; j ++) {
						if ($(search_attr_value).attr("value") == $(values[j]).attr("value")) {
							$(search_attr_value).attr("checked", true);
						}
					}
				}
			}
		});
	});
	$("a[id='save_plan_cond']").unbind().bind("click", function() {
		var plan_id = $("#cond_plan_id").val();
		var array_div = $("div[name='planCondDiv']");
		var plan_cond = "";
		if (array_div!=null && array_div.length > 0) {
			plan_cond += "\[";
			for (var i=0; i<array_div.length; i ++) {
				plan_cond += "\{"; // 组装每一个属性的JSON对象
				var arr_div = array_div[i];
				var obj_code = $(arr_div).attr("obj_code"); // fact对象code
				var attr_code = $(arr_div).attr("attr_code"); // fact对象属性code
				var attr_name = $(arr_div).attr("attr_name"); // fact对象属性名称
				var is_include = $("#is_include_"+attr_code).val(); // 是否包含此属性
				if (is_include == "-1") {
					alert("请选择【"+attr_name+"】是否匹配！"); return;
				}
				var ele_type = $(arr_div).attr("ele_type");
				plan_cond += "\"obj_code\":\""+obj_code+"\",";
				plan_cond += "\"attr_code\":\""+attr_code+"\",";
				plan_cond += "\"attr_name\":\""+attr_name+"\",";
				plan_cond += "\"is_include\":\""+is_include+"\",";
				plan_cond += "\"ele_type\":\""+ele_type+"\",";
				/* plan_cond += "'attr_code':'"+attr_code+"',";
				plan_cond += "'attr_name':'"+attr_name+"'";
				plan_cond += "'is_include':'"+is_include+"',";
				plan_cond += "'ele_type':'"+ele_type+"'"; */
				var attr_values = $("input[attr_code='attr_code_"+attr_code+"']:checked"); // 属性集合
				var arr_value = "values:\[";
				if (attr_values.length <= 0) {
					alert("请选择【"+attr_name+"】属性值！");return;
				}
				for (var j =0; j<attr_values.length; j++) {
					var input_values = attr_values[j];
					var input_value = ($(input_values).attr("value"));
					var input_name = ($(input_values).attr("attr_name"));
					if (j != attr_values.length -1) {
						arr_value += "\{\"value\":\""+input_value+"\",\"name\":\""+input_name+"\"\},"; // 属性值
					} else {
						arr_value += "\{\"value\":\""+input_value+"\",\"name\":\""+input_name+"\"\}"; // 属性名
					}
				}
				arr_value += "\]";
				plan_cond += arr_value;
				if (i != array_div.length -1) {
					plan_cond += "\},";
				} else {
					plan_cond += "\}";
				}
			}
			plan_cond += "\]";
			alert("plan_cond : " + plan_cond);
			$.ajax({
				type : "post",
				async : false,
				url : "ruleManager!savePlanCond.do?ajax=yes",
				data : {"cond_plan_id":plan_id,"plan_cond":plan_cond},
				dataType : "json",
				success : function(data) {
					data = eval(data);
					alert(data.message);
				}
			});
		}
	});
});
</script>