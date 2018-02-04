package services

import java.time.{Clock, Instant}
import javax.inject._
import play.api.Logger
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future
import scalikejdbc._

@Singleton
class ApplicationTimer @Inject() (clock: Clock, appLifecycle: ApplicationLifecycle) {

  // This code is called when the application starts.
  private val start: Instant = clock.instant

  Logger.info(s"ApplicationTimer demo: Starting application at $start.")
  appLifecycle.addStopHook { () =>
    val stop: Instant = clock.instant
    val runningTime: Long = stop.getEpochSecond - start.getEpochSecond
    Logger.info(s"ApplicationTimer demo: Stopping application at ${clock.instant} after ${runningTime}s.")
    Future.successful(())
  }

  private val startUpVal:Int = Func()

  Logger.info(s"$startUpVal")

  private def Func():Int = {
    DB autoCommit { implicit s =>
      sql"""INSERT into test (x) VALUES (1)""".execute().apply()
    }

    val count = DB readOnly{ implicit s =>
      sql"""SELECT COUNT(*) FROM test""".map(_.int(1)).single.apply()
    }

    count.getOrElse(0)
  }
}
