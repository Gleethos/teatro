package teatro.play.graph;

import teatro.core.backstage.GridSpaceMap;
import teatro.templates.NavigableSurface;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.util.List;

public class NodeWindow
{
    private static JFrame _window;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;

    NavigableSurface Surface;

    private JButton startButton;
    private JButton randomizeNetworkButton;

    public NodeWindow(List<Object> tree) {
        
        Surface = new NavigableSurface();
        SurfaceNode nnode = new SurfaceNode(tree, -400, 2500, this);
        addSurfaceObject(nnode);
        Surface.setPreferredSize(new Dimension(500, 500));
        Surface.setBackground(Color.black);

        System.setProperty("sun.java2d.opengl", "true");
        _window = new JFrame("Network Display");
        _window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _window.setSize(WIDTH, HEIGHT);
        _window.setLocationRelativeTo(null);
        _window.setResizable(true);
        //_window.setLayout(null);
        //_window.addKeyListener(this);
        _window.setFocusable(true);
        _window.setUndecorated(false);
        //_window.getRootPane().setWindowDecorationStyle(JRootPane.BOTTOM_ALIGNMENT);
        //((JComponent) _window).setBorder(new EmptyBorder(5, 5, 5, 5));
        // NodeGraphPanel.addKeyListener(this);
        // NodeGraphPanel.setFocusable(true);

        //_window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //_window.setUndecorated(true);
        _window.setVisible(true);

        JMenuBar MenuBar = new JMenuBar();
        MenuBar.setBackground(Color.black);
        _window.setJMenuBar(MenuBar);
        JMenu ProjectMenu = new JMenu("Project");
        ProjectMenu.setOpaque(true);
        //ProjectMenu.setBackground(Color.cyan);
        MenuBar.add(ProjectMenu);
        JMenuItem OpenOption = new JMenuItem("Open...");
        ProjectMenu.add(OpenOption);

        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BorderLayout());
        //MainPanel.addInto(new JLabel("AbstractSurfaceNode display:",JLabel.CENTER), BorderLayout.NORTH);
        MainPanel.setBackground(Color.CYAN);
        MainPanel.add(Surface, BorderLayout.CENTER);

        JPanel controlBarPanel = new JPanel();
        controlBarPanel.setLayout(new FlowLayout());
        controlBarPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        controlBarPanel.setBackground(Color.CYAN);
        randomizeNetworkButton = new JButton("RANDOMIZE EVERYTHING!");
        //randomizeNetworkButton.addActionListener(this);
        controlBarPanel.add(randomizeNetworkButton);

        startButton = new JButton("RUN NETWORK");
        // startButton.addActionListener(this);
        controlBarPanel.add(startButton);
        MainPanel.add(controlBarPanel, BorderLayout.SOUTH);

        _window.getContentPane().add(MainPanel);
        _window.setVisible(true);
    }

    public void addSurfaceObject(SurfaceNode PanelNeuron) {
        if (Surface.getMap() == null) {
            Surface.setMap(new GridSpaceMap(PanelNeuron.getX(), PanelNeuron.getY(), 10000));
        }
        Surface.setMap(Surface.getMap().addAndUpdate(PanelNeuron));
    }

}
