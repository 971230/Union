<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<style>
.mutil_info{
	padding-left:10px;
	text-align:left;
}
</style>
<div>
	<form action="${ctx}/shop/admin/specvalue!list.do" method="post" id="searchNoSegForm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <%--<th>接口名称：</th>
	    	      <td>
	    	           <html:selectdict name="params.region_id" curr_val="${params.region_id }"
	    	              attr_code="DC_COMMON_REGION_GUANGDONG" style="width:150px" 
					      appen_options='<option value="">--请选择--</option>'></html:selectdict>
	    	      </td> --%>
	    	      <th>关键字名称：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="inner.match_content" value="${inner.match_content}" /> 
	    	      </td>	
	    	      <th>搜索id：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="inner.search_id" value="${inner.search_id}" /> 
	    	      </td>
	    	      <th>搜索编码：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="inner.search_code" value="${inner.search_code}" /> 
	    	      </td>     
				 </tr>
				 <tr>
				 	<th>时间段：</th>
					<td>
						<input type="text" name="inner.start_date"
							value="${inner.start_date }" id="start_date" readonly="readonly"
							class="dateinput ipttxt" style="width:90px;" dataType="date" />
						-
						<input type="text" name="inner.end_date"
							value="${inner.end_date }" id="end_date" readonly="readonly"
							class="dateinput ipttxt" style="width:90px;" dataType="date" />
					</td>
					<th>是否归类：</th>
		    	      <td>
		    	          <select name="inner.catalog">
		    	          	<option value="all" <c:if test="${inner.catalog == 'all' }">selected</c:if> >全部</option>
		    	          	<option value="yes" <c:if test="${inner.catalog == 'yes' }">selected</c:if> >已归类</option>
		    	          	<option value="no" <c:if test="${inner.catalog == 'no' }">selected</c:if> >未归类</option>
		    	          </select>
		    	      </td>	
		    	      <th></th>
		    	      <td>
		    	          <input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
		    	      </td> 		
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>
	  	 
	  	 <%--<div class="comBtnDiv">
	  	     <input class="comBtn" type="button" id="searchBtn" value="添加" style="margin-right:10px;"
				onclick="window.location.href='${ctx}/shop/admin/keyrule!toAdd.do'" />
		</div>		 --%>	
	</form>
	<div class="right_warp">
    <form id="theForm" class="grid">
	<grid:grid from="webpage" formId="searchNoSegForm" action="${ctx}/shop/admin/specvalue!list.do">
	    <grid:header>
	    	<%-- <input type="hidden" name="inner.match_content" value="${inner.match_content}" />
			<input type="hidden" name="inner.catalog" value="${inner.catalog}" />
			<input type="hidden" name="inner.start_date" value="${inner.start_date}" />
			<input type="hidden" name="inner.end_date" value="${inner.end_date}" />--%>
		    <grid:cell style="width:280px;">搜索编码</grid:cell>
		    <grid:cell style="width:200px;">搜索id|关键字id</grid:cell>
		    <grid:cell>关键字名称</grid:cell>
		    <grid:cell style="width:110px;">归类</grid:cell>
		    <grid:cell style="width:130px;text-align:left;">预警信息</grid:cell>
		    <grid:cell style="width:150px;text-align:left;">超时提醒</grid:cell>
		    <grid:cell style="width:130px;">创建时间</grid:cell>
			<grid:cell style="width:120px;">操作</grid:cell>
		</grid:header>

		<grid:body item="objeto">
		    <grid:cell>${objeto.search_code}</grid:cell>
		    <grid:cell>${objeto.search_id }|${objeto.key_id }</grid:cell>
		    <grid:cell>
		    	<%-- ${objeto.match_content }--%>
		    	<div title="<c:out value=" ${objeto.match_content} " />" style="width:100px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;text-align:center;">
		            <c:out value=" ${objeto.match_content} " />
				</div>
                    
		    </grid:cell>
		    <grid:cell>
		    	${objeto.catalog_name }
		    </grid:cell>
		    <grid:cell>
                <div>
                 	<div class="mutil_info"><%-- 是否预警：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                 		<c:if test="${objeto.warming_flag eq 'Y' }">预警</c:if>
                 		<c:if test="${objeto.warming_flag eq 'N'}">不预警</c:if>
                 	</div>
                 	<c:if test="${(!empty objeto.warming_limit) && objeto.warming_limit != '0'}">
                 		<div class="mutil_info">时长（小时）：${objeto.warming_limit }</div>
                 	</c:if>
                 </div>
		    </grid:cell>
		    <grid:cell>
                <div>
                 	<div class="mutil_info"><%--是否超时预警：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
                 		<c:if test="${objeto.timeout_flag eq 'Y' }">超时提醒</c:if>
                 		<c:if test="${objeto.timeout_flag eq 'N'}">超时不提醒</c:if>
                 	</div>
                 	<c:if test="${(!empty objeto.timeout_limit) && objeto.timeout_limit != '0'}">
                 		<div class="mutil_info">时长（小时）：${objeto.timeout_limit }</div>
                 	</c:if>
                 </div>
		    </grid:cell>
		    <grid:cell>${fn:substring(objeto.create_time , 0 , 19)}</grid:cell>
			<grid:cell>
				<a href="javascript:void(0);" onclick="load_catalog('${objeto.key_id}')">归类</a>|
				<a href="${ctx }/shop/admin/specvalue!toUpdate.do?inner.key_id=${objeto.key_id }">修改</a>
				<c:if test="${objeto.match_content eq '未匹配到关键字(缺省)' }">
					|<a href="javascript:void(0);" onclick="window.location.href='${ctx}/shop/admin/keyrule!orderexpList.do?inner.key_id=${objeto.key_id }&inner.search_id=${objeto.search_id }';">重新抽取</a>
				</c:if>
				<c:if test="${not empty objeto.match_content }">
					<br/>
					<a href="javascript:void(0);" onclick="change_specvalue_update('${objeto.search_id}','${objeto.key_id }')">更新到文件系统</a>
				</c:if>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    </div>
