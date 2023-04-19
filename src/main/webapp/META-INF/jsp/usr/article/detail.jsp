<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var = "pageTitle" value = "ARTICLE LIST"/>
<%@ include file="../common/head.jspf" %>

<section class="mt-5 text-xl">
<div class="mx-auto">
		<table class="table-box-type-1">
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>제목</th>
				<th>내용</th>
				<th>작성자</th>
			</tr>
		</thead>
		<tbody>

			<tr>
				<th>${article.id }</th>
				<th>${article.regDate.substring(0,10) }</th>
				<th>${article.title }</th>
				<th>${article.body }</th>
				<th>${article.name }</th>
			</tr>

		</tbody>
	</table>

		</div>
</section>
