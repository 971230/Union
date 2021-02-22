
function verifyNombre(nombre){
	var m = /.*[\u4e00-\u9fa5]+.*$/;//含有一个或多个汉字
	if(m.test(nombre)){
		if(nombre.length<3){
			return false;
		}else {
			return true;	
		}
	}else{
		var patrn =/[a-zA-Z0-9_]{3,16}/;
		if((nombre== "")||nombre ==null ){
			 return true;
		}else if(!patrn.test(nombre)){
			return false;
		}else {
			return true;	
		}
	}
}

function verifyPhone(phone){
	if((phone== "")||phone ==null )
		 return true;
	var patrn  = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
	if(!patrn.test(phone)){
		return false;
	}
	return true;
}

function verifyMobile(mobile){
	var patrn =/^[1][0-9]{10}$/;
	if((mobile== "")||mobile ==null ){
		 return true;
	}else if(!patrn.test(mobile)){
		return false;
	}else{
		return true;	
	}
}

function verifyEmail(email){
	var patrn = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)*(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/;
	if((email== "")||email ==null ){
		 return true;
	}else if(!patrn.test(email)){
		return false;
	}else{
		return true;
	}

}


function verifyPuerto(value,contents){
 		 if(value== ""||value ==null || value == undefined){
			return true;
		 }else if(!isNaN(value)){
		 	alert(contents[0]);
			return false;
	 	 }else if(value <1 || value >65536){
	 		alert(contents[1]);
			return false;
	 	 }else{
	 		return true;
	 	 }

}

function verifyIp(ip){
	 var patrn = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
    
 	if((ip== "")||ip ==null ){
		return true;
	}else  if(!patrn.exec(ip)){        
         return false;
     }else{
    	 return true;
     }
}

function verifyMac(mac) {
	 var patrn =/[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}/;
 	if((mac== "")||mac ==null ){
			return true;
     }else if(!patrn.exec(mac)) {        
         return false;
     }{
    	 return true;
     }

}

function isNull(value){
	 if(value== ""||value ==null ){
		return true;
	}else{
		return false;
	}
}

function isNaN(value){
	if(value==null||value==""||value ==undefined){
		return true;
	}
	var exp = /^[0-9]+$/;
	return exp.test(value);
}