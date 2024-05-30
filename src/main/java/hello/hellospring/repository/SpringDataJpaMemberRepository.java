package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>,MemberRepository {

    //자동으로 Name을 인식하여 JPQL select m from Member m where m.name=? 으로 실행
    @Override
    Optional<Member> findByName(String name);
}
