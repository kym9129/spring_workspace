package com.kh.spring.memo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.memo.model.dao.MemoDao;
import com.kh.spring.memo.model.vo.Memo;

/**
 * 
 * 의존주입 받은 객체는 우리가 작성한
 * MemoController, MemoServiceImpl, MemoDaoImpl타입 객체가 아닌
 * proxy객체이다.
 *
 */
@Service
public class MemoServiceImpl implements MemoService {
	
	@Autowired
	private MemoDao memoDao;

	@Override
	public int insertMemo(String memo) {
		return memoDao.insertMemo(memo);
	}

	@Override
	public List<Memo> selectMemoList() {
		return memoDao.selectMemoList();
	}

}
