package hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TrafficController {

    @GetMapping
    public String cpu(){
        int value = 0;
        for (int i = 0; i < 10000000L; i++) {
            value += i;
        }
        System.out.println("value = " + value);
        return "ok";
    }

}
