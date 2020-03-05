package fb.util.transformation

interface AfterReading {
    fun afterReading(o:Any,field:TField):TField
}