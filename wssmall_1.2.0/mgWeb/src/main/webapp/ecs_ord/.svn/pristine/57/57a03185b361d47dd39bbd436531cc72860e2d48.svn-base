<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
  </head>
<script src="<%=request.getContextPath() %>/publics/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<body>
<div>

	 <!-- 外系统订单号导入:开始 -->
                <div class="grid_n_div">
                	<div class="grid_n_cont">
					   <div class="grid_n_cont_sub">
	                        <table >
	                          	<tr>
	                                <td >总商订单号导入：</td>
	                                <td><input type="file" class="ipttxt" name="file" id="excel_type_zb" /> </td>
	                                <td>	<input class="comBtn" type="button"  onclick="ajaxFileUpload('excel_type_zb');" value="确认导入"  style="margin-right:10px;" />
						   		    </td>
	                          	</tr>
	                        </table>
                       </div>  
        	        </div>
        	   </div>
        <!-- 外系统订单号导入:结束 -->	 

</div>
 

</body>
</html>

<script type="text/javascript">
function ajaxFileUpload(excel_from){
	var fileName =$("#"+excel_from).val();
	if (fileName==null||fileName=='') {
		MessageBox.show("上传文件为空 ", 3, 2000);
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	var extPattern = /.+\.(xls|xlsx|csv)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			
		} else {
			MessageBox.show("只能上传EXCEL文件！", 2, 3000);
			return;
		}
	}
	
    $("#loading")
    .ajaxStart(function(){
        $(this).show();
    })//开始上传文件时显示一个图片
    .ajaxComplete(function(){
        $(this).hide();
    });//文件上传完成将图片隐藏起来
    
    $.ajaxFileUpload
    (
        {
            url:'auditZBAction!importacion.do?ajax=yes&file_name='+fileName,//用于文件上传的服务器端请求地址
            secureuri:false,//一般设置为false
            fileElementId:excel_from,//文件上传空间的id属性  <input type="file" id="file" name="file" />
            dataType: 'json',//返回值类型 一般设置为json
            success: function (data, status)  //服务器成功响应处理函数
            {
                alert(data.message);//从服务器返回的json中取出message中的数据,其中message为在struts2中action中定义的成员变量
                
                if(typeof(data.error) != 'undefined')
                {
                    if(data.error != '')
                    {
                        alert(data.error);
                    }else
                    {
                        alert(data.message);
                    }
                }
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                alert(e);
            }
        }
    )
    return false;
}
</script>
	



       
