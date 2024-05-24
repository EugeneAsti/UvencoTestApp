package ru.aeyu.uvencotestapp.data.source.remote

import ru.aeyu.uvencotestapp.data.source.remote.models.ProductItemRemote

interface RemoteDataSource {
    fun getRemoteData(): List<ProductItemRemote>
}