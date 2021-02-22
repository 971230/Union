<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/supplier.js"></script>
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
	<form action="supplier!auditlist.do" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody><tr>
	    	      <th>公司名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="company_name" value="${company_name }" class="searchipt" /></td>
	    	      <th>注册人:</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="user_name" value="${user_name }" /> </td>
	    	      <th>联系电话:</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="phone" value="${phone }" /> </td>
	    	     <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"   id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
		<!-- 	<a href="partner!add.do?is_edit=false" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
		 	<a href="javascript:;" id="delBtn" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>-->
			
		 <!-- <a href="partner!trash_list.do" style="margin-right:10px;" class="graybtn1"><span>回收站</span></a>	<a href="partner!list.do?is_edit=true" style="margin-right:10px;" class="graybtn1"><span>列表编辑</span></a> -->
		</div>
		<input type="hidden" name="supplier_type" value="${supplier_type}" />
	</form>
	<form id="gridform" class="grid">
		<grid:grid  from="webpage" >

	<grid:header>
	<grid:cell >选择</grid:cell> 
	<grid:cell sort="company_name">公司名称</grid:cell> 
  	<grid:cell >电子邮件</grid:cell> 
	<grid:cell >QQ号</grid:cell> 
	<grid:cell >注册人姓名</grid:cell> 
	<grid:cell >性别</grid:cell> 
	<grid:cell >身份证号码</grid:cell> 
	<grid:cell >联系电话</grid:cell> 
	<grid:cell >创建时间</grid:cell> 
	<grid:cell width="50px">操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
	 <grid:cell><input type="radio" name="id" value="${obj.supplier_id}" /></grid:cell>
	<grid:cell><a title="查看" href="">${obj.company_name}</a> </grid:cell>
     <grid:cell >${obj.email} </grid:cell> 
     <grid:cell >${obj.qq} </grid:cell> 
     <grid:cell >${obj.user_name} </grid:cell> 
     <grid:cell >
     	<c:if test="${obj.sex eq '1'}">
     		男
     	</c:if>
     	 <c:if test="${obj.sex eq '2'}">
     		女
     	</c:if>
     </grid:cell>
     <grid:cell >${obj.id_card}</grid:cell> 
     <grid:cell >${obj.phone}</grid:cell> 
    <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${obj.register_time}"></html:dateformat> </grid:cell> 
    <grid:cell>
		<a title="审核" class="auditName" supplier_id="${obj.supplier_id }" supplier_type="${supplier_type}">审核</a>
	</grid:cell>
  </grid:body>  
  
</grid:grid>  
	</form>
	 <div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
 <script type="text/javascript">
$(function(){
	Supplier.init();
});
</script>
 

