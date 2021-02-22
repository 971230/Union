var WssNet={EventUtil:{addHandler:function(a,c,b){if(a.addEventListener){a.addEventListener(c,b,false)}else{if(a.attachEvent){a.attachEvent("on"+c,b)}else{a["on"+c]=b}}},getEvent:function(a){return a?a:window.event},getTarget:function(a){return a.target?a.target:a.srcElement}},CookieUtil:{c:"WSSNETID",s:"JSESSIONID",e:navigator.cookieEnabled,get:function(c){if(!this.e){return""}var b=document.cookie;var f=encodeURIComponent(c)+"=";var a=b.indexOf(f);var e="";if(a>-1){var d=b.indexOf(";",a);if(d==-1){d=b.length}e=decodeURIComponent(b.substring(a+f.length,d))}return e},set:function(b,d,a){if(!this.e){return}var c=encodeURIComponent(b)+"="+encodeURIComponent(d);if(a instanceof Date){c+=";expires="+a.toGMTString()}document.cookie=c},unset:function(a){if(!this.e){return}this.set(a,"",new Date(0))},getSessId:function(){return this.get(this.s)||this.get(this.s.toLowerCase())},getCookId:function(){var a=this.get(this.c)||this.get(this.c.toLowerCase());if(!a&&this.e){var b=new Date();a=""+b.getFullYear()+b.getMonth()+b.getDate()+"-"+Math.floor(Math.random()*1000000);b.setTime(b.getTime()+30*24*3600*1000);this.set(this.c,a,b)}return a}},DomUtil:{gi:function(a){return document.getElementById(a)},gc:function(g,e,a){var d=new Array();if(e==null){e=document}if(a==null){a="*"}var c=e.getElementsByTagName(a);var b=c.length;var f=new RegExp("(^|\\s)"+g+"(\\s|$)");for(i=0,j=0;i<b;i++){if(f.test(c[i].className)){d[j]=c[i];j++}}return d}},Client:{params:new Array(),urlArgs:new Array(),getParams:function(){var d=new Array();var c=this.params;for(var b=0,a=c.length;b<a;b++){d.push(c[b])}return d},Init:function(){this.initUrlArgs();this.initCookie();this.initClient();this.initOutClk();this.initExtClk();this.initSwb();this.onload()},initUrlArgs:function(){var c=location.search;if(c&&c.length>1){var c=c.substring(1);var a=c.split("&");for(var b=0;b<a.length;b++){if(!a[b]){continue}var d=a[b].split("=");this.urlArgs[d[0]]=typeof d[1]==="undefined"?"":d[1]}}},initCookie:function(){var a=WssNet.CookieUtil;var c=this.Const;var b=this.urlArgs.nbr;a.set("LATN_CODE_COOKIE",c.lan_code);a.set("ecss_identity",c.ecss_identity);if(b){a.set("wt_acc_nbr",b);a.set("wt_userid",b);a.set("wt_usertype",c.user_type);a.set("loginEvent",c.login_event)}},Const:{lan_code:"0000",acc_nbr:"",user_type:"",login_event:"",ecss_identity:Math.floor(Math.random()*100000000),track_web_url:"http://jx.189.cn"},initExtClk:function(){var c=WssNet.DomUtil.gc("wss_click");if(c&&c.length>0){var d="";for(var b=0,a=c.length;b<a;b++){d=c[b];WssNet.EventUtil.addHandler(d,"click",function(e){var p=WssNet.DomUtil.gc("wss_trick");var o={};for(var h=0,f=p.length;h<f;h++){var r=p[h];var s=r.type;var k=r.id;o["id_"+k]=r.id;o["nm_"+k]=s;switch(s){case"checkbox":if(r.checked){var q=o["vl_"+k];o["vl_"+k]=q?q+","+r.value:r.value}break;case"radio":if(r.checked){o["vl_"+k]=r.value}break;default:o["vl_"+k]=r.value}}var g=WssNet.Client.getParams();for(var l in o){g.push("w_"+l+"="+o[l])}g.push("udi=T");WssNet.Sender("x",g)})}}},initClient:function(){var b={ie:0,webkit:0,gecko:0,opera:0,khtml:0},d={se360:0,se:0,maxthon:0,qq:0,tt:0,theworld:0,cometbrowser:0,greenbrowser:0,ie:0,chrome:0,netscape:0,firefox:0,opera:0,safari:0,konq:0},c={sys_name:navigator.platform.toLowerCase()},k={win:false,mac:false,x11:false},e=screen.width+"x"+screen.height,a=navigator.userAgent.toLowerCase(),f=c.sys_name;k.win=f.indexOf("win")==0;k.mac=f.indexOf("mac")==0?"mac":false;k.x11=(f.indexOf("linux")==0||f=="x11")?"x11":false;if(k.win){var h="windows ";if(/win(?:dows )?([^do]{2})\s?(\d+\.\d+)?/.test(a)){if(RegExp["$1"]=="nt"){switch(RegExp["$2"]){case"5.0":k.win=h+"2000";break;case"5.1":k.win=h+"xp";break;case"6.0":k.win=h+"vista";break;case"6.1":k.win=h+"7";break;default:k.win=h+"nt";break}}else{if(RegExp["$1"]=="9x"){k.win=h+"me"}else{k.win=h+RegExp["$1"]}}}}for(var m in b){if(typeof m==="string"){var l="gecko"===m?/rv:([\w.]+)/:RegExp(m+"[ \\/]([\\w.]+)");if(l.test(a)){b.version=window.opera?window.opera.version():RegExp.$1;b[m]=parseFloat(b.version);b.type=m;break}}}for(var m in d){if(typeof m==="string"){var l=null;switch(m){case"se360":l=/360se(?:[ \/]([\w.]+))?/;break;case"se":l=/se ([\w.]+)/;break;case"qq":l=/qqbrowser\/([\w.]+)/;break;case"tt":l=/tencenttraveler ([\w.]+)/;break;case"safari":l=/version\/([\w.]+)/;break;case"konq":l=/konqueror\/([\w.]+)/;break;case"netscape":l=/navigator\/([\w.]+)/;break;default:l=RegExp(m+"(?:[ \\/]([\\w.]+))?")}if(l.test(a)){d.version=window.opera?window.opera.version():RegExp.$1?RegExp.$1:"";d[m]=parseFloat(d.version);d.type=m;break}}}var n=this.params;var g=WssNet.CookieUtil;n.push("c_id="+g.getCookId());n.push("s_id="+g.getSessId());n.push("source_url="+document.referrer);n.push("visit_url="+location.href);n.push("os="+(k.win||k.mac||k.x11));n.push("browser="+d.type+" "+d.version);n.push("area_code="+g.get("LATN_CODE_COOKIE"));n.push("user_no="+g.get("wt_acc_nbr"));n.push("user_type="+g.get("wt_usertype"));n.push("user_id="+g.get("wt_userid"));n.push("if_logined="+(g.get("loginEvent")==this.Const.login_event?1:0));n.push("screen="+e);n.push("identify_id="+g.get("ecss_identity"));n.push("serv_kind="+serv_kind);n.push("serv_type="+serv_type);n.push("serv_no="+serv_no)},initSwb:function(){var g="",e=document.embeds.length;for(var d=0;d<e;d++){var c=document.embeds[d];var b=c.id;g+="function "+b+'_DoFSCommand(command, args) {if(command=="callJavascript") {arg = args.split("#");saveswf.save(\''+b+"',arg[0],'','c');}} ";if(navigator.appName&&navigator.appName.indexOf("Microsoft")!=-1&&navigator.userAgent.indexOf("Windows")!=-1&&navigator.userAgent.indexOf("Windows 3.1")==-1){var f=new Array();f.push('<script language="VBScript">\n');f.push("On Error Resume Next\n");f.push("Sub "+b+"_FSCommand(ByVal command, ByVal args)\n");f.push("	Call "+b+"_DoFSCommand(command, args)\n");f.push("End Sub\n");f.push("<\/script>\n");document.write(f.join(""))}}if(e>0){var a=document.createElement("script");a.innerHTML=g;document.getElementsByTagName("head")[0].appendChild(a)}},initOutClk:function(){for(var b=0,a=document.links.length;b<a;b++){var c=document.links[b];if(c.host!=location.host){WssNet.EventUtil.addHandler(c,"click",function(d){var g=location.href;var f=c.href;var e=WssNet.Client.getParams();e.push("source_url="+g);e.push("visit_url="+f);WssNet.Sender("1",e)})}}},onload:function(){WssNet.Sender("0",this.getParams())},unload:function(a){WssNet.Sender("1",this.getParams())}},PageClk:function(d){var c=this.EventUtil.getTarget(d);var h=document.documentElement;if(d&&d.clientX){if(/wss_click|trk_tab|track/.test(c.className)){return}var b=this.Client.getParams();var a=d.clientX,g=d.clientY;var f=window.pageXOffset||h.scrollLeft;var e=window.pageYOffset||h.scrollTop;a+=f,g+=e;b.push("x="+a);b.push("y="+g);b.push("html_element_id="+c.id);b.push("html_element="+c.nodeName);b.push("html_value="+(c.text||c.innerHTML).substr(0,5));this.Sender("c",b)}},Sender:function(d,e){var a=new Image(1,1);var c=encodeURI(e.join("&"));c=c.replace(new RegExp("undefined|null","gm"),"");var b="/tr.gif?f="+d+"&"+c;a.src=b}};var saveswf={save:function(f,e,k,h){var g=WssNet.Client.getParams();g.push("flash_media_object_id="+f);g.push("flash_popup_url="+e);g.push("location="+k);g.push("html_element=flash");WssNet.Sender(h,g)}};window.onerror=function(){return true};var eu=WssNet.EventUtil;var cl=WssNet.Client;eu.addHandler(window,"load",function(){if(!window.serv_kind){serv_kind=""}if(!window.serv_type){serv_type=""}if(!window.serv_no){serv_no=""}cl.Init()});