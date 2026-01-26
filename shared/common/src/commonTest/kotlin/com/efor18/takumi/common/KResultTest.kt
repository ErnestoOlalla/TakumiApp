package com.efor18.takumi.common

import kotlin.test.*

class KResultTest {

    @Test
    fun `test KResult success creation and access`() {
        val value = "test value"
        val result = KResult.success(value)
        
        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        assertEquals(value, result.value())
        assertNull(result.error())
    }

    @Test
    fun `test KResult error creation with parameters`() {
        val throwable = RuntimeException("Test exception")
        val result = KResult.error(KErrorType.NO_NETWORK, "Network error", throwable)
        
        assertTrue(result.isError)
        assertFalse(result.isSuccess)
        assertNull(result.value())
        
        val error = result.error()
        assertNotNull(error)
        assertEquals(KErrorType.NO_NETWORK, error.type)
        assertEquals("Network error", error.message)
        assertEquals(throwable, error.throwable)
    }

    @Test
    fun `test KResult map function with Success`() {
        val originalValue = 5
        val result = KResult.success(originalValue)
        
        val mappedResult = with(result) { map { it * 2 } }
        
        assertTrue(mappedResult.isSuccess)
        assertEquals(10, mappedResult.value())
    }

    @Test
    fun `test KResult map function with Error propagates error`() {
        val error = KError(KErrorType.NO_NETWORK, "Network error")
        val result: KResult<Int> = KResult.error(error)
        
        val mappedResult = with(result) { map { it * 2 } }
        
        assertTrue(mappedResult.isError)
        assertEquals(error, mappedResult.error())
    }

    @Test
    fun `test KResult map function type transformation`() {
        val result = KResult.success("123")
        
        val mappedResult = with(result) { map { it.toInt() } }
        
        assertTrue(mappedResult.isSuccess)
        assertEquals(123, mappedResult.value())
    }

    @Test
    fun `test ERROR_UNKNOWN constant`() {
        val result = KResult.unknownError()
        
        assertTrue(result.isError)
        assertEquals(ERROR_UNKNOWN, result.error())
        assertEquals(KErrorType.UNKNOWN, ERROR_UNKNOWN.type)
        assertEquals("Unknown Error", ERROR_UNKNOWN.message)
    }
}
