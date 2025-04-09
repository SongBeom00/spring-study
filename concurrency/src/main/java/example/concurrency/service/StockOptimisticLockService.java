package example.concurrency.service;

import example.concurrency.domain.Stock;
import example.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockOptimisticLockService {

    private final StockRepository stockRepository;

    @Retryable(
            value = OptimisticLockingFailureException.class,
            maxAttempts = 15, // 100개 동시에 요청 시 15초 설정 해야 동작됨.
            backoff = @Backoff(delay = 100)
    )
    @Transactional
    public void decrease(Long id, Long quantity){
        Stock stock = stockRepository.findByWithOptimisticLock(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
