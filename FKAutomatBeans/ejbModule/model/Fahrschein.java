package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FAHRSCHEIN database table.
 * 
 */
@Entity
@NamedQuery(name="Fahrschein.findAll", query="SELECT f FROM Fahrschein f")
public class Fahrschein implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long fid;

	//bi-directional many-to-one association to Kunde
	@ManyToOne
	@JoinColumn(name="FK_KUNDE")
	private Kunde kunde;

	//bi-directional many-to-one association to Strecke
	@ManyToOne
	@JoinColumn(name="FK_STRECKE")
	private Strecke strecke;

	public Fahrschein() {
	}

	public long getFid() {
		return this.fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public Kunde getKunde() {
		return this.kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public Strecke getStrecke() {
		return this.strecke;
	}

	public void setStrecke(Strecke strecke) {
		this.strecke = strecke;
	}

}