package com.example.android4a.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.android4a.local.models.UserLocal

/**
 * Requests corresponding to a User into the database
 */
@Dao
interface DatabaseDaoDao {
    /**
     * Get all the users
     *
     * @return [List] of [UserLocal]
     */
    @Query("SELECT * FROM userlocal")
    fun getAll(): List<UserLocal>

    /**
     * Get a user by name]
     *
     * @param email the email's user to look for
     * @return [UserLocal] the user or [null]
     */
    @Query("SELECT * FROM userlocal WHERE email LIKE :email LIMIT 1")
    fun findByName(email: String): UserLocal?

    /**
     * Insert a user
     *
     * @param UserLocal the user to insert
     */
    @Insert
    fun insert(user: UserLocal)

    /**
     * Delete a user
     *
     * @param UserLocal the user to delete
     */
    @Delete
    fun delete(user: UserLocal)

    /**
     * Delete a user by email
     */
    @Query("DELETE FROM userlocal WHERE email = :email")
    fun deleteByEmailAndPassword(email: String)
}