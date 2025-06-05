package hotel_project;

public class GenericBST<T extends Comparable<T>> {

    private class Node {
        T data;
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
        }
    }

    private Node root;

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, T data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, data);
        } else {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.data.toString());
            inorderRec(root.right);
        }
    }
public void delete(T value) {
    root = deleteRec(root, value);
}

private Node deleteRec(Node root, T value) {
    if (root == null) return root;
    int cmp = value.compareTo(root.data);
    if (cmp < 0) {
        root.left = deleteRec(root.left, value);
    } else if (cmp > 0) {
        root.right = deleteRec(root.right, value);
    } else {
                if (root.left == null)
            return root.right;
        else if (root.right == null)
            return root.left;
        root.data = minValue(root.right);
        root.right = deleteRec(root.right, root.data);
    }
    return root;
}

private T minValue(Node root) {
    T minv = root.data;
    while (root.left != null) {
        minv = root.left.data;
        root = root.left;
    }
    return minv;
}
}
