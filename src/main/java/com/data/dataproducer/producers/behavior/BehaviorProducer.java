package com.data.dataproducer.producers.behavior;

import com.data.dataproducer.entity.AWebPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 行为数据
 * @author danny
 * @date 2019/6/28 8:06 PM
 */
@Slf4j
@Component
public class BehaviorProducer {

    @Autowired
    private Map<Integer, AWebPage> webPages;

    /**
     * 模拟PC端埋点采集行为数据
     */
    @Scheduled(fixedRate = 10)
    public void pcBehavior () {

    }


}
