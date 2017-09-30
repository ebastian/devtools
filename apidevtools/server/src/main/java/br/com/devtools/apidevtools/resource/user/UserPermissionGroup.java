package br.com.devtools.apidevtools.resource.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import br.com.devtools.apidevtools.resource.user.permission.PermissionGroup;

@Audited
@Entity
@Table
public class UserPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId", nullable=false, foreignKey=@ForeignKey(name="fk_user"))
	private User user;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="permissionGroupId", nullable=false, foreignKey=@ForeignKey(name="fk_permissiongroup"))
	private PermissionGroup grupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PermissionGroup getGrupo() {
		return grupo;
	}

	public void setGrupo(PermissionGroup grupo) {
		this.grupo = grupo;
	}
	
}
