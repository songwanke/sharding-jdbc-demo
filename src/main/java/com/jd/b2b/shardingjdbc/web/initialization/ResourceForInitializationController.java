package com.jd.b2b.shardingjdbc.web.initialization;

import com.google.gson.Gson;
import com.jd.b2b.shardingjdbc.domain.initialization.NewResourceMenuRequestVo;
import com.jd.b2b.shardingjdbc.domain.initialization.NewResourceMenuResultVo;
import com.jd.b2b.shardingjdbc.service.initialization.service.InitMenuService;
import com.jd.b2b.shardingjdbc.web.util.ReadIniconfigUtil;
import com.jd.ecc.commons.web.model.RespData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author songwanke
 * @date 2018/4/10
 */
@RestController
@RequestMapping("/init/resource")
@Slf4j
public class ResourceForInitializationController {

    @Autowired
    private InitMenuService initMenuService;

    @Autowired
    private HttpServletRequest request;

    private static final String SERVICE_NAME = "/authority";
    private static final String INI_CONFIG = "interceptor.ini";

    private String path(String sectionName){
        return getScheme()+ sectionName+SERVICE_NAME;
    }


    private String getScheme(){
        int serverPort = request.getServerPort();
        if(serverPort == 80){
            return "http://";
        }else if(serverPort == 443){
            return "https://";
        }
        return "http://";
    }

