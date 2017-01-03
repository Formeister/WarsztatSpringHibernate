package com.warsztat.springhibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
 
import org.hibernate.validator.constraints.NotEmpty;
 

@Entity
@Table(name="USER")
public class User implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
 
    @NotEmpty
    @Column(name="PESEL", unique=true, nullable=false)
    private String pesel;
     
    @NotEmpty
    @Column(name="PASSWORD", nullable=false)
    private String password;
         
    @NotEmpty
    @Column(name="FIRST_NAME", nullable=false)
    private String firstName;
 
    @NotEmpty
    @Column(name="LAST_NAME", nullable=false)
    private String lastName;
 
    @NotEmpty
    @Column(name="EMAIL", nullable=false)
    private String email;
 
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ACTION", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "ACTION_ID") })
    private Set<Action> actions = new HashSet<Action>();
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getPesel() {
        return pesel;
    }
 
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Set<Action> getActions() {
        return actions;
    }
 
    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((pesel == null) ? 0 : pesel.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (pesel == null) {
            if (other.pesel != null)
                return false;
        } else if (!pesel.equals(other.pesel))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "Użytkownik [id=" + id + ", pesel=" + pesel + ", hasło=" + password
                + ", imię=" + firstName + ", nazwisko=" + lastName
                + ", email=" + email + "]";
    }
    
}