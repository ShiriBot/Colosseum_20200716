package kr.co.tjoeun.colosseum_20200716.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {

    companion object{

//        표시할 시간을 재료로 주면 => 상황에 맞는 String으로 변환해주는 기능

//        10초 이내 : 방금 전
//        1분 이내 : ?초 전
//        1시간 이내 : ?분 전
//        12시간 이내 : ?시간 전
//        그 이상 : ?년 ?월 ? 오전/오후 ?시 ?분

        private val dateformat = SimpleDateFormat("yyyy년  M월 d일 a h시 m분")

        fun getTimeAgoFromCalendar(datetime:Calendar) : String {

//            현재 시간이 몇시인지 아아야함. => Calendar 세어 민들면 현재시간

            val now = Calendar.getInstance()
//            현재시간 - 재료로 들어오는 시간 =? 몇 ms 차이가 나는지?
//            30분 차이 : 30* 60* 1000 => 1800000

            val msDiff = now.timeInMillis - datetime.timeInMillis

//            이 값에 따라 분기

            if (msDiff <10*1000){
                return  " 방금 전"
            }
            else if (msDiff<1*60*1000){
                val second = msDiff /1000
                return "${second}초 전"
            }

            else if (msDiff<1*60*60*1000){
                val minute = msDiff /1000 /60
                return "${minute}분 전"
            }
            else if (msDiff<12*60*60*1000){
                val hour = msDiff /1000 /60 /60
                return "${hour}시간 전"
            }
            else {
                return dateformat.format(datetime.time)
            }

        }


    }


}