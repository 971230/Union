package com.ztesoft.component.ui.taglib;

//import java.util.ArrayList;
//import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

//import com.ztesoft.component.framework.vo.ControlLoadPlanReturn;

public class FieldTag extends TagSupport {

	private String field;

	private String label;

	private String type;

	private String defaultValue;

	private String format;

	private String visible;

	private String sortable;

	private String readOnly;

	private String editable;

	private String required;

	private String dropDown;

	private String attrCode;

	private String editorType;

	private String toolTip;

	private String tag;

	private String popup;

	private String labelLayout;

	private String labelCheckbox;

	private String inputLayout;

	private String textareaLayout;

	private String textarea;

	private String textareaHeight;

	private String validType;

	private String validMsg;

	private String minLen;

	private String maxLen;

	private String password;

	private String keyField;

	private String subList;

	private String subListDataset;

	private String blankValue;

	private String blankId;

	private String maxHeight;

	private String configParams;

	private String dbField;

	private String dbFieldControl;

	private String size;

	private String permissionControl;

	private String dynaControlId;

	private String parameter;

	private String checkbox;

	private String checkboxAttrCode;

	private String radiobox;

	private String radioboxAttrCode;
	
	
	private String align ;//xuruihao added 20090424
	public String getAlign(){
		return align;
	}
	public void setAlign(String align){
		this.align = align;
	}
	/**
	 * @return Returns the checkbox.
	 */
	public String getCheckbox() {
		return checkbox;
	}

	/**
	 * @param checkbox
	 *            The checkbox to set.
	 */
	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * @return Returns the parameter.
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            The parameter to set.
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getDbFieldControl() {
		return (dbFieldControl != null ? dbFieldControl : "inverse");
	}

	public void setDbFieldControl(String dbFieldControl) {
		this.dbFieldControl = dbFieldControl;
	}

	public String getDbField() {
		return dbField;
	}

	public void setDbField(String dbField) {
		this.dbField = dbField;
	}

