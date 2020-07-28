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
import kr.co.tjoeun.colosseum_20200716.R
import kr.co.tjoeun.colosseum_20200716.ViewReplyDetailActivity
import kr.co.tjoeun.colosseum_20200716.datas.Reply
import kr.co.tjoeun.colosseum_20200716.utils.ServerUtil
import kr.co.tjoeun.colosseum_20200716.utils.TimeUtil
import org.json.JSONObject

class ReReplyAdapter (val mContext: Context, resId : Int, val mList : List<Reply>): ArrayAdapter<Reply> (mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var temprow = convertView

        if (temprow==null){
            temprow = inf.inflate(R.layout.re_reply_list_item,null)
                    }

        val row = temprow !!

        val replyNickname = row.findViewById<TextView>(R.id.replyNickname)
        val replySide = row.findViewById<TextView>(R.id.replySide)
        val replyWriteTimeTxt = row.findViewById<TextView>(R.id.replyWriteTimeTxt)
        val replyTxt = row.findViewById<TextView>(R.id.replyTxt)

        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)

        val data = mList[position]

        replyNickname.text = data.writer.nickName
        replySide.text = "${data.selestedSide.title}"
        replyTxt.text = data.content

//        시간정보 텍스트뷰 내용 설정 => 방금 전, ?분전, ?시간 전 등등등

        replyWriteTimeTxt.text = TimeUtil.getTimeAgoFromCalendar(data.writtenDateTime)

        likeBtn.text = "좋아요 ${data.likeCount}"
        dislikeBtn.text = "싫어요 ${data.dislikeCount}"

        likeBtn.setOnClickListener {

            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id,true, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val dataobj = json.getJSONObject("data")
                    val replyobj = dataobj.getJSONObject("reply")

                    val reply = Reply.getReplyFromJson(replyobj)

                    data.likeCount = reply.likeCount
                    data.dislikeCount = reply.dislikeCount
                    val uiHandler = Handler(Looper.getMainLooper())
                    uiHandler.post {
                    notifyDataSetChanged()}
                }
            })
        }

        dislikeBtn.setOnClickListener {

            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id,false, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val dataobj = json.getJSONObject("data")
                    val replyobj = dataobj.getJSONObject("reply")

                    val reply = Reply.getReplyFromJson(replyobj)

                    data.likeCount = reply.likeCount
                    data.dislikeCount = reply.dislikeCount
                    val uiHandler = Handler(Looper.getMainLooper())
                    uiHandler.post {
                        notifyDataSetChanged()}
                }
            })
        }



        return row

    }


}