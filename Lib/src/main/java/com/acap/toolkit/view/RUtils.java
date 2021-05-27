package com.acap.toolkit.view;

import androidx.annotation.IdRes;

import com.acap.toolkit.app.AppUtils;
import com.acap.toolkit.structure.KeyValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Tip:
 *      R文件相关工具
 *
 * Created by ACap on 2021/5/27 15:38
 * </pre>
 */
public class RUtils {

    private static Map<Integer, String> Id_To_Name;

    /**
     * 获得ID在布局中的名称
     *
     * @param id
     * @return
     */
    public static String getNameById(@IdRes int id) {
        return getNameById(AppUtils.getPackageName(), id);
    }

    /**
     * 获得ID在布局中的名称
     *
     * @param packageName R文件所在包
     * @param id
     * @return
     */
    public static String getNameById(String packageName, @IdRes int id) {
        if (Id_To_Name == null) {
            Id_To_Name = new HashMap<>();
            List<KeyValue<String, Integer>> items = getFieldsByClass(packageName + ".R$id");

            for (KeyValue<String, Integer> item : items) {
                Id_To_Name.put(item.getValue(), item.getKey());
            }
        }
        return Id_To_Name.get(id);
    }


    private static List<KeyValue<String, Integer>> getFieldsByClass(String cls) {
        List<KeyValue<String, Integer>> list = new ArrayList<>();
        try {
            Class<?> aClass = Class.forName(cls);
            Field[] fields = aClass.getFields();
            for (Field field : fields) {
                list.add(new KeyValue(field.getName(), field.getInt(null)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
