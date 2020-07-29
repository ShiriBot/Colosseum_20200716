package kr.co.tjoeun.colosseum_20200716.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kr.co.tjoeun.colosseum_20200716.R
import kr.co.tjoeun.colosseum_20200716.datas.Notification
import kr.co.tjoeun.colosseum_20200716.datas.Reply
import java.util.zip.Inflater

class NotificationAdapter (val mContext: Context, resId : Int, val mList : List<Notification>) : ArrayAdapter<Notification>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var temprow = convertView

        if (temprow==null){
            temprow=inf.inflate(R.layout.notification_list_item,null)
        }

        val row = temprow!!

        val notificationTxt = row.findViewById<TextView>(R.id.notificationTxt)
        val notificationTimeTxt = row.findViewById<TextView>(R.id.notificationTimeTxt)

        val data = mList[position]

        notificationTxt.text = data.title

        return row
    }


}