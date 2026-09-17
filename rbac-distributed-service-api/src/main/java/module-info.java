module com.dwarfeng.rbacds.api {

    requires com.dwarfeng.rbacds.stack;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.web;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.aop;
    requires org.jetbrains.annotations;

    exports com.dwarfeng.rbacds.api.integration.subgrade;

    opens com.dwarfeng.rbacds.api.integration.subgrade to spring.core, spring.beans, spring.context, spring.aop;
}
