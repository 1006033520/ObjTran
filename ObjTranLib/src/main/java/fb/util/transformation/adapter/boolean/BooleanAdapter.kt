package fb.util.transformation.adapter.boolean

import fb.util.transformation.type.Types
import fb.util.transformation.ReadFieldInterFace
import fb.util.transformation.TField
import fb.util.transformation.WriteFieldInterFace
import fb.util.transformation.adapter.BaseAdapter

class BooleanAdapter:BaseAdapter() {
    override fun isType(field: TField): Boolean {
        return field.type in Types.BOOLEAN_S
    }

    override fun read(field: TField): ReadFieldInterFace {
        return object :ReadFieldInterFace{
            override fun read(list: ArrayList<TField>) {
            }

        }
    }

    override fun write(field: TField): WriteFieldInterFace {
        return object :WriteFieldInterFace{
            override fun assignment(inField: TField) {
                if (isType(inField))
                    field.value = inField.value
            }

            override fun write() {
            }

        }
    }
}