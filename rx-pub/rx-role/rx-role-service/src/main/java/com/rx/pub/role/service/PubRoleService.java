package com.rx.pub.role.service;

import com.rx.pub.role.po.PubRolePo;
import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;

/**
 * (PubRole)Service
 *
 * @author klf
 * @since 2019-12-30 14:47:23
 */
public interface PubRoleService extends BaseService<PubRolePo> {

    /**
     * 分页查询
     */
    Pager<PubRolePo> searchPage(PubRolePo po);

}