    /**
     * 列表页--环境列表
     * @return
     * @throws IOException
     */
    @ApiOperation("环境列表")
    @RequestMapping(value = "/readPlatform",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public List<String> readPlatform() throws IOException {
        return ReadIniconfigUtil.initPlatform(INI_CONFIG);
    }

    /**
     * 列表页-各环境下的租户id
     * @param sectionName 环境名称 指的是 ini文件中[]的值
     * @return
     * @throws IOException
     */
    @ApiOperation("各环境下的租户id")
    @RequestMapping(value = "/readPlatformId",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public List<Long> readPlatformId(String sectionName) throws IOException{
        return ReadIniconfigUtil.initPlatformIdV2(INI_CONFIG,sectionName);
    }

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
    @RequestMapping(value = "/queryAllResources", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RespData<List<NewResourceMenuResultVo>> queryAllResources(
            @ApiParam("平台id") @RequestParam("platformId") Long platformId,
            @ApiParam("环境平台") @RequestParam("sectionName") String sectionName,
            @ApiParam("platform，public，seller，buyer，shop, buyerMobile,company,personal") @RequestParam(value = "owner",required = false) String owner,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token
    ){

        List<NewResourceMenuResultVo> newResourceMenuResultVos = initMenuService.queryAllResources(platformId,sectionName,owner,token);
        return RespData.success("查询列表成功",newResourceMenuResultVos);
    }

    /**
     * 列表页--添加新菜单/添加子级
     * @param platformId
     * @param token
     * @param resourceRequestVoString
     * @return
     */
    @ApiOperation("添加新菜单/添加子级")
    @RequestMapping(value = "/insertResource", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RespData<Integer> insertResource(
            @ApiParam("平台id") @RequestParam("platformId") Long platformId,
            @ApiParam("环境平台") @RequestParam("sectionName") String sectionName,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token,
            @ApiParam("添加菜单信息项") @RequestParam("resourceRequestVoString") String resourceRequestVoString
    ){
        Integer integer = initMenuService.addMenu(platformId,token,resourceRequestVoString,sectionName);
        return RespData.success("新增菜单成功", integer);
    }

    /**
     * 列表页--编辑菜单项
     * @param platformId
     * @param token
     * @param resourceRequestVoString
     * @return
     */
    @ApiOperation("编辑菜单项")
    @RequestMapping(value = "/updateResource", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RespData<Integer> updateResource(
            @ApiParam("平台id") @RequestParam("platformId") Long platformId,
            @ApiParam("环境平台") @RequestParam("sectionName") String sectionName,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token,
            @ApiParam("编辑菜单信息项") @RequestParam("resourceRequestVoString") String resourceRequestVoString
    ){
        Integer result = initMenuService.updateResource(platformId, resourceRequestVoString, token, sectionName);
        return RespData.success("更新成功",result);
    }

    /**
     * 列表页--编辑菜单项--信息回显
     * @param platformId
     * @param token
     * @param code
     * @return
     */
    @ApiOperation("编辑菜单项--信息回显")
    @RequestMapping(value = "/queryResourceByCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RespData<NewResourceMenuResultVo> queryResourceByCode(
            @ApiParam("平台id") @RequestParam("platformId") Long platformId,
            @ApiParam("环境平台") @RequestParam("sectionName") String sectionName,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token,
            @ApiParam("资源项唯一标识") @RequestParam("code") String code

    ){
        NewResourceMenuResultVo newResourceMenuResultVo = initMenuService.queryResourceByCode(platformId, code, token,sectionName);
        return RespData.success("查询资源信息成功",newResourceMenuResultVo);
    }

    /**
     * 列表页--删除
     * @param platformId
     * @param token
     * @param code
     * @return
     */
    @ApiOperation("删除")
    @RequestMapping(value = "/updateResourceYnByCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RespData<Integer> updateResourceYnByCode(
            @ApiParam("平台id") @RequestParam("platformId") Long platformId,
            @ApiParam("环境平台") @RequestParam("sectionName") String sectionName,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token,
            @ApiParam("资源项唯一标识") @RequestParam("code") String code

    ){
        Integer integer = initMenuService.updateResourceYnByCode(platformId, code,token,sectionName);
        return RespData.success("删除资源信息成功",integer);
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
     */
    @RequestMapping(value = "/syncData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RespData syncData(
            @ApiParam("源平台id") @RequestParam("sourcePlatformId") Long sourcePlatformId,
            @ApiParam("源环境平台") @RequestParam("sourceSectionName") String sourceSectionName,
            @ApiParam("源资源项唯一标识") @RequestParam("sourceCode") String sourceCode,
            @ApiParam("platform，public，seller，buyer，shop, buyerMobile,company,personal") @RequestParam(value = "owner",required = false) String owner,
            @ApiParam("目标平台id") @RequestParam("targetPlatformId") Long targetPlatformId,
            @ApiParam("目标环境平台") @RequestParam("targetSectionName") String targetSectionName,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token
    ){
        initMenuService.syncData(sourcePlatformId,sourceSectionName,sourceCode,owner,targetPlatformId,targetSectionName,token);
        return RespData.success("同步成功");
    }

    /**
     * 列表页--菜单项上移下移
     * @param platformId
     * @param flag
     * @param clickVoString
     * @return
     */
    @ApiOperation("菜单项上移下移")
    @RequestMapping(value = "/move", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RespData move(
            @ApiParam("平台id") @RequestParam("platformId") Long platformId,
            @ApiParam("标志位： -1 上移 1 下移") @RequestParam("flag") Integer flag,
            @ApiParam("clickVoString") @RequestParam("clickVoString") String clickVoString,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token,
            @ApiParam("源环境平台") @RequestParam("sectionName") String sectionName
    ){
        initMenuService.move(platformId,  flag, clickVoString,token, sectionName);
        return RespData.success("移动成功");
    }

    /**
     * 列表页--菜单项置顶置底
     * @param platformId
     * @param flag
     * @param clickVoString
     * @return
     */
    @ApiOperation("菜单项置顶置底")
    @RequestMapping(value = "toFirstOrLast", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RespData toFirstOrLast(
            @ApiParam("平台id") @RequestParam("platformId") Long platformId,
            @ApiParam("标志位： -1 置顶 1 置底") @RequestParam("flag") Long flag,
            @ApiParam("clickVoString") @RequestParam("clickVoString") String clickVoString,
            @ApiParam("ZGVsZXRlX3Rva2VuX2J5X2Jhc2U2NA==") @RequestParam(value = "token",required = false) String token,
            @ApiParam("源环境平台") @RequestParam("sectionName") String sectionName
    ){
        initMenuService.toFirstOrLast(platformId, flag, clickVoString,token,sectionName);
        return RespData.success("操作成功");
    }

    @RequestMapping(value = "getString", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String string(@RequestBody NewResourceMenuRequestVo newResourceMenuRequestVo ){
        return new Gson().toJson(newResourceMenuRequestVo);
    }

}
