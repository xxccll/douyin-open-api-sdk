package vip.gadfly.tiktok.open.bean.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * https://open.douyin.com/platform/doc/6848798087398328323
 *
 * @author Gadfly
 * @since 2021-09-30 10:32
 */
@Data
@Accessors(chain = true)
public class TtOpTiktokShareIdRequest {
    /**
     * 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     */
    @JSONField(name = "need_callback")
    @JsonAlias("need_callback")
    @JsonProperty("need_callback")
    @SerializedName("need_callback")
    private Boolean needCallback = true;
    /**
     * 每页数量
     */
    @JSONField(name = "source_style_id")
    @JsonAlias("source_style_id")
    @JsonProperty("source_style_id")
    @SerializedName("source_style_id")
    private String sourceStyleId = "";
    /**
     * 查询关键字，例如美食
     */
    @JSONField(name = "default_hashtag")
    @JsonAlias("default_hashtag")
    @JsonProperty("default_hashtag")
    @SerializedName("default_hashtag")
    private String defaultHashtag = "";
    /**
     * 查询城市，例如上海、北京
     */
    @JSONField(name = "link_param")
    @JsonAlias("link_param")
    @JsonProperty("link_param")
    @SerializedName("link_param")
    private String linkParam = "";
}
