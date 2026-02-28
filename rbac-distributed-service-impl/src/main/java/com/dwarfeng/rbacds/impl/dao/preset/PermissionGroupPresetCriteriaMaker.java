package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
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
public class PermissionGroupPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case PermissionGroupMaintainService.CHILD_FOR_SCOPE:
                childForScope(criteria, objs);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_PARENT:
                childForParent(criteria, objs);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_ASC:
                childForParentPermissionGroupStringIdAsc(criteria, objs);
                break;
            case PermissionGroupMaintainService.PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                permissionGroupStringIdLikePermissionGroupStringIdAsc(criteria, objs);
                break;
            case PermissionGroupMaintainService.
                         CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                childForParentPermissionGroupStringIdLikePermissionGroupStringIdAsc(criteria, objs);
                break;
            case PermissionGroupMaintainService.NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                nameLikePermissionGroupStringIdAsc(criteria, objs);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_PARENT_NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                childForParentNameLikePermissionGroupStringIdAsc(criteria, objs);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_SCOPE_ROOT_PERMISSION_GROUP_STRING_ID_ASC:
                childForScopeRootPermissionGroupStringIdAsc(criteria, objs);
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

    @SuppressWarnings("DuplicatedCode")
    private void childForParent(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("parentScopeStringId"));
                criteria.add(Restrictions.isNull("parentPermissionGroupStringId"));
            } else {
                PermissionGroupKey permissionGroupKey = (PermissionGroupKey) objs[0];
                String scopeStringId = permissionGroupKey.getScopeStringId();
                String permissionGroupStringId = permissionGroupKey.getPermissionGroupStringId();
                criteria.add(Restrictions.eqOrIsNull("parentScopeStringId", scopeStringId));
                criteria.add(Restrictions.eqOrIsNull("parentPermissionGroupStringId", permissionGroupStringId));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForParentPermissionGroupStringIdAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("parentScopeStringId"));
                criteria.add(Restrictions.isNull("parentPermissionGroupStringId"));
            } else {
                PermissionGroupKey permissionGroupKey = (PermissionGroupKey) objs[0];
                String scopeStringId = permissionGroupKey.getScopeStringId();
                String permissionGroupStringId = permissionGroupKey.getPermissionGroupStringId();
                criteria.add(Restrictions.eqOrIsNull("parentScopeStringId", scopeStringId));
                criteria.add(Restrictions.eqOrIsNull("parentPermissionGroupStringId", permissionGroupStringId));
            }
            criteria.addOrder(Order.asc("permissionGroupStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void permissionGroupStringIdLikePermissionGroupStringIdAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            String pattern = (String) objs[0];
            criteria.add(Restrictions.like("identifier", pattern, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("permissionGroupStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForParentPermissionGroupStringIdLikePermissionGroupStringIdAsc(
            DetachedCriteria criteria, Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("parentScopeStringId"));
                criteria.add(Restrictions.isNull("parentPermissionGroupStringId"));
            } else {
                PermissionGroupKey permissionGroupKey = (PermissionGroupKey) objs[0];
                String scopeStringId = permissionGroupKey.getScopeStringId();
                String permissionGroupStringId = permissionGroupKey.getPermissionGroupStringId();
                criteria.add(Restrictions.eqOrIsNull("parentScopeStringId", scopeStringId));
                criteria.add(Restrictions.eqOrIsNull("parentPermissionGroupStringId", permissionGroupStringId));
            }
            String pattern = (String) objs[1];
            criteria.add(Restrictions.like("identifier", pattern, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("permissionGroupStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void nameLikePermissionGroupStringIdAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            String pattern = (String) objs[0];
            criteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("permissionGroupStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForParentNameLikePermissionGroupStringIdAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("parentScopeStringId"));
                criteria.add(Restrictions.isNull("parentPermissionGroupStringId"));
            } else {
                PermissionGroupKey permissionGroupKey = (PermissionGroupKey) objs[0];
                String scopeStringId = permissionGroupKey.getScopeStringId();
                String permissionGroupStringId = permissionGroupKey.getPermissionGroupStringId();
                criteria.add(Restrictions.eqOrIsNull("parentScopeStringId", scopeStringId));
                criteria.add(Restrictions.eqOrIsNull("parentPermissionGroupStringId", permissionGroupStringId));
            }
            String pattern = (String) objs[1];
            criteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("permissionGroupStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForScopeRootPermissionGroupStringIdAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("scopeStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("scopeStringId", stringIdKey.getStringId()));
            }
            detachedCriteria.add(Restrictions.isNull("parentScopeStringId"));
            detachedCriteria.add(Restrictions.isNull("parentPermissionGroupStringId"));
            detachedCriteria.addOrder(Order.asc("permissionGroupStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
