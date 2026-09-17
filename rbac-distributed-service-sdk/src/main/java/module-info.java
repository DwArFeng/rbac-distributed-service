module com.dwarfeng.rbacds.sdk {

    requires com.dwarfeng.rbacds.base;
    requires com.dwarfeng.rbacds.stack;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.data;
    requires com.dwarfeng.subgrade.web;
    requires com.alibaba.fastjson2;
    requires jakarta.validation;
    requires org.hibernate.validator;
    requires org.mapstruct;

    exports com.dwarfeng.rbacds.sdk.bean;
    exports com.dwarfeng.rbacds.sdk.bean.dto;
    exports com.dwarfeng.rbacds.sdk.bean.entity;
    exports com.dwarfeng.rbacds.sdk.bean.key;
    exports com.dwarfeng.rbacds.sdk.bean.key.formatter;
    exports com.dwarfeng.rbacds.sdk.handler;
    exports com.dwarfeng.rbacds.sdk.handler.pusher;
    exports com.dwarfeng.rbacds.sdk.handler.resetter;
    exports com.dwarfeng.rbacds.sdk.exception;
    exports com.dwarfeng.rbacds.sdk.util;

    opens com.dwarfeng.rbacds.sdk.i18n to com.dwarfeng.rbacds.base;
}
