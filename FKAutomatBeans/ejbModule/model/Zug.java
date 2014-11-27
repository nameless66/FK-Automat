package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ZUG database table.
 * 
 */
@Entity
@NamedQuery(name="Zug.findAll", query="SELECT z FROM Zug z")
public class Zug implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long zid;

	//bi-directional many-to-one association to Reservierung
	@OneToMany(mappedBy="zug")
	private List<Reservierung> reservierungs;

	public Zug() {
	}

	public long getZid() {
		return this.zid;
	}

	public void setZid(long zid) {
		this.zid = zid;
	}

	public List<Reservierung> getReservierungs() {
		return this.reservierungs;
	}

	public void setReservierungs(List<Reservierung> reservierungs) {
		this.reservierungs = reservierungs;
	}

	public Reservierung addReservierung(Reservierung reservierung) {
		getReservierungs().add(reservierung);
		reservierung.setZug(this);

		return reservierung;
	}

	public Reservierung removeReservierung(Reservierung reservierung) {
		getReservierungs().remove(reservierung);
		reservierung.setZug(null);

		return reservierung;
	}

}