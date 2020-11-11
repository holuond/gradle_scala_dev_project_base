package your.pckg

import your.pckg.config.{AppConfig, ConfigLoader}
import org.apache.log4j.Logger
import scala.util.{Failure, Success, Try}

object Main {
  private val logger: Logger = Logger.getLogger(getClass.getName)

  /**
   * Acquires the [[config.AppConfig]] and passes the execution further.
   *
   * @param args command line args
   */
  def main(args: Array[String]): Unit = {
    logger.info("Started main function")
    implicit val appConfig: AppConfig = ConfigLoader.getAppConfigFromArgs(args) match {
      case Success(config) => config
      case Failure(ex) => throw ex
    }
    // TODO continue your execution here with config params already loaded
  }
}
