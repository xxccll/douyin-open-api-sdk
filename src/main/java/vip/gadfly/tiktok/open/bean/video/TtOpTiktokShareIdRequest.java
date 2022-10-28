package vip.gadfly.tiktok.open.bean.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * https://developer.open-douyin.com/docs/resource/zh-CN/dop/develop/openapi/video-management/douyin/search-video/video-share-result
 *
 * @author Gadfly
 * @since 2021-09-30 10:32
 */
@Data
@Accessors(chain = true)
public class TtOpTiktokShareIdRequest {
    /**
     * 如果需要知道视频分享成功的结果，need_callback设置为true
     */
    @JSONField(name = "need_callback")
    @JsonAlias("need_callback")
    @JsonProperty("need_callback")
    @SerializedName("need_callback")
    private Boolean needCallback = true;
    /**
     * 多来源样式id（暂未开放）
     */
    @JSONField(name = "source_style_id")
    @JsonAlias("source_style_id")
    @JsonProperty("source_style_id")
    @SerializedName("source_style_id")
    private String sourceStyleId = "";
    /**
     * 追踪分享默认hashtag
     */
    @JSONField(name = "default_hashtag")
    @JsonAlias("default_hashtag")
    @JsonProperty("default_hashtag")
    @SerializedName("default_hashtag")
    private String defaultHashtag = "";
    /**
     * 分享来源url附加参数（暂未开放）
     */
    @JSONField(name = "link_param")
    @JsonAlias("link_param")
    @JsonProperty("link_param")
    @SerializedName("link_param")
    private String linkParam = "";
}
