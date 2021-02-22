/**
字符串截取
*@param str 输入字符串
*@param length 截取字符串长度
*@param endstyle 结束样式
*/
function omissionString(str, length, endstyle) {
	if (undefined == str || "string" != typeof(str))
		return false;
	if (undefined == length || "number" != typeof(length))
		return false;
	var myEndStyle = undefined == endstyle || "string" != typeof(endstyle)
			|| null == endstyle ? "..." : endstyle;
	var strLength = str.length;
	var newStr = "";
	if (0 < strLength) {
		if (length < strLength) {
			newStr = str.substr(0, length - 1) + myEndStyle;
		} else {
			newStr = str;
		}
	}
	return newStr;
}
/**
添加收藏夹
*@param sURL URL
*@param sTitle 标题
*/
function addBookmark(sURL, sTitle)
{
    try{window.external.addFavorite(sURL, sTitle);}
    catch (e){try{window.sidebar.addPanel(sTitle, sURL, "");}catch (e){}}
}
//克隆对象，作为公共函数 add by wui
var CommonUtils ={}
CommonUtils.apply = function(o, c, defaults) {
	if (defaults) {
		CommonUtils.apply(o, c);
	}
	if (o && c && typeof c == 'object') {
		for (var p in c) { // 字符串的false转换为boolean
			if (c[p] == "false") {
				c[p] = false;
			}
			if (c[p] == "true") {
				c[p] = true;
			}
			o[p] = c[p];
		}
	}
	return o;
};

var toString = Object.prototype.toString
CommonUtils.applyStr = function(c,o) {
	for (var p in o){
		if(isArray(o[p]))continue;
		if(isFunction(o[p]))continue;	
		c[p] = o[p];
	}
	return o;
};

function isArray(v){
	return toString.apply(v) === '[object Array]';
}
function isFunction(v){
	return v && typeof v == "object";
}		
CommonUtils.getDom=function(){
 	e =window.event||e; 
    var srcElement= e.srcElement||e.target;
    return srcElement;
}
CommonUtils.downLoad = function(form_id,callBack){
	var me =this;
	var attr_codes ="";
	$("#"+form_id).find("select").each(function(){
		var attr_code = $(this).attr("attr_code");
		attr_code && (attr_codes+=attr_code+",");
	})
	var param ={};
	param.attr_codes =attr_codes;
	param.method='GET_STATIC_DATA';
	Service.excuteNoLoading("PLAN_ACCEPT", false, param, function(reply){
		var optionsMap = reply['RET_OBJ'];
		for(p in optionsMap){
			var attr_code = p;
			var options = optionsMap[attr_code];
			for(var i=0;i<options.length;i++)
			{
				var option =options[i];
				var attr_value = option['value']||option['pkey']||option['sm_used_view'];
				var attr_desc = option['value_desc']||option['pname']||option['sm_disp_view'];
				
				$("select[attr_code='"+attr_code+"']").append("<option "+"value="+attr_value+">"+attr_desc+"</option>");
			}
		}
		callBack && callBack();
	})
}	
$(function(){
	var KeyValues  = window.KeyValues || {};
	KeyValues.CDMA_PRODDUCT_ID ='80000045';
	KeyValues.XDSL_PRODDUCT_ID ='80000030';
	KeyValues.PHONE_PRODDUCT_ID ='80000000';
	KeyValues.SMALL_PRODDUCT_ID ='80000018';
	KeyValues.getProductName = function(product_id){
		if(KeyValues.CDMA_PRODDUCT_ID ==product_id){
			return "手机";
		}
		if(KeyValues.XDSL_PRODDUCT_ID ==product_id){
			return "宽带";
		}
		if(KeyValues.XDSL_PRODDUCT_ID ==product_id){
			return "固话";
		}
		if(KeyValues.SMALL_PRODDUCT_ID ==product_id){
			return "小灵通";
		}		
	}
	window.KeyValues =KeyValues;
})
/**
*格式校验
*/

