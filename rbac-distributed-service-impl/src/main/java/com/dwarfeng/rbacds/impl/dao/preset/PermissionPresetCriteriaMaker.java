package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PermissionPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case PermissionMaintainService.ID_LIKE:
                idLike(criteria, objs);
                break;
            case PermissionMaintainService.CHILD_FOR_GROUP:
                childForGroup(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
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

    private void childForGroup(DetachedCriteria criteria, Object[] objs) {
        try {
            StringIdKey groupIdKey = (StringIdKey) objs[0];
            String stringId = Objects.isNull(groupIdKey) ? null : groupIdKey.getStringId();
            criteria.add(Restrictions.eqOrIsNull("groupStringId", stringId));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
