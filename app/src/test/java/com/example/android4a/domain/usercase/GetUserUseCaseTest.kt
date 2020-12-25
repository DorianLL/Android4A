package com.example.android4a.domain.usercase

import com.example.android4a.repository.UserRepository
import com.example.android4a.domain.entity.User
import com.example.android4a.domain.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetUserUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val classUnderTest = GetUserUseCase(userRepository)

    @Test
    fun `invoke with invalid email`() {
        runBlocking {
            val email = "test@test.fr"
            coEvery { userRepository.getUser(email) } returns null
            val result = classUnderTest.invoke(email)
            assertEquals(result, null)
        }
    }

    @Test
    fun `invoke with valid email`() {
        runBlocking {
            val email = "test@test.fr"
            val expectedUserReturn = User(email, "123456")
            coEvery { userRepository.getUser(email) } returns expectedUserReturn

            val result = classUnderTest.invoke(email)

            coVerify(exactly = 1) {userRepository.getUser(email)}
            assertEquals(result, expectedUserReturn)
        }
    }
}