<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div id="btnlists">
<div class="comBtns" style="position: fixed;bottom: 0px;width: 100%;">
	<c:forEach items="${ordBtns }" var="btn">
		<a href="javascript:void(0);" orderbtns="btn" name="${btn.btn_ename }" ac_url="${btn.action_url }" show_type="${btn.show_type }" hide_page="${btn.hide_page }" order_id="${order_id }" form_id="${btn.form_id }" check_fn="${btn.check_fn }" callcack_fn="${btn.callback_fn }" class="dobtn" style="margin-right:5px;">${btn.btn_cname }</a>
	</c:forEach>
</div>
<div id="order_btn_event_dialog"></div>
<script type="text/javascript">
	$(function(){
		Eop.Dialog.init({id:"order_btn_event_dialog",modal:true,title:"订单处理", height:"600px",width:"800px"});
		$("a[orderbtns=btn]").bind("click",btnEvent);
	});
	
	function closeDialog(){
		Eop.Dialog.close("order_btn_event_dialog");
	}
	
	function btnEvent(){
		var $this = $(this);
		var order_id = $this.attr("order_id");		
		var order_sub = false;
		$(window.top.document).find(".main_all_iframe").each(function(){
				if($(this).is(':visible')){
							$($(this)).contents().find("#right_content").find(".auto_frame").each(function(){
										if($(this).is(':visible')){
												if("yes"==$(this).attr("order_sub")){
														order_sub=true ;
														return false;	
												}
										}
							})
						
				}
		})	
		
		var checkFn = $this.attr("check_fn");

		var form_id = $this.attr("form_id");
		if(checkFn){
			var flag = eval(checkFn)(order_id,form_id);
			if(!flag){
				//alert("数据验证没有通过，请检查必填的选项!");
				return ;
			}
		}
		var callcack_fn = $this.attr("callcack_fn");
		if(callcack_fn)callcack_fn=eval(callcack_fn);
		var ac_url = $this.attr("ac_url");
		/**
		 * execute 直接执行
		 * dialog 打开窗口
		 * open 打开浏览器原生窗口
		 * link 当前窗口打开连接
		 * submit 提交表单
		 * ajaxSubmit 提交表单
		 * his_back 返回
		 */
		var show_type = $this.attr("show_type");
		
		var dealDescIE8 = encodeURI(encodeURI($("#node_deal_message").val()));
		
		if(ac_url.indexOf("?")!=-1){
			ac_url = ac_url+"&order_id="+order_id+"&dealDescIE8="+dealDescIE8;
		}else{
			ac_url = ac_url+"?order_id="+order_id+"&dealDescIE8="+dealDescIE8;
		}
		
		if(ac_url.indexOf("http:") == -1) {
			ac_url = ctx+ "/" + ac_url;
		}
		
		if("ajaxSubmit"==show_type){
			$.Loading.show();
			ac_url += "&ajax=yes";
			//alert("0")
			var options = {
				type : "post",
				url : ac_url,
				dataType : "json",
				success : function(result) {
					$.Loading.hide();
					if(callcack_fn)callcack_fn(result);
				},
				error : function(e,b) {
					alert("处理失败，请重试!");
					$.Loading.hide();
				}
			}
			if(form_id){
				var form = $("#"+form_id);
				if(form && form.length>0){
					//alert($("#node_deal_message").val());
					form.ajaxSubmit(options);
				}else{
					$($("form")[0]).ajaxSubmit(options);
				}
			}else{
				$($("form")[0]).ajaxSubmit(options);
			}
		}else if("submit"==show_type){
			if(form_id){
				var form = $("#"+form_id);
				if(form && form.length>0){
					form.attr("action",ac_url).attr("method","post").submit();
				}else{
					$("form").attr("action",ac_url).attr("method","post").submit();
				}
			}else{
				$("form").attr("action",ac_url).attr("method","post").submit();
			}
		} else if("execute"==show_type){
			$.Loading.show();
			ac_url += "&ajax=yes";
			$.ajax({
				url:ac_url,
				type : "post",
				dataType:"json",
				success:function(data){
					$.Loading.hide();
					if(callcack_fn)callcack_fn(data);
				},error:function(a,b){
					$.Loading.hide();
				}
			});
		}else if("dialog"==show_type){
			ac_url += "&ajax=yes";
			Eop.Dialog.open("order_btn_event_dialog");
			var options = {
					type : "post",
					url : ac_url,
					dataType : "html",
					success : function(result) {
						$("#order_btn_event_dialog").empty().append(result);
						if(callcack_fn)callcack_fn(result);
					},
					error : function(e,b) {
						alert("处理失败，请重试!");
					}
				}
				if(form_id){
					var form = $("#"+form_id);
					if(form && form.length>0){
						form.ajaxSubmit(options);
					}else{
						var form = $("form");
						if(form && form.length>0){
							$(form[0]).ajaxSubmit(options);
						}else{
							$("#order_btn_event_dialog").load(ac_url,function(responseText){
								if(callcack_fn)callcack_fn(responseText);
							});
						}
					}
				}else{
					var form = $("form");
					if(form && form.length>0){
						$(form[0]).ajaxSubmit(options);
					}else{
						$("#order_btn_event_dialog").load(ac_url,function(responseText){
							if(callcack_fn)callcack_fn(responseText);
						});
					}
				}
		}else if("open"==show_type && !order_sub){
			window.open(ac_url,'_blank');
		}else if("link"==show_type && !order_sub){
			window.location.href=ac_url;
		}else if("his_back"==show_type && !order_sub){
			window.history.back(-1);
		}
		
		if(order_sub && "link"==show_type && "goBackCheck" != checkFn){
			var currframe;
			$(window.parent.document).find("iframe").each(function(){
                var oid = $(this).attr("order_id");
                if(order_id == oid){
                	currframe = $(this);
                }
            });
			currframe.attr("src",ac_url);
		}
		else if("ajaxSubmit"==show_type||"dialog"==show_type||"execute"==show_type){
			
		}else{
			var bottom_tab_ul = $(window.top.document).find("#bottom_tab_ul");
			$(window.top.document).find(".main_all_iframe").each(function(){
					if($(this).is(':visible')){
								$($(this)).contents().find("#right_content").find(".auto_frame").each(function(){
											if($(this).is(':visible')){
													if("yes"==$(this).attr("order_sub")){//详情页面
															var li_curr = bottom_tab_ul.find("li[order_detail='"+order_id+"']");				
															$(li_curr).prev().addClass("curr").show();		
															$(li_curr).prev().trigger("click");
															$(li_curr).removeClass("curr").hide().remove();														
															$(this).hide();
															$(this).prev().show();
															$(this).remove();
															return false;
													}
													
											}
											else if(!$(this).attr("order_sub")){//订单列表页面
												$(this).contents().find(".right_warp").find("#order_list_fm tbody tr input[name='orderidArray']").each(function(){
														if($(this).val() == order_id){
															$(this).attr("checked",false);
														}
													})
													
											}
								});
								
							
					}
			})			
		}
		
				
	}
</script>
</div>