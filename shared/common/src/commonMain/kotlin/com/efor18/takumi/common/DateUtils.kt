package com.efor18.takumi.common

/**
 * Formats an ISO date string to a human-readable format (day month year)
 * Uses simple string parsing to avoid complex datetime dependencies
 * @param isoDate ISO formatted date string (e.g., "2017-11-04T18:48:46.250Z" or "December 2, 2017")
 * @return Formatted date string (e.g., "4 November 2017") or original string if parsing fails
 */
fun formatToHumanReadableDate(isoDate: String): String {
    return try {
        // Handle already formatted dates (just pass through)
        if (!isoDate.contains("T") && !isoDate.contains("Z")) {
            return isoDate
        }
        
        // Parse ISO date format like "2017-11-04T18:48:46.250Z"
        val datePart = isoDate.split("T")[0]
        val parts = datePart.split("-")
        
        if (parts.size == 3) {
            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val day = parts[2].toInt()
            
            val monthNames = arrayOf(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            )
            
            if (month in 1..12) {
                val monthName = monthNames[month - 1]
                return "$day $monthName $year"
            }
        }
        
        isoDate // Fallback to original string if parsing fails
    } catch (e: Exception) {
        isoDate // Fallback to original string if parsing fails
    }
}

/**
 * Formats an ISO date string to a short format (MMM dd, yyyy)
 * @param isoDate ISO formatted date string
 * @return Formatted date string (e.g., "Nov 04, 2017") or original string if parsing fails
 */
fun formatToShortDate(isoDate: String): String {
    return try {
        // Handle already formatted dates
        if (!isoDate.contains("T") && !isoDate.contains("Z")) {
            return isoDate
        }
        
        // Parse ISO date format
        val datePart = isoDate.split("T")[0]
        val parts = datePart.split("-")
        
        if (parts.size == 3) {
            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val day = parts[2].toInt()
            
            val monthAbbreviations = arrayOf(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            )
            
            if (month in 1..12) {
                val monthAbbr = monthAbbreviations[month - 1]
                return "$monthAbbr ${day.toString().padStart(2, '0')}, $year"
            }
        }
        
        isoDate // Fallback to original string if parsing fails
    } catch (e: Exception) {
        isoDate // Fallback to original string if parsing fails
    }
}
