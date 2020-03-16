package fb.util.transformation.adapter

import fb.util.transformation.*


class DefAdapter : CreateReadFieldInterFace,CreateWriteFieldInterFace {

    companion object{
        val read =  object :ReadFieldInterFace{
            override fun read(list: ArrayList<TField>) {
            }
        }
        val write = object :WriteFieldInterFace{
            override fun merge(inField: TField) {

            }

            override fun write() {

            }

        }
    }

    override fun createRead(field: TField):ReadFieldInterFace {
        return read
    }

    override fun createWrite(outField: TField):WriteFieldInterFace? {
        return write
    }
}