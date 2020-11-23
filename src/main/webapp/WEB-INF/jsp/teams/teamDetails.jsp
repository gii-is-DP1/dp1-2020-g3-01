<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="team">

	<h2>Team Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out
						value="${team.name}" /></b></td>
		</tr>
		<tr>
			<th>Creation Date</th>
			<td><c:out value="${team.creationDate}" /></td>
		</tr>
		<tr>
			<th>Nif</th>
			<td><c:out value="${team.nif}" /></td>
		</tr>
	</table>

	<%-- <spring:url value="{managerId}/teams/details" var="viewTeamsUrl">
		<spring:param name="managerId" value="${manager.id}" />
	</spring:url> --%>

</petclinic:layout>

