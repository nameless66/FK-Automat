package bean;
import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "abteilung", schema = "MAUTEMA1")
public class Department implements Serializable {
	private static final long serialVersionUID = -1388228924414864206L;

	@Id
	private long abtnr;

	private String bezeichnung;

	// bi-directional many-to-one association to Mitarbeiter
	@OneToMany(mappedBy = "abteilung", fetch=FetchType.EAGER)
	private Set<Employee> mitarbeiters;

	public Department() {
	}

	public long getAbtnr() {
		return this.abtnr;
	}

	public void setAbtnr(long abtnr) {
		this.abtnr = abtnr;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public Set<Employee> getMitarbeiters() {
		return this.mitarbeiters;
	}

	public void setMitarbeiters(Set<Employee> mitarbeiters) {
		this.mitarbeiters = mitarbeiters;
	}

	@Override
	public String toString() {
//		return "Department [abtnr=" + abtnr + ", bezeichnung=" + bezeichnung
//				+ ", mitarbeiters=" + mitarbeiters + "]";
		return "Department [abtnr=" + abtnr + ", bezeichnung=" + bezeichnung + "]";
	}

}
