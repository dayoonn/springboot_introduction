package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;// JdbcTemplate 선언

    @Autowired // 자동 주입된 데이터 소스를 사용하여 객체 생성 , 생략 가능(생성자가 하나이기 때문)
    public JdbcTemplateMemberRepository(DataSource dataSource) { //데이터 소스 인젝션 받음

        this.jdbcTemplate = new JdbcTemplate(dataSource);// 주입받은 데이터 소스로 JdbcTemplate 초기화
    }

    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert를 사용하여 INSERT 쿼리 실행 및 자동 생성된 키 반환
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");// 테이블 이름 및 자동 생성된 키 컬럼 설정(DB 컬럼값과 동일해야함)
        Map<String, Object> parameters = new HashMap<>();// 매개변수 맵 생성
        parameters.put("name", member.getName());// 이름 설정
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters)); //INSERT 쿼리를 실행한 후에 자동으로 생성된 키(primary key)를 반환하는 메서드
        member.setId(key.longValue());// 반환된 키를 사용하여 회원 객체에 ID 설정
        return member;// 저장된 회원 객체 반환
    }


    @Override
    public Optional<Member> findById(Long id) {
        // ID를 사용하여 회원 조회 쿼리 실행 및 결과 반환
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(),id);
        return result.stream().findAny();// 조회된 결과 중 임의의 결과 반환
    }

    @Override
    public List<Member> findAll() {
        // 모든 회원 조회 쿼리 실행 및 결과 반환
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }
    @Override
    public Optional<Member> findByName(String name) {
        // 이름을 사용하여 회원 조회 쿼리 실행 및 결과 반환
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    // ResultSet의 row를 매핑하기 위한 RowMapper 구현
    //row 단위로 ResultSet의 row를 매핑하기 위해 JdbcTemplate에서 사용하는 인터페이스
    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> { //람다
            Member member = new Member();// 새로운 회원 객체 생성
            member.setId(rs.getLong("id"));// ResultSet에서 ID 가져와서 회원 객체에 설정
            member.setName(rs.getString("name"));// ResultSet에서 이름 가져와서 회원 객체에 설정
            return member;// 매핑된 회원 객체 반환
        };
    }
}
