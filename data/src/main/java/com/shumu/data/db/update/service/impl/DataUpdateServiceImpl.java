package com.shumu.data.db.update.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.db.update.entity.DataUpdateTable;
import com.shumu.data.db.update.mapper.DataUpdateMapper;
import com.shumu.data.db.update.service.IDataUpdateService;

/**
 * @description: DataUpdate
 * @author: Li
 * @date: 2023-02-14
 */
@Service
public class DataUpdateServiceImpl extends ServiceImpl<DataUpdateMapper, DataUpdateTable>
		implements IDataUpdateService {
	@Autowired
	private DataUpdateMapper dataUpdateMapper;

	@Override
	public void insertData(String table, List<String> fields, Map<String, Object> data) {
		dataUpdateMapper.insertData(table, fields, data);
	}

	@Override
	public void batchInsertData(String table, List<String> fields, List<Map<String, Object>> datas) {
		dataUpdateMapper.batchInsertData(table, fields, datas);
	}

	@Override
	public void updateDataById(String table, String id, List<String> fields, Map<String, Object> data) {
		dataUpdateMapper.updateDataById(table, id, fields, data);
	}

	@Override
	public void updateDataByFlag(String table, List<String> flags, List<String> fields, Map<String, Object> data) {
		dataUpdateMapper.updateDataByFlag(table, flags, fields, data);
	}

	@Override
	public List<String> getPrimaryKey(String table, String primary, List<Map<String, Object>> flags) {
		try {
			return dataUpdateMapper.getPrimaryKey(table, primary, flags);
		} catch (Exception e) {
			return null;
		}
	}
}
