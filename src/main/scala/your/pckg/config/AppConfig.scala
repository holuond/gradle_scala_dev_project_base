package your.pckg.config

/**
 * Container for application configuration
 */
case class AppConfig(user: String,
                     host: String,
                     fileExtensions: List[String]
                    )