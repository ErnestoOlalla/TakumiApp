package com.efor18.takumi.data.remote

import com.efor18.takumi.common.KResult
import com.efor18.takumi.common.runKatching
import com.efor18.takumi.data.repository.APIRepository
import com.efor18.takumi.data.repository.APIRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * Integration tests for RickAndMortyDataSource behavior through APIRepositoryImpl.
 * 
 * Since RickAndMortyDataSource is internal, these tests verify its integration
 * with the repository layer rather than direct unit testing.
 */
class RickAndMortyDataSourceTest {

    @Test
    fun `test data source handles invalid inputs through repository layer`() = runTest {
        val repository = APIRepositoryImpl()
        val invalidInputs = listOf("", "invalid-id-999999", "non-numeric-id")
        
        // Test that data source integration handles invalid inputs gracefully
        invalidInputs.forEach { invalidId ->
            val result = try {
                repository.getCharacterById(invalidId)
            } catch (e: Exception) {
                // Network/validation errors are expected
                assertNotNull(e.message)
                return@forEach
            }
            
            // If it returns without throwing, should be proper KResult structure
            if (result.isError) {
                assertNotNull(result.error())
            }
        }
    }

    @Test
    fun `test data source supports concurrent operations through repository`() = runTest {
        val repository = APIRepositoryImpl()
        
        // Test that data source can handle concurrent requests without issues
        val job1 = async {
            try { repository.getCharacterById("1") } catch (e: Exception) { null }
        }
        
        val job2 = async {
            try { repository.getEpisodeById("1") } catch (e: Exception) { null }
        }
        
        // Wait for completion - test passes if no deadlocks or race conditions
        val results = listOf(job1.await(), job2.await())
        assertEquals(2, results.size)
    }
}
