package kr.co.tjoeun.colosseum_20200716

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kr.co.tjoeun.colosseum_20200716.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SetValues()
        SetupEvents()
    }

    override fun SetValues() {

    }

    override fun SetupEvents() {

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

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
                    val responseMsg = json.getString("message")

                    if (codeNum==200){
//                        로그인 성공
                    }
                    else{
//                        로그인 실패 => 토스트로 실패했다고 출력하자.
//                        어떤 이유로 실패했는지 버서가 주는 메시지를 출력
                        runOnUiThread {
                            Toast.makeText(mContext,responseMsg,Toast.LENGTH_SHORT).show()
                        }

                    }


                }

            })


        }

    }


}