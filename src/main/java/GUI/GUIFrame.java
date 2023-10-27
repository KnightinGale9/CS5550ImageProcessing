package GUI;

import imageProcessing.ImageStorage;
import imageProcessing.filterProcessing.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;


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
    private JButton globalHEButton;
    private JButton localHEButton;
    private JTextField localmask;
    private ArrayList<JCheckBox> bitplaneCheckbox;
    private JButton bitPlaneButton;
    private JTextField boxMask;
    private JTextField weightedAverageMask;
    private JTextField weightedAverageBVal;
    private JTextField medianMask;
    private JTextField sharpeningMask;
    private JComboBox<Integer> middleSharp;
    private JComboBox<Integer> signSharp;
    private JRadioButton boxRadio;
    private JRadioButton wgavgRadio;
    private JTextField highBoostTXT;
    private JTextField ASelect;
    private JButton highboostButton;
    private JButton boxButton;
    private JButton weightedAverageButton;
    private JButton medianButton;
    private JButton sharpButton;
    private JTextField alphaTrimmedD;
    private JButton alphaTrimmedMeanButton;
    private JTextField alphaTrimmedMeanMask;
    private JButton contraharmonicMeanButton;
    private JTextField contraharmonicQ;
    private JTextField contraharmonicMeanMask;
    private JButton harmonicMeanButton;
    private JTextField harmonicMeanMask;
    private JButton geometricMeanButton;
    private JTextField geometricMeanMask;
    private JButton arithmeticMeanButton;
    private JTextField arithmeticMeanMask;

    private JButton midpointButton;
    private JTextField midpointMask;
    private JButton maxButton;
    private JTextField maxMask;
    private JButton minButton;
    private JTextField minMask;

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

        JPanel histogramEqualization = new JPanel(null);
        JLabel globalHistogramEqualizationLabel =new JLabel("Global Histogram Equalization:");
        globalHistogramEqualizationLabel.setBounds(5,5,200,50);
        histogramEqualization.add(globalHistogramEqualizationLabel);
        globalHEButton = new JButton("Global Histogram Equalization");
        globalHEButton.setBounds(210,5,300,50);
        globalHEButton.addActionListener(act);
        histogramEqualization.add(globalHEButton);


        JLabel localHistogramEqualizationLabel =new JLabel(" Local Histogram Equalization:    MASK(ODD Numbers ONLY):");
        localHistogramEqualizationLabel.setBounds(5,60,400,50);
        histogramEqualization.add(localHistogramEqualizationLabel);

        localmask = new JTextField("3");
        localmask.setBounds(400,65,100,40);
        localmask.setInputVerifier(new OddNumInputVerify());

        histogramEqualization.add(localmask);
        localHEButton = new JButton("Local Histogram Equalization");
        localHEButton.setBounds(510,60,300,50);
        localHEButton.addActionListener(act);
        histogramEqualization.add(localHEButton);

        JPanel boxFilterPanel = new JPanel();
        JLabel smoothLabel = new JLabel("Box Filters(Smoothing Filter)");
        smoothLabel.setPreferredSize(new Dimension(180,50));
        boxFilterPanel.add(smoothLabel);
        boxFilterPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        boxMask = new JTextField("3");
        boxMask.setPreferredSize(new Dimension(100,40));
//        boxMask.setInputVerifier(new OddNumInputVerify());
        boxFilterPanel.add(boxMask);
        boxButton = new JButton("Box Filter");
        boxButton.setPreferredSize(new Dimension(100,50));
        boxButton.addActionListener(act);
        boxFilterPanel.add(boxButton);

        JPanel weightedAverageFilterPanel = new JPanel();
        JLabel weightedAverageLabel = new JLabel("Weighted Average Filters(Smoothing Filter)");
        weightedAverageLabel.setPreferredSize(new Dimension(270,50));
        weightedAverageFilterPanel.add(weightedAverageLabel);
        weightedAverageFilterPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        weightedAverageMask = new JTextField("3");
        weightedAverageMask.setPreferredSize(new Dimension(100,40));
        weightedAverageFilterPanel.add(weightedAverageMask);
        weightedAverageFilterPanel.add(new JLabel("  B Value(Positive num>1): "));
        weightedAverageBVal = new JTextField("2");
        weightedAverageBVal.setPreferredSize(new Dimension(100,40));
        weightedAverageFilterPanel.add(weightedAverageBVal);
