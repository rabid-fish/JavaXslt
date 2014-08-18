package com.github.rabid_fish.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "list")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbListWrapper {

	@XmlAnyElement(lax = true)
    private List<?> list;

	public JaxbListWrapper() {
		super();
	}
	
	public JaxbListWrapper(List<?> list) {
		this.list = list;
	}

}
