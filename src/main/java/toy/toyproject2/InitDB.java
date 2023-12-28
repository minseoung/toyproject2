package toy.toyproject2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toy.toyproject2.controller.dto.ItemBookAddRequest;
import toy.toyproject2.controller.dto.MemberAddRequest;
import toy.toyproject2.domain.entity.Address;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.service.ItemService;
import toy.toyproject2.service.MemberService;

@Component
@RequiredArgsConstructor
public class InitDB {

        private final MemberService memberService;
        private final ItemService itemService;
    @PostConstruct
    public void init() {
        MemberAddRequest request1 = new MemberAddRequest("dbwotjr", "1234", "메뚜재석", "유재석", 53, new Address("서울", "마포대로", "1111"));
        MemberAddRequest request2 = new MemberAddRequest("qkraudtn", "1234", "거성명수", "박명수", 56, new Address("부산", "서면대로", "2222"));
        MemberAddRequest request3 = new MemberAddRequest("wjdwnsgk", "1234", "식신준하", "정준하", 55, new Address("인천", "강화대로", "3333"));
        MemberAddRequest request4 = new MemberAddRequest("wjdgudehs", "1234", "노잼형돈", "정형돈", 47, new Address("서울", "성수대로", "4444"));
        MemberAddRequest request5 = new MemberAddRequest("shghdcjf", "1234", "돌I홍철", "노홍철", 45, new Address("경기", "부천대로", "5555"));
        Long savedMemberId1 = memberService.join(request1);
        Long savedMemberId2 = memberService.join(request2);
        Long savedMemberId3 = memberService.join(request3);
        Long savedMemberId4 = memberService.join(request4);
        Long savedMemberId5 = memberService.join(request5);

        ItemBookAddRequest addRequest1 = new ItemBookAddRequest("이것이 JAVA다", 36000, 10, "신용권", "979-11");
        ItemBookAddRequest addRequest2 = new ItemBookAddRequest("이것이 SPRING이다", 22000, 20, "김용권", "979-21");
        ItemBookAddRequest addRequest3 = new ItemBookAddRequest("이것이 SQL이다", 32000, 10, "박용권", "979-31");
        ItemBookAddRequest addRequest4 = new ItemBookAddRequest("이것이 PYTHON다", 42000, 5, "진용권", "979-41");
        Long savedItemId1 = itemService.addItem(addRequest1, savedMemberId1);
        Long savedItemId2 = itemService.addItem(addRequest2, savedMemberId1);
        Long savedItemId3 = itemService.addItem(addRequest3, savedMemberId1);
        Long savedItemId4 = itemService.addItem(addRequest4, savedMemberId1);
        ItemBookAddRequest addRequest5 = new ItemBookAddRequest("인간실격", 21000, 10, "나카무라", "154-21");
        ItemBookAddRequest addRequest6 = new ItemBookAddRequest("배산임수", 20000, 10, "양반", "154-31");
        ItemBookAddRequest addRequest7 = new ItemBookAddRequest("삼국지", 52000, 5, "여포", "154-41");
        Long savedItemId5 = itemService.addItem(addRequest5, savedMemberId2);
        Long savedItemId6 = itemService.addItem(addRequest6, savedMemberId2);
        Long savedItemId7 = itemService.addItem(addRequest7, savedMemberId2);
        ItemBookAddRequest addRequest8 = new ItemBookAddRequest("롤러코스터", 15000, 10, "김기연", "561-21");
        ItemBookAddRequest addRequest9 = new ItemBookAddRequest("빵집운영법", 17000, 10, "노홍철", "951-31");
        Long savedItemId8 = itemService.addItem(addRequest8, savedMemberId4);
        Long savedItemId9 = itemService.addItem(addRequest9, savedMemberId5);
    }
}
