package martin.site.documents;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursos")
public class Curso {

	@Id
	private String id;
	
	@Size(min = 2)
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String sigla;
	
	private boolean estado;

	public Curso() {}
	
	public Curso(String nombre, String sigla, boolean estado) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	
}
