package com.smalldogg.study.itemservice.web.basic;

import com.smalldogg.study.itemservice.domain.item.Item;
import com.smalldogg.study.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam Integer price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);

        model.addAttribute("item", item);

        return "/basic/item";
    }

    //    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        //@ModelAttirbute는 모델 객체를 만들어줌
        itemRepository.save(item);
//        model.addAttribute("item",item); //Attribute도 자동으로 추가해줌. ModelAttribute에 지정한 이름으로 AttributeName이 쓰임

        return "/basic/item";
    }

    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        //@ModelAttribute의 이름이 없을 경우, 모델 객체의 클래스명 맨 앞글자를 소문자로 바꿔서 자동으로 이름을 작성함.
        itemRepository.save(item);
//        model.addAttribute("item",item);

        return "/basic/item";
    }

    //    @PostMapping("/add")
    public String addItemV4(Item item) {
        //@ModelAttribute의 이름이 없을 경우, 모델 객체의 클래스명 맨 앞글자를 소문자로 바꿔서 자동으로 이름을 작성함.
        itemRepository.save(item);
//        model.addAttribute("item",item);

        return "/basic/item";
    }

    /**
     * PRG(POST/Redirect/GET)
     * @param item
     * @return
     */
//    @PostMapping("/add")
    public String addItemV5(Item item) {
        //@ModelAttribute의 이름이 없을 경우, 모델 객체의 클래스명 맨 앞글자를 소문자로 바꿔서 자동으로 이름을 작성함.
        itemRepository.save(item);
//        model.addAttribute("item",item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
        //http://localhost:8080/basic/items/3?status=true
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
