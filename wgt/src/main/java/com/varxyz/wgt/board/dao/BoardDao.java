package com.varxyz.wgt.board.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.varxyz.wgt.board.domain.Board;

@Component("boardDao")
public class BoardDao {
	private JdbcTemplate jdbcTemplate;

	public BoardDao(DataSource dataSource) {
	      jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//게시글 생성
	public void create(Board board) {
		String sql = "INSERT INTO Board (number, title, content, image)" + " VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, board.getNumber(), board.getTitle(), board.getContent(), board.getImage());
	}
	
	//게시글 읽기
	public List<Board> read() {
		String sql = "SELECT * FROM Board";
		List<Board> list = new ArrayList<>();
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Board>(Board.class));
		return null;
	}
	
	//게시글 수정
	public void update(Board board) {
		String sql = "UPDATE Board SET title = ?, content = ?, image =? WHERE userId = ?";
		jdbcTemplate.update(sql, board.getTitle(), board.getContent(), board.getImage(), board.getUserId());
	}

	//게시글 삭제
	public List<Board> delete(String userId) {
		String sql = "DELETE FROM Board WHERE userId = ?";
		List<Board> list = new ArrayList<>();
		jdbcTemplate.update(sql, userId);
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Board>(Board.class));
		return list;
	}
	
	//ID로 게시글 찾기
	public List<Board> find(String userId) {
		String sql = "SELECT * FROM Board WHERE userId = ?";
		List<Board> list = new ArrayList<>();
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Board>(Board.class));
		return list;
	}
}
