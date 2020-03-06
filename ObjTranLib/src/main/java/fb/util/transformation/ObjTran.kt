package fb.util.transformation

import fb.util.transformation.adapter.DefAdapter
import fb.util.transformation.adapter.`object`.CreateObject
import fb.util.transformation.adapter.`object`.ObjectAdapter
import fb.util.transformation.adapter.array.ArrayAdapter
import fb.util.transformation.adapter.boolean.BooleanAdapter
import fb.util.transformation.adapter.byte.ByteAdapter
import fb.util.transformation.adapter.char.CharAdapter
import fb.util.transformation.adapter.double.DoubleAdapter
import fb.util.transformation.adapter.float.FloatAdapter
import fb.util.transformation.adapter.int.IntAdapter
import fb.util.transformation.adapter.long.LongAdapter
import fb.util.transformation.adapter.short.ShortAdapter
import fb.util.transformation.adapter.string.StringAdapter
import java.lang.Exception

class ObjTran {

    //    val createReadFieldInterFaces = ArrayList<CreateReadFieldInterFace>()
//    val createWriteFieldInterFaces = ArrayList<CreateWriteFieldInterFace>()
    private val createAdapterInterFaces = ArrayList<AdapterInterFace>()
    private val createObjInterFaces = ArrayList<CreateObjInterFace>()
    private val afterReadings = ArrayList<AfterReading>()

    private var inField: TField? = null
    private var outField: TField? = null

    init {
        createAdapterInterFaces.add(ShortAdapter())
        createAdapterInterFaces.add(FloatAdapter())
        createAdapterInterFaces.add(DoubleAdapter())
        createAdapterInterFaces.add(CharAdapter())
        createAdapterInterFaces.add(ByteAdapter())
        createAdapterInterFaces.add(BooleanAdapter())
        createAdapterInterFaces.add(LongAdapter())
        createAdapterInterFaces.add(BooleanAdapter())
        createAdapterInterFaces.add(IntAdapter())
        createAdapterInterFaces.add(StringAdapter())
        createAdapterInterFaces.add(ArrayAdapter(this))
        createAdapterInterFaces.add(ObjectAdapter(this))

        createObjInterFaces.add(CreateObject(this))
    }

    fun inObject(vararg objs: Any): ObjTran {
        inField = TField(
            this,
            value = objs,
            type = objs::class.java
        )
        return this
    }

    fun toObjects(vararg objs: Any): ObjTran {
        outField = TField(
            this,
            value = objs,
            type = objs::class.java
        )
        return this
    }

    fun exec(): ObjTran {
        if (inField != null && outField != null) {
            exec(inField!!, outField!!)
        }
        return this
    }

    private fun exec(inField: TField, outField: TField) {
        outField.assignment(inField)
        outField.write()
    }

    internal fun read(field: TField):ReadFieldInterFace {
        createAdapterInterFaces.forEach {
            val r = it.createRead(field)
            if (r != null)
                return r
        }
        return DefAdapter.read
    }

    internal fun findCreateWrite(field: TField):WriteFieldInterFace {
        createAdapterInterFaces.forEach {
            val w = it.createWrite(field)
            if (w != null)
                return w
        }
        return DefAdapter.write
    }

    internal fun createObj(c: Class<out Any>): Any? {
        createObjInterFaces.forEach {
            val obj = it.createObj(c)
            if (obj != null)
                return obj
        }
        return null
    }


}