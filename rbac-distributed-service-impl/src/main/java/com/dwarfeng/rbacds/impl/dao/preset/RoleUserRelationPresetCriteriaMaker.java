package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateRoleUserRelation;
import com.dwarfeng.rbacds.stack.service.RoleUserRelationMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class RoleUserRelationPresetCriteriaMaker implements PresetCriteriaMaker<HibernateRoleUserRelation> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateRoleUserRelation> root,
            String preset, Object[] objs
    ) {
        switch (preset) {
            case RoleUserRelationMaintainService.CHILD_FOR_ROLE:
                childForRole(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case RoleUserRelationMaintainService.CHILD_FOR_USER:
                childForUser(criteriaBuilder, criteriaQuery, root, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void childForRole(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateRoleUserRelation> root,
            Object[] objs
    ) {
        try {
            String roleStringId = Objects.isNull(objs[0]) ? null : ((StringIdKey) objs[0]).getStringId();
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    CriteriaQueryHelper.equalOrIsNull(criteriaBuilder, root.get("roleStringId"), roleStringId)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForUser(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateRoleUserRelation> root,
            Object[] objs
    ) {
        try {
            String userStringId = Objects.isNull(objs[0]) ? null : ((StringIdKey) objs[0]).getStringId();
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    CriteriaQueryHelper.equalOrIsNull(criteriaBuilder, root.get("userStringId"), userStringId)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
