package com.marina.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "corona")
public class Corona {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String areaCode;
	private String weekName;
	private int count;

}
