package person.walker.utils;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
public class GeneratorOrderListUtils {
    LinkedList<String> linkedList = new LinkedList();
    public static GeneratorOrderListUtils create(){
        return new GeneratorOrderListUtils();
    }
    public GeneratorOrderListUtils add(String e) {
        linkedList.add(e);
        return this;
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();
        if (linkedList.size() < 1) {
            return null;
        }
        String firstElement = linkedList.pollFirst();
        if (linkedList.size() < 1) {
            return firstElement;
        } else {
            sb.append(firstElement);
            for (String e : linkedList) {
                sb.append(",").append(e);
            }
            return sb.toString();
        }
    }
}
