package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
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
public class PermissionPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case PermissionMaintainService.CHILD_FOR_SCOPE:
                childForScope(criteria, objs);
                break;
            case PermissionMaintainService.CHILD_FOR_GROUP:
                childForGroup(criteria, objs);
                break;
            case PermissionMaintainService.CHILD_FOR_SCOPE_PERMISSION_STRING_ID_ASC:
                childForScopePermissionStringIdAsc(criteria, objs);
                break;
            case PermissionMaintainService.PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC:
                permissionStringIdLikePermissionStringIdAsc(criteria, objs);
                break;
            case PermissionMaintainService.NAME_LIKE_PERMISSION_STRING_ID_ASC:
                nameLikePermissionStringIdAsc(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
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

    private void childForGroup(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("groupScopeStringId"));
                criteria.add(Restrictions.isNull("groupPermissionGroupStringId"));
            } else {
                PermissionGroupKey permissionGroupKey = (PermissionGroupKey) objs[0];
                String scopeStringId = permissionGroupKey.getScopeStringId();
                String permissionGroupStringId = permissionGroupKey.getPermissionGroupStringId();
                criteria.add(Restrictions.eqOrIsNull("groupScopeStringId", scopeStringId));
                criteria.add(Restrictions.eqOrIsNull("groupPermissionGroupStringId", permissionGroupStringId));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForScopePermissionStringIdAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("scopeStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("scopeStringId", stringIdKey.getStringId()));
            }
            detachedCriteria.addOrder(Order.asc("permissionStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void permissionStringIdLikePermissionStringIdAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            String pattern = (String) objs[0];
            criteria.add(Restrictions.like("permissionStringId", pattern, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("permissionStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void nameLikePermissionStringIdAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            String pattern = (String) objs[0];
            criteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("permissionStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
