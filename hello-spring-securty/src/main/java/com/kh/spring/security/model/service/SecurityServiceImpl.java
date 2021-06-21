package com.kh.spring.security.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.spring.security.model.dao.SecurityDao;

import lombok.extern.slf4j.Slf4j;

@Service("securityService") //빈 id 지정
@Slf4j
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	private SecurityDao securityDao;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		//비번 맞는지 아닌지 직접 검사 안해줘도 됨
		UserDetails member = securityDao.loadUserByUsername(id);
		log.debug("member = {}", member);
		if(member == null)
			throw new UsernameNotFoundException(id);
		
		return member;
	}

}
