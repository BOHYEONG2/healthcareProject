package kr.ac.kopo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import kr.ac.kopo.vo.ExerciseVO;
import kr.ac.kopo.vo.UserVO;

@Mapper
public interface ExerciseMapper {
	
	// �옉紐낃퇋移�
	// �뻾�쐞_湲곌컙_紐⑺몴_with議곌굔_and異붽�議곌굔_and異붽�議곌굔...
	
	@Select("SELECT * FROM EXERCISE WHERE USER_ID = #{uid} ORDER BY E_NO DESC ")
	List<ExerciseVO> select_ExerciseDataTest(String uid);
	
	
	
	@Insert("INSERT INTO exercise (E_NO, USER_ID, TYPE, CNT) "
			+ "VALUES (COALESCE((SELECT MAX(E_NO) + 1 FROM exercise), 1), #{userId}, #{type}, #{cnt}) ")
	void insert_exerciseData(ExerciseVO vo);
	
	
	/**
	 * �씪, 二�, �썡, �뀈, �쟾泥댁떆媛� �슫�룞 �룞�옉 �슏�닔 珥� �빀 援ы븯�뒗 硫붿꽌�뱶�뱾
	 * @param vo
	 * @return
	 */
	@Select("SELECT COALESCE(sum(cnt), 0) FROM exercise "
			+ "WHERE END_TIME >= TRUNC(SYSDATE) AND END_TIME < TRUNC(SYSDATE) + 1 "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_today_exerciseCount_withUid_andType(ExerciseVO vo);
	
	
	@Select("SELECT COALESCE(sum(cnt), 0) FROM exercise "
			+ "WHERE END_TIME >= TRUNC(SYSDATE, 'IW') AND END_TIME < TRUNC(SYSDATE, 'IW') + 7 "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_week_exerciseCount_withUid_andType(ExerciseVO vo);
	
	
	@Select("SELECT COALESCE(SUM(cnt), 0) FROM exercise "
			+ "WHERE END_TIME >= TRUNC(SYSDATE, 'MONTH') AND END_TIME < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1) "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_month_exerciseCount_withUid_andType(ExerciseVO vo);
	

	@Select("SELECT COALESCE(SUM(CNT), 0) FROM EXERCISE "
			+ "WHERE TO_CHAR(END_TIME, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY') "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_year_exerciseCount_withUid_andType(ExerciseVO vo);
	
	
	@Select("SELECT COALESCE(sum(cnt), 0) FROM EXERCISE "
			+ "WHERE USER_ID = #{userId} AND TYPE = #{type} ")
	Integer select_allTime_exerciseCount_withUid_andType(ExerciseVO vo);
	//
	

	/**
	 * �씪, 二�, �썡, �뀈, �쟾泥댁떆媛� �슫�룞 �꽭�듃 珥� �빀 援ы븯�뒗 硫붿꽌�뱶�뱾
	 * @param vo
	 * @return
	 */
	@Select("SELECT COALESCE(count(cnt), 0) FROM exercise "
			+ "WHERE END_TIME >= TRUNC(SYSDATE) AND END_TIME < TRUNC(SYSDATE) + 1 "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_today_exerciseTryCount_withUid_andType(ExerciseVO vo);
	
	
	@Select("SELECT COALESCE(count(cnt), 0) FROM exercise "
			+ "WHERE END_TIME >= TRUNC(SYSDATE, 'IW') AND END_TIME < TRUNC(SYSDATE, 'IW') + 7 "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_week_exerciseTryCount_withUid_andType(ExerciseVO vo);
	
	
	@Select("SELECT COALESCE(count(cnt), 0) FROM exercise "
			+ "WHERE END_TIME >= TRUNC(SYSDATE, 'MONTH') AND END_TIME < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1) "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_month_exerciseTryCount_withUid_andType(ExerciseVO vo);

	
	@Select("SELECT COALESCE(count(CNT), 0) FROM EXERCISE "
			+ "WHERE TO_CHAR(END_TIME, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY') "
			+ "AND TYPE = #{type} AND USER_ID = #{userId} ")
	Integer select_year_exerciseTryCount_withUid_andType(ExerciseVO vo);
	
	
	@Select("SELECT COALESCE(count(cnt), 0) FROM EXERCISE "
			+ "WHERE USER_ID = #{userId} AND TYPE = #{type} ")
	Integer select_allTime_exerciseTryCount_withUid_andType(ExerciseVO vo);	
	//
	
	
	/**
	 * �쟾泥� �쑀�� �꽭�듃 �닔 �룊洹�
	 * @return
	 */
	@Select("SELECT AVG(COUNT_PER_USER) AS CNT "
			+ "FROM ( "
			+ "    SELECT USER_ID, COUNT(*) AS COUNT_PER_USER "
			+ "    FROM EXERCISE "
			+ "    WHERE END_TIME >= TRUNC(SYSDATE, 'MONTH') AND END_TIME < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1) "
			+ "    GROUP BY USER_ID "
			+ ") ")
	Integer select_month_allUserExerciseTryCountAverage();
			
	
	@Select("SELECT AVG(COUNT_PER_USER) AS CNT "
			+ "FROM ( "
			+ "    SELECT USER_ID, COUNT(*) AS COUNT_PER_USER "
			+ "    FROM EXERCISE "
			+ "    GROUP BY USER_ID "
			+ ") ")
	Integer select_allTime_allUserExerciseTryCountAverage();
	
	
	//
	
	
	
	/**
	 * �슫�룞 �뜲�씠�꽣 �젅肄붾뱶 媛��졇�삤湲�
	 * @param userId
	 * @return
	 */
	@Select("SELECT * FROM ( "
			+ "	SELECT * FROM exercise "
			+ "	WHERE user_id = #{userId} AND E_NO <= #{eNo} "
			+ "	ORDER BY END_TIME DESC "
			+ "	) "
			+ "WHERE rownum <= #{cnt} ")
	List<ExerciseVO> select_exerciseRecode_withUid_andRange(ExerciseVO vo);
	
	
	
	@Select("SELECT COALESCE(count(user_id), 0) FROM EXERCISE "
			+ "WHERE USER_ID = #{userId}")
	Integer select_allTime_exerciseTryCount_withUid(@Param("userId")String userId);
	
	
	
	@Select("SELECT USER_ID, COALESCE(MAX(CNT), 0 ) AS CNT FROM EXERCISE "
			+ "WHERE TYPE= #{type} GROUP BY USER_ID ORDER BY CNT DESC ")
	List<ExerciseVO> select_allTime_topFiveExerciseCount_withType(@Param("type")String type);
	
}
