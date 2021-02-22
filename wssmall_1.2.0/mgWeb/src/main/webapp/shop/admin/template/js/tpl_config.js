var Template={
	url:"",
	pattern:"",
	saveTpl:function(){
		var flag=$("#flag").val();
		var order_template_type=$("#tplType").val();
		var os=document.getElementById("typeSel");
		if(flag=="update"){
			//清空下拉列表
			os.options.length = 0;
			// 生成option对象
			var option1= new Option("原子模型","1");
			var option2= new Option("复合模型","2");
			// 添加到下拉列表中
			os.options[os.options.length] = option1;
			os.options[os.options.length] = option2;
			if(order_template_type=="1"){
				option1.selected=true;
			}else{
				option2.selected=true;
			}
		}else{
			order_template_type=$("#typeSel").val();
			os.options[0].selected=true;
		}
		$("#saveBtn").bind("click",function(){
			var flag=$("#flag").val();
			var templateId=$("#templateId").val();
			var order_template_code=$("#template_code").val();
			var order_template_name=$("#template_name").val();
			var order_template_version=$("#template_version").val();
			var order_template_type=$("#typeSel").val();
			var order_business_type=$("#business_type").val();
			var order_area=$("#area").val();
			var order_source_system=$("#source_system").val();
			var order_exchange_protocol=$("#exchange_protocol").val();
			var order_template_ids=$("#template_ids").val();
			var order_callback_info=$("#callback_info").val();
			var order_response_content=$("#response_content").val();
			var catalogue_id=$("#catalogueId").val();
			//判空
			if(Template.validateText("template_code","模板编码")==false
				|| 	Template.validateText("template_name","模板名称")==false
				|| 	Template.validateText("template_version","版本号")==false
				|| 	Template.validateText("typeSel","模板类型")==false
				/*|| 	Template.validateText("business_type","适合业务类型")==false
				|| 	Template.validateText("area","适合地区")==false
				|| 	Template.validateText("source_system","适合调用来源")==false
				|| 	Template.validateText("exchange_protocol","交互协议类型")==false
				|| 	Template.validateText("template_ids","子模板列表")==false
				|| 	Template.validateText("response_content","报文标准内容")==false*/
			){
				return;
			}
			pattern=/^[(a-z)|(A-Z)|(0-9)]{1,20}$/;
			if(!pattern.test(order_template_code)){
				alert("模板编码为1-20位字母和数字组合");
				$("#template_code").focus();
				return;
			}
			pattern=/^[0-9]{1,3}$/;
			if(!pattern.test(order_template_version)){
				alert("请输入1-3位数的版本号");
				$("#template_version").focus();
				return;
			}
			//ajax提交
			if(flag=="add"){
				url=ctx+"/shop/admin/ordTplAction!saveTpl.do?ajax=yes";
			}else{
				url=ctx+"/shop/admin/ordTplAction!updateTpl.do?ajax=yes&ordTplDTO.order_template_id="+templateId;
			}
			data={"ordTplDTO.order_template_code":order_template_code,"ordTplDTO.order_template_name":order_template_name,"ordTplDTO.order_template_version":order_template_version,"ordTplDTO.order_template_type":order_template_type,
				 "ordTplDTO.order_business_type":order_business_type,"ordTplDTO.order_area":order_area,"ordTplDTO.call_source_system":order_source_system,"ordTplDTO.exchange_protocol":order_exchange_protocol,
				 "ordTplDTO.sub_template_id":order_template_ids,"ordTplDTO.callback_info":order_callback_info,"ordTplDTO.basic_response_content":order_response_content,"ordTplDTO.catalogue_id":catalogue_id};
		
			Cmp.excute('', url, data, function(data){
				if(data!=null){
					if(data.msg=="该模板已存在"){
						alert(data.msg);
						$("#template_code").focus();
						return;
					}
					Directory.page_close();
					//mod by chen.zuoqin  只在模板目录树上打开界面
					if(flag=="add"){
						templateId=data.tplId;
						$("a[id='root_id"+catalogue_id+"']").trigger("click");
					}
					$("#tpl_id"+templateId).html("<i class='treeic4'></i>"+order_template_name);
					$("a[id='tpl_id"+templateId+"']").trigger("click");
					getTreeBase(id);
				}
			},"json");
		});
		$("#cloBtn").bind("click",function(){
			Directory.page_close();
		});
	},
	validateText:function(id,title){
		var oText=$("#"+id);
		if(oText==null || oText.val()=="" || oText.val()=="0"){
			alert(title+"不能为空");
			oText.focus();
			return false;
		}else{
			return true;
		}
	}
};
Template.saveTpl();	
