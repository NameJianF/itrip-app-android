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

import live.itrip.app.data.model.BaseDetailModel;
import live.itrip.app.data.model.BlogDetailModel;
import live.itrip.app.data.model.PlanDetailModel;
import live.itrip.common.util.AppLog;


/**
 * Created by Feng on 2017/7/27.
 */

public class DetailCache {
    private static final long ONE_DAY = 86400000;//1天毫秒
    private static String FAVORITE_CACHE; // 收藏数据缓存
    private static String NO_FAVORITE_CACHE; // 未收藏数据缓存

    public static void init(Context context) {
        FAVORITE_CACHE = DataCacheManager.getCacheDir(context) + File.separator + "favorite" + File.separator;
        NO_FAVORITE_CACHE = context.getCacheDir() + File.separator + "no_favorite" + File.separator;
        update(false);
        update(true);
    }

    /**
     * 更新文件夹过期的文件
     *
     * @param favorite 是否是收藏 文件夹名 NO_FAVORITE_CACHE | FAVORITE_CACHE
     */
    private static void update(boolean favorite) {
        File file = new File(favorite ? FAVORITE_CACHE : NO_FAVORITE_CACHE);
        if (!file.exists()) {
            file.mkdirs();
            return;
        }
        File[] files = file.listFiles();
        long currentDate = new Date().getTime();//当前时间
        long delayDate = (favorite ? 10 : 2) * ONE_DAY;
        for (File f : files) {
            if (currentDate - f.lastModified() >= delayDate) {
                f.delete();
            }
        }
    }

    /**
     * 添加到缓存文件
     */
    public static void addCache(BaseDetailModel model) {
        if (model == null)
            return;
        String name = "";
        if (model instanceof BlogDetailModel) {
            name = "blog" + model.getId();
        } else if (model instanceof PlanDetailModel) {
            name = "plan" + model.getId();
        }
        String path = (model.getFavorite() == 1 ? FAVORITE_CACHE : NO_FAVORITE_CACHE) + name;
        File file = new File(path);
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(new Gson().toJson(model).getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取缓存
     */
    public static BlogDetailModel readBlogDetailCache(BaseDetailModel model) {
        if (model == null || model.getId() <= 0)
            return null;
        String name = "blog" + model.getId();
        String path = (model.getFavorite() == 1 ? FAVORITE_CACHE : NO_FAVORITE_CACHE) + name;
        File file = new File(path);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            Type type = new TypeToken<BlogDetailModel>() {
            }.getType();
            BlogDetailModel subBean = new Gson().fromJson(reader, type);
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

    /**
     * 读取缓存
     */
    public static PlanDetailModel readPlanDetailCache(BaseDetailModel model) {
        if (model == null || model.getId() <= 0)
            return null;
        String name = "plan" + model.getId();
        String path = (model.getFavorite() == 1 ? FAVORITE_CACHE : NO_FAVORITE_CACHE) + name;
        File file = new File(path);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            Type type = new TypeToken<PlanDetailModel>() {
            }.getType();
            PlanDetailModel subBean = new Gson().fromJson(reader, type);
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
