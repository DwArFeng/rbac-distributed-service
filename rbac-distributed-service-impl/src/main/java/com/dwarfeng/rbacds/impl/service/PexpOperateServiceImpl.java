package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.rbacds.stack.handler.PexpOperateHandler;
import com.dwarfeng.rbacds.stack.service.PexpOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class PexpOperateServiceImpl implements PexpOperateService {

    private final PexpOperateHandler pexpOperateHandler;

    private final ServiceExceptionMapper sem;

    public PexpOperateServiceImpl(
            PexpOperateHandler pexpOperateHandler,
            ServiceExceptionMapper sem
    ) {
        this.pexpOperateHandler = pexpOperateHandler;
        this.sem = sem;
    }

    @Override
    public PexpCreateResult create(PexpCreateInfo info) throws ServiceException {
        try {
            return pexpOperateHandler.create(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("创建权限表达式时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void update(PexpUpdateInfo info) throws ServiceException {
        try {
            pexpOperateHandler.update(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新权限表达式时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void remove(PexpRemoveInfo info) throws ServiceException {
        try {
            pexpOperateHandler.remove(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("移除权限表达式时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
