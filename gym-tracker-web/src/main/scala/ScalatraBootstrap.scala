import javax.servlet.ServletContext

import com.dyrkin.tracker.core.service.Services
import com.dyrkin.tracker.web.{HtmlRoutes, JsonRoutes}
import org.scalatra.LifeCycle

/**
  * @author eugene zadyra
  */
class ScalatraBootstrap extends LifeCycle {

  val services = new Services

  override def init(context: ServletContext) {
    context mount(new JsonRoutes(services), "/json/*")
    context mount(new HtmlRoutes, "/")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    services.db.close()
  }
}
