var ImportExcle = {
		init:function(){
			//this.importacion();
			
			
	   },
	   importacion :function() {
		   $("form[name='import_from']").each(function(){
			   var form=$(this);
			   form.find("input[name='submit_import_form']").click(function(){
				   var f_form= $(this).parents("form[name='import_from']");
				   var fileName =f_form.find("input[name='file']").val();
					if (fileName==null||fileName=='') {
						MessageBox.show("上传文件为空 ", 3, 2000);
						return;
					}
					fileName = encodeURI(encodeURI(fileName));
					var extPattern = /.+\.(xls|xlsx)$/i;
					if ($.trim(fileName) != "") {
						if (extPattern.test(fileName)) {
							form.submit();
						} else {
							MessageBox.show("只能上传EXCEL文件！", 2, 3000);
							return;
						}
					}
					$.Loading.show("正在导入，请耐心等待...");
				});
			});
		}
}

function export_wl_info(){ 
	var batch_number=$("#batch_number").val();
	if(confirm("请确认已经完成周稽核，否则导出数据为空")){
		$.ajax({
			type : "post",
			async : false,
			url:"moneyAuditAction!expOfflinePay.do?ajax=yes&batch_number="+batch_number,
			data : {
				
			},
			dataType : "json",
			success : function(data) {
					document.getElementById("exportTemplate").click();
			}
		});
	}
	
	
 
}
function ajaxFileUpload(excel_from){
    var myBt=document.getElementById(excel_from+"_bt")
	myBt.disabled = true;
    myBt.value = "上传中...";
	
	var batch_number=$("#batch_number").val();
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
            url:'moneyAuditExcleAction!importacion.do?ajax=yes&excel_from='+excel_from+'&batch_number='+batch_number+'&file_name='+fileName,//用于文件上传的服务器端请求地址
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
function auditData(type){
	
	var myBt=document.getElementById(type+"_audit_bt")
	var oldVal=myBt.getAttribute("value");
   
	
	if(window.confirm('稽核批次号'+$("#batch_number").val()+',请确认excel数据已导入，否则稽核结果不准确？')){
        //alert("确定");
		myBt.disabled = true;
		 myBt.value = "稽核中...";
		  $.ajax({
				type : "post",
				async : false,
				url:"moneyAuditAction!auditData.do?ajax=yes",
				data : {
					"excel_from" : type,
					"batch_number" :$("#batch_number").val()
				},
				dataType : "json",
				success : function(data) {
					if(data.result=='0'){//成功
						alert("稽核成功");
					}else{
						alert("稽核失败，"+data.msg);
					}
					 myBt.disabled = false;
					 myBt.value = oldVal;//还原
				}
			});
        return true;
     }else{
        //alert("取消");
        return false;
    }
	 
}
/**
 * 折叠与展开
 */
function openDiv(){
	var me = window.splitScreen;
	$(".openArrow,.closeArrow").each(function(){
		$(this).attr("class","closeArrow");
		$(this).attr("href","javascript:void(0);");
		$(this).click(function(){
		  var grid_n_div = $(this).parent().parent();
		  var grid_n_cont = grid_n_div.find("[class='grid_n_cont']");
		
		  var openOrCloseClass = $(this).attr("class");
		  if(openOrCloseClass =="openArrow"){
			  $(this).attr("class","closeArrow");
			  grid_n_cont.show();
		  }
		  if(openOrCloseClass =="closeArrow"){
			  $(this).attr("class","openArrow");
			  grid_n_cont.hide();
		  }
		  if(me.canNext && me.waitInsts.length>0){
			  window.setTimeout(function(){me.scrollJspInst($(this))},150);
				return false
		  }
		});
	});
}


$(function(){
	/*$(".closeArrow").focus();*/
	/*$("#batch_number").blur(false); */
	 ImportExcle.init();
	 openDiv();
});