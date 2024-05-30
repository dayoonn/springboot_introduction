package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; //엔티티 매니저 (데이터베이스 통신, 엔티티 생명주기 관리)

    public JpaMemberRepository(EntityManager em) {
        this.em = em; // 엔티티 매니저 주입
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 멤버를 영속화하고 데이터베이스에 저장
        return member; // 저장된 멤버 반환
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // 주어진 ID로 멤버를 조회
        return Optional.ofNullable(member); // 조회된 멤버를 Optional로 감싸서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 이름을 사용하여 멤버를 조회하는 JPQL 쿼리 실행
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)// 이름 매개변수 설정
                .getResultList(); // 결과 리스트 반환
        return result.stream().findAny(); // 조회된 결과 중 임의의 결과를 Optional로 감싸서 반환
    }

    @Override
    public List<Member> findAll() {
        // 모든 멤버를 조회하는 JPQL 쿼리 실행
        return em.createQuery("select m from Member as m", Member.class).getResultList();
        // 결과 리스트 반환
    }
}
