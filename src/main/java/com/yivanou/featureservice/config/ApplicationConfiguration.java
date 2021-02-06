package com.yivanou.featureservice.config;

import com.yivanou.featureservice.repository.FeaturesDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean(initMethod = "init")
    public FeaturesDataSource getFeaturesDataSource() {
        return new FeaturesDataSource();
    }
}
