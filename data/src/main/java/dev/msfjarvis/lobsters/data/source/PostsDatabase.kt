package dev.msfjarvis.lobsters.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.msfjarvis.lobsters.data.model.LobstersEntity
import dev.msfjarvis.lobsters.model.LobstersPost

@Database(
  entities = [
    LobstersEntity::class,
  ],
  version = 1,
  exportSchema = false,
)
@TypeConverters(
  LobstersApiTypeConverters::class,
  DateTimeTypeConverters::class,
)
abstract class PostsDatabase : RoomDatabase() {
  abstract fun postsDao(): PostsDao
}
