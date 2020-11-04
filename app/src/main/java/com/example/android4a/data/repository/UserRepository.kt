package com.example.android4a.data.repository

import com.example.android4a.domain.entity.User
import com.example.android4a.local.DatabaseDaoDao
import com.example.android4a.local.models.toData
import com.example.android4a.local.models.toEntity

class UserRepository(private val databaseDaoDao: DatabaseDaoDao) {

    suspend fun createUser(user: User){
        databaseDaoDao.insert(user.toData())
    }

    fun getUser(email: String): User? {
        val userLocal = databaseDaoDao.findByName(email)
        return userLocal.toEntity()
    }
}