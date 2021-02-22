<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/tpl_index.js"></script>
<link href="<%=request.getContextPath() %>/publics/lucene/info.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/template/css/tree.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/rule/css/style.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/template/css/tpl_index.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/admin/point.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>

<div class="gridWarp" >
	<!-- 目录管理 -->
	<div class="tpl_left" style="width:200px;float:left">
		<!-- 左边菜单树 -->
	   	<div id="rule_page_left1">
		   <input type="hidden" id="catalogueId" value="" /> 
	       <input type="hidden" id="catalogueName" value="" />
	       <input type="hidden" id="orderTemplateId" value="" />
	       <input type="hidden" id="orderTemplateName" value="" />
	       <div style="position: absolute;margin-left:100px;margin-top:100px;"><img id="loading" src="<%=request.getContextPath()%>/shop/admin/template/images/loading.gif" style="display:none"></div>
		   <div class="tree" id="catalogue_div" style="height:100%;"></div>
		</div>
		<div class="contextMenu" id="rightMenu1">
			<ul id="menu1" style="display: none;">
				<li><em class=""></em><a id="addOrderTpl" href="javascript:void(0);">新增模板</a></li>
			    <li><em class=""></em><a id="addCatalogue" href="javascript:void(0);">新增子目录</a></li>
			    <li><em class=""></em><a id="modCatalogue" href="javascript:void(0);">修改目录</a></li>
			    <li><em class=""></em><a id="delCatalogue" href="javascript:void(0);">删除目录</a></li>
			</ul>
			<ul id="menu3" style="display: none;">
			    <li><em class=""></em><a id="modOrderTpl" href="javascript:void(0);">修改模板</a></li>
			    <li><em class=""></em><a id="copyOrderTpl" href="javascript:void(0);">复制模板</a></li>
			    <li><em class=""></em><a id="delOrderTpl" href="javascript:void(0);">删除模板</a></li>
			</ul>
		</div>	
	</div>
	<!-- 目录树 -->
	<div class="treeRightCon">
   		<div class="tab_li">
        	<ul class="clearfix">
            	<li class="curr"><a href="javascript:void(0);">视图</a></li>
            	<li><a href="javascript:void(0);">源码</a></li>
            </ul>
        </div>
      	<div class="tab_li_cont">
   	  		<div class="tree_toolbar">
            	<div class="toolbar_l">
                	<ul name="model_switch" class="tab_qh clearfix">
                    	<li name="simple_model" class="curr"><a href="javascript:void(0);">预览模式</a></li>
                    	<li name="oper_model"><a href="javascript:void(0);">编辑模式</a></li>
                    </ul>
                </div>
                <div class="tool_list">
                	<ul class="tree_clearfix" id="btnView" style="display: none;">
                    	<li id="cutNodeTree"><a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_cut.png">剪切</a></li>
                    	<li id="copyNodeTree"><a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_copy.png">复制</a></li>
                    	<li id="pasteNodeTree"><a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_paste.png">粘贴</a></li>
                    	<li id="delNodeTree"><a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_delete.png">删除</a></li>
                    	<li id="upNodeTree"><a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_up.png">上移</a></li>
                    	<li id="downNodeTree"><a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_down.png">下移</a></li>
                    	<li><a id="addNodeTree" href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_input.png">插入</a>
                        	<div class="tool_posi" style="display: none;">
                            	<span class="arr_up"></span>
                              	<div class="item1">
                                	<label>
                                    	<input type="radio" checked="checked" value="1" id="RadioGroup1_0" name="RadioGroup1" />同级新增
                                    </label>
                                  	<label>
                                    	<input type="radio" id="RadioGroup1_1" value="2" name="RadioGroup1" />子节点新增
                                   	</label>
    							</div>
                                <div class="item2">新增行数<input type="text" style="width:80px; margin-left:10px;" id="textfield" name="textfield"/></div>
                                <div class="item2"><input type="button" class="toolBtn" value="提交" id="addNodeTreeButton" name="addNodeTreeButton"/></div>
                            </div>
                        </li>
                        <li id="ModNodeTree"><a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_down.png">修改</a></li>
                    	<li id="setUpTree">
                    		<a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_key.png">一键设置</a>
                    	</li>
                    	<li id="reflashSetUpTree">
                    		<a href="javascript:void(0);"><img width="16" height="16" src="${ctx}/shop/admin/template/images/ic_key.png">刷新字段属性</a>
                    	</li>
                    </ul>
           		</div>
            </div>
        </div>
        <div id="topDiv"></div>
    </div>
	<div id="showDlg"></div>
</div>