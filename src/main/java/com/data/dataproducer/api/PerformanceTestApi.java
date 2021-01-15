package com.data.dataproducer.api;

import com.data.dataproducer.config.DataCacheConfig;
import com.data.dataproducer.entity.AUser;
import com.data.dataproducer.service.IAUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author danny
 * @date 2020/9/23 8:55 PM
 */
@RestController
@RequestMapping("/api/performance")
public class PerformanceTestApi {

    @Autowired
    private IAUserService iaUserService;

    @Autowired
    private DataCacheConfig dataCacheConfig;

    @GetMapping("/testNull")
    public void testNull() {

    }

    @GetMapping("/testNullParam")
    public String testNullParam() {
        return new Date().toString();
    }

    @GetMapping("/testOne")
    public String testOne(String code) {
        return new Date().toString() + code.hashCode();
    }

    @GetMapping("/testOneNullRet")
    public void testNullRet(String code) {

    }

    @GetMapping("/testOpeDb")
    public Object testOperatDb(@RequestParam("opDbTimes") int opDbTimes) {
        List<AUser> users = new ArrayList<>();
        for (int i = 0; i < opDbTimes; i++) {
            long maxUserId = dataCacheConfig.getMaxUserId();
            users.add(iaUserService.getById(maxUserId - i));
        }
        return users;
    }


}
