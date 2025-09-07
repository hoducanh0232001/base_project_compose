package com.example.core.settings

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Settings.int(key: String = "", defaultValue: Int): ReadWriteProperty<Any?, Int> =
    IntDelegate(this, key, defaultValue)

fun Settings.long(key: String = "", defaultValue: Long): ReadWriteProperty<Any?, Long> =
    LongDelegate(this, key, defaultValue)

fun Settings.string(key: String = "", defaultValue: String): ReadWriteProperty<Any?, String> =
    StringDelegate(this, key, defaultValue)

fun Settings.float(key: String = "", defaultValue: Float): ReadWriteProperty<Any?, Float> =
    FloatDelegate(this, key, defaultValue)

fun Settings.double(key: String = "", defaultValue: Double): ReadWriteProperty<Any?, Double> =
    DoubleDelegate(this, key, defaultValue)

fun Settings.boolean(key: String = "", defaultValue: Boolean): ReadWriteProperty<Any?, Boolean> =
    BooleanDelegate(this, key, defaultValue)

fun <T> Settings.serializer(
    key: String = "",
    serializer: KSerializer<T>,
    defaultValue: T,
): ReadWriteProperty<Any?, T> =
    SerializerDelegate(this, key, serializer, defaultValue)

fun Settings.nullableInt(key: String = ""): ReadWriteProperty<Any?, Int?> =
    NullableIntDelegate(this, key)

fun Settings.nullableLong(key: String = ""): ReadWriteProperty<Any?, Long?> =
    NullableLongDelegate(this, key)

fun Settings.nullableString(key: String = ""): ReadWriteProperty<Any?, String?> =
    NullableStringDelegate(this, key)

fun Settings.nullableFloat(key: String = ""): ReadWriteProperty<Any?, Float?> =
    NullableFloatDelegate(this, key)

fun Settings.nullableDouble(key: String = ""): ReadWriteProperty<Any?, Double?> =
    NullableDoubleDelegate(this, key)

fun Settings.nullableBoolean(key: String = ""): ReadWriteProperty<Any?, Boolean?> =
    NullableBooleanDelegate(this, key)

fun <T> Settings.nullableSerializer(
    key: String = "",
    serializer: KSerializer<T>,
): ReadWriteProperty<Any?, T?> =
    NullableSerializerDelegate(this, key, serializer)

private class IntDelegate(
    private val settings: Settings,
    key: String,
    private val defaultValue: Int,
) : OptionalKeyDelegate<Int>(key) {
    override fun getValue(key: String): Int = settings[key, defaultValue]
    override fun setValue(key: String, value: Int) {
        settings[key] = value
    }
}

private class LongDelegate(
    private val settings: Settings,
    key: String,
    private val defaultValue: Long,
) : OptionalKeyDelegate<Long>(key) {
    override fun getValue(key: String): Long = settings[key, defaultValue]
    override fun setValue(key: String, value: Long) {
        settings[key] = value
    }
}

private class StringDelegate(
    private val settings: Settings,
    key: String,
    private val defaultValue: String,
) : OptionalKeyDelegate<String>(key) {
    override fun getValue(key: String): String = settings[key, defaultValue]
    override fun setValue(key: String, value: String) {
        settings[key] = value
    }
}

private class FloatDelegate(
    private val settings: Settings,
    key: String,
    private val defaultValue: Float,
) : OptionalKeyDelegate<Float>(key) {
    override fun getValue(key: String): Float = settings[key, defaultValue]
    override fun setValue(key: String, value: Float) {
        settings[key] = value
    }
}

private class DoubleDelegate(
    private val settings: Settings,
    key: String,
    private val defaultValue: Double,
) : OptionalKeyDelegate<Double>(key) {
    override fun getValue(key: String): Double = settings[key, defaultValue]
    override fun setValue(key: String, value: Double) {
        settings[key] = defaultValue
    }
}

private class BooleanDelegate(
    private val settings: Settings,
    key: String,
    private val defaultValue: Boolean,
) : OptionalKeyDelegate<Boolean>(key) {
    override fun getValue(key: String): Boolean = settings[key, defaultValue]
    override fun setValue(key: String, value: Boolean) {
        settings[key] = value
    }
}

private class SerializerDelegate<T>(
    private val settings: Settings,
    key: String,
    private val serializer: KSerializer<T>,
    private val defaultValue: T,
) : OptionalKeyDelegate<T>(key) {
    override fun getValue(key: String): T {
        return runCatching {
            Json.decodeFromString(serializer, settings[key, ""])
        }.getOrNull() ?: defaultValue
    }

    override fun setValue(key: String, value: T) {
        settings[key] = Json.encodeToString(serializer, value)
    }
}

private class NullableIntDelegate(
    private val settings: Settings,
    key: String,
) : OptionalKeyDelegate<Int?>(key) {
    override fun getValue(key: String): Int? = settings[key]
    override fun setValue(key: String, value: Int?) {
        settings[key] = value
    }
}

private class NullableLongDelegate(
    private val settings: Settings,
    key: String,
) : OptionalKeyDelegate<Long?>(key) {
    override fun getValue(key: String): Long? = settings[key]
    override fun setValue(key: String, value: Long?) {
        settings[key] = value
    }
}

private class NullableStringDelegate(
    private val settings: Settings,
    key: String,
) : OptionalKeyDelegate<String?>(key) {
    override fun getValue(key: String): String? = settings[key]

    override fun setValue(key: String, value: String?) {
        settings[key] = value
    }
}

private class NullableFloatDelegate(
    private val settings: Settings,
    key: String,
) : OptionalKeyDelegate<Float?>(key) {
    override fun getValue(key: String): Float? = settings[key]
    override fun setValue(key: String, value: Float?) {
        settings[key] = value
    }
}

private class NullableDoubleDelegate(
    private val settings: Settings,
    key: String,
) : OptionalKeyDelegate<Double?>(key) {
    override fun getValue(key: String): Double? = settings[key]
    override fun setValue(key: String, value: Double?) {
        settings[key] = value
    }
}

private class NullableBooleanDelegate(
    private val settings: Settings,
    key: String,
) : OptionalKeyDelegate<Boolean?>(key) {
    override fun getValue(key: String): Boolean? = settings[key]
    override fun setValue(key: String, value: Boolean?) {
        settings[key] = value
    }
}

private class NullableSerializerDelegate<T>(
    private val settings: Settings,
    key: String,
    private val serializer: KSerializer<T>,
) : OptionalKeyDelegate<T?>(key) {
    override fun getValue(key: String): T? {
        return runCatching {
            Json.decodeFromString(serializer, settings[key, ""])
        }.getOrNull()
    }

    override fun setValue(key: String, value: T?) {
        if (value == null) {
            settings -= key
        } else {
            settings[key] = Json.encodeToString(serializer, value)
        }
    }
}

/**
 * A [ReadWriteProperty] that coordinates its [getValue] and [setValue] functions via a [String] key. If the [key]
 * is null, then [KProperty.name] will be used as a default.
 */
private abstract class OptionalKeyDelegate<T>(private val key: String?) :
    ReadWriteProperty<Any?, T> {

    abstract fun getValue(key: String): T
    abstract fun setValue(key: String, value: T)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = getValue(key ?: property.name)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setValue(key ?: property.name, value)
    }
}
