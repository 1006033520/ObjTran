package fb.util.transformation

abstract class BaseWrite (protected val filed:TField): WriteFieldInterFace {
    var context:ObjTran = filed.context


    protected fun next(inField: TField){
        filed.childs.forEach { child ->
            inField.childs.forEach { `in` ->
                if (child.equal(`in`))
                    child.merge(`in`)
            }
        }
    }
}