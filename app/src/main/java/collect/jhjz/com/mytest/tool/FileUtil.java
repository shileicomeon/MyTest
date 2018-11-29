package collect.jhjz.com.mytest.tool;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2016/10/27.
 */
public class FileUtil {
    private Context context;

    private static String FILENAME = "liuchang_c";//MODE_APPEND

    //设备查询列表
//    private static String FILE_QUERYCRITERIA_SHEBEI = "kt_query_criteria";
    //浏览记录
    public static String FILE_LIULANJILU = "kt_liuchang_c";

    /**
     * 推送订单列表
     * */
    public static String FILENAME_JPUSHORDER = "liuchang_jpushorder";

    /**
     * 消息通知
     * */
    public static String FILENAME_JPUSHNOTI = "liuchang_jpushnoti";

    /**
     * 搜索缓存
     * */
    public static String FILESOUSUO_LISHI = "liuchang_sousuo";


    public FileUtil(Context context){
        this.context = context;
    }

    //保存文件操作
    public synchronized void save(String data,String fileName,int type) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput(fileName, type);//context.MODE_APPEND
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //读取文件操作
    public synchronized String load(String fileName) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = this.context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
