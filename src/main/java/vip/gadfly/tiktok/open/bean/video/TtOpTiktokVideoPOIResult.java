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
public class TtOpTiktokVideoPOIResult extends TtOpBaseResult {
    private static final long serialVersionUID = 1L;

    @JSONField(name = "pois")
    @JsonAlias("pois")
    @JsonProperty("pois")
    @SerializedName("pois")
    private List<Poi> pois;


    @NoArgsConstructor
    @Data
    public static class Poi {

        @JSONField(name = "address")
        @JsonAlias("address")
        @JsonProperty("address")
        @SerializedName("address")
        private String address;

        @JSONField(name = "city")
        @JsonAlias("city")
        @JsonProperty("city")
        @SerializedName("city")
        private String city;

        @JSONField(name = "cityCode")
        @JsonAlias("cityCode")
        @SerializedName("cityCode")
        @JsonProperty("city_code")
        private String cityCode;

        @JSONField(name = "country")
        @JsonAlias("country")
        @SerializedName("country")
        @JsonProperty("country")
        private String country;

        @JSONField(name = "country_code")
        @JsonAlias("country_code")
        @SerializedName("country_code")
        @JsonProperty("country_code")
        private String countryCode;

        @JSONField(name = "district")
        @JsonAlias("district")
        @SerializedName("district")
        @JsonProperty("district")
        private String district;

        @JSONField(name = "location")
        @JsonAlias("location")
        @SerializedName("location")
        @JsonProperty("location")
        private String location;

        @JSONField(name = "poi_id")
        @JsonAlias("poi_id")
        @SerializedName("poi_id")
        @JsonProperty("poi_id")
        private String poiId;

        @JSONField(name = "poiName")
        @JsonAlias("poiName")
        @SerializedName("poiName")
        @JsonProperty("poi_name")
        private String poiName;

        @JSONField(name = "province")
        @JsonAlias("province")
        @SerializedName("province")
        @JsonProperty("province")
        private String province;
    }
}
