package vip.gadfly.tiktok.open.api.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import vip.gadfly.tiktok.config.TtOpConfigStorage;
import vip.gadfly.tiktok.core.enums.TtOpTicketType;
import vip.gadfly.tiktok.core.util.URIUtil;
import vip.gadfly.tiktok.open.api.TtOpOAuth2Service;
import vip.gadfly.tiktok.open.bean.oauth2.TtOpAccessTokenRequest;
import vip.gadfly.tiktok.open.bean.oauth2.TtOpAccessTokenResult;
import vip.gadfly.tiktok.open.common.ITtOpBaseService;

import static vip.gadfly.tiktok.core.enums.TtOpApiUrl.OAuth2.*;

/**
 * @author Gadfly
 * @since 2021-09-18 14:19
 */
@Slf4j
@RequiredArgsConstructor
public class TtOpOauth2ServiceImpl implements TtOpOAuth2Service {
    private final ITtOpBaseService ttOpBaseService;

    protected TtOpConfigStorage getTtOpConfigStorage() {
        return this.ttOpBaseService.getTtOpConfigStorage();
    }

    @Override
    public String buildAuthorizationUrl(String redirectUri, String scope, String state, String optionalScope) {
        log.debug("构造oauth2授权的url连接，收到的参数：redirectUri={},scope={},state={}", redirectUri, scope, state);
        return String.format(CONNECT_OAUTH2_AUTHORIZE_URL.getUrl(getTtOpConfigStorage()),
                getTtOpConfigStorage().getClientKey(), URIUtil.encodeURIComponent(redirectUri), scope, StringUtils.trimToEmpty(state), StringUtils.trimToEmpty(optionalScope));
    }

    @Override
    public String buildSilentAuthorizationUrl(String redirectUri, String scope, String state) {
        log.debug("构造oauth2授权的url连接，收到的参数：redirectUri={},scope={},state={}", redirectUri, scope, state);
        return String.format(CONNECT_SILENT_OAUTH2_AUTHORIZE_URL.getUrl(null),
                getTtOpConfigStorage().getClientKey(), URIUtil.encodeURIComponent(redirectUri), scope, StringUtils.trimToEmpty(state));
    }

    @Override
    public TtOpAccessTokenResult getAccessTokenByAuthorizationCode(String authorizationCode) {
        log.debug("使用授权码换取用户信息的接口调用凭据，收到的参数：authorizationCode={}", authorizationCode);
        TtOpConfigStorage ttOpConfigStorage = getTtOpConfigStorage();
        String url = OAUTH2_ACCESS_TOKEN_URL.getUrl(ttOpConfigStorage);
        log.debug("url={}", url);
        TtOpAccessTokenRequest request = new TtOpAccessTokenRequest()
                .setGrantType(TtOpAccessTokenRequest.GRANT_TYPE_CODE)
                .setClientKey(ttOpConfigStorage.getClientKey())
                .setClientSecret(ttOpConfigStorage.getClientSecret())
                .setCode(authorizationCode);
        Multimap<String, String> headers = LinkedListMultimap.create();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        TtOpAccessTokenResult result = this.ttOpBaseService.postWithHeaders(url, headers, request, TtOpAccessTokenResult.class);
        ttOpConfigStorage.updateAccessToken(result);
        ttOpConfigStorage.updateRefreshToken(result);
        return result;
    }

    @Override
    public TtOpAccessTokenResult refreshAccessToken(String openid) {
        TtOpConfigStorage ttOpConfigStorage = getTtOpConfigStorage();
        String url = OAUTH2_REFRESH_TOKEN_URL.getUrl(ttOpConfigStorage);
        String refreshToken = ttOpConfigStorage.getRefreshToken(openid);
        log.debug("url={},rft={}", url, refreshToken);
        TtOpAccessTokenRequest request = new TtOpAccessTokenRequest()
                .setGrantType(TtOpAccessTokenRequest.GRANT_TYPE_REFRESH)
                .setClientKey(ttOpConfigStorage.getClientKey())
                .setRefreshToken(refreshToken)
                ;
        Multimap<String, String> headers = LinkedListMultimap.create();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        TtOpAccessTokenResult result = this.ttOpBaseService.postWithHeaders(url, headers, request, TtOpAccessTokenResult.class);
        ttOpConfigStorage.updateAccessToken(result);
        return result;
    }

