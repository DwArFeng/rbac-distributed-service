package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateRoleUserRelation;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case UserMaintainService.ROLE_IN_ROLE_NOT_IN_KEY_ASC:
                roleInRoleNotInKeyAsc(detachedCriteria, objects);
                break;
            case UserMaintainService.ID_LIKE:
                idLike(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    /**
     * 角色在集合内且不在排除集合内（按用户主键升序排序）。
     *
     * <p>
     * 使用子查询从 {@link HibernateRoleUserRelation} 中投影出满足条件的 {@code user_id}，主查询用
     * {@link Subqueries#propertyIn} 关联，避免对 {@code roleUserRelations} 连接产生重复行；且不采用关联 EXISTS
     * （{@code eqProperty} 与外层 DetachedCriteria 组合时 Hibernate 5 可能无法解析实体而报错）。
     *
     * <p>
     * 等价语义：存在至少一条用户-角色关系，其 {@code role_id} 属于 in 集合，且（若 notIn 非空）该 {@code role_id}
     * 不属于 notIn 集合。
     */
    private void roleInRoleNotInKeyAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            // 由调用人员自行保证类型安全。
            @SuppressWarnings("unchecked")
            Collection<StringIdKey> inRoleKeys = (Collection<StringIdKey>) objects[0];
            // 由调用人员自行保证类型安全。
            @SuppressWarnings("unchecked")
            Collection<StringIdKey> notInRoleKeys = (Collection<StringIdKey>) objects[1];
            if (inRoleKeys.isEmpty()) {
                // 如果 inRoleKeys 为空，则不应该查出任何数据，此处设置一个不可能存在的条件。
                detachedCriteria.add(Restrictions.isNull("stringId"));
            } else {
                Collection<String> inRoleStringIds = inRoleKeys.stream().map(StringIdKey::getStringId)
                        .collect(Collectors.toSet());
                DetachedCriteria subCriteria = DetachedCriteria.forClass(HibernateRoleUserRelation.class);
                subCriteria.add(Restrictions.in("roleStringId", inRoleStringIds));
                if (!notInRoleKeys.isEmpty()) {
                    Collection<String> notInRoleStringIds = notInRoleKeys.stream().map(StringIdKey::getStringId)
                            .collect(Collectors.toSet());
                    subCriteria.add(Restrictions.not(Restrictions.in("roleStringId", notInRoleStringIds)));
                }
                subCriteria.setProjection(Projections.property("userStringId"));
                detachedCriteria.add(Subqueries.propertyIn("stringId", subCriteria));
            }
            detachedCriteria.addOrder(Order.asc("stringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void idLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String id = (String) objects[0];
            detachedCriteria.add(Restrictions.like("stringId", id, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
