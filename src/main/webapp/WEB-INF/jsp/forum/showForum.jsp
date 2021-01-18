
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="showForum">

	<h2>Team Forum</h2>

	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out value="${forum.name}" /></b></td>
		</tr>
		<tr>
			<th>Creation Date</th>
			<td><c:out value="${forum.creationDate}" /></td>
		</tr>
	</table>
	<spring:url value="{forumId}/deleteForum" var="deleteForum"><spring:param name="forumId" value="${forum.id}" />
	</spring:url>
		<a href="${fn:escapeXml(deleteForum)}" class="btn btn-default">Delete Team Forum</a>
	<spring:url value="{forumId}/editForum" var="editForum"><spring:param name="forumId" value="${forum.id}" />
	</spring:url>
		<a href="${fn:escapeXml(editForum)}" class="btn btn-default">Edit Forum Name</a>
		
	<h2>Threads</h2>
	<spring:url value="{forumId}/thread/newThread" var="newThread">
	<spring:param name="forumId" value="${forum.id}" />
	</spring:url>
		<a href="${fn:escapeXml(newThread)}" class="btn btn-default">Create a new thread</a>
	<table class="table table-striped">
		<c:forEach var="thread" items="${forum.threads}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt><c:out value="${thread.id}" /></dt>
						<dd>
							<c:out value="${thread.title}" />
						</dd>
						<dd>
							<spring:url value="thread/{threadId}/viewThread" var="viewThread">
							<spring:param name="threadId" value="${thread.id}" />
							</spring:url>
							<a href="${fn:escapeXml(viewThread)}" class="btn btn-default">View Thread</a>
						</dd>
						<dd>
							<spring:url value="{forumId}/{threadId}/deleteThread" var="deleteThread">
							<spring:param name="threadId" value="${thread.id}" />
							<spring:param name="forumId" value="${forum.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteThread)}" class="btn btn-default">Delete Thread</a>
						</dd>
					</dl> 
					
				</td>
			</tr>
		</c:forEach>
	</table>
</petclinic:layout>