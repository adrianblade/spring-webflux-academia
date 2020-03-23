package martin.site.documents;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estudiantes")
public class Estudiante {

	@Id
	private String id;
	
	@NotEmpty
	@Size(min = 3)
	private String nombres;
	
	@NotEmpty
	@Size(min = 3)
	private String apellidos;
	
	@NotEmpty
	@Size(min = 8)
	private String dni;
	
	@NotNull(message = "campo oligatorio")
	private double edad;
	
	public Estudiante() {}

	public Estudiante(String nombres, String apellidos,String dni, double edad) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni = dni;
		this.edad = edad;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public double getEdad() {
		return edad;
	}

	public void setEdad(double edad) {
		this.edad = edad;
	}
	
	
}
