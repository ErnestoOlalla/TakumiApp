package com.efor18.takumi.common

import kotlin.test.*

class DateUtilsTest {

    @Test
    fun `test formatToHumanReadableDate with valid ISO date`() {
        val isoDate = "2017-11-04T18:48:46.250Z"
        val expected = "4 November 2017"
        
        val result = formatToHumanReadableDate(isoDate)
        
        assertEquals(expected, result)
    }

    @Test
    fun `test formatToHumanReadableDate with single digit day and month`() {
        val singleDigitDate = "2023-01-05T12:00:00Z"
        val expected = "5 January 2023"
        
        val result = formatToHumanReadableDate(singleDigitDate)
        
        assertEquals(expected, result)
    }

    @Test
    fun `test formatToHumanReadableDate with invalid input returns original`() {
        val invalidInputs = listOf(
            "December 2, 2017", // Already formatted
            "not-a-date", // Invalid format
            "2023-13-25T12:00:00Z" // Invalid month
        )
        
        invalidInputs.forEach { input ->
            assertEquals(input, formatToHumanReadableDate(input))
        }
    }

    @Test
    fun `test formatToShortDate with valid ISO date`() {
        val isoDate = "2017-11-04T18:48:46.250Z"
        val expected = "Nov 04, 2017"
        
        val result = formatToShortDate(isoDate)
        
        assertEquals(expected, result)
    }

    @Test
    fun `test formatToShortDate with single digit day`() {
        val singleDigitDay = "2023-05-05T12:00:00Z"
        val expected = "May 05, 2023"
        
        val result = formatToShortDate(singleDigitDay)
        
        assertEquals(expected, result)
    }

    @Test
    fun `test formatToShortDate with invalid input returns original`() {
        val invalidInputs = listOf(
            "December 2, 2017", // Already formatted
            "not-a-date", // Invalid format
            "2023-00-15T12:00:00Z" // Invalid month
        )
        
        invalidInputs.forEach { input ->
            assertEquals(input, formatToShortDate(input))
        }
    }

    @Test
    fun `test both functions with leap year date`() {
        val leapYearDate = "2024-02-29T12:00:00Z"
        
        assertEquals("29 February 2024", formatToHumanReadableDate(leapYearDate))
        assertEquals("Feb 29, 2024", formatToShortDate(leapYearDate))
    }
}
