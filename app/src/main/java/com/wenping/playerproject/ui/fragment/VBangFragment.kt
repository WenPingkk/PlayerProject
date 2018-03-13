package com.wenping.playerproject.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.AsyncQueryHandler
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.view.View
import android.widget.AdapterView
import com.wenping.playerproject.R
import com.wenping.playerproject.adapter.VbangAdapter
import com.wenping.playerproject.base.BaseApp
import com.wenping.playerproject.base.BaseFragment
import com.wenping.playerproject.model.AudioBean
import com.wenping.playerproject.ui.activity.AudioPlayerActivity
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

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
        //申请权限
        //动态权限申请
        handlePermission()

        loadSongs()
    }

    private fun handlePermission() {
        //查看是否有权限
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val checkSelfPermission = context?.let { ActivityCompat.checkSelfPermission(it, permission) }
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            //已经获取了数据
            loadSongs()
        } else {
            if (shouldShowRequestPermissionRationale(context as Activity,permission)) {
                //需要弹出
                alert("我们只会访问音乐文件,不会访问隐私照片", "温馨提示"){
                    yesButton { requestPermission() }
                    noButton {  }
                }.show()
            } else {
                //不需要弹出
                requestPermission()
            }
        }


    }

    /**
     * 真正申请权限
     */
    private fun requestPermission() {
        var permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permissions,1)

    }

    /**
     * 接收权限授权结果
     * requestCode 请求码
     * permissions 权限申请数组
     * grantResults 申请之后的结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSongs()
        } else {
           //不需要处理
        }
    }

    private fun loadSongs() {
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
        val handler = object : AsyncQueryHandler(resolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                //查询完成的回调 主线程中
                //刷新列表
                //1.设置数据
                (cookie as VbangAdapter).swapCursor(cursor)
                //2.刷新列表
            }
        }
        //开始查询
        handler.startQuery(0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
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
        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = adapter?.getItem(position) as Cursor
            val list :ArrayList<AudioBean> = AudioBean.getAudioBeans(item)
            startActivity<AudioPlayerActivity>("list" to list,"position" to position)
        }
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