VTypes = function() {
	var alpha = /^[a-zA-Z_]+/;
	var numOnly = /^[0-9]+$/;
	var alphanum = /^[a-zA-Z0-9_]+$/;
	var email = /^(\w+)([-+.][\w]+)*@(\w[-\w]*\.){1,5}([A-Za-z]){2,4}$/;
	var url = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
	var time = /^([1-9]|1[0-9]):([0-5][0-9])(\s[a|p]m)$/i;
	var require = /.+/;
	var number = /^\d+$/;
	var integer = /^[-\+]?\d+$/;
	var double = /^[-\+]?\d+(\.\d+)?$/;
	var alphachinese = /^[a-zA-Z_\u4E00-\u9FA5]+/;
	var isExistNum = /^[a-zA-Z0-9@]*[0-9]+[a-zA-Z0-9@]*$/;
	var mobile = /^1[3|4|5|8][0-9]\d{4,8}$/;  //手机号码（最少输入前7位）
	var isMixNum = /^[a-zA-Z@]+[0-9]+[a-zA-Z0-9@]*$/;
	var ct_mobile = /^(133|153|180|189)\d{8}$/;
	return {
		'ct_mobile':function(v){
			return ct_mobile.test(v);
		},
	    'mobile' : function(v) {
	        return mobile.test(v);
	    },
	    'mobileText' : '请输入正确的手机号码，如：1890731****',
	    
		'ip' : function(v) {
			return /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(v)
		},
		'ipText' : '必须为数字IP地址格式:如127.0.0.1',

		'chinese' : function(v) {
			var regix = eval('/[^\u4e00-\u9fa5]/');
			return !regix.test(v + "");

		},
		'chineseText' : '只能输入汉字!',

		'require' : function(v) {
			return require.test(v)
		},
		'requireText' : '必填内容不能为空!',
		'datetime' : function(v) {
			var a = v
					.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2}) (\d{0,2}):(\d{0,2}):(\d{0,2})$/);
			if (a == null) {
				return false;
			}
			if (a[2] >= 13 || a[3] >= 32 || a[4] >= 24 || a[5] >= 60
					|| a[6] >= 60) {
				return false;
			}
			return true;
		},
		'datetimeText' : '日期时间格式必须为0000-00-00 00:00:00 例如1999-11-10 20:30:29.',

		'date' : function(v) {
			var a = v.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2})$/);
			if (a == null) {
				return false;
			}
			if (a[2] >= 13 || a[3] >= 32 || a[4] >= 24 || a[5] >= 60
					|| a[6] >= 60) {
				return false;
			}
			return true;
		},
		'dateText' : '日期时间格式必须为0000-00-00  例如1999-11-10',

		'number' : function(v) {
			return number.test(v);
		},
		'numberText' : '数字格式不正确',

		'integer' : function(v) {
			return integer.test(v);
		},
		'integerText' : '整数格式不正确',

		'double' : function(v) {
			return double.test(v);
		},
		'doubleText' : '小数填写不正确',
		'time' : function(v) {
			var a = v.match(/^(\d{0,2}):(\d{0,2}):(\d{0,2})$/);
			if (a == null) {
				return false;
			}
			if (a[1].length < 2 || a[2].length < 2 || a[3].length < 2) {
				return false;
			}
			if (a[1] >= 24 || a[2] >= 60 || a[3] >= 60) {
				return false;
			}
			return true;
		},
		'timeText' : '时间的格式必须处于00:00:00和23:59:59之间（时间中的:必须为半角格式）.',

		'email' : function(v) {
			return email.test(v);
		},
		'emailText' : '输入格式必须为emal如:"user@example.com"',
		'emailMask' : /[a-z0-9_\.\-@]/i,
		'url' : function(v) {
			return url.test(v);
		},
		'urlText' : '输入格式必须为url 如:"http:/' + '/www.example.com"',

		'alpha' : function(v) {
			return alpha.test(v);
		},
		'numOnly': function(v) {
			return numOnly.test(v);
		},
		'alphaText' : '只能包含字母或 _',
		'alphaMask' : /[a-z_]/i,
		'alphanum' : function(v) {
			return alphanum.test(v);
		},
		'alphachinese' : function(v) {
			return alphachinese.test(v);
		},
		'isExistNum': function(v) {
			return isExistNum.test(v);
		},
		'isMixNum': function(v) {
			return isMixNum.test(v);
		},
		'alphachineseText' : '只能包含字母或汉字或 _ ',
		'idCard' : function(idcard) {

			var Errors = ["验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!",
					"身份证号码校验错误!", "身份证地区非法!"];

			var area = {
				11 : "北京",
				12 : "天津",
				13 : "河北",
				14 : "山西",
				15 : "内蒙古",
				21 : "辽宁",
				22 : "吉林",
				23 : "黑龙江",
				31 : "上海",
				32 : "江苏",
				33 : "浙江",
				34 : "安徽",
				35 : "福建",
				36 : "江西",
				37 : "山东",
				41 : "河南",
				42 : "湖北",
				43 : "湖南",
				44 : "广东",
				45 : "广西",
				46 : "海南",
				50 : "重庆",
				51 : "四川",
				52 : "贵州",
				53 : "云南",
				54 : "西藏",
				61 : "陕西",
				62 : "甘肃",
				63 : "青海",
				64 : "宁夏",
				65 : "新疆",
				71 : "台湾",
				81 : "香港",
				82 : "澳门",
				91 : "国外"
			}

			var idcard, Y, JYM;
			var S, M;
			var idcard_array = [];
			idcard_array = idcard.split("");
			// 地区检验

			if (area[parseInt(idcard.substr(0, 2))] == null) {
				return Errors[4];
			}

			// 身份号码位数及格式检验
			switch (idcard.length) {
				case 15 :
					if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
							|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
									.substr(6, 2)) + 1900)
									% 4 == 0)) {
						Ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
					} else {
						Ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
					}
					if (Ereg.test(idcard))

						return Errors[0];
					else
						return Errors[2];
					break;

				case 18 :
					// 18位身份号码检测
					// 出生日期的合法性检查
					// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
					// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
					if (parseInt(idcard.substr(6, 4)) % 4 == 0
							|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
									.substr(6, 4))
									% 4 == 0)) {
						Ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
					} else {
						Ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
					}
					if (Ereg.test(idcard)) {// 测试出生日期的合法性
						// 计算校验位
						S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10]))
								* 7
								+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
								* 9
								+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
								* 10
								+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
								* 5
								+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
								* 8
								+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
								* 4
								+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
								* 2
								+ parseInt(idcard_array[7])
								* 1
								+ parseInt(idcard_array[8])
								* 6
								+ parseInt(idcard_array[9]) * 3;
						Y = S % 11;
						M = "F";
						JYM = "10X98765432";
						M = JYM.substr(Y, 1);// 判断校验位
						if (M == idcard_array[17] || (idcard_array[17]  && idcard_array[17].toUpperCase() =="X"))
							return Errors[0]; // 检测ID的校验位
						else{
							
							return Errors[3];
						}
					} else
						return Errors[2];
					break;

				default :
					return Errors[1];
					break;

			}
		},
		'alphanumText' : '只能包含字母，数字或 _',
		'alphanumMask' : /[a-z0-9_]/i
	};
}();


