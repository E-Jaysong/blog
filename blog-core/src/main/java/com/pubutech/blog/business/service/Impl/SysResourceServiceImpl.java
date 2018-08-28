package com.pubutech.blog.business.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pubutech.blog.business.entity. GroupResources;
import com.pubutech.blog.business.entity.Resources;
import com.pubutech.blog.business.entity.ResourcesGroup;
import com.pubutech.blog.business.service.SysResourcesService;
import com.pubutech.blog.framework.holder.RequestHolder;
import com.pubutech.blog.persistence.bean.SysResources;
import com.pubutech.blog.persistence.condition.ResourceSqlCondition;
import com.pubutech.blog.persistence.mapper.SysResourceMapper;
import com.pubutech.blog.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class SysResourceServiceImpl implements SysResourcesService {

    @Autowired
    private SysResourceMapper resourceMapper;

    /**
     * 分页查询
     *
     * @param condition
     * @return
     */
    @Override
    public PageInfo<Resources> findPageBreakByCondition(ResourceSqlCondition condition) {
        PageHelper.startPage(condition.getPageNumber(), condition.getPageSize());
        List<SysResources> sysResources = resourceMapper.findPageBreakByCondition(condition);
        if (CollectionUtils.isEmpty(sysResources)) {
            return null;
        }
        List<Resources> resources = new ArrayList<>();
        for (SysResources r : sysResources) {
            resources.add(new Resources(r));
        }
        PageInfo bean = new PageInfo<SysResources>(sysResources);
        bean.setList(resources);
        return bean;
    }

    /**
     * 获取用户的资源列表
     *
     * @param map
     * @return
     */
    @Override
    public List<Resources> listUserResources(Map<String, Object> map) {
        List<SysResources> sysResources = resourceMapper.listUserResources(map);
        if (CollectionUtils.isEmpty(sysResources)) {
            return null;
        }
        List<Resources> resources = new ArrayList<>();
        for (SysResources r : sysResources) {
            resources.add(new Resources(r));
        }
        return resources;
    }

    /**
     * 获取ztree使用的资源列表
     *
     * @param rid
     * @return
     */
    @Override
    public List<Map<String, Object>> queryResourcesListWithSelected(Long rid) {
        List<SysResources> sysResources = resourceMapper.queryResourcesListWithSelected(rid);
        if (CollectionUtils.isEmpty(sysResources)) {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (SysResources resources : sysResources) {
            map = new HashMap<String, Object>(3);
            map.put("id", resources.getId());
            map.put("pId", resources.getParentId());
            map.put("checked", resources.getChecked());
            map.put("name", resources.getName());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 获取资源的url和permission
     *
     * @return
     */
    @Override
    public List<Resources> listUrlAndPermission() {
        List<SysResources> sysResources = resourceMapper.listUrlAndPermission();
        return getResources(sysResources);
    }

    /**
     * 获取所有可用的菜单资源
     *
     * @return
     */
    @Override
    public List<Resources> listAllAvailableMenu() {
        List<SysResources> sysResources = resourceMapper.listAllAvailableMenu();
        return getResources(sysResources);
    }

    /**
     * 获取用户关联的所有资源
     *
     * @param userId
     * @return
     */
    @Override
    public List<Resources> listByUserId(Long userId) {
        List<SysResources> sysResources = resourceMapper.listByUserId(userId);
        return getResources(sysResources);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    public Resources insert(Resources entity) {
        Assert.notNull(entity, "Resources不可为空！");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        resourceMapper.insert(entity.getSysResources());
        return entity;
    }

    @Override
    public SysResources insert(SysResources entity) {
        Assert.notNull(entity, "SysResources不可为空！");
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        resourceMapper.insert(entity);
        return entity;
    }


    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    public void insertList(List<Resources> entities) {
        Assert.notNull(entities, "entities不可为空！");
        List<SysResources> sysResources = new ArrayList<>();
        String regIp = IpUtil.getRealIp(RequestHolder.getRequest());
        for (Resources resources : entities) {
            resources.setUpdateTime(new Date());
            resources.setCreateTime(new Date());
            sysResources.add(resources.getSysResources());
        }
        resourceMapper.insertList(sysResources);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    @Override
    public boolean removeByPrimaryKey(Long primaryKey) {
        return resourceMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    @Override
    public boolean update(Resources entity) {
        Assert.notNull(entity, "Resources不可为空！");
        entity.setUpdateTime(new Date());
        return resourceMapper.updateByPrimaryKey(entity.getSysResources()) > 0;
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateSelective(Resources entity) {
        Assert.notNull(entity, "Resources不可为空！");
        entity.setUpdateTime(new Date());
        return resourceMapper.updateByPrimaryKeySelective(entity.getSysResources()) > 0;
    }

    @Override
    public boolean updateSelective(SysResources sysResources){
        Assert.notNull(sysResources, "Resources不可为空！");
        sysResources.setUpdateTime(new Date());
        return resourceMapper.updateByPrimaryKeySelective(sysResources) > 0;
    }
    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public Resources getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysResources sysResources = resourceMapper.selectByPrimaryKey(primaryKey);
        return null == sysResources ? null : new Resources(sysResources);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public Resources getOneByEntity(Resources entity) {
        Assert.notNull(entity, "User不可为空！");
        SysResources sysResources = resourceMapper.selectOne(entity.getSysResources());
        return null == sysResources ? null : new Resources(sysResources);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    public List<Resources> listAll() {
        List<SysResources> sysResources = resourceMapper.selectAll();
        return getResources(sysResources);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public List<Resources> listByEntity(Resources entity) {
        Assert.notNull(entity, "Resources不可为空！");
        List<SysResources> sysResources = resourceMapper.select(entity.getSysResources());
        return getResources(sysResources);
    }

    private List<Resources> getResources(List<SysResources> sysResources) {
        if (CollectionUtils.isEmpty(sysResources)) {
            return null;
        }
        List<Resources> resources = new ArrayList<>();
        for (SysResources r : sysResources) {
            resources.add(new Resources(r));
        }
        return resources;
    }

    @Override
    public List<ResourcesGroup> listAllGroupResources(){
        return turn(resourceMapper.listAllGroupResources());
    }

    @Override
    public List<ResourcesGroup> listAllGroupResourcesByUserName(String userName){
        return turn(resourceMapper.listAllGroupResourcesByUserName(userName));
    }

    @Override
    public List<ResourcesGroup> listAllGroupResourcesByUserId(long userId){
        return turn2(resourceMapper.listAllGroupResourcesByUserIdOrderByParentId(userId));
    }

    @Override
    public List<ResourcesGroup> listResourcesByRoleId(long roleId){
        return turn2(resourceMapper.listResourcesByRoleId(roleId));
    }

    private List<ResourcesGroup> turn(List< GroupResources> list){
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<ResourcesGroup> resourcesGroupList = new ArrayList<>();
        ResourcesGroup resourcesGroupTemp = null;
        HashMap<Long,ResourcesGroup> parentParentHash = new HashMap<>();
        HashMap<Long,ResourcesGroup> parentHash = new HashMap<>();
        HashMap<Long,ResourcesGroup> childHash = new HashMap<>();
        for( GroupResources  groupResources:list ){
            long parentParentId =  groupResources.getParentParentId();
            if(!parentParentHash.containsKey(parentParentId)){
                resourcesGroupTemp = new ResourcesGroup();
                resourcesGroupTemp.setAvailable( groupResources.getParentParentAvailable());
                resourcesGroupTemp.setExternal( groupResources.getParentParentExternal());
                resourcesGroupTemp.setIcon( groupResources.getParentParentIcon());
                resourcesGroupTemp.setId( groupResources.getParentParentId());
                resourcesGroupTemp.setName( groupResources.getParentParentName());
                resourcesGroupTemp.setParent_id( groupResources.getParentParentParentId());
                resourcesGroupTemp.setPermisssion( groupResources.getParentParentPermission());
                resourcesGroupTemp.setSort( groupResources.getParentParentSort());
                resourcesGroupTemp.setType( groupResources.getParentParentType());
                resourcesGroupTemp.setUrl( groupResources.getParentParentUrl());
                resourcesGroupTemp.setList(new ArrayList<ResourcesGroup>());
                parentParentHash.put(parentParentId,resourcesGroupTemp);
            }

            long parentId =  groupResources.getParentId();
            if(!parentHash.containsKey(parentId)){
                resourcesGroupTemp = new ResourcesGroup();
                resourcesGroupTemp.setAvailable( groupResources.getParentAvailable());
                resourcesGroupTemp.setExternal( groupResources.getParentExternal());
                resourcesGroupTemp.setIcon( groupResources.getParentIcon());
                resourcesGroupTemp.setId( groupResources.getParentId());
                resourcesGroupTemp.setName( groupResources.getParentName());
                resourcesGroupTemp.setParent_id( groupResources.getParent_parent_id());
                resourcesGroupTemp.setPermisssion( groupResources.getParentPermission());
                resourcesGroupTemp.setSort( groupResources.getParentSort());
                resourcesGroupTemp.setType( groupResources.getParentType());
                resourcesGroupTemp.setUrl( groupResources.getParentUrl());
                resourcesGroupTemp.setList(new ArrayList<ResourcesGroup>());
                parentHash.put(parentId,resourcesGroupTemp);
            }

            long id =  groupResources.getId();
            if(!childHash.containsKey(id)){
                resourcesGroupTemp = new ResourcesGroup();
                resourcesGroupTemp.setAvailable( groupResources.getAvailable());
                resourcesGroupTemp.setExternal( groupResources.getExternal());
                resourcesGroupTemp.setIcon( groupResources.getIcon());
                resourcesGroupTemp.setId( groupResources.getId());
                resourcesGroupTemp.setName( groupResources.getName());
                resourcesGroupTemp.setParent_id( groupResources.getParent_id());
                resourcesGroupTemp.setPermisssion( groupResources.getPermission());
                resourcesGroupTemp.setSort( groupResources.getSort());
                resourcesGroupTemp.setType( groupResources.getType());
                resourcesGroupTemp.setUrl( groupResources.getUrl());
                childHash.put(id,resourcesGroupTemp);
            }
        }
        ResourcesGroup temp = null;
        ResourcesGroup parentTemp = null;
        ArrayList<ResourcesGroup> listTemp;
        for(long id : childHash.keySet()){
            temp = childHash.get(id);
            long parentId = temp.getParent_id();
            parentTemp = parentHash.get(parentId);
            listTemp = parentTemp.getList();
            listTemp.add(temp);
        }

        for(long parentId : parentHash.keySet()){
            temp = parentHash.get(parentId);
            long parentParentId = temp.getParent_id();
            parentTemp = parentParentHash.get(parentParentId);
            listTemp = parentTemp.getList();
            listTemp.add(temp);
        }

        for(long id : parentParentHash.keySet()){
            resourcesGroupList.add(parentParentHash.get(id));
        }
        return resourcesGroupList;
    }

    public List<ResourcesGroup> listAllResourcesGroup(){
        return turn2(resourceMapper.listAllOrderByparentId());
    }

    public List<ResourcesGroup> turn2(List<SysResources> list){

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<ResourcesGroup> resourcesGroupList = new ArrayList<>();
        ResourcesGroup resourcesGroupTemp = null;
        for(SysResources sysResources :list){
            if(0l == sysResources.getParentId()){
                addResourcesGroup(resourcesGroupList,getResourcesGroup(sysResources));
            }else{
                setChild(sysResources,resourcesGroupList);
            }
        }
        return resourcesGroupList;
    }

    private void setChild(SysResources sysResources,List<ResourcesGroup> list){
        long id = sysResources.getParentId();
        for(ResourcesGroup temp :list){
            if(id == temp.getId()){
                addResourcesGroup(temp.getList(),getResourcesGroup(sysResources));
                return ;
            }
            if(!CollectionUtils.isEmpty(temp.getList())){
                setChild(sysResources,temp.getList());
            }
        }
    }

    private void addResourcesGroup(List<ResourcesGroup> list , ResourcesGroup resourcesGroup){
        for(ResourcesGroup temp : list){
            if(temp.getId() == resourcesGroup.getId())return;
        }
        list.add(resourcesGroup);
    }

    private ResourcesGroup getResourcesGroup(SysResources sysResources){
        ResourcesGroup resourcesGroupTemp = new ResourcesGroup();
        resourcesGroupTemp.setAvailable( sysResources.getAvailable());
        resourcesGroupTemp.setExternal( sysResources.getExternal());
        resourcesGroupTemp.setIcon( sysResources.getIcon());
        resourcesGroupTemp.setId( sysResources.getId());
        resourcesGroupTemp.setName( sysResources.getName());
        resourcesGroupTemp.setParent_id( sysResources.getParentId());
        resourcesGroupTemp.setPermisssion( sysResources.getPermission());
        resourcesGroupTemp.setSort( sysResources.getSort());
        resourcesGroupTemp.setType( sysResources.getType());
        resourcesGroupTemp.setUrl( sysResources.getUrl());
        if(!StringUtils.isEmpty(sysResources.getChecked())){
            resourcesGroupTemp.setChecked("true".equals(sysResources.getChecked()));
        }
        resourcesGroupTemp.setList(new ArrayList<ResourcesGroup>());
        return resourcesGroupTemp;
    }
}
