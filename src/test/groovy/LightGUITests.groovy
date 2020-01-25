import org.junit.Test
import teatro.play.graph.NodeWindow

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
        //Tsr a = new Tsr(2).setRqsGradient(true)
        //Tsr b = new Tsr(-4)
        //Tsr c = new Tsr(3).setRqsGradient(true)
        //Tsr s =  (a*b) + 2
        //Tsr x = new Tsr([s * (s+c)], "th(I[0])")

        NodeWindow w = new NodeWindow([
                ["1", "2", "3"],
                ["6", "4"],
                ["8"]
        ])
        def map = w.getBuilder().getSurface().getMap()
        Thread.sleep(3000)
        def things = map.getAll()
        assert things.size()>1
        double[] frame = new double[4]
        frame[0] = -4000000
        frame[1] = +4000000
        frame[2] = -4000000
        frame[3] = +4000000
        //assert label.getAllWithin(frame).size()>1
        map = map.removeAndUpdate(map.getAllWithin(frame).get(0))
        assert map!=null
        assert map.getAll().size()<things.size()
        Thread.sleep(15000)
        //while(true){
        //
        //}
    }




}
