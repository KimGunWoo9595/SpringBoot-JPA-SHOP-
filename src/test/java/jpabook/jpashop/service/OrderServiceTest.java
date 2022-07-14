package jpabook.jpashop.service;

import com.sun.xml.bind.v2.schemagen.xmlschema.AttrDecls;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.thymeleaf.standard.expression.OrExpression;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        em.persist(member);
        Item book = createBook();
        em.persist(book);
        //when
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);


        Assertions.assertEquals(OrderStatus.ORDER ,getOrder.getStatus());
        Assertions.assertEquals(1,getOrder.getOrderItems().size());
        Assertions.assertEquals(8,book.getStockQuantity());
    }

    private Item createBook() {
        Item book = new Book();
        book.setName("jpa에 관하여");
        book.setPrice(12300);
        book.setStockQuantity(10);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("김건우");
        member.setAddress(new Address("서울","강가","123-123"));
        return member;
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //givenm
        Member member = createMember();
        Item item = createBook();
        //when
        orderService.order(member.getId(),item.getId(),1);
        //then

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        em.persist(member);
        Item book = createBook();
        em.persist(book);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when

        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.CANCEL,order.getStatus());
    }


}