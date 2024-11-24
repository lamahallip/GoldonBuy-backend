package com.goldonbuy.goldonbackend.catalogContext.requestDTO;

import lombok.Data;

@Data
public class AddImageRequest {
    private Long id;
    private String fileName;
    private String downloadUrl;
}
