package com.goldonbuy.goldonbackend.catalogContext.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoldenConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
