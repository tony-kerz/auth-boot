package com.kerz.auth.dao

import com.kerz.orient.OrientRepositoryConfiguration
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [OrientRepositoryConfiguration, UserRepositoryImpl, UserRepositoryInitializer])
class UserRepositoryTest {

  //Logger log = LoggerFactory.getLogger(UserRepositoryTest)

  @Autowired
  UserRepository userRepository

  @Autowired
  UserRepositoryInitializer initializer

  @Before
  void setUp() throws Exception {
    initializer.setUp()
  }

  @After
  void tearDown() throws Exception {
    initializer.tearDown()
  }

  @Test
  void shouldWork() throws Exception {
    def name = 'st-send-1'
    UserDetails user = userRepository.findByUsername(name)
    assertNotNull('user required', user)
    assertEquals(name, user.username)
    assertEquals(6, user.authorities.size())
    String priv = UserRepositoryInitializer.APP_ST
    assertTrue("authorities should contain $priv", user.authorities.contains(new SimpleGrantedAuthority(priv)))
  }

  @Test
  void shouldReturnNull() throws Exception {
    UserDetails user = userRepository.findByUsername('does-not-exist')
    assertNull(user)
  }
}
