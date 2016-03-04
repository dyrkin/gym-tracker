
class Util {

	//Normalize digit
	static function nd (digit) {
		if(digit < 10) {
			return "0" + digit;
		} else {
			return digit;
		}
	}
	
	static function strWidth(dc, font, str) {
		return dc.getTextWidthInPixels(str, font);	
	}
}