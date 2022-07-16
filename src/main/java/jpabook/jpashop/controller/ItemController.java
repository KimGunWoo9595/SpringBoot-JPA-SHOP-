package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){// 상품 등록 페이지로 이동하는 컨트롤러
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){ // 상품 등록을 디비에 넣어주는 컨트롤러
        Book book = new Book();
        //아래는 폼객체에 담긴 값들을 엔티티에 넣어주는 작업!
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){ // 디비에서 상품 목록들을 조회해서 화면에 뿌려준 페이지로 이동하는 컨트롤러
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")        //@PathVariable : url로 데이터 받아오는 것?
    public String updateItemForm(@PathVariable Long itemId, Model model){
        Book item = (Book)itemService.findOne(itemId); // 수정 버튼을 누르면 해당 아이템
        //위의 객체는 id로 찾아온 엔티티 객체
        //또 BookForm로 넘기려고
        BookForm form = new BookForm();

        form.setId(item.getId()); // 아이디가 셋팅이 되어있다. 이말은 jpa에 한번 들어갔다가 나온애라는 것이다. 식별자가 디비에있으면 준영속 엔티티이다
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";

    }

    @PostMapping("items/{itemId}/edit")
    public String update(@PathVariable Long itemId,@ModelAttribute("form") BookForm form){
        //변경감지(dirty checking)
        itemService.updateItem(itemId,form.getName(),form.getPrice(),form.getStockQuantity());
        return "redirect:/items";


    //merge사용시
      /*  Book book = new Book();
        book.setId(form.getId());// 아이디(식별자)가 셋팅이 되어있다. 이말은 jpa에 한번 들어갔다가 나온애라는 것이다. 식별자가 디비에있으면 준영속 엔티티이다
        book.setName(form.getName());
        book.addStock(form.getStockQuantity());
        book.setPrice(form.getPrice());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());*/

        //itemService.saveItem(book);

         /*준영속 엔티티?
        영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다.
        (여기서는 itemService.saveItem(book) 에서 수정을 시도하는 Book 객체다. Book 객체는 이미 DB
        에 한번 저장되어서 식별자가 존재한다. 이렇게 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준
        영속 엔티티로 볼 수 있다.*/
    }
}
