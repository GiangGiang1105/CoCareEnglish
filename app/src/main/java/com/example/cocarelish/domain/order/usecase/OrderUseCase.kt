package com.example.cocarelish.domain.order.usecase

import com.example.cocarelish.domain.order.OrderRepository
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
){
}