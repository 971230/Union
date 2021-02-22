package com.ztesoft.net.mall.core.action.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.AdminUserInf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AlarmLog;
import com.ztesoft.net.mall.core.model.AlarmReceiver;
import com.ztesoft.net.mall.core.model.AlarmTask;
import com.ztesoft.net.mall.core.model.AlarmTemplate;
import com.ztesoft.net.mall.core.model.ElementsObjectAttr;
import com.ztesoft.net.mall.core.service.IAlarmLogManager;
import com.ztesoft.net.mall.core.service.IAlarmReceiverManager;
import com.ztesoft.net.mall.core.service.IAlarmTaskManager;
import com.ztesoft.net.mall.core.service.IAlarmTemplateManager;
import com.ztesoft.net.mall.core.service.ITaskManager;
import com.ztesoft.net.mall.core.utils.AlarmUtils;
import commons.CommonTools;

public class AlarmAction extends WWAction {

	private String alarm_task_id;
	private String alarm_task_name;
	private String text_pattern;
	private String min_money;
	private String max_money;
	private String min_amount;
	private String max_amount;
	private String task_create_time;
	private String task_aead_time;
	private String distributor_id;
	private String phone;
	private String partner_id;
	private String elements_object;
	private String task_content;
	private String alarm_task_type;
	private String user_name;
	private String partner_name;
	private String task_name;
	private String owner_task_id;

	private String templet_name;
	private String templet_type;
	private String alarm_log_id;
	private String status;
	private String id;
	/**
	 * 判断是否选中
	 */
	private String alarm_amount_flag;
	private String alarm_money_flag;
	private String text_pattern_flag;
	private String reconciliation_time_flag;
	private String not_task_deadlines_flag;
	private String not_task_money_flag;
	private String not_task_target_flag;

	private Map taskMap;
	private String flag;

	private String recevier_id;
	private IAlarmTaskManager alarmTaskManager;
	private IAlarmReceiverManager alarmReceiverManager;
	private AlarmTask alarmTask;
	private AlarmReceiver alarmReceiver;
	private List conditionJsonLists;
	private List elementsObjectJsonLists;
	private String ElementsObjectAttr;
	private ITaskManager taskManager;
	private IAlarmTemplateManager AlarmTemplateManager;
	private AlarmTemplate alarmTemplate;
	private IAlarmLogManager alarmLogManager;
	private AlarmLog alarmLog;

	private String template_id;
	private String startDate;
	private String endDate;
	private String recevier_phone_num;
	private String recevier_username;
	private AdminUserInf adminUserServ;

