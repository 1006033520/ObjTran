package fb.util.transformation

interface BeforeWriting {
    fun beforeWriting(field:TField,o:Any)
}