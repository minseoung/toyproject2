package toy.toyproject2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.toyproject2.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByLoginid(String loginid);
    public Member findByNickname(String nickname);
}
