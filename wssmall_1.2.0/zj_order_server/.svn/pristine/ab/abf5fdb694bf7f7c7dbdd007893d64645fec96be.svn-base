package com.ztesoft.net.mall.core.timer;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;


 

  
 
 
 
import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.FTPUtil;


public class AuditFileTimer {
	private static Logger logger = Logger.getLogger(AuditFileTimer.class);
	private static String AUDIT_SQL = " SELECT (eo.order_id||'|'||eg.name||'|'||eddr.other_field_value||'|'||eozd.county_id||'|'||'00000004'||'|'||'开户'||'|'|| "+
									  " '0'||'|'||replace(NVL(eoel.payplatformorderid,'|'),',','|')||'|'||'1'||'|'||eoie.phone_num||'|'||'在线支付'||'|'|| " +
									  "	decode(epl.pay_method,'40','001','41','002','42','003','43','004','44','005','1','000','10','000','')||'|'|| " +
									  " eo.paymoney*100||'|'||eoel.order_origfee*100||'|'||replace(NVL(eoel.payplatformorderid,'|'),',','|')||'|'||'1'||'|'|| " +
									  " to_char(eo.create_time,'YYYYMMDDHH24MISS')||'|'||to_char(eo.pay_time,'YYYYMMDD')||'|'||eozd.develop_point_code ) as info" +
									  " FROM es_order eo  " +
									  " LEFT JOIN es_order_zhwq_adsl eozd ON eo.order_id=eozd.order_id " +
									  " LEFT JOIN es_order_ext eoe  ON eo.order_id=eoe.order_id " +
									  " left join es_order_items_ext eoie on eoie.order_id=eo.order_id " +
									  " LEFT JOIN es_order_extvtl eoel ON eo.order_id=eoel.order_id " +
									  " LEFT JOIN es_dc_public_dict_relation eddr ON eoe.order_city_code=eddr.field_value " +
									  " left join es_goods eg on eg.goods_id=eo.goods_id " +
									  " LEFT JOIN es_payment_logs epl ON epl.order_id=eo.order_id " +
									  " WHERE trunc(eo.create_time,'dd')>=trunc(sysdate-1,'dd') " +
									  " and trunc(eo.create_time,'dd') <trunc(sysdate,'dd') " +
									  " AND eddr.stype='bss_area_code' " +
									  " and eoe.order_from IN ('10071','10016') " +
									  " and eo.pay_status='1' " +
									  " AND eoel.payplatformorderid IS NOT NULL " +
									  " AND epl.pay_method NOT IN ('DLS','WZF') " +
									  " AND eoel.refundplatformorderid IS NULL " +
									  " AND regexp_substr(eoel.payplatformorderid,'[^|]+',1,1,'i') !='null' " +
									  " UNION ALL " +
									  " SELECT eo.order_id||'|'||eg.name||'|'||eddr.other_field_value||'|'||eozd.county_id||'|'||'00000004'||'|'||'开户'||'|'|| " +
									  " CASE WHEN trunc(eoe.refund_time,'dd')=TRUNC(eo.create_time,'dd')  " +
									  "   THEN '1' " +
									  "   ELSE '2'  " +
									  " END " +
									  " ||'|'||eoel.refundplatformorderid||'||'||'2'||'|'||eoie.phone_num||'|'||'在线支付'||'|'|| " +
									  " decode(epl.pay_method,'40','001','41','002','42','003','43','004','44','005','1','000','10','000','')||'|'|| " +
									  " eo.paymoney*100||'|'||eoel.order_origfee*100||'|'||replace(NVL(eoel.payplatformorderid,'|'),',','|')||'|'||'2'||'|'|| " +
									  " to_char(eo.create_time,'YYYYMMDDHH24MISS')||'|'||to_char(eo.pay_time,'YYYYMMDD')||'|'||eozd.develop_point_code " +
									  " FROM es_order eo  " +
									  " LEFT JOIN es_order_zhwq_adsl eozd ON eo.order_id=eozd.order_id " +
									  " LEFT JOIN es_order_ext eoe  ON eo.order_id=eoe.order_id " +
									  " left join es_order_items_ext eoie on eoie.order_id=eo.order_id " +
									  " LEFT JOIN es_order_extvtl eoel ON eo.order_id=eoel.order_id " +
									  " LEFT JOIN es_dc_public_dict_relation eddr ON eoe.order_city_code=eddr.field_value " +
									  " left join es_goods eg on eg.goods_id=eo.goods_id " +
									  " LEFT JOIN es_payment_logs epl ON epl.order_id=eo.order_id " +
									  " WHERE trunc(eo.create_time,'dd')>=trunc(sysdate-1,'dd') " +
									  " and trunc(eo.create_time,'dd') <trunc(sysdate,'dd') " +
									  " AND eddr.stype='bss_area_code' " +
									  " and eoe.order_from IN ('10071','10016') " +
									  " and eo.pay_status='1' " +
									  " AND epl.pay_method NOT IN ('DLS','WZF') " +
									  " AND eoel.refundplatformorderid IS NOT NULL";
	private static String NEW_AUDIT_SQL = " SELECT distinct (eo.order_id||'|'||eg.name||'|'||eddr.other_field_value||'|'||eozd.county_id||'|'||decode(eoe.order_from,'10071','00000004','10078','00000006')||'|'||'开户'||'|'|| "
                                 	    + " '0'||'|'||replace(NVL(eoel.payplatformorderid,'|'),',','|')||'|'||'1'||'|'||eoie.phone_num||'|'||'在线支付'||'|'||  "
                                        + " decode(epl.pay_method,'40','001','41','002','42','003','43','004','44','005','1','000','10','000',epl.pay_method)||'|'||  "
                                        + "  eo.paymoney*1000||'|'||eoel.order_origfee*1000||'|'||replace(NVL(eoel.payplatformorderid,'|'),',','|')||'|'||decode(eoe.flow_trace_id,'L','1','J','1','5')||'|'||  "
                                        + " to_char(eo.create_time,'YYYYMMDDHH24MISS')||'|'||to_char(eo.pay_time,'YYYYMMDD')||'|'||eozd.develop_point_code||'|'|| decode(eoel.out_operator,null,decode(eoe.order_from,'10071',(select a.pname from es_dc_public_ext a where a.stype='DIC_BSS_GONGHAO' and a.pkey=eoe.order_city_code),'10078',(select a.pname from es_dc_public_ext a where a.stype='DIC_BSS_GONGHAO_NEW' and a.pkey=eoe.order_city_code)),eoel.out_operator)||'|'|| "
                                        + " substr(nvl(eoel.order_charge_id,eoie.bssorderid),0,8)||'|'||eoie.bssorderid||'|'||nvl(eoel.order_charge_id,eoie.bssorderid)||'|'||decode(eoe.is_aop,'1','CBSS','2','BSS')) as info "
                                        + " FROM es_order eo   "
                                        + " LEFT JOIN es_order_zhwq_adsl eozd ON eo.order_id=eozd.order_id  "
                                        + " LEFT JOIN es_order_ext eoe  ON eo.order_id=eoe.order_id  "
                                        + " left join es_order_items_ext eoie on eoie.order_id=eo.order_id  "
                                        + " LEFT JOIN es_order_extvtl eoel ON eo.order_id=eoel.order_id  "
                                        + " LEFT JOIN es_dc_public_dict_relation eddr ON eoe.order_city_code=eddr.field_value  "
                                        + " left join es_goods eg on eg.goods_id=eo.goods_id  "
                                        + " LEFT JOIN es_payment_logs epl ON epl.order_id=eo.order_id  "
                                        + " WHERE trunc(eo.create_time,'dd')>=trunc(sysdate-1,'dd')  "
                                        + " and trunc(eo.create_time,'dd') <trunc(sysdate,'dd')  "
                                        /*+ " WHERE 1=1 "*/
                                        + " AND eddr.stype='bss_area_code'  "
                                        + " and eo.pay_status='1'  "
                                        + " AND eoel.payplatformorderid IS NOT NULL  "
                                        + " AND epl.pay_method IN ('40','41','42','43','44','1','10','51','52')  "
                                        + " AND eoel.refundplatformorderid IS NULL  "
                                        + " AND regexp_substr(eoel.payplatformorderid,'[^|]',1,1,'i') !='null'  "
                                        + " UNION ALL  "
                                        + " SELECT eo.order_id||'|'||eg.name||'|'||eddr.other_field_value||'|'||eozd.county_id||'|'||decode(eoe.order_from,'10071','00000004','10078','00000006')||'|'||'开户'||'|'||  "
                                        + " CASE WHEN trunc(eoe.refund_time,'dd')=TRUNC(eo.create_time,'dd')   "
                                        + " THEN '1'  "
                                        + " ELSE '2'   "
                                        + " END  "
                                        + " ||'|'||eoel.refundplatformorderid||'||'||'2'||'|'||eoie.phone_num||'|'||'在线支付'||'|'||  "
                                        + " decode(epl.pay_method,'40','001','41','002','42','003','43','004','44','005','1','000','10','000',epl.pay_method)||'|'||  "
                                        + " eo.paymoney*1000||'|'||eoel.order_origfee*1000||'|'||replace(NVL(eoel.payplatformorderid,'|'),',','|')||'|'||decode(eoe.flow_trace_id,'L','1','J','1','5')||'|'||  " 
                                        + " to_char(eo.create_time,'YYYYMMDDHH24MISS')||'|'||to_char(eo.pay_time,'YYYYMMDD')||'|'||eozd.develop_point_code ||'|'|| decode(eoel.out_operator,null,decode(eoe.order_from,'10071',(select a.pname from es_dc_public_ext a where a.stype='DIC_BSS_GONGHAO' and a.pkey=eoe.order_city_code),'10078',(select a.pname from es_dc_public_ext a where a.stype='DIC_BSS_GONGHAO_NEW' and a.pkey=eoe.order_city_code)),eoel.out_operator)||'|'|| "
                                        + " substr(nvl(eoel.order_charge_id,eoie.bssorderid),0,8)||'|'||eoie.bssorderid||'|'||nvl(eoel.order_charge_id,eoie.bssorderid)||'|'||decode(eoe.is_aop,'1','CBSS','2','BSS') "
                                        + " FROM es_order eo   "
                                        + " LEFT JOIN es_order_zhwq_adsl eozd ON eo.order_id=eozd.order_id  "
                                        + " LEFT JOIN es_order_ext eoe  ON eo.order_id=eoe.order_id  "
                                        + " left join es_order_items_ext eoie on eoie.order_id=eo.order_id  "
                                        + " LEFT JOIN es_order_extvtl eoel ON eo.order_id=eoel.order_id  "
                                        + " LEFT JOIN es_dc_public_dict_relation eddr ON eoe.order_city_code=eddr.field_value  "
                                        + " left join es_goods eg on eg.goods_id=eo.goods_id  "
                                        + " LEFT JOIN es_payment_logs epl ON epl.order_id=eo.order_id  "
                                        + " WHERE trunc(eo.create_time,'dd')>=trunc(sysdate-1,'dd')  "
                                        + " and trunc(eo.create_time,'dd') <trunc(sysdate,'dd')  "
                                        /*+ " WHERE 1=1 "*/
                                        + " AND eddr.stype='bss_area_code'    "                   
                                        + " and eo.pay_status='1'  "
                                        + " AND epl.pay_method IN ('40','41','42','43','44','1','10','51','52')  "
                                        + " AND eoel.refundplatformorderid IS NOT NULL ";
	
