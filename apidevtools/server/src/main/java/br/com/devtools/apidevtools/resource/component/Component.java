package br.com.devtools.apidevtools.resource.component;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.devtools.apidevtools.core.adapters.LocalDateTimerAdapter;

@Entity
@Table
public class Component {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100, nullable=false)
	private String name;
	
	@Column(columnDefinition="text")
	private String description;
	
	@Column(nullable=false)
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime creation;
	
	@Column
	@XmlJavaTypeAdapter(value=LocalDateTimerAdapter.class)
	private LocalDateTime death;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime creation) {
		this.creation = creation;
	}

	public LocalDateTime getDeath() {
		return death;
	}

	public void setDeath(LocalDateTime death) {
		this.death = death;
	}
	
}
