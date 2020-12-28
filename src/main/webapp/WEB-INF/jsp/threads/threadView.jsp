
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="threadView">

	<h2><c:out value="${thread.title}" /></h2>
	
	<table class="table table-striped">
		<c:forEach var="message" items="${thread.messages}">
			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt><c:out value="${message.title}" /></dt>
						<dd>
							<c:out value="${message.text}" />
						</dd>
						<dt>
							<c:out value="${message.user.username}" />
						</dt>
					</dl> 
					
				</td>
			</tr>
		</c:forEach>
	</table>
	<spring:url value="message/new" var="newMessage">
	</spring:url>
		<a href="${fn:escapeXml(newMessage)}" class="btn btn-default">Post a new message</a>
</petclinic:layout>