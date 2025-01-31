package vip.gadfly.tiktok.open.bean.message;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import vip.gadfly.tiktok.core.util.json.TiktokOpenJsonBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Gafly
 * @since 2021/09/22
 **/
@Slf4j
@Data
public class TtOpWebhookMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 事件名
     */
    @JSONField(name = "event")
    @JsonAlias("event")
    @JsonProperty("event")
    @SerializedName("event")
    private String event;
    /**
     * 事件发起用户user_id
     */
    @JSONField(name = "from_user_id")
    @JsonAlias("from_user_id")
    @JsonProperty("from_user_id")
    @SerializedName("from_user_id")
    private String fromUserId;
    /**
     * 事件接收用户user_id
     */
    @JSONField(name = "to_user_id")
    @JsonAlias("to_user_id")
    @JsonProperty("to_user_id")
    @SerializedName("to_user_id")
    private String toUserId;
    /**
     * 使用应用的client_key
     */
    @JSONField(name = "client_key")
    @JsonAlias("client_key")
    @JsonProperty("client_key")
    @SerializedName("client_key")
    private String clientKey;
    @JSONField(name = "msg_id")
    @JsonAlias("msg_id")
    @JsonProperty("msg_id")
    @SerializedName("msg_id")
    private String msgId;
    /**
     * Webhook 消息内容
     */
    @JSONField(name = "content")
    @JsonAlias("content")
    @JsonProperty("content")
    @SerializedName("content")
    private WebhookContent content;

    public static TtOpWebhookMessage fromJson(String json) {
        return TiktokOpenJsonBuilder.instance().parse(json, TtOpWebhookMessage.class);
    }

    @Data
    public static class WebhookContent {
        /**
         * 在管理中心 Webhooks页填写你要配置的请求网址。完成请求网址编辑后，点击保存按钮时，
         * 开放平台会向你配置的网址推送一个 application/json 格式的 POST 请求, 该请求用于验证你配置的网址的合法性。
         * 通过开放平台验证的请求网址才能配置成功，请求网址配置成功后，你才可以进一步订阅你的应用关心的事件。
         * 当你收到开放平台 POST 验证请求时，你需要解析出 challenge 值，并立即返回该 challenge 值作为响应。
         */
        @JSONField(name = "challenge")
        @JsonAlias("challenge")
        @JsonProperty("challenge")
        @SerializedName("challenge")
        private Long challenge;
        /**
         * 创建的视频id
         * 被点赞的素材id
         */
        @JSONField(name = "item_id")
        @JsonAlias("item_id")
        @JsonProperty("item_id")
        @SerializedName("item_id")
        private String itemId;
        /**
         * 创建的视频id,可以直接拼接抖音域名打开 https://www.douyin.com/video/video_id
         * 创建的视频id
         */
        @JSONField(name = "video_id")
        @JsonAlias("video_id")
        @JsonProperty("video_id")
        @SerializedName("video_id")
        private String videoId;
        /**
         * 追踪分享默认hashtag
         * 追踪分享默认hashtag
         */
        @JSONField(name = "has_default_hashtag")
        @JsonAlias("has_default_hashtag")
        @JsonProperty("has_default_hashtag")
        @SerializedName("has_default_hashtag")
        private String hasDefaultHashtag;
        /**
         * 标识分享的share_id
         */
        @JSONField(name = "share_id")
        @JsonAlias("share_id")
        @JsonProperty("share_id")
        @SerializedName("share_id")
        private String shareId;
        /**
         * 授权scope列表
         */
        @JSONField(name = "scopes")
        @JsonAlias("scopes")
        @JsonProperty("scopes")
        @SerializedName("scopes")
        private List<String> scopes;
        /**
         * 消息类型
         */
        @JSONField(name = "message_type")
        @JsonAlias("message_type")
        @JsonProperty("message_type")
        @SerializedName("message_type")
        private String messageType;
        /**
         * 文本消息内容
         */
        @JSONField(name = "text")
        @JsonAlias("text")
        @JsonProperty("text")
        @SerializedName("text")
        private String text;
        /**
         * 资源类型
         */
        @JSONField(name = "resource_type")
        @JsonAlias("resource_type")
        @JsonProperty("resource_type")
        @SerializedName("resource_type")
        private String resourceType;
        /**
         * 资源高度
         */
        @JSONField(name = "resource_height")
        @JsonAlias("resource_height")
        @JsonProperty("resource_height")
        @SerializedName("resource_height")
        private String resourceHeight;
        /**
         * 资源宽度
         */
        @JSONField(name = "resource_width")
        @JsonAlias("resource_width")
        @JsonProperty("resource_width")
        @SerializedName("resource_width")
        private String resourceWidth;
        /**
         * 资源链接
         */
        @JSONField(name = "resource_url")
        @JsonAlias("resource_url")
        @JsonProperty("resource_url")
        @SerializedName("resource_url")
        private String resourceUrl;
        /**
         * 卡片标题 / 消息内容
         */
        @JSONField(name = "title")
        @JsonAlias("title")
        @JsonProperty("title")
        @SerializedName("title")
        private String title;
        /**
         * 卡片图标
         */
        @JSONField(name = "icon_url")
        @JsonAlias("icon_url")
        @JsonProperty("icon_url")
        @SerializedName("icon_url")
        private String iconUrl;
        /**
         * 卡片描述
         */
        @JSONField(name = "description")
        @JsonAlias("description")
        @JsonProperty("description")
        @SerializedName("description")
        private String description;
        /**
         * 跳转链接
         */
        @JSONField(name = "link_url")
        @JsonAlias("link_url")
        @JsonProperty("link_url")
        @SerializedName("link_url")
        private String linkUrl;
        /**
         * 内容中附带链接的文本内容信息
         */
        @JSONField(name = "actions")
        @JsonAlias("actions")
        @JsonProperty("actions")
        @SerializedName("actions")
        private Map<String, ActionInfo> actions;
        /**
         * 进入对话来源场景["video", "homepage"]
         */
        @JSONField(name = "scene")
        @JsonAlias("scene")
        @JsonProperty("scene")
        @SerializedName("scene")
        private String scene;
        /**
         * 来源场景对应id（video对应视频id）
         */
        @JSONField(name = "object")
        @JsonAlias("object")
        @JsonProperty("object")
        @SerializedName("object")
        private String object;
        /**
         * 来源场景对应id（video对应视频id）
         */
        @JSONField(name = "action_time")
        @JsonAlias("action_time")
        @JsonProperty("action_time")
        @SerializedName("action_time")
        private Date actionTime;
        /**
         * 来源场景对应id（video对应视频id）
         */
        @JSONField(name = "action_type")
        @JsonAlias("action_type")
        @JsonProperty("action_type")
        @SerializedName("action_type")
        private Integer actionType;

        /**
         * 点赞数(来源视频评论)
         */
        @JSONField(name = "digg_count")
        @JsonAlias("digg_count")
        @JsonProperty("digg_count")
        @SerializedName("digg_count")
        private Integer diggCount;

        /**
         * 评论用户openid(来源视频评论)
         */
        @JSONField(name = "comment_user_id")
        @JsonAlias("comment_user_id")
        @JsonProperty("comment_user_id")
        @SerializedName("comment_user_id")
        private String commentUserId;

        /**
         * 评论内容(来源视频评论)
         */
        @JSONField(name = "content")
        @JsonAlias("content")
        @JsonProperty("content")
        @SerializedName("content")
        private String content;

        /**
         * 回复总数(来源视频评论)
         */
        @JSONField(name = "reply_comment_total")
        @JsonAlias("reply_comment_total")
        @JsonProperty("reply_comment_total")
        @SerializedName("reply_comment_total")
        private Integer replyCommentTotal;

        /**
         * 回复的评论 id(来源视频评论)
         */
        @JSONField(name = "reply_to_comment_id")
        @JsonAlias("reply_to_comment_id")
        @JsonProperty("reply_to_comment_id")
        @SerializedName("reply_to_comment_id")
        private String replyToCommentId;

        /**
         * 回复的视频 id(来源视频评论)
         */
        @JSONField(name = "reply_to_item_id")
        @JsonAlias("reply_to_item_id")
        @JsonProperty("reply_to_item_id")
        @SerializedName("reply_to_item_id")
        private String replyToItemId;

        /**
         * @ 的用户 id(来源视频评论)
         */
        @JSONField(name = "at_user_id")
        @JsonAlias("at_user_id")
        @JsonProperty("at_user_id")
        @SerializedName("at_user_id")
        private String atUserId;

        /**
         * 评论id id(来源视频评论)
         */
        @JSONField(name = "comment_id")
        @JsonAlias("comment_id")
        @JsonProperty("comment_id")
        @SerializedName("comment_id")
        private String commentId;

        /**
         * 评论时间(来源视频评论)
         */
        @JSONField(name = "create_time")
        @JsonAlias("create_time")
        @JsonProperty("create_time")
        @SerializedName("create_time")
        private Integer createTime;


        @Data
        public static class ActionInfo {
            @JSONField(name = "name")
            @JsonAlias("name")
            @JsonProperty("name")
            @SerializedName("name")
            private String name;

            @JSONField(name = "value")
            @JsonAlias("value")
            @JsonProperty("value")
            @SerializedName("value")
            private String value;

            @JSONField(name = "action_type")
            @JsonAlias("action_type")
            @JsonProperty("action_type")
            @SerializedName("action_type")
            private String actionType;
        }
    }
}
