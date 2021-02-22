var contractConfig = {
  init:function(){
    var self = this;
    
    Eop.Dialog.init({id:"terminalList",modal:true,title:"终端列表",width:'750px'});
    Eop.Dialog.init({id:"contractList",modal:true,title:"合约列表",width:'750px'});
    Eop.Dialog.init({id:"offerList",modal:true,title:"套餐列表",width:'750px'});
    
	$("#addTerminalBtn").unbind("click").bind("click",function() {
		self.showTerminal();
	});
	
	$("#addContractBtn").unbind("click").bind("click",function() {
		self.showContract();
	});
	
	$("#addOfferBtn").unbind("click").bind("click",function() {
		self.showOffer();
	});
	
	$("#isAccNbr").click(function() {
		self.changeNbrModel();
	});
	
	$("a[name='gooddelete']").live("click",function(){
	 	var tr = $(this).closest("tr");
	 	tr.remove();
	});
	$("a[name='gooddeleteOffer']").live("click",function(){
	 	var tr = $(this).closest("tr");
	 	tr.remove();
	 	self.flreshParamsBg();
	});
	
	$('#selectOkBottf').unbind("click").bind('click', function () {
        self.saveContractMachine();
     });
	$('#editContractBtn').unbind("click").bind('click', function () {
        self.editContractMachine();
     });
	
	$("#goods_price").numberbox({
	    min:0 
	});
	$("#contractMachineTables").find("input").each(function(idx,item){
		if($(item).attr("ime_mode_enable")!='1'){
			$(item).numberbox({ 
			    min:0 
			});
		}
		
	});
	
	self.initCatTree();
	self.loadLevel();
  },
  initCatTree:function(){
	  $.ajax({
			url:app_path+'/shop/admin/contractConfig!getCatTree.do?ajax=yes',
			type:"get",
			dataType:"html",
			success:function(html){
				$("#goods_cat_tree").append($(html).html());
			},
			error:function(){
				alert("获取分类树出错");
			}
		});
  },
  //切换是否卡号模式
  changeNbrModel : function() {
  	 var isNbr = $("#isAccNbr").is(":checked");
  	 if(isNbr){
  		$("#terminalTablesInput").hide();
  		$("#bss_code").show();
  		$("#is_set").show();
  		$("#is_attached").show();
  		$("#mobile_price").hide();
  	 }else{
  		$("#terminalTablesInput").show();
  		$("#bss_code").hide();
  		$("#is_set").hide();
  		$("#is_attached").hide();
  		$("#mobile_price").show();
  	 }
  },
  
  //展示终端
 showTerminal : function(params) {
 	 var url =app_path + "/shop/admin/contractConfig!terminalList.do?ajax=yes";
     Eop.Dialog.open("terminalList"); 
     $("#terminalList").load(url,params,function(){});
 },
 //展示合约
 showContract : function(contract_name) {
 	 var url =app_path + "/shop/admin/contractConfig!contractList.do?ajax=yes";
     Eop.Dialog.open("contractList"); 
     if(contract_name){
    	 url += "&contract_name="+encodeURI(encodeURI(contract_name,true),true);
     }
     $("#contractList").load(url,function(){});
 },
 
 //展示套餐
 showOffer : function(offer_name) {
	 var select = $("#contractTables input[name='goodsId']");
	 if(!select || select.length==0){
		 alert("请先选择合约!");
		 return ;
	 }
	 var contracts_gids = "";
	 select.each(function(idx,item){
		 var contract_gid = $(item).attr("gid");
		 if(idx>0){
			 contracts_gids += ",";
		 }
		 contracts_gids += contract_gid;
	 });
	 
	 
 	 var url =app_path + "/shop/admin/contractConfig!offerList.do?ajax=yes&selected_contracts="+contracts_gids;
     Eop.Dialog.open("offerList"); 
     if(offer_name){
    	 url += "&offer_name="+encodeURI(encodeURI(offer_name,true),true);
     }
     $("#offerList").load(url,function(){});
 },
 //加载档次
 loadLevel:function(){
	 var self = this;
		$.ajax({
			type : "POST",
			dataType : 'json',
			url : "contractConfig!getPackegeLevel.do?ajax=yes",
			success : function(result) {
				if(result && result.levelList){
                   var levelList = result.levelList;
                   for(var i=0;i<levelList.length;i++){
                	   self.createLevelArea(levelList[i]);  
                   }
				}
			},
			error : function(XMLHttpRequest, textStatus){
				alert("出错了");
			}
		}); 
 },
 //根据档次初始化档次参数配置
 createLevelArea:function(level){
	 $("#contractMachineTables thead tr").append("<td style='text-align:center;'>"+level+"</td>");
	 $("#contractMachineTables").find("#mobile_price").append("<td style='text-align:center;'><input  size='3' name='"+level+"' class='top_up_ipt'  value='0' onkeydown='onlyNum(this)' style='ime-mode:disabled;'></td>");
	 $("#contractMachineTables").find("#deposit_fee").append("<td style='text-align:center;'><input  size='3' name='"+level+"' class='top_up_ipt' value='0' onkeydown='onlyNum(this)' style='ime-mode:disabled;'></td>");
	 $("#contractMachineTables").find("#order_return").append("<td style='text-align:center;'><input  size='3' name='"+level+"' class='top_up_ipt' value='0' onkeydown='onlyNum(this)' style='ime-mode:disabled;'></td>");
	 $("#contractMachineTables").find("#mon_return").append("<td style='text-align:center;'><input size='3' name='"+level+"' class='top_up_ipt' value='0' onkeydown='onlyNum(this)' style='ime-mode:disabled;'></td>");
	 $("#contractMachineTables").find("#all_give").append("<td style='text-align:center;'><input  size='3' name='"+level+"' class='top_up_ipt' value='0' onkeydown='onlyNum(this)' style='ime-mode:disabled;'></td>");
 },
 //刷新配置区域背景
 flreshParamsBg : function() {
	 //套餐
	 $("#contractMachineTables input").closest("td").removeClass("month-fee-selected");
	 $("#offerTables").find("input[name='goodsId']").each(function(idx3,offer){
		 var month_fee = $(offer).attr("month_fee");
		 if(month_fee){
			 var input = $("#contractMachineTables").find("input[name="+month_fee+"]");
			 if(input){
				 $(input).closest("td").addClass("month-fee-selected");
			 }
		 }
	 });
 },
 checkPreSave: function(action){
	 var select ;
	 if(action != 'edit'){
		 var goods_price = $("#goods_price").val();
		 if(!goods_price){
			 if(goods_price!=0 || goods_price!="0"){
				 alert("请先填写商品销售价格!");
				 $("#goods_price").focus();
				 return false;
			 }
		 }
		 var cat_id = $("#goods_cat_tree").val();
		 if(!cat_id){
			 alert("请选择商品分类!");
			 $("#goods_cat_tree").focus();
			 return false;
		 }
		 
	 }
	 
	 var isAccNbr = $("#isAccNbr").is(":checked");
	 if(!isAccNbr){
		 select = $("#terminalTables input[name='goodsId']");
		 if(!select || select.length==0){
			 alert("请先选择终端!");
			 return false;
		 }
	 }
	 select = $("#contractTables input[name='goodsId']");
	 if(!select || select.length==0){
		 alert("请先选择合约!");
		 return false;
	 }
	 select = $("#offerTables input[name='goodsId']");
	 if(!select || select.length==0){
		 alert("请先选择套餐!");
		 return false;
	 }
	 return true;
 },
 getConfigParams:function(month_fee,isAccNbr){
	 var params = {};
	 if(month_fee){
		 
		 var mobile_price = $("#mobile_price").find("input[name="+month_fee+"]").val();
		 var deposit_fee = $("#deposit_fee").find("input[name="+month_fee+"]").val();
		 var order_return = $("#order_return").find("input[name="+month_fee+"]").val();
		 var mon_return = $("#mon_return").find("input[name="+month_fee+"]").val();
		 var all_give = $("#all_give").find("input[name="+month_fee+"]").val();
		 var bss_code = $("#bss_code").find("input[name='paramvalues']").val();
		 var is_set = $("#is_set").find("select[name='paramvalues']").val();
		 var is_attached = $("#is_attached").find("select[name='paramvalues']").val();
		 
			if(isAccNbr){
				params.paramnums = [7];
				params.groupnames = ["号卡参数"];
				params.paramnames = ["预存金额", "首月返还", "分月返还", "总送费金额","BSS编码","是否成品卡","是否副卡"];
				params.paramvalues = [deposit_fee, order_return, mon_return, all_give,bss_code,is_set,is_attached];
				params.ename = ["deposit_fee", "order_return", "mon_return", "all_give","bss_code","is_set","is_attached"];
				params.attrcode = ["", "", "", "","","DC_IS_SET","DC_IS_ATTACHED"];
				params.attrvaltype = ["0", "0", "0", "0","0","1","1"];
				params.attrtype = ["goodsparam", "goodsparam", "goodsparam", "goodsparam", "goodsparam", "goodsparam", "goodsparam"];
			}
			else{
				params.paramnums = [5];
				params.groupnames = ["合约机参数"];
				params.paramnames = ["购机金额", "预存金额", "首月返还", "分月返还", "总送费金额"];
				params.paramvalues = [mobile_price, deposit_fee, order_return, mon_return, all_give];
				params.ename = ["mobile_price", "deposit_fee", "order_return", "mon_return", "all_give"];
				params.attrcode = ["", "", "", "", ""];
				params.attrvaltype = ["0", "0", "0", "0", "0"];
				params.attrtype = ["goodsparam", "goodsparam", "goodsparam", "goodsparam", "goodsparam"];
			}
	 }
	 
     return params;
     
 },
 getTagsByName:function(terminal_name,contract_name){
	 var tag_id = "";
	 if(terminal_name && contract_name){
		 var tag_name = $.trim(terminal_name)+$.trim(contract_name);
			$.ajax({
				async:false,
				type : "POST",
				dataType : 'json',
				data :{tag_name:tag_name},
				url : "contractConfig!getTagsByName.do?ajax=yes",
				success : function(result) {
					if(result && result.tag_id){
				    	tag_id = result.tag_id;
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					//alert("出错了");
				}
			}); 
	 }
     return tag_id;
 },

 //保存合约机
 saveContractMachine:function(){
	 var self = this;
	 if(!self.checkPreSave()){
		 return;
	 }
	 var flag_all = true;
	 //终端
	 var terminal_name = "";
	 var terminal_gid = "";
	 var terminal_pid = "";
	 var goods_price = "";
	 var sn = "";
	 var model_code = "";
	 var cat_id = "";
	 var type_id = "";
	 
	 var isAccNbr = $("#isAccNbr").is(":checked");
	 if(!isAccNbr){//如果是合约机，则取终端
		 cat_id = $("#goods_cat_tree").val();
		 type_id = "20002"; 
	     $("#terminalTables").find("input[name='goodsId']").each(function(idx1,terminal){
	    	  terminal_name = $( terminal).attr("goods_name");
	    	  terminal_pid = $( terminal).attr("pid");
	    	  terminal_gid =$( terminal).attr("gid");
	    	  sn = $( terminal).attr("sn");
	    	  model_code = $( terminal).attr("model_code");
	    	  brand_id=$( terminal).attr("bid");
	     });		 
	 }else{//如果是号卡
		 cat_id = $("#goods_cat_tree").val();
		 type_id = "20000";
	 }
     goods_price = $("#goods_price").val();
     
     //合约
	 var contract_name = "";
	 var contract_gid = "";
	 var contract_pid = "";
	 $("#contractTables").find("input[name='goodsId']").each(function(idx2,contract){
    	  contract_name = $(contract).attr("goods_name");
    	  contract_pid =$(contract).attr("pid");
    	  contract_gid =$(contract).attr("gid");
	 });
	 
	 //通过终端盒套餐的名称找到商品包（es_tags），然后保存到商品包关联表es_tag_rel
     var tag_id = self.getTagsByName(terminal_name,contract_name);
	 
	 //套餐
	 var offer_array = [];
	 $("#offerTables").find("input[name='goodsId']").each(function(idx3,offer){
		 var offer1 = {};
		 offer1.offer_name = $(offer).attr("goods_name");
		 offer1.offer_gid =$(offer).attr("gid");
		 offer1.offer_pid =$(offer).attr("pid");
		 offer1.month_fee = $(offer).attr("month_fee");
		 offer1.rel_code = $(offer).attr("rel_code");
    	 offer_array.push(offer1);
	 });
	 
	 $.Loading.show("正在批量生成合约机，请耐心等待...");
	 for(var i=0;i<offer_array.length;i++){
		 var offer = offer_array[i];
		 name = terminal_name +offer.offer_name+ contract_name;
		 if(isAccNbr){
			 brand_id="2014032710061";
		 }
		 var data = {"goods.name":name,"goods.type_id":type_id,"goods.model_code":model_code,"goods.sn":sn,
				 "goodsPackage.p_code":offer.rel_code,"goodsPackage.sn":sn,"goods.cat_id":cat_id,
				 "goods.brand_id":brand_id,"goods.type":"goods","goods.price":goods_price,
				 "tag_id":tag_id};
		 if(offer.month_fee){
			 var parmas = self.getConfigParams(offer.month_fee,isAccNbr); 
			 data.paramnums = parmas.paramnums ; 
			 data.groupnames = parmas.groupnames ;
			 data.paramnames= parmas.paramnames  ;  
			 data.paramvalues = parmas.paramvalues  ;
			 data.ename = parmas.ename ;     
			 data.attrcode = parmas.attrcode ;   
			 data.attrvaltype= parmas.attrvaltype ;
			 data.attrtype = parmas.attrtype ; 
		 }
		 var goods_ids = [];
		 var product_ids = [];
		 var rel_types = [];
		 var rel_codes = [];
		 
		 if(!isAccNbr){//如果是合约机，则取终端
			 goods_ids.push(terminal_gid); 
			 product_ids.push(terminal_pid);
			 rel_types.push("PRO_REL_GOODS");
			 rel_codes.push("");
		 }
		 
		 goods_ids.push(contract_gid);
		 goods_ids.push(offer.offer_gid);
		 
		 product_ids.push(contract_pid);
		 product_ids.push(offer.offer_pid);
		 
		 rel_types.push("PRO_REL_GOODS");
		 rel_types.push("PRO_REL_GOODS");
		 
		 rel_codes.push("");
		 rel_codes.push("");
		 
		 data.goods_ids = goods_ids;
		 data.product_ids = product_ids;
		 data.rel_types = rel_types;
		 data.rel_codes = rel_codes;
		 
		 var flag = self.saveData(data);
		 
		 if(!flag){
			 flag_all = false;
		 }
	 }
	 
	 $.Loading.hide();
	 if(flag_all){
		 alert("保存成功！");
		 window.location.href= ctx + "/shop/admin/goods!goodsList.do";
	 }else{
		 alert("保存失败！");
	 }
	 
 },
 //保存合约接数据
 saveData:function(data){
		var flag = true;
		$.ajax({
			async:false,
			type : "POST",
			dataType : 'json',
			data :data,
			url : "goods!saveAdd.do?ajax=yes",
			success : function(result) {
				if(result){
			    	if(result.result==1){
			    		flag = true;
			    	}else if(result.result==0){
			    		alert(result.message);
			    		flag = false;
			    	}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				//alert("出错了");
			}
		}); 
		return flag;
 },

 //批量修改合约机
 editContractMachine:function(){
	 var self = this;
	 if(!self.checkPreSave("edit")){
		 return;
	 }
	 $.Loading.show("正在批量修改合约机，请耐心等待...");
	 //终端
	 var terminal_pid = "";
	 var type_id = "";
	 var isAccNbr = $("#isAccNbr").is(":checked");
	 var cat_id = $("#goods_cat_tree").val();
	 if(!isAccNbr){//如果是合约机，则取终端
		 type_id = "20002"; 
		 terminal_pid = $("#terminalTables").find("input[name='goodsId']").attr("pid");
	 }else{//如果是号卡
		 type_id = "20000";
	 }
     var goods_price = $("#goods_price").val();
     
     //合约
	 var contract_pid = $("#contractTables").find("input[name='goodsId']").attr("pid");
	 
	 //套餐
	 var offer_array = [];
	 var offer_pids = [];
	 $("#offerTables").find("input[name='goodsId']").each(function(idx3,offer){
		 var offer1 = {};
		 offer1.offer_name = $(offer).attr("goods_name");
		 offer1.offer_gid =$(offer).attr("gid");
		 offer1.offer_pid =$(offer).attr("pid");
		 offer1.month_fee = $(offer).attr("month_fee");
		 offer1.rel_code = $(offer).attr("rel_code");
    	 offer_array.push(offer1);
    	 offer_pids.push(offer1.offer_pid);
	 });
	 
	 //查出符合条件的合约机或号卡
	 var queryGoodsData = {"type_id":type_id,"terminal_pid":terminal_pid,"contract_pid": contract_pid, "offerPids":offer_pids.join(",")};
	  $.ajax({
			type : "POST",
			dataType : 'json',
			data :queryGoodsData,
			url : "goods!getContractmachine.do?ajax=yes",
			success : function(result) {
				var totalCount = 0;
				var successCount = 0;
				var failureCount = 0;
				if(result && result.goodsList){
		            var goodsList = result.goodsList;
		            totalCount = goodsList.length;
		            for(var i=0;i<goodsList.length;i++){
		         	    var goods_id = goodsList[i].goods_id;
		         	    var stype_id = goodsList[i].stype_id;
			       		var data = {"goods_id":goods_id,"type_id":type_id,"cat_id":cat_id,"price":goods_price};
				   		if(stype_id){
							 var params = self.getConfigParams(stype_id,isAccNbr);
							 data.paramnums = params.paramnums ; 
							 data.groupnames = params.groupnames ;
							 data.paramnames= params.paramnames  ;  
							 data.paramvalues = params.paramvalues  ;
							 data.ename = params.ename ;     
							 data.attrcode = params.attrcode ;   
							 data.attrvaltype= params.attrvaltype ;
							 data.attrtype = params.attrtype ;
							 
						 }
				   		
				   		//将商品对应的货品重新传入到后台
						 var goods_ids = [];
						 var product_ids = [];
						 var rel_types = [];
						 var rel_codes = [];
				   		 var goods_rels = goodsList[i].goods_rels;
				   		 for(var j=0;j<goods_rels.length;j++){
				   			 var goods_rel = goods_rels[j]
							 goods_ids.push(goods_rel.z_goods_id);
							 product_ids.push(goods_rel.product_id);
							 rel_types.push(goods_rel.rel_type);
							 rel_codes.push(goods_rel.rel_code);
				   		 }
						 data.goods_ids = goods_ids;
						 data.product_ids = product_ids;
						 data.rel_types = rel_types;
						 data.rel_codes = rel_codes;
						 
						 var flag = self.editData(data);
						 if(flag){
							successCount++;
								
						 }else{
							failureCount++; 
						 }
		            }
				}
				$.Loading.hide();
				alert("操作成功！共查询到"+totalCount+"条记录,修改成功数："+successCount+"条，修改失败数："+failureCount+"条");
				if(totalCount>0){
					window.location.href= ctx + "/shop/admin/goods!goodsList.do";					
				}
			},
			error : function(XMLHttpRequest, textStatus){
				$.Loading.hide();
				alert("出错了");
			}
	  }); 
 },
 //修改合约机数据
 editData:function(data){
	 var flag = true;
		$.ajax({
			async:false,
			type : "POST",
			dataType : 'json',
			data :data,
			url : "goods!editContract.do?ajax=yes",
			success : function(result) {
				if(result && result.result == 1){
					flag = true;
				}else{
					flag = false;
				}
			},
			error : function(XMLHttpRequest, textStatus){
				
			}
		}); 
	 return flag;
		
 }
 
}
$(function(){
	contractConfig.init();
});

function onlyNum(obj) {
    $(obj).keypress(function (event) {
        var eventObj = event || e;
        var keyCode = eventObj.keyCode || eventObj.which;
        if ((keyCode >= 48 && keyCode <= 57) || keyCode==8)
            return true;
        else
            return false;
    }).bind("paste", function () {
    //获取剪切板的内容
        var clipboard = window.clipboardData.getData("Text");
        if (/^\d+$/.test(clipboard))
            return true;
        else
            return false;
    });
};
   