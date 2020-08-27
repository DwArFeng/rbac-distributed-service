package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class RolePresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case RoleMaintainService.ROLE_FOR_USER:
                roleForUser(detachedCriteria, objects);
                break;
            case RoleMaintainService.ENABLED_ROLE_FOR_USER:
                enabledRoleForUser(detachedCriteria, objects);
                break;
            case RoleMaintainService.ID_LIKE:
                idLike(detachedCriteria, objects);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void roleForUser(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isEmpty("users"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.createAlias("users", "u");
                detachedCriteria.add(Restrictions.eqOrIsNull("u.stringId", stringIdKey.getStringId()));
            }
            detachedCriteria.addOrder(Order.asc("stringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void enabledRoleForUser(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isEmpty("users"));
                detachedCriteria.add(Restrictions.eq("enabled", true));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.createAlias("users", "u");
                detachedCriteria.add(Restrictions.eqOrIsNull("u.stringId", stringIdKey.getStringId()));
                detachedCriteria.add(Restrictions.eq("enabled", true));
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
            detachedCriteria.addOrder(Order.asc("stringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
