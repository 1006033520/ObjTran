package fb.util.transformation.adapter.`object`

import com.google.gson.annotations.SerializedName
import fb.util.transformation.*
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

/**
 * object适配器
 */
open class ObjectAdapter(val context: ObjTran) : AdapterInterFace {
    override fun createRead(field: TField): ReadFieldInterFace? {
        return Read(context, field)
    }

    open class Read(val context: ObjTran, val filed: TField) : ReadFieldInterFace {
        override fun read(list: ArrayList<TField>) {
            filed.type.declaredFields.forEach { jfiled ->
                jfiled.isAccessible = true
                val value = filed.value?.let {
                    jfiled.get(it)
                }

                list.add(
                    ObjectField(
                        context,
                        key = jfiled.name,
                        type = value?.javaClass ?: {
                            var type = jfiled.type
                            if (filed is ObjectField && filed.genericTypes != null) {
                                val t = filed.genericTypes[jfiled.genericType.typeName]
                                type = t ?: jfiled.type
                            }
                            type as Class<out Any>
                        }(),
                        value = value,
                        field = jfiled,
                        genericTypes = {
                            var hashMap: HashMap<String, Class<out Any>>? = null
                            val genericType = jfiled.genericType
                            if (genericType is ParameterizedType) {
                                hashMap = HashMap()
                                genericType.actualTypeArguments.forEachIndexed { index, type ->
                                    val key = jfiled.type.typeParameters[index]
                                    type as Class<out Any>
                                    hashMap[key.name] = type
                                }
                            }
                            hashMap
                        }(),
                        annotations = jfiled.annotations
                    )
                )
            }
        }

    }

    override fun createWrite(outField: TField): WriteFieldInterFace? {
        return Write(outField)
    }

    open class Write(filed: TField) : BaseWrite(filed) {
        override fun merge(inField: TField) {
            if (filed.type == inField.type && filed.value == null) {
                filed.value = inField.value
            } else {
                next(inField)
            }

        }

        override fun write() {
            filed.childs.forEach {
                it as ObjectField
                if (it.isValue) {
                    if (it.value == null && it.childs.size != 0) {
                        it.value = filed.context.createObj(it.type)
                    }
                    if (it.value != null) {
                        it.field.set(filed.value, it.value)
                        it.write()
                    }
                }
            }
        }
    }

    /**
     * @param genericTypes 泛型对照
     */
    open class ObjectField(
        context: ObjTran,
        key: String? = null,
        value: Any? = null,
        type: Class<out Any>,
        val field: Field,
        val genericTypes: HashMap<String, Class<out Any>>? = null,
        var annotations: Array<Annotation>
    ) : TField(context, key, value, type) {
        var alternate: Array<String>? = null

        init {
            serializedName()
        }

        private fun serializedName(){
            try {
                val serializedName = field.getAnnotation(SerializedName::class.java)
                if (serializedName != null) {
                    alternate = serializedName.alternate
                    super.key = serializedName.value
                }
            }catch (e:ClassNotFoundException){

            }

        }

        override fun merge(inField: TField) {
            super.merge(inField)
            isValue = true
        }
    }
}