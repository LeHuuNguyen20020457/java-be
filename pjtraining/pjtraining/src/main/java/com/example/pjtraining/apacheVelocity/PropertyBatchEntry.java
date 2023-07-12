package com.example.pjtraining.apacheVelocity;

public class PropertyBatchEntry {
    private String physicalName;

    private String type;

    private int length;

    private int accuracy;

    private Boolean required;

    private int mainKey;

    private String defaultValue;

    public PropertyBatchEntry() {}
    public PropertyBatchEntry(String physicalName, String type, int length, int accuracy, boolean required, int mainKey, String defaultValue) {
        this.physicalName = physicalName;
        this.type = type;
        this.length = length;
        this.accuracy = accuracy;
        this.required = required;
        this.mainKey = mainKey;
        this.defaultValue = defaultValue;
    }

    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getMainKey() {
        return mainKey;
    }

    public void setMainKey(int mainKey) {
        this.mainKey = mainKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
