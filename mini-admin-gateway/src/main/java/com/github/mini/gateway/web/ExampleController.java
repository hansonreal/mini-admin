package com.github.mini.gateway.web;

import com.github.mini.common.ann.Anonymous;
import com.github.mini.common.result.BaseResult;
import com.github.mini.common.result.DataResult;
import com.github.mini.common.util.SynchronizedByKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 并发编程测试
 */
@Slf4j
@RestController
@RequestMapping("/example")
public class ExampleController {

    @Autowired
    private SynchronizedByKeyService synchronizedByKeyService;

    @PostMapping("/order/{orderNo}")
    @Anonymous
    public BaseResult createOrder(@PathVariable("orderNo") String orderNo) {
        log.info("[{}] 开始", orderNo);
        synchronizedByKeyService.exec(orderNo, () -> {
            //service();
        });
        log.info("[{}] 结束", orderNo);
        return DataResult.ok("");
    }

}
