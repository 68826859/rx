package com.rx.pub.role.mapper;

import com.rx.pub.role.dto.PubRoleOwnerDto;
import com.rx.pub.role.po.PubRoleOwnerPo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.rx.pub.mybatis.impls.MyBatisMapper;


/**
 * 授权(PubRoleOwner)Mapper
 *
 * @author klf
 * @since 2019-12-30 15:06:14
 */
public interface PubRoleOwnerMapper extends MyBatisMapper<PubRoleOwnerPo>{

    List<PubRoleOwnerDto> searchList(@Param("param")PubRoleOwnerPo po);

}