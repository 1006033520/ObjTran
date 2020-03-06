package fb.util.transformation.adapter.`object`

import fb.util.transformation.CreateObjInterFace
import fb.util.transformation.ObjTran

class CreateObject(private val context:ObjTran) :CreateObjInterFace {
    override fun createObj(c: Class<out Any>): Any? {
        c.constructors.forEach { constructor ->
            if (constructor.typeParameters.isEmpty()){
                return constructor.newInstance()
            }
        }
        return null
    }
}