package com.example.android4a.domain.usecase

import com.example.android4a.domain.entity.User
import com.example.android4a.repository.UserRepository

/**
 * GetUserUseCase use case to create a user
 *
 * @param userRepository the user's repository
 */
class CreateUserUseCase(private val userRepository: UserRepository) {
    /**
     * Insert a user
     *
     * @param [User] to insert into the repository
     * @return [User]
     */
    suspend fun invoke(user: User) {
        userRepository.createUser(user)
    }
}