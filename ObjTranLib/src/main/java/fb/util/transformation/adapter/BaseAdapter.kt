package fb.util.transformation.adapter

import fb.util.transformation.*

abstract class BaseAdapter : AdapterInterFace{

    abstract fun isType(field: TField):Boolean

    abstract fun read(field: TField):ReadFieldInterFace
    abstract fun write(field: TField):WriteFieldInterFace

    override fun createRead(field: TField): ReadFieldInterFace? {
        if (isType(field))
            return read(field)
        return null
    }

    override fun createWrite(outField: TField): WriteFieldInterFace? {
        if (isType(outField))
            return write(outField)
        return null
    }
}