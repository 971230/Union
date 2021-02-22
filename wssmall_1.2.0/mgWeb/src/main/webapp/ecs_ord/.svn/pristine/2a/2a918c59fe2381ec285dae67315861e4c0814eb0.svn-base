<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.second select {  
    width: 152px;  
    height: 106px;  
    margin: 0px;  
    outline: none;  
    border: 1px solid #999;  
    margin-top: 22px; 
    background-color: white; 
}  
.second select option{
	background-color: inherit; 
}
.op{
	background-color: transparent; 
    bacground:tansparent;
    -webkit-appearance: none;
}
.second input {  
    width: 150px;  
    top: 0px;  
    outline: none;  
    border: 0pt;  
    position: absolute;  
     line-height: 22px;   
    /* left: 8px; */  
    height: 22px;  
    border: 1px solid #CECECE;  
    background-color: #FFFFFF; 
    
     margin-top:4px; 
}  
.second ul {  
    position: absolute;  
    top: 27px;  
    border: 1px solid #999;  
    left: 8px;  
    width: 125px;  
    line-height: 16px;  
}  
.ul li{  
    list-style: none;  
    width: 161px;  
    /* left: 15px; */  
    margin-left: -40px;  
    font-family: 微软雅黑;  
    padding-left: 4px;  
}  
</style>
<div>
	<form method="post" id="serchUserform" action='ordAuto!newQueryPersonnel.do' ajax="yes">
	 	<div class="searchformDiv">
		 	<table>
				 <c:if test="${allotType != 'return' && allotType != 'yxd' && allotType != 'yxdreturn' }">
					<tr>
						<th>分配类型：</th>
						<td>
						<select id="selectType">
								<option value="personnel">人员</option>
								<c:if test="${is_work_custom==1}">
								<option value="group">团队</option>
								</c:if>
						</select>
						</td>
					</tr>
					<!-- 人员分配 -->
					<tr id="trPersonnel">
					<th>关键字：</th>
					<td>
						<input type="text" class="ipttxt" id="userparams"   value="${userparams}"/>
						<input type="text" class="ipttxt" id="lockOrderIdStr"  value="${lockOrderIdStr}" style="display :none;"/>
					</td>
				    <c:if test="${allotType == 'allot' }">
					    <th>县分：</th>
					    <td style="position: absolute;">
							<span class="second">  
							    <input type="text" name="allot_county_name" id="makeupCo" class="makeinp ipt_new" onfocus="setfocus(this)" oninput="setinput(this);" placeholder=""  autocomplete="off" value="${allot_county_name}"/>  
							    <select name="allot_county_id" id="typenum"  onchange="changeF(this)" size="10" style="display:none;">  
							    	<c:forEach items="${countyList}" var="ds">
			               				<option value="${ds.field_value}" ${ds.field_value==allot_county_id?'selected':''}>${ds.field_value_desc}</option>
			               			</c:forEach>
							    </select>  
							</span>  
						</td>
						<th> </th>
						<td>
						    <input type="button" style="margin-left:100px;"  class="comBtn schConBtn" id="selUserBtn" value="搜&nbsp;&nbsp;索" />
						    <input type="button" style="margin-left:5px;"  class="comBtn schConBtn" value="确&nbsp;&nbsp;定"  id="updateLockBtn">
				    	</td>
				    </c:if>
					<th></th>
					<td>
						<c:if test="${allotType == 'query' }">
							<input style="width:40px;"  class="comBtn schConBtn" id="qryUserBtn" value="搜索" />
						    <input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定"  id="queryLockIdBtn">
					    </c:if>
				   		<!-- <a href="javascript:void(0)" id="search_btn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a> -->
					</td>
				</tr>
				<!-- 团队分配 -->
				
					<tr id="trGroup">
						<th>层级：</th>
						<td>
						<select id="search_team_level" class="table_select">
								<option value=""></option>
								<option value="province">省公司</option>
								<option value="region">地市</option>
								<option value="county">县分</option>
						</select>
						</td>
						<th>团队编号</th>
						<td><input  type="text" id="team_id" class="ipttxt"/></td>
						<th>团队名称</th>
						<td><input type="text" id="team_name" class="ipttxt"/></td>
						<th></th>
						<td>
						<input type="button"style="margin-left: 5px; "class="comBtn schConBtn" id="selUserTeam" value="搜&nbsp;&nbsp;索" />
						<input type="button" style="margin-left: 5px; "class="comBtn schConBtn" value="确&nbsp;&nbsp;定" id="updateLockBtnTeam"></td>
					</tr>
				</c:if>
				
				<c:if test="${allotType == 'yxd' }">
					<!-- 人员分配 -->
					<tr id="trPersonnel">
					<th>关键字：</th>
					<td>
						<input type="text" class="ipttxt" id="userparams"   value="${userparams}"/>
						<input type="text" class="ipttxt" id="lockOrderIdStr"  value="${lockOrderIdStr}" style="display :none;"/>
					</td>
					    <th>县分：</th>
					    <td style="position: absolute;">
							<span class="second">  
							    <input type="text" name="allot_county_name" id="makeupCo" class="makeinp ipt_new" onfocus="setfocus(this)" oninput="setinput(this);" placeholder=""  autocomplete="off" value="${allot_county_name}"/>  
							    <select name="allot_county_id" id="typenum"  onchange="changeF(this)" size="10" style="display:none;">  
							    	<c:forEach items="${countyList}" var="ds">
			               				<option value="${ds.field_value}" ${ds.field_value==allot_county_id?'selected':''}>${ds.field_value_desc}</option>
			               			</c:forEach>
							    </select>  
							</span>  
						</td>
						<td>
						    <input type="button" style="margin-left:338px;"  class="comBtn schConBtn" id="selUserBtn" value="搜&nbsp;&nbsp;索" />
						    <input type="button" style="margin-left:5px;"  class="comBtn schConBtn" value="确&nbsp;&nbsp;定"  id="updateLockBtn">
				    	</td>
				</tr>
				<!-- 团队分配 -->
				</c:if>
		 </table>
	  </div>
	</form>
		<input type="hidden" id="allotType"  value="${allotType}" />
	    <input type="hidden" id="county_code1" value="${county_code}"/>
	    <input type="hidden" id="county_code" value="${county_code}"/>
	    <input type="hidden" id="is_work_custom" value="${is_work_custom}">
	    
	<c:if test="${allotType == 'return' || allotType == 'yxdreturn'}">
		<div>
			<input type="text" class="ipttxt" id="lockOrderIdStr"  value="${lockOrderIdStr}" style="display :none;"/>
			<font>回退原因:</font> 
			<input type="text" class="ipttxt" id="remarks"  />
			<input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定"  id="updateLockBtn">
		</div>
	</c:if>
	
	<c:if test="${allotType != 'return' && allotType !='yxdreturn'}">
		<input type="text" class="ipttxt" id="remarks" style="display :none;" />
	</c:if>
	<div >
		<div id="divfromGroup">
			<iframe src="" id="fromGroup" name="fromGroup" style="width: 100%; height: 100%;"></iframe>
		</div>
		<div id="divfromPersonnel">
			<iframe src="" id="fromPersonnel"  style="width: 100%; height: 450px;"></iframe>
		</div>
	</div>
