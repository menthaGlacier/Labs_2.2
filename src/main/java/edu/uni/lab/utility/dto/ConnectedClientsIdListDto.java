package edu.uni.lab.utility.dto;

import java.io.Serializable;
import java.util.List;

public class ConnectedClientsIdListDto implements Serializable {
	private List<Integer> list;

	public ConnectedClientsIdListDto(List<Integer> list) {
		this.list = list;
	}

	public List<Integer> idList() {
		return list;
	}
}
