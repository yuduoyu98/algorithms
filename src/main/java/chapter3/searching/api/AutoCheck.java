package chapter3.searching.api;

/**
 * 在每次插入/删除后自动校验OST的有序性和正确性
 */
public interface AutoCheck {

    /**
     * 自动校验逻辑
     * 默认关闭
     */
    default boolean check(){
        return true;
    }

    static void keyNotNull(Object key, String method) {
        if (key == null) throw new IllegalArgumentException("argument to " + method + "() is null");
    }

}