/**
 * 对象基础接口类
 * @param {} obj
 */
// The Base object constructor
var Base = function(obj){
    // Only work if there's something to extend
    if (obj) 
        // If used as Base(), use the prototype of the caller
        if (this == window) 
            Base.prototype.extend.call(obj, arguments.callee.prototype);
        
        // Otherwise, this is called as new Base(), so extend the object
        else 
            this.extend(obj);
};

//移植
Base.version = "1.0.2";
Base.prototype = {
    // A function for overriding one object property with another
    extend: function(source, value){
        var extend = Base.prototype.extend;
        
        // Check to see if we're overriding a property with a new value
        if (arguments.length == 2) {
            // Remember the original parent property value
            var ancestor = this[source];
            
            // Check to see if we're overriding a parent method, and that this.base()
            // is actually used by the overrider
            if ((ancestor instanceof Function) && (value instanceof Function) &&
            ancestor.valueOf() != value.valueOf() &&
            /\bbase\b/.test(value)) {
            
                // Remember the old function
                var method = value;
                
                // and build a new function wrapper, to have sane 
                value = function(){
                    // Remember the old value of this.base to be restored later
                    var previous = this.base;
                    // Calling this.base() calls the old parent function
                    this.base = ancestor;
                    
                    // Execute the new, overriding, function
                    var returnValue = method.apply(this, arguments);
                    
                    // Restore the this.base() property
                    this.base = previous;
                    
                    // Return the accurate return value
                    return returnValue;
                };
                
                // valueOf and toString get messed by our modified
                // wrapper function, so make them appear normal
                value.valueOf = function(){
                    return method;
                };
                value.toString = function(){
                    return String(method);
                };
            }
            
            // Attach the new property to the source
            return this[source] = value;
            
            // If only a source was provided, copy all properties over from
            // the parent class to this new child
        }
        else 
            if (source) {
                var _prototype = {
                    toSource: null
                };
                
                // We modify these two functions later on, so protect them
                var _protected = ["toString", "valueOf"];
                
                // if we are prototyping then include the constructor
                if (Base._prototyping) 
                    _protected[2] = "constructor";
                
                // Copy over the protected functions indvidually
                for (var i = 0; (name = _protected[i]); i++) 
                    if (source[name] != _prototype[name]) 
                        extend.call(this, name, source[name]);
                
                // Copy each of the source object's properties to this object
                for (var name in source) 
                    if (!_prototype[name]) 
                        extend.call(this, name, source[name]);
            }
        
        return this;
    },
    
    // The this.base() function which we'll be implementating later on
    base: function(){
    }
};

