package com.yan.rbac.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yan.rbac.dao.service.facade.RbacGroupDaoService;
import com.yan.rbac.dao.service.spring.RbacGroupDaoServiceSpringImpl;
import com.yan.rbac.model.RbacGroup;
import com.yan.rbac.vo.ResultVo;

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,  
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,  
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")  
@RestController
public class RbacGroupController {

    @RequestMapping(value="/findGroups")
    public ResultVo findGroups(@RequestParam Map<String, Object> map) {
    	ResultVo queryResultVo = new ResultVo();
    	
    	//根据条件查询总条数
    	long total = 0;
    	List<RbacGroup> groups = null;
    	//查询结果
    	
    	String page = (String)map.get("page");
		String r = (String)map.get("rows");
		
		//日期格式
		String pattern = "yyyy-MM-dd hh:mm:ss";
		
		//下面的开始时间和结束时间，用来查找一段时间内创建的组
		//开始时间
		Date startTime = getDateTimeFromRequest(map, pattern, "startTime");
		map.put("startTime", startTime);
		
		//结束时间
		Date endTime = getDateTimeFromRequest(map, pattern, "endTime");
		map.put("endTime", endTime);
		
		//记录有效状态
		String validStatus = (String)map.get("validStatus");
		map.put("validStatus", validStatus);
		
		String groupId = (String)map.get("groupId");
		map.put("groupId", groupId);
		
		String groupName = (String)map.get("groupName");
		map.put("groupName", groupName);
		
		//因为只有分页中会传入这两个参数，所以这两个参数并不总是有值，需要非空判断
		int pageNo = 1;
		if(page != null && !"".equals(page.trim())){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = 10;
		if(r != null && !"".equals(r.trim())){
			pageSize = Integer.parseInt(r);
		}
		
		if(pageNo < 1){
			pageNo = 1;
		}
		if(pageSize <= 0){
			pageSize = 10;
		}
		int offset = (pageNo-1)*pageSize;
		
		//第一个参数指定第一个返回记录行的偏移量，第二个参数指定返回记录行的最大数目。初始记录行的偏移量是 0(而不是 1)
		map.put("offset", offset);
		map.put("pageSize", pageSize);
		
		RbacGroupDaoService rbacGroupDaoService = new RbacGroupDaoServiceSpringImpl();
		total = rbacGroupDaoService.getCountByCondition(map);
		groups = rbacGroupDaoService.getGroupsByCondition(map);
		
		//下面这两个变量在这个方法中并不是必须的
		queryResultVo.setTotal(total);
		queryResultVo.setRows(groups);
		queryResultVo.setSuccess(true);
		queryResultVo.setErrorMsg(null);

        return queryResultVo;
    }
 
    @RequestMapping(value="/insertGroup")
    public ResultVo insertGroup(@RequestParam Map<String, Object> map) {
    	ResultVo queryResultVo = new ResultVo();
    	
    	if(map != null && map.size() > 0) {
    		String id = (String)map.get("id");
    		
    		String groupId = (String)map.get("groupId");
    		
    		String groupName = (String)map.get("groupName");
    		
    		String validStatus = "1";
    		
    		RbacGroup group = new RbacGroup();
    		group.setId(id);
    		group.setGroupId(groupId);
    		group.setGroupName(groupName);
    		group.setValidStatus(validStatus);
    		
    		RbacGroupDaoService rbacGroupDaoService = new RbacGroupDaoServiceSpringImpl();
			group.setInsertTime(new Date());
			group.setUpdateTime(new Date());
			rbacGroupDaoService.insertGroup(group);
    		
    		queryResultVo.setObject(group);
    		queryResultVo.setSuccess(true);
    		queryResultVo.setErrorMsg(null);
    	}else {
    		queryResultVo.setObject(null);
    		queryResultVo.setSuccess(false);
    		queryResultVo.setErrorMsg("必要参数不全，请检查参数！");
    	}

        return queryResultVo;
    }
    
    @RequestMapping(value="/updateGroup")
    public ResultVo updateGroup(@RequestParam Map<String, Object> map) {
    	ResultVo queryResultVo = new ResultVo();
    	
    	if(map != null && map.size() > 0) {
    		String id = (String)map.get("id");
    		
    		String groupId = (String)map.get("groupId");
    		
    		String groupName = (String)map.get("groupName");
    		
    		String validStatus = (String)map.get("validStatus");
    		
    		RbacGroup group = new RbacGroup();
    		group.setId(id);
    		group.setGroupId(groupId);
    		group.setGroupName(groupName);
    		group.setValidStatus(validStatus);
    		
    		RbacGroupDaoService rbacGroupDaoService = new RbacGroupDaoServiceSpringImpl();
    		
    		if(id != null && !"".equals(id.trim())) {
    			//修改
    			group.setUpdateTime(new Date());
    			rbacGroupDaoService.updateGroupById(group);
    		}
    		
    		queryResultVo.setObject(group);
    		queryResultVo.setSuccess(true);
    		queryResultVo.setErrorMsg(null);
    	}else {
    		queryResultVo.setObject(null);
    		queryResultVo.setSuccess(false);
    		queryResultVo.setErrorMsg("必要参数不全，请检查参数！");
    	}

        return queryResultVo;
    }
    
    @RequestMapping(value="/deleteGroup")
    public ResultVo deleteGroup(@RequestParam Map<String, Object> map) {
    	ResultVo queryResultVo = new ResultVo();
    	
    	if(map != null && map.size() > 0) {
    		String id = (String)map.get("id");
    		
    		String groupId = (String)map.get("groupId");
    		
    		String groupName = (String)map.get("groupName");
    		
    		//通过软删除的方式删除组
    		String validStatus = "0";
    		
    		RbacGroup group = new RbacGroup();
    		group.setId(id);
    		group.setGroupId(groupId);
    		group.setGroupName(groupName);
    		group.setValidStatus(validStatus);
    		
    		RbacGroupDaoService rbacGroupDaoService = new RbacGroupDaoServiceSpringImpl();
    		
    		if(id != null && !"".equals(id.trim())) {
    			//修改
    			group.setUpdateTime(new Date());
    			rbacGroupDaoService.updateGroupValidStatusByGroupId(group);
    		}
    		
    		queryResultVo.setObject(group);
    		queryResultVo.setSuccess(true);
    		queryResultVo.setErrorMsg(null);
    	}else {
    		queryResultVo.setObject(null);
    		queryResultVo.setSuccess(false);
    		queryResultVo.setErrorMsg("必要参数不全，请检查参数！");
    	}

        return queryResultVo;
    }
    
    /**
	 * 根据参数名称从request中获取并组装对象
	 * 
	 * @param map
	 * @param pattern
	 * @param parameterName
	 * @return 如果日期为空，返回null
	 * 如果不符合日期格式，返回null
	 */
	public static Date getDateTimeFromRequest(Map<String, Object> map, String pattern, String parameterName){
		Date date = null;
		
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(pattern);
		
		String dateTimeStr = (String)map.get(parameterName);
		
		if(dateTimeStr != null && !"".equals(dateTimeStr.trim())){
			try {
				date = dateTimeFormat.parse(dateTimeStr.trim());
			} catch (ParseException e) {
				e.printStackTrace();
				date = null;
			}
		}
		return date;
	}
	
}