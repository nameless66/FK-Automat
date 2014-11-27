package bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the MITARBEITER database table.
 * 
 */
@Entity
@Table(name = "mitarbeiter", schema = "MAUTEMA1")
public class Employee implements Serializable {
	private static final long serialVersionUID = 6849024972454221038L;

	@Id
	private long persnr;

	@Temporal(TemporalType.DATE)
	private Date geburtsdatum;

	private String name;

	private String vorname;

	// bi-directional many-to-one association to Abteilung
	@ManyToOne
	@JoinColumn(name = "ABTNR")
	private Department abteilung;

	public Employee() {
	}

	public long getPersnr() {
		return this.persnr;
	}

	public void setPersnr(long persnr) {
		this.persnr = persnr;
	}

	public Date getGeburtsdatum() {
		return this.geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Department getAbteilung() {
		return this.abteilung;
	}

	public void setAbteilung(Department abteilung) {
		this.abteilung = abteilung;
	}

	@Override
	public String toString() {
		return "Employee [persnr=" + persnr + ", geburtsdatum=" + geburtsdatum
				+ ", name=" + name + ", vorname=" + vorname + "]";
	}

}
