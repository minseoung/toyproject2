package toy.toyproject2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.ItemBookAddRequest;
import toy.toyproject2.controller.dto.ItemBookEditRequest;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.entity.item.Book;
import toy.toyproject2.domain.entity.item.Item;
import toy.toyproject2.domain.repository.ItemRepository;
import toy.toyproject2.domain.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public Long addItem(ItemBookAddRequest addRequest, Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            Book book = new Book(addRequest.getName(), addRequest.getPrice(), addRequest.getStockQuantity(), member, addRequest.getAuthor(), addRequest.getIsbn());
            Book savedItem = itemRepository.save(book);
            return savedItem.getId();
        } else {
            log.info("해당 멤버가 존재하지 않습니다. memberId={}", memberId);
            return null;
        }
    }

    public void editItem(ItemBookEditRequest editRequest, Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.edit(editRequest.getName(), editRequest.getPrice(), editRequest.getStockQuantity(), editRequest.getAuthor(), editRequest.getIsbn());
        }
    }

    public Optional<Item> findOne(Long id) {
        return itemRepository.findById(id);
    }

    public Page<Item> findItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
}
