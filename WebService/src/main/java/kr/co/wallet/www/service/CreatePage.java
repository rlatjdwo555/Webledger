package kr.co.wallet.www.service;

import org.springframework.stereotype.Service;

import kr.co.wallet.www.vo.Criteria;
import kr.co.wallet.www.vo.PageMaker;

@Service
public class CreatePage {
	
	public PageMaker getPageMaker(Criteria cri, int totalCount){
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalCount);
		return pageMaker;
	}
}
