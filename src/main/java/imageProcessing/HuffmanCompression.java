package imageProcessing;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanCompression {

    public void huffmanEncoding(byte[] imageRaster)
    {
        ByteArrayOutputStream encoder = new ByteArrayOutputStream();
        HashMap<Byte,Integer> imageDict = new HashMap<>();
        for(int i=0; i < imageRaster.length; i++)
        {
            if(!imageDict.containsKey(imageRaster[i]))
            {
                imageDict.put(imageRaster[i],1 );
            }
            imageDict.put(imageRaster[i],imageDict.get(imageRaster[i])+1 );
        }
//        System.out.println(imageDict);
        PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>(imageDict.size(),new HuffmanMinComparator());
        for (Byte data:imageDict.keySet()) {
            HuffmanNode huff= new HuffmanNode(data,imageDict.get(data));
            minHeap.add(huff);
        }
        HuffmanNode root = null;
        while (minHeap.size() > 1) {

            // first min extract.
            HuffmanNode x = minHeap.poll();

            // second min extract.
            HuffmanNode y = minHeap.poll();

            // new node f which is equal
            HuffmanNode f = new HuffmanNode(Byte.valueOf("0"),x.getFrequency()+y.getFrequency());

            // first extracted node as left child.
            f.setLeft(x);

            // second extracted node as the right child.
            f.setRight(y);

            // marking the f node as the root node.
            root = f;

            // add this node to the priority-queue.
            minHeap.add(f);
        }
        System.out.println("debug");

        // print the codes by traversing the tree
//        printCode(root, "");
    }
    class HuffmanNode {



        private byte data;
        private int frequency;

        private HuffmanNode left=null;
        private HuffmanNode right=null;

        public HuffmanNode(byte data,int f)
        {
            this.data=data;
            this.frequency=f;
        }
        public byte getData() {
            return data;
        }

        public int getFrequency() {
            return frequency;
        }
        public HuffmanNode getLeft() {
            return left;
        }

        public void setLeft(HuffmanNode left) {
            this.left = left;
        }

        public HuffmanNode getRight() {
            return right;
        }

        public void setRight(HuffmanNode right) {
            this.right = right;
        }

    }

    // comparator class helps to compare the node
    // on the basis of one of its attribute.
    // Here we will be compared
    // on the basis of data values of the nodes.
    class HuffmanMinComparator implements Comparator<HuffmanNode> {
        public int compare(HuffmanNode x, HuffmanNode y)
        {
            return x.getFrequency()-y.getFrequency();
        }
    }
}
