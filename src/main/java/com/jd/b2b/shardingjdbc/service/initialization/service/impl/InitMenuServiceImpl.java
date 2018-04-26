package com.jd.b2b.shardingjdbc.service.initialization.service.impl;

import com.google.gson.Gson;
import com.jd.b2b.shardingjdbc.domain.initialization.NewResourceMenuRequestVo;
import com.jd.b2b.shardingjdbc.domain.initialization.NewResourceMenuResultVo;
import com.jd.b2b.shardingjdbc.service.initialization.feign.AuthorityClient;
import com.jd.b2b.shardingjdbc.service.initialization.service.InitMenuService;
import com.jd.b2b.shardingjdbc.web.util.FeignClientUtils;
import com.jd.ecc.commons.lib.utils.BeanCopierUtils;
import com.jd.ecc.commons.web.model.RespData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songwanke
 * @date 2018/4/19
 */
@Service
@Slf4j
public class InitMenuServiceImpl implements InitMenuService{

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
    @Override
    public List<NewResourceMenuResultVo> queryAllResources(Long platformId, String sectionName, String owner, String token) {
        log.info("查询资源列表platformId:{},sectionName:{},owner:{}",platformId,sectionName,owner);
        List<NewResourceMenuResultVo> data = new ArrayList<>();
        RespData<List<NewResourceMenuResultVo>> listRespData = FeignClientUtils.getClient(AuthorityClient.class, sectionName).queryAllResources(platformId, owner, token);

        if(null != listRespData){
            data = listRespData.getData();
        }
        return data;
    }

    /**
     * 列表页--添加新菜单/添加子级
     * @param platformId
     * @param token
     * @param resourceRequestVo
     * @param sectionName
     * @return
     */
    @Override
    public Integer addMenu(Long platformId, String token, String resourceRequestVo, String sectionName) {
        log.info("添加菜单==========>platformId:{},resourceRequestVo:{},sectionName:{}", platformId,resourceRequestVo,sectionName);
        RespData<Integer> integerRespData = FeignClientUtils.getClient(AuthorityClient.class, sectionName).insertResource(platformId, token, resourceRequestVo);

        if(null != integerRespData && null != integerRespData.getData()){
            return integerRespData.getData();
        }
        return null;
    }

    /**
     * 列表页--编辑菜单项
     * @param platformId
     * @param resourceRequestVo
     * @param token
     * @param sectionName
     * @return
     */
    @Override
    public Integer updateResource(Long platformId, String resourceRequestVo, String token, String sectionName) {
        log.info("编辑菜单项==========>platformId:{}, resourceRequestVo:{},sectionName:{}", platformId, resourceRequestVo,sectionName);
        RespData<Integer> integerRespData = FeignClientUtils.getClient(AuthorityClient.class, sectionName).updateResource(platformId, token, resourceRequestVo);

        if(null != integerRespData && null != integerRespData.getData()){
            return integerRespData.getData();
        }
        return null;
    }

    /**
     * 列表页--编辑菜单项--信息回显
     * @param platformId
     * @param code
     * @param token
     * @param sectionName
     * @return
     */
    @Override
    public NewResourceMenuResultVo queryResourceByCode(Long platformId, String code, String token, String sectionName) {
        log.info("查询菜单项==========>platformId:{}，code:{},sectionName:{}", platformId,code,sectionName);
        RespData<NewResourceMenuResultVo> newResourceMenuResultVoRespData = FeignClientUtils.getClient(AuthorityClient.class, sectionName).queryResourceByCode(platformId, token, code);

        if(null != newResourceMenuResultVoRespData && null != newResourceMenuResultVoRespData.getData()){
            return newResourceMenuResultVoRespData.getData();
        }
        return null;
    }

