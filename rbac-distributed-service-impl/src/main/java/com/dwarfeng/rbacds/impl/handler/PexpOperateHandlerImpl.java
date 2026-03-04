package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.handler.PexpOperateHandler;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

@Component
public class PexpOperateHandlerImpl implements PexpOperateHandler {

    private final PexpMaintainService pexpMaintainService;

    private final HandlerValidator handlerValidator;

    public PexpOperateHandlerImpl(
            PexpMaintainService pexpMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.pexpMaintainService = pexpMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public PexpCreateResult create(PexpCreateInfo info) throws HandlerException {
        try {
            // 展开参数。
            PexpKey key = info.getKey();
            String content = info.getContent();
            String remark = info.getRemark();

            // 确认作用域存在。
            StringIdKey scopeKey = new StringIdKey(key.getScopeStringId());
            handlerValidator.makeSureScopeExists(scopeKey);
            // 确认角色存在。
            StringIdKey roleKey = new StringIdKey(key.getRoleStringId());
            handlerValidator.makeSureRoleExists(roleKey);
            // 确认权限表达式不存在。
            handlerValidator.makeSurePexpNotExists(key);

            // 根据参数构造权限表达式实体。
            Pexp pexp = new Pexp(key, content, remark);

            // 调用维护服务插入权限表达式实体。
            pexpMaintainService.insert(pexp);

            // 返回权限表达式主键。
            return new PexpCreateResult(key);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void update(PexpUpdateInfo info) throws HandlerException {
        try {
            // 展开参数。
            PexpKey key = info.getKey();
            String content = info.getContent();
            String remark = info.getRemark();

            // 确认权限表达式存在。
            handlerValidator.makeSurePexpExists(key);

            // 根据参数更新权限表达式实体。
            Pexp pexp = pexpMaintainService.get(key);
            pexp.setContent(content);
            pexp.setRemark(remark);

            // 调用维护服务更新权限表达式实体。
            pexpMaintainService.update(pexp);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void remove(PexpRemoveInfo info) throws HandlerException {
        try {
            // 展开参数。
            PexpKey key = info.getKey();

            // 确认权限表达式存在。
            handlerValidator.makeSurePexpExists(key);

            // 调用维护服务删除权限表达式实体。
            pexpMaintainService.delete(key);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}
