package app.ito.akki.databaserealm

import io.realm.RealmObject
//openと記述するのはRealmを使う際に必要になるから
open class Memo (
    open var title: String = "",
    open var content: String = ""
    //RealmObjectという型を継承している
) : RealmObject()
