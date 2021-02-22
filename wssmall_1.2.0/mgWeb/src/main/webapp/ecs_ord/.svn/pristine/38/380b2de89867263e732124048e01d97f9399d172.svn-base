function ajaxFileUpload(excel_from){
    var myBt=document.getElementById(excel_from+"_bt")
	myBt.disabled = true;
    myBt.value = "上传中...";
	
	var fileName =$("#"+excel_from).val();
	if (fileName==null||fileName=='') {
		MessageBox.show("上传文件为空 ", 3, 2000);
		myBt.disabled = false;
	    myBt.value = "确认导入";
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	var extPattern = /.+\.(xls|xlsx|csv)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			
		} else {
			MessageBox.show("只能上传EXCEL文件！", 2, 3000);
			myBt.disabled = false;
		    myBt.value = "确认导入";
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
            url:'moneyAuditExcleAction!importacion_orderInput.do?ajax=yes&file_name='+fileName,//用于文件上传的服务器端请求地址
            secureuri:false,//一般设置为false
            fileElementId:excel_from,//文件上传空间的id属性  <input type="file" id="file" name="file" />
            dataType: 'json',//返回值类型 一般设置为json
            success: function (data, status)  //服务器成功响应处理函数
            {
            	
                alert(data.excel_message);//从服务器返回的json中取出message中的数据,其中message为在struts2中action中定义的成员变量
                myBt.disabled = false;
        	    myBt.value = "确认导入";
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
                myBt.disabled = false;
        	    myBt.value = "确认导入";
            }
        }
    )
    return false;
}