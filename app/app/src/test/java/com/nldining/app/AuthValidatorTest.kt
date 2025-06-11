package com.nldining.app

import org.junit.Assert.*
import org.junit.Test

class AuthValidatorTest {

    @Test
    fun validEmail_returnsTrue() {
        assertTrue(AuthValidator.isValidEmail("test@example.com"))
    }

    @Test
    fun invalidEmail_returnsFalse() {
        assertFalse(AuthValidator.isValidEmail("invalid-email"))
    }
}