package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernatePermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PermissionGroupPresetCriteriaMaker implements PresetCriteriaMaker<HibernatePermissionGroup> {

    @Override
    public void makeCriteria(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermissionGroup> root,
            String preset, Object[] objects
    ) {
        switch (preset) {
            case PermissionGroupMaintainService.CHILD_FOR_SCOPE:
                execute(builder, query, root, objects, false, null, false);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_PARENT:
                execute(builder, query, root, objects, true, null, false);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_ASC:
                execute(builder, query, root, objects, true, null, true);
                break;
            case PermissionGroupMaintainService.PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                execute(builder, query, root, objects, false, "identifier", true);
                break;
            case PermissionGroupMaintainService.
                         CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                execute(builder, query, root, objects, true, "identifier", true);
                break;
            case PermissionGroupMaintainService.NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                execute(builder, query, root, objects, false, "name", true);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_PARENT_NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC:
                execute(builder, query, root, objects, true, "name", true);
                break;
            case PermissionGroupMaintainService.CHILD_FOR_SCOPE_ROOT_PERMISSION_GROUP_STRING_ID_ASC:
                childForScopeRoot(builder, query, root, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void execute(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermissionGroup> root, Object[] objects,
            boolean parent, String likeAttribute, boolean ordered
    ) {
        try {
            int index = 0;
            if (parent) {
                addParent(builder, query, root, objects[index++]);
            } else if (likeAttribute == null) {
                addScope(builder, query, root, objects[index++]);
            }
            if (likeAttribute != null) {
                CriteriaQueryHelper.addRestriction(
                        builder, query,
                        CriteriaQueryHelper.likeAnywhere(builder, root.get(likeAttribute), (String) objects[index])
                );
            }
            if (ordered) {
                CriteriaQueryHelper.addOrder(query, builder.asc(root.get("permissionGroupStringId")));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForScopeRoot(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermissionGroup> root, Object[] objects
    ) {
        try {
            addScope(builder, query, root, objects[0]);
            CriteriaQueryHelper.addRestriction(builder, query, builder.isNull(root.get("parentScopeStringId")));
            CriteriaQueryHelper.addRestriction(
                    builder, query, builder.isNull(root.get("parentPermissionGroupStringId"))
            );
            CriteriaQueryHelper.addOrder(query, builder.asc(root.get("permissionGroupStringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void addScope(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermissionGroup> root, Object value
    ) {
        String stringId = Objects.isNull(value) ? null : ((StringIdKey) value).getStringId();
        CriteriaQueryHelper.addRestriction(
                builder, query, CriteriaQueryHelper.equalOrIsNull(builder, root.get("scopeStringId"), stringId)
        );
    }

    private void addParent(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermissionGroup> root, Object value
    ) {
        PermissionGroupKey key = Objects.isNull(value) ? null : (PermissionGroupKey) value;
        CriteriaQueryHelper.addRestriction(
                builder, query, CriteriaQueryHelper.equalOrIsNull(
                        builder, root.get("parentScopeStringId"), Objects.isNull(key) ? null : key.getScopeStringId()
                )
        );
        CriteriaQueryHelper.addRestriction(
                builder, query, CriteriaQueryHelper.equalOrIsNull(
                        builder, root.get("parentPermissionGroupStringId"),
                        Objects.isNull(key) ? null : key.getPermissionGroupStringId()
                )
        );
    }
}
