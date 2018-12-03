package io.github.frapples.javademoandcookbook.demoandcase.demo.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.MutablePair;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/11/23
 */
public class StackRecursive {

    @Data
    @AllArgsConstructor
    private static class Node {
        private int value;
        private Node left;
        private Node right;
    }

    public static void main(String[] args) {
        Node node = new Node(1,
            new Node(3,
                new Node(12, null, null),
                new Node(14, null, null)),
            new Node(2, null, null)
        );


        foreachNode(node);
        System.out.println("-----");
        foreachNodeStack(node);
    }

    private static void foreachNode(Node node) {
        if (node == null) {
            return;
        }

        System.out.println(node.getValue());
        foreachNode(node.getLeft());
        foreachNode(node.getRight());
    }

    private static void foreachNodeStack(Node node) {
        final int STEP_START = 0;
        final int STEP_END_FOREACH_LEFT = 1;
        final int STEP_END_FOREACH_RIGHT = 2;
        Deque<MutablePair<Node, Integer>> stack = new ArrayDeque<>();
        stack.push(MutablePair.of(node, STEP_START));

        while (!stack.isEmpty()) {
            MutablePair<Node, Integer> context = stack.getFirst();
            int step = context.getRight();
            Node n = context.getLeft();

            switch (step) {
                case STEP_START:
                    if (n == null) {
                        stack.pop();
                        break;
                    }
                    System.out.println(n.getValue());
                    context.setRight(STEP_END_FOREACH_LEFT);
                    stack.push(MutablePair.of(n.getLeft(), STEP_START));
                    break;
                case STEP_END_FOREACH_LEFT:
                    context.setRight(STEP_END_FOREACH_RIGHT);
                    stack.push(MutablePair.of(n.getRight(), STEP_START));
                    break;
                case STEP_END_FOREACH_RIGHT:
                    stack.pop();
                default:
                    break;
            }
        }
    }
}