	private static final String  AUDIT_SQL_3 =  
			        "       SELECT distinct (eo.order_id || '|' || eg.name || '|' ||                                                      "+
					"                       eddr.other_field_value || '|' || eozd.county_id || '|' || CASE                                "+
					"                         WHEN eoel.refundplatformorderid IS NOT NULL THEN                                            "+
					"                          '00000009'                                                                                 "+
					"                         ELSE                                                                                        "+
					"                          decode(eoe.order_from,                                                                     "+
					"                                 '10071',                                                                            "+
					"                                 '00000004',                                                                         "+
					"                                 '10078',                                                                            "+
					"                                 '00000006',                                                                         "+
					"                                 '10082',                                                                            "+
					"                                 '00000007',                                                                         "+
					"                                 '10072',                                                                            "+
					"                                 '00000008',                                                                         "+
					"                                 '10083',                                                                            "+
					"                                 '00000005','10093','00000012')                                                                         "+
					"                       END || '|' || '开户' || '|' || '0' || '|' ||                                                  "+
					"                       replace(NVL(eoel.payplatformorderid, '|'),                                                    "+
					"                                ',',                                                                                 "+
					"                                '|') || '|' || '1' || '|' ||                                                         "+
					"                       eoie.phone_num || '|' || '在线支付' || '|' ||                                                 "+
					"                       decode(epl.pay_method,                                                                        "+
					"                               '40',                                                                                 "+
					"                               '001',                                                                                "+
					"                               '41',                                                                                 "+
					"                               '002',                                                                                "+
					"                               '42',                                                                                 "+
					"                               '003',                                                                                "+
					"                               '43',                                                                                 "+
					"                               '004',                                                                                "+
					"                               '44',                                                                                 "+
					"                               '005',                                                                                "+
					"                               '1',                                                                                  "+
					"                               '000',                                                                                "+
					"                               '10',                                                                                 "+
					"                               '000',                                                                                "+
					"                               epl.pay_method) || '|' ||                                                             "+
					"                       eo.paymoney * 1000 || '|' ||                                                                  "+
					"                       eoel.order_origfee * 1000 || '|' || '' || '|' || '' || '|' ||                                 "+
					"                       decode(eoe.flow_trace_id,                                                                     "+
					"                               'L',                                                                                  "+
					"                               '1',                                                                                  "+
					"                               'J',                                                                                  "+
					"                               '1',                                                                                  "+
					"                               '5') || '|' ||                                                                        "+
					"                       to_char(eo.create_time, 'YYYYMMDDHH24MISS') || '|' ||                                         "+
					"                       to_char(eo.pay_time, 'YYYYMMDD') || '|' ||                                                    "+
					"                       eoel.out_office || '|' ||                                                                     "+
					"                       decode(eoel.out_operator,                                                                     "+
					"                               null,                                                                                 "+
					"                               decode(eoe.order_from,                                                                "+
					"                                      '10071',                                                                       "+
					"                                      (select a.pname                                                                "+
					"                                         from es_dc_public_ext a                                                     "+
					"                                        where a.stype = 'DIC_BSS_GONGHAO'                                            "+
					"                                          and a.pkey = eoe.order_city_code),                                         "+
					"                                      '10078',                                                                       "+
					"                                      (select a.pname                                                                "+
					"                                         from es_dc_public_ext a                                                     "+
					"                                        where a.stype =                                                              "+
					"                                              'DIC_BSS_GONGHAO_NEW'                                                  "+
					"                                          and a.pkey = eoe.order_city_code)),                                        "+
					"                               eoel.out_operator) || '|' ||                                                          "+
					"                       substr(nvl(eoel.order_charge_id, eoie.bssorderid),                                            "+
					"                               0,                                                                                    "+
					"                               8) || '|' || eoie.bssorderid || '|' ||                                                "+
					"                       nvl(eoel.order_charge_id, eoie.bssorderid) || '|' ||                                          "+
					"                       decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')) as info                                          "+
					"         FROM es_order eo                                                                                            "+
					"         LEFT JOIN es_order_zhwq_adsl eozd                                                                           "+
					"           ON eo.order_id = eozd.order_id                                                                            "+
					"         LEFT JOIN es_order_ext eoe                                                                                  "+
					"           ON eo.order_id = eoe.order_id                                                                             "+
					"         left join es_order_items_ext eoie                                                                           "+
					"           on eoie.order_id = eo.order_id                                                                            "+
					"         LEFT JOIN es_order_extvtl eoel                                                                              "+
					"           ON eo.order_id = eoel.order_id                                                                            "+
					"         LEFT JOIN es_dc_public_dict_relation eddr                                                                   "+
					"           ON eoe.order_city_code = eddr.field_value                                                                 "+
					"         left join es_goods eg                                                                                       "+
					"           on eg.goods_id = eo.goods_id                                                                              "+
					"         LEFT JOIN es_payment_logs epl                                                                               "+
					"           ON epl.order_id = eo.order_id                                                                             "+
					"        WHERE trunc(eo.create_time, 'dd') >= trunc(sysdate - 1 , 'dd')                                                "+
					"          and trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')                                                    "+
					"          AND eddr.stype = 'bss_area_code'                                                                           "+
					"          and eo.pay_status = '1'                                                                                    "+
					"          AND eoel.payplatformorderid IS NOT NULL                                                                    "+
					"          AND epl.pay_method IN                                                                                      "+
					"              ('40', '41', '42', '43', '44', '1', '10', '51', '52')                                                  "+
					"          AND eoel.refundplatformorderid IS NULL                                                                     "+
					"          AND regexp_substr(eoel.payplatformorderid, '[^|]', 1, 1, 'i') !=                                           "+
					"              'null'                                                                                                 "+
					"          AND eo.paymoney > 0                                                                                        "+
					"       UNION ALL                                                                                                     "+
					"       SELECT eo.order_id || '|' || eg.name || '|' ||                                                                "+
					"              eddr.other_field_value || '|' || eozd.county_id || '|' || CASE                                         "+
					"                WHEN eoel.refundplatformorderid IS NOT NULL THEN                                                     "+
					"                 '00000009'                                                                                          "+
					"                ELSE                                                                                                 "+
					"                 decode(eoe.order_from,                                                                              "+
					"                        '10071',                                                                                     "+
					"                        '00000004',                                                                                  "+
					"                        '10078',                                                                                     "+
					"                        '00000006',                                                                                  "+
					"                        '10082',                                                                                     "+
					"                        '00000007',                                                                                  "+
					"                        '10072',                                                                                     "+
					"                        '00000008',                                                                                  "+
					"                        '10083',                                                                                     "+
					"                        '00000005')                                                                                  "+
					"              END || '|' || '开户' || '|' || CASE                                                                    "+
					"                WHEN trunc(eoe.refund_time, 'dd') =                                                                  "+
					"                     TRUNC(eo.create_time, 'dd') THEN                                                                "+
					"                 '1'                                                                                                 "+
					"                ELSE                                                                                                 "+
					"                 '2'                                                                                                 "+
					"              END || '|' ||                                                                                          "+
					"              substr(eoel.payplatformorderid,1,(instr(eoel.payplatformorderid,'|',1,1)) -1) || '|' || eoel.refundplatformorderid || '|' || '1' || '|' ||                           "+
					"              eoie.phone_num || '|' || '在线支付' || '|' ||                                                          "+
					"              decode(epl.pay_method,                                                                                 "+
					"                     '40',                                                                                           "+
					"                     '001',                                                                                          "+
					"                     '41',                                                                                           "+
					"                     '002',                                                                                          "+
					"                     '42',                                                                                           "+
					"                     '003',                                                                                          "+
					"                     '43',                                                                                           "+
					"                     '004',                                                                                          "+
					"                     '44',                                                                                           "+
					"                     '005',                                                                                          "+
					"                     '1',                                                                                            "+
					"                     '000',                                                                                          "+
					"                     '10',                                                                                           "+
					"                     '000',                                                                                          "+
					"                     epl.pay_method) || '|' || eo.paymoney * 1000 || '|' ||                                          "+
					"              eoel.order_origfee * 1000 || '|' ||                                                                    "+
					"              substr(eoel.payplatformorderid,1,(instr(eoel.payplatformorderid,'|',1,1)) -1) || '|' || eoel.refundplatformorderid || '|' ||                                         "+
					"              decode(eoe.flow_trace_id, 'L', '1', 'J', '1', '5') || '|' ||                                           "+
					"              to_char(eo.create_time, 'YYYYMMDDHH24MISS') || '|' ||                                                  "+
					"              to_char(eo.pay_time, 'YYYYMMDD') || '|' || eoel.out_office || '|' ||                                   "+
					"              decode(eoel.out_operator,                                                                              "+
					"                     null,                                                                                           "+
					"                     decode(eoe.order_from,                                                                          "+
					"                            '10071',                                                                                 "+
					"                            (select a.pname                                                                          "+
					"                               from es_dc_public_ext a                                                               "+
					"                              where a.stype = 'DIC_BSS_GONGHAO'                                                      "+
					"                                and a.pkey = eoe.order_city_code),                                                   "+
					"                            '10078',                                                                                 "+
					"                            (select a.pname                                                                          "+
					"                               from es_dc_public_ext a                                                               "+
					"                              where a.stype = 'DIC_BSS_GONGHAO_NEW'                                                  "+
					"                                and a.pkey = eoe.order_city_code)),                                                  "+
					"                     eoel.out_operator) || '|' ||                                                                    "+
					"              substr(nvl(eoel.order_charge_id, eoie.bssorderid), 0, 8) || '|' ||                                     "+
					"              eoie.bssorderid || '|' ||                                                                              "+
					"              nvl(eoel.order_charge_id, eoie.bssorderid) || '|' ||                                                   "+
					"              decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')                                                            "+
					"         FROM es_order eo                                                                                            "+
					"         LEFT JOIN es_order_zhwq_adsl eozd                                                                           "+
					"           ON eo.order_id = eozd.order_id                                                                            "+
					"         LEFT JOIN es_order_ext eoe                                                                                  "+
					"           ON eo.order_id = eoe.order_id                                                                             "+
					"         left join es_order_items_ext eoie                                                                           "+
					"           on eoie.order_id = eo.order_id                                                                            "+
					"         LEFT JOIN es_order_extvtl eoel                                                                              "+
					"           ON eo.order_id = eoel.order_id                                                                            "+
					"         LEFT JOIN es_dc_public_dict_relation eddr                                                                   "+
					"           ON eoe.order_city_code = eddr.field_value                                                                 "+
					"         left join es_goods eg                                                                                       "+
					"           on eg.goods_id = eo.goods_id                                                                              "+
					"         LEFT JOIN es_payment_logs epl                                                                               "+
					"           ON epl.order_id = eo.order_id                                                                             "+
					"        WHERE ((trunc(eo.create_time, 'dd') >= trunc(sysdate - 1, 'dd') and                                          "+
					"              trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')) or                                                "+
					"              (trunc(eoe.refund_time, 'dd') >= trunc(sysdate - 1, 'dd') and                                          "+
					"              trunc(eoe.refund_time, 'dd') < trunc(sysdate, 'dd')))                                                 "+
					"          AND eddr.stype = 'bss_area_code'                                                                           "+
					"          and eo.pay_status = '1'                                                                                    "+
					"          AND epl.pay_method IN                                                                                      "+
					"              ('40', '41', '42', '43', '44', '1', '10', '51', '52')                                                  "+
					"          AND eoel.refundplatformorderid IS NOT NULL                                                                 "+
					"          AND eo.paymoney > 0                                                                                        "+
					"       UNION ALL                                                                                                     "+
					"       SELECT eo.order_id || '|' || eg.name || '|' ||                                                                "+
					"              eddr.other_field_value || '|' || eozd.county_id || '|' || CASE                                         "+
					"                WHEN eoel.refundplatformorderid IS NOT NULL THEN                                                     "+
					"                 '00000009'                                                                                          "+
					"                ELSE                                                                                                 "+
					"                 decode(eoe.order_from,                                                                              "+
					"                        '10071',                                                                                     "+
					"                        '00000004',                                                                                  "+
					"                        '10078',                                                                                     "+
					"                        '00000006',                                                                                  "+
					"                        '10082',                                                                                     "+
					"                        '00000007',                                                                                  "+
					"                        '10072',                                                                                     "+
					"                        '00000008',                                                                                  "+
					"                        '10083',                                                                                     "+
					"                        '00000005')                                                                                  "+
					"              END || '|' || '开户' || '|' || '0' || '|' ||                                                           "+
					"              replace(NVL(eoel.payplatformorderid, '|'), ',', '|') || '|' || '1' || '|' ||                           "+
					"              eoie.phone_num || '|' || '在线支付' || '|' ||                                                          "+
					"              decode(epl.pay_method,                                                                                 "+
					"                     '40',                                                                                           "+
					"                     '001',                                                                                          "+
					"                     '41',                                                                                           "+
					"                     '002',                                                                                          "+
					"                     '42',                                                                                           "+
					"                     '003',                                                                                          "+
					"                     '43',                                                                                           "+
					"                     '004',                                                                                          "+
					"                     '44',                                                                                           "+
					"                     '005',                                                                                          "+
					"                     '1',                                                                                            "+
					"                     '000',                                                                                          "+
					"                     '10',                                                                                           "+
					"                     '000',                                                                                          "+
					"                     epl.pay_method) || '|' || eo.paymoney * 1000 || '|' ||                                          "+
					"              eoel.order_origfee * 1000 || '|' ||                                                                    "+
					"              replace(NVL('', '|'), ',', '|') || '|' || '3' || '|' ||                                                "+
					"              to_char(eo.create_time, 'YYYYMMDDHH24MISS') || '|' ||                                                  "+
					"              to_char(eo.pay_time, 'YYYYMMDD') || '|' || eoel.out_office || '|' ||                                   "+
					"              decode(eoel.out_operator,                                                                              "+
					"                     null,                                                                                           "+
					"                     decode(eoe.order_from,                                                                          "+
					"                            '10071',                                                                                 "+
					"                            (select a.pname                                                                          "+
					"                               from es_dc_public_ext a                                                               "+
					"                              where a.stype = 'DIC_BSS_GONGHAO'                                                      "+
					"                                and a.pkey = eoe.order_city_code),                                                   "+
					"                            '10078',                                                                                 "+
					"                            (select a.pname                                                                          "+
					"                               from es_dc_public_ext a                                                               "+
					"                              where a.stype = 'DIC_BSS_GONGHAO_NEW'                                                  "+
					"                                and a.pkey = eoe.order_city_code)),                                                  "+
					"                     eoel.out_operator) || '|' ||                                                                    "+
					"              substr(nvl(eoel.order_charge_id, eoie.bssorderid), 0, 8) || '|' ||                                     "+
					"              eoie.bssorderid || '|' ||                                                                              "+
					"              nvl(eoel.order_charge_id, eoie.bssorderid) || '|' ||                                                   "+
					"              decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')                                                            "+
					"         FROM es_order eo                                                                                            "+
					"         LEFT JOIN es_order_zhwq_adsl eozd                                                                           "+
					"           ON eo.order_id = eozd.order_id                                                                            "+
					"         LEFT JOIN es_order_ext eoe                                                                                  "+
					"           ON eo.order_id = eoe.order_id                                                                             "+
					"         left join es_order_items_ext eoie                                                                           "+
					"           on eoie.order_id = eo.order_id                                                                            "+
					"         LEFT JOIN es_order_extvtl eoel                                                                              "+
					"           ON eo.order_id = eoel.order_id                                                                            "+
					"         LEFT JOIN es_dc_public_dict_relation eddr                                                                   "+
					"           ON eoe.order_city_code = eddr.field_value                                                                 "+
					"         left join es_goods eg                                                                                       "+
					"           on eg.goods_id = eo.goods_id                                                                              "+
					"         LEFT JOIN es_payment_logs epl                                                                               "+
					"           ON epl.order_id = eo.order_id                                                                             "+
					"        WHERE trunc(eo.create_time, 'dd') >= trunc(sysdate - 1, 'dd')                                                "+
					"          and trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')                                                    "+
					"          AND eddr.stype = 'bss_area_code'                                                                           "+
					"          and eo.pay_status = '1'                                                                                    "+
					"          AND epl.pay_method IN                                                                                      "+
					"              ('40', '41', '42', '43', '44', '1', '10', '51', '52')                                                  "+
					"          AND eoel.refundplatformorderid IS NULL                                                                     "+
					"          AND eo.paymoney > 0                                                                                        "+
					"          AND eoe.exception_desc IS NOT NULL                                                                         ";
	
