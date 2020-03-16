package fb.util.transformation

object Equal {
    val defEqual by lazy {
        object : FieldEqual {
            override fun equal(inField: TField, outField: TField): Boolean {
                return inField.key == outField.key
            }
        }
    }

}