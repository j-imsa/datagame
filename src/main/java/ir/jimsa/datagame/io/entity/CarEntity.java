package ir.jimsa.datagame.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity(name = "cars")
public class CarEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String carId;

    @Column(nullable = false, length = 5)
    private String source;

    @Column(nullable = false, length = 10)
    private String codeListCode;

    @Column(nullable = false, length = 20, unique = true)
    private String code;

    private String displayValue;
    private String longDescription;

    private Date fromDate;
    private Date toDate;

    private String sortingPriority;

}
