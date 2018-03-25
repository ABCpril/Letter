package com.express.letter.bean;

import com.hyphenate.chat.EMGroup;

/**
 * Created by angcyo on 2018/03/25 08:42
 */
public class GroupItem {
    public static final String NORMAL = "NORMAL";
    public static final String NEW_GROUP = "NEW_GROUP";
    public static final String ADD_GROUP = "ADD_GROUP";

    private EMGroup mEMGroup;

    private String type = NORMAL;

    public GroupItem(EMGroup EMGroup) {
        mEMGroup = EMGroup;
    }

    public GroupItem(String type) {
        this.type = type;
    }

    public GroupItem() {
    }

    public EMGroup getEMGroup() {
        return mEMGroup;
    }

    public void setEMGroup(EMGroup EMGroup) {
        mEMGroup = EMGroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
