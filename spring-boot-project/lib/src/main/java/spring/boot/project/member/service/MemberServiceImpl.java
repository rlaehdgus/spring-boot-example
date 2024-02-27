package spring.boot.project.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import spring.boot.project.advice.exception.CEmailSigninFailedException;
import spring.boot.project.member.domain.MemberVo;
import spring.boot.project.member.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder; // 비밀번호 암호화

	@Override
	public MemberVo findByUserId(MemberVo memberVo) {
		
		MemberVo user = memberRepository.findByUserId(memberVo.getUserId());
		
		if (!passwordEncoder.matches(memberVo.getPassword(), user.getPassword())) {
            // matches : 평문, 암호문 패스워드 비교 후 boolean 결과 return
            throw new CEmailSigninFailedException();
        }
		
		return user;
	}

}
