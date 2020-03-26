package com.rx.pub.crud.controller;

import com.rx.base.result.DataResult;
import com.rx.base.result.type.BusinessException;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.crud.annotation.PermissionCrud;
import com.rx.pub.crud.dto.CrudSearchDto;
import com.rx.pub.crud.enm.CrudTypeEnum;
import com.rx.pub.crud.enm.PermissionEumn;
import com.rx.pub.crud.po.PubCrud;
import com.rx.pub.crud.service.IPubCrudService;
import com.rx.pub.crud.vo.RecycleSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pub/recycle")
@ExtClass(extend = SpringProvider.class, alternateClassName = "RecycleController")
public class RecycleController{
    @Autowired
    private IPubCrudService pubCrudService;



    @PermissionCrud(PermissionEumn.查看回收站对象)
    @RequestMapping(value = "/listDeletedBean", method = RequestMethod.GET)
    @ResponseBody
    public DataResult listDeletedBean(RecycleSearchVo recycleSearchVo) throws Exception{
	    
    	CrudSearchDto searchDto = new CrudSearchDto();
    	searchDto.setBeginTime(recycleSearchVo.getBeginTime());
    	searchDto.setEndTime(recycleSearchVo.getEndTime());
        return new DataResult(pubCrudService.listRecyclePage(searchDto));
    }
    
    @PermissionCrud(PermissionEumn.查看回收站对象)
	@RequestMapping(value = "/getRecycleDetail", method = RequestMethod.GET)
    @ResponseBody
	public DataResult getRecycleDetail(String id) throws Exception{
    	PubCrud pc = pubCrudService.selectByPrimaryKey(id);
        if (pc == null){
            throw new BusinessException("不存在此元素");
        }
	    return new DataResult("成功",pc);
	}

    @PermissionCrud(PermissionEumn.删除回收站对象)
    @RequestMapping(value = "/removeBean", method = RequestMethod.GET)
    @ResponseBody
    public DataResult removeBean(String[] id) throws Exception{
        for (String recId : id) {
        	pubCrudService.deleteRecycle(recId);
        }
        return new DataResult("成功");
    }

    @PermissionCrud(PermissionEumn.删除回收站对象)
    @RequestMapping(value = "/removeBeanList", method = RequestMethod.GET)
    @ResponseBody
    public DataResult removeBeanList() throws Exception{
    	PubCrud pc = new PubCrud();
    	pubCrudService.delete(pc);
        return new DataResult("成功");
    }
    
    @PermissionCrud(PermissionEumn.还原回收站对象)
	@RequestMapping(value = "/restoreBean", method = RequestMethod.GET)
    @ResponseBody
	public DataResult restoreBean(String id) throws Exception{
    	pubCrudService.restoreRecycle(id);
	    return new DataResult("成功");
	}

}
