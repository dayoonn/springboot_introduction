package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository(); //인터페이스 말고 구현체 클래스로 임시 변경

    @AfterEach //하나의 메서드가 끝날 때 마다 실행됨
    public void afterEach(){
        repository.clearStore();
    }

    @Test //어노테이션 필수
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result=repository.findById(member.getId()).get(); //Optional에서 값을 꺼낼 때 get() 사용
        Assertions.assertThat(member).isEqualTo(result);
        //assertThat(테스트 타켓).메소드1().메소드2().메소드3();
        // assertThat(actual).isEqualTo(expected): actual(실제값)이 expected(기대값)과 내용
    }

    @Test
    public void findByName(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result=repository.findByName("spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        //비교를 위해 두개의 멤버 생성
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
        //멤버 갯수 비교

    }

}
