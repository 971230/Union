<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${staticserver }/js/admin/Tab.js"></script>
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

<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">预存款信息</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
<div >
 <form id="gridform" class="grid">
 <div class="searchformDiv">
		<span><b>预存款冻结/解冻记录</b></span>
	</div>
 <grid:grid  from="freezeLogList" >
	<grid:header>
    <grid:cell>操作日期</grid:cell> 
	<grid:cell >操作类型</grid:cell> 
	<grid:cell >当前操作金额</grid:cell> 
	<grid:cell >冻结|解冻前金额</grid:cell> 
	<grid:cell >冻结|解冻后金额</grid:cell> 
	<grid:cell >备注</grid:cell> 
	</grid:header>

  <grid:body item="freeze">
     <grid:cell ><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${freeze.op_time}"></html:dateformat> </grid:cell> 
     <grid:cell >${freeze.op_type }</grid:cell> 
     <grid:cell >
     	<fmt:formatNumber value="${freeze.amount}" type="currency" pattern="￥.00" />
     </grid:cell> 
     <grid:cell >
     	<fmt:formatNumber value="${freeze.old_amount}" type="currency" pattern="￥.00" />
     </grid:cell> 
     <grid:cell >
     	<fmt:formatNumber value="${freeze.new_amount}" type="currency" pattern="￥.00" />
     </grid:cell>
     <grid:cell >${freeze.comments }</grid:cell> 
  </grid:body>  
  
</grid:grid>  

<div class="searchformDiv">
		<span><b>预存款充值/返销记录</b></span>
	</div>
<grid:grid  from="depostLogList" >
	<grid:header>
    <grid:cell>操作日期</grid:cell> 
	<grid:cell >操作类型</grid:cell> 
	<grid:cell >当前操作金额</grid:cell> 
	<grid:cell >充值/返销前金额</grid:cell> 
	<grid:cell >充值/返销后金额</grid:cell> 
	<grid:cell >备注</grid:cell> 
	</grid:header>

  <grid:body item="depost">
     <grid:cell ><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${depost.op_time}"></html:dateformat></grid:cell> 
     <grid:cell >${depost.op_type }</grid:cell> 
     <grid:cell >
     	<fmt:formatNumber value="${depost.amount}" type="currency" pattern="￥.00" />
     </grid:cell> 
     <grid:cell >
     	<fmt:formatNumber value="${depost.old_amount}" type="currency" pattern="￥.00" />
     </grid:cell> 
     <grid:cell >
     	<fmt:formatNumber value="${depost.new_amount}" type="currency" pattern="￥.00" />
     </grid:cell>
     <grid:cell >${depost.comments }</grid:cell> 
  </grid:body> 
  </grid:grid>
</form>

<div style="clear: both; padding-top: 5px;"></div>
</div>
