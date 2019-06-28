package com.data.dataproducer.controller;

import com.data.dataproducer.config.DataCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author danny
 * @date 2019/6/28 10:40 AM
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private DataCacheConfig dataCacheConfig;

    @RequestMapping("/cache")
    public Object dataCacheConfig () {
        return dataCacheConfig.toString();
    }


}
