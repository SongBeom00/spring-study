package hello.order.v1;

import hello.order.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class OrderServiceV1 implements OrderService {

    @Autowired MeterRegistry registry;

    public OrderServiceV1(MeterRegistry registry){
        this.registry = registry;
    }

    private AtomicInteger stock = new AtomicInteger(100);

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();
        Counter.builder("my.order")
                .tag("class", this.getClass().getName())
                //tag 를 통해 주문과 취소를 구분한다.
                .tag("method", "order")
                .description("order")
                .register(registry).increment();
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();
        Counter.builder("my.order")
                .tag("class", this.getClass().getName())
                //tag 를 통해 주문과 취소를 구분한다.
                .tag("method", "cancel")
                .description("order")
                .register(registry).increment();

    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
