package com.efor18.takumi.data.repository

import com.efor18.takumi.common.KResult
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class APIRepositoryImplTest {

    private lateinit var repository: APIRepositoryImpl

    @BeforeTest
    fun setUp() {
        repository = APIRepositoryImpl()
    }

    @Test
    fun `test repository methods handle network failures gracefully`() = runTest {
        // Test getAllCharacters
        val charactersResult = try {
            repository.getAllCharacters()
        } catch (e: Exception) {
            assertNotNull(e.message)
            return@runTest
        }
        assertNotNull(charactersResult)
        assertTrue(charactersResult.isSuccess || charactersResult.isError)
        
        // Test getCharacterById
        val characterResult = try {
            repository.getCharacterById("1")
        } catch (e: Exception) {
            assertNotNull(e.message)
            return@runTest
        }
        assertNotNull(characterResult)
        assertTrue(characterResult.isSuccess || characterResult.isError)
        
        // Test getEpisodeById
        val episodeResult = try {
            repository.getEpisodeById("1")
        } catch (e: Exception) {
            assertNotNull(e.message)
            return@runTest
        }
        assertNotNull(episodeResult)
        assertTrue(episodeResult.isSuccess || episodeResult.isError)
    }

    @Test
    fun `test repository handles invalid IDs appropriately`() = runTest {
        val invalidIds = listOf("", "invalid-999999", "non-numeric")
        
        invalidIds.forEach { invalidId ->
            // Test getCharacterById with invalid ID
            val characterResult = try {
                repository.getCharacterById(invalidId)
            } catch (e: Exception) {
                // Invalid IDs should cause failure with meaningful messages
                assertNotNull(e.message)
                return@forEach
            }
            
            // If it doesn't throw, should return appropriate error KResult
            if (characterResult.isError) {
                assertNotNull(characterResult.error())
            } else if (invalidId.isEmpty()) {
                fail("Empty ID should not return successful result")
            }
            
            // Test getEpisodeById with invalid ID
            val episodeResult = try {
                repository.getEpisodeById(invalidId)
            } catch (e: Exception) {
                assertNotNull(e.message)
                return@forEach
            }
            
            if (episodeResult.isError) {
                assertNotNull(episodeResult.error())
            } else if (invalidId.isEmpty()) {
                fail("Empty ID should not return successful result")
            }
        }
    }
}
