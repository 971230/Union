
/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
//分销商选择器
var AgentSelector = {
	conid:undefined, 
	init:function (conid, onConfirm) {
		this.conid = conid;
		var self = this;
		$("#searchBtn").click(function () {//searchBtn 搜索
			self.search(onConfirm);
		});
		$("#" + conid + " .submitBtn").click(function () {
			if ($("input[name=\"agentid\"]:checked").length > 0) {	
					//赋值
				var value = $("input[name=\"agentid\"]:checked").val();
				var arr = value.split(",");
				$("#cml_user_id").val(arr[1]);
				$("#cml_user_name").val(arr[2]);
			}
			Eop.Dialog.close(conid);
		});
	}, 
	search:function (onConfirm) {
		var self = this;
		var options = {
			url:app_path + "/shop/admin/buyoutCloudReport!queryAgent.do?ajax=yes", 
			type:"post", 
			dataType:"html", 
			success:function (result) {
				$("#" + self.conid).empty().append(result);
				self.init(self.conid, onConfirm);
			},
			 error:function (e) {
				alert("查询出错:(");
			}
		};
		$("#query_agent_form").ajaxSubmit(options);
	}, 
	open:function (conid, onConfirm) {
		var self = this;
		var url = app_path + "/shop/admin/buyoutCloudReport!queryAgent.do?ajax=yes";
		$("#" + conid).load(url, function () {
			self.init(conid, onConfirm);
		});
		Eop.Dialog.open(conid);
	},
	goodsInit:function(){
		$("#goodsSearch").unbind("click").bind("click",function(){
			var url = ctx + "/shop/admin/commission!listGoods.do?ajax=yes";
			alert(url);
			Cmp.ajaxSubmit('goods_search_form','refGoodsDlg',url,{},AgentSelector.goodsInit);
		});
		
		
		$("#insureBtn").unbind("click").bind("click",function(){
			var goods = $("#commission_list_form [type='radio']:checked");
			if(goods.length == 0){
				alert("请先选择商品！");
				return;
			}
			var info = goods.attr("attr").split("_");
			$("#goods_id").val(info[0]);
			$("#goods_name").val(info[1]);
			Eop.Dialog.close("refGoodsDlg");
		});
	}
};
$(function () {
	$("#start_time").datepicker();
	$("#end_time").datepicker();
	$("#monthcycle").datepicker();
	
	Eop.Dialog.init({id:"refAgentDlg", modal:false, title:"选择员工", width:"500px"});
	Eop.Dialog.init({id:"refGoodsDlg", modal:false, title:"选择商品", width:"500px"});
	Eop.Dialog.init({id:"payApplyDlg", modal:false, title:"调价申请", width:"700px"});
	Eop.Dialog.init({id:"balanceApplyDlg", modal:false, title:"结算申请", width:"700px"});
	
	
	/**
	*	选取用户
	*/
	$("#refAgentBtn").click(function () {
		AgentSelector.open("refAgentDlg", null);
	});
	
	/**
	*	选取商品
	*/
	$("#refGoodsBtn").bind("click",function(){
		var url = app_path + "/shop/admin/commission!listGoods.do?ajax=yes";
		$("#refGoodsDlg").load(url, function () {
			AgentSelector.goodsInit();
		});
		Eop.Dialog.open("refGoodsDlg");
	});
	
	/**
	*	调价申请
	*/
	$(".detailEditApply").bind("click",function(){
		var info = $(this).attr("attr").split("_");
		var list_id = info[0];
		var goods_id = info[1];
		var apply_type ='00A';
		var url = ctx + "/shop/admin/commissionApply!addCommissionApply.do?ajax=yes";
		  $("#payApplyDlg").load(url,{list_id:list_id,apply_type:apply_type},function(){
            
          }); 
		
		Eop.Dialog.open("payApplyDlg");
		
		
		//alert("list_id:"+list_id+"|||"+"goods_id:"+goods_id);
	});
	
	
	/**
	*	结算申请
	*/
	$(".detailEndApply").bind("click",function(){
		var info = $(this).attr("attr").split("_");
		var list_id = info[0];
		var goods_id = info[1];
		var apply_type ='00B';
		var url = ctx + "/shop/admin/commissionApply!balanceApply.do?ajax=yes";
		  $("#balanceApplyDlg").load(url,{list_id:list_id,apply_type:apply_type},function(){
          }); 
		Eop.Dialog.open("balanceApplyDlg");
		//alert("list_id:"+list_id+"|||"+"goods_id:"+goods_id);
	});
});
/**
 * 查询所有
 */
function clean_agent() {
	$("#cml_user_id").val("");
	$("#cml_user_name").val("");
}

function clean_goods() {
	$("#goods_id").val("");
	$("#goods_name").val("");
}


$(function($) {
	$.datepicker.regional['zh-CN'] = {
		clearText : '清除',
		clearStatus : '清除已选日期',
		closeText : '关闭',
		closeStatus : '不改变当前选择',
		prevText : '&lt;上月',
		prevStatus : '显示上月',
		nextText : '下月&gt;',
		nextStatus : '显示下月',
		currentText : '今天',
		currentStatus : '显示本月',
		monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月',
				'十月', '十一月', '十二月' ],
		monthNamesShort : [ '一', '二', '三', '四', '五', '六', '七', '八', '九', '十',
				'十一', '十二' ],
		monthStatus : '选择月份',
		yearStatus : '选择年份',
		weekHeader : '周',
		weekStatus : '年内周次',
		dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
		dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
		dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
		dayStatus : '设置 DD 为一周起始',
		dateStatus : '选择 m月 d日, DD',
		dateFormat : 'yymm',
		firstDay : 1,
		initStatus : '请选择日期',
		isRTL : false
	};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
	$(".dateinput").datepicker({onSelect:function(){
		this.focus();
	}});
	
});


String.prototype.trim = function (){
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
};