    @Override
    public TtOpAccessTokenResult renewRefreshToken(String openid) {
        TtOpConfigStorage ttOpConfigStorage = getTtOpConfigStorage();
        String refreshToken = ttOpConfigStorage.getRefreshToken(openid);
        String url = OAUTH2_RENEW_REFRESH_TOKEN_URL.getUrl(ttOpConfigStorage);
        log.debug("url={},rft={}", url, refreshToken);
        TtOpAccessTokenRequest request = new TtOpAccessTokenRequest()
                .setClientKey(ttOpConfigStorage.getClientKey())
                .setRefreshToken(refreshToken)
                ;
        Multimap<String, String> headers = LinkedListMultimap.create();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        TtOpAccessTokenResult result = this.ttOpBaseService.postWithHeaders(url, headers, request, TtOpAccessTokenResult.class);
        ttOpConfigStorage.updateRefreshToken(openid, result.getRefreshToken(), result.getExpiresIn());
        return result;
    }

    @Override
    public String getClientToken(boolean forceRefresh) {
        if (!forceRefresh) {
            return ttOpBaseService.getTicket(TtOpTicketType.CLIENT);
        }
        TtOpConfigStorage ttOpConfigStorage = getTtOpConfigStorage();
        String url = OAUTH2_CLIENT_TOKEN_URL.getUrl(ttOpConfigStorage);
        log.debug("url={}", url);
        TtOpAccessTokenRequest request = new TtOpAccessTokenRequest()
                .setGrantType(TtOpAccessTokenRequest.GRANT_TYPE_CLIENT)
                .setClientKey(ttOpConfigStorage.getClientKey())
                .setClientSecret(ttOpConfigStorage.getClientSecret());
        Multimap<String, String> headers = LinkedListMultimap.create();
        headers.put("Content-Type", "multipart/form-data");
        TtOpAccessTokenResult result = this.ttOpBaseService.postWithHeaders(url, headers, request, TtOpAccessTokenResult.class);
        ttOpConfigStorage.updateTicket(TtOpTicketType.CLIENT, result.getAccessToken(), result.getExpiresIn());
        return result.getAccessToken();
    }

    @Override
    public String getJsapiTicket(boolean forceRefresh) {
        if (!forceRefresh) {
            return ttOpBaseService.getTicket(TtOpTicketType.JSAPI);
        }
        String rawUrl = OAUTH2_JSAPI_TICKET_URL.getUrl(getTtOpConfigStorage());
        String url = String.format(rawUrl, ttOpBaseService.getTicket(TtOpTicketType.CLIENT));
        log.debug("url={}", url);
        TtOpAccessTokenResult result = this.ttOpBaseService.get(url, TtOpAccessTokenResult.class);
        this.getTtOpConfigStorage().updateTicket(TtOpTicketType.JSAPI, result.getTicket(), result.getExpiresIn());
        return result.getTicket();
    }

    @Override
    public String getOpenTicket(boolean forceRefresh) {
        if (!forceRefresh) {
            return ttOpBaseService.getTicket(TtOpTicketType.OPEN_TICKET);
        }
        String rawUrl = OAUTH2_OPEN_TICKET_URL.getUrl(getTtOpConfigStorage());
        String url = String.format(rawUrl, ttOpBaseService.getTicket(TtOpTicketType.CLIENT));
        log.debug("url={}", url);
        TtOpAccessTokenResult result = this.ttOpBaseService.get(url, TtOpAccessTokenResult.class);
        this.getTtOpConfigStorage().updateTicket(TtOpTicketType.OPEN_TICKET, result.getTicket(), result.getExpiresIn());
        return result.getTicket();
    }

    @Override
    public String getTicket(TtOpTicketType type, boolean forceRefresh) {
        switch (type) {
            case JSAPI:
                return this.getJsapiTicket(forceRefresh);
            case OPEN_TICKET:
                return this.getOpenTicket(forceRefresh);
            case CLIENT:
                return this.getClientToken(forceRefresh);
            default:
                return null;
        }
    }
}
