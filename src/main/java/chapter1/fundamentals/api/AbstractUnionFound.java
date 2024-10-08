package chapter1.fundamentals.api;

/**
 * Union Found 并查集 抽象实现
 */
public abstract class AbstractUnionFound implements UnionFound {

    protected int[] set;
    protected int count;

    public AbstractUnionFound(int N) {
        set = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            set[i] = i;
        }
    }

    @Override
    public abstract void union(int p, int q);

    @Override
    public abstract int find(int p);

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int count() {
        return count;
    }
}
