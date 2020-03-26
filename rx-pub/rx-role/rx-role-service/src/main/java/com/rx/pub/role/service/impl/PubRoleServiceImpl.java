package com.rx.pub.role.service.impl;

import com.rx.pub.role.po.PubRolePo;
import com.rx.pub.role.mapper.PubRoleMapper;
import com.rx.pub.role.service.PubRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rx.base.page.Pager;
import com.rx.base.page.PageExcute;
import java.util.List;
import com.rx.pub.mybatis.impls.MybatisBaseService;





/**
 * (PubRole)Service实现类
 *
 * @author klf
 * @since 2019-12-30 14:47:24
 */
@Service
public class PubRoleServiceImpl extends MybatisBaseService<PubRolePo> implements PubRoleService {

    private static final Logger log = LoggerFactory.getLogger(PubRoleServiceImpl.class);

    @Resource
    private PubRoleMapper pubRoleMapper;
    
    @Override
    public Pager<PubRolePo> searchPage(PubRolePo po) {
        return this.getPageExcuter().selectByPage(new PageExcute<PubRolePo>() {
            @Override
            public List<PubRolePo> excute() {
                return pubRoleMapper.searchList(po);
            }
        },this.getPagerProvider().getPager(PubRolePo.class));
    }
    
}