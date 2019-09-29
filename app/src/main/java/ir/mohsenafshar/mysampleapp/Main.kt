package ir.mohsenafshar.mysampleapp

fun main() {
    calc {
        var res = 0
        for ( i in 0..10000)
            for (j in 0 until 10000)
                res += i + j
    }
}


fun calc(func: () -> Unit) {
    val t1 = System.currentTimeMillis()
    func()
    val t2 = System.currentTimeMillis()
    println("time elapsed: ${(t2 - t1)} ms")
}