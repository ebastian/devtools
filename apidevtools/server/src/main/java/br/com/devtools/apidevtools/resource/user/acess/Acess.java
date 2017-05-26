package br.com.devtools.apidevtools.resource.user.acess;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.devtools.apidevtools.core.adapters.LocalDateTimerAdapter;
import br.com.devtools.apidevtools.resource.user.User;

@Entity
@Table
public class Acess {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId", nullable=false, foreignKey=@ForeignKey(name="fk_Acess_User"))
	private User user;
	
	@Column(length=40, nullable=false)
	private String name;
	
	@Column(nullable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime creation;
	
	@Column
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime death;
	
	@Column(length=64, nullable=false)
	private String password;
	
	@Column(length=128, nullable=false)
	private String hash;
	
	@Column(nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private AcessStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public AcessStatus getStatus() {
		return status;
	}

	public void setStatus(AcessStatus status) {
		this.status = status;
	}
	
}
