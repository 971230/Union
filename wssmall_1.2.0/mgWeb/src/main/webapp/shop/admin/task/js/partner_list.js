var editChoiceList = {
     init : function() {
          var me = this;
          me.initClk();
     },
 	 initClk : function() {
          var me = this;
                
     },
     selectOkClk : function() {
     	
     },
     /**
      * 搜索按钮事件加载出自己查询出来的数据
      * */
     searchBottonClk : function() {
          
          var me = this;
          var task_id = $("input[name='task_id2']").val();
          var username = $("input[name='username']").val();
          var realname = $("input[name='realname']").val();
          var options = {
                    url :'task!getPartner.do?ajax=yes',
                    type : "POST",
                    data:{task_id:task_id, username:username, realname:realname},
                    dataType : 'html',            
                    success : function(result) {  
                         $("#refPartnerDlg").empty().append(result);
                         me.init();
                    },
                    error : function(e) {
                         alert("出错啦:(");
                    }
          };
          $("#choiceform").ajaxSubmit(options);
          
     },
     addTr: function(userid, username, realname, lan_id, lan_name, city_id, city_name){
     		var isExists = false;
			$("#partnerList tbody tr").each(function(){
				var tr = $(this);
				if($("input[name='userid']", tr).val() == userid){
					alert("该分销商已经添加");
					isExists = true;
				}
			});
			if(!isExists){
				var tr = $("<tr></tr>"); 
				$("<td></td>").html("<input type='hidden' name='userid' class='userid' value='"+userid+"'  />"+username).appendTo(tr);
				$("<td></td>").html(realname).appendTo(tr);
				$("<td></td>").html("<input type='hidden' name='lan_id' class='lan_id' value='"+lan_id+"'  /><input type='text' style='text-align:center;' name='lan_name' class='ipttxt lan_name' value='"+lan_name+"' />").appendTo(tr);
				$("<td></td>").html("<input type='hidden' name='city_id' class='city_id' value='"+city_id+"'  /><input type='text' style='text-align:center;' name='city_name' class='ipttxt city_name' value='"+city_name+"' />").appendTo(tr);
				$("<td></td>").html("<input type='text' style='text-align:center;' id='task_number' class='ipttxt new_task_number' value='0' dataType='int' required='true'>").appendTo(tr);
				$("<td></td>").html("<a href='javascript:void(0);' name='removeBtn'>删除</a>").appendTo(tr);
				tr.appendTo("#partnerList tbody");
			}
     }
    };
$(function(){
     editChoiceList.init();
});