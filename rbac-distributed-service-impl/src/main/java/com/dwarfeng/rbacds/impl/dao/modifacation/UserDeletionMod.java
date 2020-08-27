package com.dwarfeng.rbacds.impl.dao.modifacation;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateRole;
import com.dwarfeng.rbacds.impl.bean.entity.HibernateUser;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DeletionMod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDeletionMod implements DeletionMod<HibernateUser> {

    @Override
    public List<Object> updateBeforeDelete(HibernateUser hibernateUser) {
        List<Object> objects = new ArrayList<>();
        for (HibernateRole role : hibernateUser.getRoles()) {
            role.getUsers().remove(hibernateUser);
            objects.add(role);
        }
        return objects;
    }
}
