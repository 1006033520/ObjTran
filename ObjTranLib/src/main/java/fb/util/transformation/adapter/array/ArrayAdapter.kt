package fb.util.transformation.adapter.array

import fb.util.transformation.*

class ArrayAdapter(val context: ObjTran) : AdapterInterFace {
    override fun createRead(field: TField): ReadFieldInterFace? {
        if (field.type.isArray)
            return Read(context, field)
        return null
    }

    class Read(val context: ObjTran, val field: TField) : ReadFieldInterFace {
        override fun read(list: ArrayList<TField>) {
            var readType:ReadType = ReadType.KEY_NUMBER
            if (field is ArrayField){
                readType = field.readType
            }

            field.value?.also { v ->
                val length = java.lang.reflect.Array.getLength(v)
                for (i in 0 until length) {
                    val value = java.lang.reflect.Array.get(v, i)
                    list.add(
                        TField(
                            context,
                            key = when(readType){
                                ReadType.KEY_NUMBER -> "$i"
                                ReadType.KEY_NULL -> null
                            },
                            value = value,
                            type = value::class.java
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
            if (field.value == null) {
                inField.childs.forEach {
                    println("${it.value} +++")
                    field.childs.add( TField(
                        context,
                        value = it.value,
                        type = it.type
                    ))

                }
            }
            next(inField)
        }

        override fun create() {
            super.create()
            field.value =
                java.lang.reflect.Array.newInstance(field.type.componentType, field.childs.size)
        }

        override fun write() {
            field.childs.forEach {
                it.write()
            }
            val value = field.value
            if (value != null && field.isValue) {
                for (i in 0 until field.childs.size) {
                    field.childs[i].value?.let {
                        println("$it -----")
                        java.lang.reflect.Array.set(value, i, it)
                    }
                }
            }
        }

    }

    enum class ReadType{
        KEY_NUMBER,
        KEY_NULL
    }

    class ArrayField(private val tField: TField,val readType:ReadType):TField(tField.context,tField.key,tField.value,tField.type)

}