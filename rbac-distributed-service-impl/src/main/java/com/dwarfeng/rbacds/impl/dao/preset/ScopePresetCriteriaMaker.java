package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateScope;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ScopePresetCriteriaMaker implements PresetCriteriaMaker<HibernateScope> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateScope> root,
            String preset, Object[] objs
    ) {
        switch (preset) {
            case ScopeMaintainService.ID_LIKE:
                idLike(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case ScopeMaintainService.NAME_LIKE:
                nameLike(criteriaBuilder, criteriaQuery, root, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void idLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateScope> root, Object[] objects
    ) {
        try {
            String pattern = (String) objects[0];
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("stringId"), pattern)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void nameLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateScope> root, Object[] objs
    ) {
        try {
            String name = (String) objs[0];
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("name"), name)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
