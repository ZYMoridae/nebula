package com.jz.nebula.service.channel.netease;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NeteaseResponse {

    @SerializedName("BA8D4A3Rwangning")
    private List<NeteaseNewsItem> techResonse;

    @SerializedName("BBM54PGAwangning")
    private List<NeteaseNewsItem> newsResponse;

    @SerializedName("BA10TA81wangning")
    private List<NeteaseNewsItem> entertainmentResponse;

    @SerializedName("BA8E6OEOwangning")
    private List<NeteaseNewsItem> sportResponse;

    @SerializedName("BA8EE5GMwangning")
    private List<NeteaseNewsItem> financeResponse;

    @SerializedName("BAI67OGGwangning")
    private List<NeteaseNewsItem> militaryResponse;

    @SerializedName("BAI6I0O5wangning")
    private List<NeteaseNewsItem> phoneResponse;

    @SerializedName("BAI6JOD9wangning")
    private List<NeteaseNewsItem> digitalResponse;

    @SerializedName("BA8F6ICNwangning")
    private List<NeteaseNewsItem> fashionResponse;

    @SerializedName("BAI6RHDKwangning")
    private List<NeteaseNewsItem> gamingResponse;

    @SerializedName("BA8FF5PRwangning")
    private List<NeteaseNewsItem> educationResponse;

    @SerializedName("BDC4QSV3wangning")
    private List<NeteaseNewsItem> healthResponse;

    @SerializedName("BEO4GINLwangning")
    private List<NeteaseNewsItem> travelResponse;

}
