package com.dwarfeng.rbacds.impl.dao.preset;

import com.dwarfeng.rbacds.impl.bean.entity.HibernatePermission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PermissionPresetCriteriaMaker implements PresetCriteriaMaker<HibernatePermission> {

    @Override
    public void makeCriteria(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermission> root,
            String preset, Object[] objects
    ) {
        switch (preset) {
            case PermissionMaintainService.CHILD_FOR_SCOPE:
                execute(builder, query, root, objects, Mode.SCOPE, null, false);
                break;
            case PermissionMaintainService.CHILD_FOR_GROUP:
                execute(builder, query, root, objects, Mode.GROUP, null, false);
                break;
            case PermissionMaintainService.CHILD_FOR_SCOPE_PERMISSION_STRING_ID_ASC:
                execute(builder, query, root, objects, Mode.SCOPE, null, true);
                break;
            case PermissionMaintainService.PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC:
                execute(builder, query, root, objects, Mode.NONE, "permissionStringId", true);
                break;
            case PermissionMaintainService.NAME_LIKE_PERMISSION_STRING_ID_ASC:
                execute(builder, query, root, objects, Mode.NONE, "name", true);
                break;
            case PermissionMaintainService.CHILD_FOR_SCOPE_PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC:
                execute(builder, query, root, objects, Mode.SCOPE, "permissionStringId", true);
                break;
            case PermissionMaintainService.CHILD_FOR_GROUP_PERMISSION_STRING_ID_ASC:
                execute(builder, query, root, objects, Mode.GROUP, null, true);
                break;
            case PermissionMaintainService.CHILD_FOR_SCOPE_GROUP_IS_NULL_PERMISSION_STRING_ID_ASC:
                execute(builder, query, root, objects, Mode.SCOPE_NULL_GROUP, null, true);
                break;
            case PermissionMaintainService.
                         CHILD_FOR_SCOPE_GROUP_IS_NULL_PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC:
                execute(builder, query, root, objects, Mode.SCOPE_NULL_GROUP, "permissionStringId", true);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void execute(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermission> root, Object[] objects,
            Mode mode, String likeAttribute, boolean ordered
    ) {
        try {
            int index = 0;
            switch (mode) {
                case SCOPE:
                    addScope(builder, query, root, objects[index++]);
                    break;
                case GROUP:
                    addGroup(builder, query, root, objects[index++]);
                    break;
                case SCOPE_NULL_GROUP:
                    addScope(builder, query, root, objects[index++]);
                    addGroup(builder, query, root, null);
                    break;
                case NONE:
                    break;
                default:
                    throw new IllegalStateException("未知的查询模式: " + mode);
            }
            if (likeAttribute != null) {
                CriteriaQueryHelper.addRestriction(
                        builder, query,
                        CriteriaQueryHelper.likeAnywhere(builder, root.get(likeAttribute), (String) objects[index])
                );
            }
            if (ordered) {
                CriteriaQueryHelper.addOrder(query, builder.asc(root.get("permissionStringId")));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void addScope(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermission> root, Object value
    ) {
        String stringId = Objects.isNull(value) ? null : ((StringIdKey) value).getStringId();
        CriteriaQueryHelper.addRestriction(
                builder, query, CriteriaQueryHelper.equalOrIsNull(builder, root.get("scopeStringId"), stringId)
        );
    }

    private void addGroup(
            CriteriaBuilder builder, CriteriaQuery<?> query, Root<HibernatePermission> root, Object value
    ) {
        PermissionGroupKey key = Objects.isNull(value) ? null : (PermissionGroupKey) value;
        CriteriaQueryHelper.addRestriction(
                builder, query, CriteriaQueryHelper.equalOrIsNull(
                        builder, root.get("groupScopeStringId"), Objects.isNull(key) ? null : key.getScopeStringId()
                )
        );
        CriteriaQueryHelper.addRestriction(
                builder, query, CriteriaQueryHelper.equalOrIsNull(
                        builder, root.get("groupPermissionGroupStringId"),
                        Objects.isNull(key) ? null : key.getPermissionGroupStringId()
                )
        );
    }

    private enum Mode {
        NONE,
        SCOPE,
        GROUP,
        SCOPE_NULL_GROUP
    }
}
