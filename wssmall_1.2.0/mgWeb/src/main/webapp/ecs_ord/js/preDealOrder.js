$(function(){
	Ship.provChange();
	Ship.cityChange();
	Shop.prodTypeChange();
	Gift.init(); 
	$(".ipttxt").removeClass("ipttxt");
	 
	  var cust_flag = $("#custFlag").val();
	  if(!cust_flag){
	  $("#union_cust").hide();
  }
	  $("#selDevelopmentBtn").bind("click",function(){
		  var url= app_path+"/shop/admin/orderWarningAction!getDevelopmentList.do?ajax=yes";
			$("#selDevelopmentDlg").load(url,{},function(){});
			 Eop.Dialog.open("selDevelopmentDlg");
		  });
	  Eop.Dialog.init({id:"selDevelopmentDlg",modal:true,title:"选择发展人",width:'800px'});
	   
	  //选择县分
	 $("#selXcountyBtn").bind("click",function(){
		 var stdOrderId = $("#stdOrderId").val();
		 var url= app_path+"/shop/admin/orderWarningAction!getXcountyList.do?ajax=yes&stdOrderId="+stdOrderId;
			$("#selXcountyDlg").load(url,{},function(){});
			 Eop.Dialog.open("selXcountyDlg");
		 
		});
	  Eop.Dialog.init({id:"selXcountyDlg",modal:true,title:"选择县分信息",width:'800px'});
	  
	  //选择标准地址
	  $("#selStdAddrBtn").bind("click",function(){
		     var county_id = $("[field_name='county_id']").val();
		     if(county_id=='' || county_id == null ){
		    	 alert("请先选择营业县分");
		    	 return;
		     }
		     debugger;
			 var url= app_path+"/shop/admin/orderWarningAction!getStdAddress.do?ajax=yes&first_load=yes";
				$("#selStdAddressDlg").load(url,{},function(){});
				 Eop.Dialog.open("selStdAddressDlg");
			});
		 Eop.Dialog.init({id:"selStdAddressDlg",modal:true,title:"选择标准地址",width:'800px'});
	//查询光猫ID
	$("#selModermIDBtn").bind("click",function(){
		var stdOrderId = $("#stdOrderId").val();
		var extraInfo = $("#extraInfo").val();
		var url = app_path+"/shop/admin/orderWarningAction!queryModermId.do?ajax=yes&stdOrderId="+stdOrderId+"&extraInfo="+extraInfo;
		$("#selModermIDDlg").load(url,{},function(){});
		 Eop.Dialog.open("selModermIDDlg");
	}); 
	 Eop.Dialog.init({id:"selModermIDDlg",modal:true,title:"查询光猫ID",width:'800px'});
	 
	 //县分自动查询
	 $("#selAreaCountyBtn").bind("click",function(){
		 var stdOrderId = $("#stdOrderId").val();
		 var url = app_path+"/shop/admin/orderWarningAction!areaQuery.do?ajax=yes&stdOrderId="+stdOrderId;
		 $.Loading.show("正在查询。请稍候...");
		 $.ajax({
			 url:url,
			 dataType:'json',
			 success: function(reply){
				if(reply.result==0) {
					$.Loading.hide();
					var value = reply.data;
					var countyName = value.split(",")[0];
					var countyId = value.split(",")[1];
					$("#county_name").val(countyName);
					$("[field_name='county_id']").val(countyId);
					alert(reply.msg);
				}else {
					$.Loading.hide();
					alert(reply.msg);
				}
		     }
		});
	 });
});
function preDealSave_callback(reply){
	 if(reply.result=='0'){
		 alert("操作成功");
		 window.location.href=data.action_url;
	 }else{
		 alert(reply.message);
	 }
}
function reBackCheck(){
	return true;
}
//货品类型
var TypeFun={
	 getCatList:function(typeSel,catSel){
			   var product_type = $("[name='"+typeSel+"']").val();
			   var product_cat =  $("[name='"+catSel+"']").val();
			   $.ajax({
		        	url:app_path+"/shop/admin/orderFlowAction!getCatListByProdType.do?ajax=yes&product_type="+product_type,
		        	dataType:"json",
		        	data:{},
		        	success:function(reply){
		        		var catList = reply;
		        		var catHtmlStr = "<option value=''>--请选择--</option>";
		        		for(var i=0;i<catList.length;i++){
		        			var obj = catList[i];
		        			if(product_cat==obj.cat_id){
		        				catHtmlStr += "<option value='"+obj.cat_id+"' selected='selected'>"+obj.name+"</option>";
		        			}else{
		        				catHtmlStr += "<option value='"+obj.cat_id+"'>"+obj.name+"</option>";
		        			}
		        		}
		        	    $("[name='"+catSel+"']").empty().html(catHtmlStr);
		        	}
				});
			   
	 }
}
//物流信息
var Ship={
    provChange:function(){
    	$("#provinc_code").change(function(){
    		Ship.getCityListByProvId();
    	});
    },
    cityChange:function(){
    	$("#city_code").change(function(){
    		Ship.getDistrictListByCityCode();
    	});
    },
	getCityListByProvId:function(){
		var prov_id = $("#provinc_code").val();
		$.ajax({
         	url:app_path+"/shop/admin/orderFlowAction!getCityListByProvId.do?ajax=yes&provinc_code="+prov_id,
         	dataType:"json",
         	data:{},
         	success:function(reply){
         		$("#city_code").empty();
         		var cityList = reply;
         		var cityHtmlStr = "";
         		cityHtmlStr =("<option value='0' selected='selected'>--请选择--</option>");
         		for(var i=0;i<reply.length;i++){
         			var obj = reply[i];
         			cityHtmlStr += "<option value='"+obj.code+"'>"+obj.name+"</option>";
         		}
         	    $("#city_code").empty().html(cityHtmlStr);
         	}
		});
	},
	getDistrictListByCityCode:function(){
		var city_code = $("#city_code").val();
		$.ajax({
         	url:app_path+"/shop/admin/orderFlowAction!getDistrictListByCityId.do?ajax=yes&city_code="+city_code,
         	dataType:"json",
         	data:{},
         	success:function(reply){
         		$("#district_id").empty();
         		var cityList = reply;
         		var districtHtmlStr = "";
         		districtHtmlStr =("<option value='0' selected='selected'>--请选择--</option>");
         		for(var i=0;i<reply.length;i++){
         			var obj = reply[i];
         			districtHtmlStr += "<option value='"+obj.code+"'>"+obj.name+"</option>";
         		}
         	    $("#district_id").empty().html(districtHtmlStr);
         	}
		});
	}
	
}
var Shop={
	//货品类型切换事件
	prodTypeChange:function(){
	   $("#product_type").change(function(){
		   Shop.getCatByTypeId();
	   });
	},
	//根据货品Id获取获取小类列表
    getCatByTypeId:function(){
    	var product_type = $("#product_type").val();
    	alert("product_type======="+product_type);
    	$.ajax({
         	url:app_path+"/shop/admin/orderFlowAction!getCatListByProdType.do?ajax=yes&product_type="+product_type,
         	dataType:"json",
         	data:{},
         	success:function(reply){
         		var catList = reply;
         		var catHtmlStr = "";
         		for(var i=0;i<catList.length;i++){
         			var obj = catList[i];
         			catHtmlStr += "<option value='"+obj.cat_id+"'>"+obj.name+"</option>";
         		}
         	    $("#catList").empty().html(catHtmlStr);
         	}
		});
    },
  //三种不同类型的货品的品牌切换事件
    brandChange:function(){
    	$("#termianl.brand_number").change(function(){
    		var brand_code = $(this).val();
    		Shop.getSpecificationByType(brand_code,"termianl.specification_code");
    	});
    	$("#netCard.brand_number").change(function(){
    		var brand_code = $(this).val();
    		Shop.getSpecificationByType(brand_code,"netCard.specification_code");
    	});
    	$("#accessories.brand_number").change(function(){
    		var brand_code = $(this).val();
    		Shop.getSpecificationByType(brand_code,"accessories.specification_code");
    	});
    },
	//根据品牌Id型号列表   根据ID加载下拉框数据
    getSpecificationByType:function(type_id,id){/*id为不同类型select的Id*/
    	$.ajax({
         	url:app_path+"/shop/admin/orderFlowAction!getBrandListByTypeId.do?ajax=yes&product_type="+type_id,
         	dataType:"json",
         	data:{},
         	success:function(reply){
         		var brandList = reply;
         		var brandHtmlStr = "";
         		for(var i=0;i<brandList.length;i++){
         			var obj = brandList[i];
         			brandHtmlStr += "<option value='"+obj.brand_id+"'>"+obj.name+"</option>";
         		}
         	    $("#"+id).empty().html(brandHtmlStr);
         	}
		});
    }
}
var Gift={
	init:function(){
		Gift.brandChange();
	},
    brandChange:function(){
    	$("[name='gift.params.gift_brand']").each(function(){
    		$(this).change(function(){
    			var $obj = $(this);
    			Gift.getModelList($obj);
    		});
    	});
        $("[name='gift.params.gift_brand']").each(function(){
	    	var $obj = $(this);
	    	Gift.getModelList($obj);
	    });
    },
	getModelList:function($obj){
		 var giftBrand = $obj.val();
    	 var modelCodeTd = $obj.parent("td").parent("tr").find("name='gift.params.gift_model'");
    	 $.ajax({
          	url:app_path+"/shop/admin/orderFlowAction!getAccessoriesByBrandId.do?ajax=yes&brand_code="+giftBrand,
          	dataType:"json",
          	data:{},
          	success:function(reply){
          		var modelList = reply;
          		var modelHtmlStr = "";
          		for(var i=0;i<modelList.length;i++){
          			var obj = modelList[i];
          			modelHtmlStr += "<option value='"+obj.code+"'>"+obj.name+"</option>";
          		}
          		 modelCodeTd.empty().html(modelHtmlStr);
          	}
 		});
	}

}
function certCallBack(data){	
	var result = data.result;
	if("0"==result){
		alert(data.message);
		var certinfo = data.certinfo;
		$("input[field_name='cert_address']").val(certinfo.addr);
		$("input[field_name='cert_failure_time']").val(certinfo.exp);
		var sex = certinfo.sex;
		if(sex=="M"){
			sex = "男";
		}else{
			sex = "女";
		};
		//and zhangjun
		/*var title1 = "姓名："+certinfo.certName+"  性别："+sex+"  民族："+certinfo.nation;
		var title2 = certinfo.certId;
		var title3 = certinfo.addr;
		$("#gztPhoto").attr("src","data:image/gif;base64,"+data.photo);
		$("#imgTitle1").text(title1);
		$("#imgTitle2").text(title2);
		$("#imgTitle3").text(title3);
		var picurls = data.picurls;
		$("#userPhoto1").attr("src",picurls[0]);
		$("#userPhoto2").attr("src",picurls[1]);
		$("#userPhoto3").attr("src",picurls[2]);
		$(".photo").bind("click",function(){
			var bigPic = parent.document.getElementById("bigPic");
			if(bigPic){
				bigPic.src = this.src;
				parent.document.getElementById("shield").style.display = "block";
				parent.document.getElementById("alertFram").style.display = "block";
			}else{
				showBigPic(this);
			}			
		});
		$("#certPhotoDiv").show();*/
	}else{
		alert(data.message);
	}
}
function valiPhotoLog(order_id,isAgree){
	var url = ctx+"/shop/admin/orderFlowAction!valiPhotoLog.do?ajax=yes&order_id="+order_id+"&isAgree="+isAgree;
	//$.Loading.show();
	$.ajax({
		type : "post",
		async : false,
		url : url,
		data : {},
		dataType : "json",
		success : function(data) {
			alert(data.message);
			//$.Loading.hide();
			$("#certPhotoDiv").hide();
		}
	});
}
function certEdit(){
	$("#certIsEdit").val("yes");
}
function isCertEdit(){
	var iscertedit = $("#certIsEdit").val();
	if("yes"==iscertedit){
		alert("请先保存信息再校验身份证");
		return false;
	}else{
		return true;
	}
}
function showBigPic(obj){
	var shield = document.createElement("DIV");
	shield.id = "shield";
	shield.style.position = "absolute";
	shield.style.left = "0px";
	shield.style.top = "0px";
	shield.style.width = "100%";
	shield.style.height = "100%";
	shield.style.background = "#D3D3D3";
	shield.style.textAlign = "center";
	shield.style.zIndex = "2999";
	shield.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
	shield.style.opacity="0.6"; 
	var alertFram = document.createElement("DIV");
	alertFram.id="alertFram";
	//<div id="dlg_bssDiv" class="dialogIf jqmID2" style="width: 800px; margin-top: 50px; z-index: 3000; opacity: 0.8; display: block;">
	alertFram.style.display="block";
	alertFram.style.position = "absolute";
	alertFram.style.width = "600px";
	alertFram.style.height = "600px";
	alertFram.style.left = "20%";
	alertFram.style.top="50px";
	alertFram.className = "dialogIf jqmID2";
	alertFram.style.background="white"
	alertFram.style.zIndex = "3000";
	var imgstr = "<div class=\"dialog_box\"><div class=\"head\"><div class=\"title\">大图</div><span class=\"closeBtn\" onclick=\"alertFram.style.display = 'none';shield.style.display = 'none';\"></span></div><div class=\"body dialogContent\">";
	imgstr = imgstr + "<img id='bigPic' src='"+obj.src+"' title=''/>";
	imgstr = imgstr + "</div><div class=\"head\"style=\"height:3px;background:#CAD9E6;cursor:move;\"></div></div>";
	alertFram.innerHTML = imgstr;
	parent.document.body.appendChild(alertFram);
	parent.document.body.appendChild(shield);

	$("#bigPic").bind("click",function(){
		parent.document.getElementById("shield").style.display = "none";
		parent.document.getElementById("alertFram").style.display = "none";
	});
}
function sms3NetSend(photoStatus,order_id) {
	var url = ctx+"/shop/admin/orderFlowAction!sms3NetSend.do?ajax=yes&order_id="+order_id+"&isAgree="+photoStatus;
	//$.Loading.show();
	$.ajax({
		type : "post",
		async : false,
		url : url,
		data : {},
		dataType : "json",
		success : function(data) {
			//$.Loading.hide();
			alert(data.message);
		}
	});
}

