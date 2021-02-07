package app.ito.akki.databaserealm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Realmの変数を宣言する
    //今後この変数を使ってデータベースを操作する
    val realm: Realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //変数memoに取得したデータを代入
        val memo: Memo? = read()

        if(memo != null){
            titleEditText.setText(memo.title)
            contentEditText.setText(memo.content)
        }
        //保存ボタンを押した時、titleEditTextとcontentEditTextに入力されたテキストを取得し,
        //save()メソッドに値を渡す
        saveButton.setOnClickListener {
            val title: String = titleEditText.text.toString()
            val content: String = contentEditText.text.toString()
            //save()というメソッドに引数として渡す
            save(title, content)
        }
    }
    //以下のコードを記述することでパフォーマンスが良くなる
    //onDestroy()は画面が終了した時に実行される部分
    override fun onDestroy() {
        super.onDestroy()
        //画面終了時にRealmを閉じる
        //Realmを終了する
        realm.close()
    }

    //返り値の型に?がついているのは返ってくるデータがnullになる可能性があるため
    fun read(): Memo? {
        //データベース中のMemoリストから最初のデータを一つ取り出している
        return realm.where(Memo::class.java).findFirst()
    }

    fun save(title: String, content: String){
        //すでに保存されているメモを取得
        val memo: Memo? = read()
        //この中でデータベースの書き込みをする
        realm.executeTransaction{
            if(memo != null){
                memo.title = title
                memo.content = content
            } else {
                //メモの新規作成
                //realm.executeTransaction内だとitをrealmの変数として扱う
                val newMemo: Memo = it.createObject(Memo::class.java)
                newMemo.title = title
                newMemo.content = content

            }
        }
        //一つ目の引数は表示するViewなので画面全体のベースとなっているConstraintLayoutのid
        //三つ目はSnackBarを表示する長さを指定
        Snackbar.make(container, "保存しました!!", Snackbar.LENGTH_SHORT).show()
    }
}