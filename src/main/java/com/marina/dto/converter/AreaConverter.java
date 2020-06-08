package com.marina.dto.converter;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.marina.dto.AreaDto;
import com.marina.model.Area;

@Component
public class AreaConverter {
    public Area convertDto2Area(AreaDto areaDto) {
        if (areaDto == null)
            return null;

        areaDto.trim();
        Area area = new Area();
        area.setCode(areaDto.getCode());
        area.setLabel(areaDto.getLabel());
        return area;
    }
    
    public AreaDto convertArea2Dto(Area area) {
        if(area == null)
            return null;
        AreaDto areaDto = new AreaDto();
        areaDto.setCode(area.getCode());
        areaDto.setLabel(area.getLabel());

        return areaDto;
    }
    
    public List<AreaDto> convertArea2Dto(List<Area> areas) {
        if(areas == null || areas.isEmpty())
            return new ArrayList<>();

        return areas.stream().map(this::convertArea2Dto).collect(Collectors.toList());
    }
    
    public List<Area> convertDto2Area(List<AreaDto> areas) {
        if(areas == null || areas.isEmpty())
            return new ArrayList<>();

        return areas.stream().map(this::convertDto2Area).collect(Collectors.toList());
    }
}
