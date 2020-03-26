package com.rx.pub.role.service;


import com.rx.pub.role.enm.UserGroupRelationEnum;
import com.rx.pub.role.po.PubUserGroupPo;
import java.util.List;
import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.base.user.RxGroupable;
import com.rx.base.user.RxUserable;

/**
 * 用户组(PubUserGroup)Service
 *
 * @author klf
 * @since 2019-12-30 15:14:21
 */
public interface PubUserGroupService extends BaseService<PubUserGroupPo> {

    /**
     * 分页查询
     */
    Pager<PubUserGroupPo> searchPage(PubUserGroupPo po);

    
    


	List<PubUserGroupPo> listGroupUser(RxGroupable group, UserGroupRelationEnum userGroupRelationEnum,Class<? extends RxUserable> userType);
	
	List<PubUserGroupPo> addGroupUser(RxGroupable group,RxUserable user, UserGroupRelationEnum userGroupRelationEnum);


	
	List<PubUserGroupPo> listUserGroups(RxUserable user,Class<? extends RxGroupable> groupType,UserGroupRelationEnum userGroupRelationEnum);
	List<PubUserGroupPo> listUserGroups(String userId, Class<? extends RxUserable> userType,Class<? extends RxGroupable> groupType,UserGroupRelationEnum userGroupRelationEnum);
}