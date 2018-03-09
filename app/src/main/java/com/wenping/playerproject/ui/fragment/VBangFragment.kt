package com.wenping.playerproject.ui.fragment

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.database.Cursor
import android.os.AsyncTask
import android.provider.MediaStore
import android.view.View
import com.wenping.playerproject.R
import com.wenping.playerproject.adapter.VbangAdapter
import com.wenping.playerproject.base.BaseFragment
import com.wenping.playerproject.util.CursorUtil
import kotlinx.android.synthetic.main.fragment_vbang.*

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
                //刷新列表
                //1.设置数据
                (cookie as VbangAdapter).swapCursor(cursor)
                //2.刷新列表
            }
        }
        //开始查询
        handler.startQuery(0,adapter,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        arrayOf(MediaStore.Audio.Media._ID,//这个是必须要查的字段
                                MediaStore.Audio.Media.DATA,
                                MediaStore.Audio.Media.SIZE,
                                MediaStore.Audio.Media.DISPLAY_NAME,
                                MediaStore.Audio.Media.ARTIST),
                        null, null, null)
    }

    var adapter :VbangAdapter?=null
    override fun initListener() {
        adapter = VbangAdapter(context,null,true)
        listview.adapter = adapter
    }

//    class AudioTask : AsyncTask<ContentResolver, Void, Cursor>() {
//        //后台执行的任务
//        override fun doInBackground(vararg p0: ContentResolver?): Cursor? {
//
//            val cursor = p0[0]?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                    arrayOf(MediaStore.Audio.Media.DATA,
//                            MediaStore.Audio.Media.SIZE,
//                            MediaStore.Audio.Media.DISPLAY_NAME,
//                            MediaStore.Audio.Media.ARTIST),
//                    null, null, null)
//            return cursor
//        }
//
//        /**
//         * 后台任务的结果回调到主线程中
//         */
//        override fun onPostExecute(result: Cursor?) {
//            super.onPostExecute(result)
//            CursorUtil.logCursor(result)
//        }
//
//        override fun onProgressUpdate(vararg values: Void?) {
//            super.onProgressUpdate(*values)
//
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        //界面销毁 关闭cursor
        //1.获取adapter中的cursor对象
        //2.将adapter中的cursor为null
        adapter?.changeCursor(null)
    }

}