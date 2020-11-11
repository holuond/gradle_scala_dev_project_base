package your.pckg.config

import org.apache.log4j.Logger
import pureconfig.ConfigSource
import scopt.OptionParser
// necessary for pureconfig: import pureconfig.generic.auto._
import pureconfig.generic.auto._
import scala.util.Try

/**
  * Reads input parameters from the command line,
  * extracts the HOCON application config into [[AppConfig]] case class
  */
object ConfigLoader {
  private val logger: Logger = Logger.getLogger(getClass.getName)

  /**
    * Takes in command line args and parses it into the [[AppConfig]] case class container
    *
    * @param args command line args (from main)
    * @return Try wrapped [[AppConfig]] container
    */
  def getAppConfigFromArgs(args: Array[String]): Try[AppConfig] = {
    Try {
      logger.info(s"Parsing ${args.length} command line args.")
      val parsedArgs: ParsedArgs = parseArguments().parse(args, ParsedArgs("")).get

      logger.info(s"Reading config file from path: ${parsedArgs.appConfigPathName}")
      val appConfig: AppConfig = ConfigSource.file(parsedArgs.appConfigPathName).at("app").loadOrThrow[AppConfig]

      logger.info("Successfully loaded an AppConfig instance")
      appConfig
    }
  }

  /**
    * A container for command line arguments
    *
    * @param appConfigPathName pathname of app config
    */
  private case class ParsedArgs(appConfigPathName: String)

  /**
    * Integrates scopt in a standard manner for parsing command line arguments into [[ParsedArgs]]
    *
    * @return scopt's OptionParser
    */
  private def parseArguments(): OptionParser[ParsedArgs] = {
    new scopt.OptionParser[ParsedArgs]("your.app") {
      head("your.app", this.getClass.getPackage.getImplementationVersion)

      opt[String]('c', "config")
        .action((x, c) => c.copy(appConfigPathName = x))
        .text("Application config pathname")
        .required()
    }
  }
}
