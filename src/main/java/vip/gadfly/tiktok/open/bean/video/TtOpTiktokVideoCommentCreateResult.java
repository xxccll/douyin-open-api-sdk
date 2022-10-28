package vip.gadfly.tiktok.open.bean.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vip.gadfly.tiktok.open.common.TtOpBaseResult;

import java.util.List;

/**
 * @author Gadfly
 * @since 2021-11-09 11:59
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class TtOpTiktokVideoCommentCreateResult extends TtOpBaseResult {

    private static final long serialVersionUID = 1L;


    @JSONField(name = "comment_id")
    @JsonAlias("comment_id")
    @JsonProperty("comment_id")
    @SerializedName("comment_id")
    private String commentId;

}
