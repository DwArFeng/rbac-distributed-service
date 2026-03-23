package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateRoleUserRelation;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class RolePresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case RoleMaintainService.CHILD_FOR_USER_AVAILABLE_KEY_ASC:
                childForUserAvailableKeyAsc(detachedCriteria, objects);
                break;
            case RoleMaintainService.ENABLED:
                enabled(detachedCriteria, objects);
                break;
            case RoleMaintainService.ID_LIKE:
                idLike(detachedCriteria, objects);
                break;
            case RoleMaintainService.NOT_CHILD_FOR_USER_KEY_ASC:
                notChildForUserKeyAsc(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForUserAvailableKeyAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                // 如果 userKey 为空，则不应该查出任何数据，此处设置一个不可能存在的条件。
                detachedCriteria.add(Restrictions.isNull("stringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eq("enabled", true));
                detachedCriteria.createAlias("roleUserRelations", "r");
                detachedCriteria.add(Restrictions.eq("r.userStringId", stringIdKey.getStringId()));
                detachedCriteria.add(Restrictions.eq("r.enabled", true));
            }
            detachedCriteria.addOrder(Order.asc("stringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void enabled(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            detachedCriteria.add(Restrictions.eq("enabled", true));
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

    /**
     * 非子项于用户（按主键升序排序）。
     *
     * <p>
     * 查询逻辑：通过子查询从 {@link HibernateRoleUserRelation} 表中筛选出与指定用户有关联关系的角色主键，
     * 然后在主查询中排除这些角色，从而得到用户尚未关联的所有角色。
     *
     * <p>
     * SQL 等价于：
     * <pre>
     * SELECT * FROM tbl_role
     * WHERE id NOT IN (
     *     SELECT role_id FROM tbl_role_user_relation WHERE user_id = ?
     * )
     * ORDER BY id ASC
     * </pre>
     *
     * @param detachedCriteria 主查询的 DetachedCriteria，对应 Role 实体
     * @param objects          预设参数，objects[0] 为用户主键 {@link StringIdKey}
     */
    private void notChildForUserKeyAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                // 如果 userKey 为空，则不应该查出任何数据，此处设置一个不可能存在的条件。
                detachedCriteria.add(Restrictions.isNull("stringId"));
            } else {
                StringIdKey userKey = (StringIdKey) objects[0];
                // 子查询：从角色用户关系表中查询出该用户已关联的角色主键
                DetachedCriteria subCriteria = DetachedCriteria.forClass(HibernateRoleUserRelation.class);
                subCriteria.add(Restrictions.eq("userStringId", userKey.getStringId()));
                subCriteria.setProjection(Projections.property("roleStringId"));
                // 主查询：排除子查询结果中的角色，即得到用户未关联的角色
                detachedCriteria.add(Subqueries.propertyNotIn("stringId", subCriteria));
            }
            detachedCriteria.addOrder(Order.asc("stringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
