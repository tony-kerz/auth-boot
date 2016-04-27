package com.kerz.auth.dao

import com.kerz.auth.domain.User
import com.kerz.auth.domain.Privilege
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

import javax.annotation.Resource

import static com.kerz.orient.OrientRepository.asClass

@Component
class UserRepositoryInitializer {
  final static String APP_ST = 'app:st'
  final static String APP_ST_OUT = "$APP_ST:out"
  final static String APP_ST_IN = "$APP_ST:in"
  final static String APP_ST_CANCEL = "$APP_ST:cancel"
  final static String APP_ST_ASSIGN = "$APP_ST:assign"
  final static String APP_ST_NOTIFY = "$APP_ST:notify"
  final static String APP_ST_NET_CONFIG = "$APP_ST:net-cfg"
  final static String APP_ST_NOTIFY_CONFIG = "$APP_ST:notify-cfg"
  private final static String PATIENT = 'patient'
  final static String PATIENT_CREATE = "$PATIENT:create"
  final static String PATIENT_UPDATE = "$PATIENT:update"
  final static String PATIENT_READ = "$PATIENT:read"
  private final static String REFERRAL = 'referral'
  final static String REFERRAL_WRITE = "$REFERRAL:write"
  final static String REFERRAL_READ = "$REFERRAL:read"
  private final static String TEMPLATE = 'template'
  final static String TEMPLATE_WRITE = "$TEMPLATE:write"
  final static String TEMPLATE_READ = "$TEMPLATE:read"
  private final static String QUEST = 'quest'
  final static String QUEST_WRITE = "$QUEST:write"
  final static String QUEST_READ = "$QUEST:read"
  private final static String ORG = 'org'
  final static String ORG_WRITE = "$ORG:write"
  final static String ORG_READ = "$ORG:read"
  final static String USER = 'ROLE_USER'


  String[] vTypes = [
    User.CLASS_NAME,
    Privilege.CLASS_NAME
  ]

  String[] eTypes = [
    User.E_HAS_PRIV
  ]

  @Resource(name='graph')
  Graph g

  @Resource(name='graphNoTx')
  Graph gNoTx

  @Autowired
  PasswordEncoder passwordEncoder

  static Log log = LogFactory.getLog(UserRepositoryInitializer)

  void setUp() {
    vTypes.each { name ->
      if (!g.getVertexType(name)) {
        gNoTx.createVertexType(name)
      }
    }

    eTypes.each { name ->
      if (!g.getEdgeType(name)) {
        gNoTx.createEdgeType(name)
      }
    }

    addUser(
      'st-send-1',
      's3cret',
      [
        APP_ST,
        APP_ST_OUT,
        APP_ST_CANCEL,
        APP_ST_NOTIFY,
        PATIENT_CREATE,
        REFERRAL_WRITE,
        USER
      ]
    )

    g.commit()
  }

  void tearDown() {
    log.debug('tear-down')

    vTypes.each { name ->
      if (g.getVertexType(name)) {
        gNoTx.dropVertexType(name)
      }
    }
    log.debug('dropped v-types')

    eTypes.each { name ->
      if (g.getEdgeType(name)) {
        gNoTx.dropEdgeType(name)
      }
    }
    log.debug('dropped e-types')

    g.V.remove()
    log.debug('removed V')

    g.E.remove()
    log.debug('removed E')

    g.rollback()
  }

  def addUser(String name, String password, def privNames) {
    Vertex user = g.addVertex(asClass(User.CLASS_NAME))
    user.setProperty(User.P_NAME, name)
    user.setProperty(User.P_PASSWORD, passwordEncoder.encode(password))
    privNames.each { privName ->
      def priv = g.V('@class', Privilege.CLASS_NAME).has(Privilege.P_NAME, privName)
      if (!priv) {
        priv = g.addVertex(asClass(Privilege.CLASS_NAME))
        priv.setProperty(Privilege.P_NAME, privName)
      }
      g.addEdge(asClass(User.E_HAS_PRIV), user, priv, User.E_HAS_PRIV)
    }
  }

}
