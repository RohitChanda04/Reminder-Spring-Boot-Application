package organization.reminder.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import organization.reminder.entity.Owner;
import organization.reminder.entity.Reminder;
import organization.reminder.services.OwnerService;
import organization.reminder.services.ReminderService;
import organization.reminder.services.impl.ReminderServiceImplementation;

@Controller
public class ReminderController {
	
	private int sessionID = -1; // global variable to store the ownerID and create the current session
	private Owner sessionOwner;
	private String sessionOwnerName;
	private ReminderService reminderService;
	private OwnerService ownerService;
	private LocalDate currentDate = LocalDate.now();
	private List<Reminder> selectedReminders = new ArrayList<>();

	public ReminderController(ReminderService reminderService, OwnerService ownerService) {
		super();
		this.reminderService = reminderService;
		this.ownerService = ownerService;
	}
	
	
	// log in methods and validations at the bottom
	
	
	
	// Handler method to handle the list of reminders and return model and view
	
	@GetMapping("/reminders")
	public String entry() {
		return "entry_page";
	}
	
	@GetMapping("/reminders_Admin")
	public String ListReminders(Model model) {
		model.addAttribute("reminders", reminderService.getAllReminders());
		return "reminders";
	}
	
	
	
	

	@GetMapping("/reminders/new")
	public String createReminderForm(Model model) {
		// create the reminder object to hold the reminder data
		System.out.println("1");
		Reminder reminder = new Reminder();
		model.addAttribute("reminder", reminder);
		return "create_reminder";
	}
	
	@PostMapping("/reminders_Admin")
	public String saveReminder(@ModelAttribute("reminder") Reminder reminder) {
		System.out.println("2");
		reminderService.saveReminder(reminder);
		return "redirect:/reminders_Admin";
	}
	
	
	
	// Method to update the "checked" attribute
	
	@PostMapping("/updateChecked/{serialNumber}")
	public String updateChecked(@PathVariable int serialNumber, Boolean isSelected) {
		Reminder reminder = reminderService.getReminderBySerialNumber(serialNumber);
		if(reminder.getChecked())
			reminder.setChecked(false);
		else
			reminder.setChecked(true);
		reminderService.saveReminder(reminder);
		return "redirect:/reminders_Admin";
	}
	
	
	
	// Method to show the list of expired reminders
	
