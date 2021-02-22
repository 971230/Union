
var ShopUtil = { 
	/**
	 * 获取字符字节长度
	 * 
	 * @param {}
	 *            str
	 * @return {}
	 */
	getByteLen : function(str) {// 获取字符串长度
		var l = str.length;
		var n = l;
		for (var i = 0; i < l; i++) {
			if (str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255) {
				n++;
			}
		}
		return n;
	},
	/**
	 * 判断对象是否为空
	 * 
	 * @param {}
	 *            obj
	 * @return {Boolean}
	 */
	isEmpty : function(obj) {
		var me = this;
		if (null == obj || "undefined" == obj) {
			return true;
		} else {
			var objType = typeof obj;
			// 字符串
			if ('string' === objType && me.getByteLen(obj) == 0) {
				return true;
			}
			// 数组
			if ($.isArray(obj) && obj.length == 0) {
				return true;
			}
			return false;
		}
	},
	getInputDomain : function() {
					var iv = {},me=this;
					$("input[type='checkbox'][dataType]:checked").each(
						function() {
							var $obj = $(this);
							var ivId = $obj.attr("name");
							// 真值
							var ivVal = $obj.val();
							me.setObjVal(iv, ivId, ivVal);
							// 显示值
							var ivValDesc = $obj.attr("cname");
							me.setObjVal(iv, ivId + "_desc", ivValDesc);
					});

					// text
					$("input[type='text'][dataType]").each(function() {
							var $obj = $(this);
							var ivId = $obj.attr("id");
							var ivVal = $obj.val();
							me.setObjVal(iv, ivId, ivVal);
							
						});
						
					// file
					$("input[type='file'][dataType]").each(function() {
							var $obj = $(this);
							var ivId = $obj.attr("id");
							var ivVal = $obj.val();
							me.setObjVal(iv, ivId, ivVal);
							
						});
					
					// password
					$("input[type='password'][dataType]").each(function() {
							var $obj = $(this);
							var ivId = $obj.attr("id");
							var ivVal = $obj.val();
							me.setObjVal(iv, ivId, ivVal);
							
						});

					// hidden
					$("input[type='hidden'][dataType]").each(
							function() {
								var $obj = $(this);
								var ivId = $obj.attr("id");
								var ivVal = $obj.val();
								me.setObjVal(iv, ivId, ivVal);
							});

					// select
					$("select[dataType=''] option:selected ").each(
							function() {
								var $obj = $(this);
								var ivId = $obj.parent().attr("id");
								// 真值
								var ivVal = $obj.val();
								me.setObjVal(iv, ivId, ivVal);
								// 显示值
								var ivValDesc = $obj.text();
								me.setObjVal(iv, ivId + "_desc", ivValDesc);

							});

					// textarea
					$("textarea[dataType]").each(function() {
								var $obj = $(this);
								var ivId = $obj.attr("id");
								// 真值
								var ivVal = $obj.val();
								me.setObjVal(iv, ivId, ivVal);

							});
			
					$("span[dataType]").each(function() {
						var $obj = $(this);
						var ivId = $obj.attr("id");
						// 真值
						var ivVal = $obj.text();
						
						me.setObjVal(iv, ivId, ivVal);

					});
					// radio
					$("input[type='radio'][dataType='']:checked").each(
							function() {
								var $obj = $(this);
								var ivId = $obj.attr("name");
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
				}
};