    /**
     * 列表页--删除
     * @param platformId
     * @param code
     * @param token
     * @param sectionName
     * @return
     */
    @Override
    public Integer updateResourceYnByCode(Long platformId, String code, String token, String sectionName) {
        log.info("删除==========>platformId:{}，code:{},sectionName:{}", platformId,code,sectionName);
        RespData<Integer> integerRespData = FeignClientUtils.getClient(AuthorityClient.class, sectionName).updateResourceYnByCode(platformId, token, code);

        if(null != integerRespData && null != integerRespData.getData()){
            return integerRespData.getData();
        }
        return null;
    }

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
     *
     * 1.
     * 2.queryAllResources 查询目标资源
     * if(size>0){
     *  2.2    queryResourceByCode（sourceCode,targetPlatformId）；null --> insert,  not null-- > update
     * }else{
     *  2.1  查询所有数据--》 insert
     * }
     */
    @Override
    public void syncData(Long sourcePlatformId, String sourceSectionName, String sourceCode, String owner, Long targetPlatformId, String targetSectionName, String token) {
        log.info("根据targetPlatformId:{},targetSectionName:{}查询目标数据",targetPlatformId,targetSectionName);
        List<NewResourceMenuResultVo> data = new ArrayList<>();
        RespData<List<NewResourceMenuResultVo>> listRespData = FeignClientUtils.getClient(AuthorityClient.class, targetSectionName).queryAllResourceNotDeal(targetPlatformId, null, token);
        if(null != listRespData && null != listRespData.getData()){
            data = listRespData.getData();
        }

        log.info("查询源数据sourcePlatformId:{},sourceCode:{}",sourcePlatformId,sourceCode);
        RespData<NewResourceMenuResultVo> sourceNewResourceMenuResultVoRespData = FeignClientUtils.getClient(AuthorityClient.class, sourceSectionName).queryResourceByCode(sourcePlatformId, token, sourceCode);
        Boolean b = false;
        NewResourceMenuResultVo sourceNewResourceMenuResultVo = new NewResourceMenuResultVo();
        NewResourceMenuRequestVo targetNewResourceMenuRequestVo = new NewResourceMenuRequestVo();
        if(null != sourceNewResourceMenuResultVoRespData && null != sourceNewResourceMenuResultVoRespData.getData()){
            sourceNewResourceMenuResultVo = sourceNewResourceMenuResultVoRespData.getData();
            BeanCopierUtils.copyProperties(sourceNewResourceMenuResultVo,targetNewResourceMenuRequestVo);
            targetNewResourceMenuRequestVo.setPlatformId(targetPlatformId);
            b = true;
        }

        String targetNewResourceMenuRequestVoString = new Gson().toJson(targetNewResourceMenuRequestVo);

        log.info("校验目标数据size:{}--》同步所有的数据 or 同步单条数据",data.size());
        if(data.size() > 0){
            log.info("根据sourceCode：{},targetSectionName:{}查询单条数据",sourceCode,targetSectionName);
            RespData<NewResourceMenuResultVo> targetNewResourceMenuResultVoRespData = FeignClientUtils.getClient(AuthorityClient.class, targetSectionName).queryResourceByCode(targetPlatformId, token, sourceCode);
            if(null != targetNewResourceMenuResultVoRespData && null != targetNewResourceMenuResultVoRespData.getData()){
                log.info("要同步的目标数据已存在记录，修改目标资源");
                if(b){
                    FeignClientUtils.getClient(AuthorityClient.class,targetSectionName).updateResource(targetPlatformId,token,targetNewResourceMenuRequestVoString);
                }
            }else if(null != targetNewResourceMenuResultVoRespData && null == targetNewResourceMenuResultVoRespData.getData()){
                log.info("要同步的目标数据不存在记录，新增目标资源");
                if(b){
                    FeignClientUtils.getClient(AuthorityClient.class,targetSectionName).syncInsertResource(targetPlatformId,token,targetNewResourceMenuRequestVoString);
                }
            }
        }else{
            List<NewResourceMenuRequestVo> newResourceMenuRequestVoList = new ArrayList<>();
            RespData<List<NewResourceMenuResultVo>> sourceList = FeignClientUtils.getClient(AuthorityClient.class, targetSectionName).queryAllResourceNotDeal(sourcePlatformId, null, token);
            if(null != sourceList && null != sourceList.getData()){
                List<NewResourceMenuResultVo> newResourceMenuResultVos = sourceList.getData();
                for(NewResourceMenuResultVo newResourceMenuResultVo:newResourceMenuResultVos){
                    NewResourceMenuRequestVo newResourceMenuRequestVo = new NewResourceMenuRequestVo();
                    BeanCopierUtils.copyProperties(newResourceMenuResultVo,newResourceMenuRequestVo);
                    newResourceMenuRequestVo.setPlatformId(targetPlatformId);
                    newResourceMenuRequestVoList.add(newResourceMenuRequestVo);
                }
            }
            String newResourceMenuRequestVoListString = new Gson().toJson(newResourceMenuRequestVoList);
            //同步所有数据
            FeignClientUtils.getClient(AuthorityClient.class, targetSectionName).insertAllResource(targetPlatformId, token, newResourceMenuRequestVoListString);
        }
    }

    /**
     * 列表页--菜单项上移下移
     * @param platformId
     * @param flag
     * @param clickVoString
     * @param token
     * @param sectionName
     */
    @Override
    public void move(Long platformId, Integer flag, String clickVoString, String token, String sectionName) {
        log.info("移动菜单--> platformId:{}, clickVo:{}, flag:{}, sectionName:{}", platformId, clickVoString, flag, sectionName);
        FeignClientUtils.getClient(AuthorityClient.class,sectionName).move(platformId, flag, clickVoString, token);
    }

    /**
     * 列表页--菜单项置顶置底
     * @param platformId
     * @param flag
     * @param clickVo
     * @param token
     * @param sectionName
     */
    @Override
    public void toFirstOrLast(Long platformId, Long flag, String clickVo, String token, String sectionName) {
        log.info("移动菜单--> platformId:{}, clickVo:{}, flag:{}, sectionName:{}", platformId, clickVo, flag, sectionName);
        FeignClientUtils.getClient(AuthorityClient.class,sectionName).toFirstOrLast(platformId, flag, clickVo, token);
    }
}