	private static final String AUDIT_SQL_4 = 
			"	SELECT distinct (eo.order_id || '|' || eg.name || '|' ||                                                              "+
			"               eddr.other_field_value || '|' || eozd.county_id || '|' ||                                             "+
			"               decode(eoe.order_from,                                                                                "+
			"                       '10071',                                                                                      "+
			"                       '00000004',                                                                                   "+
			"                       '10078',                                                                                      "+
			"                       '00000006',                                                                                   "+
			"                       '10082',                                                                                      "+
			"                       '00000007',                                                                                   "+
			"                       '10072',                                                                                      "+
			"                       '00000008',                                                                                   "+
			"                       '10083',                                                                                      "+
			"                       '00000005','10093','00000012') || '|' || '开户' || '|' || '0' || '|' ||                                          "+
			"               replace(NVL(eoel.payplatformorderid, '|'), ',', '|') || '|' || '1' || '|' ||                          "+
			"               eoie.phone_num || '|' || '在线支付' || '|' ||                                                         "+
			"               decode(epl.pay_method,                                                                                "+
			"                       '40',                                                                                         "+
			"                       '001',                                                                                        "+
			"                       '41',                                                                                         "+
			"                       '002',                                                                                        "+
			"                       '42',                                                                                         "+
			"                       '003',                                                                                        "+
			"                       '43',                                                                                         "+
			"                       '004',                                                                                        "+
			"                       '44',                                                                                         "+
			"                       '005',                                                                                        "+
			"                       '1',                                                                                          "+
			"                       '000',                                                                                        "+
			"                       '10',                                                                                         "+
			"                       '000',                                                                                        "+
			"                       epl.pay_method) || '|' || eo.paymoney * 1000 || '|' ||                                        "+
			"               eoel.order_origfee * 1000 || '|' || '' || '|' || '' || '|' ||                                         "+
			"               decode(eoe.flow_trace_id, 'L', '1', 'J', '1', '5') || '|' ||                                          "+
			"               to_char(eo.create_time, 'YYYYMMDDHH24MISS') || '|' ||                                                 "+
			"               to_char(eo.pay_time, 'YYYYMMDD') || '|' || eoel.out_office || '|' ||                                  "+
			"               decode(eoel.out_operator,                                                                             "+
			"                       null,                                                                                         "+
			"                       decode(eoe.order_from,                                                                        "+
			"                              '10071',                                                                               "+
			"                              (select a.pname                                                                        "+
			"                                 from es_dc_public_ext a                                                             "+
			"                                where a.stype = 'DIC_BSS_GONGHAO'                                                    "+
			"                                  and a.pkey = eoe.order_city_code),                                                 "+
			"                              '10078',                                                                               "+
			"                              (select a.pname                                                                        "+
			"                                 from es_dc_public_ext a                                                             "+
			"                                where a.stype = 'DIC_BSS_GONGHAO_NEW'                                                "+
			"                                  and a.pkey = eoe.order_city_code)),                                                "+
			"                       eoel.out_operator) || '|' ||                                                                  "+
			"               substr(nvl(eoel.order_charge_id, eoie.bssorderid), 0, 8) || '|' ||                                    "+
			"               eoie.bssorderid || '|' ||                                                                             "+
			"               nvl(eoel.order_charge_id, eoie.bssorderid) || '|' ||                                                  "+
			"               decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')) as info                                                  "+
			" FROM es_order eo                                                                                                    "+
			" LEFT JOIN es_order_zhwq_adsl eozd                                                                                   "+
			"   ON eo.order_id = eozd.order_id                                                                                    "+
			" LEFT JOIN es_order_ext eoe                                                                                          "+
			"   ON eo.order_id = eoe.order_id                                                                                     "+
			" left join es_order_items_ext eoie                                                                                   "+
			"   on eoie.order_id = eo.order_id                                                                                    "+
			" LEFT JOIN es_order_extvtl eoel                                                                                      "+
			"   ON eo.order_id = eoel.order_id                                                                                    "+
			" LEFT JOIN es_dc_public_dict_relation eddr                                                                           "+
			"   ON eoe.order_city_code = eddr.field_value                                                                         "+
			" left join es_goods eg                                                                                               "+
			"   on eg.goods_id = eo.goods_id                                                                                      "+
			" LEFT JOIN es_payment_logs epl                                                                                       "+
			"   ON epl.order_id = eo.order_id                                                                                     "+
			"WHERE trunc(eo.create_time, 'dd') >= trunc(sysdate - 1, 'dd')                                                        "+
			"  and trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')                                                             "+
			"  AND eddr.stype = 'bss_area_code'                                                                                      "+
			"  and eo.pay_status = '1'                                                                                            "+
			"  AND eoel.payplatformorderid IS NOT NULL                                                                            "+
			"  AND epl.pay_method IN                                                                                              "+
			"      ('40', '41', '42', '43', '44', '1', '10', '51', '52')                                                          "+
			"  AND (eoel.refundplatformorderid IS NULL or                                                                         "+
			"       (eoel.refundplatformorderid IS NOT NULL and                                                                   "+
			"       TRUNC(eoe.refund_time, 'dd') = TRUNC(eo.create_time, 'dd')))                                                  "+
			"  AND regexp_substr(eoel.payplatformorderid, '[^|]', 1, 1, 'i') != 'null'                                            "+
			"  AND eo.paymoney > 0                                                                                                "+
			"  and eoe.exception_desc is null    "+
			"UNION ALL                                                                                                             "+
			"SELECT eo.order_id || '|' || eg.name || '|' || eddr.other_field_value || '|' ||                                       "+
			"      eozd.county_id || '|' || decode(eoe.order_from,                                                                "+
			"                                      '10071',                                                                       "+
			"                                      '00000004',                                                                    "+
			"                                      '10078',                                                                       "+
			"                                      '00000006',                                                                    "+
			"                                      '10082',                                                                       "+
			"                                      '00000007',                                                                    "+
			"                                      '10072',                                                                       "+
			"                                      '00000008',                                                                    "+
			"                                      '10083',                                                                       "+
			"                                      '00000005','10093','00000012') || '|' || '开户' || '|' || CASE                                    "+
			"        WHEN trunc(eoe.refund_time, 'dd') = TRUNC(eo.create_time, 'dd') THEN                                         "+
			"         '1'                                                                                                         "+
			"        ELSE                                                                                                         "+
			"         '2'                                                                                                         "+
			"      END || '|' ||                                                                                                  "+
			"      eoel.refundplatformorderid  || '|' ||                                                                          "+
			"      substr(eoel.payplatformorderid,(instr(eoel.payplatformorderid, '|', 2, 1))+1)                            "+ 
			" || '|' || '1' || '|' || eoie.phone_num || '|' ||                                    							"+
			"      '在线支付' || '|' || decode(epl.pay_method,                                                                       "+
			"                              '40',                                                                                  "+
			"                              '001',                                                                                 "+
			"                              '41',                                                                                  "+
			"                              '002',                                                                                 "+
			"                              '42',                                                                                  "+
			"                              '003',                                                                                 "+
			"                              '43',                                                                                  "+
			"                              '004',                                                                                 "+
			"                              '44',                                                                                  "+
			"                              '005',                                                                                 "+
			"                              '1',                                                                                   "+
			"                              '000',                                                                                 "+
			"                              '10',                                                                                  "+
			"                              '000',                                                                                 "+
			"                              epl.pay_method) || '|' || eo.paymoney * 1000 || '|' ||                                 "+
			"      eoel.order_origfee * 1000 || '|' ||                                                                            "+
			"      replace(NVL(eoel.payplatformorderid, '|'), ',', '|') || '|' ||                                                 "+
			"      decode(eoe.flow_trace_id, 'L', '1', 'J', '1', '5') || '|' ||                                                   "+
			"      to_char(eoe.refund_time, 'YYYYMMDDHH24MISS') || '|' ||                                                         "+
			"      to_char(eoe.refund_time, 'YYYYMMDD') || '|' || eoel.out_office || '|' ||                                       "+
			"      decode(eoel.out_operator,                                                                                      "+
			"             null,                                                                                                   "+
			"             decode(eoe.order_from,                                                                                  "+
			"                    '10071',                                                                                         "+
			"                    (select a.pname                                                                                  "+
			"                       from es_dc_public_ext a                                                                       "+
			"                      where a.stype = 'DIC_BSS_GONGHAO'                                                              "+
			"                        and a.pkey = eoe.order_city_code),                                                           "+
			"                    '10078',                                                                                         "+
			"                    (select a.pname                                                                                  "+
			"                       from es_dc_public_ext a                                                                       "+
			"                      where a.stype = 'DIC_BSS_GONGHAO_NEW'                                                          "+
			"                        and a.pkey = eoe.order_city_code)),                                                          "+
			"             eoel.out_operator) || '|' ||                                                                            "+
			"      to_char(eoe.refund_time, 'YYYYMMDD') || '|' || eoie.bssorderid || '|' ||                                       "+
			"      nvl(eoel.order_charge_id, eoie.bssorderid) || '|' ||                                                           "+
			"      decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')                                                                    "+
			" FROM es_order eo                                                                                                    "+
			" LEFT JOIN es_order_zhwq_adsl eozd                                                                                   "+
			"   ON eo.order_id = eozd.order_id                                                                                    "+
			" LEFT JOIN es_order_ext eoe                                                                                          "+
			"   ON eo.order_id = eoe.order_id                                                                                     "+
			" left join es_order_items_ext eoie                                                                                   "+
			"   on eoie.order_id = eo.order_id                                                                                    "+
			" LEFT JOIN es_order_extvtl eoel                                                                                      "+
			"   ON eo.order_id = eoel.order_id                                                                                    "+
			" LEFT JOIN es_dc_public_dict_relation eddr                                                                           "+
			"   ON eoe.order_city_code = eddr.field_value                                                                         "+
			" left join es_goods eg                                                                                               "+
			"   on eg.goods_id = eo.goods_id                                                                                      "+
			" LEFT JOIN es_payment_logs epl                                                                                       "+
			"   ON epl.order_id = eo.order_id                                                                                     "+
			"WHERE ((trunc(eo.create_time, 'dd') >= trunc(sysdate - 1, 'dd') and                                                  "+
			"      trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')) or                                                         "+
			"      (trunc(eoe.refund_time, 'dd') >= trunc(sysdate - 1, 'dd') and                                                  "+
			"      trunc(eoe.refund_time, 'dd') < trunc(sysdate, 'dd')))                                                          "+
			"  AND eddr.stype = 'bss_area_code'                                                                                   "+
			"  and eo.pay_status = '1'                                                                                            "+
			"  AND epl.pay_method IN                                                                                              "+
			"      ('40', '41', '42', '43', '44', '1', '10', '51', '52')                                                          "+
			"  AND eoel.refundplatformorderid IS NOT NULL                                                                         "+
			"  AND eo.paymoney > 0                                                                                                "+
			"  and eoe.exception_desc is null    "+
			"UNION ALL                                                                                                             "+
			"SELECT eo.order_id || '|' || eg.name || '|' || eddr.other_field_value || '|' ||                                       "+
			"      eozd.county_id || '|' || decode(eoe.order_from,                                                                "+
			"                                      '10071',                                                                       "+
			"                                      '00000004',                                                                    "+
			"                                      '10078',                                                                       "+
			"                                      '00000006',                                                                    "+
			"                                      '10082',                                                                       "+
			"                                      '00000007',                                                                    "+
			"                                      '10072',                                                                       "+
			"                                      '00000008',                                                                    "+
			"                                      '10083',                                                                       "+
			"                                      '00000005','10093','00000012') || '|' || '开户' || '|' || '0' || '|' ||                           "+
			"      replace(NVL(eoel.payplatformorderid, '|'), ',', '|') || '|' || '1' || '|' ||                                   "+
			"      eoie.phone_num || '|' || '在线支付' || '|' ||                                                                  "+
			"      decode(epl.pay_method,                                                                                         "+
			"             '40',                                                                                                   "+
			"             '001',                                                                                                  "+
			"             '41',                                                                                                   "+
			"             '002',                                                                                                  "+
			"             '42',                                                                                                   "+
			"             '003',                                                                                                  "+
			"             '43',                                                                                                   "+
			"             '004',                                                                                                  "+
			"             '44',                                                                                                   "+
			"             '005',                                                                                                  "+
			"             '1',                                                                                                    "+
			"             '000',                                                                                                  "+
			"             '10',                                                                                                   "+
			"             '000',                                                                                                  "+
			"             epl.pay_method) || '|' || eo.paymoney * 1000 || '|' ||                                                  "+
			"      eoel.order_origfee * 1000 || '|' || replace(NVL('', '|'), ',', '|') || '|' || CASE                             "+
			"        WHEN eoel.refundplatformorderid IS NULL THEN                                                                 "+
			"         '3'                                                                                                         "+
			"        ELSE                                                                                                         "+
			"         '6'                                                                                                         "+
			"      END || '|' || to_char(eo.create_time, 'YYYYMMDDHH24MISS') || '|' ||                                            "+
			"      to_char(eo.pay_time, 'YYYYMMDD') || '|' || eoel.out_office || '|' ||                                           "+
			"      decode(eoel.out_operator,                                                                                      "+
			"             null,                                                                                                   "+
			"             decode(eoe.order_from,                                                                                  "+
			"                    '10071',                                                                                         "+
			"                    (select a.pname                                                                                  "+
			"                       from es_dc_public_ext a                                                                       "+
			"                      where a.stype = 'DIC_BSS_GONGHAO'                                                              "+
			"                        and a.pkey = eoe.order_city_code),                                                           "+
			"                    '10078',                                                                                         "+
			"                    (select a.pname                                                                                  "+
			"                       from es_dc_public_ext a                                                                       "+
			"                      where a.stype = 'DIC_BSS_GONGHAO_NEW'                                                          "+
			"                        and a.pkey = eoe.order_city_code)),                                                          "+
			"             eoel.out_operator) || '|' ||                                                                            "+
			"      substr(nvl(eoel.order_charge_id, eoie.bssorderid), 0, 8) || '|' ||                                             "+
			"      eoie.bssorderid || '|' || nvl(eoel.order_charge_id, eoie.bssorderid) || '|' ||                                 "+
			"      decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')                                                                    "+
			" FROM es_order eo                                                                                                    "+
			" LEFT JOIN es_order_zhwq_adsl eozd                                                                                   "+
			"   ON eo.order_id = eozd.order_id                                                                                    "+
			" LEFT JOIN es_order_ext eoe                                                                                          "+
			"   ON eo.order_id = eoe.order_id                                                                                     "+
			" left join es_order_items_ext eoie                                                                                   "+
			"   on eoie.order_id = eo.order_id                                                                                    "+
			" LEFT JOIN es_order_extvtl eoel                                                                                      "+
			"   ON eo.order_id = eoel.order_id                                                                                    "+
			" LEFT JOIN es_dc_public_dict_relation eddr                                                                           "+
			"   ON eoe.order_city_code = eddr.field_value                                                                         "+
			" left join es_goods eg                                                                                               "+
			"   on eg.goods_id = eo.goods_id                                                                                      "+
			" LEFT JOIN es_payment_logs epl                                                                                       "+
			"   ON epl.order_id = eo.order_id                                                                                     "+
			"WHERE trunc(eo.create_time, 'dd') >= trunc(sysdate - 1, 'dd')                                                        "+
			"  and trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')                                                             "+
			"  AND eddr.stype = 'bss_area_code'                                                                                   "+
			"  and eo.pay_status = '1'                                                                                            "+
			"  AND epl.pay_method IN                                                                                              "+
			"      ('40', '41', '42', '43', '44', '1', '10', '51', '52')                                                          "+
			"  AND eo.paymoney > 0                                                                                                "+
			"  AND eoe.exception_desc IS NOT NULL                                                                                ";

