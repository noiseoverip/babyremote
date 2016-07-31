package com.remote.esauali.babyremote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
  Intent serviceIntent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Create service intent used for starting, stopping our service
    serviceIntent = new Intent(MainActivity.this, PlayerService.class);
    // Perform action on button click
    this.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          MainActivity.this.stopService(serviceIntent);
        }catch (Exception e) {
          // Ignore if this fails
        }
        MainActivity.this.startService(serviceIntent);
      }
    });
  }
}
