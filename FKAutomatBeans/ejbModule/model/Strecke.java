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
@Table(name = "strecke")
public class Strecke implements Serializable {
	private static final long serialVersionUID = 6871452228725721686L;

	@SequenceGenerator(name = "streckeKeyGenerator", sequenceName = "strecke_Seq", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "streckeKeyGenerator")
	@Id
	private long sid;

	private String nach;

	private String von;

	//bi-directional many-to-one association to Fahrschein
	@OneToMany(mappedBy="strecke")
	private List<Fahrschein> fahrscheins;

	public Strecke() {
	}
	
	public Strecke(long sid, String von, String nach) {
	      this.sid = sid;
	      this.von = von;
	      this.nach = nach;
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

	@Override
	public String toString() {
		return "Strecke [sid=" + sid + ", nach=" + nach + ", von=" + von + "]";
	}

}