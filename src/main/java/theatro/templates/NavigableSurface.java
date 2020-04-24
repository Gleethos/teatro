package theatro.templates;


import theatro.core.backstage.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.Timer;


public class NavigableSurface extends JPanel implements Surface, ActionListener
{
    // I/O:
    private SurfaceListener _listener;
    // Navigation:
    private AffineTransform _scaler = new AffineTransform();
    private AffineTransform _translator = new AffineTransform();

    //Space search label:
    AbstractSpaceMap _surfaceMap = null;

    // Animations:
    private theatro.core.backstage.Animator _animator;

    private int _lastSenseX;
    private int _lastSenseY;

    private Timer _frameTimer;

    // Sense Focus:
    private SurfaceObject _focusObject;

    public SurfaceObject getFocusObject() {
        return _focusObject;
    }

    public void setFocusObject(SurfaceObject object) {
        _focusObject = object;
    }

    @Override
    protected boolean requestFocusInWindow(boolean temporary) {
        return super.requestFocusInWindow(temporary);
    }

    private double CenterX = getWidth() / 2;
    private double CenterY = getHeight() / 2;

    private int[] LongPress;
    private int[] Swipe;
    private int[] Click;
    private int[] DoubleClick;
    private int[] Sense;
    private double[] Scaling;
    private int[] Drag;
    private int[] Press;

    public int[] getLongPress() {
        return LongPress;
    }

    public int[] getSwipe() {
        return Swipe;
    }

    public int[] getDoubleClick() {
        return DoubleClick;
    }

    public int[] getSense() {
        return Sense;
    }

    public double[] getScaling() {
        return Scaling;
    }

    public int[] getDrag() {
        return Drag;
    }

    public void setDrag(int[] newDragging) {
        Drag = newDragging;
    }

    public void setPress(int[] newPress) {
        Press = newPress;
    }

    public void setLongPress(int[] newPress) {
        LongPress = newPress;
    }

    public int[] getPress() {
        return Press;
    }

    public void setClick(int[] newClick) {
        Click = newClick;
    }

    public void setScaling(double[] newScaling) {
        Scaling = newScaling;
    }

    public void setDoubleClick(int[] newClick) {
        DoubleClick = newClick;
    }

    public void setMovement(int[] newMove) {
        Sense = newMove;
    }

    public void setSwipe(int[] newSwipe) {
        Swipe = newSwipe;
    }

    private boolean _touchMode = true;

    private boolean _drawRepaintSpaces = true;
    private boolean _advancedRendering = true;
    private boolean _mapRendering = false;

    private long _frameStart;
    private int _frameDelta;

    private double _fps;
    private double _smoothFPS;

    public interface SurfaceAction {
        boolean actOn(NavigableSurface panel);
    }

    public AffineTransform getScaler() {
        return _scaler;
    }

    public AffineTransform getTranslator() {
        return _translator;
    }

    public double getCenterX() {
        return CenterX;
    }

    public double getCenterY() {
        return CenterY;
    }

    public void setCenterX(double value) {
        CenterX = value;
    }

    public void setCenterY(double value) {
        CenterY = value;
    }

    public SurfaceListener getListener() {
        return _listener;
    }

    // Settings:
    public boolean isAntialiasing() {
        return _advancedRendering;
    }

    public boolean isMaprendering() {
        return _mapRendering;
    }

    public boolean isClipRendering() {
        return _drawRepaintSpaces;
    }

    public boolean isInTouchMode() {
        return _touchMode;
    }

    private SurfaceRepaintSpace _currentFrameSpace;

    @Override
    public SurfaceRepaintSpace getCurrentFrameSpace() {
        return _currentFrameSpace;
    }

    List<ObjectPainter>[] _layers = new List[]{
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>(),
            new ArrayList<ObjectPainter>()
    };

