package br.com.devtools.apidevtools.resource.component;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.devtools.apidevtools.core.adapters.LocalDateAdapter;

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
	@XmlJavaTypeAdapter(value=LocalDateAdapter.class)
	private LocalDate creation;
	
	@Column
	@XmlJavaTypeAdapter(value=LocalDateAdapter.class)
	private LocalDate death;

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

	public LocalDate getCreation() {
		return creation;
	}

	public void setCreation(LocalDate creation) {
		this.creation = creation;
	}

	public LocalDate getDeath() {
		return death;
	}

	public void setDeath(LocalDate death) {
		this.death = death;
	}
	
}
