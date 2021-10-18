package com.mj.deliveryapp.data.repository.restaurant.food

import com.mj.deliveryapp.data.database.dao.FoodMenuBasketDao
import com.mj.deliveryapp.data.entity.RestaurantFoodEntity
import com.mj.deliveryapp.data.network.FoodApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultRestaurantFoodRepository(
    private val foodApiService: FoodApiService,
    private val foodMenuBasketDao: FoodMenuBasketDao,
    private val ioDispatcher: CoroutineDispatcher
): RestaurantFoodRepository {

    override suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity> = withContext(ioDispatcher) {
        val response = foodApiService.getRestaurantFoods(restaurantId)

        if(response.isSuccessful) {
            response.body()?.map { it.toEntity(restaurantId) } ?: listOf()
        } else {
            listOf()
        }
    }

    override suspend fun getAllFoodMenuListInBasket(): List<RestaurantFoodEntity>  = withContext(ioDispatcher) {
        foodMenuBasketDao.getAll()
    }

    override suspend fun getFoodMenuListInBasket(restaurantId: Long): List<RestaurantFoodEntity> = withContext(ioDispatcher) {
        foodMenuBasketDao.getAllByRestaurantId(restaurantId)
    }

    override suspend fun insertFoodMenuInBasket(restaurantFoodEntity: RestaurantFoodEntity)  = withContext(ioDispatcher) {
        foodMenuBasketDao.insert(restaurantFoodEntity)
    }

    override suspend fun removeFoodMenuInBasket(foodId: String)  = withContext(ioDispatcher) {
        foodMenuBasketDao.delete(foodId)
    }

    override suspend fun clearFoodMenuListInBasket()  = withContext(ioDispatcher) {
        foodMenuBasketDao.deleteAll()
    }
}