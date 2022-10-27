package vip.gadfly.tiktok.open.bean.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vip.gadfly.tiktok.open.bean.userinfo.TtOpBaseUserInfo;
import vip.gadfly.tiktok.open.common.TtOpBaseResult;

import java.util.List;

/**
 * @author Gadfly
 * @since 2021-11-09 11:59
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class TtOpTiktokVideoCommentResult extends TtOpBaseResult {

    private static final long serialVersionUID = 1L;


    @JSONField(name = "list")
    @JsonAlias("list")
    @JsonProperty("list")
    @SerializedName("list")
    private List<Comment> list;

    @NoArgsConstructor
    @Data
    public static class Comment {
        @JSONField(name = "comment_id")
        @JsonAlias("comment_id")
        @JsonProperty("comment_id")
        @SerializedName("comment_id")
        private String commentId;

        @JSONField(name = "comment_user_id")
        @JsonAlias("comment_user_id")
        @JsonProperty("comment_user_id")
        @SerializedName("comment_user_id")
        private String commentUserId;

        @JSONField(name = "content")
        @JsonAlias("content")
        @SerializedName("content")
        @JsonProperty("content")
        private String content;

        @JSONField(name = "create_time")
        @JsonAlias("create_time")
        @SerializedName("create_time")
        @JsonProperty("create_time")
        private Long createTime;

        @JSONField(name = "digg_count")
        @JsonAlias("digg_count")
        @SerializedName("digg_count")
        @JsonProperty("digg_count")
        private Integer diggCount;

        @JSONField(name = "reply_comment_total")
        @JsonAlias("reply_comment_total")
        @SerializedName("reply_comment_total")
        @JsonProperty("reply_comment_total")
        private Integer replyCommentTotal;

        @JSONField(name = "top")
        @JsonAlias("top")
        @SerializedName("top")
        @JsonProperty("top")
        private Boolean top;
    }

}
