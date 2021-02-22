var goodsUser = {
  group_type : undefined,
  init:function(){
     var self = this;
     self.bindGroupBtn();
     self.saveGoodsUser();
     self.bindRadio();
     self.bindRuleBtn();
     $(".delLinkRule").live("click",function(){
		$(this).closest("tr").remove();
     });
  },
  bindRuleBtn:function(){
	  $(".rule_btn").each(function(){
		  var name = $(this).attr("name");
		  $(this).bind("click",function(){
			  var radio = $("input[name='goodsUser.service_code']:checked");
			  var service_code = "";
			  if(radio.length > 0){
				  service_code = radio.val();
			  }
			  var url = ctx+"/shop/admin/goodsUserAction!listRule.do?ajax=yes&rule.rule_type="
			  					+name+"&rule.service_code="+service_code;
			  $("#"+name+"_rule_dialog").load(url,function(){
            	  goodsUser.listRuleInit(name);
			  });
			  Eop.Dialog.open(name+"_rule_dialog");
		  });
	  });
      
  },
  listRuleInit:function(name){
	  function ruleListSpec(){
		this.list = [];
	  }
	  function ruleSpec(){
		this.rule_id = "";
		this.rule_name = "";
		this.rule_code = "";
		this.rule_type = "";
	  }
	  
	  var ruleList = new ruleListSpec();
	  var div = "div[id='"+name+"_rule_dialog']";
	  $(div+" a[id='submitButton']").unbind("click").bind("click",function(){
		  var div_name = name + "_rule_dialog";
		  var url = ctx + "/shop/admin/goodsUserAction!listRule.do?ajax=yes";
		  Cmp.ajaxSubmit('rule_list_form',div_name,url,{},goodsUser.listRuleInit);
		  return false;
	  });
	  $(div+" a[id='sltRule']").unbind("click").bind("click",function(){
			$(div+" input[name='select_rule']:checked").each(function(){
				var rule = new ruleSpec();
				rule.rule_id = $(this).attr("rule_id");
				rule.rule_name = $(this).attr("rule_name");
				rule.rule_code = $(this).attr("rule_code");
				rule.rule_type = $(this).attr("rule_type");
				ruleList.list.push(rule);
			});
			
			
			//取数据绘制页面
			var html = "";
			var list = ruleList.list;
			for(var i=0;i<list.length;i++){
				var rule = list[i];
				var id = rule.rule_id;
				var flag = true;
				
				$(".hidden_rule_id").each(function(){
					var h_id = $(this).val();
					if(id == h_id){
						flag = false;
					}
				});
				
				if(flag){
					html += "<tr>";
					html += "<td>"+rule.rule_name+"</td>";
					if(rule.rule_type == "accept"){
						html += "<td>受理类规则</td>";
					}
					if(rule.rule_type == "delvery"){
						html += "<td>发货类规则</td>";
					}
					if(rule.rule_type == "pay"){
						html += "<td>支付类规则</td>";
					}
					if(rule.rule_type == "insure"){
						html += "<td>确认类规则</td>";
					}
					html += "<td>"+rule.rule_code+"</td>";
					html += "<td><a href='javascript:void(0);' class='delLinkRule'>删除 </a></td>";
					html += "<input type='hidden' name='rule_ids' class='hidden_rule_id' value='"+rule.rule_id+"'/>";
					html += "</tr>";
				}
			}
			$("#"+name+"_rule_th").after(html);
			Eop.Dialog.close(name+"_rule_dialog");
		});
  },
  saveGoodsUser:function(){
    $("#saveGoodsUser").click(function(){
         goodsUser.setGroupAndUserId();
         var url =app_path + "/shop/admin/goodsUserAction!saveEditGoodsUser.do?ajax=yes";
         Cmp.ajaxSubmit('goodsUserForm', '', url, {}, goodsUser.addJsonBack, 'json');
    });
  },
  setGroupAndUserId:function(){
      
      $("[groupValue='yes']:checked").each(function(){
          var group_id = $(this).val();
          var id = $(this).attr("id");
          $("[name='"+id+"']").val(group_id);
      });
      $("[groupValue='no']:checked").each(function(){
          var user_id = $(this).val();
          var id = $(this).attr("id");
          $("[name='"+id+"']").val(user_id);
      });
  },
  addJsonBack:function(reply){
   
    if(reply.result =='0'){
      alert("操作成功!");
      window.location.href = ctx+"/shop/admin/goodsUserAction!goodsUserList.do";
      //window.location.reload();
    }
    if(reply.result=='1'){
       alert(reply.message);
    }
  },
 
  appendGoods:function(){
     var htmlStr =""; 
      $("[name='goodsCheckBox']:checked").each(function(){
            var goods_id = $(this).attr("goods_id");
            var goods_name= $(this).attr("goods_name");
            var sn = $(this).attr("sn");
          
            var count = 0;
            $("[name='goodsUser.goodsIds']").each(function(){
                var goodsid = $(this).val();
                if(goods_id==goodsid){
                   count = count+1;
                }
               
            });
            
            if(count==0){
                  htmlStr += goodsUser.goodsStr(goods_id,goods_name,sn);
              }
	      
	     });
	  
	  $("#goodsTable").append(htmlStr);
	  var htmlJq = $(htmlStr);
	  
	  $("[name='delGoodsUser']").each(function(){
	     $(this).unbind("click").bind("click",function(){
	         $(this).closest("tr").remove();
	     });
	  });
	
  },
  goodsStr:function(goods_id,goods_name,sn){
     var goodsStr = "<tr >"+
                      "<input type='hidden' name='goodsUser.goods_id'  value='"+goods_id+"'/>"+
                      "<td>"+goods_name+"</td>"+
                      "<td>"+sn+"</td>"+
                      "<td><a href='javascript:void(0)' class='p_prted' name='delGoodsUser'>删除</a></td>"+
                    "</tr>";
     return goodsStr;
  },
  
  bindGroupBtn:function(){
     
      $("[name='qrUserBtn']").each(function(){
        $(this).click(function(){
              var group_type=$(this).attr("group_type"); 
              goodsUser.group_type = group_type;
              var url = app_path+"/shop/admin/goodsUserAction!groupList.do?ajax=yes&group_type="+group_type;
              $("#showGroupDlg").load(url,function(){
                 goodsUser.bindQrGroupBtn(group_type);
                 goodsUser.groupSearch(group_type);
             });
            
              $(".dialog_box").attr("style","text-align:center");
              Eop.Dialog.open("showGroupDlg");
        });
      });
      
  },
  
  bindQrGroupBtn:function(group_type){
    $("#qrGroupBtn").click(function(){
                if($("[name='groupRadio']:checked").length==0){
			       alert("请选择订单分组");
			       return false;
			     }else{
                 goodsUser.appendGroup(group_type);
                 Eop.Dialog.close("showGroupDlg");
                 }
              });
  },
  appendGroup:function(group_type){
     var self = this;
     
     var group_id = $("[name='groupRadio']:checked").attr("group_id");
     var group_name = $("[name='groupRadio']:checked").attr("group_name");
    
     var group_type_name = group_type;
     if(group_type=='delvery'){
         group_type_name='ship';
     }
     var htmlStr ="<li><input type ='radio' name='"+group_type_name+"UserRadio' value='"+group_id+"' id='goodsUser."+group_type_name+"_group_id' groupValue ='yes' >"+group_name+"</li>";
     
     $("#"+group_type).empty().append(htmlStr);
   
  },
  appendUserJsonBack:function(reply){
       
       var group_type= goodsUser.group_type;
       var htmlStr ="";
       var userIdName = group_type+"UserIds";
      if(reply!=null&&reply!=""){
      /*  if(group_type=="delvery"){
         group_type ="ship";
         }
       var userIdName = "goodsUser."+group_type+"_user_id";
      if(group_type=="qr"){
         userIdName ="qr_user_id";
         }*/
     for(var i=0;i<reply.length;i++){
       /*    var count  = 0;
        $("#"+goodsUser.group_type).find("[type='checkbox']").each(function(){
           var userId = $(this).val();
           count = count + 1;
        });
        
        if(count ==0){
                
         
          } */
            var obj = reply[i];   
            htmlStr = htmlStr + "<li><input type='checkbox' name='"+userIdName+"'  value='"+obj.userid +"'>"+obj.username+"</li>";
       }
      }
      htmlStr = htmlStr + "<li><input type='checkbox' name='"+userIdName+"'   value='self'>self</li>";
      htmlStr = htmlStr + "<li><input type='checkbox' name='"+userIdName+"'   value='parent'>parent</li>";
      $("#"+goodsUser.group_type).empty().append(htmlStr);
  },
  groupSearch:function(group_type){
      $("#groupSearchBtn").click(function(){
         var self = this;
		 var options = {
		        url : app_path+'/shop/admin/goodsUserAction!groupList.do?ajax=yes&group_type'+group_type,
				type : "post",
				dataType : 'html',
				success : function(result) {				
					$("#showGroupDlg").empty().append(result);
					 goodsUser.bindQrBtn();
					 goodsUser.search(group_type);
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
 
		$("#groupSerchform").ajaxSubmit(options);
     });
  },
   bindRadio:function(){
    $("[goodsRule='yes']").each(function(){
        
         $(this).click(function(){
             var type = $(this).attr("id").split("_")[0];
             $("#"+type+"_table").show();
         });
         
    });
    $("[goodsRule='no']").each(function(){
       if($(this).attr("checked")){
           var type = $(this).attr("id").split("_")[0];
           $("#"+type+"_table").hide();
           $("#"+type+"GroupId").val("");
           $("#"+type).empty();
          // $("#"+type+" input[type='checkbox']").attr("checked","");
       }
       $(this).click(function(){
           var type = $(this).attr("id").split("_")[0];
           $("#"+type+"_table").hide();
           $("#"+type+"GroupId").val("");
           $("#"+type).empty();
           //$("#"+type+" input[type='checkbox']").attr("checked","");
       });
        
    });
  }
};

$(function(){
  
   Eop.Dialog.init({id:"showGroupDlg",modal:false,title:"添加分组员工",width:'800px'});
   Eop.Dialog.init({id:"insure_rule_dialog",modal:false,title:"确认规则选择",width:'800px'});
   Eop.Dialog.init({id:"accept_rule_dialog",modal:false,title:"受理规则选择",width:'800px'});
   Eop.Dialog.init({id:"pay_rule_dialog",modal:false,title:"支付规则选择",width:'800px'});
   Eop.Dialog.init({id:"delvery_rule_dialog",modal:false,title:"发货规则选择",width:'800px'});
   $(".dialog_box").attr("style","text-align:center");
   goodsUser.init();
});