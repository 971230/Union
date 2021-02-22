<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="goodsRefAttr/index.js"></script>

<style>
.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}
.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}
h4{
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}
</style>
<form method="post" id="tmpl_attr_goods_form">
<div class="rightDiv" style="overflow: auto;">
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_1" class="selected">
						<span class="word">模板属性配置</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
	
<div class="input" >
	<div class="tableform">
   <h4>商品列表:
	    <a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" name="allGoodsBtn" id="allGoodsBtn"><span>添加商品</span></a>
  </h4>
	<div class="division" id ="contractDiv">
	  <table width="100%" id="tables">
	    <thead>
		<tr>
		 	 <td><input type="hidden" onclick="selectedChange()"  id="togggleChked" /></td> 
			<td >商品名称</td>
			<td >商品编号</td>
			 <td >操作</td>
		</tr>
	    </thead>
    <c:forEach var="pro" items="${property}">
       <tr >
          <td><input type="hidden" name="selecteds" nams="${pro.name }" value="${pro.goods_id }" gid="${pro.goods_id }" onclick="selectedChan()" /></td>
           <td>${pro.goods_id}</td>
           <td>${pro.goods_name}</td>
           <td>${pro.sn}</td>
           <td><a href='javascript:;' id="gooddelete">删除</a></div></td>
       </tr>
     </c:forEach>
     </table>
    </div>
	
</div>	
</div>
<div class="input1" >
	<div class="tableform">
    <h4>设置商品公共属性: <a href="javascript:void(0)" style="margin-right:10px;" goods="" class="graybtn1" name="selTypeBtn" id="addProperty1"><span>添加商品属性</span></a>
	</h4>
	<div class="division" id ="contractDiv1">
	 <table width="100%" cellspacing="0" cellpadding="3" id="table_module1">
		<thead>
		<tr>
			<td style="width: 100px"><input type="checkbox" id="toggleChks"  onclick="selectedChangess();"/></td>
			<td style="text-align: center;width: 150px">属性业务类型</td>
			<td style="text-align: center;width: 150px">映射属性英文名</td>
			<td style="text-align: center;width: 150px">属性中文名称</td>
			<td style="text-align: center;width: 150px">所属表</td>
			<td style="text-align: center;width: 150px">属性英文名</td>
			<td style="text-align: center;width: 150px">操作</td>
		</tr>
	</thead>
	 <c:forEach var="pro" items="${property}">
         <tr >
             <td>${pro.attr_spec_type}</td>
             <td>${pro.field_name}</td>
             <td>${pro.field_type}</td>
             <td>${pro.rel_table_name}</td>
             <td>${pro.o_field_name}</td>
             <td><a href='javascript:;' name="edit" id="#editPropertyswes" >修改</a>&nbsp;&nbsp;<a href='javascript:;' name="del" field_attr_id=${pro.field_attr_id}>删除</a></div></td>
         </tr>
      </c:forEach>
     </table>
  </div>
</div>
<div class="submitlist" align="center" style="display: none;" >
	 <table>
	 <tr>
	 <th></th>
	 <td>
        <input  type="button"  value=" 确定 " class="submitBtn" id="selectOkBotts"/>
	 </td>
	 </tr>
	 </table>
 </div>
</div>
<div class="input2" >
	<div class="tableform">
	<h4>
		已选模板属性
	</h4>
	<div class="division" id ="contractDiv2">
		<table width="100%" id="select_attr_table2">
	    <thead>
		<tr>
		    <td style="text-align: center;width: 150px"><input type="hidden" id="toggleChks"  onclick="selectedChangess();"/></td>
			<td style="text-align: center;width: 200px">属性业务类型</td>
			<td style="text-align: center;width: 200px">映射属性英文名</td>
			<td style="text-align: center;width: 200px">属性中文名称</td>
			<td style="text-align: center;width: 200px">所属表</td>
			<td style="text-align: center;width: 200px">属性英文名</td>
			<td style="text-align: center;width: 200px">操作</td>
		</tr>
	</thead>
    
     </table>
	</div>
</div>
</div>
<div class="input2" style="height:100px ">
<div class="submitlist" align="center"  >
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 保存 " class="submitBtn" id="selectOkBottf"/>
	 </td>
	 </tr>
	 </table>
 </div>
