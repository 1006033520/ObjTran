package fb.util.transformation

/**
 * 字段
 * 输入及输入内容保存
 *
 * @param context 上下文
 * @param key 主键
 * @param value 值 不是最终结果
 * @param type 字段的类型
 * @param fieldEqual 主键对比工具
 */
open class TField(
    val context: ObjTran,
    var key: String? = null,
    value: Any? = null,
    var type: Class<out Any>,
    var fieldEqual: ArrayList<FieldEqual> = ArrayList(Equal.defEqual)
) : ReadFieldInterFace, WriteFieldInterFace {
    private val read by lazy {
        context.read(this)
    }
    private val write by lazy {
        context.findCreateWrite(this)
    }

    var isValue = false
        protected set

    var value: Any? = value
        set(v) {
            if (v != null) {
                field = v
                isValue = true
            }
        }


    val childs by lazy {
        val list = ArrayList<TField>()
        read(list)
        list
    }

    open fun equal(inField: TField): Boolean {
        fieldEqual.forEach {
            val equal = it.equal(inField, this)
            if (equal)
                return true
        }
        return false
    }

    override fun read(list: ArrayList<TField>) {
        read.read(list)
    }

    override fun assignment(inField: TField) {
        write.assignment(inField)
    }

    override fun write() {
        write.write()
    }
}