package fb.util.transformation.adapter.`object`

import fb.util.transformation.*
import java.lang.reflect.Field

class ObjectAdapter(val context: ObjTran) : AdapterInterFace {
    override fun createRead(field: TField): ReadFieldInterFace? {
        return Read(context, field)
    }

    class Read(val context: ObjTran, val filed: TField) : ReadFieldInterFace {
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
                        type = value?.javaClass?:jfiled.type,
                        value = value,
                        field = jfiled
                    )
                )
            }
        }

    }

    override fun createWrite(outField: TField): WriteFieldInterFace? {
        return Write(outField)
    }

    class Write(filed: TField) : BaseWrite(filed) {
        override fun assignment(inField: TField) {
            if (filed.type == inField.type && filed.value == null) {
                filed.value = inField.value
            } else {
                next(inField)
            }

        }

        override fun write() {
            filed.childs.forEach {
                val objectField = it as ObjectField
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

    class ObjectField(
        context: ObjTran,
        key: String? = null,
        value: Any? = null,
        type: Class<out Any>,
        val field: Field
    ) : TField(context, key, value, type) {
        override fun assignment(inField: TField) {
            super.assignment(inField)
            isValue = true
        }
    }

}