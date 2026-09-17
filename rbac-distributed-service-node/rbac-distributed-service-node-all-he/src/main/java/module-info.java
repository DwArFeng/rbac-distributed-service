module com.dwarfeng.rbacds.node.all.he {

    requires com.dwarfeng.rbacds.base;
    requires com.dwarfeng.rbacds.stack;
    requires com.dwarfeng.rbacds.sdk;
    requires com.dwarfeng.rbacds.impl;
    requires com.alibaba.fastjson2;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.aop;
    requires com.dwarfeng.subgrade.data;
    requires com.dwarfeng.springtelqos.core;
    requires com.dwarfeng.springtelqos.api;
    requires com.dwarfeng.springterminator.core;
    requires com.dwarfeng.springterminator.api;
    requires com.dwarfeng.datamark.core;
    requires com.dwarfeng.datamark.api;
    requires jakarta.annotation;
    requires jakarta.el;
    requires java.sql;
    requires org.apache.commons.logging;
    requires org.glassfish.expressly;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.expression;
    requires spring.aop;
    requires spring.data.redis;
    requires org.aspectj.weaver;
    requires dubbo;
    requires org.slf4j;

    exports com.dwarfeng.rbacds.node.all.he.configuration;
    exports com.dwarfeng.rbacds.node.all.he.handler;
    exports com.dwarfeng.rbacds.node.all.he.launcher;

    opens com.dwarfeng.rbacds.node.all.he.configuration to spring.core, spring.beans, spring.context, spring.aop;
    opens com.dwarfeng.rbacds.node.all.he.i18n to com.dwarfeng.rbacds.base;
}
