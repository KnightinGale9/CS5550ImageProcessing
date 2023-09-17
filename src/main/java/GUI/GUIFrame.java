package GUI;

import imageProcessing.ImageStorage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;


public class GUIFrame extends JFrame {
    private static GUIFrame instance;
    private ImageStorage image = new ImageStorage();
    private JPanel originalImage;
    private JLabel originalLabel;
    private JPanel transformImage;
    private JLabel transformLabel;
    private JTabbedPane tabbedpane;
    private JButton openFile;
    private JButton transformAsOriginal;
    private JLabel bitLevelLabel;
    private JSlider bitLevel;
    private JLabel originalPicLabel;
    private JLabel transformPicLabel;
    private JSlider subsampling;
    private JLabel subsamplinglabel;
    private JButton subSampleBtn;

    private JSlider upsampling;
    private JLabel upsamplinglabel;
    private JButton upSampleBtn;

    private JRadioButton r1;
    private JRadioButton r2;
    private JRadioButton r3;
    private JTextField nameSave;
    private JButton saveFile;

    public static GUIFrame getInstance(){
        if(instance==null){
            instance =new GUIFrame();
        }
        return instance;
    }

    private GUIFrame() {
        // Actions act = new Actions();
        this.setTitle("Image Processing");
        Actions act = new Actions();
        Change change = new Change();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1435, 815);
        this.setResizable(false);
        this.setBackground(Color.GRAY);

        JPanel spacialResolution = new JPanel(null);
//        spacialResolution.setBackground(Color.WHITE);
        subsamplinglabel = new JLabel("Subsample:");
        subsamplinglabel.setBounds(5,5,75,50);
        spacialResolution.add(subsamplinglabel);
        subsampling = new JSlider();
//        subsampling.setPreferredSize(new Dimension(500,100));
        subsampling.setBounds(110,5,500,50);
        spacialResolution.add(subsampling);
        subSampleBtn = new JButton("Subsample");
        subSampleBtn.addActionListener(act);
        subSampleBtn.setBounds(610,5,100,50);
        spacialResolution.add(subSampleBtn);

        upsamplinglabel = new JLabel("Upsample:");
        upsamplinglabel.setBounds(5,60,75,50);
        spacialResolution.add(upsamplinglabel);
        r1=new JRadioButton("Nearest Neighbor",true);
        r1.setBounds(90,60,150,50);
        r2=new JRadioButton("Linear Interpolation");
        r2.setBounds(240,60,170,50);
        r3=new JRadioButton("Bilinear Interpolation");
        r3.setBounds(400,60,200,50);
        ButtonGroup bg=new ButtonGroup();
        bg.add(r1);bg.add(r2);bg.add(r3);
        spacialResolution.add(r1);spacialResolution.add(r2);spacialResolution.add(r3);
        upsampling = new JSlider();
//        upsampling.setPreferredSize(new Dimension(500,100));
        upsampling.setBounds(600,60,500,50);
        spacialResolution.add(upsampling);
        upSampleBtn = new JButton("Upsample");
        upSampleBtn.setBounds(1100,60,100,50);
        upSampleBtn.addActionListener(act);
        spacialResolution.add(upSampleBtn);

        JPanel grayLevelResolution = new JPanel();
//        grayLevelResolution.setBackground(Color.WHITE);
        bitLevelLabel = new JLabel("Bit Level");
        grayLevelResolution.add(bitLevelLabel);
        bitLevel = new JSlider(1, 8, 8);
        bitLevel.setPreferredSize(new Dimension(500,100));
        bitLevel.setMajorTickSpacing(1);
        bitLevel.setSnapToTicks(true);
        bitLevel.setPaintTicks(true);
        bitLevel.setPaintLabels(true);
        bitLevel.setInverted(true);
        bitLevel.setBounds(100, 100, 200, 200);
        bitLevel.addChangeListener(change);
        grayLevelResolution.add(bitLevel);


        tabbedpane = new JTabbedPane();
        tabbedpane.setBounds(25, 20, 1395, 155);
        tabbedpane.add("Spatial Resolution", spacialResolution);
        tabbedpane.add("Gray Level Resolution", grayLevelResolution);
        this.add(tabbedpane);

        openFile = new JButton("<html><center>Set Original Image</center></html>");
        openFile.setBounds(20, 200, 100, 100);
        this.add(openFile);
        openFile.addActionListener(act);
        transformAsOriginal = new JButton("<html><center>Set Transform Image as Original Image</center></html>");
        transformAsOriginal.setBounds(20, 320, 100, 100);
        this.add(transformAsOriginal);
        transformAsOriginal.addActionListener(act);
        JLabel nameSaveLabel = new JLabel("<html>Name the download file:</html>");
        nameSaveLabel.setBounds(20, 460, 100, 50);
        this.add(nameSaveLabel);
        nameSave = new JTextField("transform");
        nameSave.setBounds(20, 510, 100, 40);
        this.add(nameSave);
        saveFile = new JButton("<html><center>Download Transform Image</center></html>");
        saveFile.setBounds(20, 550, 100, 100);
        this.add(saveFile);
        saveFile.addActionListener(act);



        originalImage = new JPanel(null);
//        originalImage.setBackground(Color.WHITE);
        originalImage.setBounds(155, 200, 560, 560);
        this.add(originalImage);

        originalLabel = new JLabel("Original Image");
        originalLabel.setBounds(155, 160, 100, 50);
        this.add(originalLabel);
        originalPicLabel = new JLabel();
        originalImage.add(originalPicLabel);

        transformImage = new JPanel(null);
        transformImage.setBounds(800, 200, 560, 560);
        this.add(transformImage);
        transformLabel = new JLabel("Transform Image");
        transformLabel.setBounds(800, 160, 150, 50);
        this.add(transformLabel);
        transformPicLabel = new JLabel();
        transformImage.add(transformPicLabel);
        transformPicLabel.setVisible(false);


