package hu.benefanlabs.sensomediademo.data.mapper

interface Mapper<F, T> {
    fun map(from: F): T
}

fun <F, T> Mapper<F, T>.mapNotNullList(from: List<F>): List<T> {
    return from.map { item -> map(item) }
}

fun <F, T> Mapper<F, T>.mapList(from: List<F>?): List<T>? {
    return from?.let { mapNotNullList(it) }
}