</div>

<!-- var cobj = $("input[type='radio'][name='radionames']:checked"); -->
<script type="text/javascript">
//页面初始化
	$(document).ready(function() {
		console.log("页面初始化加载。。。。。。。。");
		var allotType = $("#allotType").val();
		var lockOrderIdStr = $("#lockOrderIdStr").val();
	    var county_code = $("#county_code1").val();
	    var allot_county_name = $("#makeupCo").val();
	    console.log("订单分配人员county_code："+county_code);		
	    var is_work_custom = $("#is_work_custom").val();
		var url = ctx
				+ "/shop/admin/ordAuto!newQueryPersonnel.do?ajax=yes&allotType="+allotType
						+"&lockOrderIdStr="+lockOrderIdStr
						+"&is_work_custom="+is_work_custom
						+"&county_code="+county_code
						+"&allot_county_name="+allot_county_name;
		console.log("订单分配人员："+url);		
		$("#fromPersonnel").attr("src", url );
		$("#trGroup").hide();
		$("#divfromGroup").hide();
	});
	//分配类型
	$("#selectType").change(function() {
		//获取类型
		var type = $("#selectType").val();
		
		var allotType = $("#allotType").val();
		var lockOrderIdStr = $("#lockOrderIdStr").val();
	    var county_code = $("#county_code").val();
	    var is_work_custom = $("#is_work_custom").val();
		//团队URL
		var urlGroup = ctx+ "/shop/admin/ordAuto!newQueryGroup.do?ajax=yes"
		+"&lockOrderIdStr="+lockOrderIdStr
		+"&is_work_custom="+is_work_custom
		+"&county_code="+county_code;
		//人员URL
		var urlPersonnel = ctx
				+ "/shop/admin/ordAuto!newQueryPersonnel.do?ajax=yes&allotType="+allotType
						+"&lockOrderIdStr="+lockOrderIdStr
						+"&is_work_custom="+is_work_custom
						+"&county_code="+county_code;
		if ("group" == type) {
			$("#trGroup").show();
			$("#trPersonnel").hide();
			$("#divfromGroup").show();
			$("#divfromPersonnel").hide();
			$("#fromGroup").attr("src", urlGroup);
		}
		if ("personnel" == type) {
			$("#trGroup").hide();
			$("#trPersonnel").show();
			$("#divfromGroup").hide();
			$("#divfromPersonnel").show();
			$("#fromPersonnel").attr("src", urlPersonnel);
		}
	});
	
	// 团队搜索功能
	$("#selUserTeam").click(function() {
		var team_level = $("#search_team_level").val();
		var team_id = $("#team_id").val();
		var team_name=$("#team_name").val();
		var urlGroup = ctx + "/shop/admin/ordAuto!newQueryGroup.do?ajax=yes&team_level=" + team_level
		 +"&team_id=" + team_id 
		 +"&team_name=" + team_name;
	  $("#fromGroup").attr("src", urlGroup);
	});
	
	//团队 
	var groupJSon={};
	
	// 团队分配确定按钮
	$("#updateLockBtnTeam").click(function(){
		  //调取子类方法
        var v= $("#fromGroup")[0].contentWindow.CheckedPersonnel();
        if(v!="1"){
     	   return false ;
        }
        var team_name=groupJSon.team_name;
        var team_id=groupJSon.team_id;
        var lockOrderIdStr=groupJSon.lockOrderIdStr;
        var allotType="group";
        
        var urlGroup = ctx + "/shop/admin/ordAuto!updateLock.do?ajax=yes" 
		 +"&team_id=" + team_id 
		 +"&team_name=" + team_name
		 +"&lockOrderIdStr="+lockOrderIdStr
		 +"&allotType="+allotType;
        console.log("团队分配URL:" + urlGroup);
        $.ajax({
			type: "POST", //提交方式  
			url: urlGroup, //路径
			data:{},
			dataType : "json",//数据，这里使用的是Json格式进行传输  
			success: function(result) { //返回数据根据结果进行相应的处理  
				if(result.result==0) {
					alert(result.message);
				} else {
					alert(result.message);
				}
			}
		});
        
	});
	
	
	// 子页面JSON 所有员工;
	var tempJson={};	
	//输入显示关联
	var TempArr = [];//存储option 
	var TempValArr = [];//存储option 
	/*先将数据存入数组*/

	$("#typenum option").each(function(index, el) {
		TempArr[index] = $(this).text();
		TempValArr[index] = $(this).val();
	});
	$(document).bind('click', function(e) {
		var e = e || window.event; //浏览器兼容性     
		var elem = e.target || e.srcElement;
		while (elem) { //循环判断至跟节点，防止点击的是div子元素     
			if (elem.id && (elem.id == 'typenum' || elem.id == "makeupCo")) {
				return;
			}
			elem = elem.parentNode;
		}
		$('#typenum').css('display', 'none'); //点击的不是div或其子元素     
	});

	function changeF(this_) {
		$(this_).prev("input").val($(this_).find("option:selected").text());
		$("#typenum").css({
			"display" : "none"
		});
	};

	function setfocus(this_) {
		$("#typenum").css({
			"display" : ""
		});
		/* var select = $("#typenum");
		for (i = 0; i < TempArr.length; i++) {
			var option = $("<option class='op'></option>").text(TempArr[i])
					.val(TempValArr[i]);
			select.append(option);
		} */
	}; 

	function setinput(this_) {
		var select = $("#typenum");
		select.html("");
		select.empty();
		for (i = 0; i < TempArr.length; i++) {
			var value = this_.value;
			value = value.replace(/(^\s*)|(\s*$)/g, "");
			//若找到以txt的内容开头的，添option  
			if (TempArr[i].indexOf(value) >= 0) {
				var option = $("<option class='op'></option>").text(TempArr[i])
						.val(TempValArr[i]);
				select.append(option);
			}

		}
	};
	//输入显示关联 --end
	
	function getValue(value){
		var v="";
		
		if(typeof(value)!="undefined" && null!=value){
			v = value;
		}
		
		return v;
	}

	$(function() {
		$("#selUserBtn")
				.click(
						function() {
							var userparams = $("#userparams").val();
							userparams = encodeURI(encodeURI(userparams));
							var lockOrderIdStr = $("#lockOrderIdStr").val();
							var allot_county_name = $("input[name='allot_county_name']").val();
							var allot_county_id = getValue($("select[name='allot_county_id']").val());
							var url = ctx
									+ "/shop/admin/ordAuto!newQueryPersonnel.do?ajax=yes&allotType=allot&userparams="
									+ userparams + "&lockOrderIdStr="
									+ lockOrderIdStr + "&allot_county_name="
									+ allot_county_name + "&allot_county_id="
									+ allot_county_id;

							$("#fromPersonnel").attr("src", url );
						});

		$("#qryUserBtn")
				.click(
						function() {
							var userparams = $("#userparams").val();
							userparams = encodeURI(encodeURI(userparams));
							var lockOrderIdStr = $("#lockOrderIdStr").val();
							var url = ctx
									+ "/shop/admin/ordAuto!newQueryPersonnel.do?ajax=yes&allotType=query&userparams="
									+ userparams + "&lockOrderIdStr="
									+ lockOrderIdStr;

							$("#fromPersonnel").attr("src", url );
						});

		$("#queryLockIdBtn").click(function() {
			var obj = $("[name='lockUserChk']:checked");
			var lock_user_id = null;
			lock_user_id = obj.attr("userid");
			username = obj.attr("username");
			if (username == null || username.length == 0) {
				document.getElementById("username").value = "";
				document.getElementById("lock_user_id").value = "";
			} else {
				document.getElementById("username").value = username;
				document.getElementById("lock_user_id").value = lock_user_id;
			}

			Eop.Dialog.close("queryUserListDlg");
		});

		$("#updateLockBtn").unbind("click").bind("click",
						function() {
			                   //调取子类方法
			                  var v= $("#fromPersonnel")[0].contentWindow.CheckedPersonnel();
			                   if(v!="1"){
			                	   return false ;
			                   }
			                    var lockOrderIdStr = tempJson.lockOrderIdStr;
			    				var userid = tempJson.userid;
			    				var realname = tempJson.realname;
								var allotType = $("#allotType").val();
								var remarks = $("#remarks").val();
								var col5 = tempJson.col5;
								var permission_level = tempJson.permission_level;
								$.ajax({
											type : "post",
											async : true,
											url : ctx
													+ "/shop/admin/ordAuto!updateLock.do?ajax=yes&allotType="
													+ allotType
													+ "&userid="
													+ userid
													+ "&realname="
													+ encodeURI(encodeURI(realname))
													+ "&lockOrderIdStr="
													+ lockOrderIdStr
													+ "&remarks=" + remarks
													+ "&col5=" + col5
													+ "&permission_level=" + permission_level,
											data : {},
											dataType : "json",
											success : function(data) {
												alert(data.message);
												Eop.Dialog
														.close("queryUserListDlg");
												//location.href = ctx+"/shop/admin/ordAuto!AllotOrderList.do";
												//获取显示数据表单a标签的文本 的页码 <a class="selected">2</a>
												var obj = $("a[class='selected']");
												var pageraw = obj.text();
												var page = pageraw.substring(0,
														pageraw.length - 1);
												var link = ctx
														+ "/shop/admin/ordAuto!NewAllotOrderList.do?"
														+ "page=" + page;
												$("#serchform").attr('action',
														link);
												$("#serchform").submit();//用于修改后刷新显示锁定人
												var linkdefault = ctx
														+ "/shop/admin/ordAuto!NewAllotOrderList.do";
												$("#serchform").attr('action',
														linkdefault);//恢复ACTION到默认
											}

										});
							
						});

	});
</script>