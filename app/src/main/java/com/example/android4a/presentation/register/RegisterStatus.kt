package com.example.android4a.presentation.register

/**
 * Register status sealed class
 */
sealed class RegisterStatus

/**
 * In case of Loggin success
 *
 * @param email the successfully registered user's email
 * @return [RegisterStatus]
 */
data class RegisterSuccess(val email: String) : RegisterStatus()

/**
 * In case of failed registering
 *
 * @return [RegisterStatus]
 */
object RegisterError : RegisterStatus()