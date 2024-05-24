package ru.aeyu.uvencotestapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.aeyu.uvencotestapp.data.source.local.entities.ItemSettingsEntity

@Database(
    entities = [
        ItemSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UvencoDB : RoomDatabase() {
    abstract fun detailsDao(): ItemSettingsDao
}

const val DB_NAME = "uvenco.db"