// A wrapper function for creating a new object constructor
Base.extend = function(_instance, _static){
    // Remember the extend function
    var extend = Base.prototype.extend;
    
    // Lets us do Base.extend() and get a blank object constructor
    if (!_instance) 
        _instance = {};
    
    // Make sure to include the constructor later
    Base._prototyping = true;
    
    // Build the prototype
    var _prototype = new this;
    extend.call(_prototype, _instance);
    
    // Build the constructor
    var constructor = _prototype.constructor;
    _prototype.constructor = this;
    
    delete Base._prototyping;
    
    // Create the wrapper for the constructor function
    var klass = function(){
        if (!Base._prototyping) 
            constructor.apply(this, arguments);
        this.constructor = klass;
    };
    
    // Which inherits from Base
    klass.prototype = _prototype;
    
    // Add all the extra Base methods
    klass.extend = this.extend;
    klass.implement = this.implement;
    klass.toString = function(){
        return String(constructor);
    };
    
    // Add in all the extra properties provided by the user
    extend.call(klass, _static);
    
    // Check for a single instance case
    var object = constructor ? klass : _prototype;
    
    // Class initialisation
    if (object.init instanceof Function) 
        object.init();
    
    // Return the new object constructor
    return object;
};

// A simple function that can be used to pull additional object properties
// into a constructor – effectively creating multiple inheritance
Base.implement = function(_interface){
    // If a constructor was provided, instead of a prototype,
    // get the prototype instead
    if (_interface instanceof Function) 
        _interface = _interface.prototype;
    
    // Extend the object with the methods from the parent object
    this.prototype.extend(_interface);
};




/**
 * add by wui 工具对象
 */
