import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import javafx.util.Pair;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class MainWindow {
    private JTabbedPane tabbedPane1;
    private JRadioButton radioButton1;
    private JTextField textField1;
    private JRadioButton radioButton2;
    private JTextArea textArea1;
    private JButton generateButton;
    private JButton randomBegin;
    private JButton nextStep;
    private JButton exportRoute;
    private JTextField textField3;
    private JButton singleSP;
    private JButton allSP;
    private JTextArea textArea2;
    private JButton genNewText;
    private JButton fullImage;
    private JButton exportButton;
    private JPanel mainPanel;
    private JPanel submainPanel;
    private JPanel controlZone;
    private JPanel inputFile;
    private JPanel functionTab;
    private JPanel imagePanel;
    private JPanel imageZone;
    private JPanel exportZone;
    private JPanel _exportZone;
    private JPanel inputPanel;
    private JPanel _inputPanel;
    private JPanel textPanel;
    private JPanel textGrid;
    private JPanel ButtonPanel;
    private JPanel ButtonGrid;
    private JPanel randomTab;
    private JPanel randomPanel;
    private JPanel shortestTab;
    private JPanel shortestBPanel;
    private JPanel newSPanel;
    private JPanel SArea;
    private JScrollPane SPanel_s;
    private JPanel SCommit;
    private JScrollPane text_s;
    private JPanel shortestPanelB;
    private JPanel shortestPanelA;
    private JTextField textField2;
    private JPanel bridgeTab;
    private JPanel bButtonPanel;
    private JPanel endPanelB;
    private JPanel endPointA;
    private JButton showBridgeButton;
    private JTextField endTextB;
    private JTextField endTextA;
    private JButton importFileChooseButton;
    private JSVGCanvas svgPanel;
    private JButton dotPathButton;
    private WordGraph wordGraph;
    private File chosenFile;
    public MainWindow() {
        importFileChooseButton.addActionListener((ActionEvent e) -> {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.showDialog(new JLabel(), "导入");
                File file = jFileChooser.getSelectedFile();
                if(file != null && file.isFile()) {
                    textField1.setText(file.getAbsolutePath());
                    radioButton1.setSelected(true);
                    radioButton2.setSelected(false);
                    System.out.println(file.getAbsoluteFile());
                    chosenFile = file;
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "未选择任何文件！", "提示",JOptionPane.WARNING_MESSAGE);
                    textField1.setText("");
                }
            });
        generateButton.addActionListener((ActionEvent e) -> {
                if(radioButton1.isSelected()) {
                    try {
                        if(chosenFile == null || !chosenFile.getAbsolutePath().equals(textField1.getText())){
                            chosenFile = new File(textField1.getText());
                        }
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(chosenFile));
                        StringBuilder document = new StringBuilder();
                        while(true) {
                            String read = bufferedReader.readLine();
                            if(read == null)break;
                            document.append(read);
                        }
                        wordGraph = new WordGraph(document.toString());
                    } catch (IOException e0) {
                        // File is missing after selected.
                        JOptionPane.showMessageDialog(mainPanel, "文件读取失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(radioButton2.isSelected()) {
                    wordGraph = new WordGraph(textArea1.getText());
//                    System.out.println(imageZone.getSize());
//                    JFrame jFrame = new JFrame();
//                    jFrame.add(new JPanel(){
//                        @Override
//                        public void paintComponent(Graphics G){
//                            super.paintComponent(G);
//                            G.drawImage(bufferedImage,0,0,imageZone.getWidth(),imageZone.getHeight(),imageZone);
//                        }
//                    });
//                    jFrame.setSize(bufferedImage.getWidth()*5/4,bufferedImage.getHeight()*5/4 );
////                    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    jFrame.setVisible(true);
                }
//                wordGraph = new WordGraph();
            try{
                String svgPath = wordGraph.exportSVGFile().toURI().toString();
                System.out.println(svgPath);
//                        svgPath = new File("C:\\Users\\Ding Shi\\AppData\\Local\\Temp\\graphviz_svg6771877140673811667.svg").toURI().toString();
                svgPanel.loadSVGDocument(svgPath);
//                        svgPanel.setEnableZoomInteractor(true);
            }catch (dotPathException d0){
                JOptionPane.showMessageDialog(mainPanel,"Dot程序无响应或未配置！","错误",JOptionPane.ERROR_MESSAGE);
                d0.printStackTrace();
            }
            });
        textArea1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                radioButton2.setSelected(true);
                radioButton1.setSelected(false);
            }
        });
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                radioButton1.setSelected(true);
                radioButton2.setSelected(false);
            }
        });
        singleSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer[] res = wordGraph.shortestPath(textField2.getText().toLowerCase(),textField3.getText().toLowerCase());
                if(res == null || res[0] == WordGraph.UNREACHABLE){
                    JOptionPane.showMessageDialog(mainPanel, "未找到最短路径。", "警告", JOptionPane.WARNING_MESSAGE);
                }else{
                    try {
                        String svgPath = wordGraph.exportSVGFile().toURI().toString();
                        System.out.println(svgPath);
                        svgPanel.setURI(svgPath);
                    }catch(dotPathException d0){
                        JOptionPane.showMessageDialog(mainPanel,"Dot程序无响应或未配置！","错误",JOptionPane.ERROR_MESSAGE);
                        d0.printStackTrace();
                    }
                }
            }
        });
        showBridgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wordGraph.bridgeWord(endTextA.getText().toLowerCase(), endTextB.getText().toLowerCase());
                    String svgPath = wordGraph.exportSVGFile().toURI().toString();
                    System.out.println(svgPath);
