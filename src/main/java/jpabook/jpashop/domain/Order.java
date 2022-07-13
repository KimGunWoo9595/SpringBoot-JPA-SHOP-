package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)//@~~~ToOne모든 연관관계는 지연로딩으로 설정/ 안그러면 쿼리장애 발생
    @JoinColumn(name ="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //원래같은면 persist를 각각해줘야하는데 cascade = CascadeType.ALL를쓰면
    private List<OrderItem> orderItems = new ArrayList<>();   // order만 persist하면 orderItems까지 persist된다!

    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id") // 일대일 매핑에서의 연간관계 주인으로 지정
    private Delivery delivery;


    private LocalDateTime orderData;

    @Enumerated(EnumType.STRING)
    private  OrderStatus status;//주문 상태 [order,cancel]


    //연관관계메소드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
