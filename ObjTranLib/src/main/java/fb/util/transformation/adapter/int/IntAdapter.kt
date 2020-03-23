package fb.util.transformation.adapter.int

import fb.util.transformation.BaseWrite
import fb.util.transformation.ReadFieldInterFace
import fb.util.transformation.TField
import fb.util.transformation.type.Types
import fb.util.transformation.WriteFieldInterFace
import fb.util.transformation.adapter.BaseAdapter

class IntAdapter : BaseAdapter() {
    override fun isType(field: TField): Boolean {
        return field.type in Types.INT_S
    }

    override fun read(field: TField): ReadFieldInterFace {
        return object : ReadFieldInterFace {
            override fun read(list: ArrayList<TField>) {

            }
        }
    }

    override fun write(field: TField): WriteFieldInterFace {
        return object : BaseWrite(field) {
            override fun merge(inField: TField) {
                if (isType(inField)) {
                    field.value = inField.value
                }
            }

            override fun write() {
            }
        }
    }
}