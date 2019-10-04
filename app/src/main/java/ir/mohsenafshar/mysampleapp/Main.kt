package ir.mohsenafshar.mysampleapp

import java.lang.reflect.*
import java.lang.reflect.Array

fun main() {
//    calc {
//        var res = 0
//        for ( i in 0..10000)
//            for (j in 0 until 10000)
//                res += i + j
//    }

    val parameterUpperBound = getParameterUpperBound(0, ArrayList<String>() as ParameterizedType)
    val rawType = getRawType(parameterUpperBound)
    println(rawType)
}



fun calc(func: () -> Unit) {
    val t1 = System.currentTimeMillis()
    func()
    val t2 = System.currentTimeMillis()
    println("time elapsed: ${(t2 - t1)} ms")
}

fun getParameterUpperBound(index: Int, type: ParameterizedType): Type {
    val types = type.actualTypeArguments
    require(!(index < 0 || index >= types.size)) { "Index " + index + " not in range [0," + types.size + ") for " + type }
    val paramType = types[index]
    return if (paramType is WildcardType) {
        paramType.upperBounds[0]
    } else paramType
}

fun getRawType(type: Type): Class<*> {
    //checkNotNull(type, "type == null")

    if (type is Class<*>) {
        // Type is a normal class.
        return type as Class<*>
    }
    if (type is ParameterizedType) {
        val parameterizedType = type as ParameterizedType

        // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
        // suspects some pathological case related to nested classes exists.
        val rawType = parameterizedType.rawType
        if (rawType !is Class<*>) throw IllegalArgumentException()
        return rawType as Class<*>
    }
    if (type is GenericArrayType) {
        val componentType = (type as GenericArrayType).genericComponentType
        return Array.newInstance(getRawType(componentType), 0).javaClass
    }
    if (type is TypeVariable<*>) {
        // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
        // type that's more general than necessary is okay.
        return Any::class.java
    }
    if (type is WildcardType) {
        return getRawType((type as WildcardType).upperBounds[0])
    }

    throw IllegalArgumentException(
        "Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.javaClass.name
    )
}