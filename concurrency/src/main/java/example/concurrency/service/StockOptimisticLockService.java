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
            maxAttempts = 20,
            backoff = @Backoff(delay = 100)
    )
    @Transactional
    public void decrease(Long id, Long quantity){
        Stock stock = stockRepository.findByWithOptimisticLock(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
