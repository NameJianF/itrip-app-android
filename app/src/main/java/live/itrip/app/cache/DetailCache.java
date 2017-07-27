package live.itrip.app.cache;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import live.itrip.app.data.model.BlogModel;
import live.itrip.common.util.AppLog;


/**
 * Created by Feng on 2017/7/27.
 */

public class DetailCache {
    private static final long ONE_DAY = 86400000;//1天毫秒
    private static String CUSTOM_CACHE;
    private static String COLLECTION_CACHE;

    public static void init(Context context) {
        CUSTOM_CACHE = context.getCacheDir() + "/" + "custom_cache/";
        COLLECTION_CACHE = context.getCacheDir() + "/" + "collection_cache/";
        update(false);
        update(true);
    }

    /**
     * 更新文件夹过期的文件
     *
     * @param isCollection 是否是收藏 文件夹名 custom_cache | collection_cache
     */
    private static void update(boolean isCollection) {
        File file = new File(isCollection ? COLLECTION_CACHE : CUSTOM_CACHE);
        if (!file.exists()) {
            file.mkdirs();
            return;
        }
        File[] files = file.listFiles();
        long currentDate = new Date().getTime();//当前时间
        long delayDate = (isCollection ? 10 : 2) * ONE_DAY;
        for (File f : files) {
            if (currentDate - f.lastModified() >= delayDate) {
                f.delete();
            }
        }
    }

    /**
     * 添加到缓存文件
     */
    public static void addCache(BlogModel model) {
        if (model == null)
            return;
        String name = model.getId() + String.valueOf(model.getType());
        String path = (model.getFavorite() == 1 ? COLLECTION_CACHE : CUSTOM_CACHE)
                + name;
        File file = new File(path);
        FileOutputStream os = null;
        try {
            if (!file.exists())
                file.createNewFile();
            os = new FileOutputStream(file);
            os.write(new Gson().toJson(model).getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取缓存
     */
    static BlogModel readCache(BlogModel bean) {
        if (bean == null || bean.getId() <= 0)
            return null;
        String path = (bean.getFavorite() == 1 ? COLLECTION_CACHE : CUSTOM_CACHE)
                + bean.getId() + String.valueOf(bean.getType());
        File file = new File(path);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            Type type = new TypeToken<BlogModel>() {
            }.getType();
            BlogModel subBean = new Gson().fromJson(reader, type);
            reader.close();
            return subBean;
        } catch (Exception e) {
            AppLog.e(e.getMessage());
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
