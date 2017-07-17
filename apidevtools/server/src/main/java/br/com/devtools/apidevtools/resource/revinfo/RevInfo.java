package br.com.devtools.apidevtools.resource.revinfo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Table(schema="aud")
@RevisionEntity(RevInfoListener.class)
public class RevInfo extends DefaultRevisionEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long acessId;
	private Long userId;
	
	@Column(length=50)
	private String ip;
	
	private LocalDateTime alteration;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long acessId) {
		this.userId = acessId;
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
	public LocalDateTime getAlteration() {
		return alteration;
	}
	public void setAlteration(LocalDateTime date) {
		this.alteration = date;
	}
	
}

