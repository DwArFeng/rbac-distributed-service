package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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

    private void roleInRoleNotInKeyAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            // 由调用人员自行保证类型安全。
            @SuppressWarnings("unchecked")
            Collection<StringIdKey> inRoleKeys = (Collection<StringIdKey>) objects[0];
            // 由调用人员自行保证类型安全。
            @SuppressWarnings("unchecked")
            Collection<StringIdKey> notInRoleKeys = (Collection<StringIdKey>) objects[1];
            detachedCriteria.createAlias("roleUserRelations", "r");
            if (inRoleKeys.isEmpty()) {
                // 如果 inRoleKeys 为空，则不应该查出任何数据，此处设置一个不可能存在的条件。
                detachedCriteria.add(Restrictions.isNull("stringId"));
            } else {
                Collection<String> inRoleStringIds = inRoleKeys.stream().map(StringIdKey::getStringId)
                        .collect(Collectors.toSet());
                detachedCriteria.add(Restrictions.in("r.roleStringId", inRoleStringIds));
            }
            if (!notInRoleKeys.isEmpty()) {
                Collection<String> notInRoleStringIds = notInRoleKeys.stream().map(StringIdKey::getStringId)
                        .collect(Collectors.toSet());
                detachedCriteria.add(Restrictions.not(Restrictions.in("r.roleStringId", notInRoleStringIds)));
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
