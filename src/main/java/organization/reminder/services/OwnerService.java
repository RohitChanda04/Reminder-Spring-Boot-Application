package organization.reminder.services;

import java.util.List;

import organization.reminder.entity.Owner;
import organization.reminder.entity.Reminder;

public interface OwnerService {
	
	Owner saveOwner(Owner owner);
	
	Owner getOwnerByOwnerID(int ownerID);

	Owner getOwnerByName(String ownerName);

}
