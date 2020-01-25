package teatro.core.backstage;

public interface SurfaceObject {
	
	public boolean killable();
	public boolean hasGripAt(double x, double y, Surface HostPanel);
	
	//public ArrayList<SurfaceRepaintSpace> moveCircular(Movement context);//double centerX, double centerY, double x, double y
	public void moveCircular(double[] data, Surface Surface);//double alpha, double centerX, double centerY
	public void moveDirectional(double[] data, Surface Surface);//double startX, double startY, double targX, double targY
	public void moveTo(double[] data, Surface Surface);//double x, double y
	public void updateOn(Surface HostPanel);
	
	public abstract void movementAt(double x, double y, Surface HostPanel);
	public abstract boolean clickedAt(double x, double y, Surface HostPanel);
	public abstract boolean doubleClickedAt(double x, double y, Surface HostPanel);
	
	public abstract double getX();
	public abstract double getY();
	
	public abstract double getRadius();
	
	public abstract double getLeftPeripheral();
	public abstract double getTopPeripheral();
	public abstract double getRightPeripheral();
	public abstract double getBottomPeripheral();


	public SurfaceRepaintSpace getRepaintSpace();

	public int getLayerID();
}