        this.setVisible(true);
    }
    public class Change extends Component implements ChangeListener
    {
        public void stateChanged(ChangeEvent e) {
            if(e.getSource()==bitLevel) {
//                bitLevelLabel.setText("value of Slider is =" + bitLevel.getValue());
                image.changeBitLevel(8, bitLevel.getValue());
                BufferedImage temp = image.getImageFromArray();
                transformPicLabel.setIcon(new ImageIcon(temp));
                transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                transformPicLabel.setVisible(true);
            }
        }
    }

    public class Actions extends Component implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent e) {
             File f = null;
             try {
                 if(e.getSource() == openFile) {
                     JFileChooser chooser = new JFileChooser();
                     // optionally set chooser options ...
                     if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                         f = chooser.getSelectedFile();
                         // read  and/or display the file somehow. ....
                     } else {
                         // user changed their mind
                     }
                     image.setImageStorage(f);
                     image.setOriginalArray();
                     originalPicLabel.setIcon(new ImageIcon(image.getOriginalBufferedImage()));
                     originalPicLabel.setBounds(0,0, image.getOriginalBufferedImage().getWidth(), image.getOriginalBufferedImage().getHeight());
                     transformPicLabel.setVisible(false);

                     Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
                     for(int i = image.getOriginalBufferedImage().getHeight(),j=0; i>=32;i /=2,j++) {
                         table.put(j, new JLabel(String.valueOf(i)));
                     }
                     subsampling.setMajorTickSpacing(1);
                     subsampling.setMaximum(table.size()-1);
                     subsampling.setLabelTable(table);
                     subsampling.setPaintLabels(true);
                     subsampling.setPaintTicks(true);
                     subsampling.setSnapToTicks(true);
                     subsampling.setValue(0);
                     Hashtable<Integer, JLabel> uptable = new Hashtable<Integer, JLabel>();
                     for(int i = Integer.parseInt(table.get(0).getText()),j=0; i<=512;i*=2,j++) {
                         uptable.put(j, new JLabel(String.valueOf(i)));
                     }
                     upsampling.setMajorTickSpacing(1);
                     upsampling.setMaximum(uptable.size()-1);
                     upsampling.setLabelTable(uptable);
                     upsampling.setPaintLabels(true);
                     upsampling.setPaintTicks(true);
                     upsampling.setSnapToTicks(true);
                     upsampling.setValue(0);
                 }
                 if(e.getSource() == transformAsOriginal)
                 {
                    image.setImageStorage(image.getImageFromArray());
                    image.setTransformArrayAsOriginalArray();
                    originalPicLabel.setIcon(new ImageIcon(image.getOriginalBufferedImage()));
                    originalPicLabel.setBounds(0,0, image.getOriginalBufferedImage().getWidth(), image.getOriginalBufferedImage().getHeight());
                    transformPicLabel.setVisible(false);

                    Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
                    for(int i = image.getOriginalBufferedImage().getHeight(),j=0; i>=32;i /=2,j++) {
                        table.put(j, new JLabel(String.valueOf(i)));
                    }
                    subsampling.setMajorTickSpacing(1);
                    subsampling.setMaximum(table.size()-1);
                    subsampling.setLabelTable(table);
                    subsampling.setPaintLabels(true);
                    subsampling.setPaintTicks(true);
                    subsampling.setSnapToTicks(true);
                    subsampling.setValue(0);

                     Hashtable<Integer, JLabel> uptable = new Hashtable<Integer, JLabel>();
                     for(int i = image.getOriginalBufferedImage().getHeight(),j=0; i<=512;i*=2,j++) {
                         uptable.put(j, new JLabel(String.valueOf(i)));
                     }
                     upsampling.setMajorTickSpacing(1);
                     upsampling.setMaximum(uptable.size()-1);
                     upsampling.setLabelTable(uptable);
                     upsampling.setPaintLabels(true);
                     upsampling.setPaintTicks(true);
                     upsampling.setSnapToTicks(true);
                     upsampling.setValue(0);
                 }
                 if(e.getSource()==saveFile)
                 {
                    if(transformPicLabel.isVisible()) {
                        if(nameSave.getText()=="")
                        {
                            nameSave.setText("transform");
                        }
                        nameSave.setText(nameSave.getText().strip());
                        image.createTransformImage(nameSave.getText());
                    }
                 }
                 if(e.getSource()==upSampleBtn)
                 {
                     int[][] hold = image.getOriginalArray();
                     System.out.println("value of Slider is =" + upsampling.getValue());
                     for(int i=1;i<=upsampling.getValue();i++) {
                         System.out.printf("%d,%d\n", image.getOriginalBufferedImage().getWidth() *2, image.getOriginalBufferedImage().getHeight() * i);
                         image.upsampling(image.getOriginalArray().length * 2, image.getOriginalArray().length * 2);
                         if(r1.isSelected()) {
                             image.nearestNeighborUpsampling();
                         }
                         else if(r2.isSelected()){
                             image.linearInterpolationUpsampling();
                         }
                         else if(r3.isSelected()){
                             image.bilinearInterpolationUpsampling();
                         }
                         image.setTransformArrayAsOriginalArray();
                     }
                     image.setOriginalArray(hold);
                     if(upsampling.getValue()==0)
                     {
                         image.changeBitLevel(8,8);
                     }
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource() ==subSampleBtn)
                 {
                     image.subsampling(subsampling.getValue());
                     BufferedImage temp =  image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
             } catch (HeadlessException ex) {
                 throw new RuntimeException(ex);
             }

         }
     }

}