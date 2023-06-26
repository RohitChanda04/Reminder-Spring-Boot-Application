package organization.reminder.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import organization.reminder.entity.Owner;
import organization.reminder.entity.Reminder;

public interface ReminderService {
	
	List<Reminder> getAllReminders();
	
	Reminder saveReminder(Reminder reminder);
	
	Reminder getReminderBySerialNumber(int serialNumber);
	
	Reminder updateReminder(Reminder reminder);
	
	void deleteReminderBySerialNumber(int serialNumber);

}
