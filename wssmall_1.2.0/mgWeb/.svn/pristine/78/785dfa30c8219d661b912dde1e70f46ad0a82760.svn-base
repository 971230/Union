<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="grid" id="moduleList">
<table width="100%" cellspacing="0" cellpadding="3" >
	<col class="span-4 ColColorGray">
	<col class="span-2 ColColorOrange">
	<col class="textleft">
	
	<thead>
		<tr>
			<td width="20px">模板编号</td>
			<td width="20px">摸板名称</td>
			<td width="20px">模板信息</td>
			<td width="20px">平台</td>
			<td width="20px">操作</td>
		</tr>
	</thead>
       <tr id="table_module">
           <td width="20px">${temp.temp_inst_id}</td>
           <td width="20px">${temp.temp_name}模板</td>
           <td width="20px">${temp.temp_cols}</td>
           <td width="20px">${temp.source_from}</td>
           <input type="hidden" name="temp.goods_id" value="${goodId }">
           <td>
              <a href='javascript:;' name="edits"  temp_inst_id="${temp.temp_inst_id}">修改</a>
              <a href='javascript:;' name="dels" temp_inst_id="${temp.temp_inst_id}">删除</a>
	       </td>
       </tr>
        </table>
    </div>
    <!--属性管理弹出层-->
    <div id="moduleEdit"></div>
<script type="text/javascript" src="plugin/moduleList.js"></script>
<script type="text/javascript">
  var serviceAppModule = {
  init:function(){
    var self = this;
    $("a[name='dels']").live("click",function(){
       var temp_inst_id = $(this).attr("temp_inst_id");
       //alert(temp_inst_id+"=insy");
       self.del(temp_inst_id);
       
    });
  },
  del:function(temp_inst_id){
     var self = this;
     if(!confirm("是否确定要删除?")){
        return ;
     }  
	   url ="goodsPropertyAction!delModule.do?ajax=yes&temp_inst_id="+temp_inst_id;
       Cmp.excute('', url, {}, self.delJsonBack, 'json');
      	
  },
   delJsonBack:function(reply){
        if(reply.result==0){
          alert(reply.message);
        }  
        if(reply.result==1){
        alert("操作成功");
        
        }
   }, 
};
$(function(){
  serviceAppModule.init();
});
   
</script>
