package gameserver.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步方法调用
 *
 * @author caoxin
 */
@Component
public class AsyncHandlerService {

    public void test() {
        async_test();
    }

    @Async
    public void async_test() {
    }
}