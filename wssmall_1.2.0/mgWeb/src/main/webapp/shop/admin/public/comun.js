var diaLog = "";
var checkSubFlag =false;

 function doAjax(form,url,call,diaLogId){
		diaLog = ''||diaLogId;
		if(url.indexOf("?") > 0 ){
			url += "&ajax=yes&time="+new Date().getTime();
		}else{
			url += "?ajax=yes&time="+new Date().getTime();
		}
		Cmp.ajaxSubmit(form, '', url, {}, callBack, 'json');
 }
 
 function initDialog(id,modal,title,height,width){
	Eop.Dialog.init( {
		id : id,
		modal : modal,
		title : title,
		height : height,
		width : width
	});
}
 
function vacioForma(id,formaId){
	if(id==null){
		$(':input',"#"+formaId)   
		 .not(':button, :submit, :reset, :hidden')   
		 .val('')   
		 .removeAttr('checked')   
		 .removeAttr('selected');  
	}
}

function initChildDiv(div,id,ck,url){
	bindClick(div,id,ck,url);
	//defaultClick(div,id,url);
}


function abrirCajaVentana(divId,url){
	url = $.trim(url);
	if(url.indexOf("?") > 0 ){
		url += "&ajax=yes&time="+new Date().getTime();
	}else{
		url += "?ajax=yes&time="+new Date().getTime();
	}
	$("#"+divId).load(url, function() {
	});
	
	abrirCaja(divId);
}

function cerrarCaja(id){
	 Eop.Dialog.close(id);
 }
 
 function abrirCaja(id){
	  Eop.Dialog.open(id);
 }
  
  function callBack(reply){
		var data = eval(reply);
	     if(reply.success){
					MessageBox.show(data.params.message, 1, 2000);
	         	if(diaLog!=""&&diaLog!=undefined){
	         		cerrarCaja(diaLog);
	         	}
	         	setTimeout(function(){window.location.href=data.params.url;},500);
			  
     	   }else {
  				MessageBox.show(data.params.message, 2, 2000);
       	   }  
 }


function getEckValue(keyName){
	var count = 0;
	var ids = "",names="";
	var arrs=new Array();
	$("input[name='"+keyName+"']:checked").each(function() {
		var v = this.value;
		v = $.trim(v);
		ids += v + ",";
		count++;
	});
	$("input[name='"+keyName+"']").each(function() {
		if($(this).prev().hasClass("checked")){
			names+=$(this).next().html()+",";
		}
	});
	//是否选择了淘宝商城,淘宝没有下级
	if($("input[type='checkbox'][id='10001']").prev().hasClass("checked")){
		ids += "10001,";
	}
	ids = ids.substring(0, ids.length - 1);
	names = names.substring(0, names.length - 1);
	arrs[0] = count; 
	arrs[1] = ids;
	arrs[2] = names;
	return arrs;
}

function getCostumbreValue(keyName,costumbre){
	var count = 0;
	var ids = "";
	var arrs=new Array();
	$("input[name='"+keyName+"']:checked").each(function() {
		ids += this.getAttribute(costumbre)+ ",";
		count++;
	});
	ids = ids.substring(0, ids.length - 1);
	arrs[0] = count;
	arrs[1] = ids;
	return arrs;
}

function before(value) {
	var arrs  = getEckValue(value);
	if (arrs[0] < 1) {
		MessageBox.show("请选择要操作的记录", 3, 2000);
		return "0";
	}
	return  arrs[1];
}


function timerF(execute,time){
			setTimeout(function() {
				eval(execute)
		}, time);
}


function defaultClick(div,id,url){
		var count = $("input[name="+id+"]:checked").length;
		if(count<1){
			$eck =  $("input[name="+id+"]")[0];
			if($eck!=null)
        	$eck.checked=true;
		}
		
        $(".grid table tr:first-child").addClass("clickClass");
		beforeWarp(div,url);
}

function bindClick(div,id,ck,url){

		$(".grid table tr").click(function(){
	  	    //$(".grid table tr").attr("class","");
           //$(this).attr("class","clickClass");
			$("input[name=eckBox]").each(function() {
				$(this).attr("checked", false);
			});
			var v = $("input[name="+id+"]",$(this)).attr(ck);
			warpDiv($("#"+div), url+v);
   });
}

function  warpDiv(loadDivId,url){
        loadDivId.show();
        loadDivId.empty();
        setTimeout(function() {
				    Ztp.AUI.loadUrlInFrmByUrl(url,loadDivId);
		}, 500);

 }

function beforeWarp(div,url){
		         warpDiv($("#"+div),url);
}


function ecc(k,v) {
	if ($("input[id='"+k+"']").attr("checked") == false) { // 全选
		$("input[name='"+v+"']").each(function() {
			$("input[name='eckBox']").attr("checked", false);
		});
	} else { // 取消全选
		$("input[name='"+v+"']").each(function() {
			$("input[name='"+v+"']").attr("checked", true);
		});
	}
}