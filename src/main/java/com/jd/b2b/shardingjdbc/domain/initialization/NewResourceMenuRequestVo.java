package com.jd.b2b.shardingjdbc.domain.initialization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangqiang26
 * @mail wangqiang26@jd.com
 * @Date 16:15 2017/12/31
 */
@Data
public class NewResourceMenuRequestVo {

    /**
     * 主键
     */
    private Long id;
    /**
     * 平台id
     */
    private Long platformId;
    /**
     * menu，tab，button，input,
     */
    @ApiModelProperty(name = "资源类型",value = "menu,tab,button,input")
    private String type;
    /**
     * 资源名称
     */
    @ApiModelProperty(name = "资源名称")
    private String name;
    /**
     * 父id
     */
    private Long pid;
    /**
     * 系统默认权限 1-是 0-否
     */
    @ApiModelProperty("是否系统默认权限1：是，0：否")
    private Integer systemDefault;
    /**
     * url地址
     */
    private String url;
    /**
     * 层级
     */
    private Long level;
    /**
     * 资源归属 platform buyer seller public
     */
    @ApiModelProperty("platform buyer seller public company shop personal buyerMobile")
    private String owner;
    /**
     * 排序号
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    private String created;
    /**
     * 更新时间
     */
    private String updated;
    /**
     * 主键
     */
    private Integer yn;
    /**
     * 唯一标识
     */
    private String code;
    /**
     * 图标
     */
    private String icon;
    /**
     * target
     */
    private String target;
    /**
     * 对应到前端组件编码
     */
    @ApiModelProperty("前端对应的组件编码")
    private String htmlCode;
}
