package com.example.android4a.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android4a.domain.entity.User

/**
 * Entity of a user corresponding to the database
 *
 * @param email email of the user
 * @param password password of the user
 */
@Entity
data class UserLocal (
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
){
    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}

/**
 * Function to convert a [User] to [UserLocal]
 */
fun User.toData() : UserLocal {
    return UserLocal(
        email = this.email,
        password = this.password
    )
}

/**
 * Function to convert a [UserLocal] to a [User]
 */
fun UserLocal.toEntity() : User? {
    return User(
        email = email,
        password = password
    )
}