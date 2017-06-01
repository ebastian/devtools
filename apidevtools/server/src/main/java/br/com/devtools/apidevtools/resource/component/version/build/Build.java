package br.com.devtools.apidevtools.resource.component.version.build;

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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.devtools.apidevtools.core.adapters.LocalDateTimerAdapter;
import br.com.devtools.apidevtools.resource.component.version.Version;

@Audited
@Entity
@Table(uniqueConstraints=@UniqueConstraint(name="unique_Build_on_Version", columnNames = {"versionId", "build"}))
public class Build {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="versionId", nullable=false, foreignKey=@ForeignKey(name="fk_Build_Version"))
	private Version version;

	@Column(nullable=false, updatable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime creation;
	
	@Column(insertable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime complete;
	
	@Column(insertable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime death;
	
	@Column(nullable=false)
	private Integer build;
	
	@Column(columnDefinition="text")
	private String notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime creation) {
		this.creation = creation;
	}

	public LocalDateTime getComplete() {
		return complete;
	}

	public void setComplete(LocalDateTime complete) {
		this.complete = complete;
	}

	public LocalDateTime getDeath() {
		return death;
	}

	public void setDeath(LocalDateTime death) {
		this.death = death;
	}

	public Integer getBuild() {
		return build;
	}

	public void setBuild(Integer build) {
		this.build = build;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
