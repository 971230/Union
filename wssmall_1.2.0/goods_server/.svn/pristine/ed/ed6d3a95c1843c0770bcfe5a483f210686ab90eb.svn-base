package com.ztesoft.net.mall.plugin.standard.album;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import params.ZteResponse;

public class Thumb  extends ZteRequest<ZteResponse> {
	private String  seq         ;
	private String  es_goods_id;
	private String  thumb_path  ;
	private String  thumb_type  ;
	private String  width       ;
	private String  height      ;
	private String  file_path   ;
	private String  create_time ;
	private String  source_from ;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getEs_goods_id() {
		return es_goods_id;
	}

	public void setEs_goods_id(String es_goods_id) {
		this.es_goods_id = es_goods_id;
	}

	public String getThumb_path() {
		return thumb_path;
	}

	public void setThumb_path(String thumb_path) {
		this.thumb_path = thumb_path;
	}

	public String getThumb_type() {
		return thumb_type;
	}

	public void setThumb_type(String thumb_type) {
		this.thumb_type = thumb_type;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Thumb(){
		
	}
	
	public Thumb(String thumb_path ,String thumb_type ,String width ,String height ,String es_goods_id){
		this.es_goods_id = es_goods_id;
		this.thumb_path = thumb_path;
		this.thumb_type = thumb_type;
		this.width = width;
		this.height = height;
		this.file_path  = file_path;
	}
}
