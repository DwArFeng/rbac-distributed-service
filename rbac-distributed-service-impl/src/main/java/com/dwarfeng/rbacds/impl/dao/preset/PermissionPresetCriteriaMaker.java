package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PermissionPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        if (PermissionMaintainService.ID_LIKE.equals(preset)) {
            idLike(criteria, objs);
        } else {
            throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void idLike(DetachedCriteria criteria, Object[] objs) {
        try {
            String id = (String) objs[0];
            criteria.add(Restrictions.like("stringId", id, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("stringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
