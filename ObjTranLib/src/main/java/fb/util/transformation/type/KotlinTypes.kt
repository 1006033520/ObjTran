package fb.util.transformation.type

import kotlin.reflect.KClass

object KotlinTypes {
    var Int: KClass<*> = kotlin.Int::class
    var Long: KClass<*> = kotlin.Long::class
    var Boolean: KClass<*> = kotlin.Boolean::class
    var Short: KClass<*> = kotlin.Short::class
    var Byte: KClass<*> = kotlin.Byte::class
    var Char: KClass<*> = kotlin.Char::class
    var Double: KClass<*> = kotlin.Double::class
    var Float: KClass<*> = kotlin.Float::class
}