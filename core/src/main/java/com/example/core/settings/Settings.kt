package com.example.core.settings

import android.content.Context
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path.Companion.toOkioPath

class Settings(
    private val context: Context,
    private val name: String,
) {
    private val storage = OkioStorage(FileSystem.SYSTEM, PreferencesSerializer) {
        val dir = context.filesDir.toOkioPath().resolve("settings")
        FileSystem.SYSTEM.createDirectory(dir)
        dir.resolve("$name.settings")
    }
    private val datastore = PreferenceDataStoreFactory.create(storage)

    fun keys(): Set<String> =
        runBlocking { datastore.data.first().asMap().keys.map { it.name }.toSet() }

    fun size(): Int = runBlocking { datastore.data.first().asMap().size }
    fun clear(): Unit = keys().forEach { remove(it) }

    fun remove(key: String) {
        runBlocking { datastore.edit { it.remove(stringSetPreferencesKey(key)) } }
    }

    fun hasKey(key: String): Boolean =
        runBlocking { datastore.data.first().contains(stringSetPreferencesKey(key)) }

    fun putInt(key: String, value: Int) {
        runBlocking {
            datastore.edit { it[intPreferencesKey(key)] = value }
        }
    }

    fun getIntFlow(key: String, defaultValue: Int): Flow<Int> =
        getValue { it[intPreferencesKey(key)] ?: defaultValue }

    fun getIntOrNullFlow(key: String): Flow<Int?> =
        getValue { it[intPreferencesKey(key)] }

    fun getInt(key: String, defaultValue: Int): Int =
        runBlocking { getIntFlow(key, defaultValue).first() }

    fun getIntOrNull(key: String): Int? = runBlocking { getIntOrNullFlow(key).first() }

    fun putLong(key: String, value: Long) {
        runBlocking {
            datastore.edit { it[longPreferencesKey(key)] = value }
        }
    }

    fun getLongFlow(key: String, defaultValue: Long): Flow<Long> =
        getValue { it[longPreferencesKey(key)] ?: defaultValue }

    fun getLongOrNullFlow(key: String): Flow<Long?> =
        getValue { it[longPreferencesKey(key)] }

    fun getLong(key: String, defaultValue: Long): Long =
        runBlocking { getLongFlow(key, defaultValue).first() }

    fun getLongOrNull(key: String): Long? = runBlocking { getLongOrNullFlow(key).first() }

    fun putString(key: String, value: String) {
        runBlocking {
            datastore.edit { it[stringPreferencesKey(key)] = value }
        }
    }

    fun getStringFlow(key: String, defaultValue: String): Flow<String> =
        getValue { it[stringPreferencesKey(key)] ?: defaultValue }

    fun getStringOrNullFlow(key: String): Flow<String?> =
        getValue { it[stringPreferencesKey(key)] }

    fun getString(key: String, defaultValue: String): String = runBlocking {
        getStringFlow(key, defaultValue).first()
    }

    fun getStringOrNull(key: String): String? = runBlocking { getStringOrNullFlow(key).first() }

    fun putFloat(key: String, value: Float) {
        runBlocking {
            datastore.edit { it[floatPreferencesKey(key)] = value }
        }
    }

    fun getFloatFlow(key: String, defaultValue: Float): Flow<Float> =
        getValue { it[floatPreferencesKey(key)] ?: defaultValue }

    fun getFloatOrNullFlow(key: String): Flow<Float?> =
        getValue { it[floatPreferencesKey(key)] }

    fun getFloat(key: String, defaultValue: Float): Float = runBlocking {
        getFloatFlow(key, defaultValue).first()
    }

    fun getFloatOrNull(key: String): Float? = runBlocking { getFloatOrNullFlow(key).first() }

    fun putDouble(key: String, value: Double) {
        runBlocking {
            datastore.edit { it[doublePreferencesKey(key)] = value }
        }
    }

    fun getDoubleFlow(key: String, defaultValue: Double): Flow<Double> =
        getValue { it[doublePreferencesKey(key)] ?: defaultValue }

    fun getDoubleOrNullFlow(key: String): Flow<Double?> =
        getValue { it[doublePreferencesKey(key)] }

    fun getDouble(key: String, defaultValue: Double): Double = runBlocking {
        getDoubleFlow(key, defaultValue).first()
    }

    fun getDoubleOrNull(key: String): Double? = runBlocking {
        getDoubleOrNullFlow(key).first()
    }

    fun putBoolean(key: String, value: Boolean) {
        runBlocking {
            datastore.edit { it[booleanPreferencesKey(key)] = value }
        }
    }

    fun getBooleanFlow(key: String, defaultValue: Boolean): Flow<Boolean> =
        getValue { it[booleanPreferencesKey(key)] ?: defaultValue }

    fun getBooleanOrNullFlow(key: String): Flow<Boolean?> =
        getValue { it[booleanPreferencesKey(key)] }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean = runBlocking {
        getBooleanFlow(key, defaultValue).first()
    }

    fun getBooleanOrNull(key: String): Boolean? = runBlocking { getBooleanOrNullFlow(key).first() }

    private inline fun <T> getValue(crossinline getValue: (Preferences) -> T): Flow<T> =
        datastore.data.map { getValue(it) }.distinctUntilChanged()
}
