package fb.util.transformation

abstract class BaseWrite (protected val filed:TField): WriteFieldInterFace {
    var context:ObjTran = filed.context

    /**
     * 读取合并下一层
     */
    protected fun next(inField: TField){
        filed.childs.forEach { child ->
            inField.childs.forEach { `in` ->
                if (child.equal(`in`))
                    child.merge(`in`)
            }
        }
    }

    override fun create() {
    }
}