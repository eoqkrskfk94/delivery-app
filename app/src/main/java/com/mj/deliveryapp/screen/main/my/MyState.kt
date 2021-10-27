package com.mj.deliveryapp.screen.main.my

import android.net.Uri
import androidx.annotation.StringRes
import com.mj.deliveryapp.data.entity.OrderEntity

sealed class MyState {

    object Uninitialized: MyState()

    object Loading: MyState()

    data class Login(
        val idToken: String
    ): MyState()


    sealed class Success: MyState() {

        data class Registered(
            val userName: String,
            val profileImageUrl: Uri?,
            val orderList: List<OrderEntity>
        ): Success()

        object NotRegistered: Success()

    }

    data class Error(
        @StringRes val messageId: Int,
        val e: Throwable
    ): MyState()

}