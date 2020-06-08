package com.marina.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@XmlRootElement(name = "areasearch", namespace = "com.marina.area")
@Data
@ApiModel
@ToString(callSuper = true)
public class AreaSearch {
	@XmlElement()
	protected String label;
}
