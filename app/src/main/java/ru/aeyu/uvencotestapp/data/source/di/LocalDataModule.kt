package ru.aeyu.uvencotestapp.data.source.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aeyu.uvencotestapp.data.source.local.DB_NAME
import ru.aeyu.uvencotestapp.data.source.local.ItemSettingsDao
import ru.aeyu.uvencotestapp.data.source.local.UvencoDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDetailsDao(
        db: UvencoDB
    ): ItemSettingsDao {
        return db.detailsDao()
    }

    @Provides
    @Singleton
    fun provideRoomDataBase(
        @ApplicationContext context: Context
    ): UvencoDB {
        return Room.databaseBuilder(
            context,
            UvencoDB::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }
}
