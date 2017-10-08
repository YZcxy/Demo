public class BinarySearchTree {
    public Node root;
    public BinarySearchTree(){
        this.root = null;
    }

    /**
     * 查找key
     *   从根节点开始查找
     *   如果key小于该节点，则向左查找
     *   如果key大于该节点，则向右查找
     *   节点与key相等，则返回true
     *   否则返回false
     * @param key
     * @return
     */
    public boolean find(int key){
        Node current = root;
        boolean res = false;
        while (current!=null){
            if(key == current.val){
                res = true;
                break;
            }else if(key < current.val){
                current = current.left;
            }else {
                current = current.right;
            }
        }
        return res;
    }

    /**
     * 插入
     *    如果根节点为空，则将插入节点赋值给根节点，返回true
     *    如果key等于当前节点，则不进行插入操作，返回false
     *    如果key小于当前节点，则向左插入
     *       如果左节点为空，则进行插入，返回true
     *    如果key大于当前节点，则向右插入
     *       如果右节点为空，则进行插入，返回true
     * @param key
     * @return
     */
    public boolean insert(int key){
        //要插入的节点
        Node newNode = new Node(key);
        if(root == null){
            root = newNode;
            return true;
        }

        Node current = root;
        Node parent;
        while (true){
            parent = current;
            if(key == current.val){
                return false;
            }else if(key < current.val){
                current = current.left;
                if(current == null){
                    parent.left = newNode;
                    return true;
                }
            }else {
                current = current.right;
                if(current == null){
                    parent.right = newNode;
                    return true;
                }
            }
        }
    }

    /**
     * 删除
     *    首先找到要删除的节点，并存储该节点位于父节点的左节点还是右节点
     *    然后删除有三种情况：
     *       要删除的节点没有子节点
     *          直接删除
     *       要删除的节点只有一个子节点
     *          将子节点赋值给父节点
     *          然后删除
     *       要删除的节点有两个子节点
     *          在该节点的右子树中找到一个继承节点（继承节点为右子树最小的节点）
     *          将继承节点赋值给父节点
     *          然后将左字数赋值给继承节点
     * @param key
     * @return
     */
    public boolean delete(int key){
        Node current = root;
        Node parent = root;
        boolean isLeftChild = false;

        //查找要删除的节点在左节点还是右节点
        while(key != current.val){
            parent = current;
            if(key < current.val){
                isLeftChild = true;
                current = current.left;
            }else {
                isLeftChild = false;
                current = current.right;
            }

            if(current == null)return false;
        }

        //要删除的节点是叶子节点（没有左节点，也没有右节点）
        if(current.left==null && current.right==null){
            if(current == root){
                root = null;
            } else if(isLeftChild){
                parent.left = null;
            }else {
                parent.right = null;
            }
        }
        //要删除的节点只有左节点
        else if(current.right==null){
            if(current == root){
                root = current.left;
            }else if(isLeftChild){
                parent.left = current.left;
            }else {
                parent.right = current.left;
            }
        }
        //要删除的节点只有右节点
        else if(current.left==null){
            if(current == root){
                root = current.right;
            }else if(isLeftChild){
                parent.left = current.right;
            }else{
                parent.right = current.right;
            }
        }
        //要删除的节点左右节点都不为空
        else if(current.left!=null && current.right!=null){
            //这时候我们需要找到要删除节点的最小子节点
            Node successor = getSuccessor(current);

            if(current == root){
                root = successor;
            }else if(isLeftChild){
                parent.left = successor;
            }else{
                parent.right = successor;
            }
            successor.left = current.left;
        }
        return true;
    }

    /**
     * 找到继承节点
     * @param deleteNode
     * @return
     */
    public Node getSuccessor(Node deleteNode){
        Node successor = null;
        Node sParent = null;
        Node current = deleteNode.right;
        while(current!=null){
            sParent = successor;
            successor = current;
            current = current.left;
        }

        if(successor!=deleteNode.right){
            sParent.left = successor.right;
            successor.right = deleteNode.right;
        }
        return successor;
    }
}

class Node{
    int val;
    Node left;
    Node right;
    public Node(int val){
        this.val = val;
        left = null;
        right = null;
    }
}
