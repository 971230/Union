-----------------------------------------------
-- Export file for user MMARKET              --
-- Created by Yao Min on 2014-2-28, 11:08:28 --
-----------------------------------------------

spool inf_object.log

prompt
prompt Creating table INF_COMM_CLIENT_CALLLOG
prompt ======================================
prompt
create table INF_COMM_CLIENT_CALLLOG
(
  LOG_ID      VARCHAR2(10) not null,
  OP_CODE     VARCHAR2(100),
  REQ_TIME    DATE,
  RSP_TIME    DATE,
  EP_ADDRESS  VARCHAR2(500),
  REQ_XML     BLOB,
  RSP_XML     BLOB,
  STATE       VARCHAR2(10),
  STATE_MSG   VARCHAR2(4000),
  PARAM_DESC  VARCHAR2(4000),
  RESULT_DESC VARCHAR2(4000),
  COL1        VARCHAR2(4000),
  COL2        VARCHAR2(4000),
  COL3        VARCHAR2(4000),
  COL4        VARCHAR2(4000),
  COL5        VARCHAR2(4000)
)
;

prompt
prompt Creating table INF_COMM_CLIENT_ENDPOINT
prompt =======================================
prompt
create table INF_COMM_CLIENT_ENDPOINT
(
  EP_ID      VARCHAR2(20) not null,
  EP_ADDRESS VARCHAR2(500),
  EP_DESC    VARCHAR2(1000),
  TIMEOUT    NUMBER,
  EP_TYPE    VARCHAR2(20)
)
;
alter table INF_COMM_CLIENT_ENDPOINT
  add primary key (EP_ID);

prompt
prompt Creating table INF_COMM_CLIENT_LOG_COL
prompt ======================================
prompt
create table INF_COMM_CLIENT_LOG_COL
(
  OP_ID     VARCHAR2(20),
  PARAM_KEY VARCHAR2(500),
  COL_NAME  VARCHAR2(100)
)
;
create index IND_COMM_CLEN_OPT_ID on INF_COMM_CLIENT_LOG_COL (OP_ID);

prompt
prompt Creating table INF_COMM_CLIENT_OPERATION
prompt ========================================
prompt
create table INF_COMM_CLIENT_OPERATION
(
  OP_ID             VARCHAR2(20) not null,
  OP_CODE           VARCHAR2(100),
  EP_ID             VARCHAR2(20),
  REQ_ID            VARCHAR2(50),
  RSP_ID            VARCHAR2(50),
  OP_DESC           VARCHAR2(1000),
  LOG_LEVEL         VARCHAR2(10),
  CLOSE_FLAG        CHAR(1) default 'F' not null,
  REQ_USER_ID       VARCHAR2(20),
  DEAL_SUCCESS_FLAG VARCHAR2(5)
)
;
comment on column INF_COMM_CLIENT_OPERATION.REQ_USER_ID
  is '关联inf_comm_client_operation请求用户,空则为默认';
comment on column INF_COMM_CLIENT_OPERATION.DEAL_SUCCESS_FLAG
  is '处理成功标识';
alter table INF_COMM_CLIENT_OPERATION
  add primary key (OP_ID);

prompt
prompt Creating table INF_COMM_CLIENT_REQUEST
prompt ======================================
prompt
create table INF_COMM_CLIENT_REQUEST
(
  REQ_ID         VARCHAR2(50) not null,
  GVAR_ID        VARCHAR2(20),
  REQ_TPL        BLOB,
  CLASS_PATH     VARCHAR2(200),
  QNAME_ENCODE   VARCHAR2(200),
  QNAME          VARCHAR2(200),
  OPER_CLASSNAME VARCHAR2(200),
  OPER_METHOD    VARCHAR2(200)
)
;
alter table INF_COMM_CLIENT_REQUEST
  add primary key (REQ_ID);

prompt
prompt Creating table INF_COMM_CLIENT_REQUEST_USER
prompt ===========================================
prompt
create table INF_COMM_CLIENT_REQUEST_USER
(
  USER_ID    VARCHAR2(20) not null,
  USER_CODE  VARCHAR2(200),
  USER_NAME  VARCHAR2(200),
  USER_PWD   VARCHAR2(30),
  USER_PARAM VARCHAR2(500),
  USER_DESC  VARCHAR2(500)
)
;
comment on column INF_COMM_CLIENT_REQUEST_USER.USER_ID
  is '用户id';
comment on column INF_COMM_CLIENT_REQUEST_USER.USER_CODE
  is '用户编码，请求时候';
comment on column INF_COMM_CLIENT_REQUEST_USER.USER_NAME
  is '用户名';
comment on column INF_COMM_CLIENT_REQUEST_USER.USER_PWD
  is '用户密码';
comment on column INF_COMM_CLIENT_REQUEST_USER.USER_PARAM
  is '相关参数';
comment on column INF_COMM_CLIENT_REQUEST_USER.USER_DESC
  is '描述';
alter table INF_COMM_CLIENT_REQUEST_USER
  add primary key (USER_ID);

prompt
prompt Creating table INF_COMM_CLIENT_RESPONSE
prompt =======================================
prompt
create table INF_COMM_CLIENT_RESPONSE
(
  RSP_ID        VARCHAR2(50) not null,
  CDATA_PATH    VARCHAR2(1000),
  TRANS_TPL     BLOB,
  XML_MAPPER    BLOB,
  TRANS_FAULT   VARCHAR2(2),
  RSP_CLASSPATH VARCHAR2(200)
)
;
alter table INF_COMM_CLIENT_RESPONSE
  add primary key (RSP_ID);

prompt
prompt Creating table SEQUENCE_MANAGEMENT
prompt ==================================
prompt
create table SEQUENCE_MANAGEMENT
(
  TABLE_CODE      VARCHAR2(32),
  FIELD_CODE      VARCHAR2(32),
  SEQUENCE_CODE   VARCHAR2(32),
  SEQUENCE_LENGTH NUMBER(38),
  SEQUENCE_DESC   VARCHAR2(250)
)
;

prompt
prompt Creating sequence SEQ_CRM2TO2_ID
prompt ================================
prompt
create sequence SEQ_CRM2TO2_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20
cycle;

prompt
prompt Creating sequence SEQ_INF_COMM_CLIENT_CALLLOG_ID
prompt ================================================
prompt
create sequence SEQ_INF_COMM_CLIENT_CALLLOG_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;


spool off


insert into sequence_management (TABLE_CODE, FIELD_CODE, SEQUENCE_CODE, SEQUENCE_LENGTH, SEQUENCE_DESC)
values ('INF_COMM_CLIENT_CALLLOG', 'LOG_ID', 'SEQ_INF_COMM_CLIENT_CALLLOG_ID', 15, '通用接口客户端调用');

---系统参数表
create table DC_SYSTEM_PARAM
(
  PARAM_CODE VARCHAR2(30) not null,
  PARAM_DESC VARCHAR2(50),
  PARAM_VAL  VARCHAR2(250)
)
alter table DC_SYSTEM_PARAM
  add constraint PK_DC_SYSTEM_PARAM primary key (PARAM_CODE)
