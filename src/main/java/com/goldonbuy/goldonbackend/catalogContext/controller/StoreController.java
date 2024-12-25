package com.goldonbuy.goldonbackend.catalogContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("{api.prefix}/store")
public class StoreController {

    private final StoreService storeService;
}