//        weightedAverageMask.setInputVerifier(new OddNumInputVerify());
        weightedAverageButton = new JButton("Weighted Average Filter");
        weightedAverageButton.setPreferredSize(new Dimension(200,50));
        weightedAverageButton.addActionListener(act);
        weightedAverageFilterPanel.add(weightedAverageButton);

        JPanel medianFilterPanel = new JPanel();
        JLabel medianLabel = new JLabel("Median Filters");
        medianLabel.setPreferredSize(new Dimension(100,50));
        medianFilterPanel.add(medianLabel);
        medianFilterPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        medianMask = new JTextField("3");
        medianMask.setPreferredSize(new Dimension(100,40));
//        boxMask.setInputVerifier(new OddNumInputVerify());
        medianFilterPanel.add(medianMask);
        medianButton = new JButton("Median Filter");
        medianButton.setPreferredSize(new Dimension(100,50));
        medianButton.addActionListener(act);
        medianFilterPanel.add(medianButton);

        JPanel sharpeningPanel = new JPanel();
        JLabel sharpeningLabel = new JLabel("Sharpening Filters");
        sharpeningLabel.setPreferredSize(new Dimension(130,50));
        sharpeningPanel.add(sharpeningLabel);
        sharpeningPanel.add(new JLabel("MASK(ODD Numbers ONLY): "));
        sharpeningMask = new JTextField("3");
        sharpeningMask.setPreferredSize(new Dimension(100,40));
//        boxMask.setInputVerifier(new OddNumInputVerify());
        sharpeningPanel.add(sharpeningMask);
        sharpeningPanel.add(new JLabel("Adjacency: "));
        middleSharp = new JComboBox<>(new Integer[]{4,8});
        sharpeningPanel.add(middleSharp);
        signSharp = new JComboBox<>(new Integer[]{-1,1});
        sharpeningPanel.add(new JLabel("Center Mask Sign: "));
        sharpeningPanel.add(signSharp);
        sharpButton = new JButton("Sharpen Filter");
        sharpButton.setPreferredSize(new Dimension(100,50));
        sharpButton.addActionListener(act);
        sharpeningPanel.add(sharpButton);

        JPanel highBoostPanel = new JPanel();
        JLabel highBoostLabel = new JLabel("High Boost Filters");
        highBoostLabel.setPreferredSize(new Dimension(130,50));
        highBoostPanel.add(highBoostLabel);
        JLabel smoothHB = new JLabel("Smoothing Filters");
        smoothHB.setPreferredSize(new Dimension(130,50));
        highBoostPanel.add(smoothHB);
        boxRadio = new JRadioButton("Box Filter",true);
        highBoostPanel.add(boxRadio);
        wgavgRadio = new JRadioButton("Weighted Average Filter");
        highBoostPanel.add(wgavgRadio);
        ButtonGroup highboostBG = new ButtonGroup();
        highboostBG.add(boxRadio);
        highboostBG.add(wgavgRadio);
        highBoostPanel.add(new JLabel("MASK(ODD Numbers ONLY): "));
        highBoostTXT = new JTextField("3");
        highBoostTXT.setPreferredSize(new Dimension(100,40));
        highBoostTXT.setInputVerifier(new OddNumInputVerify());
        highBoostPanel.add(highBoostTXT);
        highBoostPanel.add(new JLabel("Value for A: "));
        ASelect = new JTextField("2");
        ASelect.setPreferredSize(new Dimension(100,40));
