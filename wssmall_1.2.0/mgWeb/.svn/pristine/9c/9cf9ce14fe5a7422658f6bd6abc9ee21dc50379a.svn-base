<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}
</style>
<div class="input" id="addPP">
<form class="validate" validate="true" method="post" action="" id='addParSss' >
		<div class="tableform">
			<h4>
				新增支付方式
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						<tr>
							<th>
								支付方式编码：
							</th>
							<td id="member_lv_td_sp">
								 <input type="text" class=" ipttxt" id="type" name="pay.type" value="${type}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								支付方式名称：
							</th>
							<td>
								<input type="text" class=" ipttxt" id="name" name="pay.name" value="${name}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								线&nbsp;上&nbsp;标&nbsp;志：
							</th>
							<td>
								 <select id="online_flag" class="ipttxt" name="pay.online_flag" value="${online_flag }"style="width: 154px;height: 25px" required="true" >
								    <option value="" >------请选择----</option>
									<option value="0">线上</option>
									<option value="1">线下</option>
								</select>   
							</td>
						</tr>
						 <tr>
					        <th>银行支付地址：</th>
					        <td>
					             <input type="text" class="ipttxt" id="bank_adss" name="pay.bank_adss" value="${credit.bank_adss}" required="true" autocomplete="on" dataType="string">
					        </td>
					    </tr>
						<tr>
					        <th>温&nbsp;馨&nbsp;提&nbsp;示：</th>
					       <td>
							<textarea rows="2" cols="30" name="pay.tip_info"></textarea>
						   <td>
		  			  </tr> 
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableform">
		    <h4>
				属性设置：     <a href="javascript:void(0);" style="margin-right:10px;" id="propss" name="addBtnProperty" class="graybtn1">属性设置</a>
			</h4>
		<div class="table_list">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr id="grid_head">
							<th style="text-align:center;width: 50px" >属性编码</th>
							<th style="text-align:center;width: 50px">属性名称</th>
							<th style="text-align:center;width: 50px">属性值</th>
							<th style="text-align:center;width: 50px">操作</th>
						</tr>
					</tbody>
					<tbody id="inputtext">
					</tbody>
				</table>
		</div>
		</div>
		 <div class="submitlist" align="center">
			 <input type="button"  id="addSavePay" value=" 保存 " class="submitBtn" name='submitBtn'/>
		</div>

 	</form> 
 </div>
<div id="addPropertyDialogs"></div>
<script type="text/javascript" >
var addPayment = {
	    init:function(){
	    var self = this;
	     $("#propss").unbind("click").bind("click",function() {
	    	 self.addP();
	     });
	     $("a[name='delete']").live("click",function(){

         	if(!confirm("是否确定要删除?")){
               return ;
             } 
         	var tr = $(this).closest("tr");
         	tr.remove();
         });
	 
	  },
	     addP:function(){
		        var tr= "<tr>";
		        tr += "<th style='text-align:center;'><input type='text' class='ipttxt' name='cname' /></th>";
		        tr += "<th style='text-align:center;'><input type='text' class='ipttxt' name='ename' /></th>";
		        tr += "<th style='text-align:center;'><input type='text' class='ipttxt' name='value' /></th>";
				tr += "<th style='text-align:center;'><a href='javascript:void(0);' name='delete'>删除</a></th>";
	            tr += "</tr>";
	            $("#inputtext").append(tr);
	    },
	}; 
	$(function(){
		addPayment.init();
		$("#addSavePay").click(function(){
			 url =ctx+"/shop/admin/creditAction!add.do?ajax=yes";
			 Cmp.ajaxSubmit('addParSss', '', url, {}, function(reply){
				 if(reply.result==1){
			          alert("操作成功!");
			          location.href=ctx+"/shop/admin/creditAction!creditList.do";
			     }	 
		        if(reply.result==0){
		            alert(reply.message);
		        }  

			 }, 'json');
		});
		
	});
</script>

