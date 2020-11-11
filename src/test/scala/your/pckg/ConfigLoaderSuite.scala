package your.pckg

import your.pckg.config.{AppConfig, ConfigLoader}
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import scala.util.{Failure, Success, Try}

/**
 * This suite tests loading of the configuration into the application.
 */
@RunWith(classOf[JUnitRunner])
class ConfigLoaderSuite extends AnyFunSpec with Matchers {

  describe("ConfigLoader") {
    it("should get an AppConfig instance with expected values") {
      val appConfigFilePath = this.getClass.getClassLoader.getResource("application.conf").getPath
      val args = Array("--config", appConfigFilePath)
      val result: Try[AppConfig] = ConfigLoader.getAppConfigFromArgs(args)

      // validate type
      result shouldBe a[Success[_]]

      // validate schema
      val appConfig: AppConfig = result.get
      appConfig.user should equal("testUser")
      appConfig.host should equal("testHost")
      appConfig.fileExtensions should equal(List("csv"))
    }

    it("should not get app config from any other source if path from args is invalid") {
      val appConfigFilePath = "!invalid-path!"
      val args = Array("--application-config", appConfigFilePath)
      val result: Try[AppConfig] = ConfigLoader.getAppConfigFromArgs(args)

      result shouldBe a[Failure[_]]
    }
  }
}
