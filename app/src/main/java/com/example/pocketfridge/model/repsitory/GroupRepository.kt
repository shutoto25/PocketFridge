package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.data.Group
import com.example.pocketfridge.model.response.GroupResponse
import retrofit2.Response

class GroupRepository {

    /** インスタンス. */
    companion object Factory {
        val instance: GroupRepository
            @Synchronized get() {
                return GroupRepository()
            }

        /** end point. */
        private const val END_POINT = "https://server-side-fridge-api.herokuapp.com/"
    }

    /** . */
    suspend fun post(group: Group): Response<GroupResponse> =
        ApiClientManager().getApiClient(END_POINT).signGroup(group)

    /** 削除. */
    suspend fun delete(groupId: String): Response<GroupResponse> =
        ApiClientManager().getApiClient(END_POINT).deleteGroup(groupId)
}