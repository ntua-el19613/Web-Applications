package db;

import java.sql.Date;

public class gameData {
	
	private final Integer id;
	private final Date gameDate;
	private final Integer team1id;
	private final Integer team1score;
	private final Integer team2id;
	private final Integer team2score;
	
	public gameData(Integer id, Date gameDate, Integer team1id, Integer team1score, Integer team2id, Integer team2score) {
		this.id = id;
		this.gameDate = gameDate;
		this.team1id = team1id;
		this.team1score = team1score;
		this.team2id = team2id;
		this.team2score = team2score;
	}

	public gameData(Date gameDate, Integer team1id, Integer team1score, Integer team2id, Integer team2score) {
		this.id = null;
		this.gameDate = gameDate;
		this.team1id = team1id;
		this.team1score = team1score;
		this.team2id = team2id;
		this.team2score = team2score;
	}
	
	public Integer getId() {
		return id;
	}

	public Date getGameDate() {
		return gameDate;
	}
	
	public Integer getTeam1id() {
		return team1id;
	}
	
	public Integer getTeam1score() {
		return team1score;
	}
	
	public Integer getTeam2id() {
		return team2id;
	}
	
	public Integer getTeam2score() {
		return team2score;
	}

	@Override
	public String toString() {
		return "gameData [id=\" + id + \", gameDate=\" + gameDate + \", team1id=\" + team1id + \", team1score=\" + team1score + \", team2id=\" + team2score + \"]";
	}
}