	public String getDefaultValue() {
		return (defaultValue != null ? defaultValue : "");
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDropDown() {
		return (dropDown != null ? dropDown
				: (this.getAttrCode() != null ? this.getAttrCode() : ""));
	}

	public void setDropDown(String dropDown) {
		this.dropDown = dropDown;
	}

	public String getEditable() {
		return (editable != null ? editable : "false");
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getEditorType() {
		return (editorType != null ? editorType : "");
	}

	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	public String getFormat() {
		return (format != null ? format : "");
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getInputLayout() {
		return (inputLayout != null ? inputLayout : "");
	}

	public void setInputLayout(String inputLayout) {
		this.inputLayout = inputLayout;
	}

	public String getKeyField() {
		return (keyField != null ? keyField : "");
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getLabelLayout() {
		return (labelLayout != null ? labelLayout : "");
	}

	public void setLabelLayout(String labelLayout) {
		this.labelLayout = labelLayout;
	}

	public String getPassword() {
		return (password != null ? password : "false");
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPopup() {
		return (popup != null ? popup : "");
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public String getReadOnly() {
		if ("true".equalsIgnoreCase(this.getLabelCheckbox()))
			return "true";
		else
			return (readOnly != null ? readOnly : "false");
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getRequired() {
		return (required != null ? required : "false");
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getSortable() {
		return (sortable != null ? sortable : "false");
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public String getTag() {
		return (tag != null ? tag : "");
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTextarea() {
		return (textarea != null ? textarea : "");
	}

	public void setTextarea(String textarea) {
		this.textarea = textarea;
	}

	public String getTextareaHeight() {
		return (textareaHeight != null ? textareaHeight : "");
	}

	public void setTextareaHeight(String textareaHeight) {
		this.textareaHeight = textareaHeight;
	}

	public String getTextareaLayout() {
		return (textareaLayout != null ? textareaLayout : "");
	}

	public void setTextareaLayout(String textareaLayout) {
		this.textareaLayout = textareaLayout;
	}

	public String getToolTip() {
		return (toolTip != null ? toolTip : "");
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public String getType() {
		return (type != null ? type : "string");
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidMsg() {
		return (validMsg != null ? validMsg : "");
	}

	public void setValidMsg(String validMsg) {
		this.validMsg = validMsg;
	}

	public String getValidType() {
		return (validType != null ? validType : "");
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public String getVisible() {
		return (visible != null ? visible : "true");
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBlankId() {
		return blankId;
	}

	public void setBlankId(String blankId) {
		this.blankId = blankId;
	}

	public String getBlankValue() {
		return blankValue;
	}

	public void setBlankValue(String blankValue) {
		this.blankValue = blankValue;
	}

	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getConfigParams() {
		return configParams;
	}

	public void setConfigParams(String configParams) {
		this.configParams = configParams;
	}

	public String getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(String maxLen) {
		this.maxLen = maxLen;
	}

	public String getMinLen() {
		return minLen;
	}

	public void setMinLen(String minLen) {
		this.minLen = minLen;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub

		Tag dataset = this.getParent();
		if (dataset != null) {

			String controlResult = "";
			if ("true".equalsIgnoreCase(this.getPermissionControl())) {

				Object permission = pageContext.getAttribute("PERMISSION");
				if (permission != null && permission instanceof PermissionVo) {
					PermissionVo perm = (PermissionVo) permission;
					controlResult = perm
							.getControlResult(((DatasetTag) dataset)
									.getDatasetId()
									+ "_" + this.getField());
				}
				if ("disabled".equalsIgnoreCase(controlResult)) {
					this.setReadOnly("true");
				} else if ("invisible".equalsIgnoreCase(controlResult)) {
					this.setVisible("false");
				}
			}

			/*
			 * ControlLoadPlanReturn controlLoadPlanReturn =
			 * (ControlLoadPlanReturn)pageContext.getRequest().getAttribute("____control_data");
			 * if( controlLoadPlanReturn != null ){ ArrayList readOnlyList =
			 * controlLoadPlanReturn.getReadonlyAttrList(); ArrayList
			 * unvisibleList = controlLoadPlanReturn.getUnvisibleAttrList() ;
			 * if( readOnlyList != null ){ for( int i = 0 ; i <
			 * readOnlyList.size() ; i ++ ){ String id =
			 * (String)readOnlyList.get(i); if(
			 * this.field.equalsIgnoreCase(id)){ this.setReadOnly("true"); } } }
			 * if( unvisibleList != null ){ for( int i = 0 ; i <
			 * unvisibleList.size() ; i ++ ){ String id =
			 * (String)unvisibleList.get(i); if(
			 * this.field.equalsIgnoreCase(id)){ this.setVisible("false"); } } } }
			 */

			if (this.dynaControlId != null && !"".equals(this.dynaControlId)) {
				PageTagUtils.getInstance().setFieldTagDynaElement(this);
			}

			FieldVo fieldVo = new FieldVo(this.getField(), this.getLabel(),
					this.getType(), this.getDefaultValue(), this.getFormat(),
					this.getVisible(), this.getSortable(), this.getReadOnly(),
					this.getEditable(), this.getRequired(), this.getDropDown(),
					this.getEditorType(), this.getToolTip(), this.getTag(),
					this.getPopup(), this.getLabelLayout(), this
							.getInputLayout(), this.getTextareaLayout(), this
							.getTextarea(), this.getTextareaHeight(), this
							.getValidType(), this.getValidMsg(), this
							.getPassword(), this.getKeyField());
			//增加复选和单选的控件
			fieldVo.setCheckbox(this.getCheckbox());
			fieldVo.setCheckboxAttrCode(this.getCheckboxAttrCode());	
			fieldVo.setRadiobox(this.getRadiobox());
			fieldVo.setRadioboxAttrCode(this.getRadioboxAttrCode());	
			//增加复选和单选的控件
			fieldVo.setPageContext(pageContext);
			if( this.align !=null && !"".equals(align)){
				fieldVo.setAlign(this.getAlign());
			}else{
				fieldVo.setAlign("middle");
			}

			if ("".equalsIgnoreCase(controlResult))
				fieldVo.setPopupEnable("true");
			else
				fieldVo.setPopupEnable("false");

			fieldVo.setLabelCheckbox(this.getLabelCheckbox());

			fieldVo.setAttrCode(this.getAttrCode());
			fieldVo.setSubList(this.getSubList());
			fieldVo.setSubListDataset(this.getSubListDataset());

			fieldVo.setDbField(this.getDbField());
			fieldVo.setDbFieldControl(this.getDbFieldControl());

			fieldVo.setBlankId(this.getBlankId());
			fieldVo.setBlankValue(this.getBlankValue());
			fieldVo.setMaxHeight(this.getMaxHeight());
			fieldVo.setMinLen(this.getMinLen());
			fieldVo.setMaxLen(this.getMaxLen());
			fieldVo.setConfigParams(this.getConfigParams());
			fieldVo.setSize(this.getSize());

			((DatasetTag) dataset).setField(fieldVo);
		}

		return super.doEndTag();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();

		this.field = null;
		this.label = null;

		this.type = null;
		this.defaultValue = null;
		this.format = null;
		this.visible = null;
		this.sortable = null;

		this.readOnly = null;
		this.editable = null;
		this.required = null;
		this.dropDown = null;
		this.attrCode = null;
		this.editorType = null;

		this.toolTip = null;
		this.tag = null;
		this.popup = null;
		this.labelLayout = null;
		this.inputLayout = null;

		this.textareaLayout = null;
		this.textarea = null;
		this.textareaHeight = null;
		this.validType = null;
		this.validMsg = null;

		this.password = null;
		this.keyField = null;
		this.subList = null;
		this.subListDataset = null;

		this.dbField = null;
		this.dbFieldControl = null;

		this.blankValue = null;
		this.blankId = null;
		this.maxHeight = null;

		this.permissionControl = null;
	}

	public String getSubList() {
		return subList;
	}

	public void setSubList(String subList) {
		this.subList = subList;
	}

	public String getSubListDataset() {
		return subListDataset;
	}

	public void setSubListDataset(String subListDataset) {
		this.subListDataset = subListDataset;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getLabelCheckbox() {
		return (labelCheckbox != null ? labelCheckbox : "false");
	}

	public void setLabelCheckbox(String labelCheckbox) {
		this.labelCheckbox = labelCheckbox;
	}

	public String getPermissionControl() {
		return permissionControl;
	}

	public void setPermissionControl(String permissionControl) {
		this.permissionControl = permissionControl;
	}

	/**
	 * @return the dynaControlId
	 */
	public String getDynaControlId() {
		return dynaControlId;
	}

	/**
	 * @param dynaControlId
	 *            the dynaControlId to set
	 */
	public void setDynaControlId(String dynaControlId) {
		this.dynaControlId = dynaControlId;
	}

	/**
	 * @return Returns the checkboxAttrCode.
	 */
	public String getCheckboxAttrCode() {
		return checkboxAttrCode;
	}

	/**
	 * @param checkboxAttrCode The checkboxAttrCode to set.
	 */
	public void setCheckboxAttrCode(String checkboxAttrCode) {
		this.checkboxAttrCode = checkboxAttrCode;
	}

	/**
	 * @return Returns the radiobox.
	 */
	public String getRadiobox() {
		return radiobox;
	}

	/**
	 * @param radiobox The radiobox to set.
	 */
	public void setRadiobox(String radiobox) {
		this.radiobox = radiobox;
	}

	/**
	 * @return Returns the radioboxAttrCode.
	 */
	public String getRadioboxAttrCode() {
		return radioboxAttrCode;
	}

	/**
	 * @param radioboxAttrCode The radioboxAttrCode to set.
	 */
	public void setRadioboxAttrCode(String radioboxAttrCode) {
		this.radioboxAttrCode = radioboxAttrCode;
	}

}
