package jpabook.jpashop.domain;

import jakarta.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "DELEVERY_ID")
    private Long id;

    private String city;
    private String street;
    private String zipcode;
//    @Enumerated(EnumType.STRING)
//    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;

}
