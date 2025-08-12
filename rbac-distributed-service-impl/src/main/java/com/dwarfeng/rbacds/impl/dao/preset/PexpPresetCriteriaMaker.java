package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
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
public class PexpPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case PexpMaintainService.PEXP_FOR_ROLE:
                childForRole(detachedCriteria, objects);
                break;
            case PexpMaintainService.PEXP_FOR_ROLE_SET:
                childForRoleSet(detachedCriteria, objects);
                break;
            case PexpMaintainService.PEXP_FOR_ROLE_DESCRIPTION_ASC:
                pexpForRoleDescriptionAsc(detachedCriteria, objects);
                break;
            case PexpMaintainService.PEXP_FOR_ROLE_DESCRIPTION_LIKE_DESCRIPTION_ASC:
                pexpForRoleDescriptionLikeDescriptionAsc(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForRole(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("roleId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("roleId", stringIdKey.getStringId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForRoleSet(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("roleId"));
            } else {
                @SuppressWarnings("unchecked")
                List<StringIdKey> stringIdKeys = (List<StringIdKey>) objects[0];
                if (stringIdKeys.isEmpty()) {
                    detachedCriteria.add(Restrictions.isNull("longId"));
                } else {
                    detachedCriteria.add(Restrictions.in("roleId", stringList(stringIdKeys)));
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void pexpForRoleDescriptionAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("roleId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("roleId", stringIdKey.getStringId()));
            }
            detachedCriteria.addOrder(org.hibernate.criterion.Order.asc("description"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void pexpForRoleDescriptionLikeDescriptionAsc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("roleId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("roleId", stringIdKey.getStringId()));
            }
            String pattern = (String) objects[1];
            detachedCriteria.add(Restrictions.like("description", pattern, MatchMode.ANYWHERE));
            detachedCriteria.addOrder(org.hibernate.criterion.Order.asc("description"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private List<String> stringList(List<StringIdKey> list) {
        return list.stream().map(StringIdKey::getStringId).collect(Collectors.toList());
    }
}