//        boxMask.setInputVerifier(new OddNumInputVerify());
        highBoostPanel.add(ASelect);
        highboostButton = new JButton("HighBoost Filter");
        highboostButton.setPreferredSize(new Dimension(150,50));
        highboostButton.addActionListener(act);
        highBoostPanel.add(highboostButton);

        JPanel minPanel = new JPanel();
        JLabel minLabel = new JLabel("Min Filters");
        minLabel.setPreferredSize(new Dimension(100,50));
        minPanel.add(minLabel);
        minPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        minMask = new JTextField("3");
        minMask.setPreferredSize(new Dimension(100,40));
        minMask.setInputVerifier(new OddNumInputVerify());
        minPanel.add(minMask);
        minButton = new JButton("Min Filter");
        minButton.setPreferredSize(new Dimension(100,50));
        minButton.addActionListener(act);
        minPanel.add(minButton);

        JPanel maxPanel = new JPanel();
        JLabel maxLabel = new JLabel("Max Filters");
        maxLabel.setPreferredSize(new Dimension(100,50));
        maxPanel.add(maxLabel);
        maxPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        maxMask = new JTextField("3");
        maxMask.setPreferredSize(new Dimension(100,40));
        maxMask.setInputVerifier(new OddNumInputVerify());
        maxPanel.add(maxMask);
        maxButton = new JButton("Max Filter");
        maxButton.setPreferredSize(new Dimension(100,50));
        maxButton.addActionListener(act);
        maxPanel.add(maxButton);

        JPanel midpointPanel = new JPanel();
        JLabel midpointLabel = new JLabel("Midpoint Filters");
        midpointLabel.setPreferredSize(new Dimension(100,50));
        midpointPanel.add(midpointLabel);
        midpointPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        midpointMask = new JTextField("3");
        midpointMask.setPreferredSize(new Dimension(100,40));
        midpointMask.setInputVerifier(new OddNumInputVerify());
        midpointPanel.add(midpointMask);
        midpointButton = new JButton("Midpoint Filter");
        midpointButton.setPreferredSize(new Dimension(100,50));
        midpointButton.addActionListener(act);
        midpointPanel.add(midpointButton);

        JPanel arithmeticMeanPanel = new JPanel();
        JLabel arithmeticMeanLabel = new JLabel("Arithmetic Mean Filters");
        arithmeticMeanLabel.setPreferredSize(new Dimension(100,50));
        arithmeticMeanPanel.add(arithmeticMeanLabel);
        arithmeticMeanPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        arithmeticMeanMask = new JTextField("3");
        arithmeticMeanMask.setPreferredSize(new Dimension(100,40));
        arithmeticMeanMask.setInputVerifier(new OddNumInputVerify());
        arithmeticMeanPanel.add(arithmeticMeanMask);
        arithmeticMeanButton = new JButton("Arithmetic Mean Filter");
        arithmeticMeanButton.setPreferredSize(new Dimension(100,50));
        arithmeticMeanButton.addActionListener(act);
        arithmeticMeanPanel.add(arithmeticMeanButton);

        JPanel geometricMeanPanel = new JPanel();
        JLabel geometricMeanLabel = new JLabel("Geometric Mean Filters");
        geometricMeanLabel.setPreferredSize(new Dimension(100,50));
        geometricMeanPanel.add(geometricMeanLabel);
        geometricMeanPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        geometricMeanMask = new JTextField("3");
        geometricMeanMask.setPreferredSize(new Dimension(100,40));
        geometricMeanMask.setInputVerifier(new OddNumInputVerify());
        geometricMeanPanel.add(geometricMeanMask);
        geometricMeanButton = new JButton("Geometric Mean Filter");
        geometricMeanButton.setPreferredSize(new Dimension(100,50));
        geometricMeanButton.addActionListener(act);
        geometricMeanPanel.add(geometricMeanButton);

        JPanel harmonicMeanPanel = new JPanel();
        JLabel harmonicMeanLabel = new JLabel("Harmonic Mean Filters");
        harmonicMeanLabel.setPreferredSize(new Dimension(100,50));
        harmonicMeanPanel.add(harmonicMeanLabel);
        harmonicMeanPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        harmonicMeanMask = new JTextField("3");
        harmonicMeanMask.setPreferredSize(new Dimension(100,40));
        harmonicMeanMask.setInputVerifier(new OddNumInputVerify());
        harmonicMeanPanel.add(harmonicMeanMask);
        harmonicMeanButton = new JButton("Harmonic Mean Filter");
        harmonicMeanButton.setPreferredSize(new Dimension(100,50));
        harmonicMeanButton.addActionListener(act);
        harmonicMeanPanel.add(harmonicMeanButton);

        JPanel contraharmonicMeanPanel = new JPanel();
        JLabel contraharmonicMeanLabel = new JLabel("Contra Harmonic Mean Filters");
        contraharmonicMeanLabel.setPreferredSize(new Dimension(100,50));
        contraharmonicMeanPanel.add(contraharmonicMeanLabel);
        contraharmonicMeanPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        contraharmonicMeanMask = new JTextField("3");
        contraharmonicMeanMask.setPreferredSize(new Dimension(100,40));
        contraharmonicMeanMask.setInputVerifier(new OddNumInputVerify());
        contraharmonicMeanPanel.add(contraharmonicMeanMask);
        contraharmonicMeanPanel.add(new JLabel(" Q value: "));
        contraharmonicQ = new JTextField("1");
        contraharmonicQ.setPreferredSize(new Dimension(100,40));
        contraharmonicMeanPanel.add(contraharmonicQ);
        contraharmonicMeanButton = new JButton("Contra Harmonic Mean Filter");
        contraharmonicMeanButton.setPreferredSize(new Dimension(100,50));
        contraharmonicMeanButton.addActionListener(act);
        contraharmonicMeanPanel.add(contraharmonicMeanButton);

        JPanel alphaTrimmedMeanPanel = new JPanel();
        JLabel alphaTrimmedMeanLabel = new JLabel("Alpha Trimmed Mean Filters");
        alphaTrimmedMeanLabel.setPreferredSize(new Dimension(100,50));
        alphaTrimmedMeanPanel.add(alphaTrimmedMeanLabel);
        alphaTrimmedMeanPanel.add(new JLabel("  MASK(ODD Numbers ONLY): "));
        alphaTrimmedMeanMask = new JTextField("3");
        alphaTrimmedMeanMask.setPreferredSize(new Dimension(100,40));
        alphaTrimmedMeanMask.setInputVerifier(new OddNumInputVerify());
        alphaTrimmedMeanPanel.add(alphaTrimmedMeanMask);
        alphaTrimmedMeanPanel.add(new JLabel(" D value: "));
        alphaTrimmedD = new JTextField("2");
        alphaTrimmedD.setPreferredSize(new Dimension(100,40));
        alphaTrimmedMeanPanel.add(alphaTrimmedD);
        alphaTrimmedMeanButton = new JButton("Alpha Trimmed Mean Filter");
        alphaTrimmedMeanButton.setPreferredSize(new Dimension(100,50));
        alphaTrimmedMeanButton.addActionListener(act);
        alphaTrimmedMeanPanel.add(alphaTrimmedMeanButton);

        JTabbedPane filters = new JTabbedPane();
        filters.add("Box filter",boxFilterPanel);
        filters.add("Weighted Average filter",weightedAverageFilterPanel);
        filters.add("Median filter", medianFilterPanel);
        filters.add("Sharpening filter", sharpeningPanel);
        filters.add("High Boosting filter", highBoostPanel);
        JTabbedPane imageRestorefilters = new JTabbedPane();
