package com.galexdev.innovcvapptest.api

import com.galexdev.innovcvapptest.data.dataModel.User
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

/**
 * Created by GalexMP on 13/02/2022
 */
interface InnocvApiService {
    @GET("User")
    suspend fun getUsers(): MutableList<User>

    @GET("User/{id}")
    suspend fun getUserById(@Path("id") id : Long ): User

    @POST("User")
    suspend fun createUser(@Body user: User)

    @PUT("User")
    suspend fun updateUser(@Body user: User)

    @DELETE("User/{id}")
    suspend fun deleteUserById(@Path("id") id : Long)

}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://hello-world.innocv.com/api/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: InnocvApiService = retrofit.create(InnocvApiService::class.java)