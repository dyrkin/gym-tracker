using Toybox.Graphics as Gfx;

class GcVars {
	var height;
	var width;
	var centerY;
	var centerX;
	var fontXTinyHeight;
	var fontTinyHeight;
	
	function initialize(dc) {
		height = dc.getHeight();
    	width = dc.getWidth();
        centerY = height / 2;
    	centerX = width / 2;
    	fontXTinyHeight = dc.getFontHeight(Gfx.FONT_XTINY);
    	fontTinyHeight = dc.getFontHeight(Gfx.FONT_TINY);
	}
	
}