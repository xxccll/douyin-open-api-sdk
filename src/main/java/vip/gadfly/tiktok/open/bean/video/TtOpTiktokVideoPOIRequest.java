package vip.gadfly.tiktok.open.bean.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * https://developer.open-douyin.com/docs/resource/zh-CN/dop/develop/openapi/video-management/douyin/search-video/video-poi
 *
 * @author Gadfly
 * @since 2021-09-30 10:32
 */
@Data
@Accessors(chain = true)
public class TtOpTiktokVideoPOIRequest {
    /**
     * 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     */
    @JSONField(name = "cursor")
    @JsonAlias("cursor")
    @JsonProperty("cursor")
    @SerializedName("cursor")
    private long cursor;
    /**
     * 每页数量
     */
    @JSONField(name = "count")
    @JsonAlias("count")
    @JsonProperty("count")
    @SerializedName("count")
    private long count = 10;
    /**
     * 查询关键字，例如美食
     */
    @JSONField(name = "keyword")
    @JsonAlias("keyword")
    @JsonProperty("keyword")
    @SerializedName("keyword")
    private String keyword = "";
    /**
     * 查询城市，例如上海、北京
     */
    @JSONField(name = "city")
    @JsonAlias("city")
    @JsonProperty("city")
    @SerializedName("city")
    private String city = "";
}
