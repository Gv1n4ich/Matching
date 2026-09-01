package com.match.matching.swipe.dto;

import com.match.matching.swipe.Swipe;

public class SwipeRequest {
    private Long targetProfileId;
    private Swipe.SwipeType type;

    public Long getTargetProfileId() { return targetProfileId; }
    public void setTargetProfileId(Long targetProfileId) { this.targetProfileId = targetProfileId; }
    public Swipe.SwipeType getType() { return type; }
    public void setType(Swipe.SwipeType type) { this.type = type; }
}