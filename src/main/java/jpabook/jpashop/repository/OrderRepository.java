package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
         private final EntityManager em;

         //--order생성--//
         public void save(Order order){
             em.persist(order);
         }

         //--order단건조회--//
        public Order findOne(Long id){
            return em.find(Order.class, id);
        }

        //--order 목록 조회--//
        public List<Order> findAll(){
            return em.createQuery("select o from Order o",Order.class)
            .getResultList();
        }

        //검색용 아직은 구현 X
        //public List<Order> findAll(OrderSearch orderSearch){}

    }


