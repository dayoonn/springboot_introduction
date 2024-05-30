package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence=0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence); //회원번호 증가
        store.put(member.getId(),member); //회원 목록에 추가
        return member;

    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //아아디로 멤버 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) //member의 getName이 파라미터로 넘어온 name 과 같은지 비교
                .findAny(); //하나라도 찾으면 반 . 없으면 optional에 null이 포함돼서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store의 Member 반환
    }

    public void clearStore(){
        store.clear(); //스토어를 비움
    }
}
