package organization.reminder.services.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import organization.reminder.entity.Owner;
import organization.reminder.entity.Reminder;
import organization.reminder.repository.ReminderRepository;
import organization.reminder.services.ReminderService;

@Service
public class ReminderServiceImplementation implements ReminderService {
	
	private ReminderRepository reminderRepository;

	public ReminderServiceImplementation(ReminderRepository reminderRepository) {
		super();
		this.reminderRepository = reminderRepository;
	}

	@Override
	public List<Reminder> getAllReminders() {
		return reminderRepository.findAll();
	}

	@Override
	public Reminder saveReminder(Reminder reminder) {
		return reminderRepository.save(reminder);
	}

	@Override
	public Reminder getReminderBySerialNumber(int serialNumber) {
		return reminderRepository.findById(serialNumber).get();
	}

	@Override
	public Reminder updateReminder(Reminder reminder) {
		return reminderRepository.save(reminder);
	}

	@Override
	public void deleteReminderBySerialNumber(int serialNumber) {
		reminderRepository.deleteById(serialNumber);
	}

	
//	public List<Reminder> getRemindersByOwner(Owner owner) {
//		return reminderRepository.findAllByOwner(owner);
//	}

}
