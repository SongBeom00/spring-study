package example.concurrency.service;

import example.concurrency.domain.Stock;
import example.concurrency.repository.StockRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {


    @Autowired StockService stockService;
    @Autowired StockPessimisticLockService stockPessimisticLockService;
    @Autowired StockOptimisticLockService stockOptimisticLockService;
    @Autowired StockRepository stockRepository;

    @BeforeEach
    public void before(){
        Stock stock = new Stock(100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after(){
        stockRepository.deleteAll();
    }

    @Test
    void 재고생성() throws Exception {
        Stock stock = new Stock(100L);
        stockRepository.saveAndFlush(stock);
    }

    @Test
    void 재고감소() throws Exception {
        stockService.decrease(1L,1L);
        Stock stock = stockRepository.findById(1L).orElseThrow();
        Assertions.assertThat(stock.getQuantity()).isEqualTo(99L);
    }

    @Test
    void 동시_100개_요청() throws Exception {
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(()->{
                try{
                    stockOptimisticLockService.decrease(1L, 1L);
                }finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(0L, stock.getQuantity());

    }

}