package fb.util.transformation

/**
 * 字段内容读取
 */
interface ReadFieldInterFace {
    /**
     * @param list 字段的子字段
     * 读取该项下的所有字段添加至list
     */
    fun read(list: ArrayList<TField>)
}