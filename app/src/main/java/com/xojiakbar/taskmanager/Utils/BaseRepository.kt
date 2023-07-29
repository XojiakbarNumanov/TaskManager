package uz.furorprogress.domain.base

import android.content.Context
import androidx.core.util.Consumer
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Utils
import com.xojiakbar.taskmanager.api.ApiClient
import com.xojiakbar.taskmanager.api.result.ErrorResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

/**
 * @param <ApiService> - API service interface
 * @version 2
</ApiService> */
abstract class BaseRepository<ApiService>(private val _context: Context) {
    private var _retrofitClient: Retrofit? = null

    protected abstract val apiService: Class<ApiService>?
    val api = ApiClient.apiService

    fun <T> getApi(service: Class<T>?): T {
        if (_retrofitClient == null) _retrofitClient = ApiClient.initClient()
        return _retrofitClient!!.create(service)
    }

    fun <T> request(
        call: Call<T>,
        success: Consumer<T>,
        error: Consumer<ErrorResult>
    ) {
        call.enqueue(object : Callback<T?> {
            override fun onResponse(
                call: Call<T?>,
                response: Response<T?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    success.accept(response.body())
                } else {
                    var errorResult = ErrorResult(-1, -1, "", "")
                    try {
                        if (response.errorBody() != null) errorResult = Utils.errorParser(
                            response.errorBody()!!
                        )!!
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (errorResult.code !== -9999) {
                        error.accept(errorResult)
                    } else {
                        error.accept(
                            ErrorResult(
                                Utils.getLocalStringByResId(
                                    _context,
                                    R.string.failure_to_connect
                                )
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                error.accept(
                    ErrorResult(
                        Utils.getLocalStringByResId(
                            _context,
                            R.string.failure_to_connect
                        )
                    )
                )
            }
        })
    }

    fun <ResponseModel> requestWithNullableBody(
        call: Call<ResponseModel>,
        success: Consumer<ResponseModel?>,
        error: Consumer<ErrorResult?>
    ) {
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) success.accept(response.body()) else {
                    var errorResult = ErrorResult(-1, -1, "", "")
                    try {
                        if (response.errorBody() != null) errorResult = Utils.errorParser(
                            response.errorBody()!!
                        )!!
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (errorResult.code !== -9999) {
                        error.accept(errorResult)
                    } else {
                        error.accept(
                            ErrorResult(
                                Utils.getLocalStringByResId(
                                    _context,
                                    R.string.failure_to_connect
                                )
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                error.accept(
                    ErrorResult(
                        Utils.getLocalStringByResId(
                            _context,
                            R.string.failure_to_connect
                        )
                    )
                )
            }
        })
    }
}