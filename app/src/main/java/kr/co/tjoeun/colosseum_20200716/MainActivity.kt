package kr.co.tjoeun.colosseum_20200716

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.colosseum_20200716.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SetValues()
        SetupEvents()
    }

    override fun SetValues() {

    }

    override fun SetupEvents() {

        loginBtn.setOnClickListener {
//            입력한 아이디와 비번을 받아서
            val inputEmail = emailEdt.text.toString()
            val inputpw = pwEdt.text.toString()

//            서버에 전달해주고 응답처리
            ServerUtil.postRequestLogin(mContext,inputEmail,inputpw, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

//                    로그인 성공 / 실패 여부 => code에 있는 Int값으로 구별.
//                    200 : 로그인 성곤
//                    그 외의 숫자 : 로그인 실패

                    val codeNum = json.getInt("code")

                    if (codeNum==200){
//                        로그인 성공
                    }
                    else{
//                        로그인 실패 => 토스트로 실패했다고 출력하자.
                        runOnUiThread {
                            Toast.makeText(mContext,"로그인 실패", Toast.LENGTH_SHORT).show()
                        }

                    }


                }

            })


        }

    }


}