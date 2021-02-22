var refreshCache = {
	init:function(){
		var self = this;
		$('#rfsConfig').click(function(){
			self.rfsConifg();
		});
		$('#rfsAllConfig').click(function(){
			//alert(1);
			self.rfsAllConfig();
		});
		$('#rfsSingle').click(function(){
			self.rfsSingle();
		});
		$('#rfsPublic').click(function(){
			self.rfsPublic();
		});
		$('#rfsGoods').click(function(){
			self.rfsGoods();
		});
		$('#rfsOrderTreeAttr').click(function(){
			self.rfsOrderTreeAttr();
		});
		$('#rfsOrderRule').click(function(){
			self.rfsOrderRule();
		});
		$('#rfsClearRuleScript').click(function(){
			self.rfsClearRuleScript();
		});
		$('#rfsLimitData').click(function(){
			self.rfsLimitData();
		});
		$('#refreshRemoteService').click(function(){
			self.refreshRemoteService();
		});
		$('#rfsMallConfig').click(function(){
			self.rfsMallConfig();
		});
		$('#rfsDStoreConfig').click(function(){
			self.rfsDStoreConfig();
		});
		$("#rfsNewGoods").click(function(){
			self.rfsNewGoods();
		});
		$("#rfsGoodsConfig").click(function(){
			self.rfsGoodsConfig();
		});
		$("#cachePlanRuleCond").click(function(){
			self.cachePlanRuleCond();
		});
		$("#cacheFreedomGruopDependElement").click(function(){
			self.cacheFreedomGruopDependElement();
		});
		$("#refreshInfCommClientInfo").click(function(){
			self.refreshInfCommClientInfo();
		});
		$("#refreshGrayConfig").click(function(){
			self.refreshGrayConfig();
		});
		$("#refreshSpecDefValues").click(function(){
			self.refreshSpecDefValues();
		});
		$("#rfsCrawlerProperties").click(function(){
			self.rfsCrawlerProperties();
		});
	},
	rfsConifg:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$("#rfsResult").load('cache!refreshCache.do?ajax=yes&refreshType=refreshCache',{},function(){
			$.Loading.hide();
		});
	},
	rfsAllConfig:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$("#rfsResult").load('cache!refreshList.do?ajax=yes&refreshType=refreshCache',{},function(){
			$.Loading.hide();
		});
	},
	rfsSingle:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$("#rfsResult").load('cache!refreshList.do?ajax=yes&refreshType=refreshSigLoginCache',{},function(){
			$.Loading.hide();
		});
	},
	rfsPublic:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$("#rfsResult").load('cache!refreshList.do?ajax=yes&refreshType=refreshDcPublicCache',{},function(){
			$.Loading.hide();
		});
	},
	rfsGoods:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "goods!refreshCache.do?ajax=yes&type=all",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	rfsNewGoods:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "goods!refreshCache.do?ajax=yes&type=new",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	rfsGoodsConfig:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "goods!refreshCache.do?ajax=yes&type=cfg",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	rfsOrderTreeAttr:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshTreeAttrAction!refreshOrderTreeAttr.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	rfsOrderRule:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshCacheAction!rfsOrderRule.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	rfsClearRuleScript:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshCacheAction!rfsClearRuleScript.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	rfsLimitData:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshCacheAction!rfsLimitData.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	}
	,
	rfsMallConfig:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshCacheAction!rfsMallConfig.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	refreshRemoteService:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshCacheAction!refreshRemoteService.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	rfsDStoreConfig:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshCacheAction!rfsDStoreConfig.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	cachePlanRuleCond:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "orderRefreshCacheAction!cachePlanRuleCond.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	cacheFreedomGruopDependElement:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$("#rfsResult").load('cache!refreshList.do?ajax=yes&refreshType=cacheFreedomGruopDependElement',{},function(){
			$.Loading.hide();
		});
	},
	refreshInfCommClientInfo:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "ordInf!refreshInfCommClientInfo.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	refreshGrayConfig:function(){
		$.Loading.show('正在刷新，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "grayDataSyncAction!refreshGrayConfig.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	},
	refreshSpecDefValues:function(){
		$.Loading.show('正在响应您的请求，请稍侯...');
		$("#rfsResult").load('cache!refreshList.do?ajax=yes&refreshType=refreshSpecDefineAndSpecValues',{},function(){
			$.Loading.hide();
		});
	},
	rfsCrawlerProperties:function(){
		$.Loading.show('正在刷新，请稍侯...');
		$.ajax({
			type : "post",
			async : true,
			url : "ordAuto!updateProperties.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				$.Loading.hide();
				alert(data.msg);
			},
			error:function(){
				$.Loading.hide();
			}
		});
	}
}
$(function(){
	refreshCache.init();
});