	public void upload() {
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "upload")) {
  			return;
		}
		PrintWriter writer = null;
		logger.info("------------------------begin---------------------------");
		String time = "";
		String url = "";
		//Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		//Matcher m = null;
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String num = cacheUtil.getConfigInfo("ORDER_DETAILS_NUM");
		
		//获取稽核文件上传配置
		String url1 = cacheUtil.getConfigInfo("AUDIT_PATH");
		String idAndPass = cacheUtil.getConfigInfo("AUDIT_ID_PASSWORD");
		String ip = cacheUtil.getConfigInfo("AUDIT_IP");
		String port = cacheUtil.getConfigInfo("AUDIT_PORT");
		String local_url = cacheUtil.getConfigInfo("ORDER_AUDIT_LOCAL");
		try{
			SimpleDateFormat new_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String[] ids = idAndPass.split("/");
			String userName  = "";
			String passWord = "";
			if(ids != null && ids.length >= 2) {
				userName = ids[0];
				passWord = ids[1];
			}
			
			logger.info("---------------------------------数据查询开始时间："+new_time.format(new java.util.Date())+"--------------------------------");
			List queueList = support.queryForList(AUDIT_SQL);
			logger.info("---------------------------------数据查询结束时间："+new_time.format(new java.util.Date())+"--------------------------------");
			Map<String,String> login_map = new HashMap<String,String>();
			login_map.put("ip", ip);
			login_map.put("port", port);
			login_map.put("userName", userName);
			login_map.put("passWord", passWord);
			FTPUtil.setArg(login_map);
			FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
			/*Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMdd");*/
			
			time=DateUtil.getTime3();
			url = url1+time;
			File f1 = new File(local_url+"/"+time); 
			if(f1.exists()) delFolder(local_url+"/"+time); f1.delete();
			f1.mkdirs();

			//FTPUtil.closeConnect();
			//boolean is_del = FTPUtil.isDirExist(url);
			//FTPUtil.closeConnect();
			//FTPUtil.makeDirectoryToPath(time,url1,is_del);
			//FTPUtil.closeConnect();
			

			/*List new_list = new ArrayList();
			
			for(int y=0;y<80;y++){
				//Map map = (Map)queueList.get(0);
				Map map = new HashMap();
				map.put("info", "ZBWO170681613030000103|20170417002|2017/04/1716:13:01|2017/04/1703:16:02||||18557500035|X|01|0|0|90063345|任全康|15658119769|杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号");
				new_list.add(map);
			}
			queueList = new_list;*/
			int j = 0;
			String str ="";
			logger.info("-----------------------生成REQfor循环开始时间："+new_time.format(new java.util.Date())+"--------------------------");

			
			if(queueList == null || queueList.size() == 0 ) {
				File f = new File(local_url+"/"+time+"/Order_Audit_"+time+"_"+"0001"+".REQ"); 
				if(f.exists()) f.delete();
				writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/"+time+"/Order_Audit_"+time+"_"+"0001"+".REQ"), "GBK"));
				//FTPUtil.uploadFile("".getBytes("GBK"), url1, "Order_Audit_"+time+"_"+"0001"+".REQ");
			}
			

			for(int i=1;i<=queueList.size();i++){
				if(i%Integer.parseInt(num)==1){
					j++;
					str =  "";
					logger.info("----------------------第"+j+"个文件内容开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
//					File f = new File("D:\\WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".REQ","GBK"); 
//					if(f.exists()) f.delete();
//					writer = new PrintWriter("D:\\WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".REQ","GBK");
					File f = new File(local_url+"/"+time+"/Order_Audit_"+time+"_"+String.format("%04d", j)+".REQ"); 
					if(f.exists()) f.delete();
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/"+time+"/Order_Audit_"+time+"_"+String.format("%04d", j)+".REQ"), "GBK"));
					
				}
				String new_str = Const.getStrValue((Map)queueList.get(i-1), "info");
				/*m = p.matcher(new_str);
				new_str = m.replaceAll("");*/
				str += new_str+"\n";
				//writer.print(Const.getStrValue((Map)queueList.get(i-1), "info")+"\n");
				if(i%Integer.parseInt(num)==0||i==queueList.size()){
					/*logger.info("----------------------第"+j+"个文件内容结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					logger.info("----------------------第"+j+"个文件生成开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
					FTPUtil.uploadFile(str.getBytes("GBK"), url1, "Order_Audit_"+time+"_"+String.format("%04d", j)+".REQ");
					logger.info("----------------------第"+j+"个文件生成结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					*///FTPUtil.closeConnect();
					writer.print(str);
					writer.close();
				}
				
			}
			logger.info("-----------------------生成REQfor循环结束时间："+new_time.format(new java.util.Date())+"--------------------------");  		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			url = url1+"/"+time;
			//FTPUtil.closeConnect();
			boolean is_del = FTPUtil.isDirExist(url);
			FTPUtil.closeConnect();
			FTPUtil.makeDirectoryToPath(time,url1,is_del);
			//FTPUtil.closeConnect();
			FTPUtil.uploadManyFile(local_url+"/"+time,url1);
			FTPUtil.closeConnect();// 关闭连接 
		}
		
		logger.info("------------------------end---------------------------");
	}
	
	private String getCfgSql(String cfg_pre_fix) throws Exception{
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		StringBuilder builder = new StringBuilder();
		
		for(int i=1;i<=10;i++){
			String key = cfg_pre_fix+i;
			
			String str = cacheUtil.getConfigInfo(key);
			
			if(org.apache.commons.lang.StringUtils.isBlank(str))
				continue;
			else
				builder.append(str);
		}
		
		return builder.toString();
	}
	
	public void new_upload() {
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "new_upload")) {
  			return;
		}
		PrintWriter writer = null;

		logger.info("------------------------begin---------------------------");

		String time = "";
		String url = "";
		//Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		//Matcher m = null;
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String num = cacheUtil.getConfigInfo("ORDER_DETAILS_NUM");
		
		//获取稽核文件上传配置
		String url1 = cacheUtil.getConfigInfo("AUDIT_PATH");
		String idAndPass = cacheUtil.getConfigInfo("AUDIT_ID_PASSWORD");
		String ip = cacheUtil.getConfigInfo("AUDIT_IP");
		String port = cacheUtil.getConfigInfo("AUDIT_PORT");
		String local_url = cacheUtil.getConfigInfo("ORDER_AUDIT_LOCAL");
