package com.buct.computer.common.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/27/11:14
 */
public enum UserTypeEnum {

    admin("admin"),
    ordinary("ordinary");

    final String typeName;


    UserTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
