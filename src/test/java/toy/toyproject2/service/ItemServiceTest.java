package toy.toyproject2.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.ItemBookAddRequest;
import toy.toyproject2.controller.dto.ItemBookEditRequest;
import toy.toyproject2.controller.dto.MemberAddRequest;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.entity.item.Item;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ItemServiceTest {
    @Autowired ItemService itemService;
    @Autowired EntityManager em;
    @Autowired MemberService memberService;

    @Test
    @DisplayName("상품 등록")
    void addItem() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();
        //when
        ItemBookAddRequest addRequest = new ItemBookAddRequest("이것이 JAVA다", 12000, 10, "신용권", "979-11");
        Long savedItemId = itemService.addItem(addRequest, savedMemberId);
        em.flush();
        em.clear();
        //then
        Item findItem = itemService.findOne(savedItemId).get();
        assertThat(findItem.getId()).isEqualTo(savedItemId);
        assertThat(findItem.getName()).isEqualTo("이것이 JAVA다");
    }

    @Test
    @DisplayName("아이템 수정")
    void editItem() {
        //given
        MemberAddRequest request = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1111"));
        Long savedMemberId = memberService.join(request);
        em.flush();
        em.clear();

        ItemBookAddRequest addRequest = new ItemBookAddRequest("이것이 JAVA다", 12000, 10, "신용권", "979-11");
        Long savedItemId = itemService.addItem(addRequest, savedMemberId);
        em.flush();
        em.clear();
        //when
        ItemBookEditRequest editRequest = new ItemBookEditRequest("저것이 JAVA다", 22000, 20, "임경균", "979-11");
        itemService.editItem(editRequest, savedItemId);
        em.flush();
        em.clear();
        //then
        Item findItem = itemService.findOne(savedItemId).get();
        assertThat(findItem.getId()).isEqualTo(savedItemId);
        assertThat(findItem.getName()).isEqualTo("저것이 JAVA다");
    }

    @Test
    @DisplayName("아이템 리스트 조회")
    void itemList() {
        //given
        MemberAddRequest request1 = new MemberAddRequest("test1", "1234", "test1", "test1", 10, new Address("서울", "마포대로", "1234"));
        MemberAddRequest request2 = new MemberAddRequest("test2", "1234", "test2", "test2", 20, new Address("서울", "마포대로", "2222"));
        Long savedMemberId1 = memberService.join(request1);
        Long savedMemberId2 = memberService.join(request2);
        em.flush();
        em.clear();

        ItemBookAddRequest addRequest1 = new ItemBookAddRequest("이것이 JAVA다", 12000, 10, "신용권", "979-11");
        ItemBookAddRequest addRequest2 = new ItemBookAddRequest("이것이 JAVA다", 22000, 20, "김용권", "979-21");
        ItemBookAddRequest addRequest3 = new ItemBookAddRequest("이것이 JAVA다", 32000, 30, "박용권", "979-31");
        ItemBookAddRequest addRequest4 = new ItemBookAddRequest("이것이 JAVA다", 42000, 40, "진용권", "979-41");
        Long savedItemId1 = itemService.addItem(addRequest1, savedMemberId1);
        Long savedItemId2 = itemService.addItem(addRequest2, savedMemberId1);
        Long savedItemId3 = itemService.addItem(addRequest3, savedMemberId2);
        Long savedItemId4 = itemService.addItem(addRequest4, savedMemberId2);
        em.flush();
        em.clear();
        //when
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "price"));
        Page<Item> page = itemService.findItems(pageRequest);
        List<Item> items = page.getContent();
        for (Item item : items) {
            System.out.println("item = " + item);
        }
    }
}