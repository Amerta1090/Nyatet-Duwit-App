package com.nyatetduwit.core.security

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SecurityManager(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)

    init {
        keyStore.load(null)
    }

    fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun isPinSet(): Boolean {
        return prefs.getString(PIN_HASH, null) != null
    }

    fun setPin(pin: String) {
        val hashedPin = hashPin(pin)
        prefs.edit().putString(PIN_HASH, hashedPin).apply()
    }

    fun verifyPin(pin: String): Boolean {
        val storedHash = prefs.getString(PIN_HASH, null) ?: return false
        return hashPin(pin) == storedHash
    }

    fun clearPin() {
        prefs.edit().remove(PIN_HASH).apply()
    }

    fun authenticateWithBiometric(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        if (!isBiometricAvailable()) {
            onError("Biometrik tidak tersedia di perangkat ini")
            return
        }

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText("Gunakan PIN")
            .build()

        val biometricPrompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        onError(errString.toString())
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("Autentikasi gagal. Coba lagi.")
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }

    private fun hashPin(pin: String): String {
        return try {
            val key = getOrCreateSecretKey()
            val mac = Mac.getInstance(HMAC_ALGORITHM)
            mac.init(key)
            val hash = mac.doFinal(pin.toByteArray(StandardCharsets.UTF_8))
            hash.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            pin.hashCode().toString()
        }
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val existingKey = keyStore.getKey(KEY_ALIAS, null)
        if (existingKey is SecretKey) return existingKey

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        keyGenerator.init(
            KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setDigests(KeyProperties.DIGEST_SHA256)
                .build()
        )
        return keyGenerator.generateKey()
    }

    suspend fun encryptData(data: String): String = withContext(Dispatchers.IO) {
        try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
            val iv = cipher.iv
            val encrypted = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
            iv.joinToString(":") { "%02x".format(it) } + ":" + encrypted.joinToString(":") { "%02x".format(it) }
        } catch (e: Exception) {
            data
        }
    }

    suspend fun decryptData(encryptedData: String): String = withContext(Dispatchers.IO) {
        try {
            val parts = encryptedData.split(":")
            val iv = parts.take(12).map { it.toInt(16).toByte() }.toByteArray()
            val encrypted = parts.drop(12).map { it.toInt(16).toByte() }.toByteArray()

            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), spec)
            String(cipher.doFinal(encrypted), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            encryptedData
        }
    }

    companion object {
        private const val PREFS_NAME = "security_prefs"
        private const val PIN_HASH = "pin_hash"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "nyatetduwit_security_key"
        private const val HMAC_ALGORITHM = "HmacSHA256"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }
}
