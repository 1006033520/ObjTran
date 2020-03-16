package fb.util.transformation

/**
 * 字段
 * 输入及输入内容保存
 *
 * @param context 上下文
 * @param key 主键
 * @param value 值 不是最终结果
 * @param type 字段的类型
 */
open class TField(
    val context: ObjTran,
    var key: String? = null,
    value: Any? = null,
    var type: Class<out Any>

) : ReadFieldInterFace, WriteFieldInterFace {
    private val read by lazy { //获取创建读取工具
        context.read(this)
    }
    private val write by lazy {//获取创建写入工具
        context.findCreateWrite(this)
    }

    var isValue = false //值是否改变
        protected set

    val fieldEqual: ArrayList<FieldEqual> by lazy {//key 对比工具
        val list = ArrayList<FieldEqual>()
        setFieldEqual(list)
        list
    }

    /**
     * @param list 对比工具列表
     */
    protected open fun setFieldEqual(list:ArrayList<FieldEqual>){
        list.add(Equal.defEqual)
    }

    var value: Any? = value //值  在没有子项这个往往是最终结果
        set(v) {
            if (v != null) {
                field = v
                isValue = true
            }
        }


    val childs by lazy { //子项
        val list = ArrayList<TField>()
        read(list)
        list
    }

    open fun equal(inField: TField): Boolean { //对比
        fieldEqual.forEach {
            val equal = it.equal(inField, this)
            if (equal)
                return true
        }
        return false
    }

    /**
     * 读取
     * 从字段中读取内容或者子项
     * @param list 子项 传入时为0个  要求对应适配器添加子项
     * 核心步骤1
     */
    override fun read(list: ArrayList<TField>) {
        read.read(list)
    }

    /**
     * 合并
     * @param inField 输入项
     * 将 inField 内容合并 成最终结果
     * 核心步骤2
     */
    override fun merge(inField: TField) {
        write.merge(inField)
    }

    /**
     * 写入
     * 把最终结果写入对应obj
     * 核心步骤3
     */
    override fun write() {
        write.write()
    }
}