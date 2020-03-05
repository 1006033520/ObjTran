package fb.util.transformation

/**
 * 创建读取数据的接口
 */
interface CreateReadFieldInterFace {
    /**
     * @param field 字段
     * @return 如果能创建读取则返回读取数据接口 否则返回null
     */
    fun createRead(field:TField):ReadFieldInterFace?
}