package toy.toyproject2.domain.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import toy.toyproject2.controller.dto.BoardSearchCond;
import toy.toyproject2.domain.entity.Board;
import toy.toyproject2.domain.entity.QBoard;
import toy.toyproject2.domain.entity.QMember;

import java.util.List;

import static toy.toyproject2.domain.entity.QBoard.*;
import static toy.toyproject2.domain.entity.QMember.*;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Board> searchBoards(BoardSearchCond cond, Pageable pageable) {
        return queryFactory
                .select(board)
                .from(board)
                .join(board.member, member)
                .fetchJoin()
                .where(titleEq(cond.getTitle()), writerEq(cond.getWriter()))
                .orderBy(pageable.getSort().stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public Predicate titleEq(String titleCond) {
        return StringUtils.hasText(titleCond) ? board.title.contains(titleCond) :null;
    }

    public Predicate writerEq(String writerCond) {
        return StringUtils.hasText(writerCond) ? board.member.nickname.contains(writerCond) : null;
    }
}