//        filters.add("Add Noise",noisePanel);
        imageRestorefilters.add("Min filter",minPanel);
        imageRestorefilters.add("Midpoint filter",midpointPanel);
        imageRestorefilters.add("Max filter",maxPanel);
        imageRestorefilters.add("Arithmetic mean filter",arithmeticMeanPanel);
        imageRestorefilters.add("Geometric mean filter",geometricMeanPanel);
        imageRestorefilters.add("Harmonic mean filter",harmonicMeanPanel);
        imageRestorefilters.add("Contraharmonic mean filter",contraharmonicMeanPanel);
        imageRestorefilters.add("Alpha-trimmed mean filter",alphaTrimmedMeanPanel);




        JPanel bitPlane = new JPanel();
        JLabel bitPLaneLabel = new JLabel("Remove the following bitplane from the Image: ");
        bitPLaneLabel.setPreferredSize(new Dimension(300,100));
        bitPlane.add(bitPLaneLabel);
        bitPlane.add(new JLabel("MSB"));
        bitplaneCheckbox=new ArrayList<>();
        for(int i=7;i>=0;i--)
        {
            bitplaneCheckbox.add(new JCheckBox(String.valueOf(i)));
            bitPlane.add(bitplaneCheckbox.get(7-i));
        }
        bitPlane.add(new JLabel("LSB  "));
        bitPlaneButton = new JButton("Remove checked Bitplane");
        bitPlaneButton.setPreferredSize(new Dimension(200,50));
        bitPlaneButton.addActionListener(act);
        bitPlane.add(bitPlaneButton);


        tabbedpane = new JTabbedPane();
        tabbedpane.setBounds(25, 20, 1395, 155);
        tabbedpane.add("Spatial Resolution", spacialResolution);
        tabbedpane.add("Gray Level Resolution", grayLevelResolution);
        tabbedpane.add("Histogram Equalization",histogramEqualization);
        tabbedpane.add("Spatial Filters",filters);
        tabbedpane.add("Image Restoration Filters",imageRestorefilters);
        tabbedpane.add("Bit Plane",bitPlane);
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
                     for(int i = image.getOriginalBufferedImage().getHeight(),j=0; i>=30;i /=2,j++) {
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
                     for(int i = Integer.parseInt(table.get(0).getText()),j=0; i<=600;i*=2,j++) {
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
                    for(int i = image.getOriginalBufferedImage().getHeight(),j=0; i>=30;i /=2,j++) {
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
                     for(int i = image.getOriginalBufferedImage().getHeight(),j=0; i<=600;i*=2,j++) {
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
                 if(e.getSource()== globalHEButton)
                 {
                     image.globalHistogramEqualization();
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()== localHEButton)
                 {
                     System.out.println(localmask.getText());
                     image.localHistogramEqualization(Integer.valueOf(localmask.getText()));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==bitPlaneButton) {
                     Set<Integer> bit = new HashSet<>();
                     for (int i = 0; i < 8; i++) {
                         if (bitplaneCheckbox.get(i).isSelected()) {
                             bit.add(Integer.valueOf(bitplaneCheckbox.get(i).getText()));
                         }
                     }
                     System.out.println(bit);
                     image.bitPlaneImage(bit);
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==boxButton)
                 {
                     image.filter(new BoxFilter(Integer.valueOf(boxMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==weightedAverageButton)
                 {
                     image.filter(new WeightedAverageFilter(Integer.valueOf(weightedAverageMask.getText()),Integer.valueOf(weightedAverageBVal.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==medianButton)
                 {
                     image.filter(new MedianFilter(Integer.valueOf(medianMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==sharpButton)
                 {
                     image.sharpeningFilter(Integer.valueOf(sharpeningMask.getText()),middleSharp.getItemAt(middleSharp.getSelectedIndex()),-1*signSharp.getItemAt(signSharp.getSelectedIndex()));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==highboostButton)
                 {
                     image.highBoostFiter((boxRadio.isSelected())?new BoxFilter(Integer.valueOf(highBoostTXT.getText())) : new WeightedAverageFilter(Integer.valueOf(highBoostTXT.getText()),2),Integer.valueOf(ASelect.getText()));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==minButton)
                 {
                     image.filter(new MinFilter(Integer.valueOf(minMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==midpointButton)
                 {
                     image.filter(new MidpointFilter(Integer.valueOf(midpointMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==maxButton)
                 {
                     image.filter(new MaxFilter(Integer.valueOf(maxMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }if(e.getSource()==arithmeticMeanButton)
                 {
                     image.filter(new ArithmeticMeanFilter(Integer.valueOf(arithmeticMeanMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }if(e.getSource()==geometricMeanButton)
                 {
                     image.filter(new GeometicMeanFilter(Integer.valueOf(geometricMeanMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==harmonicMeanButton)
                 {
                     image.filter(new HarmonicMeanFilter(Integer.valueOf(harmonicMeanMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
                 if(e.getSource()==contraharmonicMeanButton)
                 {
                     image.filter(new CountraHarmonicMeanFilter(Integer.valueOf(contraharmonicQ.getText()),Integer.valueOf(contraharmonicMeanMask.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }if(e.getSource()==alphaTrimmedMeanButton)
                 {
                     image.filter(new AlphaTrimmedMeanFIlter(Integer.valueOf(alphaTrimmedMeanMask.getText()),Integer.valueOf(alphaTrimmedD.getText())));
                     BufferedImage temp = image.getImageFromArray();
                     transformPicLabel.setIcon(new ImageIcon(temp));
                     transformPicLabel.setBounds(0, 0, temp.getWidth(), temp.getHeight());
                     transformPicLabel.setVisible(true);
                 }
             } catch (HeadlessException ex) {
                 throw new RuntimeException(ex);
             }

         }
     }
    private class OddNumInputVerify extends InputVerifier
    {
        @Override
        public boolean verify(JComponent input)
        {
            String text = ((JTextField) input).getText();
            if(text.matches("[A-Za-z].*")|| text.matches(".*[A-Za-z].*")) {
                JOptionPane.showMessageDialog(input, "Only Odd numbers are allowed", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            else if (Integer.parseInt(text)%2==1)
            {

                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(input, "Only Odd numbers are allowed", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
    }

}