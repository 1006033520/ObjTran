package fb.util.transformation.adapter.map

import fb.util.transformation.BaseWrite
import fb.util.transformation.ReadFieldInterFace
import fb.util.transformation.TField
import fb.util.transformation.WriteFieldInterFace
import fb.util.transformation.adapter.BaseAdapter

class MapAdapter : BaseAdapter() {
    override fun isType(field: TField): Boolean {
        return java.util.Map::class.java.isAssignableFrom(field.type)
    }

    override fun read(field: TField): ReadFieldInterFace {
        return object : ReadFieldInterFace {
            override fun read(list: ArrayList<TField>) {
                if (field.value != null) {
                    val map = field.value as Map<*, *>
                    map.keys.forEach { key ->
                        if (key is String) {
                            val value = map[key]
                            list.add(
                                TField(field.context, key, value, value!!::class.java)
                            )
                        }
                    }
                }
            }

        }
    }

    override fun write(field: TField): WriteFieldInterFace {
        return object : BaseWrite(field) {
            override fun merge(inField: TField) {
                if (filed.type == inField.type && filed.value == null) {
                    filed.value = inField.value
                } else if (field.value != null){
                    inField.childs.forEach {
                        val f = field[it.key]
                        if (f == null){
                            field.childs.add(it)
                        }else{
                            f.merge(it)
                        }
                    }

                }
            }

            override fun create() {
                super.create()

            }

            override fun write() {
                field.childs.forEach {

                }
            }

        }
    }
}