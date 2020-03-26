package com.rx.pub.role.mapper;

import com.rx.pub.role.po.PubUserGroupPo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.rx.pub.mybatis.impls.MyBatisMapper;


/**
 * 用户组(PubUserGroup)Mapper
 *
 * @author klf
 * @since 2019-12-30 15:14:21
 */
public interface PubUserGroupMapper extends MyBatisMapper<PubUserGroupPo>{

    List<PubUserGroupPo> searchList(@Param("param")PubUserGroupPo po);

}