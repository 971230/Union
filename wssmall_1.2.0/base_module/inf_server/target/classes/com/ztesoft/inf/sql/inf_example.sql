
select * from inf_comm_client_request_user where user_id=-1;
select * from inf_comm_client_endpoint where ep_id=3001;
select * from inf_comm_client_operation where op_code='INF_GetCustBasicByProduct' ;
select * from inf_comm_client_request where req_id='INF_GetCustBasicByProduct_REQ';
select * from inf_comm_client_response  where rsp_id='INF_GetCustBasicByProduct_RSP';


---1配置接口访问用户,配置一次即可

insert into inf_comm_client_request_user (USER_ID, USER_CODE, USER_NAME, USER_PWD, USER_PARAM, USER_DESC)
values ('-1', 'mmarkt', 'CRM接口用户', 'mmarkt', '', '默认用户');


---2配置接口访问地址,一次配置,以后复用
insert into inf_comm_client_endpoint (EP_ID, EP_ADDRESS, EP_DESC, TIMEOUT, EP_TYPE)
values ('3001', 'http://10.45.47.190:7010/CrmWeb/services/exchangeSOAP', '江西CRM自服务接口地址--网厅环境', 30, '');


----3接口配置
insert into inf_comm_client_operation (OP_ID, OP_CODE, EP_ID, REQ_ID, RSP_ID, OP_DESC, LOG_LEVEL, CLOSE_FLAG, REQ_USER_ID, DEAL_SUCCESS_FLAG)
values ('1230', 'INF_GetCustBasicByProduct', '3001', 'INF_GetCustBasicByProduct_REQ', 'INF_GetCustBasicByProduct_RSP', '江西自服务-根据产品号码查询客户基本信息', '', 'F', '', '');


---4 请求报文,用以下报文贴到blob字段

insert into inf_comm_client_request (REQ_ID, GVAR_ID, REQ_TPL, CLASS_PATH, QNAME_ENCODE, QNAME, OPER_CLASSNAME, OPER_METHOD)
values ('INF_GetCustBasicByProduct_REQ', '', '<BLOB>', '', '', '', '', '');



<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:exc="http://ws.inf.crm.ztesoft.com/exchange/">
   <soapenv:Header/>
   <soapenv:Body>
      <exc:exchange>
         <in><![CDATA[
<Root><Header>
<ExchangeId>${inf.exchangeidgen()}</ExchangeId>
<BizCode>Query</BizCode>
<ClientId>${inf.user_code}</ClientId>
<Password>${inf.user_pwd}</Password>
</Header>
<Query>
<Code>INF_GetCustBasicByProduct</Code>
<QueryParam>
<acc_nbr>${accNbr}</acc_nbr>
<lan_Code>${lan_code}</lan_Code>
</QueryParam>
</Query>
</Root>]]></in>
      </exc:exchange>
   </soapenv:Body>
</soapenv:Envelope>


-----5 返回报文,用以下报文贴到blob字段
insert into inf_comm_client_response (RSP_ID, CDATA_PATH, TRANS_TPL, XML_MAPPER, TRANS_FAULT, RSP_CLASSPATH)
values ('INF_GetCustBasicByProduct_RSP', 'namespace.soap=http://schemas.xmlsoap.org/soap/envelope/
namespace.ns=http://ws.inf.crm.ztesoft.com/exchange/
soap:Envelope/soap:Body/ns:exchangeResponse/out', '<BLOB>', '<MapperContext>
    <XpathImplicitCollection xpath=''/QueryResults'' nodeName=''CustBasicInfo'' fieldName=''CustBasicInfo''/>
</MapperContext>', '', '');




<#assign rsp=doc.Root.QueryResults>
<#if  rsp?has_content>
    ${rsp["@@markup"]}
<#else>
    <QueryResults />
</#if>









