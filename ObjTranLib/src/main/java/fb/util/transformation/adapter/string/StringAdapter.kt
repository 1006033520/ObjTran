package fb.util.transformation.adapter.string

import fb.util.transformation.*
import fb.util.transformation.adapter.BaseAdapter

class StringAdapter : BaseAdapter() {
    override fun isType(field: TField): Boolean {
        return field.type == String::class.java
    }

    override fun read(field: TField): ReadFieldInterFace {
        return object : ReadFieldInterFace {
            override fun read(list: ArrayList<TField>) {

            }
        }
    }


    override fun write(field: TField): WriteFieldInterFace {
        return object : WriteFieldInterFace {
            override fun assignment(inField: TField) {
                if (isType(inField)) {
                    if (inField.type == String::class.java)
                        field.value = inField.value
                }
            }

            override fun write() {
            }

        }
    }


}