(function(scope) {
	var CommonJS = Base.extend({
		constructor : function() {
		},
		validate : function() {
			var result = true;
			$("[fieldType='db']:visible").each(function() {
						var is_null = $(this).attr("is_null");
						var value = $(this).val();
						var vtype_code = $(this).attr("vtype");
						var cname = $(this).attr("cname");
						if ("F" == is_null && !value) {
							pop({
										text : cname + "不能为空！"
									});
							result = false;
							return false;
						}
						if (vtype_code && !VTypes[vtype_code](value)) {
							pop({
										text : cname + "输入格式非法！"
									});
							result = false;
							return false;
						}
					});
				return result;
		},
		validateCertify:function(id){
				var value =$("#"+id).val();
				var vTypes =VTypes;
				var cardValMess = vTypes['idCard'](value);
				if (cardValMess && !(cardValMess.indexOf("验证通过") > -1)) {
	
					pop({text:cardValMess});
					return false;
				}
				return true;
			},
		validateCertifyByValue:function(value){
				var vTypes =VTypes;
				var cardValMess = vTypes['idCard'](value);
				if (cardValMess && !(cardValMess.indexOf("验证通过") > -1)) {
	
					pop({text:cardValMess});
					return false;
				}
				return true;
			},
		validateCheckBox:function(check_box_name,element_id,tip_msg){
			if($("[id='"+check_box_name+"']").attr("checked") && !$("#"+element_id).val()){
				pop({text:tip_msg});
				return false;
			}
			return true;
		},
		validateConfirm:function(id,confirm_id,tip_msg){
			if($("#"+id).val() !=$("#"+confirm_id).val()){
				pop({text:tip_msg});
				return false;
			}
			return true;
		},
		getInputDomain : function() {
			var iv = {},me=this;
			$("input[type='checkbox'][fieldType='db']:checked").each(
				function() {
					var $obj = $(this);
					var ivId = $obj.attr("dbField")
							|| $obj.attr("name");
					// 真值
					var ivVal = $obj.val();
					me.setObjVal(iv, ivId, ivVal);
					// 显示值
					var ivValDesc = $obj.attr("cname");
					me.setObjVal(iv, ivId + "_desc", ivValDesc);
			});

			// text
			$("input[type='text'][fieldType='db']").each(function() {
					var $obj = $(this);
					var ivId = $obj.attr("dbField")
							|| $obj.attr("id");
					var ivVal = $obj.val();
					me.setObjVal(iv, ivId, ivVal);
					
				});
			$("input[type='password'][fieldType='db']").each(function() {
				var $obj = $(this);
				var ivId = $obj.attr("dbField")
						|| $obj.attr("id");
				var ivVal = $obj.val();
				me.setObjVal(iv, ivId, ivVal);
				
			});
			
			// hidden
			$("input[type='hidden'][fieldType='db']").each(
					function() {
						var $obj = $(this);
						var ivId = $obj.attr("dbField")
								|| $obj.attr("id");
						var ivVal = $obj.val();
						me.setObjVal(iv, ivId, ivVal);
					});

			// select
			$("[fieldType='db']select option:selected").each(
					function() {
						var $obj = $(this);
						var ivId = $obj.parent().attr("dbField")
								|| $obj.parent().attr("id");
						// 真值
						var ivVal = $obj.val();
						me.setObjVal(iv, ivId, ivVal);
						// 显示值
						var ivValDesc = $obj.text();
						me.setObjVal(iv, ivId + "_desc", ivValDesc);

					});
			// textarea
			$("textarea[fieldType='db']").each(function() {
						var $obj = $(this);
						var ivId = $obj.attr("dbField")
								|| $obj.attr("id");
						// 真值
						var ivVal = $obj.val();
						me.setObjVal(iv, ivId, ivVal);

					});
	
			$("span[fieldType='db']").each(function() {
				var $obj = $(this);
				var ivId = $obj.attr("dbField")|| $obj.attr("id");
				// 真值
				var ivVal = $obj.text();
				
				me.setObjVal(iv, ivId, ivVal);

			});
			$("a[fieldType='db']").each(function() {
				var $obj = $(this);
				var ivId = $obj.attr("dbField")|| $obj.attr("name");
				// 真值
				var ivVal = $obj.attr("value");
				me.setObjVal(iv, ivId, ivVal);

			});
			// radio
			$("input[type='radio'][fieldType='db']:checked").each(
					function() {
						var $obj = $(this);
						var ivId = $obj.attr("dbField")|| $obj.attr("name");
						// 真值
						var ivVal = $obj.val();
						
						me.setObjVal(iv, ivId, ivVal);
						// 显示值
						var ivValDesc = $obj.next("span").text();
						me.setObjVal(iv, ivId + "_desc", ivValDesc);
					});
			return iv;
		},
		setObjVal : function(obj, id, val) {
			if (obj == "undefined" || null == obj || null == id
					|| '' == id)
				return;
			if (!obj.hasOwnProperty(id)) {
				obj[id] = val;
			} else {
				obj[id] = obj[id] + "," + val;
			}
		},
		setSrcById:function(id,value){
			$("#"+id).attr("src",value);
		},
		setInnerTextById:function(id,value){
			$("#"+id).html(value);
		}
	})
	window.commonJS = new CommonJS();
}(window));
var timer
function heartBeatScroll(){ 
	$(window).bind("scroll",function(){
		window.clearTimeout(timer);
		timer = window.setTimeout(function(){
			for(i=0;i<scroll_ids.length;i++){
				var p_id =scroll_ids[i];
				var selJq = $("[id='"+p_id+"']");
				if(selJq.length>0){
					var scroll_top = $(this).scrollTop();
					var base_top = selJq.attr("base_top"); //基础高度
					if(!base_top){
						base_top = selJq.css("top");
						base_top= base_top.substring(0,base_top.length-2);
						selJq.attr("base_top",base_top);
					}
					selJq.css("top",scroll_top+new Number(base_top)+100);
				}
			}
		},50)
	});
}

