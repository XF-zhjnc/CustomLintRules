package com.mildom.lintchecks

inline fun <T1: Any, T2: Any, R: Any> safeLet(a: T1?, b: T2?, block: (T1, T2) -> R?): R? {
    return if (a != null && b != null) block(a, b) else null
}