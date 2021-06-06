package com.yapily.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "MARVEL_CHARACTER")
public class MarvelCharacter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "CHARACTER_ID")
	private Integer characterID;

	@Column(name = "CHARACTER_NAME")
	private String characterName;

	public MarvelCharacter() {
	}

	public MarvelCharacter(final Integer characterID, final String characterName) {
		this.characterID = characterID;
		this.characterName = characterName;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Integer getCharacterID() {
		return characterID;
	}

	public void setCharacterID(final Integer characterID) {
		this.characterID = characterID;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(final String characterName) {
		this.characterName = characterName;
	}
}
