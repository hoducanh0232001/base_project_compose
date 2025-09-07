package com.example.core.settings

operator fun Settings.contains(key: String): Boolean = hasKey(key)
operator fun Settings.minusAssign(key: String): Unit = remove(key)
operator fun Settings.get(key: String, defaultValue: Int): Int = getInt(key, defaultValue)
operator fun Settings.get(key: String, defaultValue: Long): Long =
    getLong(key, defaultValue)

operator fun Settings.get(key: String, defaultValue: String): String =
    getString(key, defaultValue)

operator fun Settings.get(key: String, defaultValue: Float): Float =
    getFloat(key, defaultValue)

operator fun Settings.get(key: String, defaultValue: Double): Double =
    getDouble(key, defaultValue)

operator fun Settings.get(key: String, defaultValue: Boolean): Boolean =
    getBoolean(key, defaultValue)

operator fun Settings.set(key: String, value: Int): Unit = putInt(key, value)
operator fun Settings.set(key: String, value: Long): Unit = putLong(key, value)
operator fun Settings.set(key: String, value: String): Unit = putString(key, value)
operator fun Settings.set(key: String, value: Float): Unit = putFloat(key, value)
operator fun Settings.set(key: String, value: Double): Unit = putDouble(key, value)
operator fun Settings.set(key: String, value: Boolean): Unit = putBoolean(key, value)
inline operator fun <reified T : Any> Settings.get(key: String): T? = when (T::class) {
    Int::class -> getIntOrNull(key) as T?
    Long::class -> getLongOrNull(key) as T?
    String::class -> getStringOrNull(key) as T?
    Float::class -> getFloatOrNull(key) as T?
    Double::class -> getDoubleOrNull(key) as T?
    Boolean::class -> getBooleanOrNull(key) as T?
    else -> throw IllegalArgumentException("Invalid type!")
}

inline operator fun <reified T : Any> Settings.set(key: String, value: T?): Unit =
    if (value == null) {
        this -= key
    } else {
        when (T::class) {
            Int::class -> putInt(key, value as Int)
            Long::class -> putLong(key, value as Long)
            String::class -> putString(key, value as String)
            Float::class -> putFloat(key, value as Float)
            Double::class -> putDouble(key, value as Double)
            Boolean::class -> putBoolean(key, value as Boolean)
            else -> throw IllegalArgumentException("Invalid type!")
        }
    }

operator fun Settings.set(
    key: String,
    @Suppress("UNUSED_PARAMETER") value: Nothing?,
): Unit = remove(key)
