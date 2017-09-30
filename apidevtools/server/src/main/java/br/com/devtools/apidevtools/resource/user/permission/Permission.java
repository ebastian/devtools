package br.com.devtools.apidevtools.resource.user.permission;

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
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Audited
@Entity
@Table
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="permissionGroupId", nullable=false, foreignKey=@ForeignKey(name="fk_permission"))
	private PermissionGroup group;
	
	@Column(nullable=false, length=500)
	private String className;
	
	@Column(nullable=false, length=100)
	private String authorize;
	
	@Transient
	private Boolean check;
	
	@Transient
	private String classDescription;
	
	@Transient
	private String authorizeDescription;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAuthorize() {
		return authorize;
	}

	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public String getClassDescription() {
		return classDescription;
	}

	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}

	public String getAuthorizeDescription() {
		return authorizeDescription;
	}

	public void setAuthorizeDescription(String authorizeDescription) {
		this.authorizeDescription = authorizeDescription;
	}

	public PermissionGroup getGroup() {
		return group;
	}

	public void setGroup(PermissionGroup group) {
		this.group = group;
	}
	
}