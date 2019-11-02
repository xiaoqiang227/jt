package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_item_cat")
public class ItemCat extends BasePojo{
	@TableId(type = IdType.AUTO)//主键自增
	private Long id;			//商品分类id
	private Long parentId;		//父分类ID
	private String name;		//分类名称
	private Integer status;		//商品分类状态
	private Integer sortOrder;	//排序号
	private Boolean isParent;	//是否为父级
	
}
