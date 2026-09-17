package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernatePexp;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PexpPresetCriteriaMaker implements PresetCriteriaMaker<HibernatePexp> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernatePexp> root,
            String s, Object[] objects
    ) {
        switch (s) {
            case PexpMaintainService.CHILD_FOR_SCOPE:
                childForScope(criteriaBuilder, criteriaQuery, root, objects);
                break;
            case PexpMaintainService.CHILD_FOR_ROLE:
                childForRole(criteriaBuilder, criteriaQuery, root, objects);
                break;
            case PexpMaintainService.CHILD_FOR_SCOPE_CHILD_FOR_ROLE:
                childForScopeChildForRole(criteriaBuilder, criteriaQuery, root, objects);
                break;
            case PexpMaintainService.CHILD_FOR_SCOPE_CHILD_FOR_ROLE_PEXP_STRING_ID_ASC:
                childForScopeChildForRolePexpStringIdAsc(criteriaBuilder, criteriaQuery, root, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForScope(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePexp> root, Object[] objects
    ) {
        try {
            addKey(builder, query, root, "scopeStringId", objects[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForRole(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePexp> root, Object[] objects
    ) {
        try {
            addKey(builder, query, root, "roleStringId", objects[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForScopeChildForRole(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePexp> root, Object[] objects
    ) {
        try {
            addKey(builder, query, root, "scopeStringId", objects[0]);
            addKey(builder, query, root, "roleStringId", objects[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForScopeChildForRolePexpStringIdAsc(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePexp> root, Object[] objects
    ) {
        try {
            addKey(builder, query, root, "scopeStringId", objects[0]);
            addKey(builder, query, root, "roleStringId", objects[1]);
            CriteriaQueryHelper.addOrder(query, builder.asc(root.get("pexpStringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void addKey(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePexp> root, String attribute, Object value
    ) {
        String stringId = Objects.isNull(value) ? null : ((StringIdKey) value).getStringId();
        CriteriaQueryHelper.addRestriction(
                builder, query, CriteriaQueryHelper.equalOrIsNull(builder, root.get(attribute), stringId)
        );
    }
}
