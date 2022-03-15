package com.example.cocarelish.data.order.repository

import android.util.Log
import com.example.cocarelish.data.authentication.repository.AuthRepositoryImpl
import com.example.cocarelish.data.essay.remote.dto.Deadline
import com.example.cocarelish.data.order.dto.DetailResult
import com.example.cocarelish.data.order.dto.Order
import com.example.cocarelish.data.order.dto.OrderResult
import com.example.cocarelish.domain.essay.usecase.EssayOfSystemUseCase
import com.example.cocarelish.domain.order.OrderRepository
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.const.FireBaseConst
import com.example.cocarelish.utils.listTemplate.ItemListModel
import com.example.cocarelish.utils.listTemplate.ItemListType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class OrderRepositoryImpl @Inject constructor(
    private val firebaseStore: FirebaseFirestore,
    private val essayOfSystemUseCase: EssayOfSystemUseCase
) : OrderRepository() {
    companion object {
        const val STATUS_DONE = 1
    }

    override suspend fun setUpEssayOrder(order: Order): Flow<Boolean> {
        return callbackFlow {
            withContext(Dispatchers.IO) {
                firebaseStore.collection(FireBaseConst.COLLECTION_ORDER).document()
                    .set(order).addOnCompleteListener {
                        trySend(it.isSuccessful)
                    }.addOnFailureListener {
                        Log.d(AuthRepositoryImpl.TAG, "setUpUserInformation: ${it.message}")
                    }
            }
            awaitClose { }
        }
    }

    override suspend fun getOrderResultByOrderID(orderID: String): Flow<OrderResult> {
        return callbackFlow {
            val list = mutableListOf<OrderResult>()
            firebaseStore.collection(FireBaseConst.COLLECTION_ORDER_RESULT)
                .whereEqualTo("order_id", orderID).get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        document.toObject(OrderResult::class.java)?.let { it1 ->
                            list.add(it1)
                        }
                    }
                    if(list.isNotEmpty()){
                        trySend(list[0])
                    }
                }
            awaitClose { }
        }
    }

    override suspend fun getDetailResultByID(detailResultID: String): Flow<DetailResult> {
        return callbackFlow {
            val lista = mutableListOf<DetailResult>()
            firebaseStore.collection(FireBaseConst.COLLECTION_DETAIL_RESULT)
                .whereEqualTo("id", detailResultID).get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        document.toObject(DetailResult::class.java)?.let { it1 ->
                            lista.add(it1)
                        }
                    }
                    trySend(lista[0])
                }
            awaitClose { }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    override suspend fun getAllOrderByUserId(uid: String): Flow<Resource<List<ItemListModel>>> {
        return callbackFlow {
            val list = mutableListOf<ItemListModel>()
            firebaseStore.collection(FireBaseConst.COLLECTION_ORDER).whereEqualTo("uid", uid).get()
                .addOnSuccessListener { it ->
                    for (document in it.documents) {
                        document.toObject(Order::class.java)?.let { order ->
                            CoroutineScope(Dispatchers.IO).launch {
                                essayOfSystemUseCase.getEssayByID(order.essay_id)
                                    .collect { result ->
                                        val item = ItemListModel(
                                            itemListType = ItemListType.ITEM_LIST_MY_ESSAY,
                                            status = order.status_id,
                                            orderId = order.id,
                                            question_of_test = result.question,
                                            content = order.content,
                                            type_name = getTypeEssayByTopicID(result.topic_id),
                                            teacher_name = "Nguyễn Ngọc Hà Giang",
                                        )
                                        list.add(
                                            item
                                        )
                                        if (order.status_id == STATUS_DONE) {
                                            getOrderResultByOrderID(order.id).collect { orderResult ->
                                                list.remove(item)
                                                item.score = orderResult.score
                                                list.add(item)
                                                trySend(Resource.Success(list.toList()))
                                            }
                                        }

                                        trySend(Resource.Success(list.toList()))

                                    }
                            }
                        }
                    }
                }
            awaitClose { }
        }
    }

    override suspend fun getOrderByOrderId(orderID: String): Flow<Order> {
        return callbackFlow {
            val list = mutableListOf<Order>()
            firebaseStore.collection(FireBaseConst.COLLECTION_ORDER).whereEqualTo("id", orderID)
                .get().addOnSuccessListener {
                for (document in it.documents) {
                    document.toObject(Order::class.java)?.let { it1 ->
                        list.add(it1)
                    }
                }
                trySend(list[0])
            }
            awaitClose { }
        }
    }

    private fun getTypeEssayByTopicID(topicID: Int): String {
        return when (topicID) {
            in 0..4 -> {
                Title.NORMAL
            }
            in 5..11 -> {
                Title.IELTS_WRITING_TASK_1
            }
            else -> {
                Title.IELTS_WRITING_TASK_2
            }
        }
    }

    override suspend fun getDeadLineOrder(): Flow<Resource<List<Deadline>>> {
        return callbackFlow {
            val list = mutableListOf<Deadline>()
            firebaseStore.collection(FireBaseConst.COLLECTION_DEADLINE).get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        document.toObject(Deadline::class.java)?.let { it1 ->
                            list.add(it1)
                        }
                    }
                    trySend(Resource.Success(list.toList()))
                }
            awaitClose { }
        }
    }
}