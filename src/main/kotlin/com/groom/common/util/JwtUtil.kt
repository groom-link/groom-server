package com.groom.common.util

import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import java.io.File
import java.io.FileOutputStream
import java.security.KeyPair

internal object JwtUtil {
    private const val JWT_KEY_FILE_NAME = "key"
    const val JWT_PUBLIC_KEY_FILE_NAME = "$JWT_KEY_FILE_NAME.pub"
    const val JWT_PRIVATE_KEY_FILE_NAME = JWT_KEY_FILE_NAME
    fun saveKeyPairFileIfNotExists() {
        val privateKeyFile = File(JWT_PRIVATE_KEY_FILE_NAME)
        val publicKeyFile = File(JWT_PUBLIC_KEY_FILE_NAME)
        if (!privateKeyFile.exists() || !publicKeyFile.exists()) {
            val keyPair: KeyPair = generateNewKeyPair()
            saveKeyPairFile(keyPair)
        }
    }

    private fun generateNewKeyPair(): KeyPair {
        val rsaKeyGenerator = RSAKeyGenerator(RSAKeyGenerator.MIN_KEY_SIZE_BITS)
        val generated: RSAKey = rsaKeyGenerator.generate()
        return KeyPair(generated.toPublicKey(), generated.toPrivateKey())
    }


    private fun saveKeyPairFile(keyPair: KeyPair) {
        val privateKey = keyPair.private
        val publicKey = keyPair.public
        FileOutputStream(JWT_PRIVATE_KEY_FILE_NAME).use { fos ->
            fos.write(privateKey.encoded)
        }
        FileOutputStream(JWT_PUBLIC_KEY_FILE_NAME).use { fos ->
            fos.write(publicKey.encoded)
        }
    }
}

