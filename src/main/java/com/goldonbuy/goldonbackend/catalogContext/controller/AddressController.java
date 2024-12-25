package com.goldonbuy.goldonbackend.catalogContext.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("{api.prefix}/address")
public class AddressController {

    private final AddressController addressController;
}
