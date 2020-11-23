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
    <a href="${fn:escapeXml(addTeamsUrl)}" class="btn btn-default">Create Team</a>
    
    <spring:url value="{managerId}/teams/{teamId}/pilots/new" var="addPilotUrl">
        <spring:param name="managerId" value="${manager.id}"/>
        <spring:param name="teamId" value="${team.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addPilotUrl)}" class="btn btn-default">Add Pilot</a>
    
    <spring:url value="{managerId}/pilots/{pilotId}/details" var="pilotDetailsUrl">
    	<spring:param name="managerId" value="${manager.id}"/>
        <spring:param name="pilotId" value="${pilot.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(pilotDetailsUrl)}" class="btn btn-default">Show Pilot</a>

	

</petclinic:layout>
