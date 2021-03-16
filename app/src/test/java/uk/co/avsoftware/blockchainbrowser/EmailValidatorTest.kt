package uk.co.avsoftware.blockchainbrowser

import com.google.common.truth.Truth.assertThat
import org.apache.commons.validator.routines.EmailValidator
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(EmailValidator.getInstance().isValid("name@email.com")).isTrue()
    }
}