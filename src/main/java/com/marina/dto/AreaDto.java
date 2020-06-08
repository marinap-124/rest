package com.marina.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.StringUtils;
import lombok.Data;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "areadto", namespace = "com.marina")
@Data
@ToString()

public class AreaDto {
	@XmlElement()
	private String code;

	@XmlElement()
	private String label;

	public void trim() {
		code = StringUtils.trim(code);
		label = StringUtils.trim(label);
	}
}
