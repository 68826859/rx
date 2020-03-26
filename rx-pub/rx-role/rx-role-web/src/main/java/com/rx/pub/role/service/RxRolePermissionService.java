package com.rx.pub.role.service;

import java.util.List;

import com.rx.base.user.RxRoleable;
import com.rx.base.user.RxUserable;
import com.rx.pub.role.dto.PubRoleResourceDto;
import com.rx.pub.role.enm.RoleResourceReverseEumn;

/**
 */
public interface RxRolePermissionService {

    void addUserRole(RxUserable user,RxRoleable role);

    List<PubRoleResourceDto> getListRoleResourceDtoByRole(String roleId,RoleResourceReverseEumn roleResourceReverseEumn);
}