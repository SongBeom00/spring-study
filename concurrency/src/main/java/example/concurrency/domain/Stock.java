package example.concurrency.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;

    @Version
    private Long version;

    public Stock(Long quantity) {
        this.quantity = quantity;
    }

    public void decrease(Long quantity){
        if(this.quantity < quantity){
            throw new RuntimeException("재고 부족");
        }
        this.quantity -= quantity;
    }



}
