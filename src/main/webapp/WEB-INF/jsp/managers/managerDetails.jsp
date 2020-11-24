<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="owners">

	<h2>Manager Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out
						value="${manager.firstName} ${manager.lastName}" /></b></td>
		</tr>
		<tr>
			<th>Birth Date</th>
			<td><c:out value="${manager.birthDate}" /></td>
		</tr>
		<tr>
			<th>Residence</th>
			<td><c:out value="${manager.residence}" /></td>
		</tr>
		<tr>
			<th>Nationality</th>
			<td><c:out value="${manager.nationality}" /></td>
		</tr>
	</table>

    <spring:url value="{managerId}/teams/new" var="addTeamsUrl">
        <spring:param name="managerId" value="${manager.id}"/>
    </spring:url>
  	<spring:url value="{managerId}/teams/details" var="viewTeamsUrl">
		<spring:param name="managerId" value="${manager.id}" />
	</spring:url>
  
  	<c:choose>
		<c:when test="${team}">
			<a href="${fn:escapeXml(addTeamsUrl)}" class="btn btn-default">Add
				Team</a>
		</c:when>
		<c:otherwise>
			<a href="${fn:escapeXml(viewTeamsUrl)}" class="btn btn-default">View
				Team</a>
		</c:otherwise>
	</c:choose>

</petclinic:layout>
