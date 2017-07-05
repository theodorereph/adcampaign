package com.treph.compaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Theodore on 7/1/2017.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AdCompaignServer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdCompaignServer.class, args);
    }
}
