using Toybox.WatchUi as Ui;
using Toybox.Graphics as Gfx;
using Toybox.System as Sys;
using Util as U;
    
class ExerciseView extends Ui.View {

	var vars;
	
	var topLineIndent = 50;
	var bottomLineIndent = 50;
	var topInfoLineIndent = 94;
	var leftLineIndent = 80;
	var rightLineIndent = 80;
	var timer = new Timer.Timer();
	var activityTimer = new Timer.Timer();
	var restTimer = new Timer.Timer();
	var activitySeconds = 0;
	var restSeconds = 120;
	var lastReps = 12;
	var lastWeight = 55;
	var blink = false;
	
	var state = 0; //0 - Not started, 1 - Started, 2 - Enter reps, 3 - Enter weight

    function initialize() {
        View.initialize();
    }
    
    function onKey(keyCode) {
    	if(keyCode == 4) { //select key
    		incState();
    	} else if(keyCode == 8) {//up key
    		if(state == 2 && lastReps > 0) {
    			lastReps = lastReps - 1;	
    		} else if(state == 3 && lastWeight > 0) {
    			lastWeight = lastWeight - 1;	
    		}		
    	} else if(keyCode == 13) { //down key
    		if(state == 2) {
    			lastReps = lastReps + 1;	
    		} else if(state == 3) {
    			lastWeight = lastWeight + 1;	
    		}		
    	} 
    	Ui.requestUpdate();
    }
    
    function incState() {
    	state = state + 1;
    	if(state > 3){
    		state = 0;
    	}
    	if(state == 1) {
    	    restSeconds = 120;
    		restTimer.stop();
    		activityTimer.start( method(:onActivityTimer), 1000, true );
    	} else {
    		activitySeconds = 0;
    		activityTimer.stop();	
    	}
    	if(state == 2) {
    		restTimer.start( method(:onRestTimer), 1000, true );
    	}
    	Sys.println("State: " + state);
	}
    
    function onActivityTimer() {
    	activitySeconds = activitySeconds + 1;	
    	blink = !blink;
    	Ui.requestUpdate();	
    }
    
    function onRestTimer() {
    	if(restSeconds > 0){	
    		restSeconds = restSeconds - 1;
    		Ui.requestUpdate();	
    	} else {
    		restTimer.stop();	
    	}	
    }


    //! Called when this View is brought to the foreground. Restore
    //! the state of this View and prepare it to be shown. This includes
    //! loading resources into memory.
    
    function updateVars(dc) {
    	if(vars == null) {
    		vars = new GcVars(dc);
    	}	
    }
    
    function drawBackground(dc) {
    	dc.setColor( Gfx.COLOR_BLACK, Gfx.COLOR_BLACK);
        dc.clear();
        dc.setColor( Gfx.COLOR_BLACK, Gfx.COLOR_BLACK);
		dc.fillRectangle(0, 0, vars.width, vars.height);
		
		dc.setColor(Gfx.COLOR_DK_GRAY, Gfx.COLOR_BLACK);
		dc.drawLine(0, topLineIndent, vars.width, topLineIndent);	
		dc.drawLine(0, vars.height - bottomLineIndent, vars.width, vars.height - bottomLineIndent);	
		
		dc.drawLine(0, topInfoLineIndent, vars.width, topInfoLineIndent);	
		
		dc.drawLine(leftLineIndent, topInfoLineIndent, leftLineIndent, vars.height - bottomLineIndent);	
		dc.drawLine(vars.width - rightLineIndent, topInfoLineIndent, vars.width - rightLineIndent, vars.height - bottomLineIndent);
		
		if(state == 2) {
			dc.setColor(Gfx.COLOR_RED, Gfx.COLOR_BLACK);
			dc.setPenWidth(2);
			dc.drawLine(0, topInfoLineIndent, leftLineIndent, topInfoLineIndent);
			dc.drawLine(leftLineIndent, topInfoLineIndent, leftLineIndent, vars.height - bottomLineIndent);
			dc.drawLine(0, vars.height - bottomLineIndent, leftLineIndent, vars.height - bottomLineIndent);	
		}
		if(state == 3) {
			dc.setColor(Gfx.COLOR_RED, Gfx.COLOR_BLACK);
			dc.setPenWidth(2);
			dc.drawLine(vars.width - rightLineIndent, topInfoLineIndent, vars.width, topInfoLineIndent);
			dc.drawLine(vars.width - rightLineIndent, topInfoLineIndent, vars.width - rightLineIndent, vars.height - bottomLineIndent);
			dc.drawLine(vars.width - rightLineIndent, vars.height - bottomLineIndent, vars.width, vars.height - bottomLineIndent);	
		}
		if(state == 1 && blink == true) {
			dc.setColor(Gfx.COLOR_YELLOW, Gfx.COLOR_BLUE);
			dc.fillRectangle(leftLineIndent+1, topInfoLineIndent+1, vars.width - leftLineIndent - rightLineIndent - 1, vars.height - topInfoLineIndent - bottomLineIndent - 1);
		}
		dc.setPenWidth(1);
    }
    
