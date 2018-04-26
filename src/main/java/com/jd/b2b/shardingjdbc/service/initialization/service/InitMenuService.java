package com.jd.b2b.shardingjdbc.service.initialization.service;

import com.jd.b2b.shardingjdbc.domain.initialization.NewResourceMenuResultVo;

import java.util.List;

/**
 * @author songwanke
 * @date 2018/4/19
 */
public interface InitMenuService {


    /**
     * 列表页--列表
     * @param platformId
     * @param sectionName
     * @param owner
     * @param token
     * @return
     * 1.灵活查询资源列表--传入参数platformId,sectionName(环境)
     * 2.sectionName-->网关地址-->拼接接口地址-->查询列表
     */
    List<NewResourceMenuResultVo> queryAllResources(Long platformId, String sectionName, String owner, String token);

    /**
     * 列表页--添加新菜单/添加子级
     * @param platformId
     * @param token
     * @param resourceRequestVo
     * @param sectionName
     * @return
     */
    Integer addMenu(Long platformId, String token, String resourceRequestVo, String sectionName);

    /**
     * 列表页--编辑菜单项
     * @param platformId
     * @param resourceRequestVo
     * @param token
     * @param sectionName
     * @return
     */
    Integer updateResource(Long platformId, String resourceRequestVo, String token, String sectionName);

    /**
     * 列表页--编辑菜单项--信息回显
     * @param platformId
     * @param code
     * @param token
     * @param sectionName
     * @return
     */
    NewResourceMenuResultVo queryResourceByCode(Long platformId, String code, String token, String sectionName);

    /**
     * 列表页--删除
     * @param platformId
     * @param code
     * @param token
     * @param sectionName
     * @return
     */
    Integer updateResourceYnByCode(Long platformId, String code, String token, String sectionName);

    /**
     * 列表页--同步数据
     * @param sourcePlatformId
     * @param sourceSectionName
     * @param owner
     * @param token
     * @return
     * 1.根据 sourcePlatformId 以及 sourceSectionName 查询源数据
     * 2.根据 targetPlatformId 以及 targetSectionName 查询目标资源
     * 2.1 如 目标资源 size = 0，则 同步源数据的所有数据
     * 2.2 如 目标资源 size > 0，则 根据源数据的code去校验 目标资源的 单条数据是否存在，存在--> update,不存在-->insert
     */
    void syncData(Long sourcePlatformId, String sourceSectionName, String sourceCode, String owner, Long targetPlatformId, String targetSectionName, String token);

    /**
     * 列表页--菜单项上移下移
     * @param platformId
     * @param flag
     * @param clickVoString
     * @param token
     * @param sectionName
     */
    void move(Long platformId, Integer flag, String clickVoString, String token, String sectionName);

    /**
     * 列表页--菜单项置顶置底
     * @param platformId
     * @param flag
     * @param clickVo
     * @param token
     * @param sectionName
     */
    void toFirstOrLast(Long platformId, Long flag, String clickVo, String token, String sectionName);
}
