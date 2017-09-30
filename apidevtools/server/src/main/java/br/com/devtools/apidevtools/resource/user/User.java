package br.com.devtools.apidevtools.resource.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.envers.Audited;

import br.com.devtools.apidevtools.core.adapters.LocalDateTimerAdapter;

@Audited
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=200)
	private String name;
	
	@Column(length=200)
	private String email;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private UserType type;
	
	@Column(nullable=false, updatable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime creation;
	
	@Column(insertable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime death;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime creation) {
		this.creation = creation;
	}

	public LocalDateTime getDeath() {
		return death;
	}

	public void setDeath(LocalDateTime death) {
		this.death = death;
	}
	
}
