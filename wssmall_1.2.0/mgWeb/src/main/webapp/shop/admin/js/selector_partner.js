
/**
 分销商选择器
*/
var PartnerSelector={
	wrapper_id:'refPartnerDlg', //弹出页面id
	form_id:'serchform',//弹出页面表单id
	params:{},
	init:function()	{
		var self  = this;
		Eop.Dialog.init({id:"refPartnerDlg",modal:false,title:"关联分销商",width:'500px'});		
		
		$("#refPartnerBtn").click(function(){//关联按钮
			  self.open();
		});
		},
		open:function(){//弹出页面
		  	var self= this;
			var url=app_path+'/shop/admin/partner!showPartnerList.do?ajax=yes';
			Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
	        Eop.Dialog.open(this.wrapper_id);
	    },
		search:function(params){//点击搜索按钮搜索
		    jQuery.extend(params,this.params);
		    var url=app_path+'/shop/admin/partner!showPartnerList.do?ajax=yes';
			Cmp.ajaxSubmit(this.form_id,this.wrapper_id,url,this.params,this.onAjaxCallBack);
		},
		page_init:function(){  //回调
		   var self=this;
		   //alert(1);
		   $("#searchBtn").click(function(){//searchBtn 搜索
			  self.search();
		   });
		   $("#"+this.form_id+" .submitBtn").click(function(){
		   
			if($('input[name="partner_id"]:checked').length >0)
			{	
				//赋值
				var value = $('input[name="partner_id"]:checked').val();
				alert(value);
			}
			Eop.Dialog.close(this.wrapper_id);
		});
		 //alert(3);
	},
	onAjaxCallBack:function(){//ajax回调函数
	   PartnerSelector.page_init();
	}
	
};

$(function(){
	PartnerSelector.init();
})


