package com.example.cocarelish.domain.order

import com.example.cocarelish.base.BaseRepository
import com.example.cocarelish.data.essay.remote.dto.Deadline
import com.example.cocarelish.data.order.dto.Order
import com.example.cocarelish.data.order.dto.OrderResult
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.listTemplate.ItemListModel
import kotlinx.coroutines.flow.Flow

abstract class OrderRepository : BaseRepository(){
    abstract suspend fun setUpEssayOrder(order: Order): Flow<Boolean>
    abstract suspend fun getAllOrderByUserId(uid: String): Flow<Resource<List<ItemListModel>>>
    abstract suspend fun getDeadLineOrder(): Flow<Resource<List<Deadline>>>
    abstract suspend fun getOrderResultByOrderID(orderID: String): Flow<OrderResult>
}