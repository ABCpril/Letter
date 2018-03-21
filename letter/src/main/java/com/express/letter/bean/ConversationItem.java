package com.express.letter.bean;

import com.hyphenate.chat.EMConversation;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/21 17:34
 * 修改人员：Robi
 * 修改时间：2018/03/21 17:34
 * 修改备注：
 * Version: 1.0.0
 */
public class ConversationItem {

    public static final String NORMAL = "normal";
    public static final String EMPTY = "empty";

    public EMConversation mEMConversation;
    public String ConversationType = NORMAL;

    public ConversationItem() {
    }

    public ConversationItem(EMConversation EMConversation) {
        mEMConversation = EMConversation;
    }

    public ConversationItem(String conversationType) {
        ConversationType = conversationType;
    }
}
