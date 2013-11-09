
package huffmantree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author wang
 */


public class HuffmanTree {

    /**
     * @param args the command line arguments
     */
    
static Map<Character, Integer> counter = new HashMap<Character, Integer>();
static Map<Character, String> codeBook = new HashMap<Character, String>();

    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
       String filePath = "./test.txt";
       String fileOutPath = "./output.txt";
       String codeOutPath = "./codebook.txt";
       FileRead(filePath);
       FileWrite(fileOutPath);
       Node root = huffmanTreeBuild();
       codeGen(root,codeBook,""); 
       CodeWrite(codeOutPath);
       String s = "aabbbc....";
       System.out.println("Let's code and decode string: " + s);
       System.out.println("The huffmancode is: " + encodeString(s));
       System.out.println("The string is: " +decodeString(encodeString(s), root));
    }
    
    
    static String decodeString(String s, Node root){
       String stringS = "";
       Node current = root;
        char[] chararray = s.toCharArray();
            for(char tmp : chararray){
                if(tmp == '1' && current.right != null){//right subtree
                    current = current.right;
                }else if(tmp == '0' && current.left != null){//left subtree
                    current = current.left;
                }else{
                    stringS += current.c;
                    current = root;
                 //   System.out.println("find: " + current.c);
                }
            }
       return stringS; 
    }
    static String encodeString(String s){
        String binaryS = "";
            char[] chararray = s.toCharArray();
            for(char tmp : chararray){
                binaryS+=codeBook.get(tmp)+" ";
            }
        return binaryS;
    }
    
    /*
     * Read in characters in a file, count occurences of characters
     * put them into the hashmap
     */
    static void FileRead(String filePath) throws FileNotFoundException{
        Scanner in = new Scanner(new File(filePath));
        while(in.hasNextLine()){
            char[] chararray = in.nextLine().toCharArray();
            for(char tmp : chararray){
                if(counter.containsKey(tmp)){
                    int c = counter.get(tmp)+1;
                    counter.put(tmp, c); //update the counter
                }else{
                    counter.put(tmp, 1);
                }
            }
        }
        System.out.println("Read file: " + filePath + " finished");
    }
    
    static void FileWrite(String filePath) throws FileNotFoundException{
        PrintWriter out = new PrintWriter(new File(filePath));
        out.println("Character:\tcount");
        for(char a : counter.keySet()){
            out.println(a+":\t\t"+counter.get(a));
        }
        out.close();
        System.out.println("Results have been written into: " + filePath);
    }
    
    
    static void CodeWrite(String filePath) throws FileNotFoundException{
        PrintWriter out = new PrintWriter(new File(filePath));
        out.println("Character:\tHuffman code");
        for(char a : codeBook.keySet()){
            out.println(a+":\t\t"+codeBook.get(a));
        }
        out.close();
        System.out.println("Codebook has been generated in: " + filePath);
    }
    
    
    /*huffmanTree buid*/
    static Node huffmanTreeBuild(){
        queueInit();
        Node root = null;
            while (queue.size() > 1)
            {
                root = new Node(queue.remove(),queue.remove());
                queue.add(root);
            }

        return root;
    }
    
    /*huffmanCode generate*/
    static void codeGen(Node root,Map<Character, String> codeBook,String code){
            if(root.left == null && root.right == null)
                codeBook.put(root.c, code);
            if(root.left != null)
                codeGen(root.left,codeBook,code+"0");
            if(root.right != null)
                codeGen(root.right,codeBook,code+"1");
    }
    
    /*Initial the priorityQueue based on hashmap*/
    static void queueInit(){
        for(char a: counter.keySet()){
            queue.add(new Node(a,counter.get(a)));
        }
    }

    /*Construct a priority queue*/
    static  PriorityQueue<Node> queue = new PriorityQueue<Node>(10,
                new Comparator<Node>() {
            public int compare(Node n1, Node n2){
                if (n1.count > n2.count){
                    return +1;
                }
                else if (n1.count < n2.count){
                    return -1;
                }
                else {  // equal
                    return 0;
                }
            }
        });            
    
}
