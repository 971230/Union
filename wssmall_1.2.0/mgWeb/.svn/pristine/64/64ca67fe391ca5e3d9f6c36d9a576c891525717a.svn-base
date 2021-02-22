<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<div>
	<form action="${ctx}/shop/admin/keyrule!orderexpList.do" method="post" id="searchNoSegForm">
		<input type="hidden" name="inner.search_id" value="${inner.search_id }"/>
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>异常单实例id：</th>
	    	      <td>
	    	           <input type="text" class="ipttxt" name="inner.excp_inst_id" value="${inner.excp_inst_id}" /> 
	    	      </td>
	    	      <th>关键字值：</th>
	    	      <td>
	    	           <input type="text" class="ipttxt" name="inner.match_content" value="${inner.match_content}" /> 
	    	      </td>
	    	      <th>外部单号：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="inner.out_tid" value="${search_name}" /> 
	    	      </td>	
	    	      <th></th>
	    	      <td></td>
	    	      <th></th>
	    	      <td></td>       
				 </tr>
				 <tr>
				 	  <th>有无关键字：</th>
		    	      <td>
		    	      		<select name="inner.has_match_content" style="width:174px" class="ipt_new">
		    	      			<option value="Y" <c:if test="${inner.has_match_content == 'Y' || empty inner.has_match_content }">selected</c:if> >有</option>
		    	      			<option value="N" <c:if test="${inner.has_match_content == 'N' }">selected</c:if> >无</option>
		    	      		</select>
		    	      </td> 
		    	      <th><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button"></th>		
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>
	  	 
	  	 <div class="comBtnDiv">
	  	    <input class="comBtn" type="button" id="searchBtn" value="重新抽取关键字" style="margin-right:10px;"
				onclick="commit_data();" />
			<input class="comBtn" type="button" id="" value="返  回" style="margin-right:10px;"
				onclick="window.location.href='${ctx}/shop/admin/keyrule!list.do'" />
		</div>			
	</form>
	<div class="right_warp">
    <form id="theForm" action="${ctx}/shop/admin/keyrule!orderexpList.do" class="grid">
	<grid:grid from="webpage" formId="searchNoSegForm" action="${ctx}/shop/admin/keyrule!orderexpList.do">
	    <grid:header>
	   		<grid:cell width="10px">
				<input type="checkbox" id="toggleChk" />
			</grid:cell>
		    <grid:cell width="50px">序号</grid:cell>
		    <grid:cell width="180px">异常单实例id</grid:cell>
		    <grid:cell width="100px">搜索id</grid:cell>
		    <grid:cell width="100px">关联实例类型</grid:cell>
		    <grid:cell width="150px">外部单号</grid:cell>
		    <grid:cell width="80px">状态</grid:cell>
		    <grid:cell style="text-align:left;">关键字</grid:cell>
			<grid:cell width="130px">创建时间</grid:cell>
			<grid:cell width="130px">更新时间</grid:cell>
			<grid:cell width="80px">操作</grid:cell>
		</grid:header>
		<input type="hidden" name="inner.search_id" value="${inner.search_id }"/>
		<input type="hidden" class="ipttxt" name="inner.match_content" value="${inner.match_content}" /> 
		<input type="hidden" class="ipttxt" name="inner.out_tid" value="${search_name}" /> 
		<grid:body item="objeto">
			<grid:cell>
				<input type="checkbox" name="eckBox" value="${objeto.excp_inst_id}" id="${objeto.excp_inst_id }" />
				<input type="hidden" dbField="search_id" value="${objeto.search_id }" />
				<input type="hidden" dbField="log_id" value="${objeto.log_id }"/>
				<c:choose>
		    		<c:when test="${empty objeto.match_content || objeto.match_content == ''}">
						<input type="hidden" dbField="match_content" value="未匹配到关键字(缺省)" />
		    		</c:when>
		    		<c:otherwise>
						<input type="hidden" dbField="match_content" value="${objeto.match_content }" />
		    		</c:otherwise>
		    	</c:choose>
				<input type="hidden" dbField="search_code" value="${objeto.search_code }"/>
			</grid:cell>
		    <grid:cell>${objeto.seq }  </grid:cell>
		    <grid:cell>${objeto.excp_inst_id } </grid:cell>
		    <grid:cell>${objeto.search_id } </grid:cell>
		    <grid:cell>${objeto.rel_obj_type} </grid:cell>
		    <grid:cell>${objeto.out_tid} </grid:cell>
		    <grid:cell>
		    	<c:if test="${objeto.record_status == 0 }">
		    		未处理
		    	</c:if>
		    	<c:if test="${objeto.record_status == 1 }">
		    		已处理
		    	</c:if>
		    </grid:cell>
		    <grid:cell>
		    	<c:choose>
		    		<c:when test="${empty objeto.match_content || objeto.match_content == ''}">
		    			<div title="" style="width:100px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;text-align:center;">
		                   	<%--  未匹配到关键字(缺省)--%>
						</div>
		    		</c:when>
		    		<c:otherwise>
		    			<div title="<c:out value=" ${objeto.match_content} " />" style="width:100px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;text-align:center;">
		                    <c:out value=" ${objeto.match_content} " />
						</div>
		    		</c:otherwise>
		    	</c:choose>
		    </grid:cell>
		    <grid:cell>${fn:substring(objeto.excp_create_time , 0 , 19)}</grid:cell>
		    <grid:cell>${fn:substring(objeto.excp_update_time , 0 , 19)}</grid:cell>
		    <grid:cell>
		    	<div style="margin-top:5px;">
				  <a log_id="${objeto.log_id }" href="javascript:searchEsearch('${objeto.log_id}');">查看详情</a>
				</div>
				<div style="margin-bottom:5px;">
				  <a log_id="${objeto.log_id }" search_field="${objeto.search_field }" search_id="${objeto.search_id  }" search_code="${objeto.search_code }" onclick="extractSpecvalues(this);" href="javascript:void(0);">模拟抽取</a>
				</div>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    </div>
