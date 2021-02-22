package com.ztesoft.net.app.base.core.model;



public class UserMsg {
	  private String m_id;//消息主键
	    private String m_type;//消息类型
	    private String m_topic;//消息主题
	    private String m_content;//消息内容
	    private String m_sendtime;//发送时间
	    private String m_reciverid;//收件人ID
	    private String m_senderid;//发件人ID
	    private int    m_reciverstate;//收件人状态
	    private int    m_senderstate;//发件人状态
	    private String m_sendername;//发件人姓名
	    private int    m_sendertype;//发件人类型
	    private String m_recivername;
	    private int    m_recivertype;
	    

		public int getM_recivertype() {
			return m_recivertype;
		}
		public void setM_recivertype(int m_recivertype) {
			this.m_recivertype = m_recivertype;
		}
		public String getM_recivername() {
			return m_recivername;
		}
		public void setM_recivername(String m_recivername) {
			this.m_recivername = m_recivername;
		}
		public String getM_sendername() {
			return m_sendername;
		}
		public void setM_sendername(String m_sendername) {
			this.m_sendername = m_sendername;
		}
		public int getM_sendertype() {
			return m_sendertype;
		}
		public void setM_sendertype(int m_sendertype) {
			this.m_sendertype = m_sendertype;
		}
		public String getM_id() {
			return m_id;
		}
		public void setM_id(String m_id) {
			this.m_id = m_id;
		}
		public String getM_type() {
			return m_type;
		}
		public void setM_type(String m_type) {
			this.m_type = m_type;
		}
		public String getM_topic() {
			return m_topic;
		}
		public void setM_topic(String m_topic) {
			this.m_topic = m_topic;
		}
		public String getM_content() {
			return m_content;
		}
		public void setM_content(String m_content) {
			this.m_content = m_content;
		}
		public String getM_sendtime() {
			return m_sendtime;
		}
		public void setM_sendtime(String m_sendtime) {
			this.m_sendtime = m_sendtime;
		}
		public String getM_reciverid() {
			return m_reciverid;
		}
		public void setM_reciverid(String m_reciverid) {
			this.m_reciverid = m_reciverid;
		}
		public String getM_senderid() {
			return m_senderid;
		}
		public void setM_senderid(String m_senderid) {
			this.m_senderid = m_senderid;
		}
		public int getM_reciverstate() {
			return m_reciverstate;
		}
		public void setM_reciverstate(int m_reciverstate) {
			this.m_reciverstate = m_reciverstate;
		}
		public int getM_senderstate() {
			return m_senderstate;
		}
		public void setM_senderstate(int m_senderstate) {
			this.m_senderstate = m_senderstate;
		}
		
}
