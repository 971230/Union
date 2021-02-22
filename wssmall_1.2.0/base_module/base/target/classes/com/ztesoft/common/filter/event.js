/**
 * 事件工具
 */
var WssNet={
	EventUtil:{
		addHandler:function(element,type,handler){
			if(element.addEventListener){
				element.addEventListener(type,handler,false);
			}else if(element.attachEvent){
				element.attachEvent("on"+type,handler);
			}else{
				element["on"+type]=handler;
			}
		},
		getEvent:function(event){
			return event?event:window.event;
		},
		getTarget:function(event){
			return event.target?event.target:event.srcElement;
		}/*,
		removeHandler:function(element,type,handler){
			if(element.removeEventListener){
				element.removeEventListener(type,handler,false);
			}else if(element.detachEvent){
				element.detachEvent("on"+type,handler);
			}else{
				element["on"+type]=null;
			}
		}*/
	},
	CookieUtil:{
		c:"WSSNETID",
		s:"JSESSIONID",
		e:navigator.cookieEnabled,
		get:function(name){
			if(!this.e)return "";
			var ck = document.cookie;
			var cookieName = encodeURIComponent(name)+"=";
			var cookieStart = ck.indexOf(cookieName);
			var cookieValue = "";
			if(cookieStart>-1){
				var cookieEnd = ck.indexOf(";",cookieStart);
				if(cookieEnd==-1){
					cookieEnd = ck.length;
				}
				cookieValue = decodeURIComponent(ck.substring(cookieStart+cookieName.length,cookieEnd))
			}
			return cookieValue;
		},
		set:function(name,value,expires){
			if(!this.e)return;
			var cookieText = encodeURIComponent(name)+"="+encodeURIComponent(value);
			if(expires instanceof Date){
				cookieText += ";expires="+expires.toGMTString();
			}
			document.cookie = cookieText;
		},
		unset:function(name){
			if(!this.e)return;
			this.set(name,"",new Date(0));
		},
		getSessId:function(){
			return  this.get(this.s)||this.get(this.s.toLowerCase());
		},
		getCookId:function(){
			var cookId = this.get(this.c)||this.get(this.c.toLowerCase());
			if(!cookId && this.e){
				var d = new Date();
				cookId = ""+d.getFullYear()+d.getMonth()+d.getDate()+"-"+ Math.floor(Math.random()*1000000);
				d.setTime(d.getTime()+30*24*3600*1000); 
				this.set(this.c,cookId,d);
			}
			return cookId;
		}
	},
	DomUtil:{
		gi:function(id){
			return document.getElementById(id);
		},
		gc:function(searchClass,node,tag){
			var classElements = new Array();
	        if ( node == null )
	                node = document;
	        if ( tag == null )
	                tag = '*';
	        var els = node.getElementsByTagName(tag);
	        var elsLen = els.length;
	        var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
	        for (i = 0, j = 0; i < elsLen; i++) {
	                if ( pattern.test(els[i].className) ) {
	                        classElements[j] = els[i];
	                        j++;
	                }
	        }
	        return classElements;
		}/*,
		setData:function(name, value) {
			if (window.sessionStorage)
				try {
					window.sessionStorage.setItem(a, b)
				} catch (e) {
				}
		},
		getData:function(name) {
			return window.sessionStorage ? window.sessionStorage.getItem(value) : null;
		}*/
	},
	Client:{
		params:new Array(),
		urlArgs:new Array(),
		getParams:function(){
			var pa = new Array();
			var pas = this.params;
			for(var i=0,j=pas.length;i<j;i++){
				pa.push(pas[i]);
			}
			return pa;
		},
		Init:function(){//初始化
			this.initUrlArgs();
			this.initCookie();
			this.initClient();
			this.initOutClk();
			this.initExtClk();
			this.initSwb();
			this.onload();
		},
		initUrlArgs:function(){
			var search = location.search;
			if(search && search.length > 1){
				var search = search.substring(1);
				var items = search.split('&');
				for(var index = 0 ; index < items.length ; index++ ){
					if(! items[index]){
						continue;
					}
					var kv = items[index].split('=');
					this.urlArgs[kv[0]] = typeof kv[1] === "undefined" ? "":kv[1];
				}
			}
		},
		initCookie:function(){
			var cu = WssNet.CookieUtil;
			var ct = this.Const;
			var nbr = this.urlArgs["nbr"];
			//模拟cookie
			cu.set("LATN_CODE_COOKIE",ct.lan_code);
			cu.set("ecss_identity",ct.ecss_identity);
			if(nbr){
				cu.set("wt_acc_nbr",nbr);
				cu.set("wt_userid",nbr);
				cu.set("wt_usertype",ct.user_type);
				cu.set("loginEvent",ct.login_event);
			}
		},
		Const:{
			lan_code:"0000",
			acc_nbr:"",
			user_type:"",
			login_event:"",
			ecss_identity:Math.floor(Math.random()*100000000),
			track_web_url:"http://jx.189.cn"
		},
		initExtClk:function(){
			var it = WssNet.DomUtil.gc("wss_click");
			if(it && it.length>0){
				var vl ="";
				for(var i=0,j=it.length;i<j;i++){
					vl=it[i];
					WssNet.EventUtil.addHandler(vl,"click",function(event){
						var clitems = WssNet.DomUtil.gc("wss_trick");
						var obj = {};
						for(var m=0,n=clitems.length;m<n;m++){
							var item = clitems[m];
							var nm = item.type;
							var nd = item.id;
							obj["id_"+nd]=item.id;
							obj["nm_"+nd]=nm;
							switch(nm){
								case "checkbox":
									if(item.checked){
										var v = obj["vl_"+nd];
										obj["vl_"+nd]=v?v+","+item.value:item.value;
									}
									
									break;
								case "radio":
									if(item.checked){
										obj["vl_"+nd]=item.value;
									}
									break;
								default:
									obj["vl_"+nd]=item.value;
							}
						}
						var param = WssNet.Client.getParams();
						for(var it in obj){
							param.push("w_"+it+"="+obj[it]);
						}
						param.push("udi=T");
						WssNet.Sender("x",param);
					});
				}
			}
		},
		initClient:function(){
			var _engine = { ie: 0, webkit: 0, gecko: 0, opera: 0, khtml: 0 },
			_browser = { se360: 0, se: 0, maxthon: 0, qq: 0, tt: 0, theworld: 0, cometbrowser: 0, greenbrowser: 0, ie: 0, chrome: 0, netscape: 0, firefox: 0, opera: 0, safari: 0, konq: 0 },
			_platform = {sys_name:navigator.platform.toLowerCase()},
			_system = {win:false,mac:false,x11:false},
			_screen = screen.width+'x'+ screen.height,
			ua = navigator.userAgent.toLowerCase(),
			up = _platform.sys_name;
			
			_system.win= up.indexOf("win") ==0;
			_system.mac= up.indexOf("mac") ==0?"mac":false;
			_system.x11= (up.indexOf("linux") ==0 || up=="x11")?"x11":false;
			if(_system.win){
				var prefix="windows ";
				if(/win(?:dows )?([^do]{2})\s?(\d+\.\d+)?/.test(ua)){
					if(RegExp["$1"]=="nt"){
						switch(RegExp["$2"]){
							case "5.0":
								_system.win=prefix+"2000";
								break;
							case "5.1":
								_system.win=prefix+"xp";
								break;
							case "6.0":
								_system.win=prefix+"vista";
								break;
							case "6.1":
								_system.win=prefix+"7";
								break;
							default:
								_system.win=prefix+"nt";
								break;
						}
					}else if(RegExp["$1"]=="9x"){
						_system.win=prefix+"me";
					}else{
						_system.win=prefix+RegExp["$1"];
					}
				}
			}
			for (var type in _engine) {
				if (typeof type === 'string') {
					var regexp = 'gecko' === type ? /rv:([\w.]+)/ : RegExp(type + '[ \\/]([\\w.]+)');
					if (regexp.test(ua)) {
						_engine.version = window.opera ? window.opera.version() : RegExp.$1;
						_engine[type] = parseFloat(_engine.version);
						_engine.type = type;
						break;
					}
				}
			}
			for (var type in _browser) {
				if (typeof type === 'string') {
					var regexp = null;
					switch(type) {
						case "se360": regexp = /360se(?:[ \/]([\w.]+))?/; break;
						case "se": regexp = /se ([\w.]+)/; break;
						case "qq": regexp = /qqbrowser\/([\w.]+)/; break;
						case "tt": regexp = /tencenttraveler ([\w.]+)/; break;
						case "safari": regexp = /version\/([\w.]+)/; break;
						case "konq": regexp = /konqueror\/([\w.]+)/; break;
						case "netscape": regexp = /navigator\/([\w.]+)/; break;
						default: regexp = RegExp(type + '(?:[ \\/]([\\w.]+))?');
					}
					if (regexp.test(ua)) {
						_browser.version = window.opera ? window.opera.version() : RegExp.$1 ? RegExp.$1 : '';
						_browser[type] = parseFloat(_browser.version);
						_browser.type = type;
						break;
					}
				}
			}

			var pa = this.params;
			var cu = WssNet.CookieUtil;
			pa.push("c_id="+cu.getCookId());
			pa.push("s_id="+cu.getSessId());
			pa.push("source_url="+document.referrer);
			pa.push("visit_url="+location.href);
			pa.push("os="+(_system.win||_system.mac||_system.x11));
			pa.push("browser="+_browser.type+" "+_browser.version);
			pa.push("area_code="+cu.get("LATN_CODE_COOKIE"));
			pa.push("user_no="+cu.get("wt_acc_nbr"));
			pa.push("user_type="+cu.get("wt_usertype"));
			pa.push("user_id="+cu.get("wt_userid"));
			pa.push("if_logined="+(cu.get("loginEvent")==this.Const.login_event?1:0));
			pa.push("screen="+_screen);
			pa.push("identify_id="+cu.get("ecss_identity"));
			pa.push("serv_kind="+serv_kind);
			pa.push("serv_type="+serv_type);
			pa.push("serv_no="+serv_no);
		},
		initSwb:function(){
			var _s="",_l=document.embeds.length;
			for(var i=0;i<_l;i++){
				var em = document.embeds[i];
				var _id = em.id;
//				if(em.src.indexOf("player.swf")>-1){
//					//_s += "jwplayer('"+_id+"').onPlay(function() {saveswf.save('"+_id+"','',jwplayer('"+_id+"').getPosition(),'b');});jwplayer('"+_id+"').onPause(function() {saveswf.save('"+_id+"','',jwplayer('"+_id+"').getPosition(),'p');});"		
//				}else{
					_s += "function "+_id+"_DoFSCommand(command, args) {if(command==\"callJavascript\") {arg = args.split(\"#\");saveswf.save('"+_id+"',arg[0],'','c');}} ";
					if (navigator.appName && navigator.appName.indexOf("Microsoft") != -1 && navigator.userAgent.indexOf("Windows") != -1 && navigator.userAgent.indexOf("Windows 3.1") == -1) {
						var h=new Array();
						h.push('<script language=\"VBScript\"\>\n');
						h.push('On Error Resume Next\n');
						h.push('Sub '+_id+'_FSCommand(ByVal command, ByVal args)\n');
						h.push('	Call '+_id+'_DoFSCommand(command, args)\n');
						h.push('End Sub\n');
						h.push('</script\>\n');
						document.write(h.join(""));
					}
//				}
			}
			if(_l>0){
				var script = document.createElement('script');
				script.innerHTML = _s;
				document.getElementsByTagName('head')[0].appendChild(script);
			}
		},
		initOutClk:function(){
			for(var i=0,j=document.links.length;i<j;i++){
				var link = document.links[i];
				if(link.host!= location.host){//外链
					WssNet.EventUtil.addHandler(link,"click",function(event){
						var curURL = location.href;
						var goURL = link.href;
						var param = WssNet.Client.getParams();
						param.push("source_url="+curURL);
						param.push("visit_url="+goURL);
						WssNet.Sender("1",param);
					});
				}
			}
		},
		onload:function(){
			WssNet.Sender("0",this.getParams());
		},
		unload:function(event){
			WssNet.Sender("1",this.getParams());
		}
	},
	PageClk:function(event){
		var el = this.EventUtil.getTarget(event);
		var de = document.documentElement;
		if(event && event.clientX ){//用户点击
			if(/wss_click|trk_tab|track/.test(el.className))return;
			var clickParam=this.Client.getParams();
			var x=event.clientX,y=event.clientY;
			var scrollx=window.pageXOffset||de.scrollLeft;
			var scrolly=window.pageYOffset||de.scrollTop;
			x+=scrollx,y+=scrolly;
			clickParam.push("x="+x);
			clickParam.push("y="+y);
			clickParam.push("html_element_id="+el.id);
			clickParam.push("html_element="+el.nodeName);
			clickParam.push("html_value="+(el.text||el.innerHTML).substr(0,5));
			this.Sender("c",clickParam);
		}
	},
	Sender:function(f,param){
		var img = new Image(1, 1);
		var q = encodeURI(param.join("&"));
		q = q.replace(new RegExp("undefined|null","gm"),"");
		//var s = WssNet.Client.Const.track_web_url+"/TrackWeb/tr.gif?f="+f+"&" + q;
		var s = "/tr.gif?f="+f+"&" + q;
//		alert(s);
		img.src = s;
	}/*,
	doGet:function(f,param){
		
	}*/
};
var saveswf = {
	save:function(a,b,c,d){
		var _c=WssNet.Client.getParams();
		_c.push("flash_media_object_id="+a);
		_c.push("flash_popup_url="+b);
		_c.push("location="+c);
		_c.push("html_element=flash");
		WssNet.Sender(d,_c);
	}
}
//绑定window error事件
window.onerror = function(){
	return true;
};
var eu = WssNet.EventUtil;
var cl = WssNet.Client;
eu.addHandler(window,"load",function(){
	if(!window.serv_kind)serv_kind="";
	if(!window.serv_type)serv_type="";
	if(!window.serv_no)serv_no="";
	//页面初始化
	cl.Init();
	
	//绑定page click事件
//	eu.addHandler(document.body,"click",function(event){
//		WssNet.PageClk(eu.getEvent(event));
//	});
	
	//绑定page unload
//	eu.addHandler(window,"unload",function(event){
//		cl.unload(eu.getEvent(event));
//	});
});

