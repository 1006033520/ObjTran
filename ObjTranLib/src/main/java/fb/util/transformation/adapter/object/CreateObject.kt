package fb.util.transformation.adapter.`object`

import fb.util.transformation.CreateObjInterFace
import java.lang.reflect.Modifier

class CreateObject : CreateObjInterFace {

    private lateinit var unsafe: Any
    private val allocateInstance by lazy {
        val unsafeClass = Class.forName("sun.misc.Unsafe")
        val f = unsafeClass.getDeclaredField("theUnsafe")
        f.isAccessible = true
        unsafe = f[null]
        unsafeClass.getMethod("allocateInstance", Class::class.java)
    }

    override fun createObj(c: Class<out Any>): Any? {
        assertInstantiable(c)
        c.declaredConstructors.forEach { constructor ->
            return if (constructor.parameterTypes.isEmpty()) {
                constructor.newInstance()
            } else {
                allocateInstance.invoke(unsafe, c)
            }
        }

        return null
    }

    private fun assertInstantiable(c: Class<*>) {
        val modifiers = c.modifiers
        if (Modifier.isInterface(modifiers)) {
            throw UnsupportedOperationException("Interface can't be instantiated! Interface name: " + c.name)
        }
        if (Modifier.isAbstract(modifiers)) {
            throw UnsupportedOperationException("Abstract class can't be instantiated! Class name: " + c.name)
        }
    }
}