package com.rx.pub.role.service.impl;

import com.rx.pub.role.po.PubUserGroupPo;
import com.rx.pub.role.enm.UserGroupRelationEnum;
import com.rx.pub.role.mapper.PubUserGroupMapper;
import com.rx.pub.role.service.PubUserGroupService;
import tk.mybatis.mapper.entity.Example;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rx.base.page.Pager;
import com.rx.base.user.RxGroupable;
import com.rx.base.user.RxUserable;
import com.rx.base.page.PageExcute;
import java.util.List;
import com.rx.pub.mybatis.impls.MybatisBaseService;


/**
 * 用户组(PubUserGroup)Service实现类
 *
 * @author klf
 * @since 2019-12-30 15:14:21
 */
@Service
public class PubUserGroupServiceImpl extends MybatisBaseService<PubUserGroupPo> implements PubUserGroupService {

    private static final Logger logger = LoggerFactory.getLogger(PubUserGroupServiceImpl.class);

    @Resource
    private PubUserGroupMapper pubUserGroupMapper;
    
    @Override
    public Pager<PubUserGroupPo> searchPage(PubUserGroupPo po) {
        return this.getPageExcuter().selectByPage(new PageExcute<PubUserGroupPo>() {
            @Override
            public List<PubUserGroupPo> excute() {
                return pubUserGroupMapper.searchList(po);
            }
        },this.getPagerProvider().getPager(PubUserGroupPo.class));
    }

	@Override
	public List<PubUserGroupPo> listUserGroups(RxUserable user,Class<? extends RxGroupable> groupType,UserGroupRelationEnum userGroupRelationEnum) {
		Example example = new Example(PubUserGroupPo.class);
        Example.Criteria criteria = example.createCriteria();
        if(user != null) {
        	criteria.andEqualTo("userId", user.getId());
        	criteria.andEqualTo("userType", user.getClass().getName());
        }
        if(groupType != null) {
        	criteria.andEqualTo("groupType", groupType.getName());
        }
        if(userGroupRelationEnum != null) {
        	criteria.andEqualTo("relation", userGroupRelationEnum.getCode());
        }
        return selectByExample(example);
	}
	
	@Override
	public List<PubUserGroupPo> listUserGroups(String userId,Class<? extends RxUserable> userType,Class<? extends RxGroupable> groupType,UserGroupRelationEnum userGroupRelationEnum) {
		Example example = new Example(PubUserGroupPo.class);
        Example.Criteria criteria = example.createCriteria();
        if(userId != null) {
        	criteria.andEqualTo("userId", userId);
        }
        if(userType != null) {
        	criteria.andEqualTo("userType", userType.getName());
        }
        if(groupType != null) {
        	criteria.andEqualTo("groupType", groupType.getName());
        }
        if(userGroupRelationEnum != null) {
        	criteria.andEqualTo("relation", userGroupRelationEnum.getCode());
        }
        return selectByExample(example);
	}
	
	
	@Override
	public List<PubUserGroupPo> listGroupUser(RxGroupable group,UserGroupRelationEnum userGroupRelationEnum,Class<? extends RxUserable> userType) {
		
		PubUserGroupPo search = new PubUserGroupPo();
		search.setGroupId(group.getId());
		search.setGroupType(group.getClass().getName());
		if(userGroupRelationEnum != null) {
			search.setRelation(userGroupRelationEnum.getCode());
		}
		if(userType != null) {
			search.setUserType(userType.getName());
		}
		return pubUserGroupMapper.select(search);
	}

	@Override
	public List<PubUserGroupPo> addGroupUser(RxGroupable group, RxUserable user,UserGroupRelationEnum userGroupRelationEnum) {
		PubUserGroupPo record = new PubUserGroupPo(null);
		record.setRelation(userGroupRelationEnum.value());
		record.setUserId(user.getId());
		record.setUserType(user.getClass().getName());
		record.setGroupId(group.getId());
		record.setGroupType(group.getClass().getName());
		pubUserGroupMapper.insertSelective(record);
		return null;
	}
	
    
}