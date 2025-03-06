package db;

public class Role {
	
	private final Integer id;
	private final String name;
	
	public Role(String name) {
		this.id = null;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
}
