import org.junit.Test
import theatro.play.graph.NodeWindow
import theatro.play.graph.NodeWindow

class LightGUITests {

    @Test
    void testBoard(){
        NodeWindow gb = new NodeWindow([
                ["1", "2", "3"],
                ["6", "4"],
                ["8"]
        ])

        Thread.sleep(100000)
    }

    @Test
    void testVisualizer()
    {
        ////=========================================================================
        //if(!System.getProperty("os.name").toLowerCase().contains("windows")) return
        ////=========================================================================

        NodeWindow w = new NodeWindow([
                ["1", "2", "3"],
                ["6", "4"],
                ["8"]
        ])
        def map = w.getSurface().getMap()
        Thread.sleep(3000)
        def things = map.getAll()
        int size = things.size()
        assert size>1
        double[] frame = new double[4]
        frame[0] = -4000000
        frame[1] = +4000000
        frame[2] = -4000000
        frame[3] = +4000000
        //assert label.getAllWithin(frame).size()>1
        map = map.removeAndUpdate(things[0])
        assert map!=null
        assert map.getAll().size()==size-1
        Thread.sleep(25000)

    }




}
