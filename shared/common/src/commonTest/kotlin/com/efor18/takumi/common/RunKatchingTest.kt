package com.efor18.takumi.common

import kotlin.test.*

class RunKatchingTest {

    @Test
    fun `test runKatching with successful operation`() {
        val value = "test"
        
        val result = value.runKatching {
            uppercase()
        }
        
        assertTrue(result.isSuccess)
        assertEquals("TEST", result.value())
        assertNull(result.error())
    }

    @Test
    fun `test runKatching with exception returns error`() {
        val value = "not a number"
        
        val result = value.runKatching {
            toInt() // This will throw NumberFormatException
        }
        
        assertTrue(result.isError)
        assertFalse(result.isSuccess)
        assertNull(result.value())
        assertEquals(ERROR_UNKNOWN, result.error())
    }

    @Test
    fun `test runKatching with complex operation`() {
        val list = listOf(1, 2, 3, 4, 5)
        
        val result = list.runKatching {
            filter { it > 2 }
                .map { it * 2 }
                .sum()
        }
        
        assertTrue(result.isSuccess)
        assertEquals(24, result.value()) // (3*2) + (4*2) + (5*2) = 6 + 8 + 10 = 24
    }

    @Test
    fun `test runKatching type transformation`() {
        val number = 123
        
        val result = number.runKatching {
            toString()
        }
        
        assertTrue(result.isSuccess)
        assertEquals("123", result.value())
    }
}
