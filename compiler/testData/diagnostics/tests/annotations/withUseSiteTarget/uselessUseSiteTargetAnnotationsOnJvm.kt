import kotlin.reflect.KProperty

annotation class Ann
annotation class AnnRepeat

class Foo(@<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>get<!>:Ann private val x0: Int) {
    @<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>get<!>:Ann
    private val x1 = ""

    @<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>set<!>:Ann
    private var x2 = ""

    @<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>setparam<!>:Ann
    private var x3 = ""

    @<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>setparam<!>:[Ann AnnRepeat]
    private var x4 = ""

    @get:Ann
    internal val x5 = ""

    @get:Ann
    protected val x6 = ""
}

private class EffetivelyPrivate private constructor(
    @get:Ann val x0: Int,
    @get:Ann protected val x1: Int,
    @get:Ann internal val x2: Int
) {
    private class Nested {
        @get:Ann
        val fofo = 0
    }
}

class PrivateToThis<in I> {
    @<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>get<!>:Ann
    @<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>set<!>:Ann
    @<!ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION!>setparam<!>:Ann
    private var x0: I = TODO()
}

private class Other(@param:Ann private val param: Int) {
    @property:Ann
    @field:Ann
    private val other = ""

    private fun @receiver:Ann Int.receiver() {}

    @delegate:Ann
    private val delegate by CustomDelegate()
}

class CustomDelegate {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String = prop.name
}
