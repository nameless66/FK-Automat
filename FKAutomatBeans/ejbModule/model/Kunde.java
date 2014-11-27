package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the KUNDE database table.
 * 
 */
@Entity
@NamedQuery(name="Kunde.findAll", query="SELECT k FROM Kunde k")
public class Kunde implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long kid;

	private String nachname;

	private String vorname;

	//bi-directional many-to-one association to Fahrschein
	@OneToMany(mappedBy="kunde")
	private List<Fahrschein> fahrscheins;

	public Kunde() {
	}

	public long getKid() {
		return this.kid;
	}

	public void setKid(long kid) {
		this.kid = kid;
	}

	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public List<Fahrschein> getFahrscheins() {
		return this.fahrscheins;
	}

	public void setFahrscheins(List<Fahrschein> fahrscheins) {
		this.fahrscheins = fahrscheins;
	}

	public Fahrschein addFahrschein(Fahrschein fahrschein) {
		getFahrscheins().add(fahrschein);
		fahrschein.setKunde(this);

		return fahrschein;
	}

	public Fahrschein removeFahrschein(Fahrschein fahrschein) {
		getFahrscheins().remove(fahrschein);
		fahrschein.setKunde(null);

		return fahrschein;
	}

}