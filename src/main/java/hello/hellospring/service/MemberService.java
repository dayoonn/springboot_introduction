package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }


    /**
     * 회원가입
     */
    public long join(Member member) {
        //같은 이름이 있는지 중복 회원 확인
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

        private void validateDuplicateMember(Member member){
            memberRepository.findByName(member.getName()) //이름 조회
                    .ifPresent(m -> { //만약 값이 있으면 실행 (옵셔널이기 때문에 해당 메소드 사용 가능 아니면 if null )
                        throw new IllegalStateException("이미 존재하는 회원입니다."); //예외 발생
                    });
        }


    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 아이디 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