	public String isExits() {

		try {

			if (!StringUtil.isEmpty(alarm_task_name)) {
				try {
					alarm_task_name = URLDecoder.decode(alarm_task_name,
							"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (alarmTaskManager.isAlarmTaskNameExits(alarm_task_name)) {
					json = "{'result':2,'message':'名称已存在！'}";
				} else {
					json = "{'result':0,'message':'可以添加！'}";
				}
			} else if (!StringUtil.isEmpty(templet_name)) {
				try {
					templet_name = URLDecoder.decode(templet_name, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (AlarmTemplateManager.isTempletNameExits(templet_name)) {
					json = "{'result':2,'message':'模板名称已存在！'}";
				} else {
					json = "{'result':0,'message':'可以添加！'}";
				}
			}
		} catch (RuntimeException e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	public String reconciliationAlarm() {
		return "reconciliation_alarm";
	}

	public String addReconciliationAlarm() {
		try {
			String[] arrayElementsObject = elements_object.split(",");

			List<Map> elementsObjectList = new ArrayList();
			if (arrayElementsObject.length > 0) {
				for (int i = 0; i < arrayElementsObject.length; i++) {
					if ("alarm_amount".equals(arrayElementsObject[i].trim())) {
						Map elementsObjectAmountMap = new HashMap();
						elementsObjectAmountMap.put("e_name", "alarm_amount");
						elementsObjectAmountMap.put("c_name", "异常对账数量");
						elementsObjectList.add(elementsObjectAmountMap);
					} else if ("alarm_money".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectMoneyMap = new HashMap();
						elementsObjectMoneyMap.put("e_name", "alarm_money");
						elementsObjectMoneyMap.put("c_name", "异常对账金额");
						elementsObjectList.add(elementsObjectMoneyMap);
					} else if ("reconciliation_time"
							.equals(arrayElementsObject[i].trim())) {
						Map elementsObjectTimeMap = new HashMap();
						elementsObjectTimeMap.put("e_name",
								"reconciliation_time");
						elementsObjectTimeMap.put("c_name", "对账时间");
						elementsObjectList.add(elementsObjectTimeMap);
					} else if ("text_pattern".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectPatternMap = new HashMap();
						elementsObjectPatternMap.put("e_name", "text_pattern");
						elementsObjectPatternMap.put("c_name", "对账文本格式");
						elementsObjectList.add(elementsObjectPatternMap);
					}
				}

			}

			Map amountMap = new HashMap();
			Map moneyMap = new HashMap();
			Map textPatternMap = new HashMap();
			List<Map> conditionList = new ArrayList<Map>();

			amountMap.put("e_name", "alarm_amount");
			amountMap.put("c_name", "对账异常数量");
			amountMap.put("min_value", min_amount);
			amountMap.put("max_value", max_amount);
			amountMap.put("c_value", "");
			amountMap.put("checked", "yes");
			conditionList.add(amountMap);

			moneyMap.put("e_name", "alarm_money");
			moneyMap.put("c_name", "对账异常金额");
			moneyMap.put("min_value", min_money);
			moneyMap.put("max_value", max_money);
			moneyMap.put("c_value", "");
			moneyMap.put("checked", "yes");
			conditionList.add(moneyMap);

			textPatternMap.put("e_name", "text_pattern");
			textPatternMap.put("c_name", "对账文本格式");
			textPatternMap.put("min_value", "");
			textPatternMap.put("max_value", "");
			textPatternMap.put("c_value", text_pattern);
			textPatternMap.put("checked", "yes");
			conditionList.add(textPatternMap);

			JSONArray condition = JSON.parseArray(JSON.toJSONString(conditionList));
			JSONArray elements_object =JSON.parseArray(JSON.toJSONString(elementsObjectList));
			String userId = CommonTools.getUserId();
			String alarm_task_id = this.alarmTaskManager.saveAlarmTask(
					alarm_task_name, alarm_task_type, task_create_time,
					task_aead_time, task_content, userId, DBTUtil.current(),
					elements_object// 添加告警任务表
							.toString(), condition.toString(), owner_task_id);
			this.alarmReceiverManager.saveAlarmReceiver(phone, partner_id,
					partner_name, alarm_task_type, alarm_task_id,
					DBTUtil.current());// 添加告警对象接收人
		} catch (Exception e) {
			e.printStackTrace();
			json = "{'result':1,'message':'添加失败！'}";
			return WWAction.JSON_MESSAGE;
		}
		json = "{'result':0,'message':'添加成功！'}";
		return WWAction.JSON_MESSAGE;
	}

	public String userPhone() {
		this.webpage = this.alarmTaskManager.userPhoneList(user_name, this
				.getPage(), this.getPageSize());
		return "user_phone";
	}

	public String partnerList() {
		this.webpage = this.alarmTaskManager.partnerList(partner_name, this
				.getPage(), this.getPageSize());
		return "partner_list";
	}

	public String list() {
		this.webpage = this.alarmTaskManager.list(alarm_task_name,
				alarm_task_type, recevier_phone_num, recevier_username, this
						.getPage(), this.getPageSize());
		return "list";
	}

	public String search_applic_scene() {
		this.webpage = this.alarmTaskManager.list(alarm_task_name,
				alarm_task_type, recevier_phone_num, recevier_username, this
						.getPage(), this.getPageSize());
		return "search_applic_scene";
	}

	public String detail() {
		try {
			alarmTask = this.alarmTaskManager.findAlarmTaskById(alarm_task_id);
			alarmReceiver = this.alarmReceiverManager
					.findAlarmReceiverByAlarmTaskId(alarmTask
							.getAlarm_task_id());
			if (alarmTask.getOwner_task_id() != null) {
				taskMap = this.taskManager.qryTaskById(alarmTask
						.getOwner_task_id());
			}

			if (taskMap != null) {
				task_name = (String) taskMap.get("task_name");
			}
			if (!StringUtil.isEmpty(alarmTask.getCondition_desc())) {
				conditionJsonLists = AlarmUtils
						.converConditionAttrFormString(alarmTask.getCondition_desc());
			}

			if (!StringUtil.isEmpty(alarmTask.getElements_object())) {
				elementsObjectJsonLists = AlarmUtils
						.converElementsObjectAttrFormString(alarmTask
								.getElements_object());
			}

			if (Consts.ALARM_TASK_TYPE_1.equals(alarm_task_type)) {
				for (int i = 0; i < elementsObjectJsonLists.size(); i++) {
					ElementsObjectAttr elementsObjectAttr = (ElementsObjectAttr) elementsObjectJsonLists
							.get(i);
					if ("alarm_amount".equals(elementsObjectAttr.getE_name())) {
						alarm_amount_flag = "alarm_amount";
					} else if ("alarm_money".equals(elementsObjectAttr
							.getE_name())) {
						alarm_money_flag = "alarm_money";
					} else if ("reconciliation_time".equals(elementsObjectAttr
							.getE_name())) {
						text_pattern_flag = "text_pattern";
					} else if ("text_pattern".equals(elementsObjectAttr
							.getE_name())) {
						reconciliation_time_flag = "reconciliation_time";
					}
				}
				return "reconciliation_alarm_edit";
			} else if (Consts.ALARM_TASK_TYPE_2.equals(alarm_task_type)) {
				for (int i = 0; i < elementsObjectJsonLists.size(); i++) {
					ElementsObjectAttr elementsObjectAttr = (ElementsObjectAttr) elementsObjectJsonLists
							.get(i);
					if ("not_task_deadlines".equals(elementsObjectAttr
							.getE_name())) {
						not_task_deadlines_flag = "not_task_deadlines";
					} else if ("not_task_money".equals(elementsObjectAttr
							.getE_name())) {
						not_task_money_flag = "not_task_money";
					} else if ("not_task_target".equals(elementsObjectAttr
							.getE_name())) {
						not_task_target_flag = "not_task_target";
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "task_alarm_edit";
	}

	public String editReconciliationAlarm() {
		try {
			String[] arrayElementsObject = elements_object.split(",");

			List<Map> elementsObjectList = new ArrayList();
			if (arrayElementsObject.length > 0) {
				for (int i = 0; i < arrayElementsObject.length; i++) {
					if ("alarm_amount".equals(arrayElementsObject[i].trim())) {
						Map elementsObjectAmountMap = new HashMap();
						elementsObjectAmountMap.put("e_name", "alarm_amount");
						elementsObjectAmountMap.put("c_name", "异常对账数量");
						elementsObjectList.add(elementsObjectAmountMap);
					} else if ("alarm_money".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectMoneyMap = new HashMap();
						elementsObjectMoneyMap.put("e_name", "alarm_money");
						elementsObjectMoneyMap.put("c_name", "异常对账金额");
						elementsObjectList.add(elementsObjectMoneyMap);
					} else if ("reconciliation_time"
							.equals(arrayElementsObject[i].trim())) {
						Map elementsObjectTimeMap = new HashMap();
						elementsObjectTimeMap.put("e_name",
								"reconciliation_time");
						elementsObjectTimeMap.put("c_name", "对账时间");
						elementsObjectList.add(elementsObjectTimeMap);
					} else if ("text_pattern".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectPatternMap = new HashMap();
						elementsObjectPatternMap.put("e_name", "text_pattern");
						elementsObjectPatternMap.put("c_name", "对账文本格式");
						elementsObjectList.add(elementsObjectPatternMap);
					}
				}

			}

			Map amountMap = new HashMap();
			Map moneyMap = new HashMap();
			Map textPatternMap = new HashMap();
			List<Map> conditionList = new ArrayList<Map>();

			amountMap.put("e_name", "alarm_amount");
			amountMap.put("c_name", "对账异常数量");
			amountMap.put("min_value", min_amount);
			amountMap.put("max_value", max_amount);
			amountMap.put("c_value", "");
			amountMap.put("checked", "yes");
			conditionList.add(amountMap);

			moneyMap.put("e_name", "alarm_money");
			moneyMap.put("c_name", "对账异常金额");
			moneyMap.put("min_value", min_money);
			moneyMap.put("max_value", max_money);
			moneyMap.put("c_value", "");
			moneyMap.put("checked", "yes");
			conditionList.add(moneyMap);

			textPatternMap.put("e_name", "text_pattern");
			textPatternMap.put("c_name", "对账文本格式");
			textPatternMap.put("min_value", "");
			textPatternMap.put("max_value", "");
			textPatternMap.put("c_value", text_pattern);
			textPatternMap.put("checked", "yes");
			conditionList.add(textPatternMap);

			JSONArray condition =JSON.parseArray(JSON.toJSONString(conditionList));
			JSONArray elements_object = JSON.parseArray(JSON.toJSONString(elementsObjectList));
			String userId = CommonTools.getUserId();
			this.alarmTaskManager.modifAlarmTask(alarm_task_id,
					alarm_task_name, task_create_time, task_aead_time,
					task_content, userId, DBTUtil.current(), elements_object
							.toString(), condition.toString(), owner_task_id);

			this.alarmReceiverManager.modifAlarmReceiver(recevier_id, phone,
					partner_id, partner_name);
			this.json = "{'result':0,'message':'修改成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 删除对账告警任务
	public String deleteReconciliaction() {
		try {
			String a[] = id.split(",");
			String alarm_task_id = "";
			for (int i = 0; i < a.length; i++) {
				alarm_task_id += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			alarm_task_id = alarm_task_id.substring(0,
					alarm_task_id.length() - 1);
			alarmTaskManager.deleteAlarmTask(alarm_task_id);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String taskAlarm() {
		return "task_alarm";
	}

	public String addTaskAlarm() {
		try {
			String[] arrayElementsObject = elements_object.split(",");

			List<Map> elementsObjectList = new ArrayList();
			if (arrayElementsObject.length > 0) {
				for (int i = 0; i < arrayElementsObject.length; i++) {
					if ("not_task_deadlines".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectDeadlinesMap = new HashMap();
						elementsObjectDeadlinesMap.put("e_name",
								"not_task_deadlines");
						elementsObjectDeadlinesMap.put("c_name", "未达到任务完成期限");
						elementsObjectList.add(elementsObjectDeadlinesMap);
					} else if ("not_task_money".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectMoneyMap = new HashMap();
						elementsObjectMoneyMap.put("e_name", "not_task_money");
						elementsObjectMoneyMap.put("c_name", "任务未完成指定金额");
						elementsObjectList.add(elementsObjectMoneyMap);
					} else if ("not_task_target".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectTargetMap = new HashMap();
						elementsObjectTargetMap
								.put("e_name", "not_task_target");
						elementsObjectTargetMap.put("c_name", "任务未完成目标值");
						elementsObjectList.add(elementsObjectTargetMap);
					}
				}
			}
            JSONArray elements_object=JSON.parseArray(JSON.toJSONString(elementsObjectList));

			String userId = CommonTools.getUserId();
			String alarm_task_id = this.alarmTaskManager.saveAlarmTask(
					alarm_task_name, alarm_task_type, task_create_time,
					task_aead_time, task_content, userId, DBTUtil.current(),
					elements_object// 添加告警任务表
							.toString(), "", owner_task_id);
			// System.out.print(jsonArray.toString());

			this.alarmReceiverManager.saveAlarmReceiver(phone, partner_id,
					partner_name, alarm_task_type, alarm_task_id,
					DBTUtil.current());// 添加告警对象接收人
		} catch (Exception e) {
			e.printStackTrace();
			json = "{'result':1,'message':'添加失败！'}";
			return WWAction.JSON_MESSAGE;
		}
		json = "{'result':0,'message':'添加成功！'}";
		return WWAction.JSON_MESSAGE;
	}

	public String taskList() {
		this.webpage = taskManager
				.searchTaskList(null, null, null, null, null, task_name, null,
						null, null, this.getPage(), this.getPageSize());
		return "task_list";
	}

	public String editTaskAlarm() {
		try {
			String[] arrayElementsObject = elements_object.split(",");

			List<Map> elementsObjectList = new ArrayList();
			if (arrayElementsObject.length > 0) {
				for (int i = 0; i < arrayElementsObject.length; i++) {
					if ("not_task_deadlines".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectDeadlinesMap = new HashMap();
						elementsObjectDeadlinesMap.put("e_name",
								"not_task_deadlines");
						elementsObjectDeadlinesMap.put("c_name", "未达到任务完成期限");
						elementsObjectList.add(elementsObjectDeadlinesMap);
					} else if ("not_task_money".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectMoneyMap = new HashMap();
						elementsObjectMoneyMap.put("e_name", "not_task_money");
						elementsObjectMoneyMap.put("c_name", "任务未完成指定金额");
						elementsObjectList.add(elementsObjectMoneyMap);
					} else if ("not_task_target".equals(arrayElementsObject[i]
							.trim())) {
						Map elementsObjectTargetMap = new HashMap();
						elementsObjectTargetMap
								.put("e_name", "not_task_target");
						elementsObjectTargetMap.put("c_name", "任务未完成目标值");
						elementsObjectList.add(elementsObjectTargetMap);
					}
				}
			}
            JSONArray  elements_object=JSON.parseArray(JSON.toJSONString(elementsObjectList));

			String userId = CommonTools.getUserId();
			this.alarmTaskManager.modifAlarmTask(alarm_task_id,
					alarm_task_name, task_create_time, task_aead_time,
					task_content, userId, DBTUtil.current(), elements_object.toString(), "", owner_task_id);

			this.alarmReceiverManager.modifAlarmReceiver(recevier_id, phone,
					partner_id, partner_name);
			this.json = "{'result':0,'message':'修改成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String alarmTemplateList() {
		this.webpage = this.AlarmTemplateManager.list(templet_name,
				templet_type, status, alarm_task_name, this.getPage(), this
						.getPageSize());
		return "alarm_template_list";
	}

	public String addAlarmTemplate() {
		try {
			String userId = CommonTools.getUserId();
			alarmTemplate.setOperation_time(DBTUtil.current());
			alarmTemplate.setOperation_id(userId);
			this.AlarmTemplateManager.saveAlarmTemplate(alarmTemplate);
			this.json = "{'result':0,'message':'添加成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 删除模板
	public String deleteAlarmTemplate() {
		try {
			String a[] = id.split(",");
			String template_id = "";
			for (int i = 0; i < a.length; i++) {
				template_id += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			template_id = template_id.substring(0, template_id.length() - 1);
			AlarmTemplateManager.deleteAlarmTemplate(template_id);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String alarmTemplate() {
		return "alarm_template";
	}

	public String showAlarmLog() {
		this.webpage = this.alarmLogManager.list(template_id, startDate,
				endDate, this.getPage(), this.getPageSize());
		return "showAlarmLog";
	}

	public String showAlarmTemplate() {
		alarmTemplate = this.AlarmTemplateManager
				.qryAlarmTemplateByTemplateId(template_id);

		if (alarmTemplate != null) {
			alarmTask = this.alarmTaskManager.findAlarmTaskById(alarmTemplate
					.getApplic_scene_id());
			if (alarmTemplate.getTemp_date().lastIndexOf("异常对账数量") > 0) {
				alarm_amount_flag = "alarm_amount";
			}
			if (alarmTemplate.getTemp_date().lastIndexOf("异常对账金额") > 0) {
				alarm_money_flag = "alarm_money";
			}
			if (alarmTemplate.getTemp_date().lastIndexOf("对账文本格式") > 0) {
				text_pattern_flag = "text_pattern";
			}
			if (alarmTemplate.getTemp_date().lastIndexOf("对账时间") > 0) {
				reconciliation_time_flag = "reconciliation_time";
			}
			if (alarmTemplate.getTemp_date().lastIndexOf("任务未完成指定金额") > 0) {
				not_task_money_flag = "not_task_money";
			}
			if (alarmTemplate.getTemp_date().lastIndexOf("任务未完成目标值") > 0) {
				not_task_target_flag = "not_task_target";
			}
		}
		return "alarm_template_edit";
	}

	public String updateAlarmTemplate() {
		try {
			this.AlarmTemplateManager
					.midifAlarmTemplateByTemplateId(alarmTemplate);
			this.json = "{'result':0,'message':'修改成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String showAlarmLogBase() {
		alarmLog = this.alarmLogManager.qryAlarmLogById(alarm_log_id);
		return "showAlarmLogBase";
	}

	public String getAlarm_task_name() {
		return alarm_task_name;
	}

	public void setAlarm_task_name(String alarm_task_name) {
		this.alarm_task_name = alarm_task_name;
	}

	public String getText_pattern() {
		return text_pattern;
	}

	public void setText_pattern(String text_pattern) {
		this.text_pattern = text_pattern;
	}

	public String getMin_money() {
		return min_money;
	}

	public void setMin_money(String min_money) {
		this.min_money = min_money;
	}

	public String getMax_money() {
		return max_money;
	}

	public void setMax_money(String max_money) {
		this.max_money = max_money;
	}

	public String getMin_amount() {
		return min_amount;
	}

	public void setMin_amount(String min_amount) {
		this.min_amount = min_amount;
	}

	public String getMax_amount() {
		return max_amount;
	}

	public void setMax_amount(String max_amount) {
		this.max_amount = max_amount;
	}

	public String getTask_create_time() {
		return task_create_time;
	}

	public void setTask_create_time(String task_create_time) {
		this.task_create_time = task_create_time;
	}

	public String getTask_aead_time() {
		return task_aead_time;
	}

	public void setTask_aead_time(String task_aead_time) {
		this.task_aead_time = task_aead_time;
	}

	public String getDistributor_id() {
		return distributor_id;
	}

	public void setDistributor_id(String distributor_id) {
		this.distributor_id = distributor_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getElements_object() {
		return elements_object;
	}

	public void setElements_object(String elements_object) {
		this.elements_object = elements_object;
	}

	public String getTask_content() {
		return task_content;
	}

	public void setTask_content(String task_content) {
		this.task_content = task_content;
	}

	public String getAlarm_task_type() {
		return alarm_task_type;
	}

	public void setAlarm_task_type(String alarm_task_type) {
		this.alarm_task_type = alarm_task_type;
	}

	public IAlarmTaskManager getAlarmTaskManager() {
		return alarmTaskManager;
	}

	public void setAlarmTaskManager(IAlarmTaskManager alarmTaskManager) {
		this.alarmTaskManager = alarmTaskManager;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPartner_name() {
		return partner_name;
	}

	public void setPartner_name(String partner_name) {
		this.partner_name = partner_name;
	}

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	public IAlarmReceiverManager getAlarmReceiverManager() {
		return alarmReceiverManager;
	}

	public void setAlarmReceiverManager(
			IAlarmReceiverManager alarmReceiverManager) {
		this.alarmReceiverManager = alarmReceiverManager;
	}

	public String getAlarm_task_id() {
		return alarm_task_id;
	}

	public void setAlarm_task_id(String alarm_task_id) {
		this.alarm_task_id = alarm_task_id;
	}

	public AlarmTask getAlarmTask() {
		return alarmTask;
	}

	public void setAlarmTask(AlarmTask alarmTask) {
		this.alarmTask = alarmTask;
	}

	public List getConditionJsonLists() {
		return conditionJsonLists;
	}

	public void setConditionJsonLists(List conditionJsonLists) {
		this.conditionJsonLists = conditionJsonLists;
	}

	public List getElementsObjectJsonLists() {
		return elementsObjectJsonLists;
	}

	public void setElementsObjectJsonLists(List elementsObjectJsonLists) {
		this.elementsObjectJsonLists = elementsObjectJsonLists;
	}

	public String getRecevier_id() {
		return recevier_id;
	}

	public void setRecevier_id(String recevier_id) {
		this.recevier_id = recevier_id;
	}

	public AlarmReceiver getAlarmReceiver() {
		return alarmReceiver;
	}

	public void setAlarmReceiver(AlarmReceiver alarmReceiver) {
		this.alarmReceiver = alarmReceiver;
	}

	public String getAlarm_amount_flag() {
		return alarm_amount_flag;
	}

	public void setAlarm_amount_flag(String alarm_amount_flag) {
		this.alarm_amount_flag = alarm_amount_flag;
	}

	public String getAlarm_money_flag() {
		return alarm_money_flag;
	}

	public void setAlarm_money_flag(String alarm_money_flag) {
		this.alarm_money_flag = alarm_money_flag;
	}

	public String getText_pattern_flag() {
		return text_pattern_flag;
	}

	public void setText_pattern_flag(String text_pattern_flag) {
		this.text_pattern_flag = text_pattern_flag;
	}

	public String getReconciliation_time_flag() {
		return reconciliation_time_flag;
	}

	public void setReconciliation_time_flag(String reconciliation_time_flag) {
		this.reconciliation_time_flag = reconciliation_time_flag;
	}

	public String getElementsObjectAttr() {
		return ElementsObjectAttr;
	}

	public void setElementsObjectAttr(String elementsObjectAttr) {
		ElementsObjectAttr = elementsObjectAttr;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public ITaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public String getOwner_task_id() {
		return owner_task_id;
	}

	public void setOwner_task_id(String owner_task_id) {
		this.owner_task_id = owner_task_id;
	}

	public String getNot_task_deadlines_flag() {
		return not_task_deadlines_flag;
	}

	public void setNot_task_deadlines_flag(String not_task_deadlines_flag) {
		this.not_task_deadlines_flag = not_task_deadlines_flag;
	}

	public String getNot_task_money_flag() {
		return not_task_money_flag;
	}

	public void setNot_task_money_flag(String not_task_money_flag) {
		this.not_task_money_flag = not_task_money_flag;
	}

	public String getNot_task_target_flag() {
		return not_task_target_flag;
	}

	public void setNot_task_target_flag(String not_task_target_flag) {
		this.not_task_target_flag = not_task_target_flag;
	}

	public Map getTaskMap() {
		return taskMap;
	}

	public void setTaskMap(Map taskMap) {
		this.taskMap = taskMap;
	}

	public String getTemplet_name() {
		return templet_name;
	}

	public void setTemplet_name(String templet_name) {
		this.templet_name = templet_name;
	}

	public String getTemplet_type() {
		return templet_type;
	}

	public void setTemplet_type(String templet_type) {
		this.templet_type = templet_type;
	}

	public IAlarmTemplateManager getAlarmTemplateManager() {
		return AlarmTemplateManager;
	}

	public void setAlarmTemplateManager(
			IAlarmTemplateManager alarmTemplateManager) {
		AlarmTemplateManager = alarmTemplateManager;
	}

	public AlarmTemplate getAlarmTemplate() {
		return alarmTemplate;
	}

	public void setAlarmTemplate(AlarmTemplate alarmTemplate) {
		this.alarmTemplate = alarmTemplate;
	}

	public IAlarmLogManager getAlarmLogManager() {
		return alarmLogManager;
	}

	public void setAlarmLogManager(IAlarmLogManager alarmLogManager) {
		this.alarmLogManager = alarmLogManager;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAlarm_log_id() {
		return alarm_log_id;
	}

	public void setAlarm_log_id(String alarm_log_id) {
		this.alarm_log_id = alarm_log_id;
	}

	public AlarmLog getAlarmLog() {
		return alarmLog;
	}

	public void setAlarmLog(AlarmLog alarmLog) {
		this.alarmLog = alarmLog;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecevier_phone_num() {
		return recevier_phone_num;
	}

	public void setRecevier_phone_num(String recevier_phone_num) {
		this.recevier_phone_num = recevier_phone_num;
	}

	public String getRecevier_username() {
		return recevier_username;
	}

	public void setRecevier_username(String recevier_username) {
		this.recevier_username = recevier_username;
	}

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

}
