package br.com.devtools.apidevtools.resource.user.privilege;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.devtools.apidevtools.resource.user.User;

@Audited
@Entity
@Table
public class Privilege {
	
	@Id
    private long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId", nullable=false, foreignKey=@ForeignKey(name="fk_privilege_user"))
	private User user;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private PrivilegeType type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PrivilegeType getType() {
		return type;
	}

	public void setType(PrivilegeType type) {
		this.type = type;
	}

}
