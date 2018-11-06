package kr.opensoftlab.oslits.stm.stm1000.stm1000.service.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.opensoftlab.oslits.stm.stm1000.stm1000.service.Stm1000Service;
import kr.opensoftlab.oslits.stm.stm1000.stm1000.vo.Stm1000VO;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : Stm1000ServiceImpl.java
 * @Description : Stm1000ServiceImpl Business Implement class
 * @Modification Information
 *
 * @author 공대영
 *  * @since 2018.07.23.
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Service("stm1000Service")
public class Stm1000ServiceImpl extends EgovAbstractServiceImpl implements Stm1000Service {
	/** DAO Bean Injection */
    @Resource(name="stm1000DAO")
    private Stm1000DAO stm1000DAO;  
	
    @Override
	public List<Stm1000VO> selectStm1000List(Stm1000VO stm1000vo)
			throws Exception {
		// TODO Auto-generated method stub
		return stm1000DAO.selectStm1000List(stm1000vo);
	}

	@Override
	public int selectStm1000ListCnt(Stm1000VO stm1000vo) throws Exception {
		// TODO Auto-generated method stub
		return stm1000DAO.selectStm1000ListCnt(stm1000vo);
	}

	@Override
	public Object saveStm1000Info(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		
		String insNewApiId ="";
		String popupGb = (String)paramMap.get("popupGb");
		
		if("insert".equals(popupGb)){
			insNewApiId = stm1000DAO.insertStm1001Info(paramMap);
			//생성된 키가 없으면 튕겨냄
		}else if("update".equals(popupGb)){
			int result = stm1000DAO.updateStm1001Info(paramMap);
		}

		return null;
	}
	
	@Override
	public Map selectStm1000Info(Map<String, String> paramMap) {
		return stm1000DAO.selectStm1000Info(paramMap);
	}

	@Override
	public void deleteStm1000Info(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		stm1000DAO.deleteStm1000Info(paramMap);
	}
	
	@Override
	public int selectStm1000UseCountInfo(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return stm1000DAO.selectStm1000UseCountInfo(paramMap);
	}
	
	@Override
	public List<Map<String, String> > selectStm1000ApiUrlList(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return stm1000DAO.selectStm1000ApiUrlList(paramMap) ;
	}
	

}
