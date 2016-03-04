using Toybox.Timer as Timer;
using Toybox.WatchUi as Ui;
using Toybox.Graphics as Gfx;
using Toybox.System as Sys;

class ScrollableText {
	var text;
	var position = 0;

	function initialize(_text) {
    	text = _text;
    }
    
    function cutText(dc, width, font) {
    	var textWidth = width + 1;
    	var initialText = text.substring(position, text.length());
    	var initialTextWidth = dc.getTextWidthInPixels(initialText, font);
    	if(initialTextWidth <= width) {
    		position = -1;
    		return initialText;
    	}
    	while(textWidth > width) {
    		textWidth = dc.getTextWidthInPixels(initialText, font);
    		initialText = initialText.substring(0, initialText.length() - 1);
    	}
    	Sys.println(initialText);
    	return initialText;
    }
    
    function redraw(dc, x, y, width, font) {
       	dc.drawText(x, y, font, cutText(dc, width, font), Gfx.TEXT_JUSTIFY_LEFT);
    	position = position + 1;		
    }
}