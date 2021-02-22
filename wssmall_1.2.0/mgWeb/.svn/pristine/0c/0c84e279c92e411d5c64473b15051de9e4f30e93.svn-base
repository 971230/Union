if (typeof JSON !== 'object') {
    JSON = {};
}

(function () {
    'use strict';

    function f(n) {
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function () {

            return isFinite(this.valueOf())
                ? this.getUTCFullYear()     + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate())      + 'T' +
                    f(this.getUTCHours())     + ':' +
                    f(this.getUTCMinutes())   + ':' +
                    f(this.getUTCSeconds())   + 'Z'
                : null;
        };

        String.prototype.toJSON      =
            Number.prototype.toJSON  =
            Boolean.prototype.toJSON = function () {
                return this.valueOf();
            };
    }

    var cx,
        escapable,
        gap,
        indent,
        meta,
        rep;


    function quote(string) {

        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string'
                ? c
                : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }


    function str(key, holder) {

        var i,          // The loop counter.
            k,          // The member key.
            v,          // The member value.
            length,
            mind = gap,
            partial,
            value = holder[key];

        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }


        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }


        switch (typeof value) {
        case 'string':
            return quote(value);

        case 'number':


            return isFinite(value) ? String(value) : 'null';

        case 'boolean':
        case 'null':


            return String(value);


        case 'object':

            if (!value) {
                return 'null';
            }


            gap += indent;
            partial = [];


            if (Object.prototype.toString.apply(value) === '[object Array]') {


                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }


                v = partial.length === 0
                    ? '[]'
                    : gap
                    ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']'
                    : '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }


            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    if (typeof rep[i] === 'string') {
                        k = rep[i];
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {


                for (k in value) {
                    if (Object.prototype.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            }


            v = partial.length === 0
                ? '{}'
                : gap
                ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}'
                : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }


    if (typeof JSON.stringify !== 'function') {
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
        meta = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        };
        JSON.stringify = function (value, replacer, space) {


            var i;
            gap = '';
            indent = '';


            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }


            } else if (typeof space === 'string') {
                indent = space;
            }


            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                    typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }


            return str('', {'': value});
        };
    }



    if (typeof JSON.parse !== 'function') {
        cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
        JSON.parse = function (text, reviver) {



            var j;

            function walk(holder, key) {


                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }



            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }


            if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {



                j = eval('(' + text + ')');



                return typeof reviver === 'function'
                    ? walk({'': j}, '')
                    : j;
            }


            throw new SyntaxError('JSON.parse');
        };
    }
}());

