package com.jd.b2b.shardingjdbc.service.initialization.feign;

import com.google.gson.Gson;
import com.jd.b2b.shardingjdbc.domain.initialization.NewResourceMenuRequestVo;
import com.jd.b2b.shardingjdbc.domain.initialization.NewResourceMenuResultVo;
import com.jd.ecc.commons.web.model.RespData;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.runners.Parameterized;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author songwanke
 * @date 2018/4/19
 */
@Headers({"Content-Type: application/json", "INVOKER: FEIGN"})
public interface AuthorityClient {

    /**
     * 列表页--列表数据  swk
     * @param platformId
     * @param owner
     * @param token
     * @return
     */
    @RequestLine(value = "GET /feign/resource/queryAllResources?platformId={platformId}&owner={owner}&token={token}")
    RespData<List<NewResourceMenuResultVo>> queryAllResources(
            @Param("platformId") Long platformId,
            @Param(value = "owner") String owner,
            @Param(value = "token") String token
    );

    /**
     * 列表页--添加新菜单/添加子级
     * @param platformId
     * @param token
     * @param resourceRequestVoString
     * @return
     */
    @RequestLine(value = "POST /feign/resource/insertResource?platformId={platformId}&token={token}&resourceRequestVoString={resourceRequestVoString}")
    RespData<Integer> insertResource(
            @Param("platformId") Long platformId,
            @Param("token") String token,
            @Param("resourceRequestVoString") String resourceRequestVoString
    );

    /**
     * 列表页--编辑菜单项
     * @param platformId
     * @param token
     * @param resourceRequestVoString
     * @return
     */
    @RequestLine(value = "POST /feign/resource/updateResource?platformId={platformId}&token={token}&resourceRequestVoString={resourceRequestVoString}")
    RespData<Integer> updateResource(
            @Param("platformId") Long platformId,
            @Param(value = "token") String token,
            @Param("resourceRequestVoString") String resourceRequestVoString
    );

    /**
     * 列表页--编辑菜单项--信息回显
     * @param platformId
     * @param token
     * @param code
     * @return
     */
    @RequestLine(value = "GET /feign/resource/queryResourceByCode?platformId={platformId}&token={token}&code={code}")
    RespData<NewResourceMenuResultVo> queryResourceByCode(
            @Param("platformId") Long platformId,
            @Param(value = "token") String token,
            @Param("code") String code
    );

    /**
     * 列表页--删除
     * @param platformId
     * @param token
     * @param code
     * @return
     */
    @RequestLine(value = "POST /feign/resource/updateResourceYnByCode?platformId={platformId}&token={token}&code={code}")
    RespData<Integer> updateResourceYnByCode(
            @Param("platformId") Long platformId,
            @Param(value = "token") String token,
            @Param("code") String code

    );

    /**
     * 列表页--同步数据--同步所有的数据
     * @param platformId
     * @param token
     * @param newResourceMenuRequestVoListString
     * @return
     */
    @RequestLine(value = "POST /feign/resource/insertAllResource?platformId={platformId}&token={token}&newResourceMenuRequestVoListString={newResourceMenuRequestVoListString}")
    RespData<Integer> insertAllResource(
            @Param("platformId") Long platformId,
            @Param(value = "token") String token,
            @Param("newResourceMenuRequestVoListString") String newResourceMenuRequestVoListString
    );

    /**
     * 列表页--菜单项上移下移
     * @param platformId
     * @param flag
     * @param clickVoString
     * @param token
     * @return
     */
    @RequestLine(value = "POST /feign/resource/move?platformId={platformId}&token={token}&flag={flag}&clickVoString={clickVoString}")
    RespData move(
            @Param("platformId") Long platformId,
            @Param("flag") Integer flag,
            @Param("clickVoString") String clickVoString,
            @Param(value = "token") String token
    );

    /**
     * 列表页--菜单项置顶置底
     * @param platformId
     * @param flag
     * @param clickVoString
     * @param token
     * @return
     */
    @RequestLine(value = "POST /feign/resource/toFirstOrLast?platformId={platformId}&token={token}&flag={flag}&clickVoString={clickVoString}")
    RespData toFirstOrLast(
            @Param("platformId") Long platformId,
            @Param("flag") Long flag,
            @Param("clickVoString") String clickVoString,
            @Param(value = "token") String token
    );

    /**
     * 列表页--同步数据--查询所有的数据--不分层级
     * @param platformId
     * @param token
     * @param owner
     * @return
     */
    @RequestLine(value = "GET /feign/resource/queryAllResourceNotDeal?platformId={platformId}&token={token}&owner={owner}")
    RespData<List<NewResourceMenuResultVo>> queryAllResourceNotDeal(
            @Param("platformId") Long platformId,
            @Param(value = "owner") String owner,
            @Param(value = "token") String token
    );

    /**
     * 列表页--同步数据--新增
     * @param platformId
     * @param token
     * @param resourceRequestVoString
     * @return
     */
    @RequestLine(value = "POST /feign/resource/syncInsertResource?platformId={platformId}&token={token}&resourceRequestVoString={resourceRequestVoString}")
    RespData<Integer> syncInsertResource(
            @Param("platformId") Long platformId,
            @Param(value = "token") String token,
            @Param("resourceRequestVoString") String resourceRequestVoString
    );
}
