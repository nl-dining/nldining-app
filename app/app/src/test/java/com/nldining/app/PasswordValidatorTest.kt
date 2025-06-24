package com.nldining.app

import org.junit.Assert.*
import org.junit.Test

class PasswordValidatorTest {

    @Test
    fun validPassword_returnsTrue() {
        val testPswd = "Strong1!"
        assertTrue(PasswordValidator.isValid(testPswd))
    }

    @Test
    fun tooShortPassword_returnsFalse() {
        val testPswd = "S1!"
        assertFalse(PasswordValidator.isValid(testPswd))
    }

    @Test
    fun missingUppercase_returnsFalse() {
        val testPswd = "strong1!"
        assertFalse(PasswordValidator.isValid(testPswd))
    }

    @Test
    fun missingLowercase_returnsFalse() {
        val testPswd = "STRONG1!"
        assertFalse(PasswordValidator.isValid(testPswd))
    }

    @Test
    fun missingDigit_returnsFalse() {
        val testPswd = "Strong!"
        assertFalse(PasswordValidator.isValid(testPswd))
    }

    @Test
    fun missingSpecialChar_returnsFalse() {
        val testPswd = "Strong1A"
        assertFalse(PasswordValidator.isValid(testPswd))
    }
}