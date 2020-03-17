# ObjTran 类型转换库[![](.README.assets/ObjTran.svg)](https://jitpack.io/#1006033520/ObjTran)

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


dependencies {
	        implementation 'com.github.1006033520:ObjTran:v1.0-alpha02'
	}
```



objTran是类型转换 

使用kotlin编写  有兴趣的可以转成java

使用方式：

```kotlin
data class A<T>(
    var a1: String?,
    var a2: Boolean?,
    var a3: Int?,
    var a4: T?,
    var a5: D?
)
data class D(
    var a1: Int
)
data class B(
    var a1: String?,
    var a2: Boolean?,
    var a3: Int?,
    val a4: Int,
  	val a5:E? = null
)
class E {
    var a1: Int = 0
}
fun main() {
  val a = A("123", false, null, 20, D(60))
  val a2 = A(null, true, 10, null, null)
  val b = B(null, null, 0, 0)
  objTran().inObject(a,a2).toObjects(b).exec()
}
```

特性：

 1. 支持多对多

 2. 在原有数据覆盖

    列如:

    ```kotlin
    data class A(
        var a1: String?,
        var a2: String?
    )
    data class B(
        var a1: String?,
        var a2: String?
    )
    fun main(){
      val a = A("123",null)
      val b = B(null,"456")
      objTran().inObject(a).toObjects(b).exec()
      println("${b.a1} ${b.a2}")
    }
    输出：123 456
    ```

即将支持：

 	1. 泛型  （已初步支持）
 	2. map
 	3. array
 	4. json
 	5. android （intent、Budle、ContentProvider）
 	6. date
 	7. string格式化（时间、日期、等自定义）
 	8. 类型转换器（date<->String,date<->Long,String<->Int、等等）
 	9. xml

