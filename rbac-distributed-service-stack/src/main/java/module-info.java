module com.dwarfeng.rbacds.stack {

    requires com.dwarfeng.rbacds.base;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.data;
    requires com.dwarfeng.subgrade.cache;
    requires com.dwarfeng.subgrade.lifecycle;
    requires static org.jetbrains.annotations;

    exports com.dwarfeng.rbacds.stack.bean.dto;
    exports com.dwarfeng.rbacds.stack.bean.entity;
    exports com.dwarfeng.rbacds.stack.bean.key;
    exports com.dwarfeng.rbacds.stack.cache;
    exports com.dwarfeng.rbacds.stack.dao;
    exports com.dwarfeng.rbacds.stack.exception;
    exports com.dwarfeng.rbacds.stack.handler;
    exports com.dwarfeng.rbacds.stack.service;
    exports com.dwarfeng.rbacds.stack.struct;

    opens com.dwarfeng.rbacds.stack.i18n to com.dwarfeng.rbacds.base;
}
