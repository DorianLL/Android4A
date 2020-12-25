package com.example.android4a.domain.usecase

import com.example.android4a.domain.entity.User
import com.example.android4a.repository.UserRepository

/**
 * GetUserUseCase use case to get a user
 *
 * @param userRepository the user's repository
 */
class GetUserUseCase(private val userRepository: UserRepository) {
    /**
     * Get a user
     *
     * @param [email] email of the user to look for
     * @return [User]
     */
    suspend fun invoke(email: String): User? {
        return userRepository.getUser(email)
    }
}