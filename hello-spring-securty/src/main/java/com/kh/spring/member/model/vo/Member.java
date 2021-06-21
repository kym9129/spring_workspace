package com.kh.spring.member.model.vo;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member implements UserDetails{
	/**
	 * Spring Security가 필요로 하는 컬럼
	 * -username : id
	 * -password : pw
	 * -authorities : 복수개의 권한관리
	 * -enabled : 활성화 여부
	 */

	private String id;			// username
	private String password;	// password
	private String name;
	private String gender;
	private Date birthday;
	private String email;
	private String phone;
	private String address;
	private String[] hobby;
	private Date enrollDate;
	
	// 복수개의 권한을 관리
	// 문자열data("ROLE_USER", "ROLE_ADMIN")를 처리할 수 있는 GrantedAuthority의 하위 클래스
	private List<SimpleGrantedAuthority> authorities; //authorities
	
	private boolean enabled;	// 활성화 여부
	
	/**
	 * 막간 제네릭 문법
	 * Collection : List/Set의 부모 인터페이스
	 * 
	 * Collection<? extends GrantedAuthority> 
	 *   : GrantedAuthority를 상속하는 무언가 
	 *     -> GrantedAuthority의 자식타입은 ok라는 뜻 (제네릭 상한선)
	 *   ex. <? super Integer> -> Integer의 부모타입이면 ok (제네릭 하한선)
	 * Collection<GrantedAuthority>
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public boolean isAccountNonExpired() { 
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

}
