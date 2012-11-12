package factory.interfaces;

import factory.Kit;

public interface Conveyor {
		public void msgAnimationDone();
		public void msgHeresEmptyKit(Kit k);
		public void msgNeedEmptyKit(KitRobot kr);
		public void msgExportKit(KitRobot kr, Kit k);

}
