package com.my.member_app.repository;

import com.my.member_app.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// 유일하게 얘는 적어도 되고, 안 적어도 돈다,
@Repository
// 테이블과 연력ㄹ할 Entity Class를 지정, 해당 클래스의 ID의 타입을 적느다.
public interface MemberRepository extends JpaRepository<Member, Long> {
}
