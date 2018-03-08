package com.wenping.playerproject.ui.fragment

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.database.Cursor
import android.graphics.Color
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseFragment
import com.wenping.playerproject.util.CursorUtil

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class VBangFragment : BaseFragment() {
//    val handler = object : Handler() {
//        override fun handleMessage(msg: Message?) {
//            msg?.let {
//                val cusor: Cursor = it.obj as Cursor
//                CursorUtil.logCursor(cusor)
//            }
//        }
//    }

    override fun initView(): View {
        return View.inflate(context, R.layout.fragment_vbang, null)
    }

    override fun initData() {
        val resolver = context?.contentResolver
        //加载音乐列表数据
        //创建线程 不好
//        Thread(object :Runnable{
//            override fun run() {
//                val cursor = resolver?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                        arrayOf(MediaStore.Audio.Media.DATA,
//                                MediaStore.Audio.Media.SIZE,
//                                MediaStore.Audio.Media.DISPLAY_NAME,
//                                MediaStore.Audio.Media.ARTIST),
//                        null, null, null)
//                val msg:Message = Message.obtain()
//                msg.obj = cursor
//                handler.sendMessage(msg)
//            }
//        }).start()

        //改用asyncTask
//        AudioTask().execute(resolver)
        //asyncQueryHandler来查询
        val handler =object :AsyncQueryHandler(resolver){
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                //查询完成的回调 主线程中

            }
        }
        //开始查询
        handler.startQuery(0,null,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        arrayOf(MediaStore.Audio.Media.DATA,
                                MediaStore.Audio.Media.SIZE,
                                MediaStore.Audio.Media.DISPLAY_NAME,
                                MediaStore.Audio.Media.ARTIST),
                        null, null, null)
    }

    class AudioTask : AsyncTask<ContentResolver, Void, Cursor>() {
        //后台执行的任务
        override fun doInBackground(vararg p0: ContentResolver?): Cursor? {

            val cursor = p0[0]?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.ARTIST),
                    null, null, null)
            return cursor
        }

        /**
         * 后台任务的结果回调到主线程中
         */
        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            CursorUtil.logCursor(result)
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)

        }
    }
}