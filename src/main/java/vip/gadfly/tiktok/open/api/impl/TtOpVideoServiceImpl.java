package vip.gadfly.tiktok.open.api.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vip.gadfly.tiktok.config.TtOpConfigStorage;
import vip.gadfly.tiktok.core.enums.TtOpTicketType;
import vip.gadfly.tiktok.open.api.TtOpVideoService;
import vip.gadfly.tiktok.open.bean.video.*;
import vip.gadfly.tiktok.open.common.ITtOpBaseService;

import static vip.gadfly.tiktok.core.enums.TtOpApiUrl.Video.*;

/**
 * @author Gadfly
 * @since 2021-09-30 15:01
 */
@Slf4j
@RequiredArgsConstructor
public class TtOpVideoServiceImpl implements TtOpVideoService {
    private final ITtOpBaseService ttOpBaseService;

    protected TtOpConfigStorage getTtOpConfigStorage() {
        return this.ttOpBaseService.getTtOpConfigStorage();
    }

    @Override
    public TtOpTiktokVideoCreateResult createTiktokVideo(String openId, TtOpTiktokVideoCreateRequest request) {
        log.debug("创建抖音视频，收到的参数：openId={}, request={}", openId, request);
        String url = String.format(CREATE_TIKTOK_VIDEO_URL.getUrl(getTtOpConfigStorage()),
                openId, this.ttOpBaseService.getAccessToken(openId));
        log.debug("url={}， request={}", url, request);
        return this.ttOpBaseService.post(url, request, TtOpTiktokVideoCreateResult.class);
    }

    @Override
    public TtOpTiktokVideoUploadResult uploadTiktokVideo(String openId, TtOpTiktokVideoUploadRequest request) {
        log.debug("上传抖音视频，收到的参数：openId={}, request={}", openId, request);
        String url = String.format(UPLOAD_TIKTOK_VIDEO_URL.getUrl(getTtOpConfigStorage()),
                openId, this.ttOpBaseService.getAccessToken(openId));
        Multimap<String, String> headers = LinkedListMultimap.create();
        headers.put("Content-Type", "multipart/form-data");
        log.debug("url={}, headers={}", url, headers);
        return this.ttOpBaseService.postWithHeaders(url, headers, request, TtOpTiktokVideoUploadResult.class);
    }

    @Override
    public TtOpTiktokVideoDataResult getTiktokSpecificVideoData(String openId, TtOpTiktokVideoDataRequest request) {
        log.debug("查询抖音指定视频数据，收到的参数：openId={}, request={}", openId, request);
        String url = String.format(GET_TIKTOK_SPECIFIC_VIDEO_DATA_URL.getUrl(getTtOpConfigStorage()),
                openId, this.ttOpBaseService.getAccessToken(openId));
        log.debug("url={}, request={}", url, request);
        return this.ttOpBaseService.post(url, request, TtOpTiktokVideoDataResult.class);
    }

    @Override
    public TtOpTiktokShareIdResult createShareId(TtOpTiktokShareIdRequest req) {
        log.debug("生成视频shareId，收到的参数：request={}", req);
        String rawUrl = CREATE_TIKTOK_SHARE_ID_URL.getUrl(getTtOpConfigStorage());
        String accessToken = this.ttOpBaseService.getTicket(TtOpTicketType.CLIENT, false);
        String url = String.format(rawUrl, accessToken, req.getNeedCallback(), req.getSourceStyleId(), req.getDefaultHashtag(), req.getLinkParam());
        log.debug("url={}", url);
        return this.ttOpBaseService.get(url, TtOpTiktokShareIdResult.class);
    }

    @Override
    public TtOpTiktokVideoPOIResult getTiktokVideoPOI(TtOpTiktokVideoPOIRequest request) {
        log.debug("查询特定视频的视频数据, request={}", request);
        String rawUrl = GET_TIKTOK_VIDEO_POI_URL.getUrl(getTtOpConfigStorage());
        String url = String.format(rawUrl, this.ttOpBaseService.getTicket(TtOpTicketType.CLIENT, false));
        log.debug("url={}, request={}", url, request);
        return this.ttOpBaseService.post(url, request, TtOpTiktokVideoPOIResult.class);
    }

    @Override
    public TtOpTiktokVideoCommentResult getTiktokVideoComments(TtOpTiktokVideoCommentRequest req) {
        log.debug("查询特定视频的评论数据, req={}", req);
        String rawUrl = GET_TIKTOK_VIDEO_COMMENT_URL.getUrl(getTtOpConfigStorage());
        String accessToken = this.ttOpBaseService.getAccessToken(req.getOpenId());
        String url = String.format(rawUrl, accessToken, req.getOpenId(), req.getItemId(), req.getCursor(), req.getCount(), req.getSortType());
        log.debug("url={}, req={}", url, req);
        return this.ttOpBaseService.get(url, TtOpTiktokVideoCommentResult.class);
    }

    @Override
    public TtOpTiktokVideoCommentResult getTiktokVideoCommentReplies(TtOpTiktokVideoCommentRequest req, String commentId) {
        log.debug("查询评论的回复列表数据, req={}, comment_id={}", req, commentId);
        String rawUrl = GET_TIKTOK_VIDEO_COMMENT_REPLY_URL.getUrl(getTtOpConfigStorage());
        String accessToken = this.ttOpBaseService.getAccessToken(req.getOpenId());
        String url = String.format(rawUrl, accessToken, req.getOpenId(), req.getItemId(), req.getCursor(), req.getCount(), req.getSortType(), commentId);
        log.debug("url={}, req={}", url, req);
        return this.ttOpBaseService.get(url, TtOpTiktokVideoCommentResult.class);
    }

    @Override
    public TtOpTiktokVideoCommentCreateResult createTiktokVideoComment(String openId, TtOpTiktokVideoCommentCreateRequest request) {
        log.debug("发布视频评论, 收到的参数：openId={}, request={}", openId, request);
        String rawUrl = CREATE_TIKTOK_COMMENT_URL.getUrl(getTtOpConfigStorage());
        String accessToken = this.ttOpBaseService.getAccessToken(openId);
        String url = String.format(rawUrl, openId, accessToken);
        log.debug("url={}, request={}", url, request);
        return this.ttOpBaseService.post(url, request, TtOpTiktokVideoCommentCreateResult.class);
    }
}
