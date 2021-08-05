package RedBlackTrees;

import java.io.*;
import java.util.Scanner;

class Node {
    String data; // holds the key
    Node parent; // pointer to the parent
    Node left; // pointer to left child
    Node right; // pointer to right child
    int color; // 1 . Red, 0 . Black
}



public class RedBlackTree {
    private static Node TNULL;
    private Node root;


    public int searchTree(Node root, String key) {
        while (root != TNULL) {
            if (root.data.compareToIgnoreCase(key) == 0) {
                return 1;
            }
            if (root.data.compareToIgnoreCase(key) > 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return 0;
    }


    // fix the red-black tree
    private void fixInsert(Node k){
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                // if x'parent is the left child of it's parent
                u = k.parent.parent.left; // uncle
                if (u.color == 1) { // case1: recolor if uncle is red
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else { //case 2: if uncle is black and x is a left child
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    // case 3: if uncle is black and x is a right child
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else { // if x'parent is the right child of it's parent
                u = k.parent.parent.right; // uncle

                if (u.color == 1) {
                    // case1: recolor if uncle is red
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        //case 2: if uncle is black and x is a right child
                        k = k.parent;
                        leftRotate(k);
                    }
                    // case 3: if uncle is black and x is a left child
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    public RedBlackTree() {
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // rotate left at node x
    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // rotate right at node x
    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // insert the key to the tree in its appropriate position then fix it
    public void insert(String key) {
        Node node = new Node();
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1; // new node must be red

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data.compareToIgnoreCase(x.data) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.compareToIgnoreCase(y.data)< 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null){
            node.color = 0;
            return;
        }
        if (node.parent.parent == null) {
            return;
        }
        // Fix the tree
        fixInsert(node);
    }

    public Node getRoot(){
        return this.root;
    }
    public static int treeHeight(Node x) {
        if (x == TNULL) {
            return -1;
        } else {
            int leftHeight = treeHeight(x.left);
            int rightHeight = treeHeight(x.right);

            if (leftHeight > rightHeight) {
                return (leftHeight +1);
            } else return (rightHeight+1);

        }

    }

    public static int treeSize(Node x) {
        if (x == TNULL) {
            return 0;
        } else {
            return (treeSize(x.right) + treeSize(x.left) + 1);
        }
    }


    public static void main(String [] args) throws IOException {
        RedBlackTree rbt = new RedBlackTree();
        Scanner inputChoice = new Scanner( System.in );
        Scanner inputSearch = new Scanner( System.in );
        Scanner inputInsert = new Scanner( System.in );
        try
        {
            File file=new File("EN-US-Dictionary.txt");
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while((line=br.readLine())!=null)
            {
                rbt.insert(line);//returns a string that textually represents the object

            }
            System.out.println("--------Dictionary loaded Successfully---------");
            System.out.println("-----------------------------------------------");
            fr.close();    //closes the stream and release the resources


        }
        catch(IOException e)
        {
            System.out.println("Dictionary not loaded ");
            e.printStackTrace();
        }
        boolean loop = true;
         while(loop){
             System.out.println("Choose one of the Following:");
             System.out.println("----------------------------");
             System.out.println("1.Search \n2.Insert\n3.Tree Height\n4.Tree Size\n5.Exit");
             System.out.println("--------------------------------------------------------");
             int choice = inputChoice.nextInt();
             switch (choice){
                 case 1:{
                     System.out.println("What is the word you want to search?");
                     String searchKey = inputSearch.next();
                     int found = rbt.searchTree(rbt.getRoot(),searchKey);
                     if (found == 1)
                     {
                         System.out.println(searchKey + " is Found");
                     }else System.out.println(searchKey + " is not Found");
                            break;
             }
                 case 2:{
                     System.out.println("What is the word you want to insert?");
                     String newWord = inputInsert.next();
                     int flag = rbt.searchTree(rbt.getRoot(),newWord);
                     if(flag ==1 ){
                         System.out.println("ERROR: Word already exists in Dictionary");
                     } else {
                         rbt.insert(newWord);
                         System.out.println(newWord + " Inserted");
                         System.out.println("Tree Height after insertion : " +treeHeight(rbt.getRoot()));
                         System.out.println("Tree Size after insertion : "+ treeSize(rbt.getRoot()));
                     }
                     break;
                 }
                 case 3:{
                     System.out.println("Tree Height : " + treeHeight(rbt.getRoot()));
                     break;
                 }
                 case 4:{
                     System.out.println("Tree Size : "+ treeSize(rbt.getRoot()));
                     break;
                 }
                 case 5:{
                     loop = false;
                     break;
                 }
                 default:
                     System.out.println("Invalid Input!\nPlease Enter a valid choice: ");
             }

    }

    }
}


