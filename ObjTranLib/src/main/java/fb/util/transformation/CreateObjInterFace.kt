package fb.util.transformation

interface CreateObjInterFace {
    fun createObj(c:Class<out Any>):Any?
}