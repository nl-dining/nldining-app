package com.nldining.app

import org.junit.Assert.*
import org.junit.Test

class PasswordValidatorTest {

    @Test
    fun validPassword_returnsTrue() {
        val password = "Strong1!"
        assertTrue(PasswordValidator.isValid(password))
    }

    @Test
    fun tooShortPassword_returnsFalse() {
        val password = "S1!"
        assertFalse(PasswordValidator.isValid(password))
    }

    @Test
    fun missingUppercase_returnsFalse() {
        val password = "strong1!"
        assertFalse(PasswordValidator.isValid(password))
    }

    @Test
    fun missingLowercase_returnsFalse() {
        val password = "STRONG1!"
        assertFalse(PasswordValidator.isValid(password))
    }

    @Test
    fun missingDigit_returnsFalse() {
        val password = "Strong!"
        assertFalse(PasswordValidator.isValid(password))
    }

    @Test
    fun missingSpecialChar_returnsFalse() {
        val password = "Strong1A"
        assertFalse(PasswordValidator.isValid(password))
    }
}