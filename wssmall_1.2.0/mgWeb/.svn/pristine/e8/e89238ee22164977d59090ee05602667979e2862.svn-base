<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Adv.js"></script>

<form method="post" action="adv!list.do">

<div class="grid">
	<div class="searchformDiv">
		
		
广告名称：<input type="text" class="ipttxt" style="width: 120px;" name="advname" value="${advname }" />&nbsp;&nbsp;
所在广告位：
	<select  class="ipttxt"  name="acid" id="acid" style="width: 120px;">
		<option value="">所有</option>
		<c:forEach items="${adColumnList}" var="adc">
			<option value="${adc.acid}">${adc.cname }</option>
		</c:forEach>
	</select> 
<!-- 	审核状态： -->
<!-- 	<select class="ipttxt" name="state" id="state"> -->
<!-- 	     <option value="-1">所有</option> -->
<!-- 	     <option value="0" <c:if test="${state==0}">selected</c:if>>待审核</option> -->
<!-- 	     <option value="1" <c:if test="${state==1}">selected</c:if>>已发布</option> -->
	     
<!-- 	</select> -->
	发布渠道：
	<select class="ipttxt" name="source_from" id="source_from">
		<c:forEach var="sourceFrom" items="${sourceFromList}">
			<option value="${sourceFrom.pkey }"
				<c:if test="${sourceFrom.pkey==sourcefrom}">selected="selected"</c:if>>${sourceFrom.pname
				}</option>
		</c:forEach>
	</select>	
	<input type="submit"  class="comBtn"  value="搜索"/>
	<div style="clear:both"></div>
</from>
<form class="grid" id ="btn_form">

<div class="comBtnDiv">
     <input type="hidden" name = "messId" id="messId" value="${messId}">

	<a href="javascript:;" style="margin-right:10px;" id="delBtn" class="graybtn1" ><span>删除</span></a>
	<a href="adv!add.do" style="margin-right:10px;"  class="graybtn1" ><span>新增广告</span></a>

</div>

</div>
<div class="grid" id="newslist">
<grid:grid  from="webpage">
	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell width="250px">广告名称</grid:cell>
	<grid:cell width="50px">分辨率</grid:cell>
	<grid:cell sort="cname">所属广告位</grid:cell>
	<grid:cell sort="cname">发布渠道</grid:cell>
	<grid:cell sort="company">单位名称</grid:cell> 
	<grid:cell sort="begintime">开始时间</grid:cell>
	<grid:cell sort="endtime">截止时间</grid:cell> 
<!-- 	<grid:cell sort="isclose">审核状态</grid:cell> -->
	<grid:cell sort="isclose">状态</grid:cell>
	
	<grid:cell  width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="adv">
  		<grid:cell><input type="checkbox" name="id" value="${adv.aid }" /></grid:cell>
        <grid:cell>${adv.aname } </grid:cell>
        <grid:cell>${adv.resol } </grid:cell>
        <grid:cell>${adv.cname } </grid:cell>
        <grid:cell>
        	<c:if test="${adv.source_from=='LLKJ_WT'}">网厅</c:if>
        	<c:if test="${adv.source_from=='LLKJ_AGENT'}">代理商</c:if>
        	<c:if test="${adv.source_from=='WSSMALL'}">统一平台</c:if>
        </grid:cell>
        <grid:cell>${adv.company }</grid:cell>
        <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${adv.begintime }"></html:dateformat> </grid:cell>
        <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${adv.endtime }"></html:dateformat></grid:cell>
<!--          <grid:cell> -->
        
<!--         <c:if test="${adv.state=='0'}">待审核</c:if> -->
<!--         <c:if test="${adv.state=='1'}"> -->
<!--                已发布 -->
<!--         </c:if> -->
<!--          <c:if test="${adv.state=='2'}"> -->
<!--               审核不通过 -->
<!--         </c:if> -->
        
<!--         </grid:cell>   -->
        <grid:cell>
        
        <c:if test="${user_founder==4&&adv.state!=0}"> 
        	<c:if test="${adv.isclose == 0 }">已开启&nbsp;<input disabled="disabled" type="button" class="stop" advid="${adv.aid }" source_from="${adv.source_from}" value="停用" /></c:if>
        	<c:if test="${adv.isclose == 1 }">已停用&nbsp;<input disabled="disabled" type="button" class="start" advid="${adv.aid}" source_from="${adv.source_from}"  value="开启" /></c:if>
         </c:if> 
        
         <c:if test="${user_founder!=4||adv.state==0}"> 
        	<c:if test="${adv.isclose == 0 }">已开启&nbsp;<input  type="button" class="stop" advid="${adv.aid }" source_from="${adv.source_from}" value="停用" /></c:if>
        	<c:if test="${adv.isclose == 1 }">已停用&nbsp;<input type="button" class="start" advid="${adv.aid}"  source_from="${adv.source_from}" value="开启" /></c:if>
       
        </c:if>
         
        </grid:cell> 
         
        <grid:cell>
        <c:if test="${user_founder!=4||adv.state==0}">
        <a href="adv!edit.do?advid=${adv.aid}&source_from=${adv.source_from}" ><img class="modify" src="images/transparent.gif"/></a>&nbsp;&nbsp;
        </c:if> 
        </grid:cell>
      
  </grid:body>  
  
</grid:grid>  

</div>

<script>
$(function(){
	$("#acid").val("${acid}");
});



</script>

<div id="showDlg"></div>

