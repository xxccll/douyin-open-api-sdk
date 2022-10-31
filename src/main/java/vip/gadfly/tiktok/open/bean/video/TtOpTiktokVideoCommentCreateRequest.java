package vip.gadfly.tiktok.open.bean.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * https://developer.open-douyin.com/docs/resource/zh-CN/dop/develop/openapi/interaction-management/comment-management-user/video-comment-reply
 *
 */
@Data
@Accessors(chain = true)
public class TtOpTiktokVideoCommentCreateRequest {

    /**
     * 视频id @8hxdhauTCMppanGnM4ltGM780mDqPP+KPpR0qQOmLVAXb/T060zdRmYqig357zEBq6CZRp4NVe6qLIJW/V/x1w==
     */
    @JSONField(name = "item_id")
    @JsonAlias("item_id")
    @JsonProperty("item_id")
    @SerializedName("item_id")
    private String itemId;

    /**
     * 若需要对评论进行回复，则需要传评论id
     */
    @JSONField(name = "comment_id")
    @JsonAlias("comment_id")
    @JsonProperty("comment_id")
    @SerializedName("comment_id")
    private String commentId;
    /**
     *  评论的具体内容
     */
    @JSONField(name = "content")
    @JsonAlias("content")
    @JsonProperty("content")
    @SerializedName("content")
    private String content;
}
