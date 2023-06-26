package organization.reminder.entity;

import java.sql.Date;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "reminders")
public class Reminder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int serialNumber;
	
	@Column(name = "owner", nullable = false)
	private String owner;
	
	@Column(name = "priority")
	private String priority;
	
	@Column(name = "reminder_body")
	private String body;
	
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@Column(name = "date_created")
	private Date dateCreated = new Date(System.currentTimeMillis());
	
	@Column(name = "deadline")
	private LocalDate deadline;
	
	@Column(name = "checked")
	private Boolean checked; // 0 is false, 1 is true
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerID", nullable = false)
    private Owner ownerObject;
	
	public Reminder() {
		this.checked = false;
	}
	
	public Reminder(String owner, String priority, String body, LocalDate deadline) {
		super();
		this.owner = owner;
		this.priority = priority;
		this.body = body;
		this.deadline = deadline;
	}

	public int getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Owner getOwnerObject() {
		return ownerObject;
	}

	public void setOwnerObject(Owner ownerObject) {
		this.ownerObject = ownerObject;
	}

}
