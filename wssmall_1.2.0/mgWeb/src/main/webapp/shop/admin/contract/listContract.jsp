<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>var ctx ='${ctx}';</script>
<style>

.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}


.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}

h4  {
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

</style>
<div class="input" >
	<div class="tableform">
<h4>
   <input type="button" id="addBuyContract" class="graybtn1" value="添加合约" />
</h4>
		<div class="division" id ="contractDiv">
		<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody id="qrContract">
			    
			    <tr>
                    <td><div align="center"><b>名称</b></div></td>
                   
                    <td><div align="center"><b>操作</b></div></td>
                </tr> 
              <div >
			    <c:forEach var ="goods" items="${listSetContract}">
                  <tr id="tr_${goods.goods_id}">
                    <td width="20%">
                        <div align="center">${goods.goods_name}
                            <input type='hidden' name='goods_Contract' goods_name="${goods.goods_name}" goods_id='${goods.goods_id}' value="${goods.goods_id}" />
                            <a href='javascript:void(0);' class='delete'/>
                        </div>
                    </td>
                  
                    <td width="20%"><div align="center"><a href='javascript:;' name='delContract'>删除</a></div></td>
                </tr> 
               </c:forEach>
              </div>
			</tbody>
			
		</table>
	  </div>
	  <div>
	     <tr id="btnTr">
	         <td colspan='2'>
		        <div align="center"> 
		           <input type="button" id='setContractBtn' class="graybtn1" value="确  定" />
		         </div>
	          </td>
	     </tr>
     </div>
	</div>
</div>



<script type="text/javascript">
  $(function(){
      $("[name='delContract']").each(function(){ 
                 $(this).click(function(){
                 $(this).closest("tr").remove();
               });
       });
      
       $("#addBuyContract").bind("click",function(){
           contractSel.openContractList();
       });
  });
</script>
