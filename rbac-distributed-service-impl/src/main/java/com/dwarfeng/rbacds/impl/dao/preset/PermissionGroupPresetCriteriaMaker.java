package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PermissionGroupPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case PermissionGroupMaintainService.CHILD_FOR_PARENT:
                childForParent(criteria, objs);
                break;
            case PermissionGroupMaintainService.ID_LIKE:
                idLike(criteria, objs);
                break;
            case PermissionGroupMaintainService.NAME_LIKE:
                nameLike(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void childForParent(DetachedCriteria criteria, Object[] objs) {
        try {
            StringIdKey parentIdKey = (StringIdKey) objs[0];
            String stringId = Objects.isNull(parentIdKey) ? null : parentIdKey.getStringId();
            criteria.add(Restrictions.eqOrIsNull("parentStringId", stringId));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void idLike(DetachedCriteria criteria, Object[] objs) {
        try {
            String id = (String) objs[0];
            criteria.add(Restrictions.like("stringId", id, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void nameLike(DetachedCriteria criteria, Object[] objs) {
        try {
            String name = (String) objs[0];
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
