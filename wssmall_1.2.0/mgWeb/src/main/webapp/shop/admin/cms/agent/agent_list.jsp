<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="cms/js/partner.js"></script>
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

.searchformDiv table th {
	width: 110px;
}
</style>
<div >
  <input type="hidden" id="currentPartnerId" value="${currentPartnerId }">
	<form action="cmsAgent!list.do" method="post" id="parListForm">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	     
	    	    <th>行业用户组名称：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 120px;" name="part_serch.partner_name" value="${part_serch.partner_name }" class="searchipt" /></td>
<!-- 	    	      <th>行业用户组编码：</th> -->
<!-- 	    	      <td><input type="text" class="ipttxt"  style="width: 120px;" name="part_serch.partner_code" value="${part_serch.partner_code }" class="searchipt" /></td> -->
	    	      <th>联系人：</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="part_serch.linkman" value="${part_serch.linkman }" /> </td>
	    	      </tr>
	    	      <tr>
	    	      	<th>联系电话：</th>
		    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="part_serch.phone_no" value="${part_serch.phone_no }" /> </td>
		    	      <th>行业用户组状态：</th>
		    	      <td>
		    	       <select  class="ipttxt"  id="stateType" name="stateType" >
		    	         <option value="all">全部</option>
		    	          <option value="1">正常</option>
		    	          <option value="2">已冻结</option>
		    	          <option value="3">已注销</option>
		    	          <!--  add by wui只查询有效的状态，处理中的不需要加上
		    	          <option value="0">申请中</option>
		    	          <option value="-1">申请不通过</option>
		    	          <option value="-2">变更中</option>
		    	          <option value="-3">变更审核不通过</option>
		    	           -->
		    	       </select>
		    	      </td>
	    	      </tr>
	    	      <tr>
		    	    <th>MAC地址：</th>
		    	    <td>
		    	    	<input type="text" class="ipttxt searchipt"  
		    	    		style="width: 120px" name="part_serch.staff_mac" 
		    	    		value="${part_serch.staff_mac}" /> 
		    	    </td>
		    	    
		    	    <th>行业用户联系电话：</th>
		    	    <td>	
		    	    	<input type="text" class="ipttxt searchipt"  
		    	    		style="width: 120px" name="part_serch.staff_contract_phone" 
		    	    		value="${part_serch.staff_contract_phone }" /> 
		    	    </td>
		    	    
	    	      <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"   id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
    	<a href="cmsAgent!add.do?flag=add&is_edit=false" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
    	<a href="javascript:;" id="delPartnerBtn" style="margin-right:10px;" class="graybtn1" ><span>删除</span></a>
<!--     	  <c:if test="${stateType == '1' or stateType=='all'}"> -->
<!-- 	    	     <c:if test="${currFounder==3}"> -->
<!-- 				    <a href="cmsAgent!add.do?flag=add&is_edit=false" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a> -->
<!-- 				 </c:if> -->
<!-- 				<a href="javascript:;" id="amountInfoBtn" style="margin-right:10px;" class="graybtn1" ><span>预存金信息</span></a> -->
<!-- 		  </c:if> -->
			<%-- <c:if test="${currFounder!=2}"> --%>
<!-- 			    <c:if test="${stateType== '1' or stateType=='all'}"> -->
<!-- 					<c:if test="${currFounder==3}"> -->
<!-- 					<a href="javascript:;" id="amountBlockBtn" style="margin-right:10px;" class="graybtn1" ><span>预存金冻结或解冻</span></a> -->
<!-- 			        </c:if> -->
<!-- 			    </c:if>  -->
			     <%-- <c:if test="${stateType== '1' or stateType=='all'}">
 				     <a href="javascript:;" id="gradingBtn" style="margin-right:10px;" class="graybtn1" ><span>分级业务冻结</span></a>
 				     <a href="javascript:;" id="gradingOpenBtn" style="margin-right:10px;" class="graybtn1" ><span>分级业务解冻</span></a>
			         <a href="javascript:;" id="blockBtn" style="margin-right:10px;" class="graybtn1" ><span>冻结行业用户组</span></a>
			     </c:if> --%>
			   <%-- <c:if test="${stateType== '1' or stateType == '2' or stateType=='all'}">
			       
			       <a href="javascript:;" id="renewBtn" style="margin-right:10px;" class="graybtn1" ><span>解冻行业用户组</span></a>
			       <a href="javascript:;" id="keeponBtn" style="margin-right:10px;" class="graybtn1" ><span>续签</span></a>
			   </c:if> --%>
			<%-- </c:if> --%>
			
