<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/partnerShopMapping.js"></script>
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
	<form action="shopMapping!list.do" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody><tr>
	    	   <th>店铺名称:</th>
	    	    <td><input type="text" class="ipttxt"  style="width: 90px" name="shopMapping_s.shop_name" value="${shopMapping_s.shop_name }" class="searchipt" /></td>
	    	     <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
			<a href="shopMapping!add.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
		 	<a href="javascript:;" id="delAddrBtn" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
			
		 <!-- <a href="partner!trash_list.do" style="margin-right:10px;" class="graybtn1"><span>回收站</span></a>	<a href="partner!list.do?is_edit=true" style="margin-right:10px;" class="graybtn1"><span>列表编辑</span></a> -->
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid  from="webpage" >

	<grid:header>
	<grid:cell >选择</grid:cell> 
	<grid:cell>店铺编号</grid:cell>
	<grid:cell >店铺名称 </grid:cell> 
	<grid:cell >店铺类型 </grid:cell> 
	<grid:cell >创建时间 </grid:cell> 
	<grid:cell >说明 </grid:cell> 
	<grid:cell >操作 </grid:cell> 
	</grid:header>

  <grid:body item="obj">
	 <grid:cell><input type="radio" name="id" value="${obj.shop_code}" /></grid:cell>
	 <grid:cell>${obj.shop_code}</grid:cell>
     <grid:cell>${obj.shop_name}</grid:cell>
    <grid:cell>${obj.shop_type}</grid:cell>
    <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${obj.create_date}"></html:dateformat> </grid:cell>
    <grid:cell>${obj.shop_desc}</grid:cell>
    <grid:cell><a title ="修改" href="${ctx }/shop/admin/shopMapping!edit.do?shopCode=${ obj.shop_code}" class="p_prted">修改</a></grid:cell>
  </grid:body>  
  
</grid:grid>  
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
 <script type="text/javascript">
$(function(){
	PartnerShopMapping.init();
});
</script>
 

