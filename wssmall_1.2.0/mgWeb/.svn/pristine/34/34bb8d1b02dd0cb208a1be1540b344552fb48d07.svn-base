<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/batchReturn.js"></script>

<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">批量资料返档</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>        

<div class="input" align="center">
	<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
		<tr>
			<th>请选择上传文件</th>
			<td width="80%" style="vertical-align:text-bottom;" >
				<div style="width:80%;float:left;">
					<iframe name="uploadIframe" src="UpLoadFile.jsp" width="100%" 
						height="30px" frameborder="0" scrolling="no" style="margin:0px;padding:0px;overflow:hidden;">
					</iframe>
				</div>
				<div style="margin-left:1px; ">
					<span>
						<input style="font-size:12px;margin-right:-6px;margin-left:10px;" class="comBtn" value="下载模板" 
						type="submit" id="export" name="export">
					</span>
					<span>
						<input style="font-size:12px;margin-right:-6px;margin-left:20px;" class="comBtn" value="上&nbsp;传" 
						type="submit" id="upload" name="upLoadA">
					</span>					
				</div><br/>
				<p>
					<span name="dealResult">
					</span>
				</p>
			</td>
		</tr>
	</table>
</div>
<div>
	
	<div id="returnList">
		<jsp:include page="/shop/admin/batch/batchReturnList.jsp" flush="true"></jsp:include>
	</div>
</div>