    public List<ObjectPainter>[] layers() {
        return _layers;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setPressAction(SurfaceAction action) {
        applyPress = action;
    }

    SurfaceAction applyPress = Utility.DefaultPressAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setLongPressAction(SurfaceAction action) {
        applyLongPress = action;
    }

    SurfaceAction applyLongPress = Utility.DefaultLongPressAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setDoubleClickAction(SurfaceAction action) {
        applyDoubleClick = action;
    }

    SurfaceAction applyDoubleClick = Utility.DefaultDoubleClickAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setClickAction(SurfaceAction action) {
        applyClick = action;
    }

    SurfaceAction applyClick = Utility.DefaultClickAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setScaleAction(SurfaceAction action) {
        applyScaling = action;
    }

    SurfaceAction applyScaling = Utility.DefaultScalingAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setSenseAction(SurfaceAction action) {
        applySense = action;
    }

    SurfaceAction applySense = Utility.DefaultSenseAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setSwipeAction(SurfaceAction action) {
        applySwipe = action;
    }

    SurfaceAction applySwipe = Utility.DefaultSwipeAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setDragAction(SurfaceAction action) {
        applyDrag = action;
    }

    SurfaceAction applyDrag = Utility.DefaultDragAction;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public interface SurfacePainter {
        void actOn(NavigableSurface panel, Graphics2D brush);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setPaintAction(SurfacePainter action) {
        _painter = action;
    }

    SurfacePainter _painter;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //=======================================================================================

    public NavigableSurface()
    {
        _painter = NavigableSurface.Utility.DefaultPainter;
        _animator = new Animator();
        _listener = new SurfaceListener(this);
        this.addMouseListener(_listener);
        this.addMouseWheelListener(_listener);
        this.addMouseMotionListener(_listener);
        setBackground(Color.black);
        repaint(0, 0, getWidth(), getHeight());
        this.scaleAt(getWidth() / 2, getHeight() / 2, 0.001);
        _frameTimer = new Timer(25, this);
        _frameTimer.start();
    }

    public void updateAndRedraw()
    {
        _listener.updateOn(this);
        boolean redraw =
        applySwipe.actOn(this) ||
        applyClick.actOn(this) ||
        applyLongPress.actOn(this) ||
        applyDoubleClick.actOn(this) ||
        applyScaling.actOn(this) ||
        applySense.actOn(this) ||
        applyDrag.actOn(this);

        if(!redraw) return;
        System.out.println(new Random().nextDouble());
        double tlx = realX(0);
        double tly = realY(0);
        double brx = tlx+((getWidth()) / (getScale()*1)) ;
        double bry = tly+((getHeight())/ (getScale()*1));
        _currentFrameSpace = new SurfaceRepaintSpace(tlx, tly, brx, bry);

        if (_surfaceMap != null)
        {
            LinkedList<SurfaceObject> killList = new LinkedList<SurfaceObject>();
            LinkedList<SurfaceObject> updateList = new LinkedList<SurfaceObject>();
            _surfaceMap.applyToAll(
                    (SurfaceObject thing) -> {
                        updateList.add(thing);
                        if ((thing).killable()) killList.add((thing));
                        return true;
                    }
            );
            if (killList.size() > 0) System.out.println("KILLING OCCURRED! :O");
            Surface surface = this;
            updateList.forEach((SurfaceObject thing) -> thing.updateOn(surface));
            for(int ki = 0; ki<killList.size(); ki++) _surfaceMap = _surfaceMap.removeAndUpdate(killList.get(ki));
        }

        // REPAINT:
        repaint(0, 0, getWidth(), getHeight());

        _frameDelta = (int) (Math.abs((System.nanoTime() - _frameStart)));
        _fps = 1e9 / (((double) _frameDelta));
        if (_fps > 60) {
            double time = (_fps - 60.0) / 4;
            try {
                if (time < 50) {
                    Thread.sleep((long) time);
                }
            } catch (Exception e) {

            }
        }
        _frameDelta = (int) (Math.abs((System.nanoTime() - _frameStart)));
        _fps = 1e9 / (((double) _frameDelta));
        _smoothFPS = (_fps + 12 * _smoothFPS) / 13;
        _frameStart = System.nanoTime();
    }

    //================================================================================================================================
    public int lastSenseX() {
        return _lastSenseX;
    }

    public int lastSenseY() {
        return _lastSenseY;
    }

    public void setLastSenseX(int value) {
        _lastSenseX = value;
    }

    public void setLastSenseY(int value) {
        _lastSenseY = value;
    }

    public double realLastSenseX() {
        return realX(_lastSenseX);
    }

    public double realLastSenseY() {
        return realY(_lastSenseY);
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D brush = (Graphics2D) g;
        if (this.isAntialiasing())
            brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        else brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Surface shift:
        if ((this.getCenterY() != ((double) this.getHeight()) / 2) || (this.getCenterX() != ((double) this.getWidth()) / 2)) {
            double newCenterX = ((double) this.getWidth()) / 2;
            double newCenterY = ((double) this.getHeight()) / 2;
            this.translate((newCenterX - this.getCenterX()), (newCenterY - this.getCenterY()));
            this.setCenterX(newCenterX);
            this.setCenterY(newCenterY);
        }
        // Surface scale:
        brush.transform(this.getScaler());
        brush.transform(this.getTranslator());

        //Map visualization
        if (this.isMaprendering() && getMap() != null) getMap().paintStructure(brush);

        //================================
        _painter.actOn(this, brush);
        //================================

        //RENDERING VISUALIZATION:
        if (this.isClipRendering()) {
            brush.setColor(Color.WHITE);
            for (int lid = 0; lid < layers().length; lid++) {
                layers()[lid].forEach((space) -> space.paint(brush));
                layers()[lid] = new ArrayList<ObjectPainter>();
            }
        }
        //===
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    public void movementAt(int x, int y) {
        int[] newSense = new int[2];
        newSense[0] = x;
        newSense[1] = y;
        Sense = newSense;
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    private SurfaceObject find(double x, double y, boolean topMost, SurfaceObject upToException, AbstractSpaceMap.MapAction action) {
        List<SurfaceObject> List = null;
        if (_surfaceMap != null) List = _surfaceMap.findAllAt(x, y, action);
        if (List == null) return null;
        SurfaceObject best = null;
        ListIterator<SurfaceObject> Iterator = List.listIterator();
        while (Iterator.hasNext()) {
            SurfaceObject current = Iterator.next();
            if (best == null) {
                best = current;
                if (upToException != null) {
                    if (topMost) {
                        if (upToException.getLayerID() < current.getLayerID()) {
                            best = null;
                        }
                    } else {
                        if (upToException.getLayerID() > current.getLayerID()) {
                            best = null;
                        }
                    }
                }
            } else {
                if (topMost) {
                    if (upToException != null) {
                        if (current.getLayerID() > best.getLayerID() && upToException.getLayerID() > current.getLayerID()) {
                            best = current;
                        }
                    } else {
                        if ((current).getLayerID() > best.getLayerID()) {
                            best = current;
                        }
                    }
                }  else {
                    if (upToException != null) {
                        if (current.getLayerID() < best.getLayerID() && upToException.getLayerID() < current.getLayerID()) {
                            best = current;
                        }
                    } else {
                        if ((current).getLayerID() >= best.getLayerID()) {
                            best = current;
                        }
                    }
                }
            }
        }
        if (upToException != null && best != null) {
            if (best == upToException) {
                return null;
            }
        }
        return best;
    }

    //============================================================

    //--------------------------------------------------------------------------------------------------------------------------------
    public SurfaceObject findObject(double x, double y, boolean topMost, SurfaceObject upToException) {
        return find(x, y, topMost, upToException,
                (SurfaceObject element) -> {
                    if (element instanceof SurfaceObject) {
                        if ((element).hasGripAt(x, y, this)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    public double realX(double x) {
        return ((x) / _scaler.getScaleX()) - _translator.getTranslateX();
    }

    public double realY(double y) {
        return ((y) / _scaler.getScaleY()) - _translator.getTranslateY();
    }

    public double realToOnPanelX(double x) {
        return ((x + _translator.getTranslateX()) * _scaler.getScaleX());
    }

    public double realToOnPanelY(double y) {
        return ((y + _translator.getTranslateY()) * _scaler.getScaleY());
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    public void draggedBy(int[] Vector) {
        Swipe = Vector;
    }

    public void translate(double translateX, double translateY) {
        _translator.translate(translateX * 1 / _scaler.getScaleX(), translateY * 1 / _scaler.getScaleY());
        repaint(0, 0, getWidth(), getHeight());//Repaint spaces?
    }
    //--------------------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------------------------------------------------------------------------------------------
    //GraphSurface interaction:
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void scaleAt(int x, int y, double scaleFactor) {
        double[] newScaling = new double[3];
        newScaling[0] = x;
        newScaling[1] = y;
        newScaling[2] = scaleFactor;
        Scaling = newScaling;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void clickedAt(int x, int y) {
        int[] newClick = new int[2];
        newClick[0] = x;
        newClick[1] = y;
        Click = newClick;
    }

    public int[] getClick() {
        return Click;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void pressedAt(int x, int y) {
        int[] newDrag = new int[2];
        newDrag[0] = x;
        newDrag[1] = y;
        Drag = newDrag;
    }

    @Override
    public void longPressedAt(int x, int y) {
        int[] newLongPress = new int[2];
        newLongPress[0] = x;
        newLongPress[1] = y;
        LongPress = newLongPress;
    }

    @Override
    public void releasedAt(int x, int y) {
    }

    @Override
    public double getScale() {
        return _scaler.getScaleX();
    }

    @Override
    public void doubleClickedAt(int x, int y) {
        int[] newDoubleClick = new int[2];
        newDoubleClick[0] = x;
        newDoubleClick[1] = y;
        DoubleClick = newDoubleClick;
    }

    @Override
    public void draggedAt(int x, int y) {
        int[] newDrag = new int[2];
        newDrag[0] = x;
        newDrag[1] = y;
        Drag = newDrag;
    }

    @Override
    public Animator getAnimator() {
        return _animator;
    }

    @Override
    public int getFrameDelta() {
        return _frameDelta;
    }

    @Override
    public AbstractSpaceMap getMap() {
        return _surfaceMap;
    }

    @Override
    public void setMap(AbstractSpaceMap newMap) {
        _surfaceMap = newMap;
    }

    public static class Utility
    {
        //-------------------------------------
        static SurfacePainter DefaultPainter =
                (surface, brush) ->
                {
                    if (surface.isMaprendering()) {
                        if (surface.getMap() != null) {
                            surface.getMap().paintStructure(brush);
                        }
                    }
                    brush.setColor(Color.GREEN);
                    brush.fillOval((int) surface.realX(surface.lastSenseX()) - 5, (int) surface.realY(surface.lastSenseY()) - 5, 10, 10);
                };
        //-------------------------------------
        static SurfaceAction DefaultPressAction =
                (surface) ->
                {
                    int[] Press = surface.getPress();
                    if (Press == null) return false;
                    int x = Press[0];
                    int y = Press[1];
                    surface.setPress(null);
                    return true;
                };
        //-------------------------------------
        static SurfaceAction DefaultLongPressAction =
                (surface) ->
                {
                    int[] LongPress = surface.getLongPress();
                    if (LongPress == null) return false;
                    int x = LongPress[0];
                    int y = LongPress[1];
                    surface.setLongPress(null);
                    return true;
                };
        //-------------------------------------
        static SurfaceAction DefaultClickAction =
                (surface) ->
                {
                    int[] Click = surface.getClick();
                    if (Click == null) return false;
                    int x = Click[0];
                    int y = Click[1];
                    surface.getListener().setDragStart(x, y);
                    SurfaceObject found = surface.findObject(surface.realX(x), surface.realY(y), true, null);
                    if (found != null) {
                        if (found.clickedAt(surface.realX(x), surface.realY(y), surface)) {
                            found.clickedAt(x, y, surface);
                        }
                    }
                    surface.setClick(null);
                    surface.repaint(0, 0, surface.getWidth(), surface.getHeight());
                    return true;
                };
        //-------------------------------------
        static SurfaceAction DefaultDoubleClickAction = (surface) ->
        {
            int[] DoubleClick = surface.getDoubleClick();
            if (DoubleClick == null) return false;
            int x = DoubleClick[0];
            int y = DoubleClick[1];
            SurfaceObject found = surface.findObject(surface.realX(x), surface.realY(y), true, null);
            if (found != null) {
                found.doubleClickedAt(surface.realX(x), surface.realY(y), surface);
            } else {
                //Double clicked in empty space!
            }
            surface.setDoubleClick(null);
            return true;
        };
        //-------------------------------------
        static SurfaceAction DefaultScalingAction =
                (surface) ->
                {
                    if (surface.getScaling() == null) return false;
                    int x = (int) surface.getScaling()[0];
                    int y = (int) surface.getScaling()[1];
                    double scaleFactor = surface.getScaling()[2];
                    surface.setLastSenseX(x);
                    surface.setLastSenseY(y);
                    surface.translate(-x, -y);
                    surface.getScaler().scale((scaleFactor), (scaleFactor));
                    surface.translate(x, y);
                    surface.setScaling(null);
                    surface.repaint(0, 0, surface.getWidth(), surface.getHeight());
                    return true;
                };
        //-------------------------------------
        static SurfaceAction DefaultSenseAction =
                (surface) ->
                {
                    int[] Sense = surface.getSense();
                    if (Sense == null) return false;
                    int x = Sense[0];
                    int y = Sense[1];
                    surface.setMovement(null);
                    double realX = surface.realX(x);
                    double realY = surface.realY(y);
                    if (surface.getFocusObject() != null) surface.getFocusObject().movementAt(realX, realY, surface);
                    surface.setFocusObject(surface.findObject(realX, realY, true, null));
                    surface.setLastSenseX(x);
                    surface.setLastSenseY(y);
                    return true;
                };
        //-------------------------------------
        static SurfaceAction DefaultSwipeAction =
                (surface) ->
                {
                    int[] Swipe = surface.getSwipe();
                    if (Swipe == null) return false;
                    if (surface.isInTouchMode()) {
                        surface.setFocusObject(surface.findObject(surface.realX(Swipe[0]), surface.realY(Swipe[1]), true, null));
                        if (surface.getFocusObject() != null) {
                            surface.setLastSenseX(Swipe[2]);
                            surface.setLastSenseY(Swipe[3]);
                            double[] data = {surface.realX(Swipe[0]), surface.realY(Swipe[1]), surface.realX(Swipe[2]), surface.realY(Swipe[3])};
                            surface.getFocusObject().moveDirectional(data, surface);
                            Swipe[0] = Swipe[2];
                            Swipe[1] = Swipe[3];
                            return true;
                        }
                        if (surface.getFocusObject() == null) {
                            surface.translate(Swipe[2] - Swipe[0], Swipe[3] - Swipe[1]);
                            Swipe[0] = Swipe[2];
                            Swipe[1] = Swipe[3];
                            surface.setSwipe(null);
                        }
                    } else {
                        if (Swipe.length == 4) surface.translate(Swipe[2] - Swipe[0], Swipe[3] - Swipe[1]);
                        Swipe[0] = Swipe[2];
                        Swipe[1] = Swipe[3];
                        surface.setSwipe(null);
                    }
                    surface.setSwipe(null);
                    return true;
                };
        //-------------------------------------
        static SurfaceAction DefaultDragAction =
                (surface) ->
                {
                    int[] Drag = surface.getSwipe();
                    if (Drag == null) return false;
                    if (surface.isInTouchMode()) {

                    } else {

                    }
                    surface.setSwipe(null);
                    return true;
                };
        //-------------------------------------

    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == _frameTimer) updateAndRedraw();
    }

}
