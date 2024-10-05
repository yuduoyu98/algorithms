package chapter1.fundamentals.api;

import java.util.Iterator;

public abstract class PrintableLinearDS<Item> implements Iterable<Item> {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Item> iterator = iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext())
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}