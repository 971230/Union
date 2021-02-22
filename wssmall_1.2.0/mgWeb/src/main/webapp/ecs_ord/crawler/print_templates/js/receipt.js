// 准备免填单数据
function putData(retInfo) {
  for ( var key in retInfo) {
    var element = document.getElementById(key);
    if (key == 'remrk') {
      retInfo[key] = "<br/>" + retInfo[key];
    }
    if (element) {
      element.innerHTML = retInfo[key];
    }
  }
  var today = new Date();

  var year = today.getFullYear();
  var month = today.getMonth() + 1;
  var day = today.getDate();
  $('#year1Id').html(year);
  $('#month1Id').html(month);
  $('#day1Id').html(day);

  $('#year2Id').html(year);
  $('#month2Id').html(month);
  $('#day2Id').html(day);
};


function doPrint(){
	$.ajax({
		url : ctx+"/shop/admin/ordPrt!updatePrtStatus.do?ajax=yes&order_id=" + $("#order_id").val()+"&order_is_his="+$("#order_is_his").val(),
		type : "POST",
		dataType : 'json',
		success : function(result) {
			if (result.result == 1) {
				$.Loading.hide();
			    if($.browser.msie) { //IE
			        document.execCommand('print', false, null);
			    }else{
			        window.focus();
			        window.print();
			    }
			    //window.dialogArguments.fresh();
			 
			} else {
				
			}
		},
		error : function(e) {
			$.Loading.hide();
			alert("记录打印出错啦:(");
		}
	});
}
