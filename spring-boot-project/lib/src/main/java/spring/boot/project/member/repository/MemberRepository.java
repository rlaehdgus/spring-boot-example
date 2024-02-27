package spring.boot.project.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.project.member.domain.MemberVo;


public interface MemberRepository extends JpaRepository<MemberVo, String>{

	MemberVo findByUserId(String userId);

}