var pre_app = null;
var cur_app = null;
$(function(){
	$("#app_source_from").bind("change", function(){
		var appInfo = $(this).find("option:selected").data("appInfo");
		pre_app = cur_app;
		cur_app = appInfo;
		if(pre_app != null && cur_app != null && cur_app.appAddress != '' &&
				cur_app.appAddress != 'undefined' && pre_app.appAddress != '' && pre_app.appAddress != 'undefined'){
			if(getIpPort(pre_app.appAddress) == getIpPort(cur_app.appAddress)){
				$.ajax({url : ctx + "/shop/admin/appAction!reloadEopSetting.do?ajax=yes",
					type : "POST",
					dataType : 'json',
					data : {'appInfo': JSON.stringify(cur_app)},
					async : false,
					success : function(data) {
						if(data.code != '0000'){
							alert(data.message);
							$("#app_source_from").val(pre_app.sourceFrom);
						}else {
							$("#app_info").val(JSON.stringify(cur_app));
						}
					},error : function(a,b) {
						alert(a + b);
					}
				});
			}else {
				window.location.href = cur_app.appAddress;	
			}
		}else {
			alert("系统配置错误");
		}
	});
	
	
	
//	$("#username").bind("blur", function(){
//		var appList = [];
//		var userName = $(this).val().trim();
//		if(userName == null || userName == '' || userName == 'undefined'){
//			$("#app_source_from").empty();
//			return ;
//		}
//		$.ajax({url : ctx + "/wss_login!getUserApp.do?ajax=yes&user_name=" + userName,
		var appList = [];	
		$.ajax({url : ctx + "/wss_login!getUserApp.do?ajax=yes",
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				appList = data.list;
				if(1==checkType){
					if(1==data.specName){//如果是特殊用户,强制使用验证码登录---zengxianlian
						var htmlText='<input type="text" id="valid_code" name="valid_code" class="login_ipt" style="width:100px;" />';
						htmlText+='<img src="" width="94" height="30" id="code_img" />';
						$("#checkTd").empty().append(htmlText);
						$("#code_img").attr("src","../validcode.do?vtype=admin&rmd="+new Date().getTime())
						.click(function(){
							$(this).attr("src","../validcode.do?vtype=admin&rmd="+new Date().getTime() );
						});	
						$("#code_img").trigger("click");
					}else{//防止出现先使用特殊账户登录后,再用别的账户登录就能通过验证码登录---zengxianlian
						var htmlText='<input type="text" id="valid_code" name="valid_code" class="login_ipt" style="width:100px;" />';
						htmlText+='<button type="button" id="smsBtn">发送短信</button>';
						$("#checkTd").empty().append(htmlText);
						$("#smsBtn").click(function(){
							$("#err_info").html("");
							$.ajax({url : ctx + "/wss_login!sendSmsToUser.do?ajax=yes",
								data:{user_name:$("#username").val()},
								type : "POST",
								dataType : 'json',
								success : function(data) {
									if(null == data){
										alert("短信发送失败!");
									}else{
										var msg = data.message!=null?data.message:data.resp_msg;
										alert(msg);
										if(data.status==null||data.status!='0'){
											var $smsBtn = $("#smsBtn");
											var i =60;//控制按钮时间
											var j = 300;//控制验证码时间
											$smsBtn.html("重新发送("+i+")")
											$smsBtn.attr("disabled","disabled");
											var intervalId = setInterval(function(){
												i--;
												j--;
												if(i>0){
													$smsBtn.html("重新发送("+i+")");
												}else if(i==0){
													$smsBtn.html("重新发送");
													$smsBtn.removeAttr("disabled");
													clearInterval(intervalId);
												}
												if(j==0){
													//把之前的验证码置为无效
													$.ajax({url : ctx + "/wss_login!updateSmsState.do?ajax=yes",
														type : "POST",
														dataType : 'json',
														success : function(data) {
															$("#err_info").html(data.message);
														},
														error : function(a,b) {
															alert("出现错误 ，请重试");
														}
													});
												}
											},1000);
										}
									}
								},error : function(a,b) {
									alert(a+b);
								}
							});
						});
					}
				}else{
					var htmlText='<input type="text" id="valid_code" name="valid_code" class="login_ipt" style="width:100px;" />';
					htmlText+='<img src="" width="94" height="30" id="code_img" />';
					$("#checkTd").empty().append(htmlText);
					$("#code_img").attr("src","../validcode.do?vtype=admin&rmd="+new Date().getTime())
					$("#code_img").click(function(){
						$(this).attr("src","../validcode.do?vtype=admin&rmd="+new Date().getTime() );
					});	
					
					//$("#code_img").trigger("click");
				}
			},
			error : function(a,b) {
				alert(a + b);
			}
		});
		$("#app_source_from").empty();
		var login_flag = false;
		var firstApp = null;
		if(appList != null && appList.length > 0){
			var objEle = $(this).closest('tr');
			for(var i = 0; i < appList.length; i++){
				var app = appList[i];
				firstApp = app;
				var option = null;
				if(app.appName =="CMS平台"){
					app.themeSourceFrom='CMS';
				}
				if(app.themeSourceFrom == source_from){
					login_flag = true;
					var str = "{";
					for(var a in app){
						
						if(app[a]!=""){
							str += a+":"+app[a]+","	
						}
					}
					str = str.substring(0,str.length-1);
					str +="}"
					$("#app_info").val(str);
					
					
					
					pre_app = cur_app = app;
					option = $('<option value="' + (app.themeSourceFrom || app.sourceFrom) + '" selected="selected">' + app.appName + '</option>');
				}else {
					option = $('<option value="' + (app.themeSourceFrom || app.sourceFrom) + '">' + app.appName + '</option>');
				}
				option.data("appInfo", app);
				$("#app_source_from").append(option);
			}
		}
		if(appList.length > 1){
			$("#app_source_from").closest('tr').show();
		}else if(appList.length == 1){
			cur_app = firstApp;
			$.ajax({url : ctx + "/shop/admin/appAction!reloadEopSetting.do?ajax=yes",
				type : "POST",
				dataType : 'json',
				data : {'appInfo': JSON.stringify(firstApp)},
				async : false,
				success : function(data) {
					if(data.code != '0000'){
						$("#app_source_from").val(pre_app.sourceFrom);
					}else {
						$("#app_info").val(JSON.stringify(firstApp));
					}
				},error : function(a,b) {
//					alert(a + b);
				}
			});
			
		}
		
		/**
		 * 隐藏应用选择框，不能remove掉界面元素，因为系统还用到配置的应用信息
		 * 仍然需要在pm_app表中配置应用信息，且表的source_from和theme_source_from字段需要和eop.properties配置文件的db_source_from和source_from相同
		 */
		$("#app_source_from").closest('li').hide();
		
		if(!login_flag){
			if(appList.length != 0){
				$.ajax({url : ctx + "/shop/admin/appAction!jumpSystem.do?ajax=yes",
					type : "POST",
					dataType : 'json',
					data : {'appInfo': JSON.stringify(appList[0])},
					async : false,
					success : function(data) {
						if(data.code != '0000'){
							window.location.href = appList[0].appAddress;
						}
					},error : function(a,b) {
//						alert(a + b);
					}
				});
			}else {
				/*alert("没有登录系统的权限");*/
				//XMJ 修改6.18版本,登录错误统一提示"登录名或者密码错误,请核查."
				alert("登录名或者密码错误,请核查.");
			}
		}
//	});
});