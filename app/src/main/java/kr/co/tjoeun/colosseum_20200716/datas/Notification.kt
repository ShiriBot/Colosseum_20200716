package kr.co.tjoeun.colosseum_20200716.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Notification {

    var id = 0
    var title = ""

    val createAtCal = Calendar.getInstance()


    companion object{

        fun getNotificationFromJson(json:JSONObject) : Notification {

            val n = Notification()

            n.id = json.getInt("id")
            n.title = json.getString("title")

//            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            val createdAtString = json.getString("created_at")

            return n
        }


    }


}