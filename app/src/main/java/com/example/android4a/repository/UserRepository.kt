package com.example.android4a.repository

import com.example.android4a.domain.entity.User
import com.example.android4a.local.DatabaseDaoDao
import com.example.android4a.local.models.toData
import com.example.android4a.local.models.toEntity

/**
 * Repository of [User]
 *
 * @param databaseDaoDao the [DatabaseDaoDao]
 */
class UserRepository(private val databaseDaoDao: DatabaseDaoDao) {

    /**
     * Create a user inside the database
     *
     * @param [user] the user to insert
     * @return [Void]
     */
    fun createUser(user: User) {
        databaseDaoDao.insert(user.toData())
    }

    /**
     * Get a user inside the database with an email
     *
     * @param [email] the email of the user to look for
     * @return [User] or [null]
     */
    fun getUser(email: String): User? {
        val userLocal = databaseDaoDao.findByName(email)
        return userLocal?.toEntity()
    }
}