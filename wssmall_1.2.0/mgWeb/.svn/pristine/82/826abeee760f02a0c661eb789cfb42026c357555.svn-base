$(function(){
	
	$("ul[class='tab']").find("li").each(function(){
		$(this).bind("click",function(){
			$(this).addClass("active").siblings("li").removeClass();
			var tabid = $(this).attr("tabid");
			$("div[tabid='"+tabid+"']").show().siblings("div[class='input']").hide();
		});
	});
	
	
	var isEdit = $("#isEdit").val();
	
	if(isEdit=='1'){
		addAuthAction.bindDlgEvent();
	}else{
		addAuthAction.init();
	}
});

var addAuthAction = {
		auth_data : {},		
		init : function() {
			
			var self = this;
			self.newAction();
			//self.bindDlgEvent();
			
		},
		newAction : function() {
			
			var self = this;
			self.createTree();
			$("ul.checktree").checkTree();
				$("#authBtn").click(function() {
				self.saveAuth();
			});
			self.dataAuthDlg();	
				
		},
		/**
		 * 加载数据权限静态页面
		 * 
		 */
		dataAuthDlg : function() {
			var self = this;
			var loadurl = "/core/admin/orgRoleAction!roleAuthList.do";
			$("#databox").load(loadurl,function(){
				if($("#dataJSONObj").val() != ""){
					auth_data = eval("(" + $("#dataJSONObj").val() + ")");
					self.loadAuthData(auth_data);
				}
			});
		},
		/**
		 * 创建菜单树
		 */
		createTree : function() {
			var self = this;
			$.each(menu.app, function(k, v) {
				var li = self.createNode(v);
				var ul = self.createChildren(v.children);
				li.append(ul);
				$("#menubox>.menuTree").append(li);
			});
			$.each(app.all, function(k, v) {
				var li = self.createAppNode(v);
				var ul = self.createAppChildren(v.children);
				li.append(ul);
				$("#menubox>.appTree").append(li);
			});
			$.each(btn.all, function(k, v) {
				var li = self.createBtnNode(v);
				var ul = self.createBtnChildren(v.children);
				li.append(ul);
				$("#menubox>.btnTree").append(li);
			});
		},
		/**
		 * 根据子创建ul结点
		 */
		createChildren : function(menuAr) {
			var ul = $("<ul></ul>");
			var self = this;
			$.each(menuAr, function(k, v) {
						var li = self.createNode(v);

						// 如果有子则递归
						var children = v.children;
						if (children && children.length > 0) {
							li.append(self.createChildren(children));
						}
						ul.append(li);
					});
			return ul;
		},
		createAppChildren : function(menuAr) {
			var ul = $("<ul></ul>");
			var self = this;
			$.each(menuAr, function(k, v) {
						var li = self.createAppNode(v);

						// 如果有子则递归
						var children = v.children;
						if (children && children.length > 0) {
							li.append(self.createAppChildren(children));
						}
						ul.append(li);
					});
			return ul;
		},
		createBtnChildren : function(menuAr) {
			var ul = $("<ul></ul>");
			var self = this;
			$.each(menuAr, function(k, v) {
						var li = self.createBtnNode(v);

						// 如果有子则递归
						var children = v.children;
						if (children && children.length > 0) {
							li.append(self.createBtnChildren(children));
						}
						ul.append(li);
					});
			return ul;
		},
		/**
		 * 根据menu json创建菜单节点
		 */
		createNode : function(v) {
			var li = $("<li><input type=\"checkbox\" name=\"menuids\" value=\""
					+ v.id + "\"><label>" + v.text + "</label></li>");
			return li;
		},
		createAppNode : function(v) {
			var li = $("<li><input type=\"checkbox\" name=\"appIds\" value=\""
					+ v.id + "\"><label>" + v.text + "</label></li>");
			return li;
		},
		createBtnNode : function(v) {
			var li = $("<li><input type=\"checkbox\" name=\"btnIds\" value=\""
					+ v.id + "\"><label>" + v.text + "</label></li>");
			return li;
		},
		/**
		 * 保存权限
		 */
		saveAuth : function() {
			var self = this;
			var authname = $.trim($("#authname").val());
			if (authname == '') {
				alert("请输入权限名称");
				return;
			}
			$("#dataJSONObj").val(self.getRoleData());
			$.Loading.show('正在保存，请稍侯...');
			var options = {
				url : "auth!save.do?ajax=yes",
				type : "POST",
				dataType : 'json',
				success : function(result) {
					if (result.result == 1) {
						$.Loading.hide();
						var isEdit = $("#isEdit").val();
						if (isEdit == 1) {// 修改更换名称
							window.location.href =ctx +"/core/admin/auth!authList.do";
						} else {// 添加加入一行
							window.location.href =ctx +"/core/admin/auth!authList.do";
						}
						
					} else {
						$.Loading.hide();
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出错啦:(");
				}
			};
			$("#authForm").ajaxSubmit(options);
		},
		/**
		 * 加载数据权限
		 */
		getRoleData : function(){
			var id=$("#dataObjvalue").val(),role_code='',flow_node='',order_region='',order_origin='',sp_prod_type='',lock_status='',carry_mode='',normal_flag='',bespoke_flag='',society_flag='',son_order_type='',deal_org_type='',create_time='',create_user='',create_ip='',update_time='',update_user='',update_ip='',product_sub_type='',pay_mode='',handle_type='',product_busi_type='',develop_attribute='',product_brand='',source_from='',order_model='',ord_receive_user='',ord_lock_user='',busicty_id='';
			$("input[name='_selectNodeItem']").each(function(){
				if($(this).attr("checked") == true){
					flow_node += $(this).val() + ",";
			    }
			});//授权环节
			$("input[name='_selectRegionItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 order_region += $(this).val() + ",";
				 }
			});//授权地市
			/*****add by mo.chencheng 2015-12-10***********/
			var orderexp_catalog='0000,';
			$("input[name='_selectOrderexpSpecCatalogItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 orderexp_catalog += $(this).val() + ",";
				 }
			});//异常单归类
			/*****add by mo.chencheng 2015-12-10***********/
			$("input[name='_selectOriginItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 order_origin += $(this).val() + ",";
				 }
			});//授权来源
			$("input[name='_selectSpecialprodTypeItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 sp_prod_type += $(this).val() + ",";
				 }
			});//授权特殊商品类型
			$("input[name='_selectLockStatusItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 lock_status += $(this).val() + ",";
				 }
			});//授权锁定状态
			$("input[name='_selectCarryModeItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 carry_mode += $(this).val() + ",";
				 }
			});//授权配送方式
			$("input[name='_selectNormalFlagItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 normal_flag += $(this).val() + ",";
				 }
			});//授权配送方式
			$("input[name='_selectBespokeFlagItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 bespoke_flag += $(this).val() + ",";
				 }
			});//授权预约单
			$("input[name='_selectSocietyFlagItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 society_flag += $(this).val() + ",";
				 }
			});//授权社会机
			$("input[name='_selectProductSubTypeItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 product_sub_type += $(this).val() + ",";
				 }
			});//授权产品类型
			$("input[name='_selectPayModeItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 pay_mode += $(this).val() + ",";
				 }
			});//授权支付类型
			$("input[name='_selectHandleTypeItem']").each(function(){
			     if($(this).attr("checked") == true){
			    	 handle_type += $(this).val() + ",";
				 }
			});//授权处理类型
			$("input[name='_selectProductBusiTypeItem']").each(function(){
				 if($(this).attr("checked") == true){
					 product_busi_type += $(this).val() + ",";
			     }
			});//授权合约类型
			$("input[name='_selectDevelopAttributeItem']").each(function(){
				 if($(this).attr("checked") == true){
					 develop_attribute += $(this).val() + ",";
			     }
			});//授权归属渠道
			$("input[name='_selectProductBrandItem']").each(function(){
				 if($(this).attr("checked") == true){
					 product_brand += $(this).val() + ",";
			     }
			});//授权商品品牌
			$("input[name='_selectOrderModel']").each(function(){
				 if($(this).attr("checked") == true){
					 order_model += $(this).val() + ",";
			     }
			});//生产模式
			$("input[name='_selectOrderReceiveUser']").each(function(){
				if($(this).attr("checked") == true){
					ord_receive_user += $(this).val() + ",";
				}
			});//领取工号
			$("input[name='_selectOrderLockUser']").each(function(){
				if($(this).attr("checked") == true){
					ord_lock_user += $(this).val() + ",";
				}
			});//锁定工号
			$("input[name='_selectOrderBusicty']").each(function(){
				if($(this).attr("checked") == true){
					busicty_id += $(this).val() + ",";
				}
			});//授权区县
			return '{\"role_code\"'+':\"'+role_code+'\",'+
			'\"id\"'+':\"'+id+'\",'+
			'\"flow_node\"'+':\"'+flow_node+'\",'+
			'\"order_region\"'+':\"'+order_region+'\",'+
			'\"order_origin\"'+':\"'+order_origin+'\",'+
			'\"lock_status\"'+':\"'+lock_status+'\",'+
			'\"carry_mode\"'+':\"'+carry_mode+'\",'+
			'\"normal_flag\"'+':\"'+normal_flag+'\",'+
			'\"bespoke_flag\"'+':\"'+bespoke_flag+'\",'+
			'\"society_flag\"'+':\"'+society_flag+'\",'+
			'\"son_order_type\"'+':\"'+son_order_type+'\",'+
			'\"deal_org_type\"'+':\"'+deal_org_type+'\",'+
			'\"create_time\"'+':\"'+create_time+'\",'+
			'\"create_user\"'+':\"'+create_user+'\",'+
			'\"create_ip\"'+':\"'+create_ip+'\",'+
			'\"update_time\"'+':\"'+update_time+'\",'+
			'\"update_user\"'+':\"'+update_user+'\",'+
			'\"update_ip\"'+':\"'+update_ip+'\",'+
			'\"product_sub_type\"'+':\"'+product_sub_type+'\",'+
			'\"pay_mode\"'+':\"'+pay_mode+'\",'+
			'\"handle_type\"'+':\"'+handle_type+'\",'+
			'\"product_busi_type\"'+':\"'+product_busi_type+'\",'+
			'\"develop_attribute\"'+':\"'+develop_attribute+'\",'+
			'\"product_brand\"'+':\"'+product_brand+'\",'+
			'\"order_model\"'+':\"'+order_model+'\",'+
			'\"ord_receive_user\"'+':\"'+ord_receive_user+'\",'+
			'\"ord_lock_user\"'+':\"'+ord_lock_user+'\",'+
			'\"orderexp_catalog\"'+':\"'+orderexp_catalog+'\",'+
			'\"busicty_id\"'+':\"'+busicty_id+'\"'+
			'}';
		},
		/**
		 * 绑定对话框打开后各种事件
		 */
		bindDlgEvent : function(type) {
			var self = this;
			self.createTree();
			var menuObjvalue = $("#menuObjvalue").val();
			var appObjvalue = $("#appObjvalue").val();
			var btnObjvalue = $("#btnObjvalue").val();
			var dataObjvalue = $("#dataObjvalue").val();
			if (menuObjvalue) {
				var values = menuObjvalue.split(",");
				for (var i = 0; i < values.length; i++) {
					var v = values[i];
					$("div[tabid='menu']").find("ul.checktree input[value='" + v + "']")
							.attr("checked", true);
				}
			}
			if(appObjvalue){
				var appValues = appObjvalue.split(",");
				for (var i = 0; i < appValues.length; i++) {
					var v = appValues[i];
					$("div[tabid='app']").find("ul.checktree input[value='" + v + "']")
							.attr("checked", true);
				}
			}
			if(btnObjvalue){
				var btnValues = btnObjvalue.split(",");
				for (var i = 0; i < btnValues.length; i++) {
					var v = btnValues[i];
					$("div[tabid='btn']").find("ul.checktree input[value='" + v + "']")
							.attr("checked", true);
				}
			}
			if (dataObjvalue) {
				var loadurl = ctx+"/core/admin/orgRoleAction!roleAuthList.do?actid="+dataObjvalue;
				$("#databox").load(loadurl,function(){
					if($("#dataJSONObj").val() != ""){
						auth_data = eval("(" + $("#dataJSONObj").val() + ")");
						self.loadAuthData(auth_data);
					}
				});
			}
			$("ul.checktree").checkTree();
			$("#authBtn").click(function() {
						self.saveAuth();
					});
			if(type =="view"){
				$("#authname").closest("tr").hide();
				$(".checktree").find(".checkbox").hide();
			}
		},
		/**
		 * 加载数据权限
		 */
		loadAuthData : function(authdata){
			if(auth_data.flow_node){
				$("input[name='_selectNodeItem']").each(function(){
					var v = $(this).val();
					var flowNodeAuth = ","+auth_data.flow_node;
					if(flowNodeAuth.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//授权环节
			}
			if(auth_data.order_region){
				$("input[name='_selectRegionItem']").each(function(){
				     var v = $(this).val();
				     var orderRegionAuth = ","+auth_data.order_region;
				     if(orderRegionAuth.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权地市
			}
			if(auth_data.order_origin){
				$("input[name='_selectOriginItem']").each(function(){
					var v = $(this).val();
					var order_origin = ","+auth_data.order_origin;
					if(order_origin.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//授权来源
			}
			if(auth_data.sp_prod_type){
				$("input[name='_selectSpecialprodTypeItem']").each(function(){
				     var v = $(this).val();
				     var sp_prod_type = ","+auth_data.sp_prod_type;
				     if(sp_prod_type.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权特殊商品类型
			}
			if(auth_data.lock_status){
				$("input[name='_selectLockStatusItem']").each(function(){
				     var v = $(this).val();
				     var lock_status = ","+auth_data.lock_status;
				     if(lock_status.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权锁定状态
			}
			
			if(auth_data.carry_mode){
				$("input[name='_selectCarryModeItem']").each(function(){
				     var v = $(this).val();
				     var carry_mode = ","+auth_data.carry_mode;
				     if(carry_mode.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权配送方式
			}
			if(auth_data.normal_flag){
				$("input[name='_selectNormalFlagItem']").each(function(){
				     var v = $(this).val();
				     var normal_flag = ","+auth_data.normal_flag;
				     if(normal_flag.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权配送方式
			}
			if(auth_data.bespoke_flag){
				$("input[name='_selectBespokeFlagItem']").each(function(){
				     var v = $(this).val();
				     var bespoke_flag = ","+auth_data.bespoke_flag;
				     if(bespoke_flag.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权预约单
			}
			if(auth_data.society_flag){
				$("input[name='_selectSocietyFlagItem']").each(function(){
				     var v = $(this).val();
				     var society_flag = ","+auth_data.society_flag;
				     if(society_flag.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权社会机
			}
			if(auth_data.product_sub_type){
				$("input[name='_selectProductSubTypeItem']").each(function(){
				     var v = $(this).val();
				     var product_sub_type = ","+auth_data.product_sub_type;
				     if(product_sub_type.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权产品类型
			}
			if(auth_data.pay_mode){
				$("input[name='_selectPayModeItem']").each(function(){
				     var v = $(this).val();
				     var pay_mode = ","+auth_data.pay_mode;
				     if(pay_mode.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权支付类型
			}
			if(auth_data.handle_type){
				$("input[name='_selectHandleTypeItem']").each(function(){
				     var v = $(this).val();
				     var handle_type = ","+auth_data.handle_type;
				     if(handle_type.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权处理类型
			}
			if(auth_data.product_busi_type){
				$("input[name='_selectProductBusiTypeItem']").each(function(){
				     var v = $(this).val();
				     var product_busi_type = ","+auth_data.product_busi_type;
				     if(product_busi_type.indexOf(","+v+",")>-1){
				    	 $(this).attr("checked",'true');
				     }
				});//授权合约类型
			}
			if(auth_data.product_busi_type){
				$("input[name='_selectDevelopAttributeItem']").each(function(){
					var v = $(this).val();
					var develop_attribute = ","+auth_data.develop_attribute;
					if(develop_attribute.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//授权归属渠道
			}
			if(auth_data.product_brand){
				$("input[name='_selectProductBrandItem']").each(function(){
					var v = $(this).val();
					var product_brand = ","+auth_data.product_brand;
					if(product_brand.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//授权商品品牌
			}
			if(auth_data.order_model){
				$("input[name='_selectOrderModel']").each(function(){
					var v = $(this).val();
					var order_model = ","+auth_data.order_model;
					if(order_model.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//授权生产模式
			}
			
			if(auth_data.orderexp_catalog){
				$("input[name='_selectOrderexpSpecCatalogItem']").each(function(){
					var v = $(this).val();
					var orderexp_catalog = ","+auth_data.orderexp_catalog;
					if(orderexp_catalog.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//异常单归类
			}
			if(auth_data.ord_receive_user){
				$("input[name='_selectOrderReceiveUser']").each(function(){
					var v = $(this).val();
					var reUserAuth = ","+auth_data.ord_receive_user;
					if(reUserAuth.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//异常单归类
			}
			if(auth_data.ord_lock_user){
				$("input[name='_selectOrderLockUser']").each(function(){
					var v = $(this).val();
					var lockUserAuth = ","+auth_data.ord_lock_user;
					if(lockUserAuth.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//异常单归类
			}
			if(auth_data.busicty_id){
				$("input[name='_selectOrderBusicty']").each(function(){
					var v = $(this).val();
					var busictyAuth = ","+auth_data.busicty_id;
					if(busictyAuth.indexOf(","+v+",")>-1){
						$(this).attr("checked",'true');
					}
				});//授权区县
			}
		}
		
	};