package com.example.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.latte.app.Latte;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by liangbingtian on 2018/3/21.
 */

@SuppressWarnings("SpellCheckingInspection")
public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    private final ISuccess SUCCESS;


    @Override
    protected File doInBackground(Object[] params) {

        String downloadDir = (String) params[0];
        String exetension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[4];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (exetension == null || exetension.equals("")) {
            exetension = "";
        }
        if (name == null){
            return FileUtil.writeToDisk(is,downloadDir,exetension.toUpperCase(),exetension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }

    }

    //执行完一步之后回到主线程的操作
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS !=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST!=null){
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    private void autoInstallApk(File file){
         if (FileUtil.getExtension(file.getPath()).equals("apk")){
             final Intent install = new Intent();
             install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             install.setAction(Intent.ACTION_VIEW);
             install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
             Latte.getApplicationContext().startActivity(install);
         }
    }
}
