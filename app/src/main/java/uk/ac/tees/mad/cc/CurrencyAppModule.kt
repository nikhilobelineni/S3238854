package uk.ac.tees.mad.cc

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.cc.data.CurrencyApiService
import uk.ac.tees.mad.cc.data.CurrencyDao
import uk.ac.tees.mad.cc.data.CurrencyHistoryDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.currencyapi.com/v3/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApiService(retrofit: Retrofit): CurrencyApiService {
        return retrofit.create(CurrencyApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthentication() : FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesFirestore() : FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesFirebaseStorage() : FirebaseStorage = Firebase.storage


    @Singleton
    @Provides
    fun provideCurrencyHistoryDatabase(@ApplicationContext context: Context): CurrencyHistoryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CurrencyHistoryDatabase::class.java,
            "currency_history_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(database: CurrencyHistoryDatabase): CurrencyDao {
        return database.currencyDao()
    }

    @Singleton
    @Provides
    fun providesContext (@ApplicationContext context: Context) : Context {
        return context
    }
}