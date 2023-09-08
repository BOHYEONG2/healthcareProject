package kr.ac.kopo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kopo.dao.ExerciseMapper;
import kr.ac.kopo.dao.UserMapper;
import kr.ac.kopo.service.AccountService;
import kr.ac.kopo.vo.ExerciseVO;
import kr.ac.kopo.vo.UserVO;

@Controller
public class ViewController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserVO uvo;
	
	@Autowired
	private ExerciseVO evo;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ExerciseMapper exerciseMapper;
	
    public ViewController() {
    	
    }
	
	@RequestMapping("/index")
	public String index(Model model) {
		String uid = (String)session.getAttribute("uid");
		uid = uid != null ? uid : "";
		
		evo.setType("dumbbel");
		evo.setUserId(uid);
		
		List<ExerciseVO> evoList = exerciseMapper.select_ExerciseDataTest(uid);
		Integer todayCount = exerciseMapper.select_today_exerciseCount_withUid_andType(evo);
		List<ExerciseVO> topList = exerciseMapper.select_allTime_topFiveExerciseCount_withType("dumbbel");
		
		model.addAttribute("exList" , evoList);
		model.addAttribute("todayCount", todayCount);
		model.addAttribute("topFiveList", topList);
		System.out.println(topList);
		
		return "Index";
	}
	
	@RequestMapping("/toLogin")
	public String toLogin(Model model) {
		
		return "account/SingIn";
	}
	
	@RequestMapping("/toSingUp")
	public String toSingUp(Model model) {
		return "account/SingUp";
	}
	
	@GetMapping("/toMyPage")
	public String toMyPage(Model model) {
		ExerciseVO evo2 = new ExerciseVO();
		String uid = (String)session.getAttribute("uid");
		
		evo.setUserId(uid);
		evo.setType("dumbbel");
		
		evo2.setUserId(uid);
		evo2.seteNo(17);
		evo2.setCnt(5);
		
		Integer totalTry = exerciseMapper.select_allTime_exerciseTryCount_withUid(uid);
		List<ExerciseVO> exRecodeList = exerciseMapper.select_exerciseRecode_withUid_andRange(evo2);
		System.out.println(exRecodeList);
		
		model.addAttribute("totalTry", totalTry);
		model.addAttribute("exRecodeList", exRecodeList);
		
		/*
		 湲곕뒫
		 
		 �쑀�� �슫�룞 �넻怨�
		 	湲곕낯 蹂댁뿬二쇨린
		 		�삤�뒛, �씠踰덉＜, �씠踰덈떖 媛��옣 留롮씠 �븳 �슫�룞
		 			�꽭�듃, 移댁슫�듃
		 	�꽑�깮�빐�꽌 �긽�꽭 蹂닿린
		 		�떆媛꾨퀎 �꽭�듃, 移댁슫�듃
		 
		 */
		
		return "myPage/MyPage";
	}
	
	
	
}