</div>
</div>
</form>
<div id="listAllGoods"></div>
<div id="listAllType"></div>
<div id="listAllStype"></div>
<div id="addGoodsProperties"></div>
<script type="text/javascript">
var num=0;
function selectedChangess(){
	   $("#contractDiv1").find("input[name=idds]").attr("checked",$("#toggleChks").is(":checked"));
	   	 $("#contractDiv1").change(function (){
 	  $("#selectOkBotts").trigger("click");
   });
};
//单选

function selectedChanss(obj){
	if(obj.checked){
		var newtr = $(obj).parents("tr").clone();
		var ids=newtr.attr("num");
		newtr.attr("id","ids"+ids);
		
		newtr.find("input").remove();
		newtr.find("a").remove();
		newtr.find("td:last").remove();
		$("#select_attr_table2").append(newtr.append('<td style="text-align:center"><a href="javascript:void(0);" name="deletedd">删除</a></td>'));
		$(obj).closest("tr").find("input[name=hasselect]").val("on");
	}else{
		
		$(obj).closest("tr").find("input[name=hasselect]").val("off");
		var tr=$(obj).parents("tr");
		//tr.remove();
		$("#ids"+ids).remove();
	}
 }

var GoodsPropertys = {
        init:function(){
            var me=this;
            $('#addProperty1').bind('click', function () {
                me.newActions();
            });
            $('.editPropertyswes').live('click', function () {
            	var tr = $(this).parents("tr");
				$("#addGoodsProperties").load('goodsPropertyAction!saveProperty.do?ajax=yes', function () {
					var name = tr.find("input[name='name']").val();
	            	$("#field_name").val(name);
	                var desc=tr.find("input[name='desc']").val();
	            	$("#field_desc").val(desc);
	            	var code=tr.find("input[name='code']").val();
	            	$("#attr_code").val(code);
	            	var value=tr.find("input[name='value']").val();
	            	$("#default_value").val(value);
	            	var dd=tr.find("input[name='value_desc']").val();
	            	$("#default_value_desc").val(dd);
	            	var fields=tr.find("input[name='table_fields']").val();
	            	$("#owner_table_fields").val(fields);
	            	var taname=tr.find("input[name='table_name']").val();
	            	$("#rel_table_name").val(taname);
	            	var names=tr.find("input[name='field_names']").val();
	            	$("#o_field_name").val(names); 
	            	tr.remove();
	            });
	            Eop.Dialog.open("addGoodsProperties");
            });
            Eop.Dialog.init({id : "addGoodsProperties",title : "属性管理" ,width:"380px", height:"450px"});
        },
        newActions: function () {
			   var select = $("#tables input[gid]");
			   if(!select || select.length==0){
				   alert("请先选择商品!");
				   return ;
			   }
	            var editGoodsId=$('#editGoodsId').val();
	            $("#addGoodsProperties").load('goodsPropertyAction!saveProperty.do?ajax=yes&editGoodsId='+editGoodsId, function () {
					
	            });
	            Eop.Dialog.open("addGoodsProperties");
        },
    }

    GoodsPropertys.init();
    
 $("a[name='deletedd']").live("click",function(){
	var tr = $(this).closest("tr");
	tr.remove();
});

$("a[name='deletec']").live("click",function(){
	var tr = $(this).closest("tr");
	tr.remove();
}); 
$("a[name='gooddelete']").live("click",function(){
 	var tr = $(this).closest("tr");
 	tr.remove();
 });
$(function(){
	$("#selectOkBottf").click(function(){
		 url =ctx+"/shop/admin/goodsPropertyAction!addTempModule.do?ajax=yes";
		 Cmp.ajaxSubmit('tmpl_attr_goods_form', '', url, {}, function(reply){
			 if(reply.result==1){
		          alert("操作成功!");
		          location.href=ctx+"/shop/admin/goodsPropertyAction!forwardIndexJsp.do";
		     }	 
	        if(reply.result==0){
	            alert("操作失败,请完整填完信息！");
	        }  
		 }, 'json');
	});
	
});
</script>



