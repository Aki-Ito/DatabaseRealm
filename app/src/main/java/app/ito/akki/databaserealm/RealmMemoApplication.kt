package app.ito.akki.databaserealm

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmMemoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        //Realmの初期化処理
        Realm.init(this)
        //開発を効率化するための設定
        //データベースに保存するモデルに変更を加えた時,
        //アプリを削除して再インストールする手間を省くことができる
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            . build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}