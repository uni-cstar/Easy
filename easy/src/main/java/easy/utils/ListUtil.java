package easy.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucio on 16/8/12.
 */
public class ListUtil {

    /**
     * 列表是否为空
     *
     * @param lst
     * @return
     */
    public static boolean isNullOrEmpty(List lst) {
        return lst == null || lst.size() == 0;
    }

    /**
     * change list to super list
     *
     * @param tClass 父类class
     * @param datas  数据源
     * @param <T>    父类类型
     * @return 父类包装之后的数据
     */
    public static <T> List<T> changeToSuperList(Class<T> tClass, List<? extends T> datas) {
        if (datas == null)
            return null;

        if (datas.size() == 0)
            return new ArrayList<T>();

        List<T> result = new ArrayList<T>();
        for (T item : datas) {
            result.add(item);
        }
        return result;
    }

    /**
     * 获取最后一个
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> T getLast(List<T> tList) {
        if (ListUtil.isNullOrEmpty(tList))
            return null;
        return tList.get(tList.size() - 1);
    }

    /**
     * 获取第一个
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> T getFirst(List<T> tList) {
        if (ListUtil.isNullOrEmpty(tList))
            return null;
        return tList.get(0);
    }

}
