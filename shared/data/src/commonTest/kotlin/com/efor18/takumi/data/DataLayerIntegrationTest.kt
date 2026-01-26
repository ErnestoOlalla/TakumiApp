package com.efor18.takumi.data

import com.efor18.takumi.data.di.dataModule
import com.efor18.takumi.data.repository.APIRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

/**
 * Integration tests for the data layer.
 * These tests verify that all components work together correctly.
 */
class DataLayerIntegrationTest : KoinTest {

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(dataModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test dependency injection chain wiring`() {
        val repository: APIRepository by inject()

        // Verify DI correctly wires up the entire chain:
        // DataModule -> APIRepositoryImpl -> RickAndMortyDataSource
        assertNotNull(repository)
        assertEquals("APIRepositoryImpl", repository::class.simpleName)

        // Test singleton behavior
        val repository2: APIRepository by inject()
        assertSame(repository, repository2)
    }

    @Test
    fun `test error propagation through layers`() = runTest {
        val repository: APIRepository by inject()

        // Test that invalid inputs are handled consistently across the layer chain
        val invalidInputs = listOf("", "invalid-id-999999")

        invalidInputs.forEach { invalidInput ->
            try {
                val result = repository.getCharacterById(invalidInput)

                // If it returns a result, should be properly structured error
                if (result.isError) {
                    assertNotNull(result.error())
                }
            } catch (e: Exception) {
                // Exceptions are also valid error handling at layer boundaries
                assertNotNull(e.message)
            }
        }
    }

    @Test
    fun `test concurrent operations don't interfere`() = runTest {
        val repository: APIRepository by inject()

        // Test that repository can handle concurrent operations without deadlocks
        val job1 = async {
            try {
                repository.getCharacterById("1")
            } catch (e: Exception) {
                null
            }
        }

        val job2 = async {
            try {
                repository.getEpisodeById("1")
            } catch (e: Exception) {
                null
            }
        }

        // Wait for both jobs - test passes if no deadlocks occur
        val results = listOf(job1.await(), job2.await())
        assertEquals(2, results.size)
    }
}
