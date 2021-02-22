<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/partner.js"></script>
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
	<form action="partner!auditlist.do" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody><tr>
	    	     <th>分销商名称：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="partAudit_s.partner_name" value="${partAudit_s.partner_name }" class="searchipt" /></td>
	    	        <th>分销商编码：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="partAudit_s.partner_code" value="${partAudit_s.partner_code }" class="searchipt" /></td>
	    	       <th>联系人：</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="partAudit_s.linkman" value="${partAudit_s.linkman }" /> </td>
	    	      <th>联系电话：</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 120px" name="partAudit_s.phone_no" value="${partAudit_s.phone_no }" /> </td>
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
	</form>
	<form id="gridform" class="grid">
		<grid:grid  from="webpage" >

	<grid:header>
	<grid:cell >选择</grid:cell> 
	<grid:cell sort="partner_name">分销商名称</grid:cell> 
	<grid:cell>分销商编码</grid:cell> 
	<grid:cell >店铺类型</grid:cell> 
	<grid:cell >联系人</grid:cell> 
	<grid:cell >联系电话</grid:cell> 
	<grid:cell >达量</grid:cell> 
	<grid:cell>服务保证金</grid:cell>
	<grid:cell>生效时间</grid:cell>
	<grid:cell>失效时间</grid:cell>
	<grid:cell>申请时间</grid:cell>
	<grid:cell width="50px">操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
	 <grid:cell><input type="radio" name="id" value="${obj.partner_id}" /></grid:cell>
	 <grid:cell ><a title="查看" href="partner!detail.do?flag=view&state=${obj.state }&sequ=${obj.sequ }&partner_id=${obj.partner_id }">${obj.partner_name }</a> </grid:cell>
	 <grid:cell>${obj.partner_code}</grid:cell>
     <grid:cell >${obj.shop_type_desc} </grid:cell> 
     <grid:cell >${obj.linkman} </grid:cell> 
     <grid:cell >${obj.phone_no} </grid:cell> 
     <grid:cell >${obj.need_amount} </grid:cell>
     <grid:cell>${obj.service_cash}</grid:cell> 
     <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${obj.eff_date}"></html:dateformat> </grid:cell> 
     <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${obj.exp_date}"></html:dateformat> </grid:cell>
    <grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${obj.create_date}"></html:dateformat> </grid:cell>
    <grid:cell>
		<a title="审核" class="auditName" partner_id="${obj.partner_id }">审核</a>
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
 