	@GetMapping("/reminders/expired")
	public String expiredReminders(Model model) {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> expiredReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getDeadline().isBefore(currentDate))
				expiredReminders.add(reminder);
		}
		model.addAttribute("expiredReminders", expiredReminders);
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "expired_reminders";
	}
	
	
	
	// Method to show the list of expiring reminders
	
	@GetMapping("/reminders/expiring")
	public String expiringTodayReminders(Model model) {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> expiringReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getDeadline().isEqual(currentDate))
				expiringReminders.add(reminder);
		}
		model.addAttribute("expiringReminders", expiringReminders);
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "expiring_today";
	}
	
	
	
	
	// Method to check the blank values
	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
	
	
	
	
	// The next 4 methods are to update the reminders (future, expiring and expired)
	
	@GetMapping("/reminders/edit/{serialNumber}")
	public String editReminderForm(@PathVariable int serialNumber, Model model) {
		model.addAttribute("reminder", reminderService.getReminderBySerialNumber(serialNumber));
		return "edit_reminder";
	}
	
	@GetMapping("/reminders/expired/edit/{serialNumber}")
	public String editExpiredReminderForm(@PathVariable int serialNumber, Model model) {
		model.addAttribute("reminder", reminderService.getReminderBySerialNumber(serialNumber));
		return "edit_reminder";
	}
	
	@GetMapping("/reminders/expiring/edit/{serialNumber}")
	public String editExpiringReminderForm(@PathVariable int serialNumber, Model model) {
		model.addAttribute("reminder", reminderService.getReminderBySerialNumber(serialNumber));
		return "edit_reminder";
	}
	
	@PostMapping("/reminders/{serialNumber}")
	public String updateReminder(@PathVariable int serialNumber, @ModelAttribute("reminder") Reminder reminder, Model model) {
		// get the reminder from the database by the serial number
		Reminder existingReminder = reminderService.getReminderBySerialNumber(serialNumber);
		if (isBlank(reminder.getOwner()) || isBlank(reminder.getPriority()) || reminder.getDeadline() == null || isBlank(reminder.getBody())) {
	        model.addAttribute("error", "Please fill in all the fields.");
	        model.addAttribute("reminder", existingReminder);
	        return "edit_reminder";
	    }
		existingReminder.setSerialNumber(serialNumber);
		existingReminder.setOwner(reminder.getOwner());
		existingReminder.setPriority(reminder.getPriority());
		existingReminder.setDeadline(reminder.getDeadline());
		existingReminder.setBody(reminder.getBody());
		reminderService.updateReminder(existingReminder);
		if(existingReminder.getDeadline().isEqual(currentDate))
			return "redirect:/reminders/expiring";
		else
			return "redirect:/reminders_Admin";
	}

	
	

	// The next 2 methods are to handle the deletion of individual and all reminder(s) respectively
	
	@GetMapping("/reminders/{serialNumber}")
	public String deleteReminder(@PathVariable int serialNumber) {
		reminderService.deleteReminderBySerialNumber(serialNumber);
		return "redirect:/reminders_Admin";
	}
	
	@GetMapping("/reminders/deleteAll")
	public String deleteAllReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		for(Reminder reminder: reminders) {
			reminderService.deleteReminderBySerialNumber(reminder.getSerialNumber());
		}
		return "redirect:/reminders_Admin";
	}
	
	
	
	
	// The next 2 methods are to handle the deletion of individual and all expiring reminder(s) respectively
	
	@GetMapping("/reminders/expiring/{serialNumber}")
	public String deleteExpiringReminder(@PathVariable int serialNumber) {
		reminderService.deleteReminderBySerialNumber(serialNumber);
		return "redirect:/reminders/expiring";
	}
	
	@GetMapping("/reminders/expiring/deleteAll")
	public String deleteAllExpiringReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> expiringReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getDeadline().isEqual(currentDate))
				expiringReminders.add(reminder);
		}
		for(Reminder expiring: expiringReminders) {
			reminderService.deleteReminderBySerialNumber(expiring.getSerialNumber());
		}
		return "redirect:/reminders/expiring";
	}
	
	
	
	
	// The next 2 methods are to handle the deletion of individual and all expired reminder(s) respectively
	
	@GetMapping("/reminders/expired/{serialNumber}")
	public String deleteExpiredReminder(@PathVariable int serialNumber) {
		reminderService.deleteReminderBySerialNumber(serialNumber);
		return "redirect:/reminders/expired";
	}
	
	@GetMapping("/reminders/expired/deleteAll")
	public String deleteAllExpiredReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> expiredReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getDeadline().isBefore(currentDate))
				expiredReminders.add(reminder);
		}
		for(Reminder expired: expiredReminders) {
			reminderService.deleteReminderBySerialNumber(expired.getSerialNumber());
		}
		return "redirect:/reminders/expired";
	}
	
	
	
	
	// Method to delete the selected reminders from the reminder.html page
	@GetMapping("/reminders/deleteSelected")
	public String deleteSelectedReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		for(Reminder reminder: reminders) {
			if(reminder.getChecked())
				reminderService.deleteReminderBySerialNumber(reminder.getSerialNumber());
			else
				continue;
		}
		return "redirect:/reminders_Admin";
	}
	
	@GetMapping("/reminders/expiring/deleteSelected")
	public String deleteSelectedExpiringReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		for(Reminder reminder: reminders) {
			if(reminder.getChecked())
				reminderService.deleteReminderBySerialNumber(reminder.getSerialNumber());
			else
				continue;
		}
		return "redirect:/reminders/expiring";
	}
	
	@GetMapping("/reminders/expired/deleteSelected")
	public String deleteSelectedExpiredReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		for(Reminder reminder: reminders) {
			if(reminder.getChecked())
				reminderService.deleteReminderBySerialNumber(reminder.getSerialNumber());
			else
				continue;
		}
		return "redirect:/reminders/expired";
	}
	
	
	
	
	
	
	
	
	// User login and sign up
	
		@GetMapping("/owners/new")
		public String createOwnerForm(Model model) {
			// create the owner object to hold the owner data
			Owner owner = new Owner();
			model.addAttribute("owner", owner);
			return "signup";
		}
		
		@PostMapping("/owners")
		public String saveOwner(@ModelAttribute("owner") Owner owner, Model model) {
			ownerService.saveOwner(owner);
			sessionID = owner.getOwnerID();
			sessionOwnerName = ownerService.getOwnerByOwnerID(sessionID).getOwnerName();
			model.addAttribute("sessionOwnerName", sessionOwnerName);
			return "redirect:/reminders_User";
		}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		System.out.println("Entry point of login...");
		model.addAttribute("user", new Owner());
		return "Entry_page";
	}

	@PostMapping("/login")
	public String processLogin(@ModelAttribute("user") Owner user) {
		Owner owner = ownerService.getOwnerByName(user.getOwnerName());
		if (owner != null && owner.getPassword().equals(user.getPassword())) {
			sessionOwner = owner;
			sessionOwnerName = sessionOwner.getOwnerName().toUpperCase();
			sessionID = owner.getOwnerID(); // Set the session ownerID
			System.out.println("Session ID: "+sessionID);
			return "redirect:/reminders/check";
		}
		else {
			// Invalid login credentials, display error message
			return "entry_page";
		}
	}
		
	@GetMapping("/logout")
	public String processLogout() {
		sessionID = -1; // Reset the session ownerID
		System.out.println("Session ID: "+sessionID);
		return "redirect:/login";
	}
	
	
	@GetMapping("/reminders/check")
	public String showReminders(Model model) {
	    if (sessionID == 1) {
	        // Admin access, display all reminders
	        List<Reminder> reminders = reminderService.getAllReminders();
	        model.addAttribute("reminders", reminders);
	        return "reminders"; // loads "reminders.html" template
	    }
	    else
	    	return "redirect:/reminders_User";
	}
	
	
	@GetMapping("/reminders_User")
	public String ListUserReminders(Model model) {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> userReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getOwnerObject().getOwnerID() == sessionID)
				userReminders.add(reminder);
		}
		model.addAttribute("reminders", userReminders);
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "user_reminders";
	}
	
	@GetMapping("/reminders_User/new")
	public String createReminderUserForm(Model model) {
		// create the reminder object to hold the reminder data
		Reminder reminder = new Reminder();
		model.addAttribute("reminder", reminder);
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "create_reminder_user";
	}
	
	@PostMapping("/reminders_User")
	public String saveUserReminder(@ModelAttribute("reminder") Reminder reminder) {
		System.out.println("saving...");
		Owner owner = ownerService.getOwnerByOwnerID(sessionID);
		reminder.setOwnerObject(owner);
		reminder.setOwner(owner.getOwnerName());
		reminderService.saveReminder(reminder);
		return "redirect:/reminders_User";
	}
	
	@GetMapping("/reminders/expired/userReminders")
	public String expiredUserReminders(Model model) {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> userReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getOwnerObject().getOwnerID() == sessionID)
				userReminders.add(reminder);
		}
		List<Reminder> expiredReminders = new ArrayList<>();
		for(Reminder reminder: userReminders) {
			if(reminder.getDeadline().isBefore(currentDate))
				expiredReminders.add(reminder);
		}
		model.addAttribute("expiredReminders", expiredReminders);
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "expired_user_reminders";
	}
	
	@GetMapping("/reminders/expiring/userReminders")
	public String expiringTodayUserReminders(Model model) {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> userReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getOwnerObject().getOwnerID() == sessionID)
				userReminders.add(reminder);
		}
		List<Reminder> expiringReminders = new ArrayList<>();
		for(Reminder reminder: userReminders) {
			if(reminder.getDeadline().isEqual(currentDate))
				expiringReminders.add(reminder);
		}
		model.addAttribute("expiringReminders", expiringReminders);
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "expiring_today_user_reminders";
	}
	
	@GetMapping("/reminders_User/edit/{serialNumber}")
	public String editUserReminderForm(@PathVariable int serialNumber, Model model) {
		model.addAttribute("reminder", reminderService.getReminderBySerialNumber(serialNumber));
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "edit_user_reminder";
	}
	
	@GetMapping("/reminders_User/expired/edit/{serialNumber}")
	public String editExpiredUserReminderForm(@PathVariable int serialNumber, Model model) {
		model.addAttribute("reminder", reminderService.getReminderBySerialNumber(serialNumber));
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "edit_reminder";
	}
	
	@GetMapping("/reminders_User/expiring/edit/{serialNumber}")
	public String editExpiringUserReminderForm(@PathVariable int serialNumber, Model model) {
		model.addAttribute("reminder", reminderService.getReminderBySerialNumber(serialNumber));
		model.addAttribute("sessionOwnerName", sessionOwnerName);
		return "edit_reminder";
	}
	
	@PostMapping("/reminders_User/{serialNumber}")
	public String updateUserReminder(@PathVariable int serialNumber, @ModelAttribute("reminder") Reminder reminder, Model model) {
		// get the reminder from the database by the serial number
		Reminder existingReminder = reminderService.getReminderBySerialNumber(serialNumber);
		existingReminder.setSerialNumber(serialNumber);
		existingReminder.setOwner(sessionOwner.getOwnerName());
		existingReminder.setPriority(reminder.getPriority());
		existingReminder.setDeadline(reminder.getDeadline());
		existingReminder.setBody(reminder.getBody());
		existingReminder.setOwnerObject(sessionOwner);
		reminderService.updateReminder(existingReminder);
		if(existingReminder.getDeadline().isEqual(currentDate))
			return "redirect:/reminders/expiring/userReminders";
		else
			return "redirect:/reminders_User";
	}
	
	@GetMapping("/reminders_User/{serialNumber}")
	public String deleteUserReminder(@PathVariable int serialNumber) {
		reminderService.deleteReminderBySerialNumber(serialNumber);
		return "redirect:/reminders_User";
	}
	
	@GetMapping("/reminders_User/deleteAll")
	public String deleteAllUserReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> userReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getOwnerObject().getOwnerID() == sessionID)
				userReminders.add(reminder);
		}
		for(Reminder userReminder: userReminders) {
			reminderService.deleteReminderBySerialNumber(userReminder.getSerialNumber());
		}
		return "redirect:/reminders_User";
	}
	
	@GetMapping("/reminders_User/deleteSelected")
	public String deleteSelectedUserReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		List<Reminder> userReminders = new ArrayList<>();
		for(Reminder reminder: reminders) {
			if(reminder.getOwnerObject().getOwnerID() == sessionID)
				userReminders.add(reminder);
		}
		for(Reminder userReminder: userReminders) {
			if(userReminder.getChecked())
				reminderService.deleteReminderBySerialNumber(userReminder.getSerialNumber());
			else
				continue;
		}
		return "redirect:/reminders_User";
	}
	
	@GetMapping("/reminders_User/expiring/deleteSelected")
	public String deleteSelectedExpiringUserReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		for(Reminder reminder: reminders) {
			if(reminder.getChecked())
				reminderService.deleteReminderBySerialNumber(reminder.getSerialNumber());
			else
				continue;
		}
		return "redirect:/reminders/expiring/userReminders";
	}
	
	@GetMapping("/reminders_User/expired/deleteSelected")
	public String deleteSelectedExpiredUserReminders() {
		List<Reminder> reminders = reminderService.getAllReminders();
		for(Reminder reminder: reminders) {
			if(reminder.getChecked())
				reminderService.deleteReminderBySerialNumber(reminder.getSerialNumber());
			else
				continue;
		}
		return "redirect:/reminders/expired/userReminders";
	}

}
