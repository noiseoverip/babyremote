package com.remote.esauali.babyremote;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by esauali on 7/30/16.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class PlayerService extends Service {
  private static final String TAG = "service";
  private Handler handler = new Handler();
  private MediaPlayer player;
  private final int serverPort = 5000;
  private AsyncHttpServer server;

  private void play(String filename) {
    if (null != player) {
      player.stop();
    }
    player = MediaPlayer.create(this, Uri.parse("/sdcard/Music/" + filename + ".mp3"));
    if (null != player) {
      player.setLooping(true);
      player.start();
    }
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    startWebServer();
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    if (null != server) {
      server.stop();
    }
    super.onDestroy();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    // Not used
    return null;
  }

  private void startWebServer() {
    Log.d(TAG, "Starting server on port " + serverPort);
    server = new AsyncHttpServer();
    server.get("/.*", new HttpServerRequestCallback() {
      @Override
      public void onRequest(final AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        response.send("OK");
        handler.post(new Runnable() {
          @Override
          public void run() {
            Log.d(TAG,"Got request path: " + request.getPath());
            play(request.getPath().substring(1));
          }
        });
      }
    });
    server.listen(serverPort);
  }
}
