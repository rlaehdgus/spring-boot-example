package spring.boot.project.member.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import spring.boot.project.config.JwtTokenProvider;
import spring.boot.project.member.domain.MemberVo;
import spring.boot.project.member.service.MemberService;
import spring.boot.project.model.response.CommonResult;
import spring.boot.project.model.response.ResponseService;
import spring.boot.project.model.response.SingleResult;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class MemberController {
	
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider; // jwt 토큰 생성
    private final ResponseService responseService; // API 요청 결과에 대한 code, message
	
	@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = MemberVo.class))})
	@PostMapping(value = "/signin")
	public SingleResult<String> signin(@RequestBody MemberVo memberVo) {
		MemberVo user = memberService.findByUserId(memberVo);
        
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserId()), user.getRoles()));
	}

	@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = MemberVo.class))})
	@PostMapping(value = "/signup")
	public CommonResult signup(@RequestParam String id, @RequestParam String password, @RequestParam String name) {
		
//		memberService.save(MemberVo.builder()
//                .userId(id)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .roles(Collections.singletonList("ROLE_USER"))
//                .build());
        return responseService.getSuccessResult();
	}
}
