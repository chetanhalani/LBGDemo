package com.lbgdemo.data.remote.base

import com.lbgdemo.data.model.DataResponse
import retrofit2.Response

/**
 * Base class for Remote Data source
 */
abstract class BaseRemoteDataSource {

    /**
     * Method to parse the Response of API Service
     * @param T the type of Response
     * @param request
     * @return DataResponse<T> the result of the request with type T
     */
    suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): DataResponse<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                DataResponse.Success(result.body() as T)
            } else {
                DataResponse.Error("api error : " + result.errorBody().toString())
            }
        } catch (e: Throwable) {
            DataResponse.Error("Unable to connect, plz try after sometime : " + e.localizedMessage.toString())
        }
    }

}