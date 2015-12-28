package com.xlzhen.sharepictome;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xlzhen on 12/28 0028.
 * Share pictures to my APP Utils
 * 分享图片到我的APP内
 */
public class SharePicToMeUtils {
    /**
     * Share pictures to my APP Utils
     * 分享图片到我的APP内
     *
     * @param activity
     * @return picPaths 返回真实的图片地址列表 real pic Paths
     */
    public static List<String> getListPicPath(Activity activity) {
        List<String> picPaths = new ArrayList<>();

        Intent intent = activity.getIntent();
        if (Intent.ACTION_SEND.equals(intent.getAction())) {//Processing first pic 处理单张图片
            if (intent.getType().startsWith("image/")) {
                Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                picPaths.add(processTransactions(activity, imageUri));
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(intent.getAction())) {//Processing enough pic 处理多张图片
            if (intent.getType().startsWith("image/")) {
                List<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                for (Uri uri : imageUris) {
                    picPaths.add(processTransactions(activity, uri));
                }
            }
        }

        return picPaths;
    }

    /**
     * Processing Uri
     * 处理Uri，返回 真实地址
     *
     * @param activity
     * @param uri      图片"地址" pic "Path"
     * @return picPath 返回真实的图片地址 real pic Path
     */
    private static String processTransactions(Activity activity, Uri uri) {
        if (uri == null)
            return "";

        String picPath;

        Log.v("Before Transformation", uri.getPath());

        if (!uri.getPath().contains("external/images/media"))
            picPath = uri.getPath();//Unwanted To SD Path 无需转换成sd卡地址
        else {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            picPath = actualimagecursor.getString(actual_image_column_index);//Need To SD Path 需要转换成sd卡地址
        }

        Log.v("After Transformation", picPath);

        return picPath;
    }
}
