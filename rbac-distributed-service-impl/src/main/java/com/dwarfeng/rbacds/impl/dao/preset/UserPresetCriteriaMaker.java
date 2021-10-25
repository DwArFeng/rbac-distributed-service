package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case UserMaintainService.ID_LIKE:
                idLike(detachedCriteria, objects);
                break;
            case UserMaintainService.CHILD_FOR_ROLE:
                childForRole(detachedCriteria, objects);
                break;
            case UserMaintainService.CHILD_FOR_ROLE_SET:
                childForRoleSet(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
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

    private void childForRole(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isEmpty("roles"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.createAlias("roles", "r");
                detachedCriteria.add(Restrictions.eq("r.stringId", stringIdKey.getStringId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForRoleSet(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            @SuppressWarnings("unchecked")
            List<String> roleIds = ((List<StringIdKey>) objects[0]).stream()
                    .map(StringIdKey::getStringId).collect(Collectors.toList());
            if (roleIds.isEmpty()) {
                detachedCriteria.add(Restrictions.isNull("stringId"));
                return;
            }
            detachedCriteria.createAlias("roles", "r");
            detachedCriteria.add(Restrictions.in("r.stringId", roleIds));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
