package organization.reminder.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import organization.reminder.entity.Owner;
import organization.reminder.entity.Reminder;
import organization.reminder.repository.OwnerRepository;
import organization.reminder.services.OwnerService;

@Service
public class OwnerSeviceImplementation implements OwnerService {
	
	private OwnerRepository ownerRepository;

	public OwnerSeviceImplementation(OwnerRepository ownerRepository) {
		super();
		this.ownerRepository = ownerRepository;
	}

	@Override
	public Owner saveOwner(Owner owner) {
		// TODO Auto-generated method stub
		return ownerRepository.save(owner);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Owner getOwnerByOwnerID(int ownerID) {
		return ownerRepository.getById(ownerID);
	}

	@Override
	public Owner getOwnerByName(String ownerName) {
		// TODO Auto-generated method stub
		return  ownerRepository.findByOwnerName(ownerName);
	}

}
