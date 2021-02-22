<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>

<style>

ul.tab li {
	min-width: 70px;
}
</style>

<div class="input">
	<form method="post" name="productForm" action="goods!addfengXingDongMapping.do" id="addFxdMappingForm" class="validate">
		<div style="display: block;" class="goods_input" id="productTabli">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active">
						基本信息
					</li>
				</ul>
			</div>
			<div class="tab-page">
				<div class='clear'></div>
				<div tabid="tab_0" class="tab-panel">
					<table class="form-table" style="width: 100%; float: left" id='base_table'>
						<tr>
							<th>
								<span class="red">*</span>配置类型：
							</th>
							<td id="model_code_td">
								<select name="stype" style="width:200px;" class="ipttxt1" id="stype"  required="true">
									<option value="">请选择货品型号</option>
									<option value="DIC_FXD_GOODS">总部蜂行动</option>
									<option value="DIC_BLD_GOODS">京东便利店</option>
								</select>
								<div id="showResult_stype"></div>
							</td>
						</tr>
						
						<tr id="sku_tr" style="">
							<th>
								<span class="red">*</span>总部商品编码：
							</th>
							<td>
								<input type="text" name="pkey" id="pkey" required="true" 
								style="width:200px;" class="top_up_ipt" value=""  />
								 <div id="showResult"></div>
							</td>
						</tr>
						<tr id="sku_tr" >
							<th style="width:120px;">
								<span class="red">*</span>订单中心商品编码：
							</th>
							<td>
								<input type="text" name="pname" id="pname" required="true" 
								style="width:200px;" class="top_up_ipt" value=""  />
								 <div id="showResult_pname"></div>
							</td>
							
						</tr>
						<tr id="sku_tr" style="">
							<th>
								产品编码：
							</th>
							<td>
								<input type="text" name="codea" id="codea" required="true" 
								style="width:200px;" class="top_up_ipt" value=""  />
							</td>
						</tr>
						<tr id="sku_tr" style="">
							<th>
								总部商品名称：
							</th>
							<td>
								<input type="text" name="codeb" id="codeb" required="true" 
								style="width:200px;" class="top_up_ipt" value=""  />
							</td>
						</tr>
						<tr id="sku_tr" style="">
							<th>
								备注：
							</th>
							<td>
								<input type="text" name="comments" id="comments" required="true" 
								style="width:200px;" class="top_up_ipt" value="${comments}"  />
							</td>
						</tr>
					</table>
					<div style="text-align: center;padding-top:30px;">
						<a id="lastStep"  formId="productForm" class="greenbtnbig" href="javascript:history.back(-1)">返回</a>
						<a id="nextStep"  onclick="formSubmit();" class="greenbtnbig" href="javascript:void(0)">确认</a>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<script type="text/javascript">
function formSubmit(){
	var flag = check();
	if(flag){
		 $('#addFxdMappingForm').submit(); 
	}
		/*
		 var typeVue = $('#stype option:selected').val();//选中的值
		 var inputUserNameObj = $("#pkey");   //将获取ID为pkey的控件的对象  
		 var text = inputUserNameObj.val();   //获得用户输入的pkey 
		 var pname = $('#pname').val();//选中的值
		 $.ajax({
             type : "post",
             url : 'goods!member_Check.do',
             data : {
            	 'stype' : typeVue,
            	 'pkey' : text,
            	 'pname':pname
             },
             dataType : "json",//设置需要返回的数据类型
             success : function(response) {
            	 document.getElementById("showResult").innerHTML="<font color='red'>"+"总部商品编码和配置类型已存在"+"</font>";
                 alert(response);
                 alert("succ");
             },
             error : function(response) {
            	 alert("error");
                 alert(response);
             }
         });
		 
		 
		 /*
		 $.post("goods!member_Check.do?stype="+typeVue+"&pkey="+text,null,function(params){  
			 alert(params);
	         if(params.flag == '0'){//重复
	            document.getElementById("showResult").innerHTML="<font color='red'>"+"总部商品编码和配置类型已存在"+"</font>";
	         }else{
	        	 $('#addFxdMappingForm').submit(); 
	         }
		 });
		 */
	
      
     function check(){
    	 var flag = true;
    		var vue = $('#stype option:selected').val();//选中的值
    		if(vue == null||vue==""){
    			document.getElementById("showResult_stype").innerHTML="<font color='red'>"+"配置类型不能为空"+"</font>"; 
    			document.getElementById("stype").focus();//用户名输入控件获得焦点 
    		}else{
    			document.getElementById("showResult_stype").innerHTML=""; 
    		}
    		
    		 var inputUserNameObj = $("#pkey");   //将获取ID为pkey的控件的对象  
    		 var text = inputUserNameObj.val();   //获得用户输入的pkey 
    	     if(text == null || text == ""){
    	     	 document.getElementById("showResult").innerHTML="<font color='red'>"+"总部商品编码不能为空"+"</font>"; 
    	     	 document.getElementById("pkey").focus();//用户名输入控件获得焦点 
    	     }else{
    	     	 document.getElementById("showResult").innerHTML=""; 
    	     }
    	     
    	     var pname = $('#pname').val();//选中的值
    	     if(pname == null||pname==""){
    	 		document.getElementById("showResult_pname").innerHTML="<font color='red'>"+"订单中心商品编码不能为空"+"</font>"; 
    	 		document.getElementById("pname").focus();//用户名输入控件获得焦点 
    	 	}else{
    	 		document.getElementById("showResult_pname").innerHTML=""; 
    	 	}
    	     
    	     if((pname == null||pname=="")||(text == null || text == "")||(vue == null||vue=="")){
    	    	 flag = false;
    	     }
    		return flag;
      }
}
</script> 
<script type="text/javascript">  
  $(document).ready(function(){  
	  
	$("#stype").blur(function(){
		var vue = $('#stype option:selected').val();//选中的值
		if(vue == null||vue==""){
			document.getElementById("showResult_stype").innerHTML="<font color='red'>"+"配置类型不能为空"+"</font>"; 
			document.getElementById("stype").focus();//用户名输入控件获得焦点  
		}else{
			document.getElementById("showResult_stype").innerHTML=""; 
		}
	});
	  
	$("#pname").blur(function(){
		 var pname = $('#pname').val();//选中的值
	     if(pname == null||pname==""){
	 		document.getElementById("showResult_pname").innerHTML="<font color='red'>"+"订单中心商品编码不能为空"+"</font>"; 
	 		document.getElementById("pname").focus();//用户名输入控件获得焦点  
	 	}else{
	 		document.getElementById("showResult_pname").innerHTML=""; 
	 	}
	});
	
    var inputUserNameObj = $("#pkey");   //将获取ID为pkey的控件的对象  
    $("#pkey").blur(function(){   //当该控件失去焦点时发生  
        var text = inputUserNameObj.val();   //获得用户输入的pkey 
        if(text == null || text == ""){
        	 document.getElementById("showResult").innerHTML="<font color='red'>"+"总部商品编码不能为空"+"</font>"; 
        	 document.getElementById("pkey").focus();//用户名输入控件获得焦点  
        }else{
        	 document.getElementById("showResult").innerHTML=""; 
        }
       
    });     
});  
</script>

