package kr.opensoftlab.oslits.stm.stm1000.stm1100.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
















import kr.opensoftlab.oslits.com.fms.web.service.FileMngService;
import kr.opensoftlab.oslits.com.vo.LoginVO;
import kr.opensoftlab.oslits.stm.stm1000.stm1100.service.Stm1100Service;
import kr.opensoftlab.oslits.stm.stm1000.stm1100.vo.Stm1100VO;
import kr.opensoftlab.sdf.util.ReqHistoryMngUtil;
import kr.opensoftlab.sdf.util.RequestConvertor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : Stm1100Controller.java
 * @Description : Stm1100Controller Controller class
 * @Modification Information
 *
 * @author 공대영
 *  * @since 2018.07.23.
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Controller
public class Stm1100Controller {

	/** 로그4j 로거 로딩 */
	private static final Logger Log = Logger.getLogger(Stm1100Controller.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;


	/** FileMngService */
	@Resource(name="fileMngService")
	private FileMngService fileMngService;

	@Value("${Globals.fileStorePath}")
	private String tempPath;

	/** EgovFileMngUtil - 파일 업로드 Util */
	@Resource(name="EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;	

	@Resource(name = "egovFileIdGnrService")
	private EgovIdGnrService idgenService;

	@Resource(name = "historyMng")
	private ReqHistoryMngUtil historyMng;

	@Resource(name = "stm1100Service")
	private Stm1100Service stm1100Service;
	
	
	/**
	 * API 토큰관리 화면으로 이동
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stm/stm1000/stm1100/selectStm1100View.do")
	public String selectStm1100View( HttpServletRequest request, HttpServletResponse response, ModelMap model ) throws Exception {
		return "/stm/stm1000/stm1100/stm1100";
	}
	
	/**
	 * 프로젝트별 지정권한 API 정보 조회
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stm/stm1000/stm1100/selectStm1100ProjectListAjax.do")
	public ModelAndView selectStm1100ProjectListAjax( HttpServletRequest request, HttpServletResponse response, ModelMap model ) throws Exception {

		try{
			// request 파라미터를 map으로 변환
			Map<String, String> paramMap = RequestConvertor.requestParamToMapAddSelInfo(request, true);
			//현재 페이지 값, 보여지는 개체 수
			
			HttpSession ss = request.getSession();
    		LoginVO loginVO = (LoginVO) ss.getAttribute("loginVO");

    		
        	paramMap.put("licGrpId", loginVO.getLicGrpId());
			
			paramMap.put("prjGrpId", (String)ss.getAttribute("selPrjGrpId"));
			
			List apiList = stm1100Service.selectStm1100ProjectListAjax(paramMap);
			
			model.addAttribute("apiList", apiList);

			return new ModelAndView("jsonView");
		}
		catch(Exception ex){
			Log.error("selectStm1100ProjectListAjax()", ex);
			throw new Exception(ex.getMessage());
		}
	}
	
	/**
	 * API 토큰관리  등록/수정 화면으로 이동
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stm/stm1000/stm1100/selectStm1101View.do")
	public String selectStm1101View( HttpServletRequest request, HttpServletResponse response, ModelMap model ) throws Exception {
		return "/stm/stm1000/stm1100/stm1101";
	}


	/**
	 * 프로젝트별 API 토큰 권한 조회
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stm/stm1000/stm1100/selectStm1100InfoAjax.do")
	public ModelAndView selectStm1100InfoAjax( HttpServletRequest request, HttpServletResponse response, ModelMap model ) throws Exception {

		try{
			// request 파라미터를 map으로 변환
			Map<String, String> paramMap = RequestConvertor.requestParamToMapAddSelInfo(request, true);
			//현재 페이지 값, 보여지는 개체 수
			
			HttpSession ss = request.getSession();
    		LoginVO loginVO = (LoginVO) ss.getAttribute("loginVO");

    		
        	paramMap.put("licGrpId", loginVO.getLicGrpId());
			
			paramMap.put("prjGrpId", (String)ss.getAttribute("selPrjGrpId"));
			
			List apiList = stm1100Service.selectStm1100ProjectAuthList(paramMap);
						
			
			model.addAttribute("apiList", apiList);
			

			return new ModelAndView("jsonView");
		}
		catch(Exception ex){
			Log.error("selectStm1100InfoAjax()", ex);
			throw new Exception(ex.getMessage());
		}
	}
	
	/**
	 * 프로젝트별 API 토큰 권한 지정/해제
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stm/stm1000/stm1100/saveStm1100Ajax.do")
    public ModelAndView saveStm1100Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model ) throws Exception {
    	try{
    		// request 파라미터를 map으로 변환
        	Map<String, String> paramMap = RequestConvertor.requestParamToMap(request, true);
        	
        	HttpSession ss = request.getSession();
    		LoginVO loginVO = (LoginVO) ss.getAttribute("loginVO");

    	
        	String[] apiIds =  request.getParameterValues("apiId");
        	String[] isCheckeds =  request.getParameterValues("isChecked");
        	String[] orgCheckeds =  request.getParameterValues("orgChecked");
        	
        	List<Stm1100VO> list = new ArrayList<Stm1100VO>();
        	
        	Stm1100VO stm1100VO =null;
        	
        	for (int i = 0; i < apiIds.length; i++) {
        		stm1100VO  = new Stm1100VO();
        		
        		stm1100VO.setPrjId(paramMap.get("prjId"));
        		stm1100VO.setApiId(apiIds[i]);
        		stm1100VO.setIsChecked( isCheckeds[i]  );
        		stm1100VO.setOrgChecked(orgCheckeds[i]);
        		
        		stm1100VO.setRegUsrId(paramMap.get("regUsrId"));
        		stm1100VO.setRegUsrIp(paramMap.get("regUsrIp"));
        		
        		stm1100VO.setLicGrpId(loginVO.getLicGrpId());
        		
        		list.add(stm1100VO);
			}
        	
        	// 프로젝트 생성관리 수정
        	stm1100Service.saveStm1100(list);
        	
        	model.put("prjId", paramMap.get("prjId"));
      
        	model.addAttribute("message", egovMessageSource.getMessage("success.common.save"));
        	
        	return new ModelAndView("jsonView", model);
    	}
    	catch(Exception ex){
    		Log.error("saveStm1100Ajax()", ex);
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.save"));
    		return new ModelAndView("jsonView");
    	}
    }

}
