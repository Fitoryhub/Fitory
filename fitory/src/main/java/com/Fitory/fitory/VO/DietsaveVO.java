package com.Fitory.fitory.VO;

import com.Fitory.fitory.entity.Diet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DietsaveVO {
	private Diet diet;
	private List<FoodlistVO> foodlist;
}
