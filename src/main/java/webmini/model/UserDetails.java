package webmini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="USERSDETAILS")
public class UserDetails
{
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Enumerated
    @Column(name = "ROLE")
    private UserRole role;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
    	this.id = id;
	}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString()
    {
        return "id=" + id + ", name=" + name + ", email=" + email + ", role=" + role;
    }

    @Override
    public int hashCode()
    {
        int result =
                 (name != null ? name.hashCode() : 0 );

        result = 31*result
                + (email != null ? email.hashCode() : 0 );

        result = 31*result
                + (role != null ? role.hashCode() : 0 );

        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if(this == other) return true;
        if(other == null) return false;
        if(this.getClass() != other.getClass()) return false;

        UserDetails rhs = (UserDetails) other;

        if(name != null ? !name.equals(rhs.name) : rhs.name != null) return false;
        if(email != null ? !email.equals(rhs.email) : rhs.email != null) return false;
        if(role != null ? !role.equals(rhs.role) : rhs.role != null) return false;
        return true;

    }

}