//		String local_url = "D://";
		try{
			SimpleDateFormat new_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String[] ids = idAndPass.split("/");
			String userName  = "";
			String passWord = "";
			if(ids != null && ids.length >= 2) {
				userName = ids[0];
				passWord = ids[1];
			}
			
			logger.info("---------------------------------数据查询开始时间："+new_time.format(new java.util.Date())+"--------------------------------");
			//稽核SQL配置化
			String sql = this.getCfgSql("ORDER_AUDIT_NEW_SQL_");
			
			//未找到配置，取静态变量的SQL
			if(StringUtils.isBlank(sql))
				sql = AUDIT_SQL_4;
			
			logger.error("AuditFileTimer query sql:"+sql);
			
			List queueList = support.queryForList(sql);
			
			logger.info("---------------------------------数据查询结束时间："+new_time.format(new java.util.Date())+"--------------------------------");
			Map<String,String> login_map = new HashMap<String,String>();
			login_map.put("ip", ip);
			login_map.put("port", port);
			login_map.put("userName", userName);
			login_map.put("passWord", passWord);
			FTPUtil.setArg(login_map);
			FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
			/*Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMdd");*/
			
			time=DateUtil.getTime3();
			url = url1+time;
			File f1 = new File(local_url+"/new"+time); 
			if(f1.exists()) delFolder(local_url+"/new"+time); f1.delete();
			f1.mkdirs();

			//FTPUtil.closeConnect();
			//boolean is_del = FTPUtil.isDirExist(url);
			//FTPUtil.closeConnect();
			//FTPUtil.makeDirectoryToPath(time,url1,is_del);
			//FTPUtil.closeConnect();
			

//			List new_list = new ArrayList();
//			
//			for(int y=0;y<40;y++){
//				//Map map = (Map)queueList.get(0);
//				Map map = new HashMap();
//				map.put("info", "ZBWO170681613030000103|20170417002|2017/04/1716:13:01|2017/04/1703:16:02||||18557500035|X|01|0|0|90063345|任全康|15658119769|杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号");
//				new_list.add(map);
//			}
//			queueList = new_list;
			int j = 0;
			String str ="";
			logger.info("-----------------------生成REQfor循环开始时间："+new_time.format(new java.util.Date())+"--------------------------");

			
			if(queueList == null || queueList.size() == 0 ) {
				File f = new File(local_url+"/new"+time+"/Order_Audit_New_"+time+"_"+"0001"+".REQ"); 
				if(f.exists()) f.delete();
				writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/new"+time+"/Order_Audit_New_"+time+"_"+"0001"+".REQ"), "GBK"));
				//FTPUtil.uploadFile("".getBytes("GBK"), url1, "Order_Audit_"+time+"_"+"0001"+".REQ");
			}
			int otherNum = queueList.size();
			for(int i=1;i<=queueList.size();i++){
				if(i%Integer.parseInt(num)==1){
					j++;
					//查出的数据量大于num,分多个文件,文件头
					if(otherNum >= Integer.parseInt(num) ) {
						str = String.format("%010d", Integer.parseInt(num))+"\n";
						otherNum = otherNum-Integer.parseInt(num);
					}else {
						str = String.format("%010d", otherNum)+"\n";
					}
					logger.info("----------------------第"+j+"个文件内容开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
//					File f = new File("D:\\Order_Audit_New"+time+String.format("%02d", j)+".REQ"); 
//					if(f.exists()) f.delete();
//					writer = new PrintWriter("D:\\Order_Audit_New"+time+String.format("%02d", j)+".REQ");
					File f = new File(local_url+"/new"+time+"/Order_Audit_New_"+time+"_"+String.format("%04d", j)+".REQ"); 
					if(f.exists()) f.delete();
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/new"+time+"/Order_Audit_New_"+time+"_"+String.format("%04d", j)+".REQ"), "GBK"));
					
				}
				String new_str = Const.getStrValue((Map)queueList.get(i-1), "info");
				/*m = p.matcher(new_str);
				new_str = m.replaceAll("");*/
				str += new_str+"\n";
				//writer.print(Const.getStrValue((Map)queueList.get(i-1), "info")+"\n");
				if(i%Integer.parseInt(num)==0||i==queueList.size()){
					/*logger.info("----------------------第"+j+"个文件内容结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					logger.info("----------------------第"+j+"个文件生成开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
					FTPUtil.uploadFile(str.getBytes("GBK"), url1, "Order_Audit_"+time+"_"+String.format("%04d", j)+".REQ");
					logger.info("----------------------第"+j+"个文件生成结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					*///FTPUtil.closeConnect();
					writer.print(str);
					writer.close();
				}
			}
			logger.info("-----------------------生成REQfor循环结束时间："+new_time.format(new java.util.Date())+"--------------------------");  		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			url = url1+"/"+time;
			//FTPUtil.closeConnect();
			boolean is_del = FTPUtil.isDirExist(url);
			FTPUtil.closeConnect();
			FTPUtil.makeDirectoryToPath(time,url1,is_del);
			//FTPUtil.closeConnect();
			FTPUtil.uploadManyFile(local_url+"/new"+time,url1);
			FTPUtil.closeConnect();// 关闭连接 
		}
		
		logger.info("------------------------end---------------------------");
	}
	
	//删除文件夹
			//param folderPath 文件夹完整绝对路径

			     public static void delFolder(String folderPath) {
			     try {
			        delAllFile(folderPath); //删除完里面所有内容
			        String filePath = folderPath;
			        filePath = filePath.toString();
			        java.io.File myFilePath = new java.io.File(filePath);
			        myFilePath.delete(); //删除空文件夹
			     } catch (Exception e) {
			       e.printStackTrace(); 
			     }
			}

			//删除指定文件夹下所有文件
			//param path 文件夹完整绝对路径
			   public static boolean delAllFile(String path) {
			       boolean flag = false;
			       File file = new File(path);
			       if (!file.exists()) {
			         return flag;
			       }
			       if (!file.isDirectory()) {
			         return flag;
			       }
			       String[] tempList = file.list();
			       File temp = null;
			       for (int i = 0; i < tempList.length; i++) {
			          if (path.endsWith(File.separator)) {
			             temp = new File(path + tempList[i]);
			          } else {
			              temp = new File(path + File.separator + tempList[i]);
			          }
			          if (temp.isFile()) {
			             temp.delete();
			          }
			          if (temp.isDirectory()) {
			             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
			             delFolder(path + "/" + tempList[i]);//再删除空文件夹
			             flag = true;
			          }
			       }
			       return flag;
			     }
    
	public static void main(String[] args) {
		AuditFileTimer a = new AuditFileTimer();
		a.new_upload();
	}              
}
