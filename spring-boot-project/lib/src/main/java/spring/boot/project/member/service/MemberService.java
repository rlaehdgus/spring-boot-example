package spring.boot.project.member.service;

import spring.boot.project.member.domain.MemberVo;

public interface MemberService {

	MemberVo findByUserId(MemberVo memberVo);

}
