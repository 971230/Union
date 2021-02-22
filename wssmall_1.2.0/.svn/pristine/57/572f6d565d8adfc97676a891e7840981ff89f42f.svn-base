package com.ztesoft.ibss.common.valueobject;


import java.io.Serializable;



public abstract class ValueObject implements Serializable
{
    private boolean insertFlag = false;
    private boolean updateFlag = false;
    private boolean deleteFlag = false;
    private long rowVersion = 0; 

    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml){
    	return sbXml;
    }
    
    public String pathInTree(){
    	return null;
    }
    
    public boolean getInsertFlag() {
        return insertFlag;
    }

    public boolean getDeleteFlag() {
        return deleteFlag;
    }

    public boolean getUpdateFlag() {
        return updateFlag;
    }

    public long getRowVersion() {
        return rowVersion;
    }

    public void setInsertFlag(boolean pFlag) {
        insertFlag = pFlag;
        deleteFlag = false;
        updateFlag = false;
    }

    public void setUpdateFlag(boolean pFlag) {
        insertFlag = false;
        deleteFlag = false;
        updateFlag = pFlag;
    }

    public void setDeleteFlag(boolean pFlag) {
        insertFlag = false;
        deleteFlag = pFlag;
        updateFlag = false;
    }

    public void setRowVersion(long pRowVersion) {
        rowVersion = pRowVersion;
    }

    public void resetFlags() {
        insertFlag = false;
        deleteFlag = false;
        updateFlag = false;
    }
}
