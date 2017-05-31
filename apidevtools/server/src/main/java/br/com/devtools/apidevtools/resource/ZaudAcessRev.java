package br.com.devtools.apidevtools.resource;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(UserRevisionListener.class)
public class ZaudAcessRev extends DefaultRevisionEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long acessId;
	private Long personId;
	
	@Column(length=50)
	private String ip;
	
	private LocalDateTime date;

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long acessId) {
		this.personId = acessId;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getAcessId() {
		return acessId;
	}

	public void setAcessId(Long acessId) {
		this.acessId = acessId;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
}

