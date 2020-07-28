package kr.co.tjoeun.colosseum_20200716.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kr.co.tjoeun.colosseum_20200716.R
import kr.co.tjoeun.colosseum_20200716.ViewReplyDetailActivity
import kr.co.tjoeun.colosseum_20200716.datas.Reply
import kr.co.tjoeun.colosseum_20200716.utils.ServerUtil
import kr.co.tjoeun.colosseum_20200716.utils.TimeUtil
import org.json.JSONObject

class ReplyAdapter (val mContext: Context, resId : Int, val mList : List<Reply>): ArrayAdapter<Reply> (mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var temprow = convertView

        if (temprow==null){
            temprow = inf.inflate(R.layout.reply_list_item,null)
                    }

        val row = temprow !!

        val replyNickname = row.findViewById<TextView>(R.id.replyNickname)
        val replySide = row.findViewById<TextView>(R.id.replySide)
        val replyTxt = row.findViewById<TextView>(R.id.replyTxt)

//        시간 정보 텍스트뷰

        val replyWriteTimeTxt = row.findViewById<TextView>(R.id.replyWriteTimeTxt)

        val rereplyBtn = row.findViewById<Button>(R.id.rereplyBtn)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)

        val data = mList[position]

        replyNickname.text = data.writer.nickName
        replySide.text = "${data.selestedSide.title}"
        replyTxt.text = data.content

//        시간정보 텍스트뷰 내용 설정 => 방금 전, ?분전, ?시간 전 등등등

        replyWriteTimeTxt.text = TimeUtil.getTimeAgoFromCalendar(data.writtenDateTime)


////        날짜 출력 양식용 변수
//        val sdf = SimpleDateFormat("yy-MM-dd a h시 m분")
//
//        replyWriteTimeTxt.text = sdf.format(data.writtenDateTime.time)

        likeBtn.text = "좋아요 ${data.likeCount}"
        dislikeBtn.text = "싫어요 ${data.dislikeCount}"
        rereplyBtn.text = "답글 ${data.replyCount}"

//        내 좋아요 여부 / 내 싫어요 여부
        if (data.myLike){
//            좋아요 버튼의 배경을 => red_border_box로 변경
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            likeBtn.setTextColor(mContext.resources.getColor(R.color.naverRed))
        }
        else{
//          좋아요 버튼의 배경을 => grey_border_box로 변경
            likeBtn.setBackgroundResource(R.drawable.grey_border_box)
            likeBtn.setTextColor(mContext.resources.getColor(R.color.textGray))
        }

        if (data.myDislike){
//            좋아요 버튼의 배경을 => red_border_box로 변경
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.naverBlue))
        }
        else{
//          좋아요 버튼의 배경을 => grey_border_box로 변경
            dislikeBtn.setBackgroundResource(R.drawable.grey_border_box)
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.textGray))
        }

//        답글 버튼이 눌리면 => 의견 상세 화면으로 진입
        rereplyBtn.setOnClickListener {

            val myIntent = Intent(mContext,ViewReplyDetailActivity::class.java)
//            startActivity 함수는 AppCompatActivity가 내려주는 기능.
//            Adapter는 액티비티가 아니므로, startActivity 기능을 내려주지 않는다.
//            mContext 변수가 , 어떤 화면이 리스트뷰를 뿌리는지 들고 있다.
//            mContext를 이용해서 액티비티를 열어주자.

//            몇번 의견에 대한 상세를 보고싶은지 id만 넘겨주자
//            해당 화면에서 다시 서버를 통해 데이터를 받아오자.
            myIntent.putExtra("replyId",data.id)

            mContext.startActivity(myIntent)

        }

//        의견에 대한 좋아요 / 싫어요 버튼 클릭 이벤트

        likeBtn.setOnClickListener {

            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id,true,object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

//                    변경된 좋아요 갯수 / 싫어요 갯수를 파악해서
//                    버튼 문구를 새로 반영
//                    목록에 뿌려지는 data의 좋아요/싫어요 갯수를 변경

                    val dataObj = json.getJSONObject("data")
                    val replyobj = dataObj.getJSONObject("reply")

                    val reply = Reply.getReplyFromJson(replyobj)

//                    이미 화면에 뿌려져 있는 data의 내용만 교체
                    data.likeCount = reply.likeCount
                    data.dislikeCount = reply.dislikeCount

//                    내가 좋아요을 찍었는지? 아닌지? 다시 체크
                    data.myLike = reply.myLike
                    data.myDislike = reply.myDislike


//                    data의 값이 변경 => 리스트뷰를 구성하는 목록에 변경
//                    => 어댑터.notifyDataSetChanged 실행해야함.
//                    어댑터 내부=> notifyDataSetChanged가 내장되어 있다.
//                    단순 호출만하면 됨.

//                    새로고침도 => UI 변경 => runOnUIThread 등으로 UI 쓰레드가 처리하도록 해야함.
//                    어댑터는 runOnUIThread 기능이 내장되저 있지 않다.

//                    Handler를 이용해서 => UI 쓰레드에 접근하게 하자.

                    val uiHandler = Handler(Looper.getMainLooper())

                    uiHandler.post {

                        notifyDataSetChanged()

//                        서버가 알려주는 메세지를 토스르로 출력
                        val message = json.getString("message")

                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()


                    }



                }

            })

        }

//        싫어요 버튼 누르면 => 서버에 전달 처리 + 갯수 반영 / 토스트로 출력

        dislikeBtn.setOnClickListener {

            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id,false,object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val dataobj = json.getJSONObject("data")
                    val replyobj = dataobj.getJSONObject("reply")

                    val reply = Reply.getReplyFromJson(replyobj)

                    data.likeCount = reply.likeCount
                    data.dislikeCount = reply.dislikeCount

                    data.myLike = reply.myLike
                    data.myDislike = reply.myDislike

                    val uiHandler = Handler(Looper.getMainLooper())

                    uiHandler.post{

                        notifyDataSetChanged()

//                        서버가 알려주는 메세지를 토스르로 출력
                        val message = json.getString("message")

                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()


                    }

                }
            })


        }



        return row

    }

}