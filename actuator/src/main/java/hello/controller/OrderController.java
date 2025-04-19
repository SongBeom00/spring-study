package hello.controller;

import hello.order.OrderService;
import hello.order.v0.OrderServiceV0;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;


    @GetMapping("order")
    public String order(){
        log.info("order");
        orderService.order();
        return "order";
    }

    @GetMapping("cancel")
    public String cancel(){
        log.info("cancel");
        orderService.cancel();
        return "cancel";
    }

    @GetMapping("stock")
    public int stock(){
        log.info("stock");
        return orderService.getStock().get();
    }


}
