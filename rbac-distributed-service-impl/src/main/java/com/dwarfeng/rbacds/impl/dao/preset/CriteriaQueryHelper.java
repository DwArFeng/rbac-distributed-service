package com.dwarfeng.rbacds.impl.dao.preset;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Jakarta Criteria 查询配置辅助类。
 *
 * <p>
 * 该类用于保持旧 Hibernate Criteria 逐项追加查询条件和排序的语义，避免后续调用覆盖已经配置的限制或排序。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
final class CriteriaQueryHelper {

    /**
     * 向查询中追加限制条件。
     *
     * @param criteriaBuilder Criteria 构建器。
     * @param criteriaQuery   Criteria 查询。
     * @param predicate       待追加的限制条件。
     */
    public static void addRestriction(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Predicate predicate
    ) {
        Predicate restriction = criteriaQuery.getRestriction();
        criteriaQuery.where(Objects.isNull(restriction) ? predicate : criteriaBuilder.and(restriction, predicate));
    }

    /**
     * 创建相等或为空限制条件。
     *
     * @param criteriaBuilder Criteria 构建器。
     * @param expression      属性表达式。
     * @param value           比较值。
     * @return 相等或为空限制条件。
     */
    public static Predicate equalOrIsNull(
            CriteriaBuilder criteriaBuilder, Expression<?> expression, Object value
    ) {
        return Objects.isNull(value) ? criteriaBuilder.isNull(expression) : criteriaBuilder.equal(expression, value);
    }

    /**
     * 创建任意位置匹配限制条件。
     *
     * @param criteriaBuilder Criteria 构建器。
     * @param expression      文本属性表达式。
     * @param pattern         匹配文本。
     * @return 任意位置匹配限制条件。
     */
    public static Predicate likeAnywhere(
            CriteriaBuilder criteriaBuilder, Expression<String> expression, String pattern
    ) {
        return criteriaBuilder.like(expression, "%" + pattern + "%");
    }

    /**
     * 向查询中追加排序条件。
     *
     * @param criteriaQuery Criteria 查询。
     * @param order         待追加的排序条件。
     */
    public static void addOrder(CriteriaQuery<?> criteriaQuery, Order order) {
        List<Order> orders = new ArrayList<>(criteriaQuery.getOrderList());
        orders.add(order);
        criteriaQuery.orderBy(orders);
    }

    private CriteriaQueryHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
