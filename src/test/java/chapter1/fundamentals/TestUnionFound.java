package chapter1.fundamentals;

import chapter1.fundamentals.api.UnionFound;
import chapter1.fundamentals.impl.PathCompressedWeightedQuickUnionUF;
import chapter1.fundamentals.impl.QuickFindUF;
import chapter1.fundamentals.impl.QuickUnionUF;
import chapter1.fundamentals.impl.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 并查集测试
 */
public class TestUnionFound {

    private static final String DATA_PARENT_PATH = "src/test/java/chapter1/fundamentals/data/";
    private static final String DATA_BASE_URL = "https://algs4.cs.princeton.edu/15uf/";

    private static final String TINY = "tinyUF.txt";
    private static final String MEDIUM = "mediumUF.txt";
    private static final String LARGE = "largeUF.txt";

    private static final Map<String, Integer> resMap;

    static {
        resMap = new HashMap<>();
        resMap.put(TINY, 2);
        resMap.put(MEDIUM, 3);
        resMap.put(LARGE, 6);
    }

    @Test
    public void UFBasicTest() {
        testEntry(TINY, QuickFindUF.class, true);
        testEntry(TINY, QuickUnionUF.class, true);
        testEntry(MEDIUM, QuickFindUF.class, true);
        testEntry(MEDIUM, QuickUnionUF.class, true);
    }

    @Test
    public void UFPerformanceTest() {
//        testEntry(LARGE, QuickFindUF.class, false);
        //比quick-find还慢
//        testEntry(LARGE, QuickUnionUF.class, false);
        //13 sec 左右
//        testEntry(LARGE, WeightedQuickUnionUF.class, false);
        //在这个场景下也没快
        testEntry(LARGE, PathCompressedWeightedQuickUnionUF.class, false);
    }

    private <T extends UnionFound> void testEntry(String dataFile, Class<T> testClazz, boolean useLocal) {
        In in;
        if (useLocal) {
            File file = new File(DATA_PARENT_PATH + dataFile);
            in = new In(file);
        } else {
            try {
                URL url = new URL(DATA_BASE_URL + dataFile);
                in = new In(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        UnionFound uf = UFInit(testClazz, in);
        assert uf.count() == resMap.get(dataFile);
    }

    public <T extends UnionFound> UnionFound UFInit(Class<T> clazz, In in) {
        int N = in.readInt();
        StdOut.println("触点数为：" + N);
        UnionFound uf;
        try {
            uf = clazz.getDeclaredConstructor(int.class).newInstance(N);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            uf.union(p, q);
            StdOut.println(p + "-" + q + "建立连接, 连通分量为：" + uf.count());
        }
        StdOut.println("并查集构建完成，共有" + uf.count() + "个连通分量");
        return uf;
    }
}
