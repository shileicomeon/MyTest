package collect.jhjz.com.mytest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   useVp();
         noUseVp();
    }
    public void useVp(){
        startActivity(new Intent(this,ViewPagerActivity.class));
    }

    public void noUseVp(){
        startActivity(new Intent(this,FragmentManagerActivity.class));
    }
    //分享文字
    public void shareText(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "text内容.");
        shareIntent.setType("text/plain");

        //设置标题(弹出分享列表的界面标题),
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    //分享一张图片
    public void shareSingleImage(View view) {
        String imagePath = Environment.getExternalStorageDirectory() + File.separator + "aaa.jpg";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));  //imagePath--本地的文件路径
        Log.d("share", "uri:" + imageUri);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到:"));
    }

    //分享多张图片  (保存一个集合)
    public void shareMultipleImage(View view) {
        ArrayList<Uri> uriList = new ArrayList<>();

        /*String path = Environment.getExternalStorageDirectory() + File.separator;
        uriList.add(Uri.fromFile(new File(filePath+"aaa-1.jpg")));
        uriList.add(Uri.fromFile(new File(filePath+"aaa-2.jpg")));
        uriList.add(Uri.fromFile(new File(filePath+"aaa-3.jpg")));*/

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到:"));
    }
}
