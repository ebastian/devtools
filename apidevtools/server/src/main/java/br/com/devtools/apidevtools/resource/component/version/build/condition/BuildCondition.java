package br.com.devtools.apidevtools.resource.component.version.build.condition;

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

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.devtools.apidevtools.resource.component.version.build.Build;

@Audited
@Entity
@Table
public class BuildCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="buildId", foreignKey=@ForeignKey(name="fk_BuildCondition_Build"))
	private Build build;
	
	@Column(length=200)
	private String description;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private BuildConditionStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BuildConditionStatus getStatus() {
		return status;
	}

	public void setStatus(BuildConditionStatus status) {
		this.status = status;
	}

	public Build getBuild() {
		return build;
	}

	public void setBuild(Build build) {
		this.build = build;
	}
	
}
