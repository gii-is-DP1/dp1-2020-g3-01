<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="teams">

	<h2>Team Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out value="${team.name}" /></b></td>
		</tr>
		<tr>
			<th>Creation Date</th>
			<td><c:out value="${team.creationDate}" /></td>
		</tr>
		<tr>
			<th>NIF</th>
			<td><c:out value="${team.nif}" /></td>
		</tr>
	</table>

	<spring:url value="edit" var="edit">
	</spring:url>
	<a href="${fn:escapeXml(edit)}" class="btn btn-default">Edit Team</a>

	<br />
	<br />
	<h2>Pilots</h2>
	<table class="table table-striped">
		<c:forEach var="pilot" items="${team.pilot}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${pilot.firstName} ${pilot.lastName}" />
						</dd>
						<dt>Birth Date</dt>
						<dd>
							<petclinic:localDate date="${pilot.birthDate}"
								pattern="yyyy-MM-dd" />
						</dd>
						<dt>Residence</dt>
						<dd>
							<c:out value="${pilot.residence}" />
						</dd>
						<dt>Nationality</dt>
						<dd>
							<c:out value="${pilot.nationality}" />
						</dd>
						<dt>DNI</dt>
						<dd>
							<c:out value="${pilot.dni}" />
						</dd>
						<dt>Number</dt>
						<dd>
							<c:out value="${pilot.number}" />
						</dd>
						<dt>Height</dt>
						<dd>
							<c:out value="${pilot.height}" />
						</dd>
						<dt>Weight</dt>
						<dd>
							<c:out value="${pilot.weight}" />
						</dd>
					</dl> 
					
					<spring:url value="pilots/{pilotId}/details" var="showPilot">
					<spring:param name="pilotId" value="${pilot.id}" />
					</spring:url> <a href="${fn:escapeXml(showPilot)}" class="btn btn-default">Show
						Pilot</a>
				</td>
			</tr>
		</c:forEach>
	</table>

	<spring:url value="pilots/new" var="createPilot">
	</spring:url>
	<a href="${fn:escapeXml(createPilot)}" class="btn btn-default">Add
		Pilot</a>


	<br />
	<br />
	<br />
	<h2>Mechanics</h2>

	<table class="table table-striped">
		<c:forEach var="mechanic" items="${team.mechanic}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${mechanic.firstName} ${mechanic.lastName}" />
						</dd>
						<dt>Birth Date</dt>
						<dd>
							<petclinic:localDate date="${mechanic.birthDate}"
								pattern="yyyy-MM-dd" />
						</dd>
						<dt>Residence</dt>
						<dd>
							<c:out value="${mechanic.residence}" />
						</dd>
						<dt>Nationality</dt>
						<dd>
							<c:out value="${mechanic.nationality}" />
						</dd>
						<dt>DNI</dt>
						<dd>
							<c:out value="${mechanic.dni}" />
						</dd>
						<dt>Type</dt>
						<dd>
							<c:out value="${mechanic.type}" />
						</dd>
					</dl> <spring:url value="mechanics/{mechanicId}/details"
						var="showMechanic">
						<spring:param name="mechanicId" value="${mechanic.id}" />
					</spring:url> <a href="${fn:escapeXml(showMechanic)}" class="btn btn-default">Show
						Mechanic</a>
				</td>

			</tr>

		</c:forEach>
	</table>

	<spring:url value="mechanics/new" var="createMechanic">
	</spring:url>
	<a href="${fn:escapeXml(createMechanic)}" class="btn btn-default">Add
		Mechanic</a>

</petclinic:layout>
