package uk.co.avsoftware.blockchainbrowser.util

import org.mockito.Mockito

// Easy Mocks
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)