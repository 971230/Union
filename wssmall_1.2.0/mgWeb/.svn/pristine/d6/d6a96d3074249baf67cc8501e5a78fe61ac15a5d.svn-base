<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/news.js"></script>
<form method="post" action="news!list.do">

<div class="grid">
	<div class="searchformDiv">
		
		
    审核状态
	<select class="ipttxt" name="state" id="state">
	     <option value="-1">所有</option>
	     <option value="0" <c:if test="${state==0}">selected</c:if>>待审核</option>
	     <option value="1" <c:if test="${state==1}">selected</c:if>>已发布</option>
	     <option value="2" <c:if test="${state==2}">selected</c:if>>审核不通过</option>
	     
	</select>
	<input type="submit"  class="comBtn"  value="搜索"/>
	<div style="clear:both"></div>
</from>
<form class="grid" id ="btn_form">

<div class="comBtnDiv">
     <input type="hidden" name = "messId" id="messId" value="${messId}">

	<a href="javascript:;" style="margin-right:10px;" id="delBtn" class="graybtn1" ><span>删除</span></a>
	<a href="news!add.do" style="margin-right:10px;"  class="graybtn1" ><span>新增快讯</span></a>

</div>

</div>
<form id="gridform" class="grid">
<div class="grid" id="goodslist">
<grid:grid  from="webpage">
	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell width="250px">标题</grid:cell>
	<grid:cell sort="endtime">有限期</grid:cell> 
	<grid:cell sort="isclose">状态</grid:cell>
	<grid:cell  width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="news">
  		<grid:cell><input type="checkbox" name="id" value="${news.news_id }" /></grid:cell>
        <grid:cell>${news.title } </grid:cell>
        <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${news.end_time }"></html:dateformat></grid:cell>
        <grid:cell>
        
        <c:if test="${news.state=='0'}">待审核</c:if>
        <c:if test="${news.state=='1'}">
               已发布
        </c:if>
         <c:if test="${news.state=='2'}">
              审核不通过
        </c:if>
        
        </grid:cell>  
     
        <grid:cell>
         <c:if test="${currFounder!=4||news.state==0}">
        <a href="news!edit.do?news_id=${news.news_id}" ><img class="modify" src="images/transparent.gif"/></a>&nbsp;&nbsp;
           </c:if>
       </grid:cell> 
    
  </grid:body>  
  
</grid:grid>  

</div>

</form>	
<script>
 var Adv=$.extend({},Eop.Grid,{
    init:function(){
   	var self =this;
	$("#delBtn").click(function(){
	   self.doDelete();
	});
    },
    
    doDelete:function(){
        if(!this.checkIdSeled()){
			alert("请选择要删除的快讯");
			return ;
		};
		if(!confirm("确认要删除选定的快讯?")){	
			return ;
		}
		
		$.Loading.show("正在删除快讯...");
		
		this.deletePost("news!delete.do", "快讯删除成功");
    }
    
});

$(function(){
   
	$("#acid").val("${acid}");
	Adv.init();
});
</script>
