package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESERVIERUNG database table.
 * 
 */
@Entity
@NamedQuery(name="Reservierung.findAll", query="SELECT r FROM Reservierung r")
public class Reservierung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long rid;

	private BigDecimal platz;

	//bi-directional many-to-one association to Zug
	@ManyToOne
	@JoinColumn(name="ZUG_FK")
	private Zug zug;

	public Reservierung() {
	}

	public long getRid() {
		return this.rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public BigDecimal getPlatz() {
		return this.platz;
	}

	public void setPlatz(BigDecimal platz) {
		this.platz = platz;
	}

	public Zug getZug() {
		return this.zug;
	}

	public void setZug(Zug zug) {
		this.zug = zug;
	}

}