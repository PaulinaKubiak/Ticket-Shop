package pl.lodz.p.it.spjava.TSS.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "KONTO", uniqueConstraints = {
    @UniqueConstraint(name = "UNIQUE_LOGIN", columnNames = "LOGIN")
})

@DiscriminatorColumn(name = "Konto", discriminatorType = DiscriminatorType.STRING)

@DiscriminatorValue(PoziomDostepu.KluczePoziomuDostepu.NOWEKONTO_KEY)

@NamedQueries({
    @NamedQuery(name = "Konto.findAll", query = "SELECT k FROM Konto k")
    , 
    @NamedQuery(name = "Konto.findByLogin", query = "SELECT k FROM Konto k WHERE k.login = :lg")
    ,
    @NamedQuery(name = "Konto.findByNoweKonto", query = "SELECT k FROM NoweKonto k")
    ,
    @NamedQuery(name = "Konto.znajdzPoAutoryzowane", query = "SELECT k FROM Konto k WHERE k.autoryzowane = true")
    , 
    @NamedQuery(name = "Konto.znajdzKontaPoAutoryzacji", query = "SELECT k FROM Konto k WHERE k.modyfikowanePrzez IS NOT null")
})

@TableGenerator(name = "KontoIdGen", table = "TableGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "KontoAccountGen")

public class Konto extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "KontoIdGen")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "IMIE", nullable = false)
    private String imie;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "NAZWISKO", nullable = false)
    private String nazwisko;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LOGIN", nullable = false, updatable = false)
    private String login;

    @NotNull
    @Size(max = 64)
    @Column(name = "HASLO", nullable = false, updatable = true)
    private String haslo;

    @Size(max = 200)
    @Column(name = "PYTANIEKONTROLNE", nullable = false)
    private String pytaniekontrolne;

    @Size(max = 150)
    @Column(name = "ODPOWIEDZKONTROLNA", nullable = false)
    private String odpowiedzkontrolna;

    @NotNull
    @Column(name = "AKTYWNE", nullable = false)
    private boolean aktywne;

    @NotNull
    @JoinColumn(name = "DATA_MODYFIKACJI", nullable = true)
    @OneToOne
    @Temporal(TemporalType.DATE)
    private Date dataModyfikacji;

    @NotNull
    @JoinColumn(name = "MODYFIKOWANEPRZEZ", nullable = true)
    @OneToOne
    private Administrator modyfikowanePrzez;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTORYZOWANE", nullable = false)
    private boolean autoryzowane;

    public Konto() {
    }

    public Konto(Long id) {
        this.id = id;
    }

    public Konto(Long id, String imie, String nazwisko, String email, String login, String haslo, String pytaniekontrolne, String odpowiedzkontrolna, boolean aktywne, Date dataModyfikacji, Administrator modyfikowanePrzez, boolean autoryzowane) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.login = login;
        this.haslo = haslo;
        this.pytaniekontrolne = pytaniekontrolne;
        this.odpowiedzkontrolna = odpowiedzkontrolna;
        this.aktywne = aktywne;
        this.dataModyfikacji = dataModyfikacji;
        this.modyfikowanePrzez = modyfikowanePrzez;
        this.autoryzowane = autoryzowane;
    }

    public Konto(Konto konto) {
        konto.getId();
        konto.getImie();
        konto.getNazwisko();
        konto.getEmail();
        konto.getPytaniekontrolne();
        konto.getOdpowiedzkontrolna();
        konto.getLogin();
        konto.getHaslo();
    }

    public Long getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(haslo.getBytes(StandardCharsets.UTF_8));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                stringBuffer.append(Integer.toString((encodedhash[i] & 0xff) + 0x100, 16).substring(1));
            }
            this.haslo = stringBuffer.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Konto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPytaniekontrolne() {
        return pytaniekontrolne;
    }

    public void setPytaniekontrolne(String pytaniekontrolne) {
        this.pytaniekontrolne = pytaniekontrolne;
    }

    public String getOdpowiedzkontrolna() {
        return odpowiedzkontrolna;
    }

    public void setOdpowiedzkontrolna(String odpowiedzkontrolna) {
        this.odpowiedzkontrolna = odpowiedzkontrolna;
    }

    public boolean isAktywne() {
        return aktywne;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public Date getDataModyfikacji() {
        return dataModyfikacji;
    }

    public void setDataModyfikacji(Date dataModyfikacji) {
        this.dataModyfikacji = dataModyfikacji;
    }

    public Administrator getModyfikowanePrzez() {
        return modyfikowanePrzez;
    }

    public void setModyfikowanePrzez(Administrator modyfikowanePrzez) {
        this.modyfikowanePrzez = modyfikowanePrzez;
    }

    public boolean isAutoryzowane() {
        return autoryzowane;
    }

    public void setAutoryzowane(boolean autoryzowane) {
        this.autoryzowane = autoryzowane;
    }

}
