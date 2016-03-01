import javax.servlet.ServletContext

import com.dyrkin.tracker.core.service.Services
import com.dyrkin.tracker.web.Routes
import org.scalatra.LifeCycle
import slick.jdbc.JdbcBackend._

/**
  * @author eugene zadyra
  */
class ScalatraBootstrap extends LifeCycle {

  val services = new Services

  override def init(context: ServletContext) {
    context mount(new Routes(services), "/*")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    services.db.close()
  }
}
