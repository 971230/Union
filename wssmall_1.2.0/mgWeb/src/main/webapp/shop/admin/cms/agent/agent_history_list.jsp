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
</style>
<div >
	<form action="partner!historylist.do?flag2=${flag2 }" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    
	    	    <tr>
	    	   
	    	     <th>行业用户名称：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 120px;" name="part_serch.partner_name" value="${part_serch.partner_name }" class="searchipt" /></td>
	    	       <th>行业用户编码：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 120px;" name="part_serch.partner_code" value="${part_serch.partner_code }" class="searchipt" /></td>
	    	       </tr>
	    	       <tr>
	    	        <th>联系人：</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="part_serch.linkman" value="${part_serch.linkman }" /> </td>
	    	      <th>联系电话：</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="part_serch.phone_no" value="${part_serch.phone_no }" /> </td>
	    	      <c:if test="${currFounder==3 or currFounder==2}">
	    	     <th>资料变更状态：</th>
	    	       <td>
	    	       <select  class="ipttxt"  id="stateType" name="stateType">
	    	        <!-- 一级 -->  
	    	      <c:if test="${currFounder==3}"> 
	    	           <option value="allo">全部</option>
		    	       <option value="B0">待审核</option>
		    	        <option value="B1">处理中</option>
		    	      <option value="B2">审核不通过</option>
		    	      
	    	       </c:if>
	    	      <!-- 二级 -->   <c:if test="${currFounder==2 }">
	    	      <option value="allt">全部</option>
	    	       <option value="C1">处理中</option>
	    	      <option value="C2">审核不通过</option>
	    	      </c:if> 
	    	       </select>
	    	       </td>
	    	       </c:if>
	    	     <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid  from="webpage" >
	<grid:header>
	<grid:cell >选择</grid:cell> 
	<grid:cell sort="partner_name">行业用户名称</grid:cell> 
	<%-- <grid:cell >行业用户编码</grid:cell> 
	<grid:cell >店铺类型</grid:cell>  --%>
	<grid:cell >联系人</grid:cell> 
	<grid:cell >联系电话</grid:cell> 
	<%-- <grid:cell >达量</grid:cell> 
	<grid:cell >分值</grid:cell> 
	<grid:cell >预存金</grid:cell> 
	<grid:cell >冻结预存金</grid:cell> 
	<grid:cell>服务保证金</grid:cell> --%>
	<grid:cell >状态 </grid:cell> 
	<grid:cell >操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
	 <grid:cell><input type="radio" name="id" value="${obj.partner_id}" state="${obj.state }" sequ="${obj.sequ }"/></grid:cell>
     <grid:cell >${obj.partner_name }</grid:cell>
     <%--  <grid:cell>${obj.partner_code}</grid:cell>
     <grid:cell >${obj.shop_type_desc} </grid:cell>  --%>
     <grid:cell >${obj.linkman} </grid:cell> 
     <grid:cell >${obj.phone_no} </grid:cell> 
    <%--  <grid:cell >${obj.need_amount} </grid:cell>
      <grid:cell >${obj.score}</grid:cell> 
     <grid:cell >${obj.deposit}</grid:cell> 
     <grid:cell >${obj.frost_deposit} </grid:cell> 
     <grid:cell>${obj.service_cash}</grid:cell>  --%>
    <grid:cell>
             <c:if test="${obj.s_sequ == -2 or obj.m_sequ==-2}">审核不通过</c:if>
              <c:if test="${currentPartnerId==obj.partner_id}">
               <c:if test="${obj.s_sequ == -1 or obj.m_sequ==-1}">处理中</c:if>
             </c:if>
            <c:if test="${currentPartnerId!=obj.partner_id}">
              <c:if test="${obj.s_sequ == -1 or obj.m_sequ==-1}">待审核</c:if>
            </c:if>
    </grid:cell>
    <grid:cell>
        <c:if test="${stateType=='A0' or stateType=='B0' or stateType=='allo' or stateType=='allt'}">
           <c:if test="${obj.s_sequ == -1 or obj.m_sequ==-1}"> 
               <c:if test="${currentPartnerId!=obj.partner_id}"> <a title="审核" class="auditAlterBtn" partner_id="${obj.partner_id }">审核</a></c:if>
               <c:if test="${currentPartnerId==obj.partner_id}"><a title="变更记录" class="lookHistoryBtn" sequ="${obj.sequ }" partner_id="${obj.partner_id }">变更记录</a></c:if>
            </c:if>
        </c:if>
        <c:if test="${stateType=='C1' or stateType=='B1'}">
             <c:if test="${currentPartnerId==obj.partner_id}">
               <c:if test="${obj.s_sequ == -1 or obj.m_sequ==-1}"><a title="变更记录" class="lookHistoryBtn" sequ="${obj.sequ }" partner_id="${obj.partner_id }">变更记录</a></c:if>
             </c:if>
        </c:if>
         <c:if test="${stateType=='C2' or stateType=='B2' or stateType=='allo' or stateType=='allt'}">
            <c:if test="${obj.s_sequ == -2 or obj.m_sequ==-2}"><a title="查看" class="lookResonBtn" sequ="${obj.sequ }" s_sequ=${obj.s_sequ } m_sequ=${obj.m_sequ } partner_id="${obj.partner_id }">查看</a>
         </c:if>
        </c:if>
   </grid:cell>
  </grid:body>  
  
</grid:grid>  
	</form>
	 <div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
 <script type="text/javascript">
$(function(){
    $("#stateType option[value='${stateType}']").attr("selected", true);
    $("#stateType").change(function (){
       $("#button").trigger("click")
    });
    
	Partner.init();
	
});
</script>
 

