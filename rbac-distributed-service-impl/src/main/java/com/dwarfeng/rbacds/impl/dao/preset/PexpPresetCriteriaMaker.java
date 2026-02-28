package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PexpPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case PexpMaintainService.CHILD_FOR_SCOPE:
                childForScope(detachedCriteria, objects);
                break;
            case PexpMaintainService.CHILD_FOR_ROLE:
                childForRole(detachedCriteria, objects);
                break;
            case PexpMaintainService.CHILD_FOR_SCOPE_CHILD_FOR_ROLE:
                childForScopeChildForRole(detachedCriteria, objects);
                break;
            case PexpMaintainService.CHILD_FOR_SCOPE_CHILD_FOR_ROLE_PEXP_STRING_ID_ASC:
                childForScopeChildForRolePexpStringIdAsc(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForScope(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("scopeStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("scopeStringId", stringIdKey.getStringId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForRole(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("roleStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("roleStringId", stringIdKey.getStringId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForScopeChildForRole(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("scopeStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("scopeStringId", stringIdKey.getStringId()));
            }
            if (Objects.isNull(objects[1])) {
                detachedCriteria.add(Restrictions.isNull("roleStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[1];
                detachedCriteria.add(Restrictions.eqOrIsNull("roleStringId", stringIdKey.getStringId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForScopeChildForRolePexpStringIdAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("scopeStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("scopeStringId", stringIdKey.getStringId()));
            }
            if (Objects.isNull(objects[1])) {
                detachedCriteria.add(Restrictions.isNull("roleStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[1];
                detachedCriteria.add(Restrictions.eqOrIsNull("roleStringId", stringIdKey.getStringId()));
            }
            detachedCriteria.addOrder(Order.asc("pexpStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
