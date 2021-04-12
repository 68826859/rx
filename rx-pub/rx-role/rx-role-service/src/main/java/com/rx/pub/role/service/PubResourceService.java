package com.rx.pub.role.service;

import java.util.List;

import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.pub.role.dto.PubResourceDto;
import com.rx.pub.role.dto.PubResourceSearchDto;
import com.rx.pub.role.po.PubResourcePo;
/**
 * 角色资源(PubResource)Service
 *
 * @author klf
 * @since 2021-03-31 15:45:21
 */
public interface PubResourceService extends BaseService<PubResourcePo> {

    /**
     * 分页查询
     */
    Pager<PubResourceDto> searchPage(PubResourceSearchDto dto);

    /**
     * 查询列表
     */
    List<PubResourceDto> searchList(PubResourceSearchDto dto);
}