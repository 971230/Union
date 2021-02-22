
select a.*,a.rowid from inf_comm_client_endpoint a where ep_id=2;
select a.*,a.rowid from inf_comm_client_operation a where op_id=2 ;
select a.*,a.rowid from inf_comm_client_request a where req_id='INF_JsonTest_REQ';
select a.*,a.rowid from inf_comm_client_response a where  rsp_id='INF_AddCust_RSP';


---2配置接口访问地址,一次配置,以后复用**************** 地址根据现场情况配置
insert into inf_comm_client_endpoint (EP_ID, EP_ADDRESS, EP_DESC, TIMEOUT, EP_TYPE)
values ('2', 'https://test.yintong.com.cn/traderapi/llwalletopen.htm', 'Json接口', 30, 'HttpJson');




----3接口配置
insert into inf_comm_client_operation (OP_ID, OP_CODE, EP_ID, REQ_ID, RSP_ID, OP_DESC, LOG_LEVEL, CLOSE_FLAG, REQ_USER_ID, DEAL_SUCCESS_FLAG)
values ('2', 'INF_JsonTest', '2', 'INF_JsonTest_REQ', 'INF_JsonTest_RSP', '	连连钱包充值', '', 'F', '1', '');




---4 请求报文,用以下报文贴到blob字段
insert into inf_comm_client_request (REQ_ID, GVAR_ID, REQ_TPL, CLASS_PATH, QNAME_ENCODE, QNAME, OPER_CLASSNAME, OPER_METHOD)
values ('INF_JsonTest_REQ', '', '', '', '', '', '', '');




-----5 返回报文,用以下报文贴到blob字段**********返回报文还没测试
insert into inf_comm_client_response (RSP_ID, CDATA_PATH, TRANS_TPL, XML_MAPPER, TRANS_FAULT, RSP_CLASSPATH)
values ('INF_AddCust_RSP', '', '', '', '', '');