//                    BufferedImage bufferedImage = wordGraph.exportFullImage();
                    svgPanel.setURI(svgPath);
                }catch(dotPathException d0){
                    JOptionPane.showMessageDialog(mainPanel,"Dot程序无响应或未配置！","错误",JOptionPane.ERROR_MESSAGE);
                    d0.printStackTrace();
                }
            }
        });
        dotPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setFileFilter(new FileNameExtensionFilter(
                        "GraphViz dot(*.exe)", "exe"));
                jFileChooser.showDialog(new JLabel(), "选择dot程序");
                File file = jFileChooser.getSelectedFile();
                WordGraph.setDotPath(file.getAbsolutePath());
                try{
                    String response = WordGraph.testDotPath();
                    if(!response.toLowerCase().contains("graphviz")){
                        int i = JOptionPane.showConfirmDialog(mainPanel,"Dot程序可能有问题。是否继续使用？\n"+response,"警告",JOptionPane.WARNING_MESSAGE);
                        if(i == JOptionPane.NO_OPTION){
                            dotPathButton.getAction().actionPerformed(e);
                        }
                    }else{
                        JOptionPane.showMessageDialog(mainPanel,"成功调用！\n"+response,"提示",JOptionPane.PLAIN_MESSAGE);

                    }
                }catch (IOException i0){
                    JOptionPane.showMessageDialog(mainPanel,"Dot程序无法调用！","错误",JOptionPane.ERROR_MESSAGE);
                }catch (dotPathException i0){
                    JOptionPane.showMessageDialog(mainPanel,"Dot程序无响应！","错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        genNewText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                String[] sentenceArray = textArea2.getText().split("\\s");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < sentenceArray.length - 1; i++) {
                    String[] bridge = wordGraph.bridgeWord(sentenceArray[i].toLowerCase().replaceAll("[^A-Za-z\\s]",""),
                            sentenceArray[i+1].toLowerCase().replaceAll("[^A-Za-z\\s]",""));
                    sb.append(sentenceArray[i]+" ");
                    if(bridge.length > 0){
                        sb.append(bridge[random.nextInt(bridge.length)]+" ");
                    }
                }
                sb.append(sentenceArray[sentenceArray.length-1]);
                textArea2.setText(sb.toString());
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jFileChooser.setFileFilter(new FileNameExtensionFilter(
                            "PNG(.png)", "png"));
                    jFileChooser.setFileFilter(new FileNameExtensionFilter(
                            "SVG(.svg)", "svg"));
                    int i = jFileChooser.showDialog(new JLabel(), "导出");
                    if(i == JFileChooser.CANCEL_OPTION){
                        return;
                    }
                    File file = jFileChooser.getSelectedFile();
                    switch(jFileChooser.getFileFilter().getDescription()){
                        case "PNG(.png)":wordGraph.exportPNG(file);break;
                        case "SVG(.svg)":wordGraph.exportSVGFile().renameTo(file);break;
                        default:System.out.println("No such option");
                    }
                }catch(TranscoderException t){
                    t.printStackTrace();
                }catch (dotPathException d){
                    JOptionPane.showMessageDialog(mainPanel,"dot程序未配置。","错误",JOptionPane.ERROR_MESSAGE);
                }catch (FileNotFoundException f){
                    JOptionPane.showMessageDialog(mainPanel,"无法创建文件。","错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        allSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, File> fileMap = new HashMap<>();
                try {
                    String endpoint[] = wordGraph.allShortestPath(textField2.getText(), fileMap);
                    ArrayList<JRadioButton> radioButtonList = new ArrayList<>();
                    JPanel boxPanel = new JPanel();
                    boxPanel.setLayout(new BoxLayout(boxPanel,BoxLayout.Y_AXIS));
                    JSVGCanvas svgCanvas = new JSVGCanvas();
                    // Add radio button
                    for(String s:endpoint){
                        File file = fileMap.get(s);
                        if(file != null && file.exists()){
                            JRadioButton jRadioButton = new JRadioButton(s);
                            jRadioButton.addActionListener((ActionEvent e0)->{
                                svgCanvas.setURI(file.toURI().toString());
                                radioButtonList.forEach((JRadioButton j)->{j.setSelected(false);});
                                jRadioButton.setSelected(true);
                            });
                            boxPanel.add(jRadioButton);
                            radioButtonList.add(jRadioButton);
                        }else
                            System.out.println(String.format("File %s removed accidentally.", s));
                    }
                    JFrame subFrame = new JFrame("所有最短路径");
                    subFrame.getContentPane().setLayout(new GridBagLayout());
                    GridBagConstraints scrollConstraint =  new GridBagConstraints();
                    scrollConstraint.weightx = 2;
                    scrollConstraint.weighty = 1;
                    GridBagConstraints svgConstraint =  new GridBagConstraints();
                    svgConstraint.weightx = 3;
                    svgConstraint.weighty = 1;
                    svgConstraint.gridx = GridBagConstraints.RELATIVE;
                    svgConstraint.gridy = GridBagConstraints.NONE;
                    svgConstraint.fill = GridBagConstraints.BOTH;
                    subFrame.getContentPane().add(new JScrollPane(boxPanel),scrollConstraint);
                    subFrame.getContentPane().add(svgCanvas,svgConstraint);
                    subFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    subFrame.pack();
                    subFrame.setVisible(true);
                }catch (dotPathException d0){
                    JOptionPane.showMessageDialog(mainPanel,"dot程序未配置。","错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        // GridLayoutManager from JetBrains caused a NullPointException in ui loading.
        JFrame frame = new JFrame("实验1 单词图");
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e){
            e.printStackTrace();
        }
        MainWindow mainWindow = new MainWindow();
        frame.setContentPane(mainWindow.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        svgPanel.setBackground(Color.LIGHT_GRAY);
        // TODO: place custom component creation code here

    }
}
