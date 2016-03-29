package com.dyrkin.tracker.security
//
//import com.dyrkin.tracker.core.vo.WatchUserDetails
//import org.scalatra.auth.strategy.{BasicAuthStrategy, BasicAuthSupport}
//import org.scalatra.auth.{ScentrySupport, ScentryConfig}
//import org.scalatra.ScalatraBase
//import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
//
//
//class OurBasicAuthStrategy(protected override val app: ScalatraBase, realm: String) extends BasicAuthStrategy[WatchUserDetails](app, realm) {
//
//  protected def validate(userName: String, password: String)(implicit request: HttpServletRequest, response: HttpServletResponse): Option[WatchUserDetails] = {
//    DB.getUserByLogin(userName).filter(_.password == password)
//  }
//
//  protected def getUserId(user: WatchUserDetails)(implicit request: HttpServletRequest, response: HttpServletResponse): String = user.id.toString
//}
//
//trait AuthenticationSupport extends ScentrySupport[WatchUserDetails] with BasicAuthSupport[WatchUserDetails] {
//  self: ScalatraBase =>
//
//  val realm = "Scalatra Basic Auth Example"
//
//  protected def fromSession = {
//    case id: String => DB.getUserById(id.toInt).get
//  }
//
//  protected def toSession = {
//    case usr: WatchUserDetails => usr.id.toString
//  }
//
//  protected val scentryConfig = new ScentryConfig {}.asInstanceOf[ScentryConfiguration]
//
//
//  override protected def configureScentry() = {
//    scentry.unauthenticated {
//      scentry.strategies("Basic").unauthenticated()
//    }
//  }
//
//  override protected def registerAuthStrategies() = {
//    scentry.register("Basic", app => new OurBasicAuthStrategy(app, realm))
//  }
//
//}