package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.anno.Cache_Find;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	//@Autowired
	private Jedis jedis;

	@Override
	public ItemCat findItemCatById(Long itemCatId) {
		
		return itemCatMapper.selectById(itemCatId);
	}
	
	/**
	 * VO对象~~~转换~~~POJO
	 * 思路:
	 * 	1.根据parentId查询一级商品分类信息 List<ItemCat>
	 * 	2.遍历List<ItemCat>~~~itemCat(DB)
	 * 	  ~~~~~~Id/name~~~~~~~EasyUITree
	 *  3.EasyUITree~~~~~封装到List集合中返回
	 *  
	 *  原则:一个方法只完成一个任务.
	 */
	@Override
	@Cache_Find
	public List<EasyUITree> findEasyUITree(Long parentId) {
		List<ItemCat> itemCatList = 
				findItemCatByParentId(parentId);
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		for (ItemCat itemCat : itemCatList) {
			Long id = itemCat.getId();
			String text = itemCat.getName();
			//如果是父级closed 否则open
			String state = 
					itemCat.getIsParent()?"closed":"open";
			EasyUITree easyUITree = 
					new EasyUITree(id, text, state);
			treeList.add(easyUITree);
		}
		return treeList;
	}
	
	public List<ItemCat> findItemCatByParentId(Long parentId){
		QueryWrapper<ItemCat> queryWrapper = 
				new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = 
				itemCatMapper.selectList(queryWrapper);
		return itemCatList;
	}
	
	/**
	 * 缓存操作流程:
	 * 	1.先查询缓存
	 *  2.结果为nul,说明用户第一次查询.应该先查询数据库
	 *         将数据库数据保存到缓存中.
	 *  3.结果不为null, json串.将json数据转化为对象
	 */
	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		String key = "ITEM_CAT_"+parentId;
		String result = jedis.get(key); //获取json数据
		List<EasyUITree> treeList = new ArrayList<>();
		if(StringUtils.isEmpty(result)) {
			//用户第一次查询,应该查询数据库
			treeList = findEasyUITree(parentId);
			System.out.println("用户第一次查询数据库");
			//将数据保存到缓存中
			String value = ObjectMapperUtil.toJSON(treeList);
			jedis.set(key, value);
		}else {
			//说明:缓存中有数据,直接返回数据
			treeList = 
			ObjectMapperUtil.toObject(result, treeList.getClass());
			System.out.println("redis缓存查询!!!");
		}
		return treeList;
	}
}
