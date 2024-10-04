package chapter4.graphs.api.task;

/**
 * Cycle detection
 */
public interface CycleDetect {

    boolean hasCycle();

    Iterable<Integer> cycle();

    default void printCycle() {
        if (hasCycle()) {
            StringBuilder sb = new StringBuilder("cycle: ");
            for (int v : cycle())
                sb.append(v).append("->");
            System.out.println(sb.append("..."));
        }
        else System.out.println("no cycle");
    }
}
