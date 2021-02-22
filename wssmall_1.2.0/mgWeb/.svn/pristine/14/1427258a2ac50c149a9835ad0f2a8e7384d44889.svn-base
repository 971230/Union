<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript" src="goods/js/es_terminal_list.js"></script>

<form method="post"
    action='goods!esTerminalList.do' id="queryForm">
    <div class="searchformDiv">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr>
                    <th>机型编码:&nbsp;&nbsp;</th>
                    <td nowrap="nowrap">
                        <input type="text"  style="width:150px" name="sn"
                               value="${esTerminal.sn }" class="ipttxt" />              
                    </td>
                    <th>虚拟串号:&nbsp;&nbsp;</th>
                    <td >
                        <input type="text"  style="width:150px" name="terminal_no"
                               value="${esTerminal.terminal_no }" class="ipttxt" />      
                    </td>
                    <td><input type="submit" style="margin-right:10px;"
                        class="comBtn" value="搜&nbsp;索" type="submit" 
                        name="button"></td>
                </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="grid comBtnDiv" >
    <ul>
        <li>
            <div>
               <form id="esTerminalUploadForm" action="goods!importEsTerminal.do" method="post" enctype="multipart/form-data">
                   <input type="file" class="ipttxt" id="esTerminalUploadFile" name="file" /> 
                   <a href="javascript:void(0);" id="impBtn" style="margin-right:10px;" class="graybtn1">导入</a>
                   <a href="javascript:void(0);" id="downTempBtn" style="margin-right:10px;" class="graybtn1">下载模版</a>
                   <a href="goods!esTerminalDetail.do?action=add" id="addBtn" style="margin-right:10px;" class="graybtn1">添加</a>
                   <a href="javascript:void(0);" id="deleteBtn" style="margin-right:10px;" class="graybtn1">删除</a>
                </form>
            </div>  
        </li>
    </ul>
    <div style="clear:both"></div>
</div>

<div class="grid">
    
    <form method="POST" >
    <grid:grid  from="webpage">
    
        <grid:header>
            <grid:cell width="10px"><input type="checkbox" id="toggleChk" /></grid:cell>
            <grid:cell width="250px">机型编码</grid:cell> 
            <grid:cell width="250px">虚拟串号</grid:cell> 
            <grid:cell width="100px">操作</grid:cell> 
        </grid:header>
    
      <grid:body item="esTerminal">
            <grid:cell><input type="checkbox" name="id" value="${esTerminal.sn }" /></grid:cell>
            <grid:cell>${esTerminal.sn } </grid:cell>
            <grid:cell>${esTerminal.terminal_no } </grid:cell>
            <grid:cell> <a  href="goods!esTerminalDetail.do?action=edit&esTerminal.sn=${esTerminal.sn }" ><img class="modify" src="images/transparent.gif" ></a> </grid:cell> 
      </grid:body>  
      
    </grid:grid>  
    </form> 
    <div style="clear:both;padding-top:5px;"></div>
</div>
<div id="terminalImportDiv"></div>
<script>
$(function(){
    if('${params.inputMsg}'){
        MessageBox.show('${params.inputMsg}', 3, 2000);
    }
});
$("#impBtn").click(function(){
    var fileName = $("#esTerminalUploadFile").val();
    if (isNull(fileName)) {
        MessageBox.show("上传文件为空 ", 3, 2000);
        return;
    }
    var extPattern = /.+\.(xls|xlsx)$/i;
    var url = ctx + "/shop/admin/goods!importEsTerminal.do?fileNameFileName="+encodeURI(encodeURI(fileName));
    $("#esTerminalUploadForm").attr("action",url);
    if ($.trim(fileName) != "") {
        if (extPattern.test(fileName)) {
            $("#esTerminalUploadForm").submit();
        } else {
            MessageBox.show("只能上传EXCEL文件！", 2, 3000);
            return;
        }
    }
    //$.Loading.show("正在导入，请耐心等待...");
}); 

$("#downTempBtn").click(function(){
	var url = ctx + "/servlet/batchAcceptExcelServlet?url=/shop/admin/goods/goods_import_logs_ecs.jsp&lx=mb&service=terminalCodeImport";
	var temp_form = '<form id="hidden_export_excel_form" action="'+url+'" method="post"></form>';
	if($("#hidden_export_excel_form").length>0)
		$("#hidden_export_excel_form").remove();
	$("#terminalImportDiv").append(temp_form);
	$("#hidden_export_excel_form").submit();
}); 


</script>