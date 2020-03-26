package com.rx.pub.role.service;

import com.rx.pub.role.dto.PubRoleOwnerDto;
import com.rx.pub.role.po.PubRoleOwnerPo;

import java.util.List;

import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;

/**
 * 授权(PubRoleOwner)Service
 *
 * @author klf
 * @since 2019-12-30 15:06:14
 */
public interface PubRoleOwnerService extends BaseService<PubRoleOwnerPo> {

    /**
     * 分页查询
     */
    Pager<PubRoleOwnerDto> searchPage(PubRoleOwnerPo po);

	List<PubRoleOwnerPo> getListByOwners(List<String> ownerIds, String ownerType);

}