package organization.reminder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import organization.reminder.entity.Owner;
import organization.reminder.entity.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {
	
//	@Query("SELECT r FROM Reminder r WHERE r.owner = :owner")
//	List<Reminder> findAllByOwner(@Param("owner") Owner owner);

}
