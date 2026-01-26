package com.efor18.takumi.data.di

import com.efor18.takumi.data.repository.APIRepository
import com.efor18.takumi.data.repository.APIRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

class DataModuleTest : KoinTest {

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
    fun `test dataModule provides singleton APIRepository implementation`() {
        val repository1: APIRepository by inject()
        val repository2: APIRepository by inject()
        
        // Verify correct implementation is provided
        assertNotNull(repository1)
        assertTrue(repository1 is APIRepositoryImpl)
        assertEquals("APIRepositoryImpl", repository1::class.simpleName)
        
        // Verify singleton behavior
        assertSame(repository1, repository2)
    }
}
