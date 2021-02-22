/**
 * 业务受理核心模块
 */
(function(scope) {

	var PageForm = Base.extend({
		constructor : function(config) {
			this.version = "1.0";
			this.config ={};
			this.id ='';
			CommonUtils.apply(this.config,config);
		},
		getTwbCardDEF:function(form_id,parent_id){ //展示属性信息
			var me =this;
			this.config.method='GET_TWB_CARD_DEF';
			Service.asExcute("TWB_ACCEPT", false, this.config, function(reply){ //获取卡片信息
				var tabProps = reply['RET_OBJ'];
				var hide_card ='F';
				if(tabProps && tabProps.length>0)
				{
					var tabProp =tabProps[0];
					hide_card = tabProp['hide_card'];
				}
				if(me.hide_card =="T"){
					hide_card = me.hide_card;
				}
				var serv_no = me.config.serv_no_only_view || me.config.serv_no;
				var propHtmls = offMakePage.pagelayout(tabProps,serv_no,me.getFormId(),1,hide_card);
				var add_id = parent_id || me.config.div_id;
				$("[id='"+ me.config.div_id+"']").append(propHtmls);
				me.addTitle();
				me.downLoad();
			})
		},
		downLoad:function(callBack){ //装载下拉框静态数据
			var me =this;
			var attr_codes ="";
			$("#"+me.getFormId()).find("select").each(function(){
				var attr_code = $(this).attr("attr_code");
				attr_codes+=attr_code+",";
			})
			this.config.attr_codes =attr_codes;
			this.config.method='GET_STATIC_DATA';
			Service.excuteNoLoading("TWB_ACCEPT", false, me.config, function(reply){
				var optionsMap = reply['RET_OBJ'];
				for(p in optionsMap){
					var attr_code = p;
					var options = optionsMap[attr_code];
					for(var i=0;i<options.length;i++)
					{
						var option =options[i];
						var attr_value = option['value']||option['pkey']||option['sm_used_view'];
						var attr_desc = option['value_desc']||option['pname']||option['sm_disp_view'];
						var area_code = option['area_code']||option['area_code']||option['area_code']; //按本地网过滤
						var login_area_code = acpBeanFactory.req_param['area_code'];
						if(!area_code || area_code =="0000" || area_code ==login_area_code) //不存在，或者为全省
							$("select[attr_code='"+attr_code+"']").append("<option "+"value="+attr_value+">"+attr_desc+"</option>");
						
					}
				}
				callBack && callBack();
			})
			
		},
		addTitle:function(){
			var len =$("#"+this.getFormId()).find("input:visible,select:visible,textarea:visible").length;
			if(len>0 && this.p_title){
				$("<div class='ser_tit2' ><h3>"+this.p_title+"</h3><span></span></div>").insertBefore($("#"+this.getFormId()));
			}
			var addVertify = this.addVertify;
			if(len>0 && addVertify !="no") //表单添加验证码
			{	
				var formTable = $("#"+this.getFormId());
				formTable.append($("<tr><td>请输入验证码</td><td name='verfify_td'></td></tr>"));
				formTable.find("td[name='verfify_td']").append($("#verify_key_mem_div").html());
				var loginInput =formTable.find("[name='logon_valid']");
				loginInput.unbind("focus");
				loginInput.bind("focus",function(){
					var key = document.getElementById("verify_key");
					var time = new Date();
					key.setAttribute("src","/public/v4/common/control/page/image.jsp?date="+ time);
					document.getElementById('logon_valid').focus();
	
				})
				$("#verify_key_div").remove();
			}
		},
		disabledForm:function(){ //设置文本框失效
			$("#"+this.getFormId()).find("input,select,textarea").attr("disabled",true);
		},
		enableForm:function(){ //设置文本框失效
			$("#"+this.getFormId()).find("input,select,textarea").removeAttr("disabled");
		},
		validateForm:function(){
			var result = true;
			$("#"+this.getFormId()).find("input,select,textarea").each(function(){
				var is_null = $(this).attr("is_null");
				var attr_format = $(this).attr("attr_format");
				var check_message = $(this).attr("check_message");
				var field_desc = $(this).attr("field_desc");
				var cname =  $(this).closest("td").prev("td").text();
				var restrict_val = $(this).attr("min");
				if(is_null =='F' && !$(this).val())
				{
					alert(field_desc+"不能为空！");
					result = false;
					return false;
				}
				
				if(attr_format && $(this).val()){
					var regix = eval(attr_format);
					if(!regix.test($(this).val())){
						alert(check_message);
						result = false;
						return false;
					}
					
				}
			});
			return result;
		},
		setValue:function(element_id,value){ //更新输入框的值
			var serv_no = this.config.serv_no;
			var id = serv_no+"_"+element_id;
			$("#"+id).val(value);
		},
		getValue:function(element_id){ //更新输入框的值
			var serv_no = this.config.serv_no;
			var id = serv_no+"_"+element_id;
			return $("#"+id).val();
		},
		getFormId:function(){ //表单id格式
			this.id = this.config.serv_no+"_form_"+this.form_id+"";
			return this.id;
		}
	})
	
	window.PageForm =PageForm;
	
}(window));
