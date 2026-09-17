package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateRoleUserRelation;
import com.dwarfeng.rbacds.impl.bean.entity.HibernateUser;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserPresetCriteriaMaker implements PresetCriteriaMaker<HibernateUser> {

    @Override
    public void makeCriteria(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateUser> root, String preset, Object[] objects
    ) {
        switch (preset) {
            case UserMaintainService.ROLE_IN_ROLE_NOT_IN_KEY_ASC:
                roleInRoleNotInKeyAsc(builder, query, root, objects);
                break;
            case UserMaintainService.ID_LIKE:
                idLike(builder, query, root, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void roleInRoleNotInKeyAsc(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateUser> root, Object[] objects
    ) {
        try {
            // 此处类型安全由调用方保证。
            @SuppressWarnings("unchecked")
            Collection<StringIdKey> inKeys = (Collection<StringIdKey>) objects[0];
            // 此处类型安全由调用方保证。
            @SuppressWarnings("unchecked")
            Collection<StringIdKey> notInKeys = (Collection<StringIdKey>) objects[1];
            if (inKeys.isEmpty()) {
                CriteriaQueryHelper.addRestriction(builder, query, builder.isNull(root.get("stringId")));
            } else {
                jakarta.persistence.criteria.Subquery<String> subquery = query.subquery(String.class);
                Root<HibernateRoleUserRelation> relation = subquery.from(HibernateRoleUserRelation.class);
                Collection<String> inIds = inKeys.stream().map(StringIdKey::getStringId).collect(Collectors.toSet());
                jakarta.persistence.criteria.Predicate predicate = relation.get("roleStringId").in(inIds);
                if (!notInKeys.isEmpty()) {
                    Collection<String> notInIds = notInKeys.stream().map(StringIdKey::getStringId)
                            .collect(Collectors.toSet());
                    predicate = builder.and(predicate, builder.not(relation.get("roleStringId").in(notInIds)));
                }
                subquery.select(relation.get("userStringId")).where(predicate);
                CriteriaQueryHelper.addRestriction(builder, query, root.get("stringId").in(subquery));
            }
            CriteriaQueryHelper.addOrder(query, builder.asc(root.get("stringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void idLike(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernateUser> root, Object[] objects
    ) {
        try {
            CriteriaQueryHelper.addRestriction(
                    builder, query, CriteriaQueryHelper.likeAnywhere(builder, root.get("stringId"), (String) objects[0])
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
