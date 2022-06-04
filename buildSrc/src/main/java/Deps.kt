import com.ilpanda.verison.DefaultDependencyManager

object Libs {

    @JvmStatic
    val dep = Dep()

}

class Dep : DefaultDependencyManager() {

    fun version_code(): Int = 1

    fun version_name(): String = "1.0.0"

}

