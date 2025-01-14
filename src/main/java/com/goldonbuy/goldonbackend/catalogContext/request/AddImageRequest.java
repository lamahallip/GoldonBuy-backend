package com.goldonbuy.goldonbackend.catalogContext.request;

import lombok.Data;

@Data
public class AddImageRequest {
    private Long id;
    private String fileName;
    private String downloadUrl;
}
