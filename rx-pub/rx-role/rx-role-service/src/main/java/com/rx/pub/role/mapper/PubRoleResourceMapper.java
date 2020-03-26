package com.rx.pub.role.mapper;

import com.rx.pub.role.po.PubRoleResourcePo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.rx.pub.mybatis.impls.MyBatisMapper;


/**
 * 角色资源(PubRoleResource)Mapper
 *
 * @author klf
 * @since 2019-12-30 15:07:24
 */
public interface PubRoleResourceMapper extends MyBatisMapper<PubRoleResourcePo>{

    List<PubRoleResourcePo> searchList(@Param("param")PubRoleResourcePo po);

}