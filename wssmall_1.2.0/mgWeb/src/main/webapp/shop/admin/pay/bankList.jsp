<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div class="rightDiv">
<div class="stat_graph">
	<h3>
		<div class="graph_tab">
			<ul>
				<li id="show_click_1" class="selected">
					<span class="word">银行列表</span><span class="bg"></span>
				</li>
				<div class="clear"></div>
			</ul>
		</div>
	</h3>
</div>
<div class="top_up_div">
	<h4>
		可选银行
	</h4>
<div class="top_up_con">
<table id="bankList" width="100%">

<c:set var="idx" value="0"></c:set>
  <tr>
   <c:forEach var="list" items="${ bank}">
	<c:if test="${idx%3==0&&idx>0 }">
	    </tr><tr>
	</c:if>
	<td id="idxx">
		<input type="checkbox" name="checkBox" cfg_id="${cfg_id }"  value="${list.bank_id }" ${list.selected==1?'checked':'' }><img src="${acc.img_url}">${list.bank_name}
	</td>
	<c:set var="idx" value="${idx+1 }"></c:set>
</c:forEach>
</table>
</div>
<div class="top_up_div">
	<h4>
		已选银行
	</h4>
<div class="top_up_con">
<span id="bankLists" >
	<c:forEach var="list" items="${ bank}">
	<c:if test="${list.selected==1 }">
		<c:if test="${idx%3==0&&idx>0 }">
		    </tr><tr>
		</c:if>
		
	
	       ${list.bank_name}
		
		
		<c:set var="idx" value="${idx+1 }"></c:set>
	</c:if>
</c:forEach>
</span>
</div>
</div>
</div>
<script type="text/javascript">
	 $("input[name=checkBox]").click(function(){
		 if(this.checked){
			 var td = $(this).parents("td");
			 $("#bankLists").append(td);
			 td.find("input").remove();
			 
			 var cfg_id = $(this).attr("cfg_id");
			 var bank_id=$(this).attr("value");
			 url = ctx+ "/shop/admin/creditAction!add.do?ajax=yes&credit.cfg_id="+cfg_id+"&credit.bank_id="+bank_id;   	   
	         Cmp.excute('', url, {},'', 'json'); 
		 }
		 else{
			 var cfg_id=$(this).attr("value");
			 var bank_id=$(this).attr("value");
			 alert(bank_id);
			 url ="creditAction!delBank.do?ajax=yes&cfg_id="+cf_id+"&bank_id="+bank_id;    	   
		     Cmp.excute('', url, {}, '', 'json'); 
		 }
		 
	 });
	
</script>