function getUrl(){
	var pageUrl = document.location.href;
	var source_url = pageUrl || document.referrer;
	if (!source_url) {
        try {
            if (window.opener) {
            	
                source_url = window.opener.location.href;
            }
	    }catch (e) {}
    }
    return source_url;
}
		




//集团监控指标 IT运营视窗报表
//$(window).unload( function () {
//	//alert("invalidateSession");
//	invalidateSession(); 
//});

//function invalidateSession(){
//	$.ajax({
//		type: "post",
//		url: "/public/v4/common/control/page/invalidate.jsp"
//	});
//}

//add by wui 二级菜单
function showSecondMenu(serv_type,user_flag,area_code,parentPanel,func_id){
   var param ={};
   param.serv_type =serv_type;
   param.user_flag =user_flag;
   param.area_code =area_code;
   var htmlArr = [];
   htmlArr.push('<ul>');
   parentPanel.empty();
   Service.asExcute(func_id,false,param,function(reply){
		var queryList = reply[func_id];
		var lists = [];
		if(queryList != null && queryList.length>0){
			for( var i=0; i<queryList.length; i++ ){
				var map =queryList[i];
				var orderId = map['ORDER_ID'];
				var isAdd = false;
				for( var j=0; j<lists.length; j++ ){
					var returnMap =lists[j];
					var p_orderId = returnMap['ORDER_ID'];
					if(p_orderId ==orderId){
						var subList = returnMap['SUBLIST'];
						!subList&&(	returnMap["SUBLIST"]=[]);
						returnMap['SUBLIST'].push(map);
						isAdd = true;
					}else{
						isAdd = false;
					}
				}
				if(!isAdd){
					lists.push(map);
				}
			}
		}
		if(lists){
			for(var i=0;i<lists.length;i++){
				
				var menu = lists[i];
				htmlArr.push('<h6><a href="'+menu['URL']+'">'+menu['SERV_TYPE_NAME']+'</a></h6><p>');
				var subMenus = menu['SUBLIST'];
				htmlArr.push('<a href="'+menu['INTRO_URL']+'" title="'+menu['SERV_NAME']+'">'+menu['SERV_NAME']+'</a>');
				if(subMenus){
					for(var j=0;j<subMenus.length;j++){
							var subMenu = subMenus[j];
							htmlArr.push("|");
							htmlArr.push('<a href="'+subMenu['INTRO_URL']+'" title="'+subMenu['SERV_NAME']+'">'+subMenu['SERV_NAME']+'</a>');
						}
				}
			}
			htmlArr.push("</p></ul>");
			if(!!lists &&lists.length>0){
				parentPanel.append(htmlArr.join(""));
				parentPanel.show();
			}else{
				parentPanel.hide();
			}
		}
 });
}

