package br.com.devtools.apidevtools.resource.person.acess.artifact;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acessId", foreignKey=@ForeignKey(name="fk_Session_Acess"))
	private Acess acess;
	
	@Column
	private LocalDateTime creation;
	
	@Column(length=50)
	private String ip;
	
	@Column(columnDefinition="text")
	private String useragent;
	
	@Column(length=128)
	private String hash;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Acess getAcess() {
		return acess;
	}

	public void setAcess(Acess acess) {
		this.acess = acess;
	}

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime data) {
		this.creation = data;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUseragent() {
		return useragent;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
}