<div style="clear:both;padding-top:5px;"></div>
</div>
<div id="esearchDlg"></div>
<div id="extractSpecvalues"></div>
<script>
	$(function(){
		Eop.Dialog.init({id:"esearchDlg",modal:true,title:"展示报文信息",width:'800px'});
		Eop.Dialog.init({id:"extractSpecvalues",modal:true,title:"模拟抽取关键字",width:'800px'});
	});
	
	//提交选中的异常单的数据
	function commit_data(){

		var excp_inst_ids = "";//异常实例id
		var search_ids = "";//搜索id
		var log_ids = "";//esearch实例id
		var match_content = "";//旧关键字
		var search_code = "";//搜索编码
		
		$("#theForm").find("input[name='eckBox']").each(function(){
			if($(this).is(":checked")){
				excp_inst_ids = excp_inst_ids + $(this).val() + ",";
				search_ids = search_ids + $(this).parent().find("input[dbField='search_id']").val() + ",";
				log_ids = log_ids + $(this).parent().find("input[dbField='log_id']").val() + ",";
				match_content = match_content + $(this).parent().find("input[dbField='match_content']").val() + ",";
				search_code = search_code + $(this).parent().find("input[dbField='search_code']").val() + ",";
			}
		});
		if(excp_inst_ids == ""){
			alert("请选择异常实例");
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: ctx + "/shop/admin/keyrule!expInstUpdate.do",
			 dataType:"json",
			 data: "ajax=yes&inner.excp_inst_ids=" + excp_inst_ids + "&inner.search_ids=" + search_ids + "&inner.log_ids=" + log_ids + "&inner.match_content=" + match_content + "&inner.search_code=" + search_code,
			 success: function(result){
				 if (result.result == '0') {
					 alert(result.message);
					 window.location.href = ctx + "/shop/admin/keyrule!orderexpList.do?inner.search_id=" + exp_insts[0].search_id;
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
	
	//初始化展示报文弹框
	function searchEsearch(log_id){
		var url= app_path+"/shop/admin/infLogs!showEsearch.do?ajax=yes&log_id="+log_id;
	    $("#esearchDlg").load(url,{},function(){});
	    $("#out_param").val("");
		$("#in_param").val("");
		Eop.Dialog.open("esearchDlg");
	}
	
	//初始化模拟抽取关键字弹框
	function extractSpecvalues(ele){
		var log_id = $(ele).attr("log_id");
		var search_field = $(ele).attr("search_field");
		var search_id = $(ele).attr("search_id");
		var search_code = $(ele).attr("search_code");
		var url= app_path+"/shop/admin/infLogs!showEsearch.do?ajax=yes&log_id="+log_id+"&search_field="+search_field+"&search_id="+search_id+"&search_code="+search_code;
	    $("#esearchDlg").load(url,{},function(){});
	    $("#out_param").val("");
		$("#in_param").val("");
		Eop.Dialog.open("esearchDlg");
	}
</script>