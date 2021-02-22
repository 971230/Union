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
	<form action="partner!logoutlist.do" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody><tr>
	    	    <th>行业用户编号:</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="partLogout_s.partner_id" value="${partLogout_s.partner_id }" /> </td>
	    	      <th>行业用户名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="partLogout_s.partner_name" value="${partLogout_s.partner_name }" class="searchipt" /></td>
	    	       <th></th>
	    	      
	    	     <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
		<!-- 	<a href="partner!add.do?is_edit=false" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
		 	<a href="javascript:;" id="delBtn" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>-->
			
		 <!-- <a href="partner!trash_list.do" style="margin-right:10px;" class="graybtn1"><span>回收站</span></a>	<a href="partner!list.do?is_edit=true" style="margin-right:10px;" class="graybtn1"><span>列表编辑</span></a> -->
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid  from="webpage" >

	<grid:header>
	<grid:cell ><input type="checkbox"  id="toggleChk" /></grid:cell> 
	<grid:cell sort="partner_id">行业用户编码</grid:cell> 
	<grid:cell sort="partner_name">行业用户名称</grid:cell> 
	<grid:cell sort="partner_type">行业用户类型</grid:cell> 
	<grid:cell sort="need_amount">达量</grid:cell> 
	<grid:cell sort="partner_level">行业用户级别</grid:cell> 
	<grid:cell sort="partner_cat">行业用户类别</grid:cell> 
	<grid:cell sort="shop_type">店铺类型</grid:cell> 
	<grid:cell>服务保证金</grid:cell>
	<grid:cell>上级行业用户姓名</grid:cell>
	<grid:cell >法人姓名 </grid:cell> 
	<grid:cell width="50px">操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
	 <grid:cell><input type="checkbox" name="id" value="${obj.partner_id}" /></grid:cell>
	 <grid:cell>${obj.partner_id}</grid:cell>
     <grid:cell >${obj.partner_name }</grid:cell>
     <grid:cell >${obj.partner_type_desc} </grid:cell> 
     <grid:cell >${obj.need_amount} </grid:cell>
     <grid:cell >${obj.partner_level_desc} </grid:cell>
     <grid:cell >${obj.partner_cat_desc}</grid:cell>
     <grid:cell>${obj.shop_type_desc}</grid:cell>
     <grid:cell>${obj.service_cash}</grid:cell>      
	<grid:cell>${obj.realname_us}</grid:cell>
    <grid:cell>${obj.legal_name}</grid:cell>
    
    <grid:cell>
		
	</grid:cell>
  </grid:body>  
  
</grid:grid>  
	</form>
	 <div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
 <script type="text/javascript">
$(function(){
	Partner.init();
});
</script>
 

