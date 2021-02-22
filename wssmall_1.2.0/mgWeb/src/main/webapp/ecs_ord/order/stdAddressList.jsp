<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
</style>
<form method="post" id="stdaddrsearchform"  >
 <div class="searchformDiv" style ='width:100%' >
 <table>
	<tr>
	    <th>地址名称：</th>
		<td><input type="text" class="ipttxt"  name="stdArrName"  id ="stdArrName" value="${stdArrName}"/>
		<input type="hidden" id="stdOrderId" name="stdOrderId" value="${order_id}" />
		<input type="hidden" id="stdOrderCountyId" name="stdOrderCountyId" value="${county_id}" />
		</td>
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="searchBtn" id="stdsearchBtn" value="搜索" style="margin-right:10px;"/>
	    
		</td>
    </tr>
  </table>
</div>
</form>

 <div class="grid"  >
     <form method="POST"  id="roleform" >
       <grid:grid from="webpage"  formId="stdaddrsearchform" ajax="yes" >
                    <grid:header>
                    	<grid:cell>选择</grid:cell>
                        <grid:cell>标准地址ID</grid:cell>
					  	<grid:cell>标准地址名称</grid:cell> 
					  	<grid:cell>局向编码</grid:cell> 
					</grid:header>
				    <grid:body item="obj">
				    <div id="radioStdDiv">
				    	 <grid:cell><input type="radio" name="selStddo"  value="${obj.standard_addr_name},${obj.post_code},${obj.standard_addr_id}"></grid:cell>
					     <grid:cell>${obj.standard_addr_id}</grid:cell>
					     <grid:cell>${obj.standard_addr_name}</grid:cell>
					     <grid:cell>${obj.post_code}</grid:cell>
					 </div>
					</grid:body>  
				</grid:grid>
				 <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="stdAddrInsureBtn">
		         </div>
</form>
</div>

<script type="text/javascript">


var stdAddrList={
		 search:function(){
			 $("#stdsearchBtn").click(function(){
				 var stdArrName = $("#stdArrName").val();
				 var value1 = $("#stdOrderId").val();
				 var stdOrderId = value1.split(",")[0];
				 var url= app_path+"/shop/admin/orderWarningAction!getStdAddress.do?ajax=yes&first_load=no&stdOrderId="+stdOrderId;
				 
				 Cmp.ajaxSubmit('stdaddrsearchform','selStdAddressDlg',url,{},stdAddrList.search);
			 });
		 },
		initFun : function() {
			var me = this;
			stdAddrList.search();
			stdAddrList.checkRole();
			me.initClk();
		},
		initClk : function(){
			var me = this;
			me.clickRowEvent();
		},
		clickRowEvent : function(){
			$(".grid").find("tr").each(function(i,data){
				$(data).bind("click",function(){
					if(!$(this).find("input[name='selStddo']").is("onchecked")){
						
						var valueOrd = $("#stdOrderId").val();
						var stdOrderId = valueOrd.split(",")[0];
						 
						var countyId = $("[field_name='county_id']").val();
						 
						var standard_addr_id = $(this).find("td:eq(1)").html();
						var post_code = $(this).find("td:eq(3)").html();
						
						var url = app_path+"/shop/admin/orderWarningAction!resourcePre.do?ajax=yes&stdOrderId="+stdOrderId+"&standard_addr_id="+standard_addr_id+"&post_code="+post_code+"&county_id="+countyId;
						$.Loading.show("查询地区相关资源。请稍候...");
						$("#stdAddrInsureBtn").attr("disabled", true); 
						 $.ajax({
							 url:url,
							 dataType:'json',
							 success: function(reply){
								if(reply.result==0) {
									$.Loading.hide();
									$("#stdAddrInsureBtn").attr("disabled", false); 
									$("#extraInfo").val(reply.data.split("|")[0]);
									$("#access_type").val(reply.data.split("|")[1]);
									alert(reply.msg);
								}else {
									$.Loading.hide();
									alert(reply.msg);
								}
						     }
						});
						 
						
					}
				});
			});
		},
		checkRole : function() {
			$("#stdAddrInsureBtn").unbind("click").bind("click", function() {
				var code = $("[name='selStddo']:checked").val();
				if (code == null) {
					alert("请选择地址。");
				} else {
					var value = $("[name='selStddo']:checked").val();					
					var stdArrName = value.split(",")[0];
					var post_code = value.split(",")[1];
					var standard_addr_id = value.split(",")[2];
					var access_type = $("#access_type").val();
					var value1 = $("#stdOrderId").val();
					var stdOrderId = value1.split(",")[0];
					var url = app_path+"/shop/admin/orderWarningAction!saveStdAddress.do?ajax=yes&stdOrderId="+stdOrderId+"&standard_addr_id="+standard_addr_id+"&stdArrName="+stdArrName+"&exch_code="+post_code+"&access_type="+access_type;
					$.Loading.show("查询地区相关资源。请稍候...");
					$("#stdAddrInsureBtn").attr("disabled", true); 
					 $.ajax({
						 url:url,
						 dataType:'json',
						 success: function(reply){
							if(reply.result==0) {
								$.Loading.hide();
								$("#stdAddrInsureBtn").attr("disabled", false); 
								/* $("[field_name='adsl_addr']").css('display','block'); */
								$("[field_name='adsl_addr']").val(standard_addr_id);
								/* $("[field_name='adsl_addr']").css('display','none'); */
								$("[field_name='exch_code']").val(post_code);
								$("[field_name='adsl_addr_desc']").val(stdArrName);
								Eop.Dialog.close("selStdAddressDlg");
								alert(reply.msg);
							}else {
								$.Loading.hide();
								alert(reply.msg);
							}
					     }
					});
					
				}
			});
		}
	};
	$(function() {
		stdAddrList.initFun();
	});
</script>
