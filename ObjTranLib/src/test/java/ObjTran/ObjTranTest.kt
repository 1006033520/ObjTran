package ObjTran

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import fb.util.transformation.ObjTran
import fb.util.transformation.adapter.`object`.CreateObject
import org.junit.Test

class ObjTranTest {

    data class A<T>(
        val a1: T,
        val a2: C<Int, String>
    )

    class B {
        var b1: Int = 0
    }

    data class C<A, B>(
        val c1: A,
        val c2: B
    )

    data class AA(
        val a1: B?,
        val a2: CC<Int, String>? = null
    )

    class CC<A, B> {
        val c1: A? = null
        val c2: B? = null
    }


    @Test
    fun testGeneric() {
        val b = B()
        b.b1 = 123
        val a = A(b, C(456, "789"))
        val aa = AA(null, null)
        ObjTran().inObject(a).toObjects(aa).exec()
        println("${aa.a1?.b1} ${aa.a2?.c1} ${aa.a2?.c2}")
        assert(a.a1 == aa.a1 && a.a2.c1 == aa.a2?.c1 && a.a2.c2 == aa.a2.c2)
    }

    class S {
        @SerializedName("s1")
        var abc = 0
    }

    class SS {
        val s1 = 0
    }

    class SSS {
        @SerializedName("s1")
        val abd = 0
    }

    @Test
    fun serializedName() {
        val s = S()
        s.abc = 111
        val ss = SS()
        val sss = SSS()
        ObjTran().inObject(s).toObjects(ss, sss).exec()
        println("${ss.s1} ${sss.abd}")
        assert("111 111" == "${ss.s1} ${sss.abd}")
    }

    data class CreateObjectTestBean(
        val a: Int,
        val b: Long,
        val c: Boolean,
        val d: String
    )

    @Test
    fun createObject() {
        val c = CreateObject().createObj(CreateObjectTestBean::class.java)
        c as CreateObjectTestBean
        println(c.a)
        println(c.b)
        println(c.d)

//        val c1 = Gson().fromJson("{}", CreateObjectTestBean::class.java)
//        println(c1.d)
    }

}