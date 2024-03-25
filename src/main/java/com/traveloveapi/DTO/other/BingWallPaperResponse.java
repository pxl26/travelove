package com.traveloveapi.DTO.other;

import lombok.Data;

@Data
public class BingWallPaperResponse {
    private BingImage[] images;
    private Object tooltips;
}
