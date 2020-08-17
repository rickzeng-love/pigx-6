package com.pig4cloud.pigx.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AtShiftWorkKey  implements Serializable {
	public AtShiftWorkKey(String term, Integer eid) {
		this.term = term;
		this.eid = eid;
	}

	private String term;
	private Integer eid;
}
