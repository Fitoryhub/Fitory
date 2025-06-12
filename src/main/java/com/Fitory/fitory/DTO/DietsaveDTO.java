package com.Fitory.fitory.dto;

import java.util.List;

import com.Fitory.fitory.entity.Diet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DietsaveDTO {
	private Diet diet;
	private List<FoodlistDTO> foodlist;
}
