package com.ztesoft.net.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafKaManager {

	public void get(){
		

        try{	
        			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        			String kafkagettopic = cacheUtil.getConfigInfo("kafkagettopic");
        			Properties properties = new Properties();
					String kafkazookeeperconnect = cacheUtil.getConfigInfo("kafkazookeeperconnect");
					String kafkagroupid = cacheUtil.getConfigInfo("kafkagroupid");
					String kafkarebalancemaxretries = cacheUtil.getConfigInfo("kafkarebalancemaxretries");
					String kafkarebalancebackoffms = cacheUtil.getConfigInfo("kafkarebalancebackoffms");
					String kafkazookeepersessiontimeoutms = cacheUtil.getConfigInfo("kafkazookeepersessiontimeoutms");
					String kafkazookeepersynctimems = cacheUtil.getConfigInfo("kafkazookeepersynctimems");
					String kafkabootstrapservers = cacheUtil.getConfigInfo("kafkabootstrapservers");
					String kafkapollnum = cacheUtil.getConfigInfo("kafkapollnum");
					String kafkakeydeserializer = cacheUtil.getConfigInfo("kafkakeydeserializer");
					String kafkvaluedeserializer = cacheUtil.getConfigInfo("kafkvaluedeserializer");
					properties.put("zookeeper.connect", kafkazookeeperconnect);
			        properties.put("group.id", kafkagroupid);
			        properties.put("rebalance.max.retries", kafkarebalancemaxretries);
			        properties.put("rebalance.backoff.ms", kafkarebalancebackoffms);
			        properties.put("zookeeper.session.timeout.ms", kafkazookeepersessiontimeoutms);
			        properties.put("zookeeper.sync.time.ms", kafkazookeepersynctimems);
			        properties.put("bootstrap.servers", kafkabootstrapservers);
			        properties.put("enable.auto.commit", true);// 显示设置偏移量自动提交
			        properties.put("auto.commit.interval.ms", Integer.parseInt(kafkapollnum));// 设置偏移量提交时间间隔
			        properties.put("key.deserializer", kafkakeydeserializer);  
				    properties.put("value.deserializer", kafkvaluedeserializer);
				  //1.创建消费者
				      KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
				      
				      //2.订阅Topic
				      //创建一个只包含单个元素的列表，Topic的名字叫作customerCountries
				      consumer.subscribe(Collections.singletonList(kafkagettopic));
				      //支持正则表达式，订阅所有与test相关的Topic
				      //consumer.subscribe("test.*");
				      Map<String,String> custCountryMap = new HashMap();
				      //3.轮询
				      //消息轮询是消费者的核心API，通过一个简单的轮询向服务器请求数据，一旦消费者订阅了Topic，轮询就会处理所欲的细节，包括群组协调、partition再均衡、发送心跳
				      //以及获取数据，开发者只要处理从partition返回的数据即可。
				      int max_num = Integer.parseInt(cacheUtil.getConfigInfo("kafka_max_num")==null?"0":cacheUtil.getConfigInfo("kafka_max_num"));
			          int begin_num = 0;
				      try {
				          while (true) {//消费者是一个长期运行的程序，通过持续轮询向Kafka请求数据。在其他线程中调用consumer.wakeup()可以退出循环
				              //在100ms内等待Kafka的broker返回数据.超市参数指定poll在多久之后可以返回，不管有没有可用的数据都要返回
				        	  begin_num = begin_num+1 ;
				              ConsumerRecords<String, String> records = consumer.poll(Integer.parseInt(kafkapollnum));
				              for (ConsumerRecord<String, String> record : records) {
				                  //统计各个地区的客户数量，即模拟对消息的处理
				            	  //JSONObject jsonObj = JSONObject.fromObject(record.value());
				            	  //System.out.println(record.value());
				            	  JSONObject jsonObj = JSONObject.fromObject(record.value());
			                        Map<String,String> insertMap = jsonObj2Map(jsonObj,"DIC_KAFKA_INSERT");
			                        insertMap.put("source_from", "ECS");
			                        insertMap.put("syn_mode", "kafka");
			                        insertMap.put("del_status", "1");
			                        insertMap.put("deal_status", "WCL");
			                        insertMap.put("deal_num", "0");
			                        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			                        String keys = "";
			                        String values = "";
			                        for(String key:insertMap.keySet()){
			                            if(!StringUtil.isEmpty(insertMap.get(key)+"")&&!"null".equals(insertMap.get(key)+"")){
			                            	keys += key+",";
			                            	values += "'"+insertMap.get(key)+"',";
			                            }
			                        }
			                        keys = keys.substring(0, keys.lastIndexOf(","));
			                        values = values.substring(0, values.lastIndexOf(","));
			                        String insert_sql = " insert into es_kdyyd_order ("+keys+") values ("+values+") ";
			                        System.out.println(insert_sql);
			                        baseDaoSupport.execute(insert_sql, null);
				              }
				              if(begin_num>=max_num){
			                  	return;
			                  }
				          }
				      } finally {
				        //退出应用程序前使用close方法关闭消费者，网络连接和socket也会随之关闭，并立即触发一次再均衡
				    	  if(consumer!=null){
		            		  consumer.close();
		            	  }
				      }
			        /*ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
        			String topic = kafkagettopic;
		            Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		            topicCountMap.put(topic, new Integer(3));
		            Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);

		            for (final KafkaStream<byte[], byte[]> stream : messageStreams.get(topic)) {

		                ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		                while (iterator.hasNext()) {
		                    String message = null;
		                    try {
		                        message = new String(iterator.next().message(), "utf-8");
		                        System.out.println(message);
		                        JSONObject jsonObj = JSONObject.fromObject(message);
		                        Map<String,String> insertMap = jsonObj2Map(jsonObj,"DIC_KAFKA_INSERT");
		                        insertMap.put("source_from", "ECS");
		                        insertMap.put("syn_mode", "kafka");
		                        insertMap.put("del_status", "1");
		                        insertMap.put("deal_status", "WCL");
		                        insertMap.put("deal_num", "0");
		                        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		                        String keys = "";
		                        String values = "";
		                        for(String key:insertMap.keySet()){
		                            if(!StringUtil.isEmpty(insertMap.get(key)+"")&&!"null".equals(insertMap.get(key)+"")){
		                            	keys += key+",";
		                            	values += "'"+insertMap.get(key)+"',";
		                            }
		                        }
		                        keys = keys.substring(0, keys.lastIndexOf(","));
		                        values = values.substring(0, values.lastIndexOf(","));
		                        String insert_sql = " insert into es_kdyyd_order ("+keys+") values ("+values+") ";
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }finally{
		                    	consumer.shutdown();
		                    }
		                   
		                }
		                //consumer.shutdown();
		            }*/
        	
        	/*String message = "{\"APPOINTMENT_DATE\":\"2018-09-20 00:00:00\",\"APPOINTMENT_DATE_SEGMENT\":\"0\",\"BROADBAND_PRICE\":99000,\"BROAD_PRODUCT\":\"20M包一年\",\"CELL_NAME\":\"测测额\",\"CELL_TYPE\":\"03\",\"CHANNAL_FROM\":\"web\",\"CITY_CODE\":\"360\",\"CITY_NAME\":\"杭州\",\"CREATE_TIME\":\"2018-09-05 11:20:19\",\"CUST_NAME\":\"测试\",\"DISTRICT_CODE\":\"320105\",\"DISTRICT_NAME\":\"建邺区\",\"GOODS_ID\":341806252951,\"INSTALL_ADDRESS\":\"江苏南京市建邺区测测额才擦擦擦擦\",\"LINK_PHONE\":\"18651866426\",\"OPE_SYS\":\"2\",\"OPTPACKAGE_ID\":\"100110010;10020201;12121212\",\"OPTPACKAGE_PRICE\":\"88000;77000;0\",\"ORDER_ID\":2418090522889728,\"ORDER_PRICE\":264000,\"PACKAGE_STATE\":\"00\",\"PAY_AMOUT\":264000,\"PAY_STATE\":\"00\",\"PAY_TYPE\":\"03\",\"PRODUCT_ID\":\"10086010\",\"PROVINCE_CODE\":\"36\",\"PROVINCE_NAME\":\"浙江\",\"PSPT_NO\":\"412824196601195553\",\"SPEED\":\"20\",\"TARIFFTYPE\":\"包一年\"}";
        	JSONObject jsonObj = JSONObject.fromObject(message);
            Map<String,String> insertMap = jsonObj2Map(jsonObj,"DIC_KAFKA_INSERT");
            insertMap.put("source_from", "ECS");
            insertMap.put("syn_mode", "kafka");
            insertMap.put("del_status", "1");
            insertMap.put("deal_status", "WCL");
            insertMap.put("deal_num", "0");
            IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
            String keys = "";
            String values = "";
            for(String key:insertMap.keySet()){
                if(!StringUtil.isEmpty(insertMap.get(key)+"")&&!"null".equals(insertMap.get(key)+"")){
                	keys += key+",";
                	values += "'"+insertMap.get(key)+"',";
                }
            }
            keys = keys.substring(0, keys.lastIndexOf(","));
            values = values.substring(0, values.lastIndexOf(","));
            String insert_sql = " insert into es_kdyyd_order ("+keys+") values ("+values+") ";
            baseDaoSupport.execute(insert_sql, null);*/
        }catch(Exception e){
        	e.printStackTrace();
        }
        
	}
	
	
	public void send(Map map){
		Properties props = new Properties();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kafkazookeeperconnect = cacheUtil.getConfigInfo("kafkazookeeperconnect");
		String kafkabootstrapservers = cacheUtil.getConfigInfo("kafkabootstrapservers");
		String kafkakeyserializer = cacheUtil.getConfigInfo("kafkakeyserializer");
		String kafkavalueserializer = cacheUtil.getConfigInfo("kafkavalueserializer");
		String kafkasendtopic = cacheUtil.getConfigInfo("kafkasendtopic");
		/*props.put("bootstrap.servers", "10.191.129.82:19092,10.191.129.83:19092,10.191.129.84:19092");
		props.put("zookeeper.connect", "10.191.129.82:12181,10.191.129.83:12181,10.191.129.84:12181");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		String topic = "36_BroadBandProvinceProducer";*/
		props.put("bootstrap.servers", kafkabootstrapservers);
		props.put("zookeeper.connect", kafkazookeeperconnect);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", kafkakeyserializer);
		props.put("value.serializer", kafkavalueserializer);
		String topic = kafkasendtopic;
		
		String update_sql = " update es_kd_order_status_syn set syn_status=?,syn_num=(syn_num+to_number(?)) where id=? ";
    	String suc_sql = " insert into es_kd_order_status_syn_his "
    			+ " (id,bespeakid,order_id,dealstate,dealtime,syn_status,syn_num,source_from) "
    			+ " (select id,bespeakid,order_id,dealstate,dealtime,'CLCG',syn_num+1,source_from from es_kd_order_status_syn where id=?) ";
    	String del_sql = " delete from es_kd_order_status_syn where id=? ";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List list = getConsts("DIC_ZOPSTATUS2KAFKA");
		Map sendMap;
		if(list.size()<=0){
			 baseDaoSupport.execute(update_sql, new String[]{"CLSB","1",Const.getStrValue(map, "id")});
			return ;
		}
		KafkaProducer<String, String> producer = new KafkaProducer<String,String>(props);
		for (int i = 0; i < list.size(); i++) {
			
			Map statusMap = (Map)list.get(i);
			String pkey = (String)(statusMap.get("pkey")==null?"":statusMap.get("pkey"));
			String pname = (String)(statusMap.get("pname")==null?"":statusMap.get("pname"));
			String status = (String)(map.get("dealState")==null?"":map.get("dealState"));
			if(status.equals(pkey)){
				
				sendMap = new HashMap();
				String qry_sql = " select t.out_tid,(select k.districtcode from es_kdyyd_order_his k where k.bespeakid=t.out_tid) as district_code, "
						       + " (select other_field_value from es_dc_public_dict_relation where stype='city' and field_value=t.order_city_code)as city_code,w.operator_name,w.operator_num from es_order_ext t "
						       + " left join es_order_extvtl v on v.order_id=t.order_id "
						       + " left join (select row_number() OVER( PARTITION BY  b.order_id order by b.create_time desc) as rw,b.* from es_work_order b ) w on w.rw=1 and w.order_id=t.order_id "
						       + " where t.order_id=? ";
				List list1 = baseDaoSupport.queryForList(qry_sql, new String[]{(String)(map.get("order_id")==null?"":map.get("order_id"))});
				if(list1.size()>0){
					try{
					Map map1 = (Map)list1.get(0);
					if("2".equals(status)){
						sendMap.put("OPERATOR_NAME", (String)(map1.get("operator_name")==null?"":map1.get("operator_name")));
						sendMap.put("OPERATOR_TEL", (String)(map1.get("operator_num")==null?"":map1.get("operator_num")));
					}
					Random rand = new Random();
					StringBuffer flag = new StringBuffer();
					for (int j = 0; j < 6; j++) 
					{
						flag.append(rand.nextInt(9) + "");
					}
					String TRANS_ID = flag.toString();
					TRANS_ID = DateUtil.getTime7()+TRANS_ID;
					sendMap.put("TRANS_ID", TRANS_ID);
					sendMap.put("ORDER_ID", (String)(map.get("bespeakId")==null?"":map.get("bespeakId")));
					sendMap.put("PROVINCE_CODE", "36");
					sendMap.put("CITY_CODE", (String)(map1.get("city_code")==null?"":map1.get("city_code")));
					sendMap.put("DISTRICT_CODE", (String)(map1.get("district_code")==null?"":map1.get("district_code")));
					sendMap.put("CHANGE_TYPE", pname);
					if(status.contains("-")){
						String CANCEL_REASON = "";
						if("-2".equals(status)){
							CANCEL_REASON = "04";
						}else if ("-3".equals(status)){
							CANCEL_REASON = "01";
						}else{
							CANCEL_REASON = "05";
						}
						sendMap.put("CANCEL_REASON", CANCEL_REASON);
					}
					JSONObject jsonObj = JSONObject.fromObject(sendMap);
					producer.send(new ProducerRecord<String, String>(topic, jsonObj.toString()));
					baseDaoSupport.execute(suc_sql, new String[]{Const.getStrValue(map, "id")});
					baseDaoSupport.execute(del_sql, new String[]{Const.getStrValue(map, "id")});
					
				}catch(Exception e){
					 baseDaoSupport.execute(update_sql, new String[]{"CLSB","1",Const.getStrValue(map, "id")});
					e.printStackTrace();
				}
				}else{
					baseDaoSupport.execute(update_sql, new String[]{"CLSB","1",Const.getStrValue(map, "id")});
				}
			}
			
		}
			
		producer.close();
	}

	
	public void status(){
		try{
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			Properties properties = new Properties();
			String kafkapollnum = cacheUtil.getConfigInfo("kafkapollnum");
			String kafkakeydeserializer = cacheUtil.getConfigInfo("kafkakeydeserializer");
			String kafkvaluedeserializer = cacheUtil.getConfigInfo("kafkvaluedeserializer");
			String kafkazookeeperconnect = cacheUtil.getConfigInfo("kafkazookeeperconnect");
			String kafkagroupid = cacheUtil.getConfigInfo("kafkastatusgroupid");
			String kafkarebalancemaxretries = cacheUtil.getConfigInfo("kafkarebalancemaxretries");
			String kafkarebalancebackoffms = cacheUtil.getConfigInfo("kafkarebalancebackoffms");
			String kafkazookeepersessiontimeoutms = cacheUtil.getConfigInfo("kafkazookeepersessiontimeoutms");
			String kafkazookeepersynctimems = cacheUtil.getConfigInfo("kafkazookeepersynctimems");
			String kafkabootstrapservers = cacheUtil.getConfigInfo("kafkabootstrapservers");
			properties.put("zookeeper.connect", kafkazookeeperconnect);
	        properties.put("rebalance.max.retries", kafkarebalancemaxretries);
	        properties.put("rebalance.backoff.ms", kafkarebalancebackoffms);
	        properties.put("zookeeper.session.timeout.ms", kafkazookeepersessiontimeoutms);
	        properties.put("zookeeper.sync.time.ms", kafkazookeepersynctimems);
	        properties.put("bootstrap.servers", kafkabootstrapservers);
	        properties.put("enable.auto.commit", true);// 显示设置偏移量自动提交
	        properties.put("auto.commit.interval.ms", Integer.parseInt(kafkapollnum));// 设置偏移量提交时间间隔
	        properties.put("key.deserializer", kafkakeydeserializer);  
		    properties.put("value.deserializer", kafkvaluedeserializer);
	        properties.put("zookeeper.connect", kafkazookeeperconnect);
	        properties.put("group.id", kafkagroupid);
	        String topic = cacheUtil.getConfigInfo("kafkastatustopic");
	        
	      //1.创建消费者
		      KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		      
		      //2.订阅Topic
		      //创建一个只包含单个元素的列表，Topic的名字叫作customerCountries
		      consumer.subscribe(Collections.singletonList(topic));
		      //支持正则表达式，订阅所有与test相关的Topic
		      //consumer.subscribe("test.*");
		      Map<String,String> custCountryMap = new HashMap();
		      //3.轮询
		      //消息轮询是消费者的核心API，通过一个简单的轮询向服务器请求数据，一旦消费者订阅了Topic，轮询就会处理所欲的细节，包括群组协调、partition再均衡、发送心跳
		      //以及获取数据，开发者只要处理从partition返回的数据即可。
		      int max_num = Integer.parseInt(cacheUtil.getConfigInfo("kafka_max_num")==null?"0":cacheUtil.getConfigInfo("kafka_max_num"));
	          int begin_num = 0;
		      try {
		          while (true) {//消费者是一个长期运行的程序，通过持续轮询向Kafka请求数据。在其他线程中调用consumer.wakeup()可以退出循环
		              //在100ms内等待Kafka的broker返回数据.超市参数指定poll在多久之后可以返回，不管有没有可用的数据都要返回
		        	  begin_num = begin_num+1 ;
		        	  //System.out.println(begin_num);
		              ConsumerRecords<String, String> records = consumer.poll(Integer.parseInt(kafkapollnum));
		              for (ConsumerRecord<String, String> record : records) {
		                  //统计各个地区的客户数量，即模拟对消息的处理
		            	  //JSONObject jsonObj = JSONObject.fromObject(record.value());
		            	  //System.out.println(record.value());
		            	  JSONObject jsonObj = JSONObject.fromObject(record.value());
		            	  String out_tid  = jsonObj.getString("ORDER_ID");
		            	  String ORDER_STATE  = jsonObj.getString("ORDER_STATE");
	                        Map<String,String> insertMap = jsonObj2Map(jsonObj,"");
	                        insertMap.put("source_from", "ECS");
	                        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
	                        String keys = "";
	                        String values = "";
	                        for(String key:insertMap.keySet()){
	                            if(!StringUtil.isEmpty(insertMap.get(key)+"")&&!"null".equals(insertMap.get(key)+"")){
	                            	keys += key+",";
	                            	values += "'"+insertMap.get(key)+"',";
	                            }
	                        }
	                        keys = keys.substring(0, keys.lastIndexOf(","));
	                        values = values.substring(0, values.lastIndexOf(","));
	                        String insert_sql = " insert into es_kdyyd_kingcard_status ("+keys+") values ("+values+") ";
	                        baseDaoSupport.execute(insert_sql, null);
	                        OrderTreeBusiRequest order_tree =  CommonDataFactory.getInstance().getOrderTreeByOutId(out_tid);
	                        if(order_tree!=null){
	                        	String order_id = order_tree.getOrder_id();
	                        	CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{"kingcard_status"}, new String[]{ORDER_STATE});
	                        	String king_num = insertMap.get("PRE_NUM");
	                        	if(!StringUtil.isEmpty(king_num)){
	                        		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{"service_num"}, new String[]{king_num});
	                        	}
	                        }
		              }
		              if(begin_num>=max_num){
	                  	return;
	                  }
		          }
		      } finally {
		        //退出应用程序前使用close方法关闭消费者，网络连接和socket也会随之关闭，并立即触发一次再均衡
		    	  if(consumer!=null){
          		  consumer.close();
          	  }
		      }
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private Map<String,String> jsonObj2Map(JSONObject jsonObj,String stype){
		Map<String,String> map = new HashMap<String,String>();
		List<Map> list = getConsts(stype);
		for (int i = 0; i < list.size(); i++) {
			Map map2 = (Map)list.get(i);
			String pkey = Const.getStrValue(map2, "pkey");
			String pname = Const.getStrValue(map2, "pname");
			if(!StringUtil.isEmpty(jsonObj.get(pkey)+"")&&!"null".equals(jsonObj.get(pkey)+"")){
				map.put(pname,jsonObj.get(pkey)+"");
			}
			
			if("ORDER_ID".equals(pkey)){
				map.put("ID",jsonObj.get(pkey)+"");
			}
			jsonObj.remove(pkey);
		}
		Iterator iterator = jsonObj.keys();
		while (iterator.hasNext())
		{
		    String key = (String) iterator.next();
		    if(!StringUtil.isEmpty(jsonObj.get(key)+"")&&!"null".equals(jsonObj.get(key)+"")){
				map.put(key,jsonObj.get(key)+"");
			}
		}
		return map;
	}
	
	private List<Map> getConsts(String key) {
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList(key);
		return list;
	}	
	
	
	public void run(){
		/*Properties props = new Properties();
		props.put("bootstrap.servers", "10.191.129.82:19092,10.191.129.83:19092,10.191.129.84:19092");
		props.put("zookeeper.connect", "10.191.129.82:12181,10.191.129.83:12181,10.191.129.84:12181");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		String topic = "36_BroadBandOldSysProducer";
		KafkaProducer<String, String> producer = new KafkaProducer<String,String>(props);
		producer.send(new ProducerRecord<String, String>(topic, "{\"APPOINTMENT_DATE\":\"2018-09-20 00:00:00\",\"APPOINTMENT_DATE_SEGMENT\":\"0\",\"BROADBAND_PRICE\":99000,\"BROAD_PRODUCT\":\"20M包一年\",\"CELL_NAME\":\"测测额\",\"CELL_TYPE\":\"03\",\"CHANNAL_FROM\":\"web\",\"CITY_CODE\":\"360\",\"CITY_NAME\":\"杭州\",\"CREATE_TIME\":\"2018-09-05 11:20:19\",\"CUST_NAME\":\"测试\",\"DISTRICT_CODE\":\"320105\",\"DISTRICT_NAME\":\"建邺区\",\"GOODS_ID\":341806252951,\"INSTALL_ADDRESS\":\"江苏南京市建邺区测测额才擦擦擦擦\",\"LINK_PHONE\":\"18651866426\",\"OPE_SYS\":\"2\",\"OPTPACKAGE_ID\":\"100110010;10020201;12121212\",\"OPTPACKAGE_PRICE\":\"88000;77000;0\",\"ORDER_ID\":20181010001,\"ORDER_PRICE\":264000,\"PACKAGE_STATE\":\"00\",\"PAY_AMOUT\":264000,\"PAY_STATE\":\"00\",\"PAY_TYPE\":\"03\",\"PRODUCT_ID\":\"10086010\",\"PROVINCE_CODE\":\"36\",\"PROVINCE_NAME\":\"浙江\",\"PSPT_NO\":\"412824196601195553\",\"SPEED\":\"20\",\"TARIFFTYPE\":\"包一年\"}"));
		producer.send(new ProducerRecord<String, String>(topic, "{\"APPOINTMENT_DATE\":\"2018-09-20 00:00:00\",\"APPOINTMENT_DATE_SEGMENT\":\"0\",\"BROADBAND_PRICE\":99000,\"BROAD_PRODUCT\":\"20M包一年\",\"CELL_NAME\":\"测测额\",\"CELL_TYPE\":\"03\",\"CHANNAL_FROM\":\"web\",\"CITY_CODE\":\"360\",\"CITY_NAME\":\"杭州\",\"CREATE_TIME\":\"2018-09-05 11:20:19\",\"CUST_NAME\":\"测试\",\"DISTRICT_CODE\":\"320105\",\"DISTRICT_NAME\":\"建邺区\",\"GOODS_ID\":341806252951,\"INSTALL_ADDRESS\":\"江苏南京市建邺区测测额才擦擦擦擦\",\"LINK_PHONE\":\"18651866426\",\"OPE_SYS\":\"2\",\"OPTPACKAGE_ID\":\"100110010;10020201;12121212\",\"OPTPACKAGE_PRICE\":\"88000;77000;0\",\"ORDER_ID\":20181010002,\"ORDER_PRICE\":264000,\"PACKAGE_STATE\":\"00\",\"PAY_AMOUT\":264000,\"PAY_STATE\":\"00\",\"PAY_TYPE\":\"03\",\"PRODUCT_ID\":\"10086010\",\"PROVINCE_CODE\":\"36\",\"PROVINCE_NAME\":\"浙江\",\"PSPT_NO\":\"412824196601195553\",\"SPEED\":\"20\",\"TARIFFTYPE\":\"包一年\"}"));
		producer.send(new ProducerRecord<String, String>(topic, "{\"APPOINTMENT_DATE\":\"2018-09-20 00:00:00\",\"APPOINTMENT_DATE_SEGMENT\":\"0\",\"BROADBAND_PRICE\":99000,\"BROAD_PRODUCT\":\"20M包一年\",\"CELL_NAME\":\"测测额\",\"CELL_TYPE\":\"03\",\"CHANNAL_FROM\":\"web\",\"CITY_CODE\":\"360\",\"CITY_NAME\":\"杭州\",\"CREATE_TIME\":\"2018-09-05 11:20:19\",\"CUST_NAME\":\"测试\",\"DISTRICT_CODE\":\"320105\",\"DISTRICT_NAME\":\"建邺区\",\"GOODS_ID\":341806252951,\"INSTALL_ADDRESS\":\"江苏南京市建邺区测测额才擦擦擦擦\",\"LINK_PHONE\":\"18651866426\",\"OPE_SYS\":\"2\",\"OPTPACKAGE_ID\":\"100110010;10020201;12121212\",\"OPTPACKAGE_PRICE\":\"88000;77000;0\",\"ORDER_ID\":20181010003,\"ORDER_PRICE\":264000,\"PACKAGE_STATE\":\"00\",\"PAY_AMOUT\":264000,\"PAY_STATE\":\"00\",\"PAY_TYPE\":\"03\",\"PRODUCT_ID\":\"10086010\",\"PROVINCE_CODE\":\"36\",\"PROVINCE_NAME\":\"浙江\",\"PSPT_NO\":\"412824196601195553\",\"SPEED\":\"20\",\"TARIFFTYPE\":\"包一年\"}"));
		producer.close();*/
		
		/*props = new Properties();  
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kafkazookeeperconnect = cacheUtil.getConfigInfo("kafkazookeeperconnect");
		String kafkagroupid = cacheUtil.getConfigInfo("kafkagroupid");
		String kafkarebalancemaxretries = cacheUtil.getConfigInfo("kafkarebalancemaxretries");
		String kafkarebalancebackoffms = cacheUtil.getConfigInfo("kafkarebalancebackoffms");
		String kafkazookeepersessiontimeoutms = cacheUtil.getConfigInfo("kafkazookeepersessiontimeoutms");
		String kafkazookeepersynctimems = cacheUtil.getConfigInfo("kafkazookeepersynctimems");
		String kafkagettopic = cacheUtil.getConfigInfo("kafkagettopic");
		props.put("bootstrap.servers", "10.191.129.82:19092,10.191.129.83:19092,10.191.129.84:19092");
		props.put("zookeeper.connect", kafkazookeeperconnect);
		props.put("group.id", kafkagroupid);
        props.put("rebalance.max.retries", kafkarebalancemaxretries);
        props.put("rebalance.backoff.ms", kafkarebalancebackoffms);
        props.put("zookeeper.session.timeout.ms", kafkazookeepersessiontimeoutms);
        props.put("zookeeper.sync.time.ms", kafkazookeepersynctimems);
        props.put("enable.auto.commit", true);// 显示设置偏移量自动提交
        props.put("auto.commit.interval.ms", 100);// 设置偏移量提交时间间隔
	      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
	      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
	      //1.创建消费者
	      KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
	      
	      //2.订阅Topic
	      //创建一个只包含单个元素的列表，Topic的名字叫作customerCountries
	      consumer.subscribe(Collections.singletonList(kafkagettopic));
	      //支持正则表达式，订阅所有与test相关的Topic
	      //consumer.subscribe("test.*");
	      Map<String,String> custCountryMap = new HashMap();
	      //3.轮询
	      //消息轮询是消费者的核心API，通过一个简单的轮询向服务器请求数据，一旦消费者订阅了Topic，轮询就会处理所欲的细节，包括群组协调、partition再均衡、发送心跳
	      //以及获取数据，开发者只要处理从partition返回的数据即可。
	      int max_num = 100;//Integer.parseInt(cacheUtil.getConfigInfo("kafka_max_num")==null?"0":cacheUtil.getConfigInfo("kafka_max_num"));
          int begin_num = 0;
	      try {
	          while (true) {//消费者是一个长期运行的程序，通过持续轮询向Kafka请求数据。在其他线程中调用consumer.wakeup()可以退出循环
	              //在100ms内等待Kafka的broker返回数据.超市参数指定poll在多久之后可以返回，不管有没有可用的数据都要返回
	        	  begin_num = begin_num+1 ;
              	  System.out.println(begin_num);
	              ConsumerRecords<String, String> records = consumer.poll(100);
	              for (ConsumerRecord<String, String> record : records) {
	                  //统计各个地区的客户数量，即模拟对消息的处理
	            	  System.out.println(record.value());
	            	  JSONObject jsonObj = JSONObject.fromObject(record.value());
                      Map<String,String> insertMap = jsonObj2Map(jsonObj,"DIC_KAFKA_INSERT");
                      insertMap.put("source_from", "ECS");
                      insertMap.put("syn_mode", "kafka");
                      insertMap.put("del_status", "1");
                      insertMap.put("deal_status", "WCL");
                      insertMap.put("deal_num", "0");
                      IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
                      String keys = "";
                      String values = "";
                      for(String key:insertMap.keySet()){
                          if(!StringUtil.isEmpty(insertMap.get(key)+"")&&!"null".equals(insertMap.get(key)+"")){
                          	keys += key+",";
                          	values += "'"+insertMap.get(key)+"',";
                          }
                      }
                      keys = keys.substring(0, keys.lastIndexOf(","));
                      values = values.substring(0, values.lastIndexOf(","));
                      String insert_sql = " insert into es_kdyyd_order ("+keys+") values ("+values+") ";
                      baseDaoSupport.execute(insert_sql, null);
	              }
	              if(begin_num>=max_num){
                  	return;
                  }
	          }
	      } finally {
	        //退出应用程序前使用close方法关闭消费者，网络连接和socket也会随之关闭，并立即触发一次再均衡
	          consumer.close();
	      }*/
		
		String message = "{\"APPOINTMENT_DATE\":\"2018-09-20 00:00:00\",\"APPOINTMENT_DATE_SEGMENT\":\"0\",\"BROADBAND_PRICE\":99000,\"BROAD_PRODUCT\":\"20M包一年\",\"CELL_NAME\":\"测测额\",\"CELL_TYPE\":\"03\",\"CHANNAL_FROM\":\"web\",\"CITY_CODE\":\"360\",\"CITY_NAME\":\"杭州\",\"CREATE_TIME\":\"2018-09-05 11:20:19\",\"CUST_NAME\":\"测试\",\"DISTRICT_CODE\":\"320105\",\"DISTRICT_NAME\":\"建邺区\",\"GOODS_ID\":341806252951,\"INSTALL_ADDRESS\":\"江苏南京市建邺区测测额才擦擦擦擦\",\"LINK_PHONE\":\"18651866426\",\"OPE_SYS\":\"2\",\"OPTPACKAGE_ID\":\"100110010;10020201;12121212\",\"OPTPACKAGE_PRICE\":\"88000;77000;0\",\"ORDER_ID\":201810150000001,\"ORDER_PRICE\":264000,\"PACKAGE_STATE\":\"00\",\"PAY_AMOUT\":264000,\"PAY_STATE\":\"00\",\"PAY_TYPE\":\"03\",\"PRODUCT_ID\":\"10086010\",\"PROVINCE_CODE\":\"36\",\"PROVINCE_NAME\":\"浙江\",\"PSPT_NO\":\"412824196601195553\",\"SPEED\":\"20\",\"TARIFFTYPE\":\"包一年\"}";
    	JSONObject jsonObj = JSONObject.fromObject(message);
        Map<String,String> insertMap = jsonObj2Map(jsonObj,"DIC_KAFKA_INSERT");
        insertMap.put("source_from", "ECS");
        insertMap.put("syn_mode", "kafka");
        insertMap.put("del_status", "1");
        insertMap.put("deal_status", "WCL");
        insertMap.put("deal_num", "0");
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        String keys = "";
        String values = "";
        for(String key:insertMap.keySet()){
            if(!StringUtil.isEmpty(insertMap.get(key)+"")&&!"null".equals(insertMap.get(key)+"")){
            	keys += key+",";
            	values += "'"+insertMap.get(key)+"',";
            }
        }
        keys = keys.substring(0, keys.lastIndexOf(","));
        values = values.substring(0, values.lastIndexOf(","));
        String insert_sql = " insert into es_kdyyd_order ("+keys+") values ("+values+") ";
        baseDaoSupport.execute(insert_sql, null);
	}
	
	public static void main (String[] args){
		System.out.println("057100911641".substring("057100911641".length()-6, "057100911641".length()));
		
	}
}
