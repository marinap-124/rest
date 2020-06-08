package com.marina.parser;

import java.util.LinkedList;
import java.util.List;
import com.marina.model.Area;
import com.marina.model.Corona;
import lombok.Data;

@Data
public class ListHolder {
	List<Area> areas = new LinkedList<>();
	List<Corona> cases = new LinkedList<>();;

}
