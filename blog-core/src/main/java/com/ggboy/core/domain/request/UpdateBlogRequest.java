package com.ggboy.core.domain.request;

import com.ggboy.common.annotation.MaxLength;
import com.ggboy.common.annotation.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UpdateBlogRequest {
    @NotNull
    private String id;
    @NotNull
    @MaxLength(32)
    private String title;
    @NotNull
    @MaxLength(140)
    private String synopsis;
    @NotNull
    private String content;
    @NotNull
    @MaxLength(8)
    private String status;
    @NotNull
    private BigDecimal weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getWeight() {
        if (weight == null)
            return new BigDecimal(0);
        weight.setScale(3, RoundingMode.DOWN);
        return weight.compareTo(BigDecimal.TEN) == -1 ? weight.compareTo(BigDecimal.ZERO) != -1 ? weight : BigDecimal.ZERO : new BigDecimal("9.999");
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
