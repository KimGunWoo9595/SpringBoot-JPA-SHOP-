package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 매핑 전략 첫 단계 부모 클래스에 이것을 해주자(전략을 세우자)
@DiscriminatorColumn(name="dtype") // 부모에는 이것을 자식테이블에는 @DiscriminatoeValue("")이것을 해주자!
@Getter @Setter
public abstract class Item { //추상클래스로 일단 만들겠다 구현체를 갖을거기때문에

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
