package organization.reminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import organization.reminder.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	Owner findByOwnerName(String ownerName);

}
