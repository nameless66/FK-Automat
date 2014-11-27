package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the STRECKE database table.
 * 
 */
@Entity
@NamedQuery(name="Strecke.findAll", query="SELECT s FROM Strecke s")
public class Strecke implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long sid;

	private String nach;

	private String von;

	//bi-directional many-to-one association to Fahrschein
	@OneToMany(mappedBy="strecke")
	private List<Fahrschein> fahrscheins;

	public Strecke() {
	}

	public long getSid() {
		return this.sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public String getNach() {
		return this.nach;
	}

	public void setNach(String nach) {
		this.nach = nach;
	}

	public String getVon() {
		return this.von;
	}

	public void setVon(String von) {
		this.von = von;
	}

	public List<Fahrschein> getFahrscheins() {
		return this.fahrscheins;
	}

	public void setFahrscheins(List<Fahrschein> fahrscheins) {
		this.fahrscheins = fahrscheins;
	}

	public Fahrschein addFahrschein(Fahrschein fahrschein) {
		getFahrscheins().add(fahrschein);
		fahrschein.setStrecke(this);

		return fahrschein;
	}

	public Fahrschein removeFahrschein(Fahrschein fahrschein) {
		getFahrscheins().remove(fahrschein);
		fahrschein.setStrecke(null);

		return fahrschein;
	}

}