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
@Table(name = "reservierung", schema = "BAUMGAJA")
public class Reservierung implements Serializable {
	private static final long serialVersionUID = 1L;

	@SequenceGenerator(name = "reservierungKeyGenerator", sequenceName = "reservierung_seq", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservierungKeyGenerator")
	@Id
	private long rid;

	private BigDecimal platz;

	//bi-directional many-to-one association to Zug
	@ManyToOne
	@JoinColumn(name="STRECKE_FK")
	private Strecke strecke;

	public Reservierung() {
	}
	
	public Reservierung(long rid, BigDecimal platz, Strecke strecke) {
	      this.rid = rid;
	      
	      this.strecke = strecke;
	   }

	public long getRid() {
		return this.rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}


	public Strecke getStrecke() {
		return this.strecke;
	}

	public void setStrecke(Strecke strecke) {
		this.strecke = strecke;
	}

	@Override
	public String toString() {
		return "Reservierung [rid=" + rid + ", platz=" + platz 
				+ "]";
	}

}