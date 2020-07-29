package kr.co.tjoeun.colosseum_20200716.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Notification {

    var id = 0
    var title = ""

    val createAtCal = Calendar.getInstance()


    companion object{

//        SimpleDataFormat은 고정양식 => 한번만 만들고 재활용

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


        fun getNotificationFromJson(json:JSONObject) : Notification {

            val n = Notification()

            n.id = json.getInt("id")
            n.title = json.getString("title")

            n.createAtCal.time=sdf.parse(json.getString("created_at"))

            val myPhoneTimeZone = n.createAtCal.timeZone

            val timeOffset = myPhoneTimeZone.rawOffset / 1000 / 60 / 60

            n.createAtCal.add(Calendar.HOUR, timeOffset)

            return n
        }


    }


}