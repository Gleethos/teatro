package teatro;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StyleSet 
{
    private Color _background;
    private Color _groundlines;
    private Color _groundfont;

    private Color _elementground;
    private Color _elementlines;
    private Color _elementfont;

    private Color _highlight;

    private Color[] _palette;

    private static Map<String, StyleSet> _instances;

    static {
        _instances = new HashMap<>();

        _instances.put("nocture",
                new StyleSet(
                        Color.BLACK,
                        Color.CYAN,
                        Color.LIGHT_GRAY,

                        Color.CYAN,
                        Color.BLUE,
                        Color.BLACK,

                        Color.GREEN,

                        null
                )
        );

        _instances.put("laboratory",
                new StyleSet(
                        Color.WHITE,
                        Color.LIGHT_GRAY,
                        Color.BLACK,

                        Color.CYAN,
                        Color.DARK_GRAY,
                        Color.BLACK,

                        Color.GREEN,

                        null
                )
        );



    }


    private StyleSet(
            Color backgound,
            Color groundlines,
            Color groundfont,
            
            Color elementground,
            Color elementlines,
            Color elementfont,

            Color highlight,
            
            Color[] palette
    ){
        _background = backgound;
        _groundlines = groundlines;
        _groundfont = groundfont;

        _elementground = elementground;
        _elementlines = elementlines;
        _elementfont = elementfont;

        _highlight = highlight;

        _palette = palette;
    }


    public static Map<String, StyleSet> getInstances(){
        return _instances;
    }

    public Color getBackground() {
        return _background;
    }

    public Color getGroundlines() {
        return _groundlines;
    }

    public Color getGroundfont() {
        return _groundfont;
    }

    public Color getElementground() {
        return _elementground;
    }

    public Color getElementlines() {
        return _elementlines;
    }

    public Color getElementfont() {
        return _elementfont;
    }

    public Color getHighlight(){
        return _highlight;
    }

    public Color[] getPalette() {
        return _palette;
    }


    
    
    



}
