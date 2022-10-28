package vip.gadfly.tiktok.open.bean.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * https://developer.open-douyin.com/docs/resource/zh-CN/dop/develop/openapi/interaction-management/comment-management-user/comment-list
 *
 * @author Gadfly
 * @since 2021-09-30 10:32
 */
@Data
@Accessors(chain = true)
public class TtOpTiktokVideoCommentRequest {

    /**
     * 通过/oauth/access_token/获取，用户唯一标志
     */
    @JSONField(name = "open_id")
    @JsonAlias("open_id")
    @JsonProperty("open_id")
    @SerializedName("open_id")
    private String openId;

    /**
     * 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     */
    @JSONField(name = "cursor")
    @JsonAlias("cursor")
    @JsonProperty("cursor")
    @SerializedName("cursor")
    private long cursor;
    /**
     * 	每页的数量，最大不超过50，最小不低于1
     */
    @JSONField(name = "count")
    @JsonAlias("count")
    @JsonProperty("count")
    @SerializedName("count")
    private long count = 10;
    /**
     * 视频id @8hxdhauTCMppanGnM4ltGM780mDqPP+KPpR0qQOmLVAXb/T060zdRmYqig357zEBq6CZRp4NVe6qLIJW/V/x1w==
     */
    @JSONField(name = "itemId")
    @JsonAlias("itemId")
    @JsonProperty("itemId")
    @SerializedName("itemId")
    private String itemId;
    /**
     * 列表排序方式，不传默认按推荐序，可选值：time(时间逆序)、time_asc(时间顺序)
     */
    @JSONField(name = "sort_type")
    @JsonAlias("sort_type")
    @JsonProperty("sort_type")
    @SerializedName("sort_type")
    private String sortType = "time";
}
