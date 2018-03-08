package com.globallogic.dashpoc.db

import io.reactivex.Completable
import io.realm.Realm

/**
 * Created by snowcat on 8.3.2018.
 */
abstract class BaseDbProvider {

    protected abstract val realm: Realm

    fun completable(query: (realm :Realm) -> Unit): Completable {

        return Completable.defer {
            val realm = realm
            try {
                realm.executeTransaction(query)
            } catch (e: Exception) {
                return@defer Completable.error(e)
            }

            return@defer Completable.complete()
        }
    }
}