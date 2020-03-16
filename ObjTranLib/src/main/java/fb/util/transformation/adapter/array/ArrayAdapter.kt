package fb.util.transformation.adapter.array

import fb.util.transformation.*

class ArrayAdapter(val context: ObjTran) : AdapterInterFace {
    override fun createRead(field: TField): ReadFieldInterFace? {
        if (field.type == Array<Any>::class.java)
            return Read(context, field)
        return null
    }

    class Read(val context: ObjTran, val field: TField) : ReadFieldInterFace {
        override fun read(list: ArrayList<TField>) {
            field.value?.also { v ->
                val arr = v as Array<out Object>
                arr.forEach {
                    list.add(
                        TField(
                            context,
                            value = it,
                            type = it::class.java
                        )
                    )

                }
            }
        }
    }

    override fun createWrite(outField: TField): WriteFieldInterFace? {
        if (outField.type.isArray)
            return Write(outField)
        return null
    }

    class Write(val field: TField) : BaseWrite(field) {
        override fun merge(inField: TField) {
            next(inField)
        }

        override fun write() {
            field.childs.forEach {
                it.write()
            }
        }

    }
}