package github.spencerb.garcore;

import java.util.HashMap;

import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.saveddata.SavedData;

public class MachineSystem extends SavedData {

	// https://docs.fabricmc.net/develop/serialization/saved-data
	// TODO: save positions of machines here. Handles the adding and removing of machines.
	public HashMap<GlobalPos, TestMachine> machines;
	
	MachineSystem() {
		machines = new HashMap<GlobalPos, TestMachine>();
	}
	
}
