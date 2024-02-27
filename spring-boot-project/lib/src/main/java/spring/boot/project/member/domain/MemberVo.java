package spring.boot.project.member.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.boot.project.common.Aes256Converter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder // builder를 사용할 수 있게 한다.
@Entity // jpa entity 임을 알린다.
@Getter //user 필드 값의 getter를 자동생성한다.
@NoArgsConstructor // 인자 없는 생성자를 자동으로 생성한다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성한다.
@Table(name = "member")
public class MemberVo implements UserDetails {
	
	@Id
	@Column(name = "userId", nullable = false)
	@Schema(description = "User Id", example = "hong12")
	private String userId;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Convert(converter = Aes256Converter.class)
	@Column(name = "password", nullable = false)
	@Schema(description = "User Password", example = "123456")
	private String password;
	
	@Column(name = "name", nullable = false)
	@Schema(description = "User Name", example = "홍길동")
	private String name;
	
	@Convert(converter = Aes256Converter.class)
	@Column(name = "regNo", nullable = false)
	@Schema(description = "User Registration Number", example = "860824-1655068")
	private String regNo;
	
	@Column(name = "regDt", nullable = false)
	@Schema(description = "User Register Date", example = "2024/01/01 00:00:00")
	private String regDt;
	
	@Column(name = "regUserId", nullable = false)
	@Schema(description = "User Register Id", example = "hong12")
	private String regUserId;
	
	@Column(name = "updDt", nullable = true)
	@Schema(description = "User Update Date", example = "2024/01/01 00:00:00")
	private String updDt;
	
	@Column(name = "updUserId", nullable = true)
	@Schema(description = "User Update Id", example = "hong12")
	private String updUserId;
	
	
	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>(); // 회원이 가지고 있는 권한 정보들
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.userId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
	
}
