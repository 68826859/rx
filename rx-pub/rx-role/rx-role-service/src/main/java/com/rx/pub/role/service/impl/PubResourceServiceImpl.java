package com.rx.pub.role.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.pub.mybatis.impls.MybatisBaseService;
import com.rx.pub.role.dto.PubResourceDto;
import com.rx.pub.role.dto.PubResourceSearchDto;
import com.rx.pub.role.mapper.PubResourceMapper;
import com.rx.pub.role.po.PubResourcePo;
import com.rx.pub.role.service.PubResourceService;





/**
 * 角色资源(PubResource)Service实现类
 *
 * @author klf
 * @since 2021-03-31 15:45:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PubResourceServiceImpl extends MybatisBaseService<PubResourcePo> implements PubResourceService {

    private static final Logger log = LoggerFactory.getLogger(PubResourceServiceImpl.class);

    @Autowired
    private PubResourceMapper pubResourceMapper;
    
    @Override
    public Pager<PubResourceDto> searchPage(PubResourceSearchDto dto) {
        return this.getPageExcuter().selectByPage(new PageExcute<PubResourceDto>() {
            @Override
            public List<PubResourceDto> excute() {
                return pubResourceMapper.searchList(dto);
            }
        },this.getPagerProvider().getPager(PubResourceDto.class));
    }
    
     @Override
    public List<PubResourceDto> searchList(PubResourceSearchDto dto) {
         return pubResourceMapper.searchList(dto);
    }
    
}