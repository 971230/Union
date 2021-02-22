<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/partnerAddr.js"></script>
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
	<form action="parAddr!list.do" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody><tr>
	    	  <th>联系人:</th>
	    	    <td><input type="text" class="ipttxt"  style="width: 90px" name="paraddr_s.name" value="${paraddr_s.name }" class="searchipt" /></td>
	    	     <th>手机号:</th>
	    	    <td><input type="text" class="ipttxt"  style="width: 90px" name="paraddr_s.mobile" value="${paraddr_s.mobile }" class="searchipt" /></td>
	    	     <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
			<a href="parAddr!add.do?is_edit=false" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
		 	<a href="javascript:;" id="delAddrBtn" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
			
		 <!-- <a href="partner!trash_list.do" style="margin-right:10px;" class="graybtn1"><span>回收站</span></a>	<a href="partner!list.do?is_edit=true" style="margin-right:10px;" class="graybtn1"><span>列表编辑</span></a> -->
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid  from="webpage" >

	<grid:header>
	<grid:cell >选择</grid:cell> 
	<grid:cell>联系人</grid:cell>
	<grid:cell >邮政编码 </grid:cell> 
	<grid:cell >电话 </grid:cell> 
	<grid:cell >手机 </grid:cell> 
	<grid:cell >收货地址 </grid:cell> 
	<grid:cell width="50px">操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
	 <grid:cell><input type="radio" name="id" value="${obj.addr_id}" /></grid:cell>
	 <grid:cell>${obj.name}</grid:cell>
     <grid:cell>${obj.zip}</grid:cell>
    <grid:cell>${obj.tel}</grid:cell>
    <grid:cell>${obj.mobile}</grid:cell>
    <grid:cell>${obj.addr}</grid:cell>
    <grid:cell>
		<a title="修改" href="parAddr!edit.do?addrid=${obj.addr_id }"> 
		修改
		</a>
	</grid:cell>
  </grid:body>  
  
</grid:grid>  
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
 <script type="text/javascript">
$(function(){
	PartnerAddr.init();
});
</script>
 