    function drawStaticText(dc) {
        dc.setColor(Gfx.COLOR_LT_GRAY, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(vars.centerX, 0 ,  Gfx.FONT_TINY, "Clock", Gfx.TEXT_JUSTIFY_CENTER);
    	dc.drawText(vars.centerX, vars.height - bottomLineIndent ,  Gfx.FONT_TINY, "Workout", Gfx.TEXT_JUSTIFY_CENTER);
    	
    	dc.drawText(leftLineIndent / 2, topInfoLineIndent ,  Gfx.FONT_XTINY, "Reps", Gfx.TEXT_JUSTIFY_CENTER);
    	var activityRest = "Rest";
    	if(state == 1) {
    		activityRest = "Act";	
    		if(blink == true) {
    			dc.setColor(Gfx.COLOR_BLACK, Gfx.COLOR_TRANSPARENT);	
    		}
    	}
    	dc.drawText(vars.centerX, topInfoLineIndent ,  Gfx.FONT_XTINY, activityRest, Gfx.TEXT_JUSTIFY_CENTER);
    	dc.setColor(Gfx.COLOR_LT_GRAY, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(vars.width - rightLineIndent / 2, topInfoLineIndent ,  Gfx.FONT_XTINY, "Weight", Gfx.TEXT_JUSTIFY_CENTER);
    }
    
    function drawText(dc) {
        dc.setColor(Gfx.COLOR_WHITE, Gfx.COLOR_TRANSPARENT);
        //Time
		var clockTime = Sys.getClockTime();
        dc.drawText(vars.centerX, vars.fontTinyHeight,  Gfx.FONT_SMALL, U.nd(clockTime.hour) + ":" + U.nd(clockTime.min), Gfx.TEXT_JUSTIFY_CENTER);
        //Workout
    	dc.drawText(vars.centerX, vars.height - bottomLineIndent + vars.fontTinyHeight,  Gfx.FONT_SMALL, "00:00:00", Gfx.TEXT_JUSTIFY_CENTER);	
    	

		
		//Reps
		dc.setColor(Gfx.COLOR_BLUE, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(leftLineIndent / 2, topInfoLineIndent + vars.fontXTinyHeight - 5,  Gfx.FONT_NUMBER_MEDIUM, lastReps + "", Gfx.TEXT_JUSTIFY_CENTER);
    	//Activity - Rest
		dc.setColor(Gfx.COLOR_GREEN, Gfx.COLOR_TRANSPARENT);
		var activityRest = restSeconds; 
		if(state == 1) {
			activityRest = activitySeconds;	
			if(blink == true) {
				dc.setColor(Gfx.COLOR_BLACK, Gfx.COLOR_TRANSPARENT);	
			}
		}
    	dc.drawText(vars.centerX, topInfoLineIndent + vars.fontXTinyHeight - 5,  Gfx.FONT_NUMBER_MEDIUM, activityRest + "", Gfx.TEXT_JUSTIFY_CENTER);
    	//Weight
		dc.setColor(Gfx.COLOR_BLUE, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(vars.width - rightLineIndent / 2, topInfoLineIndent + vars.fontXTinyHeight - 5, Gfx.FONT_NUMBER_MEDIUM, lastWeight + "", Gfx.TEXT_JUSTIFY_CENTER);
    }
    
    function drawRepsWeightStat(reps, weight, time, dc, font, y) {
    	var statLine = "R:" + reps + "  W:" + weight + "  T:" + time;
    	var statLineWidth = dc.getTextWidthInPixels(statLine, font);
    	var half = statLineWidth / 2;
    	dc.setColor(Gfx.COLOR_LT_GRAY, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(vars.centerX - half, y,  font, "R:", Gfx.TEXT_JUSTIFY_LEFT);
    	dc.setColor(Gfx.COLOR_BLUE, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(vars.centerX - half + U.strWidth(dc, font, "R:"), y,  font, reps + "", Gfx.TEXT_JUSTIFY_LEFT);
    	dc.setColor(Gfx.COLOR_LT_GRAY, Gfx.COLOR_TRANSPARENT);	
    	dc.drawText(vars.centerX - half + U.strWidth(dc, font, "R:"+reps+"  "), y,  font, "W:", Gfx.TEXT_JUSTIFY_LEFT);
    	dc.setColor(Gfx.COLOR_GREEN, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(vars.centerX - half + U.strWidth(dc, font, "R:"+reps+"  W:"), y,  font, weight + "", Gfx.TEXT_JUSTIFY_LEFT);
    	dc.setColor(Gfx.COLOR_LT_GRAY, Gfx.COLOR_TRANSPARENT);	
      	dc.drawText(vars.centerX - half + U.strWidth(dc, font, "R:"+reps+"  W:"+weight+"  "), y,  font, "T:", Gfx.TEXT_JUSTIFY_LEFT);
      	dc.setColor(Gfx.COLOR_BLUE, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(vars.centerX - half + U.strWidth(dc, font, "R:"+reps+"  W:"+weight+"  T:"), y,  font, time + "", Gfx.TEXT_JUSTIFY_LEFT);		
    }
    
    function drawStats(dc) {
    	dc.setColor(Gfx.COLOR_LT_GRAY, Gfx.COLOR_TRANSPARENT);
    	dc.drawText(33, topLineIndent,  Gfx.FONT_XTINY, "Last", Gfx.TEXT_JUSTIFY_CENTER);
    	drawRepsWeightStat(12, 55, 120, dc, Gfx.FONT_XTINY, topLineIndent);
    	drawRepsWeightStat(9, 47, 92, dc, Gfx.FONT_SMALL, topLineIndent + vars.fontXTinyHeight);
    }

    //! Update the view
    function onUpdate(dc) {
        View.onUpdate(dc);
        dc.setPenWidth(1);
		updateVars(dc);
		drawBackground(dc);
		drawStaticText(dc);
		drawText(dc);
		drawStats(dc);
        Sys.println("Rendered");
    }
    
    function onTimer() {
    	Ui.requestUpdate();
    }

	function onShow() {
    	timer.start( method(:onTimer), 1000, true );	
    }
    
    function onHide() {
    	timer.stop();
    	activityTimer.stop();
    	restTimer.stop();
    }

}
