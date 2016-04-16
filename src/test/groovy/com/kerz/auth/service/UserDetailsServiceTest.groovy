package com.kerz.auth.service

import com.kerz.auth.dao.UserRepository
import com.kerz.auth.dao.UserRepositoryConfiguration
import com.kerz.auth.dao.UserRepositoryImpl
import com.kerz.auth.dao.UserRepositoryInitializer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [
  UserRepositoryConfiguration,
  UserRepositoryImpl,
  UserRepositoryInitializer,
  UserDetailsServiceImpl
])
class UserDetailsServiceTest {

  //Logger log = LoggerFactory.getLogger(UserRepositoryTest)

  @Autowired
  UserDetailsService userDetailsService

  @Autowired
  UserRepositoryInitializer initializer

  @Before
  void setUp() throws Exception {
    initializer.tearDown()
    initializer.setUp()
  }

  @After
  void tearDown() throws Exception {
    initializer.tearDown()
  }

  @Test
  void shouldWork() throws Exception {
    def name = 'st-send-1'
    UserDetails user = userDetailsService.loadUserByUsername(name)
    assertNotNull('user required', user)
    assertEquals(name, user.username)
    assertEquals(6, user.authorities.size())
    String priv = UserRepositoryInitializer.APP_ST
    assertTrue("authorities should contain $priv", user.authorities.contains(new SimpleGrantedAuthority(priv)))
  }

  @Test(expected = UsernameNotFoundException)
  void shouldThrow() throws Exception {
    UserDetails user = userDetailsService.loadUserByUsername('does-not-exist')
  }
}
