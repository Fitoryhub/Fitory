package com.Fitory.fitory.VO;

import java.util.List;

import com.Fitory.fitory.Entity.Diet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DietsaveVO {
	private Diet diet;
	private List<FoodlistVO> foodlist;
}
