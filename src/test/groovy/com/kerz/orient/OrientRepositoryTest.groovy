package com.kerz.orient

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.*

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes = [OrientRepositoryConfiguration, OrientRepository])
@PropertySource('classpath:application.properties')
@EnableConfigurationProperties([OrientProperties])
class OrientRepositoryTest {

  String[] vertexTypes = [Widget.simpleName, Whatsit.simpleName]

  @Autowired
  OrientRepository orientRepository

  @Before
  void setUp() throws Exception {
    vertexTypes.each {
      orientRepository.createVertexType(it)
    }
  }

  @After
  void tearDown() throws Exception {
    vertexTypes.each {
      orientRepository.dropVertexType(it)
    }
  }

  @Test
  void shouldWork() throws Exception {
    def w = new Widget(name: 'w123', price: 10.99, whatsit: new Whatsit(name: 'w456'))
    orientRepository.saveElement(w)
    def w2 = orientRepository.g.v(w.id)
    assertNotNull('entity required', w2)
    assertEquals('name should be same', w.name, w2.name)
    assertEquals('price should be same', w.price, w2.price, 0.0f)
    assertNull(w2?.whatsit)
  }
}
