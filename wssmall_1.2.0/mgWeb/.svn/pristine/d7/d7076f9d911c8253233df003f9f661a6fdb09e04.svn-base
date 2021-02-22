var MessageBox = new function(){
	var msgbox = new Object();
	msgbox.count = 0;
	msgbox.index=0;
	msgbox.id_pre = "msgbox";
	msgbox.hide = function (id,d){ 
		var dom_obj = document.getElementById(id);
		var d = d || false;
		if(dom_obj){
			if(d == true){
				dom_obj.style.display = "none";
			}else{
				dom_obj.parentNode.removeChild(dom_obj);
			} 
		}
	}
	msgbox.$ = function(id){ return document.getElementById(id); }
	msgbox.getid = function(i){
	    if(i<10){
	        return msgbox.id_pre + "00" + i;
	    }else if(i< 100){
	        return msgbox.id_pre + "0" + i;
	    }else{
	        return msgbox.id_pre + i;
	    }
	}
	msgbox.newid = function (){
	    var id_temp = "";
	    for (var i=0; i<msgbox.count; i++){
	        id_temp = msgbox.getid(i); 
	        if(msgbox.$(id_temp)){
	            continue;
	        } 
	        msgbox.index = i;
	        return id_temp;
	    } 
	    id_temp = msgbox.getid(msgbox.count);
	    msgbox.index = msgbox.count;
	    msgbox.count = msgbox.count + 1;
	    
	    return id_temp;
	}
	
	this.show = function (msg,t,_timeout,_fn){ 
		var type = t || 0;
	 	if(type > 2) type = 0;
	 	
	 	var timeout = _timeout || 5000;
	 	var nullFn = function (){};
	 	var d = "gb_tip_layer";
	 	var fn = _fn || d;
		var type_classes = new Array();
		type_classes[0] = "gtl_ico_hits";
		type_classes[1] = "gtl_ico_succ";
		type_classes[2] = "gtl_ico_fail";
		
		var newlayer_id =  msgbox.newid(); 
		
	 	var newlayer = document.createElement("div");
	 	newlayer.id = newlayer_id;
	 	newlayer.className = fn;
	 	newlayer.style.top="30%";
	 	newlayer.style.marginTop = 50 * parseInt(msgbox.index ) + "px";
	 
	 	
	 	var tip_b = document.createElement("span");
	 	tip_b.className = type_classes[type];
	 	newlayer.appendChild(tip_b);
	 	
	 	var tip_c = document.createElement("span"); 
	 	tip_c.innerHTML = msg;
	 	newlayer.appendChild(tip_c);
	 	
	 	var tip_e = document.createElement("span");
	 	tip_e.className = "gtl_end";
	 	newlayer.appendChild(tip_e);
	 	 
	 	document.body.appendChild(newlayer);
	 	
	 	if(timeout > 0){
	 	    setTimeout(function(){ newlayer.parentNode.removeChild(newlayer); }, _timeout);
	 	}
	 	
        return newlayer;
	}
	
	this.remove = function(obj){
	    obj.parentNode.removeChild(obj);
	}
	
	
}