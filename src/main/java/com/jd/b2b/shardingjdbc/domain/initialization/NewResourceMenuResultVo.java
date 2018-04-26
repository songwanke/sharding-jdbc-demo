package com.jd.b2b.shardingjdbc.domain.initialization;

import com.jd.ecc.commons.web.model.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songwanke on 2017/5/18
 */
@Data
public class NewResourceMenuResultVo extends BaseVo{

    /**
     * 平台id
    */
    @ApiModelProperty("平台id")
    private Long platformId;
    /**
     * 菜单，tab，button，input
    */
    @ApiModelProperty("菜单，tab，button，input")
    private String type;
    /**
     * 资源名称
    */
    @ApiModelProperty("资源名称")
    private String name;
    /**
     * 资源名称
    */
    private String title;
    /**
     * 父id
    */
    private Long pid;
    /**
     * 系统默认权限
    */
    private Integer systemDefault;
    /**
     * url地址
    */
    @ApiModelProperty("url地址")
    private String url;
    /**
     * 层级
    */
    private Integer level;
    /**
     * 资源归属 platform buyer seller
    */
    private String owner;
    /**
     * 排序号
    */
    private Integer orderNum;

    private String htmlCode;

    private String treeCodeDir;



    /**
     * 唯一标识
    */
    private String code;
    @ApiModelProperty("icon")
    private String icon;
    @ApiModelProperty("target")
    private String target;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private List<NewResourceMenuResultVo> children=new ArrayList<>();

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSystemDefault() {
        return systemDefault;
    }

    public void setSystemDefault(Integer systemDefault) {
        this.systemDefault = systemDefault;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<NewResourceMenuResultVo> getChildren() {
        return children;
    }

    public void setChildren(List<NewResourceMenuResultVo> children) {
        this.children = children;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    @Override
    public String toString() {
        return "ResourceMenuResultVo{" +
                "platformId=" + platformId +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", systemDefault=" + systemDefault +
                ", url='" + url + '\'' +
                ", level=" + level +
                ", owner='" + owner + '\'' +
                ", orderNum=" + orderNum +
                ", htmlCode='" + htmlCode + '\'' +
                ", code='" + code + '\'' +
                ", icon='" + icon + '\'' +
                ", target='" + target + '\'' +
                ", children=" + children +
                '}';
    }
}
