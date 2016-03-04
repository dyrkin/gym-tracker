using Toybox.WatchUi as Ui;
using Toybox.System as Sys;

class ProjectDelegate extends Ui.BehaviorDelegate {

	var ev;
	
    function initialize(_ev) {
    	ev = _ev;
        BehaviorDelegate.initialize();
    }

    function onMenu() {
        Ui.pushView(new Rez.Menus.MainMenu(), new ProjectMenuDelegate(), Ui.SLIDE_UP);
        return true;
    }
    
    function onNextMode() {
    	Sys.println("Next mode tapped");
    	return true;	
    }
    
    function onNextPage() {
    	Sys.println("Next page tapped");	
    	return false;
    }
    
    function onKey(evt) {
    	Sys.println("Key tapped: "+evt.getKey());	
    	ev.onKey(evt.getKey());
    	return false;		
    }

}