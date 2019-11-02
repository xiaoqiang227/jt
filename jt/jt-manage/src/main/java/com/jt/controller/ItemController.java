package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController //返回数据都是json
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	//http://localhost:8091/item/query?page=1&rows=20
	@RequestMapping("/query")
	public EasyUITable findItemByPage
	(Integer page,Integer rows) {

		return itemService.findItemByPage(page,rows);
	}

	/**
	 * 业务需求:
	 * 	url: http://localhost:8091/item/save
	 * 	参数: id=1&title=112312
	 * 	返回值: sysResult 200  201
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {

		itemService.saveItem(item,itemDesc);
		return SysResult.success();
	}

	/**
	 * 实现商品修改
	 * 规则:一般都需要通过主键进行修改
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}

	
	/**
	 * 商品删除
	 * url:/item/delete
	 * 参数: ids: id,id,id
	 * 返回值: SysResult
	 */
	@RequestMapping("/delete")
	public SysResult deleteItems(Long[] ids) {
		
		itemService.deleteItems(ids);
		return SysResult.success();
	}
	
	/**
	 * 下架
	 * url:http://localhost:8091/item/instock
	 */
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
		int status = 2;	//下架
		itemService.updateItemState(status,ids);
		return SysResult.success();
	}
	
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		int status = 1;	//上架
		itemService.updateItemState(status,ids);
		return SysResult.success();
	}


	/**
	 * 实现商品详情查询
	 * {status=200,msg=,data:{itemDesc的JSON数据}}
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById
				(@PathVariable Long itemId) {
		ItemDesc itemDesc = 
				itemService.findItemDescById(itemId);
		//将数据回传给页面
		return SysResult.success(itemDesc);
	}


}
