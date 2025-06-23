package com.Fitory.fitory.dto;

import com.Fitory.fitory.entity.Diet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DietsaveDTO {
	private Diet diet;
	private List<FoodlistDTO> foodlist;
}
