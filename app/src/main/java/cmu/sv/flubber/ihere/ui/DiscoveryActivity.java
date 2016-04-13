package cmu.sv.flubber.ihere.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cmu.sv.flubber.ihere.R;

public class DiscoveryActivity extends AppCompatActivity {

    final static int CAMERA_OUTPUT = 0;
    ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscoveryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(this)));
        startActivityForResult(i, CAMERA_OUTPUT);
    }

    private File getTempFile(Context context){
        final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
        if(!path.exists()){
            path.mkdir();
        }
        return new File(path, "image.tmp");
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_OUTPUT:
                    final File file = getTempFile(this);
                    try {
                        Bitmap captureBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (captureBmp.getWidth() > captureBmp.getHeight()) {
                            // create a matrix object
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            captureBmp = Bitmap.createBitmap(captureBmp , 0, 0, captureBmp .getWidth(), captureBmp .getHeight(), matrix, true);
                        }
                        imv = (ImageView) findViewById(R.id.discover_img);
                        imv.setImageBitmap(captureBmp);
                        // do whatever you want with the bitmap (Resize, Rename, Add To Gallery, etc)
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void pinOnClick(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }


}
