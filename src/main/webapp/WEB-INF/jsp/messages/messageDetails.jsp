<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="message">

	<h2>Message Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Title</th>
			<td><b><c:out value="${message.title}" /></b></td>
		</tr>
		<tr>
			<th>Text</th>
			<td><c:out value="${message.text}" /></td>
		</tr>

	</table>

	<spring:url value="/teams/forum/thread/messages/{messageId}/edit" var="editMessageUrl">
	<spring:param name="messageId" value="${message.id}" />
	
	</spring:url>
	<spring:url value="/teams/forum/thread/{threadId}/messages/{messageId}/delete" var="deleteMessageUrl">
		<spring:param name="messageId" value="${message.id}" />
		<spring:param name="threadId" value="${thread.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editMessageUrl)}" class="btn btn-default" style="margin-right: 1rem;">Edit Message</a>
	<a href="${fn:escapeXml(deleteMessageUrl)}" class="btn btn-default" style="margin-right: 1rem;">Delete Message</a>
	
</petclinic:layout>