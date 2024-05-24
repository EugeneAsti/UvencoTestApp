package ru.aeyu.uvencotestapp.data.repositories.remote

import ru.aeyu.uvencotestapp.data.source.remote.RemoteDataSource

class RemoteRepository(
    private val remoteDataSource: RemoteDataSource
) {
    fun getRemoteData() = remoteDataSource.getRemoteData()
}