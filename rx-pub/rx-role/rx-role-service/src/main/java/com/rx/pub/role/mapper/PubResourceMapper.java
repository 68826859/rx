package com.rx.pub.role.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.rx.pub.mybatis.impls.MyBatisMapper;
import com.rx.pub.role.dto.PubResourceDto;
import com.rx.pub.role.dto.PubResourceSearchDto;
import com.rx.pub.role.po.PubResourcePo;

/**
 * 角色资源(PubResource)Mapper
 *
 * @author klf
 * @since 2021-03-31 15:45:21
 */
public interface PubResourceMapper extends MyBatisMapper<PubResourcePo>{

    List<PubResourceDto> searchList(@Param("param")PubResourceSearchDto dto);

}