<div style="clear:both;padding-top:5px;"></div>
</div>
<%--归类信息弹框  --%>
<div id="select_catalog_dialog"></div>
<%--归类列表框弹框 --%>
<div id="esearchCatalogListDialog"></div>
<%--关键字更新到文件系统进度条弹框 --%>
<div id="un_check_specvalue_update_waiting_dialog"></div>
<script>
	var interval;//定时任务实例

	$(function(){
		 Eop.Dialog.init({id:"select_catalog_dialog",modal:true,title:"关键字归类",width:'450px'});
		 Eop.Dialog.init({id:"esearchCatalogListDialog",modal:true,title:"归类信息",width:'550px'});
		 Eop.Dialog.init({id:"un_check_specvalue_update_waiting_dialog",modal:true,title:"弹出框",width:'350px'});
	});
	
	//初始化归类弹框
	function load_catalog(key_id){
		var url= ctx+"/shop/admin/specvalue!catalogList.do?ajax=yes&show_type=dialog&inner.key_id=" + key_id;
		$("#select_catalog_dialog").html("loading...");
		 $("#select_catalog_dialog").load(url,{},function(){});
		 Eop.Dialog.open("select_catalog_dialog");
	}
	//归类成功回调方法  参数result：关键字归类action返回的数据
	function after_update_catalog(result){
		 alert("关键字归类成功！");
		 //window.location.href = ctx + '/shop/admin/specvalue!list.do';
	}
	//关键字更新到文件系统
	function change_specvalue_update(search_id,key_id,match_content){
		if(confirm("确认修改异常单在文件系统中的关键字？")){			
			$.ajax({
				type: "POST",
				 url: ctx + "/shop/admin/specvalue!changeSpecvalueUpdate.do",
				 data: "ajax=yes&inner.search_id="+search_id+"&inner.key_id="+key_id,
				 dataType:"json",
				 success: function(result){
					 
					 if (result.result == '0') {
						 alert(result.message);
				     } else {
				    	 alert(result.message);
				     }
				 },
				 error:function(){
					 alert("操作失败，请重试");
				 }
			});
		}
	}
	//未匹配关键字异常更新
	function un_check_specvalue_update(search_id,key_id,search_code){
		//弹框处理
		progress_dialog();
		
		$.ajax({
			type: "POST",
			 url: ctx + "/shop/admin/specvalue!unCheckSpecvalueUpdate.do",
			 data: "ajax=yes&inner.search_id="+search_id+"&inner.key_id="+key_id+"&inner.search_code="+search_code,
			 dataType:"json",
			 success: function(result){
				 if (result.result == '0') {
					 //alert(result.message);
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
	
	//进度弹框
	function progress_dialog(){
		$("#un_check_specvalue_update_waiting_dialog").html("正在处理，请稍后...(0%)");
		Eop.Dialog.open("un_check_specvalue_update_waiting_dialog");
		interval = setInterval(un_check_specvalue_update_progress, 1000);//读取进度
	}
	
	//监听未匹配关键字异常更新进度
	function un_check_specvalue_update_progress(){
		$.ajax({
			type: "POST",
			 url: ctx + "/shop/admin/specvalue!unCheckSpecvalueUpdateProgress.do",
			 data: "ajax=yes",
			 dataType:"json",
			 success: function(result){
				 if (result.result == '0') {
					 
					 if(result.percent == '100'){
						 $("#un_check_specvalue_update_waiting_dialog").html("处理完成!");
						 clearInterval(interval);//清除
					 }else{
						 $("#un_check_specvalue_update_waiting_dialog").html("正在处理，请稍后...("+result.percent+"%)"); 
					 }
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}

</script>