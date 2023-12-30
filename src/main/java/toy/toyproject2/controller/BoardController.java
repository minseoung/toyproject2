package toy.toyproject2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.toyproject2.controller.argumentResolver.Login;
import toy.toyproject2.controller.dto.*;
import toy.toyproject2.domain.entity.Board;
import toy.toyproject2.service.BoardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board/post")
    public Result<Long> post(@RequestBody @Validated BoardAddRequest addRequest, @Login Long loginMemberId) {
        Long savedBoardId = boardService.post(addRequest, loginMemberId);
        return new Result<>(savedBoardId);
    }

    @PutMapping("/board/{boardId}/edit")
    public Result<String> edit(@RequestBody @Validated BoardEditRequest editRequest, @Login Long loginMemberId,
                               @PathVariable(name = "boardId") Long boardId) {
        boardService.editV2(editRequest, boardId, loginMemberId);
        return new Result<>("수정되었습니다.");
    }

    @GetMapping("/board/{boardId}")
    public Result<BoardResponse> board(@PathVariable(name = "boardId") Long boardId) {
        Board findBoard = boardService.findBoard(boardId);
        return new Result<>(new BoardResponse(findBoard));
    }

    @GetMapping("/board/list")
    public Result<List<BoardResponse>> list() {
        List<Board> boardList = boardService.findBoardList();
        List<BoardResponse> collect = boardList.stream().map(board -> new BoardResponse(board)).collect(Collectors.toList());
        return new Result<>(collect);
    }

    @GetMapping("/board/listV2")
    public Result<List<BoardResponse>> listV2(@RequestBody @Validated BoardSearchCond cond,
                                              @RequestParam(name = "page") Integer page,
                                              @RequestParam(name = "size") Integer size) {
        List<Board> boardList = boardService.findBoardListV2(cond, page, size);
        List<BoardResponse> collect = boardList.stream().map(board -> new BoardResponse(board)).collect(Collectors.toList());
        return new Result<>(collect);
    }
}
