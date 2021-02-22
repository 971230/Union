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
<form class="validate" validate="true" method="post" action="" id='editParS' >
		<div class="tableform">
			<h4>
				新增支付方式
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<input type="hidden" id="ids" value="${id}">
					<tbody>
						<tr>
							<th>
								支付方式编码：
							</th>
							<td id="member_lv_td_sp">
								  <input type="text" class=" ipttxt" id="type" name="credit.type" value="${credit.type}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								支付方式名称：
							</th>
							<td>
								<input type="text" class=" ipttxt" id="name" name="credit.name" value="${credit.name}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								线&nbsp;上&nbsp;标&nbsp;志：
							</th>
							<td>
								<select id="online_flag" class="ipttxt" name="credit.online_flag" value="${credit.online_flag }"style="width: 154px;height: 25px" required="true" >
									<option value="0">线上</option>
									<option value="1">线下</option>
								</select>   
							</td>
						</tr>
						 <tr>
					        <th>银行支付地址：</th>
					        <td>
					             <input type="text" class="ipttxt" id="bank_adss" name="credit.bank_adss" value="${credit.bank_adss}" required="true" autocomplete="on" dataType="string">
					        </td>
					    </tr>
						<tr>
					        <th>温&nbsp;馨&nbsp;提&nbsp;示：</th>
					       <td>
								<textarea rows="2" cols="30" name="credit.tip_info">${credit.tip_info }</textarea>
						   <td>
		  			  </tr> 
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableform">
		    <h4>
				属性设置：  <a href="javascript:void(0);" style="margin-right:10px;" id="addProp" name="addBtnProperty" class="graybtn1">属性设置</a>
			</h4>
		<div class="table_list">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr id="grid_head">
							<th style="text-align:center; ">属性编码</th>
							<th style="text-align:center; ">属性名称</th>
							<th style="text-align:center; ">属性值</th>
							<th style="text-align:center; ">操作</th>
						</tr>
					</tbody>
					<tbody id="inputTest">
						 <c:forEach var="prop" items="${attrPay }">
							<tr>
								<th style="text-align:center; "><input name="cname" type="text" class='ipttxt' value="${prop.cname }"/></th>
								<th style="text-align:center; "><input name="ename" type="text" class='ipttxt'value="${prop.ename }"/></th>
								<th style="text-align:center; "><input name="value" type="text" class='ipttxt' value="${prop.value }"></th>
								<th style="text-align:center; "><a href="javascript:;"  name="deleteEditProper" class="delPaymentsc" >删除</a>	</th>
							</tr>
						</c:forEach>
				 </tbody>
				</table>
		</div>
		</div>
		 <div class="submitlist" align="center" style="height: 100px">
			 <input type="button"  id="editSavePay" value=" 保存 " class="submitBtn" name='submitBtn'/>
		</div>
		
 	</form> 
 </div>
<div id="addPropertyDialogs"></div>
<script type="text/javascript" >
$(function(){
	
	$("#editSavePay").click(function(){
		var id=$("#ids").val();
		 url =ctx+"/shop/admin/creditAction!editSave.do?ajax=yes&credit.id="+id;
		 Cmp.ajaxSubmit('editParS', '', url, {}, function(reply){
			 if(reply.result==1){
		          alert("操作成功!");
		          location.href=ctx+ "/shop/admin/creditAction!creditList.do";
		     }	 
	      if(reply.result==0){
	        alert(reply.message);
	      }  

		 }, 'json');
	});
	
});
var addProperss= {
        init:function(){
            var me=this;
            $("#addProp").unbind("click").bind("click",function() {
		      me.addP();
	        });
            
            $("a[name='deleteEditProper']").live("click",function(){

            	if(!confirm("是否确定要删除?")){
                  return ;
                } 
            	var tr = $(this).closest("tr");
            	tr.remove();
            });
        },
        addP:function(){
	        var tr= "<tr>";
	        tr += "<th style='text-align:center;'><input type='text'  class='ipttxt' name='cname' value=''></th>";
	        tr += "<th style='text-align:center;'><input type='text'  class='ipttxt' name='ename' value=''></th>";
	        tr += "<th style='text-align:center;'><input type='text'  class='ipttxt' name='value' value=''></th>";
			tr += "<th style='text-align:center;'><a href='' name='deleteEditProper'>删除</a></th>";
            tr += "</tr>";
            $("#inputTest").append(tr);
            
            
    },
  
}


addProperss.init(); 

 
</script>

