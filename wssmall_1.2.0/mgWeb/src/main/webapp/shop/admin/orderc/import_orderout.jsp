<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/import_orderout.js"></script>
<div class="input" align="center">
	<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
		<tr>
			<td colspan="2">
				<p>
					<span><b>温馨提示：</b>请点击下方关闭按钮以完成后续操作</span>
				</p>
			</td>
		</tr>
		<tr>
			<th>请选择上传文件</th>
			<td width="80%" style="vertical-align:text-bottom;" >
				<div style="width:60%;float:left;">
					<iframe name="uploadIframe" id="uploadIframe" src="/wssmall/shop/admin/batch/UpLoadFile.jsp" width="100%" 
						height="30px" frameborder="0" scrolling="no" style="margin:0px;padding:0px;overflow:hidden;">
					</iframe>
				</div>
				<div style="margin-left:1px; ">
					<span>
						
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
		
		
		
		<tr>
			<td colspan="2" text-align="center">
				<p>
					<span>
						<span sytle='float:left;margin-left:10px;width:150px;'><input style="font-size:12px;width: 100px;" class="comBtn" value="下载合约卡模板"  type="submit" id="exportComtract" name="export"></span>
						<span sytle='float:left;margin-left:10px;width:150px;'><input style="font-size:12px;width: 100px;" class="comBtn" value="下载云卡模板" type="submit" id="exportCloud" name="export"></span>
						<span sytle='float:left;margin-left:10px;width:150px;'><input style="font-size:12px;width: 120px;" class="comBtn" value="下载云卡模板(终端)" type="submit" id="exportCloudWithLan" name="export"></span>
					</span>	
				</p>
			</td>
		</tr>
		
		
		
		
		<tr>
			<td colspan="2" text-align="center">
				<p>
					<span>
						<input style="font-size:12px;margin-right:-6px;margin-left:300px;" class="comBtn" value="关&nbsp;闭" 
						type="submit" id="close" name="closeA">
					</span>	
				</p>
			</td>
		</tr>
	</table>
</div>