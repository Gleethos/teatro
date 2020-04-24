package theatro.play.graph;

import theatro.TUI;
import theatro.core.backstage.GridSpaceMap;
import theatro.core.backstage.Surface;
import theatro.templates.NavigableSurface;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.util.List;

public class NodeWindow implements TUI
{
    private static JFrame _window;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;

    NavigableSurface _surface;

    public NodeWindow(List<Object> tree) {
        
        _surface = new NavigableSurface();
        SurfaceNode nnode = new SurfaceNode(tree, -400, 2500, this);
        addSurfaceObject(nnode);
        //Surface.setPreferredSize(new Dimension(500, 500));
        _surface.setBackground(Color.black);

        System.setProperty("sun.java2d.opengl", "true");
        _window = new JFrame("Graph");
        _window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _window.setSize(WIDTH, HEIGHT);
        _window.setLocationRelativeTo(null);
        _window.setResizable(true);
        _window.setFocusable(true);
        _window.setUndecorated(false);


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
        MainPanel.add(_surface, BorderLayout.CENTER);

        _window.getContentPane().add(MainPanel);
        _window.setVisible(true);
    }

    public void addSurfaceObject(SurfaceNode n) {
        if (_surface.getMap() == null) _surface.setMap(new GridSpaceMap(n.getX(), n.getY(), 10000));
        _surface.setMap(_surface.getMap().addAndUpdate(n));
    }

    @Override
    public Surface getSurface() {
        return _surface;
    }


}
