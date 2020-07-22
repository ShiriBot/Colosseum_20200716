package kr.co.tjoeun.colosseum_20200716.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kr.co.tjoeun.colosseum_20200716.R
import kr.co.tjoeun.colosseum_20200716.datas.Reply

class ReplyAdapter (val mcontext: Context , resId : Int, val mList : List<Reply>): ArrayAdapter<Reply> (mcontext, resId, mList) {

    val inf = LayoutInflater.from(mcontext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var temprow = convertView

        if (temprow==null){
            temprow = inf.inflate(R.layout.reply_list_item,null)
                    }

        val row = temprow !!

        return row


    }

}