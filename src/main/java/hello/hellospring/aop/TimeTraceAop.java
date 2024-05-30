package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 이 클래스가 AOP 기능을 제공하는 Aspect임을 나타내는 애노테이션
@Component //일반적인 빈 선언 . Config파일에 빈을 등록했으니 생략 가능
public class TimeTraceAop {
    // Around 어드바이스: 대상 메서드 실행 전후에 실행되는 어드바이스
    //@Around("execution(* hello.hellospring..*(..))")
     @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")// SpringConfig 클래스를 제외한 모든 메서드에 적용되는 어드바이스
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();// 현재 시간을 기록하여 시작 시간을 저장
        System.out.println("START: " + joinPoint.toString());// 메서드 호출이 시작되었음을 로그로 출력
        try {
            return joinPoint.proceed();// 대상 메서드를 실행하고, 그 결과를 반환
        } finally {
            long finish = System.currentTimeMillis();// 현재 시간을 기록하여 종료 시간을 저장
            long timeMs = finish - start;// 실행 시간을 계산
            System.out.println("END: "+joinPoint.toShortString()+" " +timeMs + "ms");
        }
    }
}
