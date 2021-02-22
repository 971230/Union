var Material = {
		init:function(){
			var me = this;
			me.initClk();
			me.recoveryMaterial();
		},
		/**
		 * 编辑时还原对应项
		 * */
		recoveryMaterial:function(){
			if($("#hiddenMaterialId").val()!=""){
				//锁住不能修改的项
				$("input[name='material.aname']").attr("readonly","readonly");
				$("#notChinese").attr("readonly","readonly").attr("name","code");
				//还原素材大类
				var seq = $("#hiddenBigType").val();
				$("#select_big_type").find("option[value='"+seq+"']").attr("selected","selected").trigger("change");
				$("#type_small_"+seq).find("select").find("option[value='"+$("#hiddenSmallType").val()+"']").attr("selected","selected");
				//属性json串处理
				var jsonData = eval('(' + $('#textarea_hidden').val() + ')');
				switch(seq)
				{
				case "1":
				  $("tr.shiping").find("input[name='resolution']").val(jsonData.resolution);
				  $("tr.shiping").find("input[name='format']").val(jsonData.format);
				  $("tr.shiping").find("input[name='size']").val(jsonData.size);
				  $("tr.shiping").find("input[name='timeLength']").val(jsonData.timeLength);
				  break;
				case "2":
				  $("tr.tupian").find("input[name='resolution']").val(jsonData.resolution);
				  $("tr.tupian").find("input[name='size']").val(jsonData.size);
				  break;
				case "3":
				  $("tr.yingyong").find("input[name='resolution']").val(jsonData.resolution);
				  break;
				case "4"://------------------------
				  $("tr.xinwen").find("input[name='title']").val(jsonData.title);
				  $("tr.xinwen").find("input[name='content']").val(jsonData.content);
				  $("tr.xinwen").find("input[name='eff_date']").val(jsonData.eff_date);
				  $("tr.xinwen").find("input[name='exp_date']").val(jsonData.exp_date);
				  $("tr.xinwen").find("input[name='model']").val(jsonData.model);
				  $("tr.xinwen").find("input[name='state']").val(jsonData.state);
				  //还原状态下拉框
				  $("#news_state_sel").find("option[value='"+jsonData.state+"']").attr("selected","selected");
				  break;
				default:
				  $("tr.gonggao").find("input[name='title']").val(jsonData.title);
				  $("tr.gonggao").find("input[name='content']").val(jsonData.content);
				  $("tr.gonggao").find("input[name='eff_date']").val(jsonData.eff_date);
				  $("tr.gonggao").find("input[name='exp_date']").val(jsonData.exp_date);
				  $("tr.gonggao").find("input[name='model']").val(jsonData.model);
				  $("tr.gonggao").find("input[name='state']").val(jsonData.state);
				  $("#bbs_state_sel").find("option[value='"+jsonData.state+"']").attr("selected","selected");
				}
			}
		},
		initClk:function(){
			var me= this;
			var trShow="shiping";
			//新闻、公告状态改变事件
			$("#news_state_sel").bind("change",function(){
				$(this).parent().find("input[type='hidden']").attr("value",$(this).val());
			});
			$("#bbs_state_sel").bind("change",function(){
				$(this).parent().find("input[type='hidden']").attr("value",$(this).val());
			});
			
			$("#select_big_type").bind("change",function(){
				var v=$(this).val();
				$("#type_small_"+v).siblings().hide();
				$("#type_small_"+v).siblings().find("select").removeAttr("name");
				$("#type_small_"+v).show();
				$("#type_small_"+v).find("select").attr("name","material.subtype").show();
				
				switch(v)
				{
				case "1":
				  $("tr.shiping").show();
				  $("tr.tupian").hide();
				  $("tr.yingyong").hide();
				  $("tr.xinwen").hide();
				  $("tr.gonggao").hide();
				  trShow="shiping";
				  break;
				case "2":
				  $("tr.shiping").hide();
				  $("tr.tupian").show();
				  $("tr.yingyong").hide();
				  $("tr.xinwen").hide();
				  $("tr.gonggao").hide();
				  $("tr.tupian").find("input[type='file']").attr("name","picture");
				  $("tr.yingyong").find("input[type='file']").attr("name","");
				  $("tr.xinwen").find("input[type='file']").attr("name","");
				  trShow="tupian";
				  break;
				case "3":
				  $("tr.shiping").hide();
				  $("tr.tupian").hide();
				  $("tr.yingyong").show();
				  $("tr.xinwen").hide();
				  $("tr.gonggao").hide();	
				  $("tr.tupian").find("input[type='file']").attr("name","");
				  $("tr.yingyong").find("input[type='file']").attr("name","picture");
				  $("tr.xinwen").find("input[type='file']").attr("name","");
				  trShow="yingyong";
				  break;
				case "4":
				  $("tr.shiping").hide();
				  $("tr.tupian").hide();
				  $("tr.yingyong").hide();
				  $("tr.xinwen").show();
				  $("tr.gonggao").hide();	 
				  $("tr.tupian").find("input[type='file']").attr("name","");
				  $("tr.yingyong").find("input[type='file']").attr("name","");
				  $("tr.xinwen").find("input[type='file']").attr("name","picture");
				  trShow="xinwen";
				  break;
				default:
				  $("tr.shiping").hide();
				  $("tr.tupian").hide();
				  $("tr.yingyong").hide();
				  $("tr.xinwen").hide();
				  $("tr.gonggao").show();
				  trShow="gonggao";
				}
			});
			//编码不能中文
			$("#notChinese").blur(function(){
				if (/[\u4E00-\u9FA5]/i.test($(this).val())) {
				    alert('编码不允许有中文！');
				    $(this).val("").focus();
				}
			});
			//确定
			$("#materialConfirmBtn").bind("click",function(){
//				if($("#materialForm").find("input[required='true'][value='']").length>0){
//					alert("表单中有必填项，请检查！");
//					return;
//				}
				if(me.checkDate()){//起止时间正确
					//属性转化为json
					var json={};
					$("tr."+trShow).each(function(i,data){
						$(data).find("input").each(function(j,da){
							json[$(da).attr("name")]=$(da).val();
						});
					});
					$("#attribute_hidden").val(me.jsonToString(json));
				}
				
				$("#materialForm").submit();
			});
			
			//检测名字重复
			$("#materialNameCheck").bind("blur",function(){
				var v = $.trim($(this).val());
				if(v.length>0){
					$.ajax({
						url:"material!checkNameExists.do?ajax=yes&name="+v,
						type:"POST",
						dataType:"json",
						success:function(reply){
							$("#materialNameCheck").parent().find("span").remove();
							if(reply.result>0){
								$("#checkResult").css("color","red").html("此素材名称已经存在！");
								$("#materialNameCheck").focus();
							}else{
								$("#checkResult").css("color","green").html("此素材名称可以使用！");
							}
						},error:function(a,b,c){
							$.Loading.hide();
							alert("出现错误 ，请重试");
						}
					});
				}
			});
		},
		jsonToString:function(obj){
			var t = typeof(obj);
			if (t != "object" || obj === null) {
				// simple data type
				if (t == "string")
					obj = '"' + obj + '"';
				return String(obj);
			} else {
				// recurse array or object
				var n, v, json = [], arr = (obj && obj.constructor == Array);
				for (n in obj) {
					v = obj[n];
					t = typeof(v);
					if (t == "string")
						v = '"' + v + '"';
					else if (t == "object" && v !== null)
						v = util.jsonToString(v);
					json.push((arr ? "" : '"' + n + '":') + String(v));
				}
				return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
			}
		},
		/**
		 * 检查起止日期
		 * */
		checkDate:function(){
			var me = this;
			if(!$("tr.xinwen").is(":hidden")){
				var s1=$("#startDate_1").val();
				var s2=$("#endDate_1").val();
				if(s1!=""&&s2!=""){
					if(s1>s2){
						alert("起止时间有误，请检查！");
						return false;
					}
				}
			}
			if(!$("tr.gonggao").is(":hidden")){
				var s1=$("#startDate_2").val();
				var s2=$("#endDate_2").val();
				if(s1!=""&&s2!=""){
					if(s1>s2){
						alert("起止时间有误，请检查！");
						return false;
					}
				}
			}
			return true;
		},
		jsonBack:function(responseText){
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="material!getList.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
}


$(function() {
	Material.init();
	setTimeout(function(){
		if ($("img.material:visible").width() > 300) {
			$("img.material:visible").attr("width", "300");
		}
		if ($("img.material:visible").height() > 200) {
			$("img.material:visible").attr("height", "200");
		}
	},10);
	
});
