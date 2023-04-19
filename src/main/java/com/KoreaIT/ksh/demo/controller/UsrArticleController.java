package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.ArticleService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Controller
public class UsrArticleController {


	@Autowired
	private ArticleService articleService;

	// 액션메서드
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(HttpSession httpsession, int id, String title, String body) {
		
		boolean isLogined = false;
	
		if (httpsession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		}
		

		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다", id), id);
		} else if(article.getMemberId()==(int) httpsession.getAttribute("loginedMemberId")) {

		articleService.modifyArticle(id, title, body);

		return ResultData.from("S-1", Ut.f("%d번 글을 수정 했습니다", id), id);
		} else {
			return ResultData.from("F-C", "권한이 없습니다");
		}
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpsession, int id) {
		
		boolean isLogined = false;

		if (httpsession.getAttribute("loginedMemberId") != null) {
			isLogined = true;

		}
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		} 
		

		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다", id), id);
		} else if(article.getMemberId()==(int) httpsession.getAttribute("loginedMemberId")) {

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 삭제 했습니다", id), id);
		} else {
			return ResultData.from("F-C", "권한이 없습니다");
		}
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(HttpSession httpsession,String title, String body) {
		
		boolean isLogined = false;
		int loginedMemberId =0 ;
		
		if (httpsession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpsession.getAttribute("loginedMemberId");
		}
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		}
		
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, loginedMemberId);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticle(id);

		return ResultData.newData(writeArticleRd, article);
	}

	@RequestMapping("/usr/article/list")
	public String getArticles(Model model) {
		List<Article> articles = articleService.articles();
		
		model.addAttribute("articles", articles);
		return "/usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String getArticle(Model model, int id) {

		Article article = articleService.getArticle(id);
		
		model.addAttribute("article", article);
		return "/usr/article/detail";
	}

}