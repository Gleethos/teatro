package teatro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class GraphSurfaceBuilder
{
    GraphSurface Surface;

    public GraphSurface getSurface() {
        return Surface;
    }

    public void addSurfaceObject(SurfaceNode PanelNeuron) {
        if (Surface.getMap() == null) {
            Surface.setMap(new GridSpaceMap(PanelNeuron.getX(), PanelNeuron.getY(), 10000));
        }
        Surface.setMap(Surface.getMap().addAndUpdate(PanelNeuron));
    }

    public GraphSurfaceBuilder(List<Object> source)
    {
        Surface = new GraphSurface();
        SurfaceNode nnode = new SurfaceNode(source, -400, 2500, this);
        addSurfaceObject(nnode);
        Surface.setPreferredSize(new Dimension(500, 500));
        Surface.setBackground(Color.black);
    }

}
