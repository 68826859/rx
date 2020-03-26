package com.rx.pub.role.mapper;

import com.rx.pub.role.po.PubRolePo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.rx.pub.mybatis.impls.MyBatisMapper;


/**
 * (PubRole)Mapper
 *
 * @author klf
 * @since 2019-12-30 14:47:25
 */
public interface PubRoleMapper extends MyBatisMapper<PubRolePo>{

    List<PubRolePo> searchList(@Param("param")PubRolePo po);

}