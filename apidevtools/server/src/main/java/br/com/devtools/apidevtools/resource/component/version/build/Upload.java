package br.com.devtools.apidevtools.resource.component.version.build;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Upload {

	@Id
	private Long buildId;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="buildId", foreignKey=@ForeignKey(name="fk_Upload_Build"), insertable=false, updatable=false)
	private Build build;
	
	@Column(nullable=false, updatable=false)
	private byte[] bytes;

	public Long getId() {
		return buildId;
	}

	public void setId(Long id) {
		this.buildId = id;
	}

	public Build getBuild() {
		return build;
	}

	public void setBuild(Build build) {
		this.build = build;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
