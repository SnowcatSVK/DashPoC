package com.globallogic.dashpoc.db

import com.globallogic.dashpoc.db.models.ValueRealm
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.Realm

/**
 * Created by snowcat on 8.3.2018.
 */
class DbProvider(override val realm: Realm) : BaseDbProvider(){

    fun saveValue(key:String, value:Float) : Completable {
        return completable{realm -> realm.insertOrUpdate(ValueRealm(key, value))}
    }

    fun initValue(key:String, value:Float) : Completable {
        return completable{realm -> realm.insert(ValueRealm(key, value))}
    }

    fun getValue(key:String) : Flowable<ValueRealm> {
        return realm.asFlowable()
                .map { realm ->
                    realm.where(ValueRealm::class.java)
                            .equalTo("key", key).findFirst()
                }
                .map{results -> realm.copyFromRealm(results)}
    }
}