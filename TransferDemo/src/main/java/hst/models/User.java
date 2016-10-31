package hst.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Size(min = 3, max = 80)
	@Column(unique = false)
	private String email;

	@NotNull
	@Size(min = 2, max = 80)
	private String name;

	@NotNull
	private long balance;

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public User() {
	}

	public User(long id) {
		this.id = id;
	}
	
	public User(long id, String email, String name, long balance) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.balance = balance;
	}
	
	public User(String email, String name, long balance) {
		this.email = email;
		this.name = name;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long value) {
		this.id = value;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

} // class User
