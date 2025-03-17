package co.jedal.test.config

import org.yaml.snakeyaml.Yaml
import java.io.InputStream

object ConfigManager {
    private val config: Map<String, Any>

    init {
        val inputStream: InputStream? = object {}.javaClass.getResourceAsStream("/config.yaml")
        requireNotNull(inputStream) { "Configuration file not found!" }

        val yaml = Yaml()
        config = yaml.load(inputStream)
    }

    fun get(path: String): Any? {
        val keys = path.split(".")
        var value: Any? = config
        for (key in keys) {
            if (value is Map<*, *>) {
                value = value[key]
            } else {
                return null  // Path does not exist
            }
        }
        return value
    }
}