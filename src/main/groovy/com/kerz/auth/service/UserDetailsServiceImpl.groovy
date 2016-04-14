package com.kerz.auth.service

import com.kerz.auth.dao.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	def userRepository

  @Autowired
	UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		def user = userRepository.findByUsername(username)
		if (user == null) {
			throw new UsernameNotFoundException(String.format('User %s does not exist!', username))
		}
		user
	}
}