<!-- 			<c:if test="${currFounder==3}"> -->
<!-- 			  <c:if test="${stateType=='1' or stateType=='all'}"> -->
<!-- 			    <a href="javascript:;" id="delPartnerBtn" style="margin-right:10px;" class="graybtn1" ><span>删除</span></a> -->
<!-- 			  </c:if> -->
<!-- 			</c:if> -->
		</div>
	</form>
	<form id="gridform" class="grid">
	<grid:grid  from="webpage" excel="yes">
	<grid:header>
	<grid:cell ><input type="checkbox"  id="toggleChk" /></grid:cell> 
  	<grid:cell sort="partner_name">行业用户组名称</grid:cell> 
<!--    	<grid:cell >行业用户组编码</grid:cell> -->
<!-- 	<grid:cell >店铺类型</grid:cell>  -->
	<grid:cell >联系人</grid:cell> 
	<grid:cell >联系电话</grid:cell> 
<!-- 	<grid:cell >达量</grid:cell>  -->
<!-- 	<grid:cell >分值</grid:cell>  -->
<!-- 	<grid:cell >预存金</grid:cell>  -->
<!-- 	<grid:cell >冻结预存金</grid:cell>  -->
<!-- 	<grid:cell>服务保证金</grid:cell> -->
<!-- 	<grid:cell>合同生效时间</grid:cell> -->
<!-- 	<grid:cell>合同失效时间</grid:cell> -->
	<grid:cell >状态 </grid:cell> 
	<grid:cell >关联工号 </grid:cell>
	<grid:cell >操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
     <grid:cell><input type="checkbox" founder="${obj.founder}" name="id" userid="${obj.userid }" value="${obj.partner_id}" state="${obj.state }" sequ="${obj.sequ }"/></grid:cell>
	 <grid:cell><a title="查看" href="cmsAgent!detail.do?flag=view&state=${obj.state }&sequ=${obj.sequ }&partner_id=${obj.partner_id }">${obj.partner_name}</a> </grid:cell>
<!--       <grid:cell >${obj.userno} </grid:cell> -->
<!--      <grid:cell >${obj.shop_type_desc} </grid:cell>  -->
     <grid:cell >${obj.linkman} </grid:cell> 
     <grid:cell >${obj.phone_no} </grid:cell> 
<!--      <grid:cell >${obj.need_amount} </grid:cell> -->
<!--      <grid:cell >${obj.score}</grid:cell>  -->
<!--      <grid:cell >￥${obj.deposit}.00</grid:cell>  -->
<!--      <grid:cell >￥${obj.frost_deposit}.00 </grid:cell>  -->
<!--      <grid:cell>${obj.service_cash}</grid:cell>  -->
<!--      <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${obj.eff_date}"></html:dateformat> </grid:cell>  -->
<!--      <grid:cell><span  <c:if test="${obj.edflag=='0' }">title="离失效日期不足30天" class='red'</c:if> > <html:dateformat pattern="yyyy-MM-dd" d_time="${obj.exp_date}"></html:dateformat></span> </grid:cell>     -->
    <grid:cell>
            <c:if test="${obj.state == 0 }">未审核</c:if> 
            <c:if test="${obj.state == 1 and obj.sequ==0}">正常</c:if>
            <c:if test="${obj.state == 2 }">冻结</c:if>
            <c:if test="${obj.state == 3 }">注销</c:if>
            <c:if test="${obj.state == -1 }">审核不通过</c:if>
            <c:if test="${obj.state == 1 and obj.sequ==-2}">变更审核不通过</c:if>
            <c:if test="${obj.state == 1 and obj.sequ==-1}">变更中</c:if>
    </grid:cell>
    <grid:cell><c:if test="${obj.userid!=null}">${obj.username } </c:if> <c:if test="${obj.userid==null}"><span class='red'>未关联</span></c:if> </grid:cell> 
   <grid:cell>
       <c:if test="${obj.state == 0 or obj.state==1}">
          <c:if test="${obj.sequ==0}">
	  	  <a title="编辑" href="cmsAgent!detail.do?flag=edit&state=${obj.state }&sequ=${obj.sequ }&partner_id=${obj.partner_id }"> 修改	</a>
	  	  </c:if>
	   </c:if>
	   <c:if test="${obj.state ==-1}">
	  	<a title="恢复申请" class="againApply" state="${obj.state }" sequ="${obj.sequ }" partner_id="${obj.partner_id }"> 恢复申请	</a>
	   </c:if>
	   <c:if test="${obj.state == 1 and obj.sequ==-1}">
	    <a title="变更记录" class="lookResonBtn" sequ="${obj.sequ }" partner_id="${obj.partner_id }">变更记录</a>
	    </c:if>
	</grid:cell>
	
  </grid:body>  
  
</grid:grid>  
<!-- <img  class="modify" src="${ctx }/shop/admin/images/transparent.gif"> -->
	</form>
	 <div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
 <script type="text/javascript">
$(function(){
    $("#stateType option[value='${stateType}']").attr("selected", true);
    $("#stateType").change(function (){
       $("#button").trigger("click");
    });
	Partner.init();
	
});
</script>
 

