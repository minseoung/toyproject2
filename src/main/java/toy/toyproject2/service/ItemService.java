package toy.toyproject2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.toyproject2.controller.dto.ItemBookAddRequest;
import toy.toyproject2.controller.dto.ItemBookEditRequest;
import toy.toyproject2.controller.dto.ItemListRequest;
import toy.toyproject2.domain.entity.Member;
import toy.toyproject2.domain.entity.item.Book;
import toy.toyproject2.domain.entity.item.Item;
import toy.toyproject2.domain.repository.ItemRepository;
import toy.toyproject2.domain.repository.MemberRepository;
import toy.toyproject2.exception.NotExistItemException;
import toy.toyproject2.exception.NotExistMemberException;

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
            throw new NotExistMemberException("존재하지 않는 회원입니다.");
        }
    }

    public void editItem(ItemBookEditRequest editRequest, Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.edit(editRequest.getName(), editRequest.getPrice(), editRequest.getStockQuantity(), editRequest.getAuthor(), editRequest.getIsbn());
        } else {
            throw new NotExistItemException("해당 상품을 찾을수 없습니다.");
        }
    }

    public void editItemV2(ItemBookEditRequest editRequest, Long itemId, Long memberId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (!item.getMember().getId().equals(memberId)) {
                throw new RuntimeException("본인이 등록한 상품만 수정 가능합니다.");
            }
            item.edit(editRequest.getName(), editRequest.getPrice(), editRequest.getStockQuantity(), editRequest.getAuthor(), editRequest.getIsbn());
        } else {
            throw new NotExistItemException("해당 상품을 찾을수 없습니다.");
        }
    }

    public Book findItem(Long id) {
        Optional<Item> optionalItem = itemRepository.findItemById(id);
        if (optionalItem.isPresent()) {
            Book book = (Book) optionalItem.get();
            return book;
        } else {
            throw new NotExistItemException("해당 상품을 찾을수 없습니다.");
        }
    }

    public Optional<Item> findOne(Long id) {
        return itemRepository.findById(id);
    }

    public Page<Item> findItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Page<Item> findItemList(ItemListRequest listRequest) {
        PageRequest pageRequest = PageRequest.of(listRequest.getPage(), listRequest.getSize());
        return itemRepository.findItems(pageRequest);
    }
}
