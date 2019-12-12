package `in`.iceberg.remote.service

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DoctorsApiServiceFactory {

    private const val API_BASE_URL = "https://vivy.com"
    private const val TIME_OUT = 120L

    fun geDoctorsListApiService(): DoctorsListService {
        val okHttpClient = makeOkHttpClient(makeHttpLoggingInterceptor())
        return makeDoctorsApiService(okHttpClient)
    }

    private fun makeDoctorsApiService(okHttpClient: OkHttpClient): DoctorsListService {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(DoctorsListService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor):
            OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor).connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS).build()

    private fun makeHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}