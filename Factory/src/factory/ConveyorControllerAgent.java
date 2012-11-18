package factory;

import java.util.*;
import java.util.concurrent.Semaphore;

import factory.graphics.FrameKitAssemblyManager;
import factory.interfaces.*;
import factory.masterControl.MasterControl;
import agent.Agent;

public class ConveyorControllerAgent extends Agent implements ConveyorController {
	////Data	
	Conveyor conveyor;
	List<Kit> exported_kits = new ArrayList<Kit>();
	public enum Conveyor_State { WANTS_EMPTY_KIT, EMPTY_KIT_SENDING, NO_ACTION };
	Conveyor_State conveyor_state = Conveyor_State.NO_ACTION;
	
	Timer timer = new Timer();
	
	//UnitTesting Constructor
	public ConveyorControllerAgent() {
		super(null);
	}
	
	public ConveyorControllerAgent(MasterControl mc) {
		super(mc);
	}
	
	////Messages
	public void msgConveyorWantsEmptyKit() {
		debug("received msgConveyorWantsEmptyKit() from the Conveyor");
		if (conveyor_state.equals(Conveyor_State.NO_ACTION)) {
			conveyor_state = Conveyor_State.WANTS_EMPTY_KIT;
			stateChanged();
		}
	}
	
	
	////Scheduler
	public boolean pickAndExecuteAnAction() {
		if (conveyor_state.equals(Conveyor_State.WANTS_EMPTY_KIT)) {
			conveyor_state = Conveyor_State.EMPTY_KIT_SENDING;
			sendEmptyKit();
			return true;
		}
		return false;
	}
	
	////Actions
	private void sendEmptyKit() {
		//Creates a random time that it will take for the empty kit to make it to the kitting cell
		
		int delivery_time = (int) (1000 + (Math.random()*2000)); //Random time it will take for the empty kit to make it to the cell
		
		timer.schedule(new TimerTask(){
		    public void run(){
		    	//After this timer, the graphics needs to play the new kit animation and then after tell the ConveyorAgent about the new empty kit
		    	//The message to tell the Conveyor about the new kit is Conveyor.msgHeresEmptyKit(new Kit());
		    	
		    	DoEmptyKitArrivingAnimation();
		    	conveyor.msgHeresEmptyKit(new Kit());
		    	conveyor_state = Conveyor_State.NO_ACTION;
		    	server.command("cca fpm cmd emptyKitEntersCell");
				stateChanged();
		    }
		}, delivery_time);
	}
	
	////Animations
	private void DoEmptyKitArrivingAnimation() {
		debug("doing EmptyKitArriving Animation");
		server.command("cca fpm cmd emptyKitEntersCell");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//// Hacks / MISC
	public void setConveyor(Conveyor c) {
		this.conveyor = c;
	}

}

