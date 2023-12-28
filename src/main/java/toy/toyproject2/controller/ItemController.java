package toy.toyproject2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.toyproject2.controller.argumentResolver.Login;
import toy.toyproject2.controller.dto.*;
import toy.toyproject2.domain.entity.item.Book;
import toy.toyproject2.domain.entity.item.Item;
import toy.toyproject2.service.ItemService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/item/add")
    public Result<ItemBookAddResponse> addItem(@RequestBody @Validated ItemBookAddRequest addRequest, @Login Long loginMemberId) {
        Long addItemId = itemService.addItem(addRequest, loginMemberId);
        Book book = itemService.findItem(addItemId);
        return new Result<>(new ItemBookAddResponse(book));
    }

    @PutMapping("item/{itemId}/edit")
    public Result<ItemBookAddResponse> editItem(@RequestBody @Validated ItemBookEditRequest editRequest, @PathVariable(name = "itemId") Long itemId, @Login Long loginMemberId) {
        itemService.editItemV2(editRequest, itemId, loginMemberId);
        Book book = itemService.findItem(itemId);
        return new Result<>(new ItemBookAddResponse(book));
    }

    @GetMapping("item/{itemId}")
    public Result<ItemBookAddResponse> item(@PathVariable(name = "itemId") Long itemId) {
        Book book = itemService.findItem(itemId);
        return new Result<>(new ItemBookAddResponse(book));
    }

    @GetMapping("item/list")
    public Result<Page<ItemBookAddResponse>> list(@RequestBody @Validated ItemListRequest listRequest) {
        Page<Item> itemPage = itemService.findItemList(listRequest);
        Page<ItemBookAddResponse> result = itemPage.map(item -> new ItemBookAddResponse((Book) item));
        return new Result<>(result);
    }
}
