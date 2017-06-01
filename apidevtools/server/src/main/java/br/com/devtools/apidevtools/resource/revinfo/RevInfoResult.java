package br.com.devtools.apidevtools.resource.revinfo;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.envers.RevisionType;

import br.com.devtools.apidevtools.core.adapters.LocalDateTimerAdapter;

public class RevInfoResult<Model> {

	private Model object;
	private RevisionType revtype;
	
	private Long acessId;
	private Long personId;
	private String ip;
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime alteration;
	
	public Model getObject() {
		return object;
	}
	public void setObject(Model object) {
		this.object = object;
	}
	public Long getAcessId() {
		return acessId;
	}
	public void setAcessId(Long acessId) {
		this.acessId = acessId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public LocalDateTime getAlteration() {
		return alteration;
	}
	public void setAlteration(LocalDateTime alteration) {
		this.alteration = alteration;
	}
	public RevisionType getRevtype() {
		return revtype;
	}
	public void setRevtype(RevisionType revtype) {
		this.revtype = revtype;
	}
		
}
