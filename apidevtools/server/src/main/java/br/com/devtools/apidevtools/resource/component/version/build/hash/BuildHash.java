package br.com.devtools.apidevtools.resource.component.version.build.hash;

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

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.devtools.apidevtools.core.adapters.LocalDateTimerAdapter;
import br.com.devtools.apidevtools.resource.component.version.build.Build;

@Audited
@Entity
@Table
public class BuildHash {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	private Long buildId;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="buildId", foreignKey=@ForeignKey(name="fk_BuildHash_Build"), insertable=false, updatable=false)
	private Build build;
	
	@Column(length=128, nullable=false)
	private String hash;
	
	@Column(nullable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime creation;
	
	@Column(nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private BuildHashStatus status;

	public Long getBuildId() {
		return buildId;
	}

	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}

	public Build getBuild() {
		return build;
	}

	public void setBuild(Build build) {
		this.build = build;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime creation) {
		this.creation = creation;
	}

	public BuildHashStatus getStatus() {
		return status;
	}

	public void setStatus(BuildHashStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
