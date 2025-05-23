package hello.advanced.app.v5;

import com.fasterxml.jackson.core.TreeCodec;
import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5  {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate traceTemplate;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace, TreeCodec treeCodec) {
        this.orderRepository = orderRepository;
        this.traceTemplate = new TraceTemplate(trace);
    }

    public void orderItem(String itemId){
        traceTemplate.execute("OrderService.orderItem()", () -> {
            orderRepository.save(itemId); // 핵심 기능
            return null;
        });

    }

}
