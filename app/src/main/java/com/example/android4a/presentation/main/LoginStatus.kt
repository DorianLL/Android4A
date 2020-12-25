package com.example.android4a.presentation.main

/**
 * Login status sealed class
 */
sealed class LoginStatus

/**
 * In case of Loggin success
 *
 * @param email the successfully logged user's email
 * @return [LoginStatus]
 */
data class LoginSuccess(val email: String) : LoginStatus()

/**
 * In case of failed logging
 *
 * @return [LoginStatus]
 */
object LoginError : LoginStatus()