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
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.ItemBookAddRequest;
import toy.toyproject2.controller.dto.MemberAddRequest;
import toy.toyproject2.controller.dto.OrderAddRequest;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.entity.Order;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;

    @Test
    @DisplayName("주문 생성")
    void order() {
        //given
        MemberAddRequest request1 = new MemberAddRequest("test1", "1234", "거성명수", "박명수", 10, new Address("서울", "마포대로", "1234"));
        MemberAddRequest request2 = new MemberAddRequest("test2", "1234", "메뚜기재석", "유재석", 20, new Address("부산", "서면대로", "2222"));
        MemberAddRequest request3 = new MemberAddRequest("test3", "1234", "자바용권", "신용권", 30, new Address("인천", "강화대로", "3456"));
        Long savedMemberId1 = memberService.join(request1);
        Long savedMemberId2 = memberService.join(request2);
        Long savedMemberId3 = memberService.join(request3);
        em.flush();
        em.clear();

        ItemBookAddRequest addRequest1 = new ItemBookAddRequest("이것이 JAVA다", 12000, 10, "신용권", "979-11");
        ItemBookAddRequest addRequest2 = new ItemBookAddRequest("이것이 SPRING이다", 22000, 20, "김용권", "979-21");
        ItemBookAddRequest addRequest3 = new ItemBookAddRequest("이것이 PYTHON이다", 32000, 30, "박용권", "979-31");
        ItemBookAddRequest addRequest4 = new ItemBookAddRequest("이것이 SQL이다", 42000, 40, "진용권", "979-41");
        Long savedItemId1 = itemService.addItem(addRequest1, savedMemberId1);
        Long savedItemId2 = itemService.addItem(addRequest2, savedMemberId1);
        Long savedItemId3 = itemService.addItem(addRequest3, savedMemberId2);
        Long savedItemId4 = itemService.addItem(addRequest4, savedMemberId2);
        em.flush();
        em.clear();
        //when
        //주문 과정에서 배송지 정보 넣고 안넣으면 회원의 기본 배송지로 배송, 수량은 2개
        OrderAddRequest orderAddRequest = new OrderAddRequest(new Address("123", "123", "123"), 3);
        //자바용권께서 메뚜기재석이 등록한 이것이 PYTHON이다를 2개 샀음, 기본배송지로
        Long savedOrderId = orderService.order(orderAddRequest, savedMemberId3, savedItemId3);
        em.flush();
        em.clear();
        //then
        Order findOrder = orderService.findOne(savedOrderId).get();
        Member findMember = memberService.findOne(savedMemberId2).get();
        System.out.println("findOrder = " + findOrder);
        System.out.println("findMember = " + findMember);
        assertThat(findOrder.getId()).isEqualTo(savedOrderId);
    }

    @Test
    @DisplayName("주문 취소")
    void cancel() {
        //given
        MemberAddRequest request1 = new MemberAddRequest("test1", "1234", "거성명수", "박명수", 10, new Address("서울", "마포대로", "1234"));
        MemberAddRequest request3 = new MemberAddRequest("test3", "1234", "자바용권", "신용권", 30, new Address("인천", "강화대로", "3456"));
        Long savedMemberId1 = memberService.join(request1);
        Long savedMemberId3 = memberService.join(request3);
        em.flush();
        em.clear();

        ItemBookAddRequest addRequest1 = new ItemBookAddRequest("이것이 JAVA다", 12000, 10, "신용권", "979-11");
        Long savedItemId1 = itemService.addItem(addRequest1, savedMemberId1);
        em.flush();
        em.clear();

        OrderAddRequest orderAddRequest = new OrderAddRequest(new Address("123", "123", "123"), 3);
        Long savedOrderId = orderService.order(orderAddRequest, savedMemberId3, savedItemId1);
        em.flush();
        em.clear();

        Order order1 = orderService.findOne(savedOrderId).get();
        Member member1 = memberService.findOne(savedMemberId1).get();
        System.out.println("order = " + order1);
        System.out.println("member = " + member1);
        //when
        orderService.cancelOrder(savedOrderId);
        //then
        System.out.println("order = " + order1);
        System.out.println("member = " + member1);
    }

    @Test
    @DisplayName("주문 리스트 조회")
    void orders() {
        //given
        MemberAddRequest request1 = new MemberAddRequest("test1", "1234", "거성명수", "박명수", 10, new Address("서울", "마포대로", "1234"));
        MemberAddRequest request2 = new MemberAddRequest("test2", "1234", "메뚜기재석", "유재석", 20, new Address("부산", "서면대로", "2222"));
        MemberAddRequest request3 = new MemberAddRequest("test3", "1234", "자바용권", "신용권", 30, new Address("인천", "강화대로", "3456"));
        Long savedMemberId1 = memberService.join(request1);
        Long savedMemberId2 = memberService.join(request2);
        Long savedMemberId3 = memberService.join(request3);
        em.flush();
        em.clear();

        ItemBookAddRequest addRequest1 = new ItemBookAddRequest("이것이 JAVA다", 12000, 10, "신용권", "979-11");
        ItemBookAddRequest addRequest2 = new ItemBookAddRequest("이것이 SPRING이다", 22000, 20, "김용권", "979-21");
        ItemBookAddRequest addRequest3 = new ItemBookAddRequest("이것이 PYTHON이다", 32000, 30, "박용권", "979-31");
        ItemBookAddRequest addRequest4 = new ItemBookAddRequest("이것이 SQL이다", 42000, 40, "진용권", "979-41");
        Long savedItemId1 = itemService.addItem(addRequest1, savedMemberId1);
        Long savedItemId2 = itemService.addItem(addRequest2, savedMemberId1);
        Long savedItemId3 = itemService.addItem(addRequest3, savedMemberId2);
        Long savedItemId4 = itemService.addItem(addRequest4, savedMemberId2);
        em.flush();
        em.clear();

        OrderAddRequest orderAddRequest1 = new OrderAddRequest(new Address("123", "123", "123"), 1);
        OrderAddRequest orderAddRequest2 = new OrderAddRequest(null, 2);
        Long savedOrderId1 = orderService.order(orderAddRequest1, savedMemberId3, savedItemId3);
        Long savedOrderId2 = orderService.order(orderAddRequest2, savedMemberId3, savedItemId1);
        em.flush();
        em.clear();
        //"자바용권"이 "메뚜기재석"의 책 1권(32000원)을 사고 "거성명수"의 책 1권(24000원)을 샀다. 남은 돈 44000원
        //then
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Order> page = orderService.findOrders(pageRequest);
        List<Order> orders = page.getContent();
        for (Order order : orders) {
            System.out.println("order = " + order);
        }
        Member member1 = memberService.findOne(savedMemberId1).get();
        Member member2 = memberService.findOne(savedMemberId2).get();
        System.out.println("member1 = " + member1);
        System.out.println("member2 = " + member2);

    }
}