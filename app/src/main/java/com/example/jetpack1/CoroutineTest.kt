package com.example.jetpack1.network

val lValue by lazy {
    println("use lazy")
    "haha"
}

fun main() {
    println(lValue)
    println(lValue)
    val activity = Activity()
    println(activity.mTextView)
    println(activity.mImageView)

    val result = plus { x, y ->
        x + y
    }
    println(result)
    kotlinDSL { init ->
        append(init)
        println(this)
    }
}

fun plus(block:(Int,Int) -> Int) : Int{
    return block(2,3)
}

fun kotlinDSL(block:StringBuilder.(String) -> Unit){
    block(StringBuilder("Kotlin"),"DSL")
}

class View(var type: String) {
    override fun toString(): String {
        return type
    }
}
class Activity {

    fun findViewById(it: Int): View {
        return if (it == 5) {
            View("Text")
        } else {
            View("Image")
        }
    }
    val mTextView by bindView<View>(5)
    val mImageView by bindView<View>(1)
}

fun <T : View> Activity.bindView(id: Int): Lazy<T> = lazy {
    findViewById(id) as T
}




