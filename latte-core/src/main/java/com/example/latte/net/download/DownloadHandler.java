package com.example.latte.net.download;

import android.os.AsyncTask;

import com.example.latte.net.RestCreator;
import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liangbingtian on 2018/3/21.
 */

public class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest IREQUEST;
    private final String DOWNLOAD_DIR;//目录
    private final String EXTENSION;//后缀
    private final String NAME;//文件名
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String URL,
                           IRequest IREQUEST,
                           String DOWNLOAD_DIR,
                           String EXTENSION,
                           String NAME,
                           ISuccess SUCCESS,
                           IFailure FAILURE,
                           IError ERROR) {
        this.URL = URL;
        this.IREQUEST = IREQUEST;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
    }

    public final void handleDownload() {


        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();

                            final SaveFileTask task = new SaveFileTask(IREQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //判断文件下载是否结束，否则会下载不全。
                            if (task.isCancelled()) {
                                if (IREQUEST != null) {
                                    IREQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if (ERROR !=null){
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        if (FAILURE !=null){
                            FAILURE.onFailure();
                        }
                    }
                });

    }
}
