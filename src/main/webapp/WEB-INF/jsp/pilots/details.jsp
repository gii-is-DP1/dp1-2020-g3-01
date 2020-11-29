<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="owners">
<jsp:body>
	<h2>Pilot Information</h2>

	
	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out
						value="${pilot.firstName} ${pilot.lastName}" /></b></td>
		</tr>
		<tr>
			<th>Birth Date</th>
			<td><c:out value="${pilot.birthDate}" /></td>
		</tr>
		<tr>
			<th>Residence</th>
			<td><c:out value="${pilot.residence}" /></td>
		</tr>
		<tr>
			<th>Nationality</th>
			<td><c:out value="${pilot.nationality}" /></td>
		</tr>
		<tr>
			<th>Height</th>
			<td><c:out value="${pilot.height}" /></td>
		</tr>
		<tr>
			<th>Weight</th>
			<td><c:out value="${pilot.weight}" /></td>
		</tr>
	</table>

    <spring:url value="/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update" var="updatePilotUrl">
        <spring:param name="managerId" value="${manager.id}"/>
        <spring:param name="teamId" value="${team.id}"/>
        <spring:param name="pilotId" value="${pilot.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(updatePilotUrl)}" class="btn btn-default">Modify Pilot</a>
    <spring:url value="/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/remove" var="removePilotUrl">
        <spring:param name="managerId" value="${manager.id}"/>
        <spring:param name="teamId" value="${team.id}"/>
         <spring:param name="pilotId" value="${pilot.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(removePilotUrl)}" class="btn btn-default">Remove Pilot</a>
    

</jsp:body>	

</petclinic:layout>