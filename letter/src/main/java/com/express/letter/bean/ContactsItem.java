package com.express.letter.bean;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/21 17:50
 * 修改人员：Robi
 * 修改时间：2018/03/21 17:50
 * 修改备注：
 * Version: 1.0.0
 */
public class ContactsItem {

    public static final String NORMAL = "normal";
    public static final String ADD = "add";
    public static final String ACCEPT = "accept";
    public static final String GROUP = "group";
    public static final String EMPTY = "empty";

    /**
     * 联系人的名字
     */
    public String username;
    public String type = NORMAL;//数据类型

    public ContactsItem() {
    }

    public ContactsItem(String username, String type) {
        this.username = username;
        this.type = type;
    }

    public ContactsItem(String username) {
        this.username = username;
    }
}
