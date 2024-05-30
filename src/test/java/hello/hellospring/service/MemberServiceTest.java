package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository; //clear()을 사용하기 위해 선언

    @BeforeEach //각 메소드 테스트 전 실행
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository(); //clear()을 사용하기 위해 선언
        memberService=new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member=new Member();
        member.setName("hello");
        
        //when
        Long saveId= memberService.join(member);
        
        //then 
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring"); //이름 중복

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //제공된 실행 파일의 실행이 예상 유형의 예외를 발생시키는지 확인하고 예외를 반환. member2를 join 했을 때 예외를 기대
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //예외 메세지 비교

/*
        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
           assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/


    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}