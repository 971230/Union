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
	
	$("#username").bind("blur", function(){
		var appList = [];
		var userName = $(this).val().trim();
		if(userName == null || userName == '' || userName == 'undefined'){
			$("#app_source_from").empty();
			$("#app_source_from_txt").empty();
			return ;
		}
		$.ajax({url : ctx + "/wss_login!getUserApp.do?ajax=yes&user_name=" + userName,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				appList = data.list;
			},error : function(a,b) {
				alert(a + b);
			}
		});
		$("#app_source_from").empty();
		$("#app_source_from_txt").empty();
		var login_flag = false;
		if(appList != null && appList.length > 0){
			var objEle = $(this).closest('');
			$("#sys_choice").empty();
			for(var i = 0; i < appList.length; i++){
				var app = appList[i];
				var option = null;
				option = $('<li style="cursor:pointer;" class="app_info" sourceFrom="' + app.sourceFrom + '" appName="'+app.appName+'"><a>' + app.appName + '</a></li>');
				if(app.sourceFrom == source_from){
					login_flag = true;
					$("#app_info").val(JSON.stringify(app));
					pre_app = cur_app = app;
					$("#app_source_from_txt").val(app.appName);
					$("#app_source_from").val(app.sourceFrom);
				}
				option.data("appInfo", app);
				$("#sys_choice").append(option);
			}
		}
		if(appList.length > 1){
			$("#app_source_from").closest('.clearfix').show();
		}
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
						alert(a + b);
					}
				});
			}else {
				alert("没有登录系统的权限");
			}
		}
	});
	$(".app_info").live("click", function(){
		var curr = $(this);
		var sourceFrom = curr.attr("sourceFrom");
		var appName = curr.attr("appName");
		$("#app_source_from").val(sourceFrom);
		$("#app_source_from_txt").val(appName);
		$(".select_float").hide();
		
		var appInfo = curr.data("appInfo");
		
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
		//
	});
});