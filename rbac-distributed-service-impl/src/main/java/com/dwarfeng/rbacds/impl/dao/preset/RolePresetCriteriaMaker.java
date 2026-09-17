package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateRole;
import com.dwarfeng.rbacds.impl.bean.entity.HibernateRoleUserRelation;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class RolePresetCriteriaMaker implements PresetCriteriaMaker<HibernateRole> {

    @Override
    public void makeCriteria(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateRole> root, String preset, Object[] objects
    ) {
        switch (preset) {
            case RoleMaintainService.CHILD_FOR_USER_AVAILABLE_KEY_ASC:
                childForUserAvailableKeyAsc(builder, query, root, objects);
                break;
            case RoleMaintainService.ENABLED:
                enabled(builder, query, root, objects);
                break;
            case RoleMaintainService.ID_LIKE:
                idLike(builder, query, root, objects);
                break;
            case RoleMaintainService.NOT_CHILD_FOR_USER_KEY_ASC:
                notChildForUserKeyAsc(builder, query, root, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void childForUserAvailableKeyAsc(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateRole> root, Object[] objects
    ) {
        try {
            if (Objects.isNull(objects[0])) {
                CriteriaQueryHelper.addRestriction(builder, query, builder.isNull(root.get("stringId")));
            } else {
                StringIdKey key = (StringIdKey) objects[0];
                Join<HibernateRole, HibernateRoleUserRelation> relation = root.join("roleUserRelations");
                CriteriaQueryHelper.addRestriction(builder, query, builder.isTrue(root.get("enabled")));
                CriteriaQueryHelper.addRestriction(
                        builder, query, builder.equal(relation.get("userStringId"), key.getStringId())
                );
                CriteriaQueryHelper.addRestriction(builder, query, builder.isTrue(relation.get("enabled")));
            }
            CriteriaQueryHelper.addOrder(query, builder.asc(root.get("stringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void enabled(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateRole> root, Object[] objects
    ) {
        try {
            CriteriaQueryHelper.addRestriction(builder, query, builder.isTrue(root.get("enabled")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void idLike(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateRole> root, Object[] objects
    ) {
        try {
            CriteriaQueryHelper.addRestriction(
                    builder, query, CriteriaQueryHelper.likeAnywhere(builder, root.get("stringId"), (String) objects[0])
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void notChildForUserKeyAsc(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateRole> root, Object[] objects
    ) {
        try {
            if (Objects.isNull(objects[0])) {
                CriteriaQueryHelper.addRestriction(builder, query, builder.isNull(root.get("stringId")));
            } else {
                StringIdKey key = (StringIdKey) objects[0];
                Subquery<String> subquery = query.subquery(String.class);
                Root<HibernateRoleUserRelation> relation = subquery.from(HibernateRoleUserRelation.class);
                subquery.select(relation.get("roleStringId"))
                        .where(builder.equal(relation.get("userStringId"), key.getStringId()));
                CriteriaQueryHelper.addRestriction(builder, query, builder.not(root.get("stringId").in(subquery)));
            }
            CriteriaQueryHelper.addOrder(query, builder.asc(root.get("stringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
