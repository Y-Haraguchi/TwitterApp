<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
</head>
<body>
<div class="main-contens">
	<c:if test="${ not empty errorMessages }">
		<div class="errorMessage">
			<ul>
				<c:forEach items="${errorMessages}" var="message">
					<li><c:out value="${message}" />
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session" />
	</c:if>
	<form action="signup"  method="post"><br />
		<label for="name">名前</label>
		<%--HTMLの入力受付 --%>
		<input name="name" id="name">（名前はあなたの公開プロフィールに表示されます）<br />

		<label for="account">アカウント名</label>
		<input name="account" id="account" />（あなたの公開プロフィール：http://localhost:8080/?account=アカウント名）<br />

		<label for="passward">パスワード</label>
		<input name="passward" type="passward" id="passward" /><br />

		<label for="email">メールアドレス</label>
		<input name="email" id="email" /><br />

		<label for="description" >説明</label>
		<textarea name="description" cols="35" rows="5" id="description"></textarea><br />

		<input type="submit" value="登録" /><br />
		<a href="./">戻る</a>
	</form>
	<div class="copyright">Copyright(c)Yugi Haraguchi</div>
</div>